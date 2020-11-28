package stormbroken.hackathon2020.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.form.organization.OrganizationForm;

import java.time.LocalDateTime;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    private Integer organizationId;
    private String name;
    private Integer administerId;
    private LocalDateTime createTime;

    public Organization(OrganizationForm organizationForm, Integer userId){
        this.organizationId = -1;
        this.name = organizationForm.getName();
        this.administerId = userId;
        this.createTime = LocalDateTime.now();
    }
}
