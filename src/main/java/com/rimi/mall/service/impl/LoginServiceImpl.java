package com.rimi.mall.service.impl;

import com.rimi.mall.commons.Constant;
import com.rimi.mall.pojo.User;
import com.rimi.mall.service.ILoginService;
import com.rimi.mall.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @author admin
 * @date 2019-04-15 14:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private IUserService userService;

    /**
     * 登陆处理，判断用户是否可以登陆，
     * 成功则返回true，失败则返回false
     *
     * @param user 登陆的用户信息
     * @return 返回登陆的结果
     */
    @Override
    public boolean login(User user) {
        // 判断用户是否登陆
        if (isReLogin()) {
            return true;
        } else {
            return shiroLogin(user);
        }
    }

    /**
     * 登陆操作
     *
     * @param user 用户信息
     * @return 登陆结果
     */
    private boolean shiroLogin(User user) {
        // 组装用户身份令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            // 登陆验证
            SecurityUtils.getSubject().login(token);
            // 添加登陆的人数
            BoundValueOperations ops = redisTemplate.boundValueOps(Constant.REDIS_ONLINE);
            // 自增+1
            ops.increment();
            return true;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 在线人数
     *
     * @return 在线人数
     */
    @Override
    public long online() {
        // 获取redis中的在线人数
        String online = redisTemplate.boundValueOps(Constant.REDIS_ONLINE).get();
        return online != null ? Long.parseLong(online) : 0;
    }


    /**
     * 退出
     *
     * @return 是否退出成功
     */
    @Override
    public boolean logout() {
        try {
            SecurityUtils.getSubject().logout();
            redisTemplate.boundValueOps(Constant.REDIS_ONLINE).decrement();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 活跃人数
     *
     * @return 活跃人数
     */
    @Override
    public long active() {

        // 获取当前日期
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd");
        String key = format.format(date);
        key = Constant.REDIS_ACTIVE_PREFIX + ":" + key;

        // 获取用户
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findByUserName(username);

        // 偏移量
        long offset = user.getId() * 8;

        // 位图操作
        String finalKey = key;

        redisTemplate.execute((RedisCallback<Object>) connection -> connection.setBit(finalKey.getBytes(), offset, true));
        return 0;
    }

    public long getActive(String month){

        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.YEAR);

        long active = 0;

        // 每月
        Set<String> keys = redisTemplate.keys(Constant.REDIS_ACTIVE_PREFIX + ":" + i + ":" + month);
        for (String key : keys) {

            // 获取每个key中的活跃数
            active = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.bitCount(key.getBytes());
                }
            });
        }
        return active;
    }

    /**
     * 判断是否已经登陆
     *
     * @return 登陆结果
     */
    private boolean isReLogin() {
        // 获取当前的用户
        Subject user = SecurityUtils.getSubject();
        // 判断是否登陆
        return user.isAuthenticated();
    }
}
