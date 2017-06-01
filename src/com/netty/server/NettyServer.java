package com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer {
	public static void main(String[] args) {
		 /***
         * NioEventLoopGroup ����������I/O�����Ķ��߳��¼�ѭ������
         * Netty�ṩ����಻ͬ��EventLoopGroup��ʵ����������ͬ����Э�顣
         * ���������������ʵ����һ������˵�Ӧ�ã�
         * ��˻���2��NioEventLoopGroup�ᱻʹ�á�
         * ��һ��������������boss�����������ս��������ӡ�
         * �ڶ���������������worker�������������Ѿ������յ����ӣ�
         * һ����boss�����յ����ӣ��ͻ��������Ϣע�ᵽ��worker���ϡ�
         * ���֪�����ٸ��߳��Ѿ���ʹ�ã����ӳ�䵽�Ѿ�������Channels�϶���Ҫ������EventLoopGroup��ʵ�֣�
         * ���ҿ���ͨ�����캯�����������ǵĹ�ϵ��
         */
		EventLoopGroup bossgroup=new NioEventLoopGroup();
		EventLoopGroup workgroup=new NioEventLoopGroup();
		  /**
         * ServerBootstrap ��һ������NIO����ĸ���������
         * ����������������ֱ��ʹ��Channel
         */
		ServerBootstrap bootstrap=new ServerBootstrap();
		  /**
         * ��һ���Ǳ���ģ����û������group���ᱨjava.lang.IllegalStateException: group not set�쳣
         */
		 /***
         * ServerSocketChannel��NIO��selectorΪ��������ʵ�ֵģ����������µ�����
         * �������Channel��λ�ȡ�µ�����.
         */
		bootstrap.group(bossgroup,workgroup).channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);//tcp������
		bootstrap.option(ChannelOption.SO_SNDBUF, 32*1024);//���÷��ͻ�����
		bootstrap.option(ChannelOption.SO_RCVBUF, 32*1024);//���ý��ջ�������С  
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);//��������  
		 /***
         * ������¼������ྭ���ᱻ��������һ��������Ѿ����յ�Channel��
         * ChannelInitializer��һ������Ĵ����࣬
         * ����Ŀ���ǰ���ʹ��������һ���µ�Channel��
         * Ҳ������ͨ������һЩ���������NettyServerHandler������һ���µ�Channel
         * �������Ӧ��ChannelPipeline��ʵ������������
         * ����ĳ����ĸ���ʱ������������Ӹ���Ĵ����ൽpipline�ϣ�
         * Ȼ����ȡ��Щ�����ൽ�������ϡ�
         */
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());//���ճ����������ַ�������һ�ֽ��������  
                sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));//�ڹܵ��м�������ַ���  
            //  sc.pipeline().addLast(new FixedLengthFrameDecoder(200));�ڶ��ֶ���  
                sc.pipeline().addLast(new StringDecoder());//�����������Ϊ�ַ�����ByteBufת��String  
              sc.pipeline().addLast(new ServertHandler());//���������þ������ݽ��շ����Ĵ���  
              //sc.pipeline().addLast(new ResponseServerHandler());
                //sc.pipeline().addLast(new TimeServerHandler());
				
			}
		});
		
		 /***
         * �������������ָ����ͨ��ʵ�ֵ����ò�����
         * ��������дһ��TCP/IP�ķ���ˣ�
         * ������Ǳ���������socket�Ĳ���ѡ�����tcpNoDelay��keepAlive��
         * ��ο�ChannelOption����ϸ��ChannelConfigʵ�ֵĽӿ��ĵ��Դ˿��Զ�ChannelOptions����һ����ŵ���ʶ��
         */
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		 ChannelFuture future;
		try {
			future = bootstrap.bind(8765).sync();
			 future.channel().closeFuture().sync();//�ȴ��ر�(��������������ȴ��ͻ�������)  
		} catch (Exception e) {
			e.printStackTrace();
		}//�󶨶˿�  
		finally{
			 bossgroup.shutdownGracefully();//�ر��߳�  
		     workgroup.shutdownGracefully();//�ر��߳�  
		}
	}
	

}
