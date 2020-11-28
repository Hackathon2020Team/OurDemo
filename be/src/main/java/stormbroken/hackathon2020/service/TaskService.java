package stormbroken.hackathon2020.service;

import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.constant.TaskStatus;
import stormbroken.hackathon2020.entity.Task;
import stormbroken.hackathon2020.form.task.TaskCancelForm;
import stormbroken.hackathon2020.form.task.TaskFinishForm;
import stormbroken.hackathon2020.form.task.TaskForm;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

public interface TaskService {
    SimpleResponse add(TaskForm taskForm);

    SimpleResponse cancel(TaskCancelForm taskCancelForm);

    SimpleResponse finish(TaskFinishForm taskFinishForm);

    SimpleResponse my(String token, String status);

    SimpleResponse subtask(String token, Integer taskId);

    Task findById(Integer taskId);

    boolean updateStatus(Integer taskId, TaskStatus taskStatus);

    SimpleResponse myDo(String token, String status);
}
