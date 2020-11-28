package stormbroken.hackathon2020.service;

import stormbroken.hackathon2020.constant.PersonTaskStatus;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.entity.AdministerTask;
import stormbroken.hackathon2020.form.task.administerTask.AdministerAddForm;
import stormbroken.hackathon2020.form.task.administerTask.DoTaskCheckForm;
import stormbroken.hackathon2020.form.task.administerTask.AdministerTaskReceiveForm;

import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

public interface AdministerTaskService {
    boolean addAdminister(AdministerTask administerTask);

    SimpleResponse add(AdministerAddForm administerAddForm);

    List<AdministerTask> findByUserId(Integer userId);

    List<AdministerTask> findByTaskId(Integer taskId);

    SimpleResponse receive(AdministerTaskReceiveForm administerTaskReceiveForm);

    SimpleResponse checkDo(DoTaskCheckForm doTaskCheckForm);

    AdministerTask findByTaskAndUserId(Integer taskId, Integer userId);

    boolean updateStatus(Integer adminTaskId, PersonTaskStatus personTaskStatus);
}
