package com.chen.jms.send;

import com.chen.jms.domain.Email;
import com.chen.jms.util.JmsTemplateUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chen
 * @description
 * @pachage com.chen.mq
 * @date 2016/12/13 10:23
 */
@Component
public class MessageSend {

    public void run() {
        sendBean();
        sendMap();
        sendList();
        sendText();
        sendNull();
    }

    public static void sendBean() {
        JmsTemplateUtil.send("bean.name", new Email("hello", "world"));
    }

    public static void sendMap() {
        Map<String, Email> map = new HashMap<>();
        map.put("1", new Email("1", "3"));
        JmsTemplateUtil.send("map", map);
    }

    public void sendList() {
        List<Email> list = new ArrayList<>();
        list.add(new Email("1", "2"));
        JmsTemplateUtil.send("list", list);
    }

    public void sendNull() {
        JmsTemplateUtil.send("null", null);
    }

    public void sendText() {
        JmsTemplateUtil.send("text", "value");
    }
}
