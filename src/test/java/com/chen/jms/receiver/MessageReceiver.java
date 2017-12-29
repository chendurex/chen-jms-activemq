package com.chen.jms.receiver;

import com.chen.jms.annotation.CustomJmsListener;
import com.chen.jms.domain.Email2;
import com.chen.jms.annotation.CustomMapping;
import com.chen.jms.domain.Email;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.Session;
import java.util.List;
import java.util.Map;

/**
 * @author chen
 * @description
 * @pachage com.chen.mq
 * @date 2016/12/13 10:12
 */

@CustomJmsListener
@Component
public class MessageReceiver {

    @JmsListener(destination = "${module.name}.bean.name")
    public void bean(@CustomMapping Email email) {
        System.out.println("bean <" + email + ">");
    }

    @JmsListener(destination = "${module.name}.map")
    public void map(@CustomMapping Map<String, Email2> map) {
        System.out.println(map.getClass() + "map <" + map + ">");
    }

    @JmsListener(destination = "${module.name}.list")
    public void list(@CustomMapping List<Email2> list) {
        System.out.println("list thread" + Thread.currentThread().getId() + "list <" + list + ">");
    }

    @JmsListener(destination = "${module.name}.text")
    public void text(Message message, Session session, String text) {
        System.out.println("text thread" + Thread.currentThread().getId() + "text <"  + text +">");
    }

    @JmsListener(destination = "${module.name}.null")
    public void nullText() {
        System.out.println("--------null-------");
    }
}
