package com.netty.marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * @author wangzun
 * @version 2019/3/18 上午10:24
 * @desc
 */
public final class MarshallingCodeCFactory {
    //创建Marshalling解码器
    public static MarshallingDecoder buildMarshallingDecoder() {
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        return new MarshallingDecoder(new DefaultUnmarshallerProvider(factory,configuration),1024);
    }
    //创建Marshalling解码器
    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        return new MarshallingEncoder(new DefaultMarshallerProvider(factory,configuration));
    }
}
