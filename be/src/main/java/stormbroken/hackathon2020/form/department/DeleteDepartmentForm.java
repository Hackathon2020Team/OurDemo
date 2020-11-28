package stormbroken.hackathon2020.form.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.constant.SimpleResponse;

/**
 * @Author stormbroken
 * Create by 2020/11/25
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDepartmentForm {
    private String token;
    private Integer departmentId;
    private String name;
}
