package com.lgd.base.design.proxy;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2017/12/22 11:38 星期五
 */
public class AaaTemplate implements Template{

    public AaaTemplate() {
    }

    @Override
    public void build(StringBuilder stringBuilder) {
        stringBuilder.append("--------AAA-----");
    }



}
