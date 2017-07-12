package com.nettys.messagepack.client;

import com.nettys.messagepack.MessageDecoder;
import com.nettys.messagepack.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by Administrator on 2017/7/12.
 */
public class MessageClient {
    private static int port;
    private static String host;
    private static int sendNumber;

    public MessageClient(int port, String host, int sendNumber) {
        this.port = port;
        this.host = host;
        this.sendNumber = sendNumber;
    }

    public static void main(String[] args) {
        new MessageClient(8080, "127.0.0.1", 5).run();

    }

    public void run() {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, false);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                channel.pipeline().addLast(new StringDecoder());
                channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536, 0, 2, 0, 2));
                channel.pipeline().addLast("msgpack decoder", new MessageDecoder());
                channel.pipeline().addLast(new LengthFieldPrepender(2));
                channel.pipeline().addLast("msgpack encoder", new MessageEncoder());

                channel.pipeline().addLast(new MessageClientHandler(sendNumber));
            }
        });
        try {
            ChannelFuture f = bootstrap.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
