package stormbroken.hackathon2020.form.department;

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
public class DepartmentForm {
    private String token;

    private String name;
    private Integer organizationId;
    private String higherName;
}
