package com.lgd.base.jvm;

/**
 * <p>Title: JavaVMStackSOFDemo</p>
 * <p>Description:
 *  虚拟机栈和本地方法栈溢出 StackOverFlow
 *  VM Args：-Xss128k
 * </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/14 15:20
 */
public class JavaVMStackSOFDemo {

    private int stackLength = 1;

    // 递归调用方法，定义大量的本地变量，增大此方法帧中本地变量表的长度
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOFDemo oom = new JavaVMStackSOFDemo();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length: " + oom.stackLength);
            //throw e;
            e.printStackTrace();
        }
    }

}
