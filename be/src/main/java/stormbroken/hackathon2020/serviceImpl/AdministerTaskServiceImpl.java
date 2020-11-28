package stormbroken.hackathon2020.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stormbroken.hackathon2020.constant.PersonTaskStatus;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.constant.TaskStatus;
import stormbroken.hackathon2020.dao.AdministerTaskMapper;
import stormbroken.hackathon2020.entity.AdministerTask;
import stormbroken.hackathon2020.entity.DoTask;
import stormbroken.hackathon2020.entity.Task;
import stormbroken.hackathon2020.entity.User;
import stormbroken.hackathon2020.form.task.administerTask.AdministerAddForm;
import stormbroken.hackathon2020.form.task.administerTask.DoTaskCheckForm;
import stormbroken.hackathon2020.form.task.administerTask.AdministerTaskReceiveForm;
import stormbroken.hackathon2020.service.AdministerTaskService;
import stormbroken.hackathon2020.service.DoTaskService;
import stormbroken.hackathon2020.service.TaskService;
import stormbroken.hackathon2020.service.UserService;
import stormbroken.hackathon2020.utils.UserUtil;

import javax.xml.crypto.dsig.SignatureMethod;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Service
public class AdministerTaskServiceImpl implements AdministerTaskService {
    private static String NOT_LOGIN = "用户没有登录";
    private static String DATA_BASE = "Database Exception at AdministerTask:";

    private static String ADD_SUCCESS = "添加管理任务成功";
    private static String ADD_FAIL = "添加管理任务失败";
    private static String NOT_FIND = "没有找到这个任务";
    private static String NO_PRIVILEGE = "没有权限进行操作";
    private static String ADD_STATUS_FAIL = "任务状态无法添加";
    private static String NOT_LOWER = "不是给直属下级分配任务";
    private static String ADD_REPEAT = "不能重复添加管理任务";

    private static String RECEIVE_SUCCESS = "处理待接受任务成功";
    private static String RECEIVE_FAIL = "处理待接受任务失败";
    private static String RECEIVE_STATUS_FAIL = "任务状态不允许被接受";
    private static String RECEIVE_NO_PRIVILEGE = "没有权限接受本任务";

    private static String DOTASK_NOT_FIND = "没有找到对应执行任务";

    private static String CHECK_FAIL = "检查任务失败";
    private static String CHECK_SUCCESS = "检查任务成功";

    @Autowired
    AdministerTaskMapper administerTaskMapper;
    @Autowired
    DoTaskService doTaskService;
    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;

    @Override
    public boolean addAdminister(AdministerTask administerTask){
        try{
            administerTaskMapper.insert(administerTask);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 添加管理任务
     * @param administerAddForm
     * @return
     */
    @Override
    public SimpleResponse add(AdministerAddForm administerAddForm) {
        // 登录检查
        User user = UserUtil.getByToken(administerAddForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查目标任务是否存在
        Task task = taskService.findById(administerAddForm.getTaskId());
        if(task == null){
            return SimpleResponse.error(NOT_FIND);
        }

        // 检查是否是管理者
        if(!task.getCreateUserId().equals(user.getId())){
            return SimpleResponse.error(NO_PRIVILEGE);
        }

        // 检查当前任务状态是否正确
        if(!canAddAdmin(task.getStatus())){
            return SimpleResponse.error(ADD_STATUS_FAIL);
        }

        // 检查是否是在给直属下属或平级任务
        if(!userService.isMySameAndLower(user.getDepartmentId(), administerAddForm.getUserId())){
            return SimpleResponse.error(NOT_LOWER);
        }

        // 检查是否已经添加过
        AdministerTask administerTask = findByTaskAndUserId(administerAddForm.getTaskId(),
                administerAddForm.getUserId());
        if(administerTask != null){
            return SimpleResponse.error(ADD_REPEAT);
        }

        boolean result = false;
        administerTask = new AdministerTask(administerAddForm);
        result = addAdminister(administerTask);

        if(!result){
            return SimpleResponse.error(ADD_FAIL);
        }

        return SimpleResponse.ok(ADD_SUCCESS);
    }

    /**
     * 接受管理任务申请
     * @param administerTaskReceiveForm
     * @return
     */
    @Override
    public SimpleResponse receive(AdministerTaskReceiveForm administerTaskReceiveForm) {
        // 登录检查
        User user = UserUtil.getByToken(administerTaskReceiveForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查是否有对应任务
        AdministerTask administerTask = findById(administerTaskReceiveForm.getAdministerTaskId());
        if(administerTask == null){
            return SimpleResponse.error(NOT_FIND);
        }

        // 检查任务状态是否可以被接受
        if(!canReceive(administerTask.getPersonTaskStatus())){
            return SimpleResponse.error(RECEIVE_STATUS_FAIL);
        }

        // 检查用户有无权限接受任务
        if(!administerTask.getUserId().equals(user.getId())){
            return SimpleResponse.error(RECEIVE_NO_PRIVILEGE);
        }

        // 更新任务内容和时间
        PersonTaskStatus personTaskStatus;
        if(administerTaskReceiveForm.isReceive()){
            personTaskStatus = PersonTaskStatus.EXECUTING;
            boolean result = updateReceive(administerTask.getAdministerTaskId(), administerTaskReceiveForm.getReceiveMsg());
            if(!result){
                return SimpleResponse.error(RECEIVE_FAIL);
            }
        }else{
            personTaskStatus = PersonTaskStatus.REFUSED;
            boolean result = updateRefuse(administerTask.getAdministerTaskId(), administerTaskReceiveForm.getReceiveMsg());
            if(!result){
                return SimpleResponse.error(RECEIVE_FAIL);
            }
        }

        // 更新任务状态
        boolean result = false;
        result = updateStatus(administerTaskReceiveForm.getAdministerTaskId(), personTaskStatus);
        if(!result){
            return SimpleResponse.error(RECEIVE_FAIL);
        }

        return SimpleResponse.ok(RECEIVE_SUCCESS);
    }

    /**
     * 审核执行任务申请
     * @param doTaskCheckForm
     * @return
     */
    @Override
    public SimpleResponse checkDo(DoTaskCheckForm doTaskCheckForm) {
        // 登录检查
        User user = UserUtil.getByToken(doTaskCheckForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 找到执行的任务
        DoTask doTask = doTaskService.findById(doTaskCheckForm.getDoTaskId());
        if(doTask == null){
            return SimpleResponse.error(DOTASK_NOT_FIND);
        }

        // 找到管理的任务
        AdministerTask administerTask = findByTaskAndUserId(doTask.getTaskId(), user.getId());
        if(administerTask == null){
            return SimpleResponse.error(NOT_FIND);
        }

        // 检查任务状态是否可以被检查
        if(!canCheck(doTask.getPersonTaskStatus(), administerTask.getPersonTaskStatus())){
            return SimpleResponse.error(RECEIVE_STATUS_FAIL);
        }

        PersonTaskStatus personTaskStatus;

        boolean result = false;
        // 更新管理任务状态
        if(doTaskCheckForm.isOk()){
            personTaskStatus = PersonTaskStatus.FINISHED;
            // 更新完成任务
            result = updateFinish(administerTask.getTaskId());
            if(!result){
                return SimpleResponse.error(CHECK_FAIL);
            }
        }else{
            personTaskStatus = PersonTaskStatus.REFUSE_CHECK;
        }

        // 更新执行任务状态
        result = updateDoCheck(doTaskCheckForm.getDoTaskId(),
                doTaskCheckForm.getCheckMsg(), personTaskStatus);
        if(!result){
            return SimpleResponse.error(CHECK_FAIL);
        }

        return SimpleResponse.ok(CHECK_SUCCESS);
    }

    /**
     * 获取我管理的所有任务
     * @param userId
     * @return
     */
    @Override
    public List<AdministerTask> findByUserId(Integer userId) {
        List<AdministerTask> administerTasks = new ArrayList<>();
        try{
            administerTasks = administerTaskMapper.findByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return administerTasks;
    }

    /**
     * 获取任务的所有管理任务
     * @param taskId
     * @return
     */
    @Override
    public List<AdministerTask> findByTaskId(Integer taskId) {
        List<AdministerTask> administerTasks = new ArrayList<>();
        try{
            administerTasks = administerTaskMapper.findByTaskId(taskId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return administerTasks;
    }

    /**
     * 根据任务和用户查看负责关系
     * @param taskId
     * @param userId
     * @return
     */
    @Override
    public AdministerTask findByTaskAndUserId(Integer taskId, Integer userId){
        AdministerTask administerTask = null;
        try{
            administerTask = administerTaskMapper.findByTaskAndUserId(taskId, userId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return administerTask;
    }

    /**
     * 更新管理任务状态
     * @param adminTaskId
     * @param personTaskStatus
     * @return
     */
    @Override
    public boolean updateStatus(Integer adminTaskId, PersonTaskStatus personTaskStatus){
        try{
            administerTaskMapper.updateStatus(adminTaskId, personTaskStatus);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 是否可以添加管理任务
     * @param taskStatus
     * @return
     */
    private boolean canAddAdmin(TaskStatus taskStatus){
        return taskStatus.equals(TaskStatus.EXECUTING) || taskStatus.equals(TaskStatus.RELEASE);
    }

    /**
     * 是否可以接受管理任务
     * @param personTaskStatus
     * @return
     */
    private boolean canReceive(PersonTaskStatus personTaskStatus){
        return personTaskStatus.equals(PersonTaskStatus.NOT_RECEIVE);
    }

    /**
     * 是否可以审核提交申请
     * @param doTaskStatus
     * @param adminTaskStatus
     * @return
     */
    private boolean canCheck(PersonTaskStatus doTaskStatus,
                             PersonTaskStatus adminTaskStatus){
        return (doTaskStatus.equals(PersonTaskStatus.CHECKING) ||
                doTaskStatus.equals(PersonTaskStatus.REFUSE_CHECK)) &&
                (adminTaskStatus.equals(PersonTaskStatus.EXECUTING) ||
                adminTaskStatus.equals(PersonTaskStatus.REFUSE_CHECK));
    }

    /**
     * 更新接收状态
     * @param adminTaskId
     * @param receiveMsg
     * @return
     */
    private boolean updateReceive(Integer adminTaskId, String receiveMsg){
        try{
            administerTaskMapper.updateReceive(adminTaskId,
                    receiveMsg, LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 更新拒绝状态
     * @param adminTaskId
     * @param receiveMsg
     * @return
     */
    private boolean updateRefuse(Integer adminTaskId, String receiveMsg){
        try{
            administerTaskMapper.updateRefuse(adminTaskId,
                    receiveMsg, LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 更新检查状态
     * @param doTaskId
     * @param checkMsg
     * @param personTaskStatus
     * @return
     */
    private boolean updateDoCheck(Integer doTaskId, String checkMsg, PersonTaskStatus personTaskStatus){
        try{
            doTaskService.updateCheck(doTaskId, personTaskStatus, checkMsg);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 更新完成状态
     * @param doTaskId
     * @return
     */
    private boolean updateFinish(Integer doTaskId){
        try{
            administerTaskMapper.updateFinish(doTaskId, LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据管理任务的ID找管理任务
     * @param administerTaskId
     * @return
     */
    private AdministerTask findById(Integer administerTaskId){
        AdministerTask administerTask = null;
        try{
            administerTask = administerTaskMapper.findById(administerTaskId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return administerTask;
    }
}
