package stormbroken.hackathon2020.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.form.UserForm;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String realName;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
    private String content;
    private String url;
    private Integer departmentId;
    private Integer organizationId;

    public User(UserForm userForm){
        this.id = -1;
        this.name = userForm.getName();
        this.realName = userForm.getRealName();
        this.password = userForm.getPassword();
        this.createTime = LocalDateTime.now();
        this.lastLoginTime = LocalDateTime.now();
        this.content = userForm.getContent();
        this.url = userForm.getUrl();
        this.departmentId = userForm.getDepartmentId();
        this.organizationId = userForm.getOrganizationId();
    }

    @Override
    public boolean equals(Object obj) {
        User tmpUser = (User) obj;
        return this.id == tmpUser.id;
    }

    @Override
    public int hashCode() {
        return id;
    }


}
