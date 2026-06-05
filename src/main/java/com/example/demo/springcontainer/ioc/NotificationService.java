package com.example.demo.springcontainer.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private MessageServiceTest messageServiceTest; // 🎯 依赖的接口类型，Spring 会帮我们找到对应的实现类来注入

    // 👍 Setter 注入：对象创建出来后，Spring 会自动来调用这个 set 方法把依赖灌进去
    @Autowired
    public void setMessageService(MessageServiceTest messageServiceTest) {
        this.messageServiceTest = messageServiceTest;
    }

    public String notify(String user, String msg) {
        return messageServiceTest.sendMessage(user, msg);
    }

}
