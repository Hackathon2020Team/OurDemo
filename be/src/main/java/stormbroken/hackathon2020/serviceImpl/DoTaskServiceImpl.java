package stormbroken.hackathon2020.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stormbroken.hackathon2020.constant.PersonTaskStatus;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.constant.TaskStatus;
import stormbroken.hackathon2020.dao.DoTaskMapper;
import stormbroken.hackathon2020.entity.AdministerTask;
import stormbroken.hackathon2020.entity.DoTask;
import stormbroken.hackathon2020.entity.Task;
import stormbroken.hackathon2020.entity.User;
import stormbroken.hackathon2020.form.task.doTask.DoTaskAddForm;
import stormbroken.hackathon2020.form.task.doTask.DoTaskCheckForm;
import stormbroken.hackathon2020.form.task.doTask.DoTaskReceiveForm;
import stormbroken.hackathon2020.service.AdministerTaskService;
import stormbroken.hackathon2020.service.DoTaskService;
import stormbroken.hackathon2020.service.TaskService;
import stormbroken.hackathon2020.service.UserService;
import stormbroken.hackathon2020.utils.UserUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Service
public class DoTaskServiceImpl implements DoTaskService {
    private static String NOT_LOGIN = "用户没有登录";
    private static String DATA_BASE = "Database Exception at DoTask:";
    private static String NOT_FIND = "没有找到任务执行记录";

    private static String ADD_SUCCESS = "创建执行任务成功";
    private static String ADD_STATUS_FAIL = "任务状态不允许添加执行者";
    private static String NOT_FIND_TASK = "没有找到目标任务";
    private static String NOT_LOWER = "不是给直属下级分配任务";
    private static String REPEAT_ADD = "不可以重复添加执行任务";

    private static String RECEIVE_SUCCESS = "处理待接受任务成功";
    private static String RECEIVE_FAIL = "处理待接受任务失败";
    private static String RECEIVE_STATUS_FAIL = "任务状态不允许被接受";

    private static String CHECK_SUCCESS = "提起检查成功";
    private static String CHECK_FAIL = "提起检查成功";
    private static String CHECK_STATUS_FAIL = "任务状态不允许被检查";
    private static String CHECK_NO_PRIVILEGE = "没有权限提交任务检查";

    private static String NO_PRIVILEGE = "没有权限操作";

    @Autowired
    DoTaskMapper doTaskMapper;
    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    AdministerTaskService administerTaskService;

    /**
     * 添加执行任务
     * @param doTaskAddForm
     * @return
     */
    @Override
    public SimpleResponse add(DoTaskAddForm doTaskAddForm) {
        // 登录检查
        User user = UserUtil.getByToken(doTaskAddForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查是否有当前任务，以及是否是发起人
        Task task = taskService.findById(doTaskAddForm.getTaskId());
        if(task == null){
            return SimpleResponse.error(NOT_FIND_TASK);
        }

        if(!hasPrivilege(user.getId(), task)){
            return SimpleResponse.error(NO_PRIVILEGE);
        }

        // 检查当前任务状态是否正确
        if(!canAdd(task.getStatus())){
            return SimpleResponse.error(ADD_STATUS_FAIL);
        }

        // 检查是否是在给直属下属分派任务
        if(!userService.isMyLower(user.getDepartmentId(), doTaskAddForm.getUserId())){
            return SimpleResponse.error(NOT_LOWER);
        }

        // 检查是否重复布置
        DoTask doTask = doTaskMapper.findByTaskIdAndUserId(doTaskAddForm.getTaskId(),
                doTaskAddForm.getUserId());
        if(doTask != null){
            return SimpleResponse.error(REPEAT_ADD);
        }

        // 添加执行任务
        doTask = new DoTask(doTaskAddForm);
        try{
            doTaskMapper.insert(doTask);
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(DATA_BASE.concat("Add"));
        }

        return SimpleResponse.ok(ADD_SUCCESS);
    }

    /**
     * 接受执行任务
     * @param doTaskReceiveForm
     * @return
     */
    @Override
    public SimpleResponse receive(DoTaskReceiveForm doTaskReceiveForm) {
        // 登录检查
        User user = UserUtil.getByToken(doTaskReceiveForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查是否被执行
        DoTask doTask = findById(doTaskReceiveForm.getDoTaskId());
        if(doTask == null){
            return SimpleResponse.error(NOT_FIND);
        }

        // 检查任务是否可以被接受
        if(!canReceive(doTask.getPersonTaskStatus())){
            return SimpleResponse.error(RECEIVE_STATUS_FAIL);
        }

        // 更新相应状态
        PersonTaskStatus personTaskStatus;
        if(doTaskReceiveForm.isReceive()){
            personTaskStatus = PersonTaskStatus.EXECUTING;
            boolean result = updateReceive(doTask.getDoTaskId(), doTaskReceiveForm.getReceiveMsg());
            if(!result){
                return SimpleResponse.error(RECEIVE_FAIL);
            }
        }else{
            personTaskStatus = PersonTaskStatus.REFUSED;
            boolean result = updateRefuse(doTask.getDoTaskId(), doTaskReceiveForm.getReceiveMsg());
            if(!result){
                return SimpleResponse.error(RECEIVE_FAIL);
            }
        }

        // 更新执行任务状态
        boolean result = false;

        result = updateStatus(doTaskReceiveForm.getDoTaskId(), personTaskStatus);
        if(!result){
            return SimpleResponse.error(RECEIVE_FAIL);
        }

        // 更新本身任务状态
        result = taskService.updateStatus(doTask.getTaskId(), TaskStatus.EXECUTING);
        if(!result){
            return SimpleResponse.error(RECEIVE_FAIL);
        }

        return SimpleResponse.ok(RECEIVE_SUCCESS);
    }

    /**
     * 任务执行者提交任务
     * @param doTaskCheckForm
     * @return
     */
    @Override
    public SimpleResponse check(DoTaskCheckForm doTaskCheckForm) {
        // 登录检查
        User user = UserUtil.getByToken(doTaskCheckForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查是否被执行
        DoTask doTask = findById(doTaskCheckForm.getDoTaskId());
        if(doTask == null){
            return SimpleResponse.error(NOT_FIND);
        }

        // 任务状态是否合法
        if(!canCheck(doTask.getPersonTaskStatus())){
            return SimpleResponse.error(CHECK_STATUS_FAIL);
        }

        // 用户是否有权限提交任务
        if(!doTask.getUserId().equals(user.getId())){
            return SimpleResponse.error(CHECK_NO_PRIVILEGE);
        }

        // 提交任务
        try{
            doTaskMapper.updateUpload(doTaskCheckForm.getDoTaskId(), doTaskCheckForm.getUpload());
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(DATA_BASE.concat("checkDo"));
        }

        // 更新执行任务状态
        boolean result = false;
        result = updateStatus(doTaskCheckForm.getDoTaskId(), PersonTaskStatus.CHECKING);
        if(!result){
            return SimpleResponse.error(CHECK_FAIL);
        }

        return SimpleResponse.ok(CHECK_SUCCESS);
    }

    /**
     * 更新任务状态
     * @param doTaskId
     * @param personTaskStatus
     * @return
     */
    @Override
    public boolean updateStatus(Integer doTaskId, PersonTaskStatus personTaskStatus){
        try{
            doTaskMapper.updateStatus(doTaskId, personTaskStatus);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 更新任务检查信息
     * @param doTaskId
     * @param personTaskStatus
     * @param checkMsg
     * @return
     */
    @Override
    public boolean updateCheck(Integer doTaskId, PersonTaskStatus personTaskStatus, String checkMsg){
        try{
            doTaskMapper.updateCheck(doTaskId, personTaskStatus, checkMsg, LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据doTaskId获取到执行任务
     * @param doTaskId
     * @return
     */
    @Override
    public DoTask findById(Integer doTaskId){
        DoTask doTask = null;
        try{
            doTask = doTaskMapper.findById(doTaskId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return doTask;
    }

    /**
     * 根据任务和用户ID找到执行任务
     * @param taskId
     * @param userId
     * @return
     */
    @Override
    public DoTask findByTaskAndUserId(Integer taskId, Integer userId){
        DoTask doTask = null;
        try{
            doTask = doTaskMapper.findByTaskIdAndUserId(taskId, userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return doTask;
    }

    /**
     * 查找当前任务全部的执行任务
     * @param taskId
     * @return
     */
    @Override
    public List<DoTask> findByTaskId(Integer taskId) {
        List<DoTask> doTasks = new ArrayList<>();
        try{
            doTasks = doTaskMapper.findByTaskId(taskId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return doTasks;
    }

    @Override
    public List<DoTask> findByUserId(Integer userId) {
        List<DoTask> doTasks = new ArrayList<>();
        try{
            doTasks = doTaskMapper.findByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return doTasks;
    }

    /**
     * 更新完成时间
     * @param doTaskId
     * @return
     */
    @Override
    public boolean updateFinish(Integer doTaskId){
        try{
            doTaskMapper.updateFinish(doTaskId, LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 更新接受信息和时间
     * @param doTaskId
     * @param receiveMsg
     * @return
     */
    private boolean updateReceive(Integer doTaskId, String receiveMsg){
        try{
            doTaskMapper.updateReceive(doTaskId, receiveMsg, LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 更新拒绝情况和时间
     * @param doTaskId
     * @param receiveMsg
     * @return
     */
    private boolean updateRefuse(Integer doTaskId, String receiveMsg){
        try{
            doTaskMapper.updateRefuse(doTaskId, receiveMsg, LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 检查当前是否可以添加执行者
     * @param taskStatus
     * @return
     */
    private boolean canAdd(TaskStatus taskStatus){
        return taskStatus.equals(TaskStatus.RELEASE) || taskStatus.equals(TaskStatus.EXECUTING);
    }

    /**
     *  检查当前是否可以接受任务
     * @param personTaskStatus
     * @return
     */
    private boolean canReceive(PersonTaskStatus personTaskStatus){
        return personTaskStatus.equals(PersonTaskStatus.NOT_RECEIVE);
    }

    /**
     * 检查当前是否可以提交任务
     * @param personTaskStatus
     * @return
     */
    private boolean canCheck(PersonTaskStatus personTaskStatus){
        return personTaskStatus.equals(PersonTaskStatus.EXECUTING) ||
                personTaskStatus.equals(PersonTaskStatus.REFUSE_CHECK);
    }

    private boolean hasPrivilege(Integer userId, Task task){
        AdministerTask administerTask = administerTaskService.findByTaskAndUserId(task.getTaskId(), userId);
        return administerTask != null || task.getCreateUserId().equals(userId);
    }
}
