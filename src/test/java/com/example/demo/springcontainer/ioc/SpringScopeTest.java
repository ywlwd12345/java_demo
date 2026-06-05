package com.example.demo.springcontainer.ioc;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

// =========================================================================
// 🧪 核心测试类 (类名与文件名保持一致，整个文件唯一的 public class)
// =========================================================================
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("Spring Bean 作用域深度测试")
public class SpringScopeTest {

    @Autowired
    private ApplicationContext context; // 全局大管家，用来捞 Bean

    @Test
    @DisplayName("验证 singleton 作用域 — 同一实例")
    void testSingletonScope() {
        // 1️⃣ 第一次捞 OrderService，Spring 去库房（一级缓存）里把单例拿出来
        OrderService order1 = context.getBean(OrderService.class);
        // 2️⃣ 第二次捞 OrderService，Spring 依旧去同一个库房位置，把同一个对象交给你
        OrderService order2 = context.getBean(OrderService.class);

        // 🎯 核心断言：assertSame 相当于 order1 == order2 (比较内存地址)
        assertSame(order1, order2, "Singleton 作用域下，两次获取应返回同一实例");

        // 3️⃣ 第一次捞 DefaultUserService
        DefaultUserService user1 = context.getBean(DefaultUserService.class);
        // 4️⃣ 第二次捞 DefaultUserService
        DefaultUserService user2 = context.getBean(DefaultUserService.class);
        
        // 🎯 核心断言：再次验证默认的 @Service 就是单例
        assertSame(user1, user2, "DefaultUserService 也应是单例");
    }
}


// =========================================================================
// 📦 辅助实验材料 (去掉 public，让他们乖乖呆在一个文件包里)
// =========================================================================

// 💡 默认不加 @Scope，就是隐含了 @Scope("singleton")
@Service
class OrderService {
    public void doSomething() {
        System.out.println("订单服务正在运行...");
    }
}

@Service
class DefaultUserService {
    public void doSomething() {
        System.out.println("用户服务正在运行...");
    }
}