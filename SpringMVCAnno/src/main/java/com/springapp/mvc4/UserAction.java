package com.springapp.mvc4;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 控制器
 *
 * 采用HttpServletRequest、HttpServletResponse方式 (传统方式)
 *
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
    public String register(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {

        //获取用户名和薪水
        String username = request.getParameter("username");
        String salary = request.getParameter("salary");

        System.out.println("Username:" + username + ", Salary:" + salary);

        //绑定到session域对象中
        request.getSession().setAttribute("username",username);
        request.getSession().setAttribute("salary", salary);

        //重定向/login/hello4.jsp        不能再WEB-INF目录下
        //response.sendRedirect(request.getContextPath()+"/login/hello4.jsp");

        //自己转发  /pages/ok.jsp
        request.getRequestDispatcher("/WEB-INF/pages/ok.jsp").forward(request,response);

        //框架转发（提倡）
        //采用框架帮我们跳转页面     在/WEB-INF/目录下 /pages/hello4.jsp
        return "/WEB-INF/pages/hello4.jsp";
    }

}
