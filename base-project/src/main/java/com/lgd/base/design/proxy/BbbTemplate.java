package com.lgd.base.design.proxy;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2017/12/22 11:38 星期五
 */
public class BbbTemplate implements Template{

    private String param;

    public BbbTemplate(String param) {
        this.param = param;
    }

    @Override
    public void build(StringBuilder stringBuilder) {
        stringBuilder.append("-----BBBB------param:"+param+"----------");
    }
}
