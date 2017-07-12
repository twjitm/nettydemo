   package com.nettys.auth;

import com.nettys.privateporto.entity.MessageHandler;
import com.nettys.privateporto.entity.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2017/7/12.
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush(reqNettyMessage());
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        NettyMessage message = (NettyMessage) msg;
        if (message.getHandler() != null && message.getHandler().getType() == MessageType.LOGIN_MESSAGE) {
            byte loginresult = (byte) message.getBody();
            if (loginresult != (byte) 0) {
                ctx.close();
            } else {
                System.out.println("login is ok");
                ctx.fireChannelRead(message);
            }
        } else {
            ctx.fireChannelRead(message);
        }
    }

    private NettyMessage reqNettyMessage() {
        NettyMessage message = new NettyMessage();
        MessageHandler handler = new MessageHandler();
        handler.setType((byte) MessageType.LOGIN_MESSAGE);
        message.setHandler(handler);
        return message;
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
