package com.rimi.mall.dao;

import com.rimi.mall.pojo.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> selectAll();

}