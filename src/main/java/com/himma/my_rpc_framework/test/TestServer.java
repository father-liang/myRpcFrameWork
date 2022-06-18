package com.himma.my_rpc_framework.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestServer {
    public static void main(String[] args) throws IOException {
        //1、创建ServerSocketChannel.open();实例
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(5555));
        System.out.println("服务端启动");
        // 注册选择器
        Selector selector = Selector.open();
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //将其注册到选择器上
        //让选择器帮忙监听事件，有事件就返回，没有事件就不返回
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        while (selector.select() > 0){
            System.out.println("有关注的事件发生");
            //这是关注的事件集合
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()){
                    System.out.println("有可接收的事件发生");
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    //多线程操作
                    executorService.execute(new TestRun(Selector.open(),socketChannel));
                }
            }
        }
    }
}
