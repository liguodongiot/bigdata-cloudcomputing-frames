package com.springapp.mvc7;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 在业务方法中收集数组参数
 * Created by liguodong on 2015/11/25.
 */

@Controller
@RequestMapping(value = "/emp")
public class EmpAction {


    @RequestMapping(value = "deleteAll",method = RequestMethod.POST)
    public String deleteAll(Model model,int[] ids) throws Exception{

        System.out.println("需要删除的员工编号分别是：");
        for (int i = 0; i < ids.length; i++) {
            System.out.print(ids[i]+" ");
        }

        System.out.println();

        model.addAttribute("message","批量删除成功");
        return "/WEB-INF/pages/ok.jsp";
    }

}
