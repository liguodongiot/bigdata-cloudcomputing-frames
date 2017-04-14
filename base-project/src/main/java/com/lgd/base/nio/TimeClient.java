package com.lgd.base.nio;

/**
 * <p>Title: TimeClient</p>
 * <p>Description: </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/12 11:12
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;
        if (args!=null && args.length>0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
    }
}
