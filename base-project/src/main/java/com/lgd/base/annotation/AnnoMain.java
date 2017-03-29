package com.lgd.base.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
/**
 * Created by liguodong on 2017/3/28.
 */

public class AnnoMain{

    public static void main(String[] args) throws Exception {
        AnnoMain annoMain = new AnnoMain();
        annoMain.test("1",2);
    }

    //获取方法参数名
    public void test(@Parameter("paramStr") String param1,
                     @Parameter("paramInt") int param2)throws Exception  {
        System.out.println(param1 + param2);

        Method method = AnnoMain.class.getMethod("test", String.class, int.class);
        System.out.print("test : ");
        Annotation parameterAnnotations[][] = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (Parameter.class.equals(annotation.annotationType())) {
                    System.out.print(((Parameter) annotation).value() + ' ');
                }
            }
        }
    }
}

