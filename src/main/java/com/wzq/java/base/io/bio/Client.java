package com.wzq.java.base.io.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author: create by wangzq
 * @version: v1.0
 * @date:2020/8/10 1:06
 */
class Client {
    public static void main(String[] args) {
        Client.startClient();
    }

    public static void startClient() {
        Socket sc = new Socket();
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8989);
        BufferedReader bufferedReader = null;
        BufferedReader input = null;
        PrintWriter printWriter = null;
        try {
            sc.connect(socketAddress, 1000);

            input = new BufferedReader(new InputStreamReader(System.in));

            bufferedReader = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            printWriter = new PrintWriter(sc.getOutputStream());
            boolean flag = true;
            while(flag){
                String tapWords = input.readLine();
                printWriter.println(tapWords);
                printWriter.flush();

                String responseFromServer = bufferedReader.readLine();
                if (responseFromServer.toLowerCase().contains("bye")){
                    flag = false;
                }
                System.out.println(responseFromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            printWriter.close();
        }
    }
}
