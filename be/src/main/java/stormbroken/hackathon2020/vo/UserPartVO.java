package stormbroken.hackathon2020.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author stormbroken
 * Create by 2020/11/25
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPartVO {
    private Integer userId;
    private String realName;
    private String content;
    private String url;
    private Integer departmentId;
    private Integer organizationId;

    private String departmentName;
    private String organizationName;
    private Integer level;
}
