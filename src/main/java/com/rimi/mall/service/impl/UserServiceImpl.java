package com.rimi.mall.service.impl;

import com.rimi.mall.dao.UserMapper;
import com.rimi.mall.pojo.User;
import com.rimi.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author admin
 * @date 2019-04-15 15:19
 */
@CacheConfig(cacheNames = "user")
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    @Override
    @Cacheable(key = "#username")
    public User findByUserName(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 查询所有的用户
     *
     * @return 用户列表
     */
    @Override
    @CacheEvict(allEntries = true)
    public List<User> findAll() {
        // 从数据库中查询结果
        List<User> userList = userMapper.selectAll();
        return userList;
    }
}
