package com.himma.my_rpc_framework.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class MyDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out)
            throws Exception {
        log.info("解码器接受到消息：" + byteBuf);

        short messageType = byteBuf.readShort();

        if (messageType != MessageType.REQUEST.getCode() && messageType != MessageType.RESPONSE.getCode()){
            log.error("不支持此类消息类型");
            return;
        }

        Serializer serializer = Serializer.getSerializerByCode(byteBuf.readShort());

        if (serializer == null) throw new RuntimeException("不存在相应的序列器");

        byte[] bytes = new byte[byteBuf.readInt()];

        byteBuf.readBytes(bytes);

        out.add(serializer.deserialize(bytes, messageType));
    }
}
