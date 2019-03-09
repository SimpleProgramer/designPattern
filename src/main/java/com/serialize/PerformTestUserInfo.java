package com.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @author wangzun
 * @version 2019/3/8 下午5:16
 * @desc
 */
public class PerformTestUserInfo {

    public static void main(String[] args) throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserID(100).buildUsername("Welcome to Netty");
        int loop = 10000000;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(userInfo);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("jdk序列化执行" + loop + "次，花费时间:" + endTime + "ms");

        System.out.println("----------------------------------------------");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            userInfo.codeC(byteBuffer);
        }
        System.out.println("ByteBuffer序列化执行" + loop + "次，花费时间:" + (System.currentTimeMillis() - startTime) + "ms");


    }
}
