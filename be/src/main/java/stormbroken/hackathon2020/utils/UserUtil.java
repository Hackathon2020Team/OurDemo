package stormbroken.hackathon2020.utils;

import org.springframework.stereotype.Component;
import stormbroken.hackathon2020.entity.User;
import stormbroken.hackathon2020.MyException.ServiceException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

@Component
public class UserUtil {
    private static Map<String, User> userMap = Collections.synchronizedMap(new HashMap<>());
    private static Map<User, String> reverseMap = Collections.synchronizedMap(new HashMap<>());

    public static User getByToken(String token){
        for(String string: userMap.keySet()){
            if(string.equals(token)){
                return userMap.get(string);
            }
        }
        return null;
    }

    public static Integer getByTokenId(String token){
        for(String string: userMap.keySet()){
            if(string.equals(token)){
                return userMap.get(string).getId();
            }
        }
        return -1;
    }

    public static void login(String token, User user){
        if(reverseMap.containsKey(user)){
            String old_token = reverseMap.get(user);
            userMap.remove(old_token);
        }
        userMap.put(token,user);
        reverseMap.put(user, token);
    }

    public static boolean logOut(String token){
        for(String string: userMap.keySet()){
            if(string.equals(token)){
                System.out.println(token +":用户登出成功");
                userMap.remove(token);
                return true;
            }
        }
        return false;
    }
}
