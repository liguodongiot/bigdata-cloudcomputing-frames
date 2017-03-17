package thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 当使用ExecutorService启动了多个Callable后，每个Callable会产生一个Future，我们需要将多个Future存入一个线性表，用于之后处理数据。当然，还有更复杂的情况，有5个生产者线程，每个生产者线程都会创建任务，所有任务的Future都存放到同一个线性表中。另有一个消费者线程，从线性表中取出Future进行处理。
 * CompletionService正是为此而存在，它是一个更高级的ExecutorService，它本身自带一个线程安全的线性表，无需用户额外创建。它提供了2种方法从线性表中取出结果，poll()是非阻塞的，若目前无结果，返回一个null，线程继续运行不阻塞。take()是阻塞的，若当前无结果，则线程阻塞，直到产生一个结果，被取出返回，线程才继续运行。
 *
 * Created by liguodong on 2017/3/17.
 */
public class CompletionServiceTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService<Integer> comp = new ExecutorCompletionService<>(executor);
        for(int i = 0; i<5; i++) {
            comp.submit(new Task());
        }
        executor.shutdown();
        int count = 0, index = 1;
        while(count<5) {
            Future<Integer> f = comp.poll();
            if(f == null) {
                System.out.println(index + " 没发现有完成的任务");
            }else {
                System.out.println(index + "产生了一个随机数: " + f.get());
                count++;
            }
            index++;
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}

class Task implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Random rand = new Random();
        TimeUnit.SECONDS.sleep(rand.nextInt(7));
        return rand.nextInt();
    }

}