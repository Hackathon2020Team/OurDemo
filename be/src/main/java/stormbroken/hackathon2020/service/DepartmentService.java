package stormbroken.hackathon2020.service;

import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.entity.Department;
import stormbroken.hackathon2020.entity.User;
import stormbroken.hackathon2020.form.department.DeleteDepartmentForm;
import stormbroken.hackathon2020.form.department.DepartmentForm;
import stormbroken.hackathon2020.form.department.RenameDepartmentForm;

import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

public interface DepartmentService {
    boolean addOne(DepartmentForm departmentForm, Integer level, Integer higherId);

    SimpleResponse add(DepartmentForm departmentForm);

    Department findByOrgAndName(Integer organizationId, String name);

    Department findHighest(Integer organizationId);

    Department findById(Integer departmentId);

    SimpleResponse getAll(String token, Integer organizationId);

    List<Department> findSameLevel(Integer organizationId, Integer level);

    List<Department> findLowerDepartment(Integer organizationId, Integer departmentId);

    SimpleResponse rename(RenameDepartmentForm renameDepartmentForm);

    SimpleResponse delete(DeleteDepartmentForm deleteDepartmentForm);

    boolean hasHighestPrivilege(User user);
}
