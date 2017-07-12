package com.nettys.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 编码器
 * Created by Administrator on 2017/7/12.
 */
public class MessageDecoder<T> extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final byte[] bytes;
        final int length = byteBuf.readableBytes();
        bytes = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(), bytes, 0, length);
        MessagePack pack = new MessagePack();
        list.add(pack.read(bytes));

    }
}
