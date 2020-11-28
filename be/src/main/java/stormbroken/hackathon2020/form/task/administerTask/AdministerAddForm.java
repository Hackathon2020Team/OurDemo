package stormbroken.hackathon2020.form.task.administerTask;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author stormbroken
 * Create by 2020/11/26
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministerAddForm {
    private String token;

    private Integer taskId;
    private Integer userId;
    private String details;
    private Long finishTime;
}
