package com.netty.marshalling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wangzun
 * @version 2019/3/18 上午10:36
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubResp implements Serializable {
    private String desc;
    private int id;
    @Override
    public String toString() {
        return "收到" + id + "的订购请求，" + desc;
    }
}
