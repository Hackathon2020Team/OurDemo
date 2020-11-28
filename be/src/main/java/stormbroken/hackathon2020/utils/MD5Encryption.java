package stormbroken.hackathon2020.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author stormbroken
 * Create by 2020/11/18
 * @Version 1.0
 **/

public class MD5Encryption {
    //修改密码需要加密,使用单向哈希MD5进行加密
    public static String encrypt(String word) throws NoSuchAlgorithmException {
        //用于加密密码
        MessageDigest md = MessageDigest.getInstance("MD5");//只能从a到b
        byte[] input = word.getBytes();
        byte[] output = md.digest(input);
        return Base64.encodeBase64String(output);
    }
}