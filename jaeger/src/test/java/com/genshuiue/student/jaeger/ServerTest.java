package com.genshuiue.student.jaeger;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * created by sunshuo
 * on 2020/10/28
 */
public class ServerTest {
    @Test
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("连接成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
