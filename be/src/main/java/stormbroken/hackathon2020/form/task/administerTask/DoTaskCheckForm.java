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
public class DoTaskCheckForm {
    private String token;
    private Integer doTaskId;
    private boolean isOk;
    private String checkMsg;
}
