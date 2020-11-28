package stormbroken.hackathon2020.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordVO {
    private String oldpassword;
    private String newpassword;
}
