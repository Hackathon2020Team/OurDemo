package stormbroken.hackathon2020.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import stormbroken.hackathon2020.constant.TaskStatus;
import stormbroken.hackathon2020.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Mapper
public interface TaskMapper {
    @Insert("Insert into task (superTaskId, organizationId, createUserId, name, details, status, createTime, " +
            "deadLine, endTime) values (#{superTaskId}, #{organizationId}, #{createUserId}, #{name}, #{details}, #{status}" +
            ", #{createTime}, #{deadLine}, #{endTime})")
    boolean insert(Task task);

    @Select("Select * from task where createUserId = #{userId}")
    List<Task> findByUserId(Integer userId);

    @Select("Select * from task where superTaskId = #{superTaskId}")
    List<Task> findBySuperTaskId(Integer superTaskId);

    @Select("Select * from task where taskId = #{taskId} limit 1")
    Task findById(Integer taskId);

    @Update("Update task set status = #{status} where taskId = #{taskId}")
    boolean updateStatus(Integer taskId, TaskStatus status);

    @Update("Update task set endTime = #{endTime} where taskId = #{taskId}")
    boolean updateEndTime(Integer taskId, LocalDateTime endTime);

    @Select("Select * from task where createUserId = #{createUserId} order by createTime desc limit 1")
    Task findByUserIdRecently(Integer createUserId);
}
