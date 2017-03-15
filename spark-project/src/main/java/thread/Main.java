package thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by liguodong on 2017/3/14.
 */
public class Main {

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };



    private static int produceTaskSleepTime = 2;
    private static int produceTaskMaxNumber = 10;

    public static void main(String[] args) {

        threadPool();

    }

    public static void threadPool(){
        //线程安全
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        // 构造一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 1; i <= produceTaskMaxNumber; i++) {
            try {
                String task = "task@ " + i;
                System.out.println("创建任务并提交到线程池中：" + task);
                threadPool.execute(new ThreadPoolTask(task,threadLocalRandom));
                Thread.sleep(produceTaskSleepTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        threadPool.shutdown();
    }



}
