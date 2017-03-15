package thread;

/**
 * Created by liguodong on 2017/3/15.
 */
public class TaskRunnable implements Runnable {

    private Integer taskNum;

    public TaskRunnable(Integer taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void run() {
        try {
            System.out.println("taskNum:"+taskNum);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("running=====");
    }

}
