package stormbroken.hackathon2020.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import stormbroken.hackathon2020.entity.User;
import stormbroken.hackathon2020.vo.ChangePasswordVO;
import stormbroken.hackathon2020.vo.UserPartVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

@Mapper
public interface UserMapper {

    @Insert("Insert into user(name,password,createTime,lastLoginTime,content,url,departmentId,organizationId, realName) " +
            "values(#{name},#{password},#{createTime},#{lastLoginTime},#{content},#{url},#{departmentId},#{organizationId}, #{realName})")
    boolean insert(User user);

    @Select("Select * from user where name= #{name}")
    User findByName(String name);

    @Select("Select * from account where name=#{name} and password=#{password}")
    User findByNameAndPassword(String username,String password);

    @Update("Update user set password=#{password} where name=#{name}")
    void updatePassword(ChangePasswordVO changePasswordVO);   //修改密码

    @Update("Update user set lastLoginTime = #{lastLoginTime} where id = #{id}")
    void updateLoginTime(Integer id, LocalDateTime lastLoginTime);

    @Select("Select * from user where departmentId = #{higherId}")
    List<User> findHigh(Integer higherId);

    @Select("Select * from user where departmentId = #{departmentId}")
    List<User> findByDepartmentId(Integer departmentId);

    @Select("Select * from user where id = #{userId} limit 1")
    User findById(Integer userId);
}