package com.example.demo.springcontainer.ioc;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final UserServiceTest userServiceTest; // 🎯 final 关键字修饰，保证不可变，极其安全

    // 👍 构造器注入：Spring 看到这个构造器，会雷打不动地去容器里捞 UserService 传进来
    public OrderService(UserServiceTest userServiceTest) {
        this.userServiceTest = userServiceTest;
    }

    public String createOrder(String customerName) {
        return "Order created for " + userServiceTest.checkUser(customerName);
    }
}
