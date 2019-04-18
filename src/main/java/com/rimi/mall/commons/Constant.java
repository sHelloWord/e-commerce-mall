package com.rimi.mall.commons;

/**
 * 常量
 *
 * @author admin
 * @date 2019-04-18 10:50
 */
public final class Constant {

    /**
     * redis中在线人数的key
     */
    public static final String REDIS_ONLINE = "mall:online";

    /**
     * redis中的活跃key前缀
     */
    public static final String REDIS_ACTIVE_PREFIX = "mall:active";

    /**
     * redis中内容的key
     */
    public static final String REDIS_CONTENT = "mall:content";
}
