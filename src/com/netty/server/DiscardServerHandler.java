package com.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
/**
 * ����˴���ͨ��.����ֻ�Ǵ�ӡһ����������ݣ���������������κε���Ӧ
 * DiscardServerHandler �̳��� ChannelHandlerAdapter��
 * �����ʵ����ChannelHandler�ӿڣ�
 * ChannelHandler�ṩ������¼�����Ľӿڷ�����
 * Ȼ������Ը�����Щ������
 * ���ڽ���ֻ��Ҫ�̳�ChannelHandlerAdapter����������Լ�ȥʵ�ֽӿڷ�����
 *
 */
public class DiscardServerHandler extends ChannelHandlerAdapter{
	/***
     * �������Ǹ�����chanelRead()�¼���������
     * ÿ���ӿͻ����յ��µ�����ʱ��
     * ������������յ���Ϣʱ�����ã�
     * ��������У��յ�����Ϣ��������ByteBuf
     * @param ctx ͨ���������������Ϣ
     * @param msg ���յ���Ϣ
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	System.out.println("discardserverhandler~~~~~~~~~~~");
        try {
           // ByteBuf in = (ByteBuf) msg;
            System.out.println(msg);
          /*  while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }*/
            //��һ�������ע�͵ĵ�Ч�����Ǵ�ӡ������ַ�
          //  System.out.println(in.toString(CharsetUtil.US_ASCII));
        }finally {
            /**
             * ByteBuf��һ�����ü�������������������ʾ�ص���release()�������ͷš�
             * ���ס��������ְ�����ͷ����д��ݵ������������ü�������
             */
            ReferenceCountUtil.release(msg);
        }
    }
    /***
     * ����������ڷ����쳣ʱ����
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        /***
         * �����쳣�󣬹ر�����
         */
        cause.printStackTrace();
        ctx.close();
    }

}
