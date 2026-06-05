package com.example.demo.springcontainer.ioc;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// =========================================================================
// 🧪 核心测试类 (整个文件唯一的 public class，类名与文件名严格一致)
// =========================================================================
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("Spring Bean Prototype 多例作用域大阅兵")
public class SpringPrototypeScopeTest {
    @Autowired
    private ApplicationContext context; // 呼叫 IOC 大管家

    @Test
    @DisplayName("验证 prototype 作用域 — 每次新实例")
    void testPrototypeScope() {
        // 1️⃣ 第一次捞：Spring 发现是 prototype，现场给你【临时 new 一个】
        SessionHandler handler1 = context.getBean(SessionHandler.class);
        // 2️⃣ 第二次捞：Spring 不去拿旧的，又现场给你【临时 new 一个全新的】
        SessionHandler handler2 = context.getBean(SessionHandler.class);
        // 3️⃣ 第三次捞：Spring 再次现场【临时 new 一个更全新的】
        SessionHandler handler3 = context.getBean(SessionHandler.class);

        // 🎯 断言一：assertNotSame 相当于 handler1 != handler2 (验证它们住在完全不同的内存房间)
        assertNotSame(handler1, handler2, "每次获取应创建新实例");
        assertNotSame(handler2, handler3, "每次获取应创建新实例");

        // 🎯 断言二：assertNotEquals 验证它们的业务数据（身份证号字符串）也是不一样的
        assertNotEquals(handler1.getInstanceId(), handler2.getInstanceId(), "各自的实例 ID 应当独立不相同");
        assertNotEquals(handler2.getInstanceId(), handler3.getInstanceId(), "各自的实例 ID 应当独立不相同");

        // 💡 顺便打印一下，让你肉眼感受一下多例对象的疯狂更替：
        System.out.println(
                "handler1 的物理地址：" + Integer.toHexString(handler1.hashCode()) + " | ID: " + handler1.getInstanceId());
        System.out.println(
                "handler2 的物理地址：" + Integer.toHexString(handler2.hashCode()) + " | ID: " + handler2.getInstanceId());
        System.out.println(
                "handler3 的物理地址：" + Integer.toHexString(handler3.hashCode()) + " | ID: " + handler3.getInstanceId());
    }
}

// =========================================================================
// 📦 辅助多例实验材料 (去掉 public，享受平级同包可见待遇)
// =========================================================================

@Component
@Scope("prototype") // 🚀 硬核药方：告诉 Spring 放弃单例缓存，每次 getBean 都必须重新 new！
class SessionHandler {

    private final String instanceId;

    // 构造器：每次对象一出生，立刻随机盖一个独一无二的戳
    public SessionHandler() {
        this.instanceId = "SESSION-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String getInstanceId() {
        return this.instanceId;
    }
}