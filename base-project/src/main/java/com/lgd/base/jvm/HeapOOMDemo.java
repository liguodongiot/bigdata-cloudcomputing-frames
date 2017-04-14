package com.lgd.base.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: HeapOOMDemo</p>
 * <p>Description:
 *  Java堆溢出
 *  VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=E:\java\dump
 * </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/14 14:57
 */
public class HeapOOMDemo {

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while(true) {
            // list保留引用，避免Full GC 回收
            list.add(new OOMObject());
        }
    }

    static class OOMObject {
    }

}
