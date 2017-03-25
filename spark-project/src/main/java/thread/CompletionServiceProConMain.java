package thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 单独启动一个线程获取结果
 *
 * 获取任务的结果的要重新开一个线程获取，
 * 如果在主线程获取，就要等任务都提交后才获取，
 * 就会阻塞大量任务结果，队列过大OOM，所以最好异步开个线程获取结果。
 * Created by liguodong on 2017/3/18.
 */
public class CompletionServiceProConMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService<Integer> comp = new ExecutorCompletionService<>(executor);

        executor.submit(new ConsumerTask(comp));

        for(int i = 0; i<5; i++) {
            comp.submit(new ProducerTask());
        }
        executor.shutdown();

    }
}

class ProducerTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Random rand = new Random();
        TimeUnit.SECONDS.sleep(rand.nextInt(7));
        return rand.nextInt();
    }

}

class ConsumerTask implements Runnable {

    private CompletionService<Integer> completionService;

    public ConsumerTask(CompletionService<Integer> completionService) {
        this.completionService = completionService;
    }

    @Override
    public void run() {
        int count = 0, index = 1;

        while(count<5) {

            Future<Integer> f = null;

            try {
                //观察两者产生的不同。
                f =completionService.poll();
                //f = completionService.take();
                if(f == null) {
                    System.out.println(index + " 没发现有完成的任务");
                }else {
                    System.out.println(index + "产生了一个随机数: " + f.get());
                    count++;
                }
                index++;
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
