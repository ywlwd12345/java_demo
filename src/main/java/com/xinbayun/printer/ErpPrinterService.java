package com.xinbayun.printer;

// 💡 干净的类，没有任何 @Component
public class ErpPrinterService {
    private String brand; // 打印机品牌
    private String port;  // 串行通信端口（比如 COM1）

    public ErpPrinterService(String brand, String port) {
        this.brand = brand;
        this.port = port;
    }

    public void printLabel(String content) {
        System.out.println("🖨️ [辛巴云打印机驱动] 使用 [" + brand + "] 驱动，通过端口 [" + port + "] 正在打印标签：" + content);
    }
}