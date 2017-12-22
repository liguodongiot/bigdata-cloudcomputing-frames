package com.lgd.base.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2017/12/22 11:09 星期五
 */
public class ReportHandler  implements InvocationHandler {

    Object reportTemplate;

    public ReportHandler(Object reportTemplate) {
        super();
        this.reportTemplate = reportTemplate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        method.invoke(reportTemplate, args);
        return null;
    }

}