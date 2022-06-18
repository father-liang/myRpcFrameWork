package com.himma.my_rpc_framework.server;

import com.himma.my_rpc_framework.common.JSONSerializer;
import com.himma.my_rpc_framework.common.MyDecode;
import com.himma.my_rpc_framework.common.MyEncode;
import com.himma.my_rpc_framework.service.ServiceProvider;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new MyDecode());
        pipeline.addLast(new MyEncode(new JSONSerializer()));

        pipeline.addLast(new NettyRpcServerHandler(serviceProvider));
    }
}
