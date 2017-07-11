package com.nettys.serializable;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/7/11.
 */
public class User implements Serializable {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 编码
     *
     * @return
     */
    public ByteBuffer edcode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] val = this.getName().getBytes();
        byteBuffer.putInt(val.length);
        byteBuffer.put(val);
        byteBuffer.putInt(this.getId());
        byteBuffer.flip();
        val = null;
        byte[] bytes = new byte[byteBuffer.remaining()];
        ByteBuffer result = byteBuffer.get(bytes);
        return result;
    }
}
