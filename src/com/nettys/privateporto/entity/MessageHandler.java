package com.nettys.privateporto.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 协议头
 * Created by Administrator on 2017/7/12.
 */
public class MessageHandler {
    private int creCode = 0xabef0101;
    private int length;
    private long seeesonID;//回话od
    private byte type;
    private byte priority;//消息优先级
    private Map<String, Object> attachment = new ConcurrentHashMap<>();

    public int getCreCode() {
        return creCode;
    }

    public void setCreCode(int creCode) {
        this.creCode = creCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSeeesonID() {
        return seeesonID;
    }

    public void setSeeesonID(long seeesonID) {
        this.seeesonID = seeesonID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
}
