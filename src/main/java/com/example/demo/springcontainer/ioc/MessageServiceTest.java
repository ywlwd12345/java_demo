package com.example.demo.springcontainer.ioc;

import org.springframework.stereotype.Service;

@Service
public class MessageServiceTest {
    public String sendMessage(String receiver, String content) {
        return "Message sent to " + receiver;
    }

}
