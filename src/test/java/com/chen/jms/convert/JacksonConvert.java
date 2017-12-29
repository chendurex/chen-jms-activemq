package com.chen.jms.convert;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

/**
 * @author chen
 * @description
 * @pachage com.chen.mq.convert
 * @date 2016/12/5 16:49
 */
//@Component
public class JacksonConvert {

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        //converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("object_type");
        return converter;
    }
}
