package com.example.demo.springcontainer.ioc; // 1. 确保包名正确

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("IOC 核心能力测试")
class IocTest {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("实例 1：验证 @Bean 的注册与默认命名")
    void testBeanConfiguration() {
        // 1. 验证根据类型，能不能从容器里拿到产品
        TscPrinter printer = context.getBean(TscPrinter.class);
        assertNotNull(printer, "通过 @Bean 方法生成的 TscPrinter 应该在容器中");

        // 2. 🔥 硬核验证：名字默认是不是【方法名】 myTscPrinter
        String[] beanNames = context.getBeanNamesForType(TscPrinter.class);
        assertEquals("myTscPrinter", beanNames[0], "通过 @Bean 注册的名字必须是方法名！");

    

    }
    
    @Test
    @DisplayName("实例 4：验证 @Lazy 注解延迟初始化的超能力")
    void testLazyInitialization() {
        assertFalse(SuperHeavyEngine.isCreated, "虽然容器启动了，但懒加载的引擎现在应该还没有被 new 出来！");

        System.out.println("====== 📢 准备执行第一次 getBean() 点名 ======");
        SuperHeavyEngine engine = context.getBean(SuperHeavyEngine.class);
        
        assertNotNull(engine);
        assertTrue(SuperHeavyEngine.isCreated, "在第一次调用 getBean() 之后，引擎必须被实例化成功！");
    }

} // 💡 2. 这是 IocTest 类结束的大括号。里面干净了，没有任何冲突的内部类了！

// =======================================================
// 🔥 3. 实验材料类和配置类，老老实实呆在最外层（平级）
// =======================================================

// 💡 注意：因为你 D:\xb_studay\java-base\demo\src\main\java\com\example\springcontainer\ioc\TscPrinter.java 
// 目录下已经有一个 TscPrinter.java 了，如果这里再声明 class TscPrinter 会报“类重复定义”错误。
// 所以，我们在这里直接复用你主目录下的那个 TscPrinter，把这里的 class TscPrinter { } 注释掉或者删掉！

class VipUserService {
}

class SuperHeavyEngine {
    public static boolean isCreated = false;

    public SuperHeavyEngine() {
        isCreated = true;
    }
}

@org.springframework.context.annotation.Configuration
class MyConfig {
    @org.springframework.context.annotation.Bean
    public TscPrinter myTscPrinter() {
        // 🎯 这里的 new TscPrinter() 会直接去调用你主代码目录下的那个 TscPrinter 类！
        return new TscPrinter(); 
    }
}

@org.springframework.context.annotation.Configuration
class LazyConfig {
    @org.springframework.context.annotation.Bean
    @org.springframework.context.annotation.Lazy
    public SuperHeavyEngine heavyEngine() {
        return new SuperHeavyEngine();
    }
}