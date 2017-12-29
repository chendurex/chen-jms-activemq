package com.chen.jms;

import com.chen.jms.send.MessageSend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chen
 * @description
 * @pachage com.chen.jms
 * @date 2016/11/21 21:14
 */
@RunWith(SpringRunner.class)
@SpringBootApplication(scanBasePackages = {"com.chen"})
@SpringBootTest(classes = Application.class)
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Test
    public void run() throws Exception {
        ApplicationContextUtil.getApplicationContext().getBean(MessageSend.class).run();
    }

}
