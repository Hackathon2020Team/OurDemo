package stormbroken.hackathon2020.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stormbroken.hackathon2020.entity.Department;
import stormbroken.hackathon2020.entity.Organization;
import stormbroken.hackathon2020.entity.User;
import stormbroken.hackathon2020.form.LoginForm;
import stormbroken.hackathon2020.form.UserForm;
import stormbroken.hackathon2020.service.DepartmentService;
import stormbroken.hackathon2020.service.OrganizationService;
import stormbroken.hackathon2020.vo.FollowVO;
import stormbroken.hackathon2020.vo.UserVO;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.dao.UserMapper;
import stormbroken.hackathon2020.service.UserService;
import stormbroken.hackathon2020.utils.MD5Encryption;
import stormbroken.hackathon2020.utils.UserUtil;
import stormbroken.hackathon2020.vo.UserPartVO;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

@Service
public class UserServiceImpl implements UserService {
    private static String REGISTER_SUCCESS = "用户注册成功";
    private static String LOGIN_SUCCESS = "用户登录成功";
    private static String EMPTY = "用户名和密码不得为空";

    private static String REPEAT_NAME = "用户名被占用";
    private static String CODE_ERROR = "加密失败";
    private static String DATABASE_ERROR_PREFIX = "在UserService发生数据库错误:";
    private static String NOT_FIND = "没有找到这个用户";
    private static String PASSWORD_ERROR = "用户名或密码错误";
    private static String UPDATE_TIME_FAIL = "更新登录时间错误";
    private static String NAME_FORMAT_ERROR = "用户名应该为2个字符及以上";
    private static String PASSWORD_FORMAT_ERROR = "密码应该是6-20位的数字或字母";

    @Autowired
    UserMapper userMapper;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    OrganizationService organizationService;

    /**
     * 添加用户
     * @param userForm
     * @return
     */
    @Override
    public SimpleResponse add(UserForm userForm) {
        // 检查用户名格式
        if(userForm.getName().length() < 2){
            return SimpleResponse.error(NAME_FORMAT_ERROR);
        }

        // 检查真实姓名格式
        if(userForm.getRealName().length() < 2){
            return SimpleResponse.error(NAME_FORMAT_ERROR);
        }

        // 检查用户密码格式
        if(!isPasswordCorrect(userForm.getPassword())){
            return SimpleResponse.error(PASSWORD_FORMAT_ERROR);
        }

        // 检查重名
        if (userMapper.findByName(userForm.getName()) != null) {
            return SimpleResponse.error(REPEAT_NAME);
        }

        // 密码进行MD5编码
        try{
            userForm.setPassword(MD5Encryption.encrypt(userForm.getPassword()));
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.error(CODE_ERROR);
        }

        // 新建用户
        User newUser = new User(userForm);
        try{
            userMapper.insert(newUser);
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(DATABASE_ERROR_PREFIX.concat("add"));
        }
        return SimpleResponse.ok(REGISTER_SUCCESS);
    }

    /**
     * 用户登录
     * @param loginForm
     * @return
     */
    @Override
    public SimpleResponse login(LoginForm loginForm) {
        // 检查有无本用户
        User tmpUser = findByName(loginForm.getName());
        if(tmpUser == null){
            return SimpleResponse.error(NOT_FIND);
        }

        // 编码用户输入的MD5密码
        String password = loginForm.getPassword();
        try{
            password = MD5Encryption.encrypt(password);
        }catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
            return SimpleResponse.error(CODE_ERROR);
        }

        // 检查用户密码是否匹配
        if(!password.equals(tmpUser.getPassword())){
            return SimpleResponse.error(PASSWORD_ERROR);
        }

        // 记录上一次登录时间
        LocalDateTime lastLoginTime = tmpUser.getLastLoginTime();

        // 刷新本次登录时间
        try{
            userMapper.updateLoginTime(tmpUser.getId(), LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(DATABASE_ERROR_PREFIX.concat(UPDATE_TIME_FAIL));
        }

        // 生成Token
        String token = UUID.randomUUID().toString().replace("-","");
        UserUtil.login(token, tmpUser);

        // 转换User
        UserPartVO userPartVO = getUserPartVO(tmpUser);
        UserVO userVO = new UserVO(userPartVO, tmpUser.getId(), token, lastLoginTime, tmpUser.getName());
        return SimpleResponse.ok(userVO);
    }

    /**
     * 根据用户名称查找用户
     * @param name
     * @return
     */
    @Override
    public User findByName(String name) {
        User user = null;
        try{
            user = userMapper.findByName(name);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return user;
    }

    /**
     * 根据用户ID查找用户
     * @param userId
     * @return
     */
    @Override
    public User findById(Integer userId)  {
        User user = null;
        try{
            user = userMapper.findById(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 查找所有的直属上级
     * @param departmentId
     * @return
     */
    @Override
    public List<UserPartVO> higher(Integer departmentId){
        Department department = departmentService.findById(departmentId);

        // 最高级职位直接返回
        if(department.getHigherId() < 1){
            return new ArrayList<>();
        }

        // 查找直属上级
        List<User> users = null;
        try{
            users = userMapper.findHigh(department.getHigherId());
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }

        return trans2Part(users);
    }

    /**
     * 查找所有的同级
     * @param departmentId
     * @return
     */
    @Override
    public List<UserPartVO> same(Integer departmentId){
        // 找到当前职位
        Department department = departmentService.findById(departmentId);
        // 找到所有同级的职位
        List<Department> sameDepartments = departmentService.findSameLevel(
                department.getOrganizationId(), department.getLevel());
        // 合并所有同级别的职务
        List<User> users = new ArrayList<>();
        for(Department sameDepartment: sameDepartments){
            List<User> tmpUser = getUserByDepartment(sameDepartment.getDepartmentId());
            if(tmpUser != null){
                users.addAll(tmpUser);
            }
        }
        return trans2Part(users);
    }

    /**
     * 查找所有的直属下级
     * @param departmentId
     * @return
     */
    @Override
    public List<UserPartVO> lower(Integer departmentId){
        // 找到当前职位
        Department department = departmentService.findById(departmentId);
        // 找到所有直属下级
        List<Department> lowerDepartments = departmentService.findLowerDepartment(department.getOrganizationId(),
                departmentId);
        // 合并所有直属下级的职务
        List<User> users = new ArrayList<>();
        for(Department lowerDepartment: lowerDepartments){
            List<User> tmpUser = getUserByDepartment(lowerDepartment.getDepartmentId());
            if(tmpUser != null){
                users.addAll(tmpUser);
            }
        }
        return trans2Part(users);
    }

    /**
     * 查找所有的下级
     * @param userId
     * @return
     */
    @Override
    public List<FollowVO> lowerAll(Integer userId){
        User user = findById(userId);
        List<UserPartVO> lowerUserPartVOS = lower(user.getDepartmentId());
        if(lowerUserPartVOS == null){
            return null;
        }

        List<FollowVO> followVOS = new ArrayList<>();

        Map<Integer, List<UserPartVO>> id2Departments = new HashMap<>();
        for(UserPartVO userPartVO: lowerUserPartVOS){
            if(!id2Departments.containsKey(userPartVO.getDepartmentId())){
                id2Departments.put(userPartVO.getDepartmentId(), lower(userPartVO.getDepartmentId()));
            }
        }

        for(UserPartVO userPartVO: lowerUserPartVOS){
            List<FollowVO> tmpLowerAll = lowerAll(userPartVO.getUserId());
            FollowVO followVO = new FollowVO(userPartVO, tmpLowerAll);
            followVOS.add(followVO);
        }

        return followVOS;
    }

    /**
     * 根据部门查找所有用户
     * @param departmentId
     * @return
     */
    private List<User> getUserByDepartment(Integer departmentId){
        List<User> users = new ArrayList<>();
        try{
            users = userMapper.findByDepartmentId(departmentId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 将User转换为部分输出的VO
     * @param user
     * @return
     */
    private UserPartVO getUserPartVO(User user){
        Department department = departmentService.findById(user.getDepartmentId());
        Organization organization = organizationService.findById(user.getOrganizationId());

        return new UserPartVO(user.getId(),user.getRealName(), user.getContent()
                , user.getUrl(), user.getDepartmentId(),user.getOrganizationId(),
                department.getName(), organization.getName(), department.getLevel());
    }

    /**
     * 将User链表转换为UserPartVO
     * @param users
     * @return
     */
    private List<UserPartVO> trans2Part(List<User> users){
        List<UserPartVO> userPartVOS = new ArrayList<>();
        for(User user: users){
            UserPartVO userPartVO = getUserPartVO(user);
            userPartVOS.add(userPartVO);
        }
        return userPartVOS;
    }

    /**
     * 检查用户的密码格式是否正确
     * @param password
     * @return
     */
    private boolean isPasswordCorrect(String password){
        boolean lengthResult = password.length() >= 6 && password.length() <= 20;
        return lengthResult && password.matches("^[a-z0-9A-Z]+$");
    }

    /**
     * 返回是不是我的直属下级
     * @param departmentId
     * @param userId
     * @return
     */
    @Override
    public boolean isMyLower(Integer departmentId, Integer userId){
        List<UserPartVO> users = lower(departmentId);
        for(UserPartVO userPartVO: users){
            if(userPartVO.getUserId().equals(userId)){
                return true;
            }
        }
        return false;
    }

    /**
     * 返回是不是我的平级或直属下级
     * @param departmentId
     * @param userId
     * @return
     */
    @Override
    public boolean isMySameAndLower(Integer departmentId, Integer userId) {
        // TODO 不可以给直属下级添加
        List<UserPartVO> users = same(departmentId);
        for(UserPartVO userPartVO: users){
            if(userPartVO.getUserId().equals(userId)){
                return true;
            }
        }
        return isMyLower(departmentId, userId);
    }
}
