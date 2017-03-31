package com.lgd.base.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by liguodong on 2017/3/31.
 */
public class ProxyPerson {

    public static void main(String[] args) {
        final Person p = new PersonImpl();
        Person proxy = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), PersonImpl.class.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("proxy is coming....");

                if (method.getName() == "saySomeThing") {
                    System.out.println("say some thing is special handled.....");
                    p.saySomeThing();
                } else {
                    Object invoke = method.invoke(p, args); // 调用任何public方法都拦截
                    System.out.println("proxy is leaving....");
                }
                return null;
            }
        });
        proxy.doSomeThing();
        System.out.println("-----------------------");
        proxy.saySomeThing();

    }
}
