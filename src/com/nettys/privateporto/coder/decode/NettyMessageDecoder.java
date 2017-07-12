package com.nettys.privateporto.coder.decode;

import com.nettys.privateporto.coder.MarshallingCodeCFactory;
import com.nettys.privateporto.entity.MessageHandler;
import com.nettys.privateporto.entity.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息编码器
 * Created by twjitm on 2017/7/12.
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
    MarshallingDecoder marshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        try {
            marshallingDecoder = new MarshallingDecoder();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf fram = (ByteBuf) super.decode(ctx, in);
        if (fram == null) {
            return null;
        }
        NettyMessage message = new NettyMessage();
        MessageHandler handler = new MessageHandler();
        handler.setCreCode(in.readInt());
        handler.setLength(in.readInt());
        handler.setSeeesonID(in.readLong());
        handler.setType(in.readByte());
        handler.setPriority(in.readByte());
        int size = in.readInt();
        if (size > 0) {
            Map<String, Object> att = new HashMap<>(size);
            int keysize = 0;
            byte[] keyarray = null;
            String key = null;
            for (int i = 0; i < size; i++) {
                keysize = in.readInt();
                keyarray = new byte[keysize];
                in.readBytes(keyarray);
                key = new String(keyarray, "utf-8");
                att.put(key, marshallingDecoder.decode(in));
            }
            keyarray = null;
            key = null;
            handler.setAttachment(att);
            if (in.readableBytes() > 4) {
                message.setBody(marshallingDecoder.decode(in));
            }
            message.setHandler(handler);
        }
        return message;
    }


    public class MarshallingDecoder {
        private final Unmarshaller unmarshaller;

        /**
         * Creates a new decoder whose maximum object size is {@code 1048576} bytes.
         * If the size of the received object is greater than {@code 1048576} bytes,
         * a {@link StreamCorruptedException} will be raised.
         *
         * @throws IOException
         */
        public MarshallingDecoder() throws IOException {
            unmarshaller = MarshallingCodeCFactory.buildUnmarshaller();
        }

        protected Object decode(ByteBuf in) throws Exception {
            //1. 读取第一个4bytes，里面放置的是object对象的byte长度
            int objectSize = in.readInt();
            ByteBuf buf = in.slice(in.readerIndex(), objectSize);
            //2 . 使用bytebuf的代理类
            ByteInput input = new ChannelBufferByteInput(buf);
            try {
                //3. 开始解码
                unmarshaller.start(input);
                Object obj = unmarshaller.readObject();
                unmarshaller.finish();
                //4. 读完之后设置读取的位置
                in.readerIndex(in.readerIndex() + objectSize);
                return obj;
            } finally {
                unmarshaller.close();
            }
        }
    }

}
