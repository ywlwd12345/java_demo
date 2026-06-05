package com.example.demo.springcontainer.ioc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = PrimaryBeanTest.TestConfig.class) // 指定使用当前文件内的配置
public class PrimaryBeanTest {

    @Autowired
    private ApplicationContext context;

    // ==========================================
    // 1. 核心业务接口与实现类（为了方便，写成了内部类）
    // ==========================================
    interface UserService {
        String getUser(String username);
    }

    @Component
    static class StandardUserService implements UserService {
        @Override
        public String getUser(String username) {
            return "Standard User: " + username;
        }
    }

    @Component
    @Primary // ✨ 核心主角：声明它是首选的 Bean
    static class PremiumUserService implements UserService {
        @Override
        public String getUser(String username) {
            return "Premium User: " + username;
        }
    }

    // ==========================================
    // 2. Spring 配置类：负责将上面的 Bean 注册到容器中
    // ==========================================
    @Configuration
    static class TestConfig {
        @Bean
        public UserService standardUserService() {
            return new StandardUserService();
        }

        @Bean
        @Primary // 如果用 @Bean 注入，@Primary 也要加在这里
        public UserService premiumUserService() {
            return new PremiumUserService();
        }
    }

    // ==========================================
    // 3. 你的测试用例
    // ==========================================
    @Test
    @DisplayName("验证 @Primary 首选 Bean 选择")
    void testPrimaryBean() {
        // 容器中有两个 UserService 实现，@Primary 的优先
        UserService userService = context.getBean(UserService.class);
        
        // 验证 1：断言拿到的实例确实是 PremiumUserService，而不是 StandardUserService
        assertInstanceOf(PremiumUserService.class, userService,
                "应注入 @Primary 标记的 Bean");

        // 验证 2：调用方法，确保业务返回值符合 Premium 的预期
        String result = userService.getUser("TestUser");
        assertTrue(result.contains("Premium User"), "@Primary Bean 应正常工作");
    }
}


