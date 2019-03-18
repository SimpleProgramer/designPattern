package com.netty.protobuf;

import com.serialize.protobuf.SubscribeReqProto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzun
 * @version 2019/3/14 下午4:39
 * @desc
 */
public class SubReqClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 1; i < 11; i++) {
            SubscribeReqProto.SubscribeReq subscribeReq = createSubscribeReq(i);
            System.out.println(subscribeReq);
            ctx.write(subscribeReq);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("订购结果:" + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private SubscribeReqProto.SubscribeReq createSubscribeReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        List<String> adds = new ArrayList<>();
        adds.add("阿拉德大陆");
        builder.addAllAddress(adds);
        builder.setProductName("第" + i +"套2019春节套");
        builder.setSubReqId(1);
        builder.setUserName("浅欢欢");
        return builder.build();
    }
}
