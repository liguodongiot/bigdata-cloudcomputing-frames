package com.lgd.base.serializable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liguodong on 2017/3/30.
 */

public class Task implements Runnable, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void run() {
        System.out.println(new Date());
    }

}
