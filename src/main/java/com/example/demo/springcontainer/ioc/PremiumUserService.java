package com.example.demo.springcontainer.ioc;
import org.springframework.stereotype.Service;
@Service
public class PremiumUserService implements UserService {
    @Override
    public String getUserType() {
        return "VIP会员服务";
    }
}
