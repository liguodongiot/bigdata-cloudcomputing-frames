package thread;

import java.util.Random;

/**
 * Created by liguodong on 2017/3/25.
 */

public class SimpleThreadAndSyncMain {

    public static void main(String[] args) {
        //threadWithImplDemo();
        //threadWithExtDemo();
        //线程同步
        threadSyncDemo();
    }

    public static void threadWithImplDemo(){
        Thread thread1 = new Thread(new ThreadWithImplements(1), "thread-1");
        Thread thread2 = new Thread(new ThreadWithImplements(2), "thread-2");
        thread1.start();
        thread2.start();
        // 注意调用run和调用start的区别,直接调用run，则都运行在main线程中
//		thread1.run();
//		thread2.run();
    }

    public static void threadWithExtDemo(){
        Thread thread1 = new ThreadWithExtends("a");
        Thread thread2 = new ThreadWithExtends("b");
        thread1.start();
        thread2.start();
        /**
         * 如果是调用thread的run方法，则只是一个普通的方法调用，不会开启新的线程
         */
//		thread1.run();
//		thread2.run();
    }

    public static void threadSyncDemo(){
        final SimpleThreadAndSyncMain mySynchronized = new SimpleThreadAndSyncMain();
        final SimpleThreadAndSyncMain mySynchronized2 = new SimpleThreadAndSyncMain();
        new Thread("thread1") {
            public void run() {
                synchronized (mySynchronized) {
                    try {
                        System.out.println(this.getName()+" start");
                        //int i =1/0;   //如果发生异常，jvm会将锁释放
                        Thread.sleep(5000);
                        System.out.println(this.getName()+"醒了");
                        System.out.println(this.getName()+" end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread("thread2") {
            public void run() {
                synchronized (mySynchronized) {         //争抢同一把锁时，线程1没释放之前，线程2只能等待
				//	synchronized (mySynchronized2) {    //如果不是一把锁，可以看到两句话同时打印
                    System.out.println(this.getName()+" start");
                    System.out.println(this.getName()+" end");

                }
            }
        }.start();
    }

}


class ThreadWithImplements implements Runnable {
    int x;

    public ThreadWithImplements(int x) {
        this.x = x;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("线程" + name + "的run方法被调用……");
        for (int i = 0; i < 10; i++) {
            System.out.println(x);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ThreadWithExtends extends Thread {
    String flag;

    public ThreadWithExtends(String flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        String tname = Thread.currentThread().getName();
        System.out.println(tname+"线程的run方法被调用……");
        Random random = new Random();
        for(int i=0;i<20;i++){
            try {
                Thread.sleep(random.nextInt(10)*100);
                System.out.println(tname+ "...."+ flag);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


