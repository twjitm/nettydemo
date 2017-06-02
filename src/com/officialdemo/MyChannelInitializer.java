package com.officialdemo;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
   private final EventExecutorGroup ececutor=new DefaultEventExecutor();
	
	
	//ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的Channel
     @Override
     public void initChannel(SocketChannel channel) {//处理类.处理接收到的数据
    	 /**
    	  * IdleStateHandler
    	  * 心跳连接
    	  */
        channel.pipeline().addLast(new IdleStateHandler(5, 0, 0,TimeUnit.SECONDS)); //处理时间?  秒
    	// channel.pipeline().addLast(new DiscardServerHandler());
    	 channel.pipeline().addLast(new MyHandler()); //读写?
         channel.pipeline().addLast(new Encoder());
         channel.pipeline().addLast(new Decoder());
         channel.pipeline().addLast(new DiscardServerHandler());
         channel.pipeline().addLast(ececutor);
         
     }
 }