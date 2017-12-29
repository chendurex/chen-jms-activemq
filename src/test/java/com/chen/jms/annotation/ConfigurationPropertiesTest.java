package com.chen.jms.annotation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chen
 * @description
 * @pachage com.chen.annotation
 * @date 2017/1/9 16:50
 */
@Component
@ConfigurationProperties(prefix = "module_name")
public class ConfigurationPropertiesTest {
    private String sessionAcknowledgeMode;

    public String getSessionAcknowledgeMode() {
        return sessionAcknowledgeMode;
    }

    public void setSessionAcknowledgeMode(String sessionAcknowledgeMode) {
        this.sessionAcknowledgeMode = sessionAcknowledgeMode;
    }
}
