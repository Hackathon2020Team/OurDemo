package stormbroken.hackathon2020.form.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeForm {
    private String name;
    private String realName;
    private String password;
    private String content;
    private String url;
    private Integer departmentId;

    private String token;
}
