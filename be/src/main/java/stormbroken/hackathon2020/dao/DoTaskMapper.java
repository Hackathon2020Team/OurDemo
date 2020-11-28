package stormbroken.hackathon2020.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import stormbroken.hackathon2020.constant.PersonTaskStatus;
import stormbroken.hackathon2020.entity.DoTask;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Mapper
public interface DoTaskMapper {
    @Insert("Insert into dotask (taskId, userId, details, receiveMsg, checkMsg, upload, createTime, receiveTime, finishTime, personTaskStatus, checkTime) values " +
            "(#{taskId}, #{userId}, #{details}, #{receiveMsg}, #{checkMsg}, #{upload}, #{createTime}, #{receiveTime}, #{finishTime}, #{personTaskStatus}, #{checkTime})")
    boolean insert(DoTask doTask);

    @Select("Select * from dotask where userId = #{userId}")
    List<DoTask> findByUserId(Integer userId);

    @Update("Update dotask set finishTime = #{finishTime}, receiveMsg = #{receiveMsg} where doTaskId = #{doTaskId}")
    boolean updateRefuse(Integer doTaskId, String receiveMsg, LocalDateTime finishTime);

    @Update("Update dotask set finishTime = #{finishTime} where doTaskId = #{doTaskId}")
    boolean updateFinish(Integer doTaskId, LocalDateTime finishTime);

    @Update("Update dotask set personTaskStatus = #{personTaskStatus}, checkTime = #{checkTime}, checkMsg = #{checkMsg} where doTaskId = #{doTaskId}")
    boolean updateCheck(Integer doTaskId, PersonTaskStatus personTaskStatus, String checkMsg, LocalDateTime checkTime);

    @Update("Update dotask set receiveMsg = #{receiveMsg}, receiveTime = #{receiveTime} where doTaskId = #{doTaskId}")
    boolean updateReceive(Integer doTaskId,String receiveMsg, LocalDateTime receiveTime);

    @Update("Update dotask set upload = #{upload} where doTaskId = #{doTaskId}")
    boolean updateUpload(Integer doTaskId, String upload);

    @Update("Update dotask set personTaskStatus = #{personTaskStatus} where doTaskId = #{doTaskId}")
    boolean updateStatus(Integer doTaskId, PersonTaskStatus personTaskStatus);

    @Select("Select * from dotask where taskId = #{taskId} and userId = #{userId}")
    DoTask findByTaskIdAndUserId(Integer taskId, Integer userId);

    @Select("Select * from dotask where taskId = #{taskId}")
    List<DoTask> findByTaskId(Integer taskId);

    @Select("Select * from dotask where doTaskId = #{doTaskId} limit 1")
    DoTask findById(Integer doTaskId);
}
