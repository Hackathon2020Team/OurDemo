package stormbroken.hackathon2020.constant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.function.Supplier;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

public class SimpleResponse {
    private int code;
    private Object data;
    private transient String exception;

    /**
     * 表示操作正常完成
     */
    public SimpleResponse() {
        this.code = ResponseCode.OK;
        this.data = "Success";
    }

    public SimpleResponse(int code) {
        this.code = code;
    }

    /**
     * 可以设置一个Response的完成情况
     * @param success
     */
    public SimpleResponse(boolean success) {
        this.code = success ? 0 : -1;
        this.data = null;
    }

    public SimpleResponse(int code, Object data) {//code是否有问题，data传给前端的数据
        this.code = code;
        this.data = data;
    }

    /**
     * 出现问题，包含异常进行抛出
     * @param code
     * @param data
     * @param exception
     */
    public SimpleResponse(int code, Object data, String exception){//string 加说明
        this.code = code;
        this.data = data;
        this.exception = exception;//其他的异常部分
    }

    public static SimpleResponse exception(String message) {
        return new SimpleResponse(ResponseCode.CATCH_EXCEPTION, message);
    }

    public static SimpleResponse error(Object data) {
        return new SimpleResponse(ResponseCode.Error, data);
    }

    public static SimpleResponse ok(Object data) {
        return new SimpleResponse(ResponseCode.OK, data);
    }

    /**
     * @return if data==null return -3
     */
    public static SimpleResponse checkNull(Object data) {
        if (data == null) {
            return new SimpleResponse(ResponseCode.RESULT_IS_NULL, null);
        } else {
            return ok(data);
        }
    }

    public static SimpleResponse checkBoolean(boolean result) {
        return new SimpleResponse(result);
    }

    public static SimpleResponse ifTrue(boolean result, Supplier<?> data) {
        if (result) {
            return ok(data.get());
        }
        return new SimpleResponse(false);
    }

    public static SimpleResponse checkForbidden(boolean result) {
        if (result == true) {
            return SimpleResponse.ok(null);
        } else {
            return new SimpleResponse(403);
        }
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    @JsonIgnore
    //在序列化的时候忽略
    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}