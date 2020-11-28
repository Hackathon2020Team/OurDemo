package stormbroken.hackathon2020.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.constant.PersonTaskStatus;
import stormbroken.hackathon2020.form.task.administerTask.AdministerAddForm;
import stormbroken.hackathon2020.utils.TimeUtil;

import java.time.LocalDateTime;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministerTask {
    private Integer administerTaskId;
    private Integer taskId;
    private Integer userId;
    private String details;
    private String receiveMsg;
    private String checkMsg;
    private LocalDateTime createTime;
    private LocalDateTime receiveTime;
    private LocalDateTime checkTime;
    private LocalDateTime finishTime;
    private PersonTaskStatus personTaskStatus;

    public AdministerTask(Task task){
        this.administerTaskId = -1;
        this.taskId = task.getTaskId();
        this.userId = task.getCreateUserId();
        this.details = task.getDetails();
        this.receiveMsg = null;
        this.checkMsg = null;
        this.createTime = task.getCreateTime();
        this.receiveTime = null;
        this.checkTime = null;
        this.finishTime = task.getDeadLine();
        this.personTaskStatus = PersonTaskStatus.EXECUTING;
    }

    public AdministerTask(AdministerAddForm administerAddForm){
        this.administerTaskId = -1;
        this.taskId = administerAddForm.getTaskId();
        this.userId = administerAddForm.getUserId();
        this.details = administerAddForm.getDetails();
        this.receiveMsg = null;
        this.checkMsg = null;
        this.createTime = LocalDateTime.now();
        this.receiveTime = null;
        this.checkTime = null;
        this.finishTime = TimeUtil.getLocalDateTime(administerAddForm.getFinishTime());
        this.personTaskStatus = PersonTaskStatus.NOT_RECEIVE;
    }
}
