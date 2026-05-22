package com.xinbayun.annotation;
import com.xinbayun.selector.XinBaModuleSelector;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target(ElementType.TYPE) // 1. 声明这个注解只能贴在类/接口上面
@Retention(RetentionPolicy.RUNTIME) // 2. 声明这个注解在运行时依旧有效（这样 Spring 才能通过反射读到它）
@Documented
@Import(XinBaModuleSelector.class) // 3. 🔥 核心核心！只要别人贴了本注解，Spring 就会自动触发这个选择器
public @interface EnableXinBaModules {
    
    // 这里可以定义一些属性，比如 String version() default "1.0"; 暂时留空即可
    
}