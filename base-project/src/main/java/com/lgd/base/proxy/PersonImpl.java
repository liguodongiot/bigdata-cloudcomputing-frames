package com.lgd.base.proxy;

/**
 * Created by liguodong on 2017/3/31.
 */
public class PersonImpl implements Person {

    public void doSomeThing(){
        System.out.println("Person is doing its thing.....");
    }

    public void saySomeThing() {
        System.out.println("Person is saying its thing.....");

    }

    private void xx(){
        System.out.println("Person is xx its thing.....");
    }
}
