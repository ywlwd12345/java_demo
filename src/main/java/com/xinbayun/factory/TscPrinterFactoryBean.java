package com.xinbayun.factory;

import com.xinbayun.printer.TscPrinterDriver;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component("tscPrinter") // 💡 注意：我们把这个【工厂类】注册进 Spring，名字叫 "tscPrinter"
public class TscPrinterFactoryBean implements FactoryBean<TscPrinterDriver> {

    // 1. 🔥 核心核心：这里面写你复杂的、硬核的、纯手动的建造逻辑
    @Override
    public TscPrinterDriver getObject() throws Exception {
        System.out.println("🏭 [工厂全力运转] 正在读取硬件授权文件、握手串口...");
        TscPrinterDriver driver = new TscPrinterDriver();
        driver.initHardwareLink(); // 极其复杂的硬件初始化
        
        return driver; // 🎯 返回真正的高级产品
    }

    // 2. 告诉 Spring，你这个工厂生产出来的产品是什么类型的
    @Override
    public Class<?> getObjectType() {
        return TscPrinterDriver.class;
    }

    // 3. 顺便指定这个工厂生产的产品是单例还是多例（默认是单例）
    @Override
    public boolean isSingleton() {
        return true;
    }
}