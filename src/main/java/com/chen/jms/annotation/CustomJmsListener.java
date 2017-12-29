package com.chen.jms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chen
 * @description 自定义表示监听器
 * @pachage com.chen.jms.annotation
 * @date 2017/3/13 20:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomJmsListener {
}
