package stormbroken.hackathon2020.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.constant.TaskStatus;
import stormbroken.hackathon2020.form.statuslog.StatusLogForm;

import java.time.LocalDateTime;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusLog {
    private Integer logId;
    private Integer taskId;
    private Integer userId;
    private TaskStatus preStatus;
    private TaskStatus afterStatus;
    private LocalDateTime changeTime;

    public StatusLog(StatusLogForm statusLogForm){
        this.logId = -1;
        this.taskId = statusLogForm.getTaskId();
        this.userId = statusLogForm.getUserId();
        this.preStatus = statusLogForm.getPreStatus();
        this.afterStatus = statusLogForm.getAfterStatus();
        this.changeTime = LocalDateTime.now();
    }
}
