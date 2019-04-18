package com.rimi.mall.utils;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * redis
 *
 * @author admin
 * @date 2019-04-18 09:14
 */
public class RedisUtils {

    public static void main(String[] args) {
        // 获取redis的连接
        Jedis jedis = new Jedis("10.1.0.109", 6379);
        // 获取当前redis中的所有的key
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
        // 获取某个key的类型
        String key2 = jedis.type("key2");
        System.out.println("key2的类型为：" + key2);

        // 获取字符串中的value
        String value = jedis.get("key2");
        System.out.println("key2的值为：" + value);

        // 把字符串类型的value放入redis
        jedis.set("login", "0");

        // redis自增
        jedis.incr("login");

        String type = jedis.type("friend:4");
        System.out.println(type);

        // set获取
        Set<String> set = jedis.smembers("friend:4");
        for (String s : set) {
            System.out.println("集合中的数据：" + s);
        }

        // 删除
        Long srem = jedis.srem("friend:4", "8");
        System.out.println(srem > 0 ? "删除成功" : "删除失败");


    }
}
