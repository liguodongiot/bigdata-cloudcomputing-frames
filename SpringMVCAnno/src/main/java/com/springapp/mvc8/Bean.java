package com.springapp.mvc8;

import java.util.ArrayList;
import java.util.List;

/**
 * 在业务控制方法中收集List集合中包含的JavaBean参数
 * 封装多个Emp对象
 * Created by liguodong on 2015/11/25.
 */
public class Bean {
    private List<Emp> empList = new ArrayList<Emp>();

    public Bean() {
    }

    public List<Emp> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Emp> empList) {
        this.empList = empList;
    }
}
