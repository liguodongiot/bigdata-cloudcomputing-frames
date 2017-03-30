package com.lgd.base.socket;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by liguodong on 2017/3/30.
 */

public class ServerMain {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost",9898));

        //TODO 接受客户端的连接请求;accept是一个阻塞方法，会一直等待，到有客户端请求连接才返回
        while(true){
            Socket socket = server.accept();
            new Thread(new ServerTask(socket)).start();
        }
    }

}
