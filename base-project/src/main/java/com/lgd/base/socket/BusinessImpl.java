package com.lgd.base.socket;

/**
 * Created by liguodong on 2017/3/30.
 */
public class BusinessImpl implements Business {
    @Override
    public int getPrice(String good) {
        return good.equals("yifu")?10:20;
    }
}
