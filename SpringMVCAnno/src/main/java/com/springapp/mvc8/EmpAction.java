package com.springapp.mvc8;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 员工模块
 * Created by liguodong on 2015/11/25.
 */

@Controller
@RequestMapping(value = "/emp")
public class EmpAction {


    @RequestMapping(value = "addAll",method = RequestMethod.POST)
    public String deleteAll(Model model, Bean bean) throws Exception{

        for (Emp emp:bean.getEmpList()){
            System.out.println("Username:" + emp.getUsername() +" Salary:" + emp.getSalary());
        }
        System.out.println();

        model.addAttribute("message","批量增加员工成功");
        return "/WEB-INF/pages/ok.jsp";
    }

}
