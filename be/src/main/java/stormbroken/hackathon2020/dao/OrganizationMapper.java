package stormbroken.hackathon2020.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import stormbroken.hackathon2020.entity.Organization;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Mapper
public interface OrganizationMapper {
    @Insert("Insert into organization (name, administerId, createTime) " +
            "values (#{name}, #{administerId}, #{createTime})")
    boolean insert(Organization organization);

    @Select("Select * from organization where name = #{name} limit 1")
    Organization findByName(String name);

    @Select("Select organizationId from organization " +
            "where administerId = #{administerId} limit 1")
    Integer findByAdministerId(Integer administerId);

    @Update("Update organization set administerId = #{administerId} " +
            "where name = #{name}")
    void updateAdminister(String name, Integer administerId);

    @Select("Select * from organization " +
            "where organizationId = #{organizationId}")
    Organization findById(Integer organizationId);
}
