package com.officialdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {

    private final int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();//bossGroup，用来接收进来的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup(200); // workerGroup  用来处理已接收的连接
                                       //NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器
        try {
            ServerBootstrap b = new ServerBootstrap(); //创建服务端
            b.group(bossGroup, workerGroup)   //一旦 bossGroup接收到连接，就会把连接信息注册到workerGroup上
             .channel(NioServerSocketChannel.class)//添加协议接收类 TCP 
             .childHandler(new MyChannelInitializer()).option(
            		 ChannelOption.SO_BACKLOG, 1024).option(
            				 ChannelOption.SO_KEEPALIVE, true);//接收到数据,调用MyChannelInitializer
            ChannelFuture f = b.bind(port);
            System.out.println("==== server start ====");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new DiscardServer(8089).run();
    }
}
