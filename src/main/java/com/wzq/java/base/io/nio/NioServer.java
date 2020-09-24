package com.wzq.java.base.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) {

        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.socket().bind(new InetSocketAddress(9090));

            boolean run = true;
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
            while (run) {

                SocketChannel channel = serverChannel.accept();
                channel.configureBlocking(false);
                new Thread(() -> {
                    try {
                        // todo  多个channel复用一个
                        channel.read(byteBuffer);
                        while (run) {
                            //todo
                        }

                    } catch (Exception e) {

                    }
                });
            }

        } catch (Exception e) {

        }
    }
}
