package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by liguodong on 2017/3/25.
 */
public class LockAndSyncMain {



    public static void main(String[] args) {
        //reentrantLock();
        //readWriteLockThread();
        //syncReadWriteThread();
        //tryLockThread();
        interruptiblyThread();
    }

    private static void reentrantLock() {
        List<Integer> list = new ArrayList<>();
        Lock lock = new ReentrantLock();

        LockThread lockThread1 = new LockThread(lock,list);
        LockThread lockThread2 = new LockThread(lock,list);
        lockThread1.start();
        lockThread2.start();
    }

    private static void readWriteLockThread() {
        ReadWriteLockTask task = new ReadWriteLockTask();

        ReadWriteLockThread lockThread1 = new ReadWriteLockThread(task);
        lockThread1.start();

        ReadWriteLockThread lockThread2 = new ReadWriteLockThread(task);
        lockThread2.start();
    }

    private static void syncReadWriteThread() {
        SyncReadWriteThread lockThread1 = new SyncReadWriteThread();
        lockThread1.start();
        SyncReadWriteThread lockThread2 = new SyncReadWriteThread();
        lockThread2.start();
    }

    private static void tryLockThread() {
        List<Integer> list = new ArrayList<>();
        Lock lock = new ReentrantLock();

        TryLockThread lockThread1 = new TryLockThread(lock,list);
        lockThread1.start();
        TryLockThread lockThread2 = new TryLockThread(lock,list);
        lockThread2.start();
    }

    private static void interruptiblyThread() {
        Lock lock = new ReentrantLock();
        InterruptiblyThread thread0 = new InterruptiblyThread(lock);
        thread0.start();
        InterruptiblyThread thread1 = new InterruptiblyThread(lock);
        thread1.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();
        System.out.println("=====================");
    }

}




class LockThread extends Thread{

    Lock lock;
    List<Integer> list;

    public LockThread(Lock lock,List<Integer> list) {
        this.lock = lock;
        this.list = list;
    }

    public void run() {
        Thread thread = Thread.currentThread();
        lock.lock();
        try {
            System.out.println(thread.getName() + "得到了锁");
            for (int i = 0; i < 5; i++) {
                list.add(i);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            System.out.println(thread.getName() + "释放了锁");
            lock.unlock();
        }

    }
}


/**
 *
 * 使用读写锁，可以实现读写分离锁定，读操作并发进行，写操作锁定单个线程
 *
 * 如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁。
 * 如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁。
 */
class ReadWriteLockThread extends Thread {

    ReadWriteLockTask readWriteLockTask;

    public ReadWriteLockThread(ReadWriteLockTask readWriteLockTask) {
        this.readWriteLockTask = readWriteLockTask;
    }

    @Override
    public void run() {
        readWriteLockTask.get(Thread.currentThread());
        readWriteLockTask.write(Thread.currentThread());
    }
}

class ReadWriteLockTask {

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Random random = new Random();
    /**
     * 读操作,用读锁来锁定
     * @param thread
     */
    public void get(Thread thread) {
        rwl.readLock().lock();
        try {
            System.out.println(thread.getName()+"读操作开始");
            long start = System.currentTimeMillis();

            while(System.currentTimeMillis() - start <= 5) {
                Thread.sleep(random.nextInt(5));
                System.out.println(thread.getName()+"正在进行读操作");
            }
            System.out.println(thread.getName()+"读操作完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();
        }
    }

    /**
     * 写操作，用写锁来锁定
     * @param thread
     */
    public void write(Thread thread) {
        rwl.writeLock().lock();
        try {
            System.out.println(thread.getName()+"写操作开始");
            long start = System.currentTimeMillis();
            while(System.currentTimeMillis() - start <= 2) {
                Thread.sleep(random.nextInt(2));
                System.out.println(thread.getName()+"正在进行写操作");
            }
            System.out.println(thread.getName()+"写操作完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwl.writeLock().unlock();
        }
    }
}

/**
 * 一个线程又要读又要写，用synchronize来实现的话，读写操作都只能锁住后一个线程一个线程地进行
 */
class SyncReadWriteThread extends Thread {

    @Override
    public void run() {
        get(Thread.currentThread());
    }

    public synchronized void get(Thread thread) {
        long start = System.currentTimeMillis();
        int i=0;
        while(System.currentTimeMillis() - start <= 1) {
            i++;
            if(i%4==0){
                System.out.println(thread.getName()+"正在进行写操作");
            }else {
                System.out.println(thread.getName()+"正在进行读操作");
            }
        }
        System.out.println(thread.getName()+"读写操作完毕");
    }

}

/**
 * 观察现象：一个线程获得锁后，另一个线程取不到锁，不会一直等待
 */
class TryLockThread extends Thread {

    Lock lock;
    List<Integer> list;

    public TryLockThread(Lock lock,List<Integer> list) {
        this.lock = lock;
        this.list = list;
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        boolean tryLock = lock.tryLock();
        System.out.println(thread.getName()+" "+tryLock);
        if (tryLock) {
            try {
                System.out.println(thread.getName() + "得到了锁.");
                for (int i = 0; i < 50; i++) {
                    list.add(i);
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                System.out.println(thread.getName() + "释放了锁.");
                lock.unlock();
            }
        }
    }
}

/**
 * 观察现象：如果thread-0得到了锁，阻塞。thread-1尝试获取锁，如果拿不到，则可以被中断等待
 */
class InterruptiblyThread extends Thread {

    Lock lock;

    public InterruptiblyThread(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            insert(Thread.currentThread());
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName()+"被中断");
        }
    }

    public void insert(Thread thread) throws InterruptedException{
        //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        lock.lockInterruptibly();
        try {
            System.out.println(thread.getName()+"得到了锁");
            long startTime = System.currentTimeMillis();
            for(;;) {
                //Integer.MAX_VALUE
                if(System.currentTimeMillis() - startTime >= 3000)
                    break;
                //插入数据
            }
        } finally {
            System.out.println(Thread.currentThread().getName()+"执行finally");
            lock.unlock();
            System.out.println(thread.getName()+"释放了锁");
        }
    }
}