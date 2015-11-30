package com.springapp.mvc6;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制器
 * Created by liguodong on 2015/11/24.
 */

@Controller
@RequestMapping(value = "/person")  //模块根路径
public class PersonAction {

    /**
     * 自定义类型转换器
     */
    @InitBinder
    public void initBinder(HttpServletRequest request,ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /**
     * 业务方法
     */
    @RequestMapping(value = "/register", method= RequestMethod.POST)  //功能子路径
    public String register(Bean bean,Model model) {
        System.out.println("普通用户：" + bean.getUser());

        System.out.println("管理员：" + bean.getAdmin());

        //将user和admin绑定到Model
        model.addAttribute("user",bean.getUser());
        model.addAttribute("admin",bean.getAdmin());

        //转发到/login/MultiUser.jsp
        return "/login/MultiUser.jsp";
    }

}
