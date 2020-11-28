package stormbroken.hackathon2020.form.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskForm {
    private Integer superTaskId;
    private String name;
    private String details;
    private Long deadLine;

    private String token;
}
