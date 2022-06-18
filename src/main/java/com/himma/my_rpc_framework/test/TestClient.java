package com.himma.my_rpc_framework.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) throws IOException {
//        创建实例SocketChannel.open();
        SocketChannel socketChannel = SocketChannel.open();

//        将SocketChannel实例设置为非阻塞
        socketChannel.configureBlocking(false);
//        创建selector实例
        Selector selector = Selector.open();

//        连接服务器
        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1",5555))){
            System.out.println("连接为完成，注册到选择器当中");
            //        将Selector注册到选择器里面,并关注OP_CONNECT事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
//            监听感兴趣的事件是否完成
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                System.out.println(">>>>"+selector.selectedKeys().size());
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isValid()&&key.isConnectable()){
                    System.out.println("可连接事件完成");
                    SocketChannel channel = (SocketChannel) key.channel();

                    if (channel.isConnectionPending())
                        channel.finishConnect();
                    System.out.println("客户端连接成功");
                    channel.configureBlocking(false);

                    channel.register(selector,SelectionKey.OP_READ);
                }
            }
        }
//        连接完成进行读写操作
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("请输入：");
            String msg = scanner.nextLine();
            if ("exit".equals(msg)){
                break;
            }
//          向缓存写数据
            byteBuffer.put(msg.getBytes());
            byteBuffer.flip();
//        发送数据
            socketChannel.write(byteBuffer);
//        缓存清空
            byteBuffer.clear();
//        监听读事件，没有则阻塞
            selector.select();
            socketChannel.read(byteBuffer);
//            读写模式切换
            byteBuffer.flip();
//            通过缓冲区有效元素大小确定数组大小
            byte[] bytes1 = new byte[byteBuffer.remaining()];
//            从缓冲区读数据
            byteBuffer.get(bytes1);
            String msg1 = new String(bytes1);
            System.out.println("[recv]:"+msg1);
            byteBuffer.clear();

        }

        System.out.println("客户端关闭");
        selector.close();
        socketChannel.close();
    }
}
