package stormbroken.hackathon2020.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

@Component
public class TimeUtil {
    public static LocalDateTime getLocalDateTime(long timestamp){
        LocalDateTime time =LocalDateTime.ofEpochSecond(timestamp/1000,0, ZoneOffset.ofHours(8));
        return time;
    }

    public static LocalDateTime getDefaultLocalDateTime(){
        LocalDateTime time = LocalDateTime.ofEpochSecond(946656000/1000,0,ZoneOffset.ofHours(8));
        return time;
    }

    public static LocalDateTime now(){
        LocalDateTime time =LocalDateTime.ofEpochSecond(System.currentTimeMillis()/1000,0, ZoneOffset.ofHours(8));
        return time;
    }

    //毫秒为单位
    public static long getTimeStamp(LocalDateTime localDateTime){
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static String dateToString(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateTime = localDateTime.format(formatter);
        return dateTime;
    }
}
