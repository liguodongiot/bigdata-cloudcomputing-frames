package com.lgd.base.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Describe: ForkJoin批处理分页数据
 * author: guodong.li
 * datetime: 2017/6/7 18:47
 */
public class BatchPageForkJoinMain {

/*
    private final static int TOTAL_NUM = 23036;
    private final static int PAGE_SIZE = 20;

    private final static Logger LOGGER = LoggerFactory.getLogger(BatchPageForkJoinMain.class);

    public static void main(String[] args) {

        int total_page = 0;
        if(TOTAL_NUM%PAGE_SIZE==0){
            total_page = TOTAL_NUM/PAGE_SIZE;
        } else {
            total_page = TOTAL_NUM/PAGE_SIZE + 1;
        }

        BatchPageForkJoinMain app = new BatchPageForkJoinMain();

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        CountPageNumTask task = app.new CountPageNumTask(1,total_page);

        Future<Long> result = forkJoinPool.submit(task);

        try{
            System.out.println("The fail task num is "
                    + result.get());
        } catch(Exception e){
            LOGGER.error("获取值出错："+e);
        }


    }


    private class CountPageNumTask extends RecursiveTask<Long> {
        //一个线程10个分页
        private static final int THRESHOLD = 10; //阀值

        private int start;
        private int end;

        public CountPageNumTask(int start, int end){
            this.start = start;
            this.end = end;
        }


        @Override
        protected Long compute() {

            System.out.println("Thread ID: " + Thread.currentThread().getId() +
                    ",Thread Name: " + Thread.currentThread().getName());

            //返回失败的记录数
            Long failTaskNum = 0l;

            if((end -start) <= THRESHOLD){
                //sum = sumIteration(start, end);
            }
            else{
                int middle = (start + end) / 2;
                CountPageNumTask leftTask = new CountPageNumTask(start, middle);
                CountPageNumTask rightTask = new CountPageNumTask(middle + 1, end);
                leftTask.fork();
                rightTask.fork();

                Long leftFailResult = leftTask.join();
                Long rightFailResult = rightTask.join();

                failTaskNum = leftFailResult + rightFailResult;
            }
            return failTaskNum;
        }
    }

    public void handleData() {

    }
	*/
}
