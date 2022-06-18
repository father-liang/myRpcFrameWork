package com.himma.my_rpc_framework.client;

import com.himma.my_rpc_framework.common.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NettyRpcClient extends BasicRpcClient implements RpcClient {

    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;
    private ServiceRegister serviceRegister;
    private LoadBalance loadBalance;


    public NettyRpcClient(LoadBalance loadBalance) {
        this.serviceRegister = new ZKServiceRegister(loadBalance);
    }

    static {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    @Override
    public RpcResponse sendRequest(RpcRequest request) {
        InetSocketAddress inetSocketAddress = serviceRegister.serviceDiscovery(request.getInterfaceName());
        this.setHost(inetSocketAddress.getHostName());
        this.setPort(inetSocketAddress.getPort());
        try {
            ChannelFuture future = bootstrap.connect(getHost(), getPort()).sync();
            Channel channel = future.channel();
            channel.writeAndFlush(request);
            channel.closeFuture().sync();

            AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
            RpcResponse rpcResponse = channel.attr(key).get();
            log.info("接收到返回消息：" + rpcResponse.getData().toString());

            return rpcResponse;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
