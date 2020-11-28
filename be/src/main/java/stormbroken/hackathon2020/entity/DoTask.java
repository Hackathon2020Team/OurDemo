package stormbroken.hackathon2020.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.constant.PersonTaskStatus;
import stormbroken.hackathon2020.form.task.doTask.DoTaskAddForm;
import stormbroken.hackathon2020.utils.TimeUtil;

import java.time.LocalDateTime;
import java.util.spi.LocaleServiceProvider;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoTask {
    private Integer doTaskId;
    private Integer taskId;
    private Integer userId;
    private String details;
    private String upload;
    private String receiveMsg;
    private String checkMsg;
    private LocalDateTime createTime;
    private LocalDateTime receiveTime;
    private LocalDateTime checkTime;
    private LocalDateTime finishTime;
    private PersonTaskStatus personTaskStatus;

    public DoTask(DoTaskAddForm doTaskAddForm){
        this.doTaskId = -1;
        this.taskId = doTaskAddForm.getTaskId();
        this.userId = doTaskAddForm.getUserId();
        this.details = doTaskAddForm.getDetails();
        this.upload = null;
        this.receiveMsg = null;
        this.checkMsg = null;
        this.createTime = LocalDateTime.now();
        this.checkTime = null;
        this.receiveTime = null;
        this.finishTime = TimeUtil.getLocalDateTime(doTaskAddForm.getFinishTime());
        this.personTaskStatus = PersonTaskStatus.NOT_RECEIVE;
    }
}
