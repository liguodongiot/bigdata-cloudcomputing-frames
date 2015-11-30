package com.springapp.mvc5;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制器
 * Created by liguodong on 2015/11/24.
 */

@Controller
@RequestMapping(value = "/user")  //模块根路径
public class UserAction {


    /**
     * 自定义类型转换器
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true));
    }



    /**
     * 用户注册
     * 只能接收POST请求
     */
    //value值没有必要和方法名一致
    @RequestMapping(value = "/register", method= RequestMethod.POST)  //功能子路径
    public String register(User user,Model model) {
        System.out.println("用户注册："+user.toString());

        //将user绑定到model对象中
        model.addAttribute("user",user);

        //转发到hello5
        return "/WEB-INF/pages/hello5.jsp";
    }

}
