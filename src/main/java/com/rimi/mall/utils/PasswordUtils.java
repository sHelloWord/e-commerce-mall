package com.rimi.mall.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码工具类
 *
 * @author admin
 * @date 2019-04-15 16:16
 */
public class PasswordUtils {
    /**
     * 加密方式
     */
    private static final String ALGORITHM_NAME = "MD5";
    /**
     * 加密次数
     */
    private static final int HASH_ITERATIONS = 1024;

    /**
     * 加密密码
     *
     * @param password 源密码
     * @param salt     盐值
     * @return 加密后的密码
     */
    public static String encryptPassword(String password, String salt) {
        return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
    }

    public static void main(String[] args) {
        System.out.println(encryptPassword("123456", "admin"));
    }


}
