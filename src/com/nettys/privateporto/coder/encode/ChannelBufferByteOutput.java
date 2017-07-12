package com.nettys.privateporto.coder.encode;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ChannelBufferByteOutput implements ByteOutput {
    private final ByteBuf byteBuf;

    public ChannelBufferByteOutput(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    @Override
    public void write(int i) throws IOException {

    }

    @Override
    public void write(byte[] bytes) throws IOException {

    }

    @Override
    public void write(byte[] bytes, int i, int i1) throws IOException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }
}
