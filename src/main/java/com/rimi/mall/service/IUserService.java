package com.rimi.mall.service;

import com.rimi.mall.pojo.User;

import java.util.List;

/**
 * @author admin
 * @date 2019-04-15 15:17
 */
public interface IUserService {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    User findByUserName(String username);

    /**
     * 查询所有的用户
     *
     * @return 用户列表
     */
    List<User> findAll();
}
