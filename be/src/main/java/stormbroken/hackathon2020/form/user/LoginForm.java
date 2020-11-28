package stormbroken.hackathon2020.form;

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
public class LoginForm {
    private String name;
    private String password;
}
