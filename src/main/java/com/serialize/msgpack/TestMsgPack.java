package com.serialize.msgpack;

import com.serialize.UserInfo;
import org.msgpack.MessagePack;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzun
 * @version 2019/3/11 上午10:25
 * @desc
 */
public class TestMsgPack {
    public static void main(String[] args) throws IOException {
        List<String> src = new ArrayList<>();
        src.add("msgpack");
        src.add("kumosf");
        src.add("wangdashen");
        MessagePack messagePack = new MessagePack();
        byte[] rew = messagePack.write(src);

        List<String> desrc = messagePack.read(rew, Templates.tList(Templates.TString));
        desrc.forEach((s) -> {
            System.out.println(s);
        });
    }
}
