package com.springapp.mvc11;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by liguodong on 2015/11/26.
 */
@Controller
@RequestMapping(value="/emp")
public class EmpAction {

    /**
     * @ResponseBody Emp 表示将springmvc对象转成json文本
     */

    @RequestMapping(value = "/beentojson")
    public @ResponseBody Emp beenToJson() throws Exception{

        //创建Emp对象
        Emp emp = new Emp();
        emp.setId(1);
        emp.setUsername("允儿");
        emp.setHiredate(new Date("2015-11-26"));

        System.out.println("=========");

        return emp;
    }


    @RequestMapping(value = "/listbeentojson")
    public @ResponseBody List<Emp> listbeenToJson() throws Exception{

        //创建List对象
        List<Emp> empList = new ArrayList<Emp>();

        //向List对象中添加三个Emp对象
        empList.add(new Emp(1, "科比", 7000D, new Date("2015-11-11")));
        empList.add(new Emp(2, "乔丹", 8000D, new Date("2014-11-19")));
        empList.add(new Emp(3, "邓肯", 8900D, new Date("2034-11-19")));

        System.out.println("=========");

        //返回需要转JSON的对象
        return empList;
    }


    @RequestMapping(value = "/mapbeentojson")
    public @ResponseBody Map<String,Object> mapbeenToJson() throws Exception{

        //创建List对象
        List<Emp> empList = new ArrayList<Emp>();

        //向List对象中添加三个Emp对象
        empList.add(new Emp(1,"科比",7000D,new Date("2015-11-11")));
        empList.add(new Emp(2,"乔丹",8000D,new Date("2014-11-19")));
        empList.add(new Emp(3,"邓肯",8900D,new Date("2034-11-19")));

        System.out.println("=========");

        //创建Map对象中绑定的二个变量

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("total",empList.size());
        map.put("rows",empList);

        //返回需要转JSON的对象
        return map;
    }


}
