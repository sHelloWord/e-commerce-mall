package com.rimi.mall.commons;

import com.alibaba.fastjson.JSONArray;
import com.rimi.mall.pojo.User;
import com.rimi.mall.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

/**
 * 通知
 *
 * @author admin
 * @date 2019-04-18 14:58
 */
//@Aspect
public class Advice {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Around("execution(* com.rimi.mall.service.impl.*.findAll())")
    public Object aroundCache(ProceedingJoinPoint point) throws Throwable {
        // 缓存
        // 判断redis中是否有数据，如果有数据，
        // 则返回数据，如果没有，则查询数据库
        try {
            String value = redisTemplate.boundValueOps(Constant.REDIS_CONTENT).get();
            if (!StringUtils.isBlank(value)) {
                System.out.println("从redis中获取数据");
                // 把json格式的数据列表转换成列表对象
                return JSONArray.parseArray(value, User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object proceed = point.proceed();
        // 把结果数据存放到redis中
        // 把数据结果转换成json格式的字符串
        try {
            String jsonList = JSONArray.toJSONString(proceed);
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(Constant.REDIS_CONTENT, jsonList, Duration.ofSeconds(300));
            System.out.println("存放redis中");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proceed;
    }
}
