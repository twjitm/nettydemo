package com.netty.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * 有返回的给客户端的处理器
 * 
 * @author twjitm
 *
 */
public class ServertHandler extends ChannelHandlerAdapter   {
	/**
	 * 服务端接收到数据后返回消息给客户端的方法。
	 */
	@Override  
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        String body = (String) msg;  
        System.out.println("server"+body);//前面已经定义了接收为字符串，这里直接接收字符串就可以  
        //服务端给客户端的响应  
        String response= " hi client!$_";//发送的数据以定义结束的字符串结尾  
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));//发送必须还是ByteBuf类型  
    }  
     /**
      * 发生错误的时候调用方法
      */
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        System.out.println("发生错误了~~~~~~"); 
    	cause.printStackTrace();  
          ctx.close();  
    }  
    
      @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	// TODO Auto-generated method stub
    	super.handlerAdded(ctx);
    }
      /**
       * 客户端上线函数调用
       */
      @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	// TODO Auto-generated method stub
    	super.channelActive(ctx);
    	System.out.println("有一个客户端上线了");
    }
     
}
