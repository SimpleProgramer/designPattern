package com.serialize.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzun
 * @version 2019/3/14 下午3:43
 * @desc
 */
public class TestProtobuf {
    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }
    private static byte[] encode(SubscribeRespProto.SubscribeResp req) {
        return req.toByteArray();
    }

    private static SubscribeRespProto.SubscribeResp decodeResp(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeRespProto.SubscribeResp.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        List<String> address = new ArrayList<>();
        address.add("阿拉德大陆");
        builder.addAllAddress(address);
        builder.setProductName("2019年春节套");
        builder.setSubReqId(1);
        builder.setUserName("浅欢欢");
        return builder.build();
    }

    private static SubscribeRespProto.SubscribeResp createSubscribeResp() {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setRespCode(0);
        builder.setDesc("test");
        builder.setSubReqId(1);
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("编码之前：" + req.toString());
        SubscribeReqProto.SubscribeReq reqs = decode(encode(req));
        System.out.println("编解码之后:" + reqs.toString());
        System.out.println("Assert equal -> " + req.equals(reqs));
        System.out.println("----------------------------");
        SubscribeRespProto.SubscribeResp resp = createSubscribeResp();
        System.out.println("编码之前：" + resp.toString());
        SubscribeRespProto.SubscribeResp rps = decodeResp(encode(resp));
        System.out.println("编解码之后:" + rps.toString());
        System.out.println("Assert equal -> " + resp.equals(rps));



    }
}
