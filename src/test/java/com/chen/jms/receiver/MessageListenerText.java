package com.chen.jms.receiver;


import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author chen
 * @description
 * @pachage com.chen
 * @date 2017/1/13 19:10
 */
public class MessageListenerText implements MessageListener {
    public void onMessage(Message message) {
        throw new IllegalArgumentException("Message must be of type TextMessage");
    }
}
