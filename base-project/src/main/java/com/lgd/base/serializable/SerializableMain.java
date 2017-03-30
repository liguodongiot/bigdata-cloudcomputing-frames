package com.lgd.base.serializable;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liguodong on 2017/3/30.
 */
public class SerializableMain {

    public static void main(String[] args) throws Exception {
        serObj();
        deserObj();
    }

    //序列化对象到硬盘
    private static void serObj() throws IOException {
        Task t = new Task();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("d://tasks"));
        oos.writeObject(t);
        oos.close();
    }

    //从硬盘中反序列化对象
    private static void deserObj() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("d://tasks"));
        ExecutorService pool = Executors.newCachedThreadPool();
        Task t = (Task) ois.readObject();
        pool.execute(t);
        ois.close();
        pool.shutdown();
    }
}
