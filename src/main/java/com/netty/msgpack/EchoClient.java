package com.netty.msgpack;

import com.netty.encoder.msgpack.MsgPackDecoder;
import com.netty.encoder.msgpack.MsgPackEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author wangzun
 * @version 2019/3/8 上午11:18
 * @desc使用MsgPackDecoder/Encoder 编解码
 */
public class EchoClient {

    public void connect(String host,int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2))
                                    .addLast(new MsgPackDecoder())
                                    .addLast("frameEncoder", new LengthFieldPrepender(2))
                                    .addLast(new MsgPackEncoder())
                                    .addLast(new EchoClientHandler(10));
                        }
                    });
            ChannelFuture sync = bootstrap.connect(host, port).sync();
            sync.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient().connect("127.0.0.1",8080);
    }
}
