package com.nio.reactor;

/**
 * @author wangzun
 * @version 2019/2/26 下午5:09
 * @desc
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();

    }
}
