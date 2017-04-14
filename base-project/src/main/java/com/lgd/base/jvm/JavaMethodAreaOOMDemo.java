package com.lgd.base.jvm;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * <p>Title: JavaMethodAreaOOMDemo</p>
 * <p>Description:
 *  方法区溢出
 *  -XX:PermSize=10M -XX:MaxPermSize=10M
 * </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/14 15:52
 */
public class JavaMethodAreaOOMDemo {

    public static void main(String[] args) {
        while(true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method m, Object[] objs, MethodProxy proxy) throws Throwable {
                    // TODO Auto-generated method stub
                    return proxy.invokeSuper(obj, objs);
                }
            });
            enhancer.create();
        }
    }

    class OOMObject {}
}
