package com.springapp.mvc6;

import java.util.Date;

/**
 * 管理员
 * Created by liguodong on 2015/11/25.
 */
public class Admin {

    private String username;
    private Double salary;
    private Date hiredate;

    public Admin() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", salary=" + salary +
                ", hiredate=" + hiredate.toLocaleString() +
                '}';
    }

}



