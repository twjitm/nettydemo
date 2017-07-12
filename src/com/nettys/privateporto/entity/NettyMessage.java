  package com.nettys.privateporto.entity;

/**
 * 协议实体
 * Created by Administrator on 2017/7/12.
 */
public class NettyMessage {
    private MessageHandler handler;
    private Object body;

    public MessageHandler getHandler() {
        return handler;
    }

    public void setHandler(MessageHandler handler) {
        this.handler = handler;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
