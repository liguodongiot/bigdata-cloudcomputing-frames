package thread;

/**
 * Created by liguodong on 2017/3/14.
 */

public class TimerTaskThread extends Thread {
    public TimerTaskThread(){
        //创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。
        super.setName("TimerTaskThread");
    }

}
