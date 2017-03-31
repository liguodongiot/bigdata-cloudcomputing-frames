package com.lgd.base.proxy;

/**
 * Created by liguodong on 2017/3/30.
 */
public class SaleActionMain {

    public static void main(String[] args) throws Exception {

        saleByBossSelf();
        saleByProxy();

    }

    /**
     * 什么是动态代理？ 简单的写一个模板接口，剩下的个性化工作，交给动态代理来完成！
     * 使用代理，在这个代理中，只代理了Boss的yifu方法
     * 定制化业务，可以改变原接口的参数、返回值等
     */
    public static void saleByProxy() throws Exception {
        Boss boss = ProxyBoss.getProxy(10,Boss.class,BossImpl.class);// 将代理的方法实例化成接口
        //IBoss boss = new Boss();// 将代理的方法实例化成接口
        System.out.println("代理经营！");
        int money = boss.yifu("xxl");// 调用接口的方法，实际上调用方式没有变
        System.out.println("衣服成交价：" + money);
    }

    /**
     * 不使用代理，直接调用方法
     * 方法中规定什么业务，就只能调用什么业务，规定什么返回值，就只能输出什么返回值
     */
    public static void saleByBossSelf() throws Exception {
        Boss boss = new BossImpl();
        System.out.println("老板自营！");
        int money = boss.yifu("xxl");// 老板自己卖衣服，不需要客服，结果就是没有聊天记录
        System.out.println("衣服成交价：" + money);
    }


}
