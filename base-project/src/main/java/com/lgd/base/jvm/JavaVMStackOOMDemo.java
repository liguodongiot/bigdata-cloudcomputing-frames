package com.lgd.base.jvm;

/**
 * <p>Title: JavaVMStackOOMDemo</p>
 * <p>Description:
 *  虚拟机栈和本地方法栈溢出
 *  !!!!!注意!!!!!
 *  运行该代码可能造成系统假死！！！！
 *  VM Args：-Xss2M（可以适当大一点）
 * </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/14 15:37
 */
public class JavaVMStackOOMDemo {

    private void dontStop() {
        while(true) {
        }
    }

    // 多线程方式造成栈内存溢出 OutOfMemoryError
    public void stackLeakByThread() {
        while(true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOMDemo oom = new JavaVMStackOOMDemo();
        oom.stackLeakByThread();
    }

}
