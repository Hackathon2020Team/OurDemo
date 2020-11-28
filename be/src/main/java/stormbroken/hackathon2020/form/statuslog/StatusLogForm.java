package stormbroken.hackathon2020.form.statuslog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.constant.TaskStatus;

/**
 * @Author stormbroken
 * Create by 2020/11/26
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusLogForm {
    private Integer taskId;
    private Integer userId;
    private TaskStatus preStatus;
    private TaskStatus afterStatus;
}
