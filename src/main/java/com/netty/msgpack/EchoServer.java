package com.netty.msgpack;

import com.netty.encoder.msgpack.MsgPackDecoder;
import com.netty.encoder.msgpack.MsgPackEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wangzun
 * @version 2019/3/5 下午2:04
 * @desc 根据DelimiterBasedFrameDecoder 处理粘包拆包问题。
 *
 * DelimiterBasedFrameDecoder 自定义带长度限制的分隔符解码器。
 *  初始化时 可以自定义分隔符对消息进行分包处理，并且定义了每条消息的最大长度maxFrameLength。
 * 发的每条消息末尾都需要加上 预定义的分隔符。与LineBasedFrameDecoder的区别可理解为 1：自定义分隔符， 2：限制了消息长度。
 */
public class EchoServer {

    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup)
                 .channel(NioServerSocketChannel.class)
                 .option(ChannelOption.SO_BACKLOG,100)
                 .handler(new LoggingHandler(LogLevel.INFO))
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                     @Override
                     protected void initChannel(SocketChannel socketChannel) throws Exception {

                         //socketChannel.pipeline().addLast(new FixedBasedFrameDecoder(2048)); 这一行可以替换DelimiterBasedFrameDecoder
                         //FixedBasedFrameDecoder 是定长消息解码器 ，按照指定长度分包， 不足则认为是半包，超过直接截取。
                         socketChannel.pipeline()
                                 .addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2))
                                 .addLast(new MsgPackDecoder())
                                 .addLast("frameEncoder",new LengthFieldPrepender(2))
                                 .addLast(new MsgPackEncoder())
                                 .addLast(new EchoServerHandler());
                     }
                 });
        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer().bind(8080);
    }

}
