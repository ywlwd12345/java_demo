package com.example.demo.springcontainer.ioc;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
    @Override
    public String getUserType() {
        return "普通用户服务";
    }
}
