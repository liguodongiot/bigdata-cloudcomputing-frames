package com.lgd.base.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by liguodong on 2017/3/30.
 */
public class ClientMain {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 9898);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        PrintWriter pw = new PrintWriter(new BufferedOutputStream(out));
        pw.println("com.lgd.base.socket.BusinessImpl:getPrice:yifu");
        pw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = br.readLine();

        System.out.println("client get result: " + readLine);

        socket.close();
    }
}
