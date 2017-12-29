package com.chen.jms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chen
 * @description 自定义解析器
 *   默认的消息解析器，仅支持对象完全相同的情况，这样会导致耦合度增加
 *   添加这个注解可以让你仅需考虑数据的内容
 *   但是，仅仅支持一级层级的数据转换，比如List<Email>,Map<String,Email>
 *   而嵌套的层级如：List<Set<Email>>,Map<String,Map<String, Email>> 这样是不支持
 * @pachage com.chen.jms.annotation
 * @date 2017/3/8 17:54
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CustomMapping {
}
