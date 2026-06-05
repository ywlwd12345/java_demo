package com.example.demo.springcontainer.ioc;

// 🎯 注意：这个类头上极其干净，没有写任何 @Component 或 @Service！
public class MessageService {
    public String sendMessage(String receiver, String content) {
        System.out.println("正在发送消息给 " + receiver + ": " + content);
        return "Message sent to Alice successfully"; // 👈 确保包含测试断言要查的关键词
    }
}
