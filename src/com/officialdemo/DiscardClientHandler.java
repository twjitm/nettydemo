/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.officialdemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a client-side channel.
 */
public class DiscardClientHandler extends SimpleChannelInboundHandler<Object> {
    private Object msg;
public DiscardClientHandler(Object msg){
        this.msg=msg;
    }
    /**
     * 在没有encode , decoder的时候,netty默认的数据传输格式是ByteBuf,其他格式都不合法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {//连接建立准备通信时被调用
         ctx.writeAndFlush(msg);
		//ctx.writeAndFlush(ctx, promise);
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx)throws Exception{//连接断开时调用。
    	System.out.println("----  Client Channel Inactive/Connection Close ----");
    	
    	ctx.channel().close();
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {//接收服务端发送来的信息
    System.out.println("--- 服务器返回的消息 ---");
   	User u = (User) msg;
    	System.out.println("message is " + u.getUsername());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {//发生异常后调用
        ctx.close();
    }
    /**
     * 心跳网络
     * 当客户端在指定时间内没有io操作触发的方法
     * userEventTriggered
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    	super.userEventTriggered(ctx, evt);
    }

    
}
