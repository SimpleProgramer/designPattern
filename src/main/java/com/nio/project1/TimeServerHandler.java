package com.nio.project1;

import java.io.*;
import java.net.Socket;

/**
 * @author wangzun
 * @version 2019/2/25 下午8:27
 * @desc
 */
public class TimeServerHandler implements Runnable {
    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(),true);
            String currentTime = null;
            String body = null;
            while (true) {
                System.out.println("start read");
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("this time server receive order : " + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                out.println(currentTime);
            }
        } catch (Exception e) {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (null != out) {
                    out.close();
            }
            if (null != socket) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
