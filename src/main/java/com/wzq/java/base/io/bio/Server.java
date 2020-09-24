package com.wzq.java.base.io.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: create by wangzq
 * @version: v1.0
 * @date:2020/8/9 23:56
 */
public class Server {
    public static void main(String[] args) {
        Server.startServer();
    }


    public static void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(8989);
            for (; ;) {
                Socket sc = serverSocket.accept();
                new Thread(new ServerSocketHandler(sc)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ServerSocketHandler implements Runnable {
        Socket socket;
        PrintWriter writer;
        BufferedReader reader;

        public ServerSocketHandler(Socket socket) {
            this.socket = socket;
            try {
                writer = new PrintWriter(socket.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String receiveMsg = null;
            try {
                boolean flag = true;
                while (flag) {
                    receiveMsg = reader.readLine();
                    System.out.println(receiveMsg);
                    MsgType requestType = handleMsgGetType(receiveMsg);
                    writer.println("你发出的消息属于：".concat(requestType.name().concat(" 类型")));
                    writer.flush();
                    if (requestType == MsgType.FAREWELL) {
                        flag = false;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    writer.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        MsgType handleMsgGetType(String receiveMsg) {
            if (receiveMsg.toLowerCase().contains("hello") || receiveMsg.contains("你好") || receiveMsg.toLowerCase().contains("hi")) {
                return MsgType.GREETING;
            }
            if (receiveMsg.contains("请问") || receiveMsg.contains("知道吗") || receiveMsg.contains("是什么")) {
                return MsgType.QUESTION;
            }
            if (receiveMsg.contains("想要") || receiveMsg.contains("多少钱") || receiveMsg.contains("怎么卖")) {
                return MsgType.BUY;
            }
            if (receiveMsg.toLowerCase().contains("bye") || receiveMsg.contains("再见") || receiveMsg.contains("拜拜")) {
                return MsgType.FAREWELL;
            }
            return MsgType.UNKNOWN;
        }
    }

    enum MsgType {
        GREETING, QUESTION, BUY, FAREWELL, UNKNOWN;
    }
}
