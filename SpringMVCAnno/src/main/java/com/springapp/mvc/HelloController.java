package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 单例控制类
 */

@Controller
public class HelloController {

	//业务方法
	//只要是/hello.action的请求，都交由HelloAction对象中的hello()方法去处理
	//可以有多个action的请求，都交由HelloAction对象中的hello()方法去处理
	//@RequestMapping(value={"/a.action","/b.action","/c.action"})
	@RequestMapping("/hello.action")
	public String printWelcome(Model model) {
		model.addAttribute("message", "Hello world!");
		return "hello";
	}
}