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
public class AdministerForm {
    private String username;
    private String realName;
    private String password;
    private String content;
    private String url;
}
