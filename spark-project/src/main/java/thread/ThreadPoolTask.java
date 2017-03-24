package thread;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by liguodong on 2017/3/14.
 */

public class ThreadPoolTask implements Runnable, Serializable {

    private Object attachData;
    private ThreadLocalRandom threadLocalRandom;

    private String threadName;

    public ThreadPoolTask(Object attachData, ThreadLocalRandom threadLocalRandom) {
       Thread.currentThread().setName(attachData.toString());
        this.threadName =Thread.currentThread().getName();

        this.attachData = attachData;
        this.threadLocalRandom = threadLocalRandom;
    }

    public void run() {
        System.out.println(threadName);
        System.out.println("开始执行任务：" + attachData + ", random:"+threadLocalRandom.nextInt());

        attachData = null;
    }

    public Object getTask() {
        return this.attachData;
    }
}