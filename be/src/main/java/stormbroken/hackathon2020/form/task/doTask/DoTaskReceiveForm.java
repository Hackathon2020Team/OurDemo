package stormbroken.hackathon2020.form.task.doTask;

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
public class DoTaskReceiveForm {
    private String token;
    private Integer doTaskId;
    private String receiveMsg;
    private boolean isReceive;
}
