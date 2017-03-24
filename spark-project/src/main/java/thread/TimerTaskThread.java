package thread;

/**
 * Created by liguodong on 2017/3/14.
 */

public class TimerTaskThread extends Thread {

    public TimerTaskThread(int i){
        //创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。
        super.setName("TimerTaskThread:"+i);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello");
        System.out.println(Thread.currentThread().getName());
    }
}

class TimeTaskMain{
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            TimerTaskThread timerTaskThread = new TimerTaskThread(i);
            timerTaskThread.start();
        }

    }
}