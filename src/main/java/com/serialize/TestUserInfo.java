package com.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author wangzun
 * @version 2019/3/8 下午5:02
 * @desc
 */
public class TestUserInfo {

    public static void main(String[] args) throws IOException {
        UserInfo user = new UserInfo();
        user.buildUserID(100).buildUsername("Welcome to Netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(user);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("jdk的序列化长度:" + b.length);
        bos.close();
        System.out.println("----------------------------------------------");
        System.out.println("通过ByteBuffer序列化的长度:" + user.codeC().length);
        System.out.println("测试结果:jdk序列化长度远大于ByteBuffer长度。");
    }
}
