package com.rimi.mall.service;

import com.rimi.mall.pojo.User;

/**
 * @author admin
 * @date 2019-04-15 14:22
 */
public interface ILoginService {

    /**
     * 登陆处理，判断用户是否可以登陆，
     * 成功则返回true，失败则返回false
     *
     * @param user 登陆的用户信息
     * @return 返回登陆的结果
     */
    boolean login(User user);

    /**
     * 退出
     *
     * @return 是否退出成功
     */
    boolean logout();

    /**
     * 在线人数
     *
     * @return 在线人数
     */
    long online();

    /**
     * 活跃人数
     *
     * @return 活跃人数
     */
    long active();
}
