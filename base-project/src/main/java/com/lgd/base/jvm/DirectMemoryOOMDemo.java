package com.lgd.base.jvm;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * <p>Title: DirectMemoryOOMDemo</p>
 * <p>
 *     Description:
 *      本机直接内存溢出
 *      VM Args：-Xmx20M -XX:MaxDirectMemorySize=10M
 * </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/12 14:13
 */
public class DirectMemoryOOMDemo {

    private static final int _1MB = 1024 * 1024 * 1024;

    public static void main(String[] args) {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = null;

        try {
            unsafe = (Unsafe) unsafeField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("开始向操作系统申请内存。。。");
        while (true) {
            //unsafe 直接向操作系统申请内存
            unsafe.allocateMemory(_1MB);
            System.out.println("...");
        }
    }

}
