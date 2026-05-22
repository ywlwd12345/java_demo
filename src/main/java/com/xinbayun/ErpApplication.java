package com.xinbayun;
import com.xinbayun.annotation.EnableXinBaModules;
import com.xinbayun.service.InventoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
@EnableXinBaModules // 👈 🔥 贴上你的自定义注解！让 Spring 自动感知！
public class ErpApplication {

    public static void main(String[] args) {
        // 启动 Spring 容器
        ConfigurableApplicationContext context = SpringApplication.run(ErpApplication.class, args);

        // 验证结果：从容器中获取被动态注入的 InventoryService
        // 如果能拿到并成功打印，说明你的选择器被 Spring 完美感知并成功执行了！
        try {
            InventoryService inventoryService = context.getBean(InventoryService.class);
            inventoryService.doCheck();
        } catch (Exception e) {
            System.out.println("❌ 模块未加载！");
        }
    }
}