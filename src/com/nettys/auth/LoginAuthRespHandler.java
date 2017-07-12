package com.nettys.auth;

import com.nettys.privateporto.entity.MessageHandler;
import com.nettys.privateporto.entity.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/7/12.
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    private String[] whiteList = new String[]{"127.0.0.1", "192.168.0.50"};
    private Map<String, Object> chaeckmao = new ConcurrentHashMap<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        NettyMessage message = (NettyMessage) msg;
        if (message.getHandler() != null && message.getHandler().getType() == MessageType.LOGIN_MESSAGE) {
            String nodeIndsex = ctx.channel().remoteAddress().toString();
            NettyMessage respmsg = null;
            if (chaeckmao.containsKey(nodeIndsex)) {
                respmsg = bingObject((byte) -1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isok = false;
                for (String wip : whiteList) {
                    if (wip.equals(ip)) {
                        isok = true;
                        break;
                    }
                }
                respmsg = isok ? bingObject((byte) 0) : bingObject((byte) -1);
                if (isok) {
                    chaeckmao.put(nodeIndsex, respmsg);
                }
                ctx.writeAndFlush(chaeckmao);
            }
        } else {
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = address.getAddress().getHostAddress();
        chaeckmao.remove(ip);
        ctx.close();
        ctx.fireChannelRead(cause);
    }

    private NettyMessage bingObject(byte b) {
        NettyMessage message = new NettyMessage();
        MessageHandler handler = new MessageHandler();
        handler.setType((byte) MessageType.LOGIN_MESSAGE);
        message.setHandler(handler);
        return message;
    }
}
