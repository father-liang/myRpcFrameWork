package com.himma.my_rpc_framework.server;

import com.himma.my_rpc_framework.service.ServiceProvider;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyRpcServer extends BasicPpcServer implements RPCServer {

    public NettyRpcServer(ServiceProvider serviceProvider) {
        super(serviceProvider);
    }

    public NettyRpcServer(ServiceProvider serviceProvider,
            int corePoolSize,
            int maxPoolSize,
            int keepAliveTime,
            TimeUnit timeUnit,
            BlockingQueue<Runnable> blockingQueue) {
        super(serviceProvider, corePoolSize, maxPoolSize, keepAliveTime, timeUnit, blockingQueue);
    }

    @Override
    public void start(int port) {
        log.info("服务端启动了");

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyServerInitializer(this.getServiceProvider()));

        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }

    @Override
    public void stop() {

    }
}
