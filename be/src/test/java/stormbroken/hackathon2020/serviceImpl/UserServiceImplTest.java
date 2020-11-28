package stormbroken.hackathon2020.serviceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import stormbroken.hackathon2020.constant.ResponseCode;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.entity.User;
import stormbroken.hackathon2020.form.LoginForm;
import stormbroken.hackathon2020.service.UserService;
import stormbroken.hackathon2020.utils.UserUtil;
import stormbroken.hackathon2020.vo.UserVO;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;

    private String token;

    @BeforeEach
    public void setUp() {
        LoginForm loginForm = new LoginForm("zhy", "123456");
        SimpleResponse res = userService.login(loginForm);
        Assert.assertEquals(ResponseCode.OK, res.getCode());
        UserVO userVO = (UserVO) res.getData();
        this.token = userVO.getToken();
    }

    @Test
    void higher() {
        User user = UserUtil.getByToken(this.token);
        Assert.assertNotNull(userService.higher(user.getDepartmentId()));
    }

    @Test
    void same() {
    }

    @Test
    void lower() {
    }
}