package com.lgd.base.jvm;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>Title: RuntimeConstantPoolOOMDemo</p>
 * <p>Description:
 *  运行时常量池溢出
 *  jdk1.7 及其以后的版本已经“去永久代”，jdk1.6之前的环境下运行才会出现溢出
 *  -XX:PermSize=10M -XX:MaxPermSize=10M （间接指定常量池容量大小）
 * </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/14 15:49
 */
public class RuntimeConstantPoolOOMDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        int i = 0;
        while(true) {
            // list保留引用，避免Full GC 回收
            list.add(String.valueOf(i++).intern());
        }
    }
}
