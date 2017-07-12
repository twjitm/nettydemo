package com.nettys.messagepack.client;

import com.nettys.serializable.User;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2017/7/12.
 */
public class MessageClientHandler extends ChannelHandlerAdapter {
    private static int sendNunber;

    public MessageClientHandler(int sendNumber) {
        this.sendNunber = sendNumber;
    }

    /**
     * 链路接通是发送数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client activity");
        User[] users = getUsers();
        for (User u : users) {
            ctx.writeAndFlush(u);
        }

    }

    private User[] getUsers() {
        System.out.println("sendNunber=" + sendNunber);
        User[] users = new User[sendNunber];
        User user = null;

        for (int i = 0; i < this.sendNunber; i++) {
            user = new User();
            user.setId(i);
            user.setName("twj" + i);
            users[i] = user;
        }
        return users;
    }

    /**
     * 读取客户端返回过来的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
