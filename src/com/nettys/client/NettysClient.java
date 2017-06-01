package com.nettys.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * netty ¿Í»§¶ËÑ§Ï°³ÌÐò
 * @author twjitm
 *
 */
public class NettysClient {
	public static void main(String[] args)  {
		EventLoopGroup group=new NioEventLoopGroup();
		Bootstrap bootstrap =new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(
				new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel  channel) throws Exception {
						ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());   
						channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));  
					//	channel.pipeline().addLast(new StringDecoder());  ×Ö·û´®±àÂë
						channel.pipeline().addLast(new ObjectEncoder());
						channel.pipeline().addLast(new ClientHandler()); 
					}
		});
		ChannelFuture channelFuture;
		try {
			channelFuture = bootstrap.connect("127.0.0.1", 8765).sync();
			channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("frffff".getBytes()));
			channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(" hi server4$_".getBytes()));
			channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(" hi server3$_".getBytes())); 
			channelFuture.channel().closeFuture().sync();  
		} catch (InterruptedException e) {
			System.err.println("error~~~~~");
			e.printStackTrace();
		}finally{
			group.shutdownGracefully();  
		}
	}
}
