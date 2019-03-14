package com.netty.encoder.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author wangzun
 * @version 2019/3/11 上午10:48
 * @desc 用messagePack重写netty的编码方式
 */
public class MsgPackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        MessagePack msgpack = new MessagePack();
        byte[] raw = msgpack.write(o);
        byteBuf.writeBytes(raw);
    }
}
