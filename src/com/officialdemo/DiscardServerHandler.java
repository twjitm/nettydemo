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
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { //连接建立时发送给客户端的消息
//        User u =new User();
//        u.setUsername("this is Server");
//        ctx.writeAndFlush(u);
////    	ctx.channel().writeAndFlush(u));
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx)throws Exception{//连接断开时调用?
    	System.out.println("---- Channel Inactive /Connection Close----");
    	ctx.channel().close();
    }
    
	@Override
    public void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {//接收客户端发送来的消息
//        System.out.println("--- Server messageReceived ---");
    	User u = (User) msg;
    	System.out.println("message is " + u.getUsername());
    	u.setUsername("twjitm");
    	ctx.writeAndFlush(u);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("----------Exception-----------");
    }
}
