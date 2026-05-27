package com.xinbayun.annotation;

import com.xinbayun.registrar.XinBaPrinterRegistrar;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(XinBaPrinterRegistrar.class) // 🔥 绑定你的硬核注册器
public @interface EnableXinBaPrinter {
}