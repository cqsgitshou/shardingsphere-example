package com.sharding.jdbc.demo.service;

import com.sharding.jdbc.demo.entity.User;
import com.sharding.jdbc.demo.entity.UserOrder;
import com.sharding.jdbc.demo.mapper.UserMapper;
import com.sharding.jdbc.demo.mapper.UserOrderMapper;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import io.shardingsphere.transaction.api.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author: 学相伴-飞哥
 * @description: UserService
 * @Date : 2021/3/14
 */
@Service
public class UserOrderService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserOrderMapper orderMapper;
    @ShardingTransactionType(TransactionType.XA)
    @Transactional(rollbackFor = Exception.class)
    public int saveUserOrder(User user, UserOrder order) {
        userMapper.addUser(user);

        orderMapper.addUserOrder(order);
        //int a = 1/0; //测试回滚，统一提交的话，将这行注释掉就行
        return 1;
    }
}