package com.chen.jms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

/**
 * @author chen
 * @description
 * @pachage com.chen.jms
 * @date 2017/2/22 16:38
 */
public class ExceptionHandler implements ErrorHandler {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Override
    public void handleError(Throwable t) {
        // 可以转化为发送邮件的形式
        // 并且可以制作一个本地缓存，错误日志去重处理
        // 因为运维已经做了日志跟去重处理，所以可以不用实现
        LOG.error("消息消费失败，当前错误堆栈信息为：", t);
    }
}
