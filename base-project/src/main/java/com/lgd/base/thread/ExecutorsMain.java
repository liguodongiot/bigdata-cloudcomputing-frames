package com.lgd.base.thread;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by liguodong on 2017/3/30.
 */
public class ExecutorsMain {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //mulExecutors();

        useThreadPool();
    }

    //并发包中的各种线程池
    private static void mulExecutors() {
        //可用核数
        int cpuNums = Runtime.getRuntime().availableProcessors();
        System.out.println(cpuNums);

        //只有一个线程的线程池，因此所有提交的任务是顺序执行.
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();

        //线程池里有很多线程需要同时执行，老的可用线程将被新的任务触发重新执行，
        // 如果线程超过60秒内没执行，那么将被终止并从池中删除
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        //拥有固定线程数的线程池，如果没有任务执行，那么线程会一直等待
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(cpuNums);

        //用来调度即将执行的任务的线程池，可能是不是直接执行, 每隔多久执行一次... 策略型的
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(8);

        //只有一个线程，用来调度任务在指定时间执行
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    }

    private static void useThreadPool() throws ExecutionException, InterruptedException {
        Future<?> submit = null;
        Random random = new Random();

        //创建固定数量线程池
//		ExecutorService exec = Executors.newFixedThreadPool(4);

        //创建调度线程池
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(4);

        //用来记录各线程的返回结果
        ArrayList<Future<?>> results = new ArrayList<Future<?>>();

        for (int i = 0; i < 10; i++) {
            //fixedPool提交线程，runnable无返回值，callable有返回值
			/*submit = exec.submit(new TaskRunnable(i));*/
			/*submit = exec.submit(new TaskCallable(i));*/

            //对于schedulerPool来说，调用submit提交任务时，跟普通pool效果一致
			/*submit = exec.submit(new TaskCallable(i));*/
            //对于schedulerPool来说，调用schedule提交任务时，则可按延迟，按间隔时长来调度线程的运行
            //还可让线程多久调用一次
            submit = exec.schedule(new TaskCallable(i), random.nextInt(10), TimeUnit.SECONDS);
            //存储线程执行结果
            results.add(submit);

        }


        //打印结果
        for(Future f: results){
            boolean done = f.isDone();
            System.out.println(done?"已完成":"未完成");  //从结果的打印顺序可以看到，即使未完成，也会阻塞等待
            System.out.println("线程返回future结果： " + f.get());
        }

        exec.shutdown();
    }

}
