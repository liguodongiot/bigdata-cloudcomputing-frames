package com.lgd.base.design.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * <p>Title: 动态代理</p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2017/12/22 11:16 星期五
 */
public class Client {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        Template template = new AaaTemplate();
        Template template1 = new BbbTemplate("dddd");

        //我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        //InvocationHandler handler = new ReportHandler(reportTemplate);

        /*
         * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
         * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
         * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
         */
        //ReportTemplate subject = (ReportTemplate) Proxy.newProxyInstance(handler.getClass().getClassLoader(), reportTemplate
         //       .getClass().getInterfaces(), handler);

        //subject.build(new HashMap<String,Object>(),new StringBuilder(""));



        InvocationHandler handler = new ReportHandler(template1);
        // 通过 Proxy 为包括 Interface 接口在内的一组接口动态创建代理类的类对象
        Class clazz = Proxy.getProxyClass(handler.getClass().getClassLoader(),new Class[] {Template.class});
        // 通过反射从生成的类对象获得构造函数对象
        Constructor constructor = clazz.getConstructor(new Class[] {InvocationHandler.class });
        // 通过构造函数对象创建动态代理类实例
        Template proxy = (Template)constructor.newInstance(new Object[] { handler });
        StringBuilder stringBuilder = new StringBuilder("");
        proxy.build(stringBuilder);
        System.out.println(stringBuilder.toString());


    }

}
