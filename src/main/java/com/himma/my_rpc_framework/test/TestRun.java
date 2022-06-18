package com.himma.my_rpc_framework.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TestRun implements Runnable {
    private Selector selector;
    SocketChannel socketChannel;

    public TestRun(Selector selector, SocketChannel socketChannel) {
        this.selector = selector;
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            while (selector.select()>0){
                System.out.println("有关注的事件发生......");
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isReadable()){
                        System.out.println("有可读事件发生");
                        //创建buffer实例，
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.clear();
                        int count = 0;
                        StringBuilder s = new StringBuilder();
                        //使用缓冲区将客户端发送的数据提取完毕。
                        while ((count = socketChannel.read(buffer))>0){
                            socketChannel.read(buffer);
                            System.out.println("循环接收");
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            String msg = new String(bytes);
                            s.append(msg);
                            buffer.clear();
                            buffer.put(msg.getBytes());
                            buffer.flip();
                            socketChannel.write(buffer);
                            buffer.clear();
                        }
                        System.out.println(s.toString());
                        if (count<0){
                            socketChannel.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
