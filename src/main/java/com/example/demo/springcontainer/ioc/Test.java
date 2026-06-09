package com.example.demo.springcontainer.ioc;
import java.util.HashMap;

class User {
    String name;
    User(String name) { this.name = name; }
    // ❌ 故意不写 hashCode 和 equals
}

public class Test {
    public static void main(String[] args) {
        HashMap<User, String> map = new HashMap<>();

        // 1. 创建张三，并存入 Map
        User u1 = new User("张三");
        map.put(u1, "张三的秘密文件");

        // 2. 重新 new 一个张三，尝试去取数据
        User u2 = new User("张三");
        String result = map.get(u2);

        System.out.println("拿到了吗？ " + result); 
        // 🚨 结果打印：拿到了吗？ null  （也就是说，根本找不到！）
    }
}
