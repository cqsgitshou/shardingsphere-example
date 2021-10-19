package com.sharding.jdbc.demo.controller;

import com.sharding.jdbc.demo.entity.User;
import com.sharding.jdbc.demo.entity.UserOrder;
import com.sharding.jdbc.demo.mapper.UserMapper;
import com.sharding.jdbc.demo.mapper.UserOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author: 学相伴-飞哥
 * @description: UserController
 * @Date : 2021/3/10
 */
@RestController
@RequestMapping("/userorder")
public class UserOrderController {
    @Autowired
    private UserOrderMapper userOrderMapper;
    @GetMapping("/save")
    public String insert(UserOrder userOrder ) {

        userOrder.setCreateTime(new Date());
        userOrderMapper.addUserOrder(userOrder);
        return "success";
    }
    @GetMapping("/listuserorder")
    public List<UserOrder> listuserorder() {
        return userOrderMapper.listuserorder();
    }

}