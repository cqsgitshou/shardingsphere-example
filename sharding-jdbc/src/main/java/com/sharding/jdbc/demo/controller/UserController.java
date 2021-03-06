package com.sharding.jdbc.demo.controller;

import com.sharding.jdbc.demo.entity.User;
import com.sharding.jdbc.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
/**
 * @author: 学相伴-飞哥
 * @description: UserController
 * @Date : 2021/3/10
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/save")
    public String insert(User user ) {

        user.setNickname("zhangsan"+ new Random().nextInt());
        user.setPassword("1234567");
        user.setSex(user.getSex()); // 垃圾代码
        user.setBirthday("2021-10-10");
        user.setAge(user.getAge());
        userMapper.addUser(user);
        return "success";
    }
    @GetMapping("/listuser")
    public List<User> listuser() {
        return userMapper.findUsers();
    }

}