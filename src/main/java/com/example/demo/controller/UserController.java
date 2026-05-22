package com.example.demo.controller;

import com.example.demo.entity.User;

import com.example.demo.entity.UserEn;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.common.Result;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    private UserMapper userMapper;

    public UserController(UserService userService) {

        this.userService = userService;

    }

    @GetMapping("/user")
    public User getUser() {
        return userService.getUser();

    }

    @GetMapping("/test")
    public List<UserEn> testMyBatisPlus() {
        // 1. 插入一条数据（不需要写 insert 语句）
        UserEn userEn = new UserEn();
        userEn.setNickname("袁文林569");
        userMapper.insert(userEn); // 执行完后，user.getId() 会自动获取到自增的 ID

        // 2. 根据 ID 查询
        long id = userEn.getId();

        UserEn foundUser = userMapper.selectById(id);

        System.out.println("查询到的用户: " + foundUser);

        List<UserEn> list = new ArrayList<>();

        if (foundUser != null) {
            list.add(foundUser);

        }

        return list;
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody UserEn userEn) {
        // 模拟业务逻辑验证：假设判断注册的昵称是不是已经存在了
        if ("袁文林569".equals(userEn.getNickname())) {

            // 🔥 直接用 throw 扔出你的自定义异常！
            throw new BizException("该昵称已被占用，请换一个！");
        }

        // 正常的保存逻辑...
        return Result.success("注册成功");
    }

}