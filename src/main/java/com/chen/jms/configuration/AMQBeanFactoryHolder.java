package com.chen.jms.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen
 * @description
 * @pachage com.chen.jms
 * @date 2017/3/8 17:02
 */
@Configuration
public class AMQBeanFactoryHolder implements BeanFactoryAware {
    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AMQBeanFactoryHolder.beanFactory = beanFactory;
    }

    public static BeanFactory get() {
        return AMQBeanFactoryHolder.beanFactory;
    }
}
