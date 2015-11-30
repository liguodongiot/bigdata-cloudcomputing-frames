package com.springapp.mvc2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制器
 * Created by liguodong on 2015/11/24.
 */

@Controller
@RequestMapping(value = "/user")  //模块根路径
public class UserAction {

    /**
     * 用户注册
     */
    //value值没有必要和方法名一致
    @RequestMapping(value = "/register")  //功能子路径
    public String register(Model model){
        model.addAttribute("message", "增加用户成功");
        //System.out.println("add user success");
        System.out.println("增加用户成功");
        return "hello";
    }

    /**
     * 用户登录
     */
    @RequestMapping(value = "/login")  //功能子路径
    public String login(Model model){
        model.addAttribute("message", "用户登录成功");
        //System.out.println("user login success");
        System.out.println("用户登录成功");
        return "hello";
    }

}
