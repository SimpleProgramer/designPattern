package com.nio.project1.伪异步;

import com.nio.project1.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wangzun
 * @version 2019/2/25 下午9:33
 * @desc
 */
public class AsyncTimeServer {
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
            TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(50,1000);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                pool.execute(new TimeServerHandler(socket));
            }
        }finally {
            if (null != server) {
                server.close();
            }

        }
    }
}
