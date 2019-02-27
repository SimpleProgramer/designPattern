package com.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author wangzun
 * @version 2019/2/25 上午11:24
 * @desc
 */
public class Server {

    public static void testServer() throws IOException {

        //1.获取Selector选择器
        Selector selector = Selector.open();
        //2.获取通道
        ServerSocketChannel channel = ServerSocketChannel.open();
        //3.设置为非阻塞
        channel.configureBlocking(false);
        //4.绑定连接
        channel.bind(new InetSocketAddress(8080));
        //5.将通道注册到选择器上，并注册操作为"接收"操作。
        channel.register(selector, SelectionKey.OP_ACCEPT);
        //6.采用轮训的方式，查询获取"准备就绪"的注册 过的操作
        while (selector.select() > 0) {
            //7.获取当前选择器中所有注册的SelectionKey（"准备就绪"）
            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()) {

                //以下的if else 就相当于一个dispatch分发
                SelectionKey next = selectionKeys.next();
                if (next.isAcceptable()) {
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (next.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) next.channel();
                    // 14、读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int length = 0;
                    while ((length = socketChannel.read(byteBuffer)) != -1)
                    {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, length));
                        byteBuffer.clear();
                    }
                    socketChannel.close();

                }
                selectionKeys.remove();
            }
        }

    }
}
