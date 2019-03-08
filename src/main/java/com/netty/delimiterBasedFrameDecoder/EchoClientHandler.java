package com.netty.delimiterBasedFrameDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author wangzun
 * @version 2019/3/8 上午11:22
 * @desc
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //这里发送消息的必须在1024字节内包含 预定义的$_分隔符， 如果超长会抛TooLongFrameException异常（在server端的handler里定义长度。） ，如果没有则服务端会视为半包消息。
        ByteBuf byteBuf = Unpooled.copiedBuffer("魔幻手机陆小千$_".getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("this is " + ++counter + "times receive server: [" + body + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
