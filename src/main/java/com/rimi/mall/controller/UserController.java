package com.rimi.mall.controller;

import com.rimi.mall.pojo.User;
import com.rimi.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户
 *
 * @author admin
 * @date 2019-04-18 14:08
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> list(Model model) {
        List<User> list = userService.findAll();
        return list;
    }

}
