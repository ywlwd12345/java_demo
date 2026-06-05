package com.example.demo.springcontainer.ioc;
import org.springframework.stereotype.Component;

@Component
@org.springframework.context.annotation.Scope("prototype") // 💡 确保它是多例
public class SessionHandler {
    
}
