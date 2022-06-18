package com.himma.my_rpc_framework.client;

import com.himma.my_rpc_framework.common.JSONSerializer;
import com.himma.my_rpc_framework.common.MyDecode;
import com.himma.my_rpc_framework.common.MyEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new MyDecode());
        pipeline.addLast(new MyEncode(new JSONSerializer()));
        pipeline.addLast(new NettyClientHandler());
    }
}
