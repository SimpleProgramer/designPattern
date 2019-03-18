package com.netty.marshalling;

import com.serialize.protobuf.SubscribeReqProto;
import com.serialize.protobuf.SubscribeRespProto;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wangzun
 * @version 2019/3/18 上午10:29
 * @desc
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubReq req = (SubReq) msg;
        System.out.println(req.toString());
        ctx.writeAndFlush(BeanFactory.buildResp(req.getId()));
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
}