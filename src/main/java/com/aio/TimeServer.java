package com.aio;

/**
 * @author wangzun
 * @version 2019/2/27 下午4:06
 * @desc
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIOTimeServerHandler-001").start();
    }
}
