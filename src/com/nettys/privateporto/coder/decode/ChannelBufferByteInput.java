package com.nettys.privateporto.coder.decode;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ChannelBufferByteInput implements ByteInput {
    private final ByteBuf buffer;

    public ChannelBufferByteInput(ByteBuf buffer) {
        this.buffer = buffer;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] bytes, int i, int i1) throws IOException {
        return 0;
    }

    @Override
    public int available() throws IOException {
        return 0;
    }

    @Override
    public long skip(long l) throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
