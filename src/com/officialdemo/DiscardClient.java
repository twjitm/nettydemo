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

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class DiscardClient {

    private final String host;
    private final int port;
    private Object msg;
    public DiscardClient(String host, int port, int firstMessageSize) {
        this.host = host;
        this.port = port;
    }
     public DiscardClient(String host, int port, Object msg) {
        this.host = host;
        this.port = port;
        this.msg=msg;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                	 ch.pipeline().addLast(new Encoder());
                	 ch.pipeline().addLast(new Decoder());
                     ch.pipeline().addLast(new DiscardClientHandler(msg));
                 }
             });

            // Make the connection attempt.
            ChannelFuture f = b.connect(host, port).sync();
            
            System.out.println("==== client start ====");
            // Wait until the connection is closed.
            f.channel().closeFuture().sync(); //涓嶆敞閲婂氨鍙兘鍙戦�涓�?
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
//        new DiscardClient("127.0.0.1", 8089, 256).run();
         User user= new User();
         user.setUsername("https://twjitm.github.io");
         new DiscardClient("127.0.0.1", 8089, user).run();
    }
}
