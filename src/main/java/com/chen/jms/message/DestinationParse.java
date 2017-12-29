package com.chen.jms.message;

import javax.jms.Destination;

/**
 * @author chen
 * @description 消息目的地解析器
 * @pachage com.chen.jms.message
 * @date 2017/3/8 19:12
 */
public interface DestinationParse {
    String parse(Destination destination);
}
