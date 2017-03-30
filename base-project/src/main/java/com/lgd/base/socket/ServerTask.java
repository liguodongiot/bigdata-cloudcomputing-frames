package com.lgd.base.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by liguodong on 2017/3/30.
 */
public class ServerTask implements Runnable {
    private Socket socket;

    public ServerTask(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in;
        OutputStream out;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String request = br.readLine();
            String[] split = request.split(":");
            String className = split[0];
            String methodName = split[1];
            String methodParam= split[2];

            Class<?> forName = Class.forName(className);
            System.out.println("calling class: " + forName);
            Object newInstance = forName.newInstance();
            Method method = forName.getMethod(methodName,String.class);
            System.out.println("calling method: " + method);
            Object invoke = method.invoke(newInstance, methodParam);
            System.out.println("results: " + (int)invoke);

            PrintWriter pw = new PrintWriter(new BufferedOutputStream(out));
            pw.println((int)invoke);
            pw.flush();

            br.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
