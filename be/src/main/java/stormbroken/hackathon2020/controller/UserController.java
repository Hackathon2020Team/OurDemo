package stormbroken.hackathon2020.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stormbroken.hackathon2020.form.LoginForm;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.serviceImpl.UserServiceImpl;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/login")
    public SimpleResponse login(@RequestBody LoginForm loginForm){
        System.out.println("POST:/user/login:" + loginForm.toString());
        return userService.login(loginForm);
    }
}
