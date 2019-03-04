package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangzun
 * @version 2019/3/1 下午2:08
 * @desc
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    //private final ByteBuf firstMessage; 原 单次发送的声明

    private byte[] req;

    private int counter;

    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        //发送单条信息是在这里初始化
    }


    //通道活跃时响应该事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当TCP连接与服务端建立成功之后，响应该事件，通过ChannelHandlerContext 将firstMessage 刷入发送消息队列。在此之前。消息在发送缓冲队列。
        ByteBuf firstMessage = null;
        for (int i = 0; i < 100; i++) {
            firstMessage = Unpooled.buffer(req.length);
            firstMessage.writeBytes(req);
            ctx.writeAndFlush(firstMessage);
        }
    }
    //通道读写事件响应
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //当服务器返回消息时，channelRead被调用
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Now is :" + body);
        System.out.println("这是第"+ counter +"次收到回复");
    }

    //异常捕获
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常时，可以自定义异常处理，并且释放客户端资源。
        ctx.close();
    }
}
