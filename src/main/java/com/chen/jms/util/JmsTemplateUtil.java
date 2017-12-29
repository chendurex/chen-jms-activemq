package com.chen.jms.util;

import com.chen.jms.configuration.AMQApplicationHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author chen
 * @description 发送消息工具类
 * @pachage com.chen.jms
 * @date 2016/12/5 8:51
 */
public class JmsTemplateUtil {
    private final static Logger LOG = LoggerFactory.getLogger(JmsTemplateUtil.class);
    private static volatile JmsTemplate jmsTemplate;

    private static JmsTemplate getJmsTemplate() {
        if (jmsTemplate == null) {
            synchronized (JmsTemplateUtil.class) {
                if (jmsTemplate == null) {
                    jmsTemplate = AMQApplicationHolder.getBean(JmsTemplate.class);
                }
            }
        }
        return jmsTemplate;
    }

    /**
     * 发送消息
     * @param destination
     * @param message
     */
    public static void send(String destination, Object message) {
        boolean suc = true;
        try {
            getJmsTemplate().convertAndSend(destination, message);
        } catch (Throwable t) {
            LOG.error("消息发送失败，dest:{}，错误信息为：", destination, t);
            suc = false;
        } finally {
            // 写日志
            try {
                LOG.info("消息发送：{}，dest：{}，message：{}", suc ? "成功" : "失败", destination, new ObjectMapper().writeValueAsString(message));
            } catch (JsonProcessingException e) {
                LOG.warn("消息格式化失败，堆栈信息为:", e);
            }
        }
    }
}
