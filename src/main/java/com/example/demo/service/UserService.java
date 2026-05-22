package com.example.demo.service;
import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getUser() {

        User user = new User();

        user.setUsername("bob");

        user.setBalance(1000.0);

        return user;

    }

}
