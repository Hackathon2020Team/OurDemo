package stormbroken.hackathon2020.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stormbroken.hackathon2020.constant.PersonTaskStatus;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.constant.TaskStatus;
import stormbroken.hackathon2020.dao.TaskMapper;
import stormbroken.hackathon2020.entity.*;
import stormbroken.hackathon2020.form.task.TaskCancelForm;
import stormbroken.hackathon2020.form.task.TaskFinishForm;
import stormbroken.hackathon2020.form.task.TaskForm;
import stormbroken.hackathon2020.service.*;
import stormbroken.hackathon2020.utils.UserUtil;
import stormbroken.hackathon2020.vo.TaskVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Service
public class TaskServiceImpl implements TaskService {
    private static String NOT_LOGIN = "用户没有登录";
    private static String DATA_BASE = "Database Exception at Task:";

    private static String ADD_SUCCESS = "创建任务成功";
    private static String NOT_DO = "不是上级任务的执行人";
    private static String ADD_ADMIN_FAIL = "添加管理任务失败";

    private static String FIND_FAIL = "根据管理员查找失败";
    private static String NO_HIGHER = "没有所属任务";

    private static String CANCEL_FAIL = "取消任务失败";
    private static String CANCEL_SUCCESS = "取消任务成功";
    private static String CANCEL_STATUS_FAIL = "任务状态不可变更为取消";

    private static String FINISH_FAIL = "设置任务完成失败";
    private static String FINISH_SUCCESS = "设置任务完成成功";
    private static String FINISH_UNABLE = "还有子任务未完成";
    private static String FINISH_STATUS_FAIL = "任务状态不可变更为完成";

    private static String MY_FAIL = "拉取自己发布和负责的任务失败";
    private static String MY_SUCCESS = "拉取自己发布和负责的任务成功";

    private static String NO_PRIVILEGE = "没有权限操作";
    private static String NOT_FIND = "没有找到对应任务";

    @Autowired
    TaskMapper taskMapper;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    AdministerTaskService administerTaskService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    DoTaskService doTaskService;
    @Autowired
    UserService userService;

    /**
     * 添加任务
     * @param taskForm
     * @return
     */
    @Override
    public SimpleResponse add(TaskForm taskForm) {
        // 检查用户
        User user = UserUtil.getByToken(taskForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查是否有所属组织
        Integer orgId = user.getOrganizationId();

        // 检查是否有上级任务
        if(taskForm.getSuperTaskId() >= 1){
            Task superTask = findById(taskForm.getSuperTaskId());
            if(superTask == null){
                return SimpleResponse.error(NO_HIGHER);
            }

            // 检查是否为上级任务执行人
            DoTask doTask = doTaskService.findByTaskAndUserId(
                    taskForm.getSuperTaskId(), user.getId());
            if(doTask == null){
                return SimpleResponse.error(NOT_DO);
            }
            if(!doTask.getPersonTaskStatus().equals(PersonTaskStatus.EXECUTING)){
                return SimpleResponse.error(NOT_DO);
            }
        }

        // 添加任务
        Task task = new Task(taskForm, orgId, user.getId());
        try{
            taskMapper.insert(task);
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(DATA_BASE.concat("Add"));
        }

        // 找到刚添加的任务的ID
        task = findTaskRecently(user.getId());

        // 添加管理，并且状态为执行
        AdministerTask administerTask = new AdministerTask(task);
        boolean result = administerTaskService.addAdminister(administerTask);
        if(!result){
            return SimpleResponse.error(ADD_ADMIN_FAIL);
        }

        return SimpleResponse.ok(ADD_SUCCESS);
    }

    /**
     * 取消任务
     * @param taskCancelForm
     * @return
     */
    @Override
    public SimpleResponse cancel(TaskCancelForm taskCancelForm) {
        // 检查用户
        User user = UserUtil.getByToken(taskCancelForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查用户是否有权限取消此任务（发布人）
        Task task = findById(taskCancelForm.getTaskId());
        if(task == null){
            return SimpleResponse.error(NOT_FIND);
        }
        if(!task.getCreateUserId().equals(user.getId())){
            return SimpleResponse.error(NO_PRIVILEGE);
        }

        // 检查任务状态是否允许被取消
        if(!canCancel(task.getStatus())){
            return SimpleResponse.error(CANCEL_STATUS_FAIL);
        }

        // 递归取消所有子任务(包含直接和非直接任务) 并 刷新终止时间
        cancelSubTask(taskCancelForm.getTaskId());
        updateEndTime(taskCancelForm.getTaskId());

        return SimpleResponse.ok(CANCEL_SUCCESS);
    }

    /**
     * 确认任务完成
     * @param taskFinishForm
     * @return
     */
    @Override
    public SimpleResponse finish(TaskFinishForm taskFinishForm) {
        // 检查用户
        User user = UserUtil.getByToken(taskFinishForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查用户是否有权限结束本任务（发布人）
        Task task = findById(taskFinishForm.getTaskId());
        if(task == null){
            return SimpleResponse.error(NOT_FIND);
        }
        if(!task.getCreateUserId().equals(user.getId())){
            return SimpleResponse.error(NO_PRIVILEGE);
        }

        // 检查任务是否可以完成
        if(!canFinish(task.getStatus())){
            return SimpleResponse.error(FINISH_STATUS_FAIL);
        }

        // 检查该任务所有子任务是否已经为终止态
        if(!canSubTaskFinished(taskFinishForm.getTaskId())){
            return SimpleResponse.error(FINISH_UNABLE);
        }

        // 结束任务
        updateStatus(taskFinishForm.getTaskId(), TaskStatus.FINISHED);
        // 更新任务结束时间
        updateEndTime(taskFinishForm.getTaskId());
        // 结束所有管理任务
        List<AdministerTask> administerTasks = administerTaskService.findByTaskId(task.getTaskId());
        for(AdministerTask administerTask: administerTasks){
            if(administerTask.getPersonTaskStatus().equals(PersonTaskStatus.REFUSE_CHECK) ||
                    administerTask.getPersonTaskStatus().equals(PersonTaskStatus.CHECKING) ||
                    administerTask.getPersonTaskStatus().equals(PersonTaskStatus.EXECUTING)){
                administerTaskService.updateStatus(administerTask.getAdministerTaskId(), PersonTaskStatus.FINISHED);
            }
        }

        return SimpleResponse.ok(FINISH_SUCCESS);
    }

    /**
     * 根据状态获取我的所有发布或负责任务
     * @param token
     * @param status
     * @return
     */
    @Override
    public SimpleResponse my(String token, String status) {
        // 检查用户
        User user = UserUtil.getByToken(token);
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 查找所有我发布的任务
        List<Task> tasks = new ArrayList<>();
        try{
            tasks = taskMapper.findByUserId(user.getId());
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(MY_FAIL);
        }

        // 查找所有我负责的任务
        List<AdministerTask> administerTasks = administerTaskService.findByUserId(user.getId());
        for(AdministerTask administerTask: administerTasks){
            Task tempTask = findById(administerTask.getTaskId());
            if(tempTask.getCreateUserId().equals(user.getId())){
                continue;
            }
            if(tempTask != null){
                tasks.add(tempTask);
            }
        }

        // 根据状态过滤
        if(status != null){
            tasks = tasks.stream().filter((Task task) ->
                    (status.equals(task.getStatus().name()))).collect(Collectors.toList());
        }

        return SimpleResponse.ok(getTaskVOs(tasks));
    }

    /**
     * 获取当前所有的执行任务
     * @param token
     * @param status
     * @return
     */
    @Override
    public SimpleResponse myDo(String token, String status) {
        // 检查用户
        User user = UserUtil.getByToken(token);
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        List<Task> tasks = new ArrayList<>();

        // 查找正在执行的任务
        List<DoTask> doTasks = doTaskService.findByUserId(user.getId());
        for(DoTask doTask: doTasks){
            Task tmpTask = findById(doTask.getTaskId());
            if(tmpTask != null){
                tasks.add(tmpTask);
            }
        }

        // 根据状态过滤
        if(status != null){
            tasks = tasks.stream().filter((Task task) ->
                    (status.equals(task.getStatus().name()))).collect(Collectors.toList());
        }

        return SimpleResponse.ok(getTaskVOs(tasks));
    }

    /**
     * 当前任务的直接子任务
     * @param token
     * @param taskId
     * @return
     */
    @Override
    public SimpleResponse subtask(String token, Integer taskId) {
        // 检查用户
        User user = UserUtil.getByToken(token);
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查有无当前任务
        Task task = findById(taskId);
        if(task == null){
            return SimpleResponse.error(NOT_FIND);
        }

        // 检查用户有无权限查看当前任务的直接子任务
        if(!canViewSubTask(task, user.getId())){
            return SimpleResponse.error(NO_PRIVILEGE);
        }

        // 查找子任务
        List<Task> tasks = getSubTasks(taskId);
        return SimpleResponse.ok(getTaskVOs(tasks));
    }

    /**
     * 根据任务ID查找任务
     * @param taskId
     * @return
     */
    @Override
    public Task findById(Integer taskId){
        Task task = null;
        try{
            task = taskMapper.findById(taskId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return task;
    }

    /**
     * 更新任务状态
     * @param taskId
     * @param taskStatus
     * @return
     */
    @Override
    public boolean updateStatus(Integer taskId, TaskStatus taskStatus) {
        try{
            taskMapper.updateStatus(taskId, taskStatus);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 递归取消所有子任务
     * @param taskId
     */
    private void cancelSubTask(Integer taskId){
        cancelTask(taskId);
        List<Task> subTasks = getSubTasks(taskId);
        for(Task subTask: subTasks){
            cancelSubTask(subTask.getTaskId());
        }
    }

    /**
     * 取消当前任务的全部内容
     * @param taskId
     */
    private void cancelTask(Integer taskId){
        updateStatus(taskId, TaskStatus.CANCELED);
        updateEndTime(taskId);
        List<DoTask> doTasks = doTaskService.findByTaskId(taskId);
        for(DoTask doTask : doTasks){
            if(!doTask.getPersonTaskStatus().equals(PersonTaskStatus.FINISHED)){
                doTaskService.updateStatus(doTask.getDoTaskId(), PersonTaskStatus.CANCELED);
            }
        }
        List<AdministerTask> administerTasks = administerTaskService.findByTaskId(taskId);
        for(AdministerTask administerTask: administerTasks){
            if(!administerTask.getPersonTaskStatus().equals(PersonTaskStatus.FINISHED)){
                administerTaskService.updateStatus(administerTask.getTaskId(), PersonTaskStatus.CANCELED);
            }
        }
    }

    /**
     * 更新任务终结状态时间
     * @param taskId
     * @return
     */
    private boolean updateEndTime(Integer taskId){
        try{
            taskMapper.updateEndTime(taskId, LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 检查是否可以查看当前任务，即检查任务等级是否属于本用户
     * @param task
     * @param userId
     * @return
     */
    private boolean canViewSubTask(Task task, Integer userId){
        do{
            if(canViewThisTask(task, userId)){
                return true;
            }
            task = findById(task.getSuperTaskId());
            if(task == null){
                break;
            }
        }while(task.getSuperTaskId() >= 1);
        return false;
    }

    /**
     * 检查是否可以查看本级任务
     * @param task
     * @param userId
     * @return
     */
    private boolean canViewThisTask(Task task, Integer userId){
        // 创建者可以查看
        if(userId.equals(task.getCreateUserId())){
            return true;
        }
        List<AdministerTask> administerTasks = administerTaskService.findByTaskId(task.getTaskId());
        for(AdministerTask administerTask: administerTasks){
            if(administerTask.getUserId().equals(userId)){
                return true;
            }
        }
        return false;
    }

    /**
     * 检查任务是否可以结束
     * @param taskStatus
     * @return
     */
    private boolean canFinish(TaskStatus taskStatus){
        return taskStatus.equals(TaskStatus.EXECUTING);
    }

    /**
     * 检查任务是否可以取消
     * @param taskStatus
     * @return
     */
    private boolean canCancel(TaskStatus taskStatus){
        return taskStatus.equals(TaskStatus.EXECUTING) || taskStatus.equals(TaskStatus.RELEASE);
    }

    /**
     * 检查其所有子任务是否均终结态
     * @param taskId
     * @return
     */
    private boolean canSubTaskFinished(Integer taskId){
        List<Task> subTasks = getSubTasks(taskId);

        // 检查当前任务的所有执行任务是否结束
        List<DoTask> doTasks = doTaskService.findByTaskId(taskId);
        for(DoTask doTask: doTasks){
            if(!isDoTaskFinished(doTask.getPersonTaskStatus())){
                return false;
            }
        }

        // 检查当前任务的所有子任务是否结束
        for(Task subTask: subTasks){
            boolean result = true;
            if(!subTask.getStatus().equals(TaskStatus.FINISHED) &&
                    !subTask.getStatus().equals(TaskStatus.CANCELED)){
                return false;
            }
            result = canSubTaskFinished(subTask.getTaskId());
            if(!result){
                return result;
            }
        }
        return true;
    }

    private boolean isDoTaskFinished(PersonTaskStatus personTaskStatus){
        return personTaskStatus.equals(PersonTaskStatus.FINISHED) ||
                personTaskStatus.equals(PersonTaskStatus.REFUSED) ||
                personTaskStatus.equals(PersonTaskStatus.CANCELED);
    }

    /**
     * 获取全部子任务
     * @param subTaskId
     * @return
     */
    private List<Task> getSubTasks(Integer subTaskId){
        List<Task> tasks = null;
        try{
            tasks = taskMapper.findBySuperTaskId(subTaskId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * 列表转换为输出格式
     * @param tasks
     * @return
     */
    private List<TaskVO> getTaskVOs(List<Task> tasks){
        List<TaskVO> taskVOS = new ArrayList<>();
        for(Task task: tasks){
            TaskVO taskVO = getTaskVO(task);
            taskVOS.add(taskVO);
        }
        return taskVOS;
    }

    /**
     * 转换为输出格式
     * @param task
     * @return
     */
    private TaskVO getTaskVO(Task task){
        User user = userService.findById(task.getCreateUserId());
        Department department = departmentService.findById(user.getDepartmentId());
        List<DoTask> doTasks = doTaskService.findByTaskId(task.getTaskId());
        List<AdministerTask> administerTasks = administerTaskService.findByTaskId(task.getTaskId());
        return new TaskVO(task, user, department, doTasks, administerTasks);
    }

    /**
     * 找到当前用户创建的指定时间的任务
     * @param userId
     * @return
     */
    private Task findTaskRecently(Integer userId){
        Task task = null;
        try{
            task = taskMapper.findByUserIdRecently(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return task;
    }
}
