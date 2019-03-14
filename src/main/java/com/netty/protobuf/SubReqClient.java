package com.netty.protobuf;

import com.serialize.protobuf.SubscribeRespProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.concurrent.ExecutionException;

/**
 * @author wangzun
 * @version 2019/3/14 下午4:34
 * @desc
 */
public class SubReqClient {

    public void connect(int port, String host) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                     .channel(NioSocketChannel.class)
                     .option(ChannelOption.TCP_NODELAY,true)
                     .handler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel socketChannel) throws Exception {
                             socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                             socketChannel.pipeline().addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
                             socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                             socketChannel.pipeline().addLast(new ProtobufEncoder());
                             socketChannel.pipeline().addLast(new SubReqClientHandler());
                         }
                     });
            ChannelFuture sync = bootstrap.connect(host, port).sync();
            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new SubReqClient().connect(8080,"127.0.0.1");
    }
}
