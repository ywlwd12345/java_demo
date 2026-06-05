package com.example.demo.springcontainer.ioc;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("IOC 配置类能力深度测试")
class IocConfigTest {
    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("验证 @Bean 方式注册")
    void testBeanMethodRegistration() {
        // 1️⃣ 验证：MessageService 没有 Spring 注解，看能不能通过 @Configuration + @Bean 成功从容器捞出来
        MessageService messageService = context.getBean(MessageService.class);
        assertNotNull(messageService, "@Bean 应注册 MessageService");

        // 2️⃣ 验证：硬核知识点！IocConfig 配置类本身，是不是也被 Spring 当成一个 Bean 供起来了？
        IocConfig configBean = context.getBean(IocConfig.class);
        assertNotNull(configBean, "@Configuration 类本身也是 Bean");

        // 3️⃣ 验证功能正常：不仅要能拿到对象，还要验证这个手动 new 出来的对象，功能是不是完好无损的
        String result = messageService.sendMessage("Alice", "test");

        // 断言：返回的字符串里，必须包含 "Message sent to Alice" 这段特征码
        assertTrue(result.contains("Message sent to Alice"), "返回值应当包含发送成功的核心文案");
    }

    @Test
    @DisplayName("验证构造器注入 vs Setter 注入 vs Field 注入")
    void testInjectionMethods() {
        // 1️⃣ 验证：构造器注入
        OrderService orderService = context.getBean(OrderService.class);
        String orderResult = orderService.createOrder("Alice");
        assertTrue(orderResult.contains("Premium User: Alice"), "构造器注入应正常工作");

        // 2️⃣ 验证：Setter 注入
        NotificationService notificationService = context.getBean(NotificationService.class);
        String notifyResult = notificationService.notify("Bob", "Hello");
        assertTrue(notifyResult.contains("Message sent to Bob"), "Setter 注入应正常工作");

        // 3️⃣ 验证：Field 注入
        ProductRepository productRepository = context.getBean(ProductRepository.class);
        String productResult = productRepository.findProduct("P001");
        assertTrue(productResult.contains("P001"), "Field 注入类应正常工作");

    }

}