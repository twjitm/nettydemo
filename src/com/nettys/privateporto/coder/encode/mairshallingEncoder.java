package com.nettys.privateporto.coder.encode;

import com.nettys.privateporto.coder.MarshallingCodeCFactory;
import com.nettys.privateporto.entity.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 消息解码器
 * Created by Administrator on 2017/7/12.
 */
public class NettyMessageEnCoder extends MessageToMessageEncoder<NettyMessage> {
    MairshallingEncoder mairshallingEncoder;

    public NettyMessageEnCoder() {
        this.mairshallingEncoder = new MairshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage, List<Object> list) throws Exception {
        if (nettyMessage == null || nettyMessage.getHandler() == null)
            throw new NullPointerException("协议头为空");
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(nettyMessage.getHandler().getCreCode());
        byteBuf.writeInt(nettyMessage.getHandler().getLength());
        byteBuf.writeLong(nettyMessage.getHandler().getSeeesonID());
        byteBuf.writeByte(nettyMessage.getHandler().getType());
        byteBuf.writeByte(nettyMessage.getHandler().getPriority());
        byteBuf.writeInt(nettyMessage.getHandler().getAttachment().size());
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> entry : nettyMessage.getHandler().getAttachment().entrySet()) {
            key = entry.getKey();
            keyArray = key.getBytes("utf-8");
            value = entry.getValue();
            if (null != nettyMessage.getBody()) {
                mairshallingEncoder.encode(nettyMessage.getBody(), byteBuf);
            } else {
                byteBuf.writeInt(0);
                byteBuf.setInt(4, byteBuf.readableBytes());
            }
        }

    }

    public static class MairshallingEncoder {
        private static final byte[] LENGTH_PLACD = new byte[4];
        Marshaller marshaller;

        public MairshallingEncoder() {
            marshaller = MarshallingCodeCFactory.buildMarshalling();
        }

        public void encode(Object msg, ByteBuf out) {
            int outIndex = out.readerIndex();
            out.writeBytes(LENGTH_PLACD);
            ChannelBufferByteOutput buffer = new ChannelBufferByteOutput(out);
            try {
                marshaller.start(buffer);
                marshaller.writeObject(msg);
                marshaller.finish();
                out.setIndex(outIndex, out.writerIndex() - outIndex - 4);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    marshaller.finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
