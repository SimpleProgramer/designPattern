package com.netty.marshalling;

/**
 * @author wangzun
 * @version 2019/3/18 上午11:00
 * @desc
 */
public class BeanFactory {

    public static SubReq buildReq(int id) {
        return new SubReq("第"+ id +"套春节套",id,"名著司南");
    }
    public static SubResp buildResp(int id) {
        return new SubResp("购买成功",id);
    }
}
