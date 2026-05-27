package com.xinbayun.registrar;

import com.xinbayun.printer.ErpPrinterService;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
public class XinBaPrinterRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("====== [硬核掌控] XinBaPrinterRegistrar 开始纯手动编织 Bean 图纸 ======");

        // 1. 💡 纯手动创建一张最为详细的“建筑图纸（BeanDefinition）”
        RootBeanDefinition printerDef = new RootBeanDefinition();
        
        // 2. 告诉 Spring，这个 Bean 的底子是哪个类
        printerDef.setBeanClass(ErpPrinterService.class);
        
        // 3. 🔥 绝活演示：由于 ErpPrinterService 没有无参构造方法，
        // 我们可以硬核注入构造方法参数！（这是前面几种写法绝对做不到的！）
        ConstructorArgumentValues args = new ConstructorArgumentValues();
        args.addIndexedArgumentValue(0, "TSC-TSPL标签打印机"); // 传入第一个参数 brand
        args.addIndexedArgumentValue(1, "COM3串口驱动");       // 传入第二个参数 port
        printerDef.setConstructorArgumentValues(args);

        // 4. 还能手动定制它的作用域（设置为原型模式/多例模式，每次获取都 new 个新的）
        printerDef.setScope("prototype");

        // 5. 🎯 终极大权：手动给它起一个喜欢的 Bean 名字，并把它拍到 Spring 的账本上！
        registry.registerBeanDefinition("customTscPrinter", printerDef);
    }
}