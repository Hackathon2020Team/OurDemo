package stormbroken.hackathon2020.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stormbroken.hackathon2020.constant.ResponseCode;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.dao.OrganizationMapper;
import stormbroken.hackathon2020.entity.Department;
import stormbroken.hackathon2020.entity.Organization;
import stormbroken.hackathon2020.entity.User;
import stormbroken.hackathon2020.form.UserForm;
import stormbroken.hackathon2020.form.department.DepartmentForm;
import stormbroken.hackathon2020.form.organization.*;
import stormbroken.hackathon2020.service.DepartmentService;
import stormbroken.hackathon2020.service.OrganizationService;
import stormbroken.hackathon2020.service.UserService;
import stormbroken.hackathon2020.utils.UserUtil;
import stormbroken.hackathon2020.vo.FollowVO;
import stormbroken.hackathon2020.vo.UserPartVO;

import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private static String NOT_LOGIN = "用户没有登录";
    private static String DATA_BASE = "Database Exception at Organization:";

    private static String ADD_SUCCESS = "添加组织、管理员、职位成功";
    private static String DEPARTMENT_FAIL = "职位创建失败";
    private static String REPEAT_NAME = "组织名已经被注册";
    private static String NO_PRIVILEGE = "没有权限操作";
    private static String NO_DEPARTMENT = "没有对应职位";

    @Autowired
    OrganizationMapper organizationMapper;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserService userService;

    /**
     * 添加组织、管理员、最高职位
     * @param organizationForm
     * @return
     */
    @Override
    public SimpleResponse add(OrganizationForm organizationForm) {
        Organization organization = null;

        /**
         * 新建公司
         */
        try{
            organization = organizationMapper.findByName(organizationForm.getName());
            if(organization != null){
                return SimpleResponse.error(REPEAT_NAME);
            }
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(DATA_BASE.concat("add Find By Name"));
        }

        // 管理员之后更新
        organization = new Organization(organizationForm, -1);
        try{
            organizationMapper.insert(organization);
        }catch (Exception e){
            return SimpleResponse.exception(DATA_BASE.concat("Add"));
        }

        /**
         * 新建职务
         */
        Integer organizationId = -1;

        try{
            organization = organizationMapper.findByName(organizationForm.getName());
            organizationId = organization.getOrganizationId();
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(DATA_BASE.concat("add Find By Name"));
        }

        DepartmentForm departmentForm = new DepartmentForm();
        departmentForm.setName(organizationForm.getDepartmentName());
        departmentForm.setOrganizationId(organizationId);

        departmentForm.setHigherName("");

        boolean result = departmentService.addOne(departmentForm,1 , -1);
        if(!result){
            return SimpleResponse.error(DEPARTMENT_FAIL);
        }

        /**
         * 新建管理员
         */
        Integer departmentId = departmentService.findByOrgAndName(organizationId, organizationForm.getDepartmentName())
                .getDepartmentId();
        UserForm userForm = new UserForm(organizationForm, departmentId, organizationId);
        SimpleResponse response = userService.add(userForm);
        if(response.getCode() != ResponseCode.OK){
            return response;
        }

        Integer userId = userService.findByName(organizationForm.getUsername()).getId();

        try{
            organizationMapper.updateAdminister(organization.getName(), userId);
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(DATA_BASE.concat("Update Admin"));
        }

        return SimpleResponse.ok(ADD_SUCCESS);
    }

    /**
     * 添加员工
     * @param employeeForm
     * @return
     */
    @Override
    public SimpleResponse addEmployee(EmployeeForm employeeForm) {
        // 检查是否登录
        User user = UserUtil.getByToken(employeeForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查是否有对应职位
        Integer departmentId = employeeForm.getDepartmentId();
        Department department = departmentService.findById(departmentId);
        if (department == null) {
            return SimpleResponse.error(NO_DEPARTMENT);
        }

        // 检查是否有权限添加对应职位的员工
        if(!department.getOrganizationId().equals(user.getOrganizationId())){
            return SimpleResponse.error(NO_PRIVILEGE);
        }

        UserForm userForm = new UserForm(employeeForm, user.getOrganizationId());
        return userService.add(userForm);
    }

    /**
     * 根据管理员ID查找组织ID
     * @param administerId
     * @return
     */
    @Override
    public Integer findByAdministerId(Integer administerId) {
        Integer orgId = null;
        try{
            orgId = organizationMapper.findByAdministerId(administerId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return orgId;
    }

    /**
     * 根据组织ID，查找组织
     * @param organizationId
     * @return
     */
    @Override
    public Organization findById(Integer organizationId) {
        Organization organization = null;
        try{
            organization = organizationMapper.findById(organizationId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return organization;
    }

    /**
     * 查找直属上级
     * @param token
     * @return
     */
    @Override
    public SimpleResponse higher(String token) {
        User user = UserUtil.getByToken(token);
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }
        List<UserPartVO> userPartVOList = userService.higher(user.getDepartmentId());
        return SimpleResponse.ok(userPartVOList);
    }

    /**
     * 查找平级
     * @param token
     * @return
     */
    @Override
    public SimpleResponse equal(String token) {
        User user = UserUtil.getByToken(token);
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }
        List<UserPartVO> userPartVOList = userService.same(user.getDepartmentId());
        return SimpleResponse.ok(userPartVOList);
    }

    /**
     * 查找所有下级
     * @param token
     * @return
     */
    @Override
    public SimpleResponse allLower(String token) {
        User user = UserUtil.getByToken(token);
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }
        List<FollowVO> userPartVOList = userService.lowerAll(user.getId());
        return SimpleResponse.ok(userPartVOList);
    }

    /**
     * 查找直属下级
     * @param token
     * @return
     */
    @Override
    public SimpleResponse lower(String token) {
        User user = UserUtil.getByToken(token);
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }
        List<UserPartVO> userPartVOList = userService.lower(user.getDepartmentId());
        return SimpleResponse.ok(userPartVOList);
    }

    @Override
    public SimpleResponse sameLevel(String token) {
        User user = UserUtil.getByToken(token);
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }
        List<UserPartVO> userPartVOList = userService.same(user.getDepartmentId());
        return SimpleResponse.ok(userPartVOList);
    }

    /**
     * TODO 移动员工 任务如何处理
     * @param removeOrgForm
     * @return
     */
    @Override
    public SimpleResponse remove(RemoveOrgForm removeOrgForm) {
        return null;
    }

    /**
     * TODO 删除员工 危险 任务如何处理
     * @param deleteOrgForm
     * @return
     */
    @Override
    public SimpleResponse delete(DeleteOrgForm deleteOrgForm) {
        return null;
    }
}
