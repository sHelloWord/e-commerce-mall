package com.rimi.mall.utils;

/**
 * 字符串工具类
 *
 * @author admin
 * @date 2019-04-18 14:17
 */
public final class StringUtils {

    /**
     * 判断字符是否为空
     *
     * @param str 需要判断的字符串
     * @return 结果
     */
    public static boolean isBlank(String str) {
        return str == null || "".equals(str);
    }
}
