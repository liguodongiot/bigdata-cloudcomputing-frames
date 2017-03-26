package com.lgd.base.thread;

/**
 * Created by liguodong on 2017/3/26.
 */
public class VolatileMain {

    private static volatile int numb = 0;

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //这里延迟1毫秒，使得结果明显
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < 1000; i++) {
                        numb++;
                    }
                }
            }).start();
        }
        Thread.sleep(2000);
        //这里得到的结果并不一定是1000000，每次都有可能不同。
        System.out.println(numb);
    }

}
