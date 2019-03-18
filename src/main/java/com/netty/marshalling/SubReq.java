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
public class SubReq implements Serializable {
    private String name;
    private int id;
    private String address;
    @Override
    public String toString() {
        return id + "在" + address + "发起订购" + name + "的请求";
    }
}
