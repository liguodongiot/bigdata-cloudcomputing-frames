package com.lgd.base.jvm;

/**
 * <p>Title: LockThreadMain</p>
 * <p>
 *     Description:死锁
 *     点击检查死锁，会出现死锁的详情。
 *     thread-5的锁被thread-10持有，相反亦是，造成死锁。
 * </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/11 16:57
 */
public class LockThreadMain implements Runnable {
    int a, b;

    public LockThreadMain(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        synchronized (Integer.valueOf(a)) {
            synchronized (Integer.valueOf(b)) {
                System.out.println(a + b);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new LockThreadMain(1, 2)).start();
            new Thread(new LockThreadMain(2, 1)).start();
        }
    }
}
