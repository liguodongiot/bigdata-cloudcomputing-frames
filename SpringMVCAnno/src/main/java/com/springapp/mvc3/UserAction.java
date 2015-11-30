package com.springapp.mvc3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 控制器
 * 使用普通变量收集参数
 * 弊端：参数过多，不适用。
 *
 *
 * 原则：
 * JavaBean中的属性适合用包装类型，即Integer，Double
 * 普通方法中的参数类型适合用于基本类型，即int,double
 * Created by liguodong on 2015/11/24.
 */

@Controller
@RequestMapping(value = "/user")  //模块根路径
public class UserAction {

    /**
     * 用户注册
     * 只能接收POST请求
     */
    //value值没有必要和方法名一致
    @RequestMapping(value = "/register", method= RequestMethod.POST)  //功能子路径
    public String register(Model model,String username,double salary){
        model.addAttribute("message", "增加用户成功");
        //System.out.println("add user success");
        System.out.println("增加用户成功");
        System.out.println("Username:"+username+", Salary:"+salary);

        return "hello";
    }

    /**
     * 用户登录
     * 既能接收POST请求，也能接收GET请求
     */
    @RequestMapping(value = "/login", method= {RequestMethod.POST,RequestMethod.GET})  //功能子路径
    public String login(Model model,String username){
        model.addAttribute("message", "用户登录成功");
        //System.out.println("user login success");
        System.out.println("用户登录成功");
        System.out.println("Username:"+username);
        return "hello";
    }

}
