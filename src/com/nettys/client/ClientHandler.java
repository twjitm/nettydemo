package com.nettys.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter {
	/**
	 * �ͻ��˷���ǰ
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.handlerAdded(ctx);
		System.out.println("``````````````");
	}
	/**
	 * ���������ص�����
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
		System.out.println("�ͻ����յ����Է����ת�������ݣ�");
	        System.out.println(msg);
	}
	 
	
	

}
