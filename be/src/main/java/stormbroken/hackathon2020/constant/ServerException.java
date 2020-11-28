package stormbroken.hackathon2020.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerException extends RuntimeException{
    //The exception of Server
    private int code;
    private String msg;

    public String getMessage(){
        return this.msg;
    }

}

