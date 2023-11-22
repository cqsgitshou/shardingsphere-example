package com.sharding.jdbc.demo.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.sharding.jdbc.demo.entity.User;
import com.sharding.jdbc.demo.mapper.UserMapper;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import io.shardingsphere.transaction.api.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 写入数据，默认写入主数据库
     * @param user
     * @return
     */
    @GetMapping("/save")
    @Transactional
    @ShardingTransactionType(TransactionType.XA)
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
        List<User> list = userMapper.findUsers();
        for(User IN: list){
            System.out.println(IN.getId());
        }
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
    /**
     * 分片查询，会所有表查询一遍,默认查询从节点数据库
     * @return
     */
    @GetMapping("/listuser2")
    public List<User> listuser2() {
        List<User> list = userMapper.listuserorderById(827551755389632513L);
        System.out.println(list.size());
        for(User user: list){
            System.out.println(user.getId());
        }
        return list;
    }
    /**
     * 分片查询，会所有表查询一遍,默认查询从节点数据库
     * @return
     */
    @GetMapping("/getuserById")
    public User getuser() {
        User user = userMapper.getuserById(827551755389632513L);
        System.out.println(user);
        return user;
    }

    /**
     * 非分片查询，会所有表查询一遍,默认查询从节点数据库
     * @return
     */
    @GetMapping("/getuserByName")
    public User getuserByName() {
        User user = userMapper.getuserByName("zhangsan1274416426");
        System.out.println(user);
        return user;
    }
    /**
     * 非分片查询，会所有表查询一遍,默认查询从节点数据库
     * @return
     */
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