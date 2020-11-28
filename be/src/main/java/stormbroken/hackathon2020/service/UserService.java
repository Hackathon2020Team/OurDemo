package stormbroken.hackathon2020.service;

import stormbroken.hackathon2020.entity.User;
import stormbroken.hackathon2020.form.LoginForm;
import stormbroken.hackathon2020.form.UserForm;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.vo.FollowVO;
import stormbroken.hackathon2020.vo.UserPartVO;

import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

public interface UserService {

    SimpleResponse add(UserForm userForm);

    SimpleResponse login(LoginForm loginForm);

    User findById(Integer userId);

    User findByName(String name);

    List<UserPartVO> higher(Integer departmentId);

    List<UserPartVO> same(Integer departmentId);

    List<UserPartVO> lower(Integer departmentId);

    List<FollowVO> lowerAll(Integer userId);

    boolean isMyLower(Integer departmentId, Integer userId);

    boolean isMySameAndLower(Integer departmentId, Integer userId);
}
