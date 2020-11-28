package stormbroken.hackathon2020.service;

import stormbroken.hackathon2020.constant.PersonTaskStatus;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.entity.DoTask;
import stormbroken.hackathon2020.form.task.doTask.DoTaskAddForm;
import stormbroken.hackathon2020.form.task.doTask.DoTaskCheckForm;
import stormbroken.hackathon2020.form.task.doTask.DoTaskReceiveForm;

import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

public interface DoTaskService {
    SimpleResponse add(DoTaskAddForm doTaskAddForm);

    SimpleResponse receive(DoTaskReceiveForm doTaskReceiveForm);

    SimpleResponse check(DoTaskCheckForm doTaskCheckForm);

    boolean updateCheck(Integer doTaskId, PersonTaskStatus personTaskStatus, String checkMsg);

    DoTask findByTaskAndUserId(Integer taskId, Integer userId);

    DoTask findById(Integer doTaskId);

    List<DoTask> findByTaskId(Integer taskId);

    List<DoTask> findByUserId(Integer userId);

    boolean updateStatus(Integer doTaskId, PersonTaskStatus personTaskStatus);

    boolean updateFinish(Integer doTaskId);
}
