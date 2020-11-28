package stormbroken.hackathon2020.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import stormbroken.hackathon2020.entity.StatusLog;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Mapper
public interface StatusLogMapper {
    @Insert("Insert into statuslog (taskId, userId, preStatus, afterStatus, changeTime) values " +
            "(#{taskId}, #{userId}, #{preStatus}, #{afterStatus}, #{changeTime})")
    boolean insert(StatusLog statusLog);
}
