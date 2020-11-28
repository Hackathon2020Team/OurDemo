package stormbroken.hackathon2020.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.form.department.DepartmentForm;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private Integer departmentId;
    private Integer organizationId;
    private String name;
    private Integer level;
    private Integer higherId;

    public Department(DepartmentForm departmentForm, Integer level, Integer higherId){
        this.departmentId = -1;
        this.organizationId = departmentForm.getOrganizationId();
        this.name = departmentForm.getName();
        this.level = level;
        this.higherId = higherId;
    }
}
