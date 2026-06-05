package com.example.demo.springcontainer.ioc;

import org.springframework.stereotype.Service;

@Service
public class UserServiceTest {
    public String checkUser(String name) { return "Premium User: " + name; }
    
}
