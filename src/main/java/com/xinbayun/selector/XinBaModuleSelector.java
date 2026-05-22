package com.xinbayun.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class XinBaModuleSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 1. 模拟读取环境配置（比如从系统变量或 YML 中读取当前企业开通的权限）
        // 实际开发中也可以通过 importingClassMetadata 去读取注解上的属性
        boolean isPaidUser = true; 

        System.out.println("====== [自动感知] XinBaModuleSelector 开始动态装配模块 ======");

        // 2. 动态判断，返回需要注册为 Bean 的全限定类名数组
        if (isPaidUser) {
            // 如果是付费企业，动态加载核心库存模块
            return new String[]{ "com.xinbayun.service.InventoryService" };
        }

        // 免费用户什么都不加载
        return new String[0];
    }
}