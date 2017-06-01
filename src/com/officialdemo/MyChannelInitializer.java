package com.officialdemo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class MyChannelInitializer extends ChannelInitializer<Channel> {
                                          //ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的Channel
     @Override
     public void initChannel(Channel channel) {//处理类.处理接收到的数据
    	 /**
    	  * IdleStateHandler
    	  * 心跳连接
    	  */
         channel.pipeline().addLast(new IdleStateHandler(5, 5, 0)); //处理时间?  秒
         channel.pipeline().addLast(new MyHandler()); //读写?
         channel.pipeline().addLast(new Encoder());
         channel.pipeline().addLast(new Decoder());
         channel.pipeline().addLast(new DiscardServerHandler());
         
     }
 }