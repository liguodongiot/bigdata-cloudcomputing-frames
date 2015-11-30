package com.springapp.mvc6;

/**
 * 在业务控制方法中写入包装User的模型来收集参数
 * 封装User和Admin的对象
 * Created by liguodong on 2015/11/25.
 */
public class Bean {
    private User user;
    private Admin admin;

    public Bean(){ }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
