package com.nio.reactor;

import com.nio.reactor.TimeClientHandler;

import java.io.IOException;

/**
 * @author wangzun
 * @version 2019/2/25 下午8:38
 * @desc
 */
public class TimeClient {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {

            }
        }

        for (int i = 0; i < 100; i++) {
            new Thread(new TimeClientHandler("127.0.0.1",port),"TimeClientHandler-00" + i).start();
        }


    }

    public void start() {
        System.out.println("start TimeClient");
    }
}
