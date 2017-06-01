package com.officialdemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Decoder extends ByteToMessageDecoder{
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
    	try {
//    		System.out.println("---- decode ----");
    		if (buf.readableBytes() < 4) {
    			return;
    		}
    		int lenght = buf.readInt();
//    		System.out.println("decode length -- "+lenght);
    		byte[] tmp = new byte[lenght];
    		buf.readBytes(tmp);
            Object obj = ByteObjConverter.ByteToObject(tmp);  
            out.add(obj);  
		} catch (Exception e) {
			System.out.println("---Decode Error---");
		}
    }
}
