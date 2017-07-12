package com.nettys.messagepack.server;

import com.nettys.messagepack.MessageDecoder;
import com.nettys.messagepack.MessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * Created by Administrator on 2017/7/12.
 */
public class MessageServer {
    private void run() {
        EventLoopGroup domain = new NioEventLoopGroup();
        EventLoopGroup works = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(domain, works);
        bootstrap.option(ChannelOption.SO_BACKLOG, 100);
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.localAddress("127.0.0.1", 8080);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {


                socketChannel.pipeline().addLast("msgpack encoder", new MessageEncoder());
                socketChannel.pipeline().addLast("mdgpack decoder", new MessageDecoder<>());
                socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536, 0, 2, 0, 2));
                socketChannel.pipeline().addLast(new LengthFieldPrepender(2));
                socketChannel.pipeline().addLast(new MessageServerHandler());
            }
        });
        try {
            ChannelFuture channelFuture = bootstrap.bind("127.0.0.1", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            domain.shutdownGracefully();
            works.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new MessageServer().run();
    }
}
