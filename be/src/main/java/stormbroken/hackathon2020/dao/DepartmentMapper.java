package stormbroken.hackathon2020.dao;

import org.apache.ibatis.annotations.*;
import stormbroken.hackathon2020.entity.Department;

import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Mapper
public interface DepartmentMapper {
    @Insert("Insert into department (organizationId, name, level, higherId) " +
            "values (#{organizationId}, #{name}, #{level}, #{higherId})")
    boolean insert(Department department);

    @Select("Select * from department where organizationId = #{organizationId}")
    List<Department> findByOrg(Integer organizationId);

    @Select("Select * from department where organizationId = #{organizationId} and name = #{name} limit 1")
    Department findByOrgAndName(Integer organizationId, String name);

    @Select("Select * from department where organizationId = #{organizationId} and level = 1")
    Department findHighest(Integer organizationId);

    @Select("Select * from department where departmentId = #{departmentId}")
    Department findById(Integer departmentId);

    @Select("Select * from department where organizationId = #{organizationId} and level = #{level}")
    List<Department> findSameLevel(Integer organizationId, Integer level);

    @Select("Select * from department where organizationId = #{organizationId} and higherId = #{higherId}")
    List<Department> findLowerDepartment(Integer organizationId, Integer higherId);

    @Update("Update department set name = #{newname} where departmentId = #{departmentId} and name = #{name}")
    void updateName(Integer departmentId, String name, String newname);

    @Delete("Delete from department where departmentId = #{departmentId}")
    void delete(Integer departmentId);
}
