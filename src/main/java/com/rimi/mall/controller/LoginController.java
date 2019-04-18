package com.rimi.mall.controller;

import com.rimi.mall.pojo.User;
import com.rimi.mall.service.ILoginService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登陆相关操作
 *
 * @author admin
 * @date 2019-04-15 14:12
 */
@Controller
public class LoginController {

    @Autowired
    private ILoginService loginService;


    @RequestMapping("/login")
    public String login(User user, Model model) {
        // 登陆操作，如果用户名或密码不存在，则跳转登陆页面
        // 如果登陆成功，则跳转到成功页面
        boolean result = loginService.login(user);
        if (result) {
            model.addAttribute("msg", "登陆成功");
            return "success";
        } else {
            model.addAttribute("mdg", "登陆失败");
            return "fail";
        }
    }

    @RequestMapping("/test")
    @RequiresPermissions("test")
    public String test() {
        return "test";
    }

    @RequestMapping("/test1")
    public String test1() {
        return "test";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        boolean logout = loginService.logout();
        return logout ? "login" : "fail";
    }

    @ResponseBody
    @RequestMapping(value = "/online",method = RequestMethod.GET)
    public String online(){
        return String.valueOf(loginService.online());
    }
}
