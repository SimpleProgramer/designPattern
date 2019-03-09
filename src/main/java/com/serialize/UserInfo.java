package com.serialize;

import lombok.Data;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author wangzun
 * @version 2019/3/8 下午4:55
 * @desc
 */
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 7003025077461383509L;

    private String username;

    private int userID;

    public UserInfo buildUserID(int userID) {
        this.userID = userID;
        return this;
    }
    public UserInfo buildUsername(String username) {
        this.username= username;
        return this;
    }

    public byte[] codeC() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] value = this.username.getBytes();
        byteBuffer.putInt(value.length);
        byteBuffer.put(value);
        byteBuffer.putInt(this.userID);
        byteBuffer.flip();
        value = null;
        byte[] result = new byte[byteBuffer.remaining()];
        return result;
    }
    public byte[] codeC(ByteBuffer byteBuffer) {
        byteBuffer.clear();
        byte[] value = this.username.getBytes();
        byteBuffer.putInt(value.length);
        byteBuffer.put(value);
        byteBuffer.putInt(this.userID);
        byteBuffer.flip();
        value = null;
        byte[] result = new byte[byteBuffer.remaining()];
        return result;
    }
}
