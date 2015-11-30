package com.springapp.mvc10;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 重定向 可共享参数
 * Created by liguodong on 2015/11/26.
 */

@Controller
@RequestMapping(value="/emp")
public class EmpAction {

    @RequestMapping(value = "/find")
    public String findEmpById(int id,HttpSession session) throws Exception{

        System.out.println("查询"+id+"号员工信息");

        session.setAttribute("id",id);

        //重定向到EmpAction的另一个方法中去，即再次发送请求
        return "redirect:/emp/update.action";
    }


    @RequestMapping(value = "/update")
    public String update(HttpSession session) throws Exception{

        int id = (Integer)session.getAttribute("id");

        System.out.println("更新"+id+"号员工信息");

        return "/WEB-INF/pages/ok.jsp";
    }

}
