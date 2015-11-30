package com.springapp.mvc9;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 转发可共享参数
 * Created by liguodong on 2015/11/26.
 */

@Controller
@RequestMapping(value="/emp")
public class EmpAction {

    @RequestMapping(value = "/find")
    public String findEmpById(int id) throws Exception{

        System.out.println("查询"+id+"号员工信息");

        //转发到EmpAction的另一个方法中去，即再次发送请求
        //return "forword:/emp/update.action";
        return "update.action";
    }


    @RequestMapping(value = "/update")
    public String update(int id) throws Exception{

        System.out.println("更新"+id+"号员工信息");

        return "/WEB-INF/pages/ok.jsp";
    }

}
