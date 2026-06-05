package com.example.demo.springcontainer.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 挂牌：手工车间
public class IocConfig {

    @Bean // 贴标：告诉 Spring 乖乖执行这个方法，把 return 的成品收进库房
    public MessageService messageService() {
        return new MessageService(); // 🎯 手动拧螺丝造出对象
    }
}