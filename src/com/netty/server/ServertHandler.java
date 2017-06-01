package com.netty.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * �з��صĸ��ͻ��˵Ĵ�����
 * 
 * @author twjitm
 *
 */
public class ServertHandler extends ChannelHandlerAdapter   {
	/**
	 * ����˽��յ����ݺ󷵻���Ϣ���ͻ��˵ķ�����
	 */
	@Override  
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        String body = (String) msg;  
        System.out.println("server"+body);//ǰ���Ѿ������˽���Ϊ�ַ���������ֱ�ӽ����ַ����Ϳ���  
        //����˸��ͻ��˵���Ӧ  
        String response= " hi client!$_";//���͵������Զ���������ַ�����β  
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));//���ͱ��뻹��ByteBuf����  
    }  
     /**
      * ���������ʱ����÷���
      */
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        System.out.println("����������~~~~~~"); 
    	cause.printStackTrace();  
          ctx.close();  
    }  
    
      @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	// TODO Auto-generated method stub
    	super.handlerAdded(ctx);
    }
      /**
       * �ͻ������ߺ�������
       */
      @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	// TODO Auto-generated method stub
    	super.channelActive(ctx);
    	System.out.println("��һ���ͻ���������");
    }
     
}
