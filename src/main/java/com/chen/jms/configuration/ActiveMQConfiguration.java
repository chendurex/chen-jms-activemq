package com.chen.jms.configuration;

import com.chen.jms.exception.ExceptionHandler;
import com.chen.jms.message.CustomDefinitionMappingJackson;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

/**
 * @author chen
 *         2017/12/20 10:50
 */
@Configuration
@AutoConfigureAfter(value = {AMQApplicationHolder.class, AMQBeanFactoryHolder.class})
@EnableJms
public class ActiveMQConfiguration {
    @Value("${mq.brokerURL}")
    private String brokerURL;
    @Bean
    public ActiveMQConnectionFactory amqConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerURL);
        return factory;
    }

    @Bean
    public SingleConnectionFactory springJmsConnectionFactory() {
        SingleConnectionFactory factory = new SingleConnectionFactory();
        factory.setTargetConnectionFactory(amqConnectionFactory());
        return factory;
    }

    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        return new CustomDefinitionMappingJackson();
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(springJmsConnectionFactory());
        factory.setSessionTransacted(true);
        factory.setSessionAcknowledgeMode(1);
        factory.setMessageConverter(messageConverter());
        factory.setErrorHandler(new ExceptionHandler());
        factory.setConcurrency("1-5");
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setSessionAcknowledgeMode(1);
        jmsTemplate.setConnectionFactory(springJmsConnectionFactory());
        jmsTemplate.setMessageConverter(messageConverter());
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }
}
