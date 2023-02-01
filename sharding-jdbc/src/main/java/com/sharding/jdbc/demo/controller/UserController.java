package com.sharding.jdbc.demo.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.sharding.jdbc.demo.entity.User;
import com.sharding.jdbc.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
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
@Slf4j
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/save")
    public String insert(User user ) {

        log.info("start:{}", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        for(int i=0;i<10;i++){
            user.setNickname("zhangsan"+ new Random().nextInt());
            user.setPassword("1234567");
            user.setSex(1); // 垃圾代码
            user.setBirthday("2021-10-10");
            user.setAge(i);
            userMapper.addUser(user);
        }

        log.info("end:{}", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        return "success";
    }
    @GetMapping("/listuser")
    public List<User> listuser() {
        List<User> list = userMapper.findUsers();
        System.out.println(list.size());
        for(User user: list){
            System.out.println(user.getId());
        }
        return list;
    }

    @GetMapping("/listuserorder")
    public List<User> listuserorder() {
        List<User> list = userMapper.listuserorder("zhangsan1173181170");
        System.out.println(list.size());
        for(User user: list){
            System.out.println(user.getId());
        }
        return list;
    }

}