package stormbroken.hackathon2020.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private int id;
    private String name;
    private String realName;
    private String token;
    private LocalDateTime lastLoginTime;
    private String content;
    private String url;
    private Integer departmentId;
    private Integer organizationId;

    private String departmentName;
    private String organizationName;
    private Integer level;

    public UserVO(UserPartVO userPartVO, Integer id, String token, LocalDateTime lastLoginTime, String name){
        this.id = id;
        this.name = name;
        this.realName = userPartVO.getRealName();
        this.token = token;
        this.lastLoginTime = lastLoginTime;
        this.content = userPartVO.getContent();
        this.url = userPartVO.getUrl();
        this.departmentId = userPartVO.getDepartmentId();
        this.organizationId = userPartVO.getOrganizationId();
        this.departmentName = userPartVO.getDepartmentName();
        this.organizationName = userPartVO.getOrganizationName();
        this.level = userPartVO.getLevel();
    }
}
