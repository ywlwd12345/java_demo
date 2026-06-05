package com.example.demo.springcontainer.ioc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
class DbHelper { public String query(String id) { return "Product: " + id; } }

@Repository
public class ProductRepository {
    
    @Autowired // 👍 Field 注入：Spring 直接通过反射粗暴地把依赖塞给这个私有变量，省时省力
    private DbHelper dbHelper;

    public String findProduct(String productId) {
        return dbHelper.query(productId);
    }
}