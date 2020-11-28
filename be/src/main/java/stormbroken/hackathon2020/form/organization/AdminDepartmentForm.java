package stormbroken.hackathon2020.form.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author stormbroken
 * Create by 2020/11/27
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDepartmentForm {
    private String departmentName;
    private String name;
    private String token;
}
