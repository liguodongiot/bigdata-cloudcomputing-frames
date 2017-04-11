package com.lgd.base.jvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <p>Title: ThreadSimulationMain</p>
 * <p>
 *     Description:
 *     jconsole线程标签相当于可视化了jstack命令，遇到线程停顿时，可以使用这个也签进行监控分析。
 *     线程长时间停顿的主要原因有：等待外部资源（数据库连接等），死循环、锁等待。
 *
 *     main线程：追踪到需要键盘录入
 *     testBusyThread线程：线程阻塞在while（true），直到线程切换，很耗性能
 *     testLockThread线程：出于waitting状态，等待notify
 * </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/11 10:04
 */
public class ThreadSimulationMain {

    /**
     * 死循环演示
     */
    public static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("createBusyThread");
                while (true)
                    ;
            }
        }, "testBusyThread");
        thread.start();
    }

    /**
     * 线程锁等待
     */
    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("createLockThread");
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "testLockThread");
        thread.start();
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        createBusyThread();
        br.readLine();
        Object object = new Object();
        createLockThread(object);
    }

}
