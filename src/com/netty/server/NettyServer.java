package com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer {
	public static void main(String[] args) {
		 /***
         * NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
         * Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议。
         * 在这个例子中我们实现了一个服务端的应用，
         * 因此会有2个NioEventLoopGroup会被使用。
         * 第一个经常被叫做‘boss’，用来接收进来的连接。
         * 第二个经常被叫做‘worker’，用来处理已经被接收的连接，
         * 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
         * 如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，
         * 并且可以通过构造函数来配置他们的关系。
         */
		EventLoopGroup bossgroup=new NioEventLoopGroup();
		EventLoopGroup workgroup=new NioEventLoopGroup();
		  /**
         * ServerBootstrap 是一个启动NIO服务的辅助启动类
         * 你可以在这个服务中直接使用Channel
         */
		ServerBootstrap bootstrap=new ServerBootstrap();
		  /**
         * 这一步是必须的，如果没有设置group将会报java.lang.IllegalStateException: group not set异常
         */
		 /***
         * ServerSocketChannel以NIO的selector为基础进行实现的，用来接收新的连接
         * 这里告诉Channel如何获取新的连接.
         */
		bootstrap.group(bossgroup,workgroup).channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);//tcp缓冲区
		bootstrap.option(ChannelOption.SO_SNDBUF, 32*1024);//设置发送缓冲区
		bootstrap.option(ChannelOption.SO_RCVBUF, 32*1024);//设置接收缓冲区大小  
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);//保持连续  
		 /***
         * 这里的事件处理类经常会被用来处理一个最近的已经接收的Channel。
         * ChannelInitializer是一个特殊的处理类，
         * 他的目的是帮助使用者配置一个新的Channel。
         * 也许你想通过增加一些处理类比如NettyServerHandler来配置一个新的Channel
         * 或者其对应的ChannelPipeline来实现你的网络程序。
         * 当你的程序变的复杂时，可能你会增加更多的处理类到pipline上，
         * 然后提取这些匿名类到最顶层的类上。
         */
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());//拆包粘包定义结束字符串（第一种解决方案）  
                sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));//在管道中加入结束字符串  
            //  sc.pipeline().addLast(new FixedLengthFrameDecoder(200));第二种定长  
                sc.pipeline().addLast(new StringDecoder());//定义接收类型为字符串把ByteBuf转成String  
              sc.pipeline().addLast(new ServertHandler());//在这里配置具体数据接收方法的处理  
              //sc.pipeline().addLast(new ResponseServerHandler());
                //sc.pipeline().addLast(new TimeServerHandler());
				
			}
		});
		
		 /***
         * 你可以设置这里指定的通道实现的配置参数。
         * 我们正在写一个TCP/IP的服务端，
         * 因此我们被允许设置socket的参数选项比如tcpNoDelay和keepAlive。
         * 请参考ChannelOption和详细的ChannelConfig实现的接口文档以此可以对ChannelOptions的有一个大概的认识。
         */
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		 ChannelFuture future;
		try {
			future = bootstrap.bind(8765).sync();
			 future.channel().closeFuture().sync();//等待关闭(程序阻塞在这里等待客户端请求)  
		} catch (Exception e) {
			e.printStackTrace();
		}//绑定端口  
		finally{
			 bossgroup.shutdownGracefully();//关闭线程  
		     workgroup.shutdownGracefully();//关闭线程  
		}
	}
	

}
