package stormbroken.hackathon2020.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.form.organization.EmployeeForm;
import stormbroken.hackathon2020.form.organization.OrganizationForm;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    private String name;
    private String realName;
    private String password;
    private String content;
    private String url;
    private Integer departmentId;
    private Integer organizationId;


    public UserForm(EmployeeForm employeeForm, Integer organizationId){
        this.name = employeeForm.getName();
        this.realName = employeeForm.getRealName();
        this.password = employeeForm.getPassword();
        this.content = employeeForm.getContent();
        this.url = employeeForm.getUrl();
        this.departmentId = employeeForm.getDepartmentId();
        this.organizationId = organizationId;
    }

    public UserForm(OrganizationForm organizationForm, Integer departmentId, Integer organizationId){
        this.name = organizationForm.getUsername();
        this.realName = organizationForm.getRealName();
        this.password = organizationForm.getPassword();
        this.content = organizationForm.getContent();
        this.url = organizationForm.getUrl();
        this.departmentId = departmentId;
        this.organizationId = organizationId;
    }
}
