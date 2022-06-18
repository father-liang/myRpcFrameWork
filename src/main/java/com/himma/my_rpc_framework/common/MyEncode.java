package com.himma.my_rpc_framework.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class MyEncode extends MessageToByteEncoder {

    private Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object message, ByteBuf out)
            throws Exception {
        log.info("编码器接收到消息: " + message);

        if(message instanceof RpcRequest){
            out.writeShort(MessageType.REQUEST.getCode());
        }else if (message instanceof RpcResponse){
            out.writeShort(MessageType.RESPONSE.getCode());
        }

        out.writeShort(serializer.getType());

        byte[] serialize = serializer.serialize(message);
        out.writeInt(serialize.length);

        out.writeBytes(serialize);
        log.info("编码器成功编码信息");
    }
}
