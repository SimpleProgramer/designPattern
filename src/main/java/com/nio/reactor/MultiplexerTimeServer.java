package com.nio.reactor;

import javafx.scene.paint.Stop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wangzun
 * @version 2019/2/26 下午5:11
 * @desc
 */
public class MultiplexerTimeServer implements Runnable,Stopable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    //初始化多路复用器
    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("this time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);//每隔1s都会被唤醒继续如果有就绪状态的任务 则往下执行。
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                //接入了一个新的连接
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                //将新接入的连接注册到selector
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(buffer);
                if (readBytes > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body = new String(bytes, "utf-8");
                    System.out.println("this time server receive order : " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                    doWrite(sc,currentTime);

                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                } else {
                    ;
                }
            }
        }

    }

    private void doWrite(SocketChannel sc, String currentTime) throws IOException {
        if (currentTime != null && currentTime.length() > 0) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            sc.write(writeBuffer);
        }
    }
}
