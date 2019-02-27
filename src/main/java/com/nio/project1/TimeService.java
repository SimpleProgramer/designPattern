package com.nio.project1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wangzun
 * @version 2019/2/25 下午8:20
 * @desc
 */
public class TimeService {

    public static void main(String[] args) throws IOException {
        int port= 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("this time server is start in port :" + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        }finally {
            if (null != server) {
                server.close();
            }

        }
    }
}
