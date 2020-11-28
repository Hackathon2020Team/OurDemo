package stormbroken.hackathon2020.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.constant.TaskStatus;
import stormbroken.hackathon2020.form.task.TaskForm;
import stormbroken.hackathon2020.utils.TimeUtil;

import java.sql.Time;
import java.time.LocalDateTime;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Integer taskId;
    private Integer superTaskId;
    private Integer organizationId;
    private Integer createUserId;
    private String name;
    private String details;
    private LocalDateTime createTime;
    private LocalDateTime deadLine;
    private LocalDateTime endTime;
    private TaskStatus status;

    public Task(TaskForm taskForm, Integer organizationId, Integer createUserId){
        this.taskId = -1;
        this.superTaskId = taskForm.getSuperTaskId();
        this.organizationId = organizationId;
        this.createUserId = createUserId;
        this.name = taskForm.getName();
        this.details = taskForm.getDetails();
        this.createTime = LocalDateTime.now();
        this.deadLine = TimeUtil.getLocalDateTime(taskForm.getDeadLine());
        this.endTime = null;
        this.status = TaskStatus.RELEASE;
    }
}
