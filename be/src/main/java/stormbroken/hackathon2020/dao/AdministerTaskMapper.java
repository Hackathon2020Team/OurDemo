package stormbroken.hackathon2020.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import stormbroken.hackathon2020.constant.PersonTaskStatus;
import stormbroken.hackathon2020.entity.AdministerTask;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Mapper
public interface AdministerTaskMapper{
    @Insert("Insert into administertask (taskId, userId, details, receiveMsg, checkMsg, createTime, receiveTime, finishTime, personTaskStatus, checkTime) values " +
            "(#{taskId}, #{userId}, #{details}, #{receiveMsg}, #{checkMsg}, #{createTime}, #{receiveTime}, #{finishTime}, #{personTaskStatus}, #{checkTime})")
    boolean insert(AdministerTask administerTask);

    @Select("Select * from administertask where administerTaskId = #{administerTaskId}")
    AdministerTask findById(Integer administerTaskId);

    @Select("Select * from administertask where userId = #{userId}")
    List<AdministerTask> findByUserId(Integer userId);

    @Select("Select * from administertask where taskId = #{taskId}")
    List<AdministerTask> findByTaskId(Integer taskId);

    @Update("Update administertask set finishTime = #{finishTime} where administerTaskId = #{administerTaskId}")
    boolean updateFinish(Integer administerTaskId, LocalDateTime finishTime);

    @Update("Update administertask set checkTime = #{checkTime}, checkMsg = #{checkMsg} where administerTaskId = #{administerTaskId}")
    boolean updateCheck(Integer administerTaskId, String checkMsg, LocalDateTime checkTime);

    @Update("Update administertask set receiveMsg = #{receiveMsg}, receiveTime = #{receiveTime} where administerTaskId = #{administerTaskId}")
    boolean updateReceive(Integer administerTaskId, String receiveMsg, LocalDateTime receiveTime);

    @Update("Update administertask set receiveMsg = #{receiveMsg}, finishTime = #{finishTime} where administerTaskId = #{administerTaskId}")
    boolean updateRefuse(Integer administerTaskId, String receiveMsg, LocalDateTime finishTime);

    @Update("Update administertask set personTaskStatus = #{personTaskStatus} where administerTaskId = #{administerTaskId}")
    boolean updateStatus(Integer administerTaskId, PersonTaskStatus personTaskStatus);

    @Select("Select * from administertask where taskId = #{taskId} and userId = #{userId}")
    AdministerTask findByTaskAndUserId(Integer taskId, Integer userId);
}
