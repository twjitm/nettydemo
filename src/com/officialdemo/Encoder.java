package com.officialdemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encoder extends MessageToByteEncoder<Object>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		try {
//			System.out.println("---- encode ----");
	        byte[] datas = ByteObjConverter.ObjectToByte(msg);  
	        out.writeInt(datas.length);//长度
//	        System.out.println("encode length -- "+datas.length);
	        out.writeBytes(datas);  
	        ctx.flush(); 
		} catch (Exception e) {
			System.out.println("----Encode Error----");
		}
	}
	
}
