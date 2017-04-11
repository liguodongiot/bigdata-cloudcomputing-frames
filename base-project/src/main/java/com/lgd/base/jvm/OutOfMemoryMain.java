package com.lgd.base.jvm;

import java.util.ArrayList;

/**
 * <p>Title: OutOfMemoryMain</p>
 * <p>Description:
 *      Jconsole的内存标签相当于可视化的jstat命令，用于监视收集器管理的虚拟机内存（java堆和永久代）的变化趋势。
 *      设置的虚拟机参数为：
 *      -Xms100m -Xmx100m -XX:+UseSerialGC ，
 *      这段代码的作用是以64kb/50毫秒的速度往java堆内存中填充数据。
 *
 *
 *      从图中可以看出，运行轨迹成曲线增长，循环1000次后，虽然整个新生代Eden和Survivor区都基本上被清空了，
 *      但是老年代仍然保持峰值状态，这说明，填充的数据在GC后仍然存活，因为list的作用域没有结束。
 *      如果把System.gc();移到fillHeap(1000);后，就可以全部回收掉。
 * </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/11 11:16
 */
public class OutOfMemoryMain {

    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws Exception {
        ArrayList<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args) throws Exception {
        Thread.sleep(50000);
        fillHeap(1000);
        Thread.sleep(20000000);
    }

}
