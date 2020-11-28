package stormbroken.hackathon2020.form.task.administerTask;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author stormbroken
 * Create by 2020/11/26
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministerTaskReceiveForm {
    private String token;
    private Integer administerTaskId;
    private boolean isReceive;
    private String receiveMsg;
}
