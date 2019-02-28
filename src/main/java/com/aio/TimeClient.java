package com.aio;

/**
 * @author wangzun
 * @version 2019/2/28 上午10:04
 * @desc
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;

        new Thread(new AsyncTimeClientHandler("127.0.0.1",port),"AIO-AsyncTimeClientHandler-001").start();
    }
}
