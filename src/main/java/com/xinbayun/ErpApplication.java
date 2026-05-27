package com.xinbayun;

import com.xinbayun.annotation.EnableXinBaModules;
import com.xinbayun.annotation.EnableXinBaPrinter;
import com.xinbayun.printer.ErpPrinterService;
import com.xinbayun.service.InventoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
@EnableXinBaModules // 👈 🔥 贴上你的自定义注解！让 Spring 自动感知！
@EnableXinBaPrinter
public class ErpApplication {

    public static void main(String[] args) {
        // 启动 Spring 容器

        ConfigurableApplicationContext context = SpringApplication.run(ErpApplication.class, args);

        // 验证结果：从容器中获取被动态注入的 InventoryService
        // 如果能拿到并成功打印，说明你的选择器被 Spring 完美感知并成功执行了！
        try {
            InventoryService inventoryService = context.getBean(InventoryService.class);
            inventoryService.doCheck();

            // 验证结果：通过你刚才亲手指定的 Bean 名字 "customTscPrinter" 去容器里拿
            ErpPrinterService printer = (ErpPrinterService) context.getBean("customTscPrinter");

            // 执行打印业务
            printer.printLabel("商品：五金配件 | 数量：100件 | 入库仓：A1区");

        } catch (Exception e) {
            System.out.println("❌ 模块未加载！");
        }
    }
}