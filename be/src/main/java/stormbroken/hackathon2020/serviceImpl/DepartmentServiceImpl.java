package stormbroken.hackathon2020.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.dao.DepartmentMapper;
import stormbroken.hackathon2020.entity.Department;
import stormbroken.hackathon2020.entity.User;
import stormbroken.hackathon2020.form.department.DeleteDepartmentForm;
import stormbroken.hackathon2020.form.department.DepartmentForm;
import stormbroken.hackathon2020.form.department.RenameDepartmentForm;
import stormbroken.hackathon2020.service.DepartmentService;
import stormbroken.hackathon2020.service.UserService;
import stormbroken.hackathon2020.utils.UserUtil;
import stormbroken.hackathon2020.vo.FollowVO;

import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static String NOT_LOGIN = "用户没有登录";
    private static String DATA_BASE = "Database Exception at Department:";

    private static String REPEAT_NAME = "本公司内不允许有重名职位";

    private static String NOT_FIND = "没有找到对应职位";
    private static String NO_HIGHER = "没有这个上级职位";
    private static String NO_PRIVILEGE = "没有权限进行操作";

    private static String ADD_SUCCESS = "新建职位成功";
    private static String ADD_FAIL = "新建职位失败";

    private static String RENAME_FAIL = "重命名职位失败";
    private static String RENAME_SUCCESS = "重命名职位成功";

    private static String DELETE_FAIl = "删除职位失败";
    private static String DELETE_SUCCESS = "删除职位成功";
    private static String DELETE_UNABLE = "对应职位及其下属还有员工，无法删除职位";

    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    UserService userService;

    /**
     * 新建一个职位
     * @param departmentForm
     * @param level
     * @param higherId
     * @return
     */
    @Override
    public boolean addOne(DepartmentForm departmentForm, Integer level, Integer higherId){
        Department department = new Department(departmentForm, level, higherId);
        try{
            departmentMapper.insert(department);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 检查是否是本组织最高级别的管理者
     * @param user
     * @return
     */
    @Override
    public boolean hasHighestPrivilege(User user){
        Department department = findHighest(user.getOrganizationId());
        return department.getDepartmentId().equals(user.getDepartmentId());
    }

    /**
     * 添加职位对外接口
     * @param departmentForm
     * @return
     */
    @Override
    public SimpleResponse add(DepartmentForm departmentForm) {
        User user = UserUtil.getByToken(departmentForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查是否有管理者权限添加职位
        if(!hasHighestPrivilege(user)){
            return SimpleResponse.error(NO_PRIVILEGE);
        }

        // 检查对应直属上级职位是否存在
        Department higherDepartment = findByOrgAndName(departmentForm.getOrganizationId(), departmentForm.getHigherName());
        if(higherDepartment == null){
            return SimpleResponse.error(NO_HIGHER);
        }

        // 检查本组织内是否有同名职位
        Department thisDepartment = findByOrgAndName(departmentForm.getOrganizationId(), departmentForm.getName());
        if(thisDepartment != null){
            return SimpleResponse.error(REPEAT_NAME);
        }

        // 添加职位
        if(!addOne(departmentForm, higherDepartment.getLevel() + 1, higherDepartment.getDepartmentId())){
            return SimpleResponse.error(ADD_FAIL);
        }

        return SimpleResponse.ok(ADD_SUCCESS);
    }

    /**
     * 获取本公司的全部职位
     * @param token
     * @param organizationId
     * @return
     */
    @Override
    public SimpleResponse getAll(String token, Integer organizationId) {
        User user = UserUtil.getByToken(token);
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查是有有权限查看职位
        if(!user.getOrganizationId().equals(organizationId)){
            return SimpleResponse.error(NO_PRIVILEGE);
        }

        // 获取所有的职位
        List<Department> departments = null;
        try{
            departments = departmentMapper.findByOrg(organizationId);
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(DATA_BASE.concat("GetAll"));
        }

        return SimpleResponse.ok(departments);
    }

    /**
     * 重命名职位
     * @param renameDepartmentForm
     * @return
     */
    @Override
    public SimpleResponse rename(RenameDepartmentForm renameDepartmentForm) {
        User user = UserUtil.getByToken(renameDepartmentForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查是否有管理者权限添加职位
        if(!hasHighestPrivilege(user)){
            return SimpleResponse.error(NO_PRIVILEGE);
        }

        // 检查是否有对应的职位
        Department department = findByOrgAndName(user.getOrganizationId(), renameDepartmentForm.getName());
        if(department == null){
            return SimpleResponse.error(NOT_FIND);
        }

        try{
            departmentMapper.updateName(renameDepartmentForm.getDepartmentId(), renameDepartmentForm.getName(),
                    renameDepartmentForm.getNewname());
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(RENAME_FAIL);
        }

        return SimpleResponse.ok(RENAME_SUCCESS);
    }

    /**
     * 删除职位
     * @param deleteDepartmentForm
     * @return
     */
    @Override
    public SimpleResponse delete(DeleteDepartmentForm deleteDepartmentForm) {
        User user = UserUtil.getByToken(deleteDepartmentForm.getToken());
        if(user == null){
            return SimpleResponse.error(NOT_LOGIN);
        }

        // 检查是否有管理者权限添加职位
        if(!hasHighestPrivilege(user)){
            return SimpleResponse.error(NO_PRIVILEGE);
        }

        // 检查是否有对应的职位
        Department department = findByOrgAndName(user.getOrganizationId(), deleteDepartmentForm.getName());
        if(department == null){
            return SimpleResponse.error(NOT_FIND);
        }

        // 检查对应职位上是否有职员
        List<FollowVO> users = userService.lowerAll(user.getId());
        if(users != null && users.size() != 0){
            return SimpleResponse.error(DELETE_UNABLE);
        }

        try{
            departmentMapper.delete(deleteDepartmentForm.getDepartmentId());
        }catch (Exception e){
            e.printStackTrace();
            return SimpleResponse.exception(DELETE_FAIl);
        }

        return SimpleResponse.ok(DELETE_SUCCESS);
    }

    /**
     * 根据部门ID查找部门
     * @param departmentId
     * @return
     */
    @Override
    public Department findById(Integer departmentId) {
        Department department = null;
        try{
            department = departmentMapper.findById(departmentId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return department;
    }

    /**
     * 找到公司最高级别职位
     * @param organizationId
     * @return
     */
    @Override
    public Department findHighest(Integer organizationId){
        Department department = null;
        try{
            department = departmentMapper.findHighest(organizationId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return department;
    }

    /**
     * 根据公司 + 职位名找到职位信息
     * @param organizationId
     * @param name
     * @return
     */
    @Override
    public Department findByOrgAndName(Integer organizationId, String name) {
        Department department = null;
        try{
            department = departmentMapper.findByOrgAndName(organizationId, name);
        }catch (Exception e){
            e.printStackTrace();
        }
        return department;
    }

    /**
     * 找到公司同级别所有的职位
     * @param organizationId
     * @param level
     * @return
     */
    @Override
    public List<Department> findSameLevel(Integer organizationId, Integer level) {
        List<Department> departments = null;
        try{
            departments = departmentMapper.findSameLevel(organizationId, level);
        }catch (Exception e){
            e.printStackTrace();
        }
        return departments;
    }

    /**
     * 找到公司直属下级的所有职位
     * @param organizationId
     * @param departmentId
     * @return
     */
    @Override
    public List<Department> findLowerDepartment(Integer organizationId, Integer departmentId) {
        List<Department> departments = null;
        try{
            departments = departmentMapper.findLowerDepartment(organizationId, departmentId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return departments;
    }

}
