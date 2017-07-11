package com.nettys.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2017/7/11.
 */
public class EchoServerHandler extends ChannelHandlerAdapter {
    private int constor = 0;

    /**
     * 客户端连接成功消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("------------------------------------");
        ctx.writeAndFlush(Unpooled.copiedBuffer("twj".getBytes()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        String body = (String) msg;
        System.out.println("num=" + constor + ",msg:" + msg);
        body += "$_";
        ByteBuf b = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(b);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
