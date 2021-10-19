package com.sharding.jdbc.demo.mapper;

import com.sharding.jdbc.demo.entity.UserOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 学相伴-飞哥
 * @description: UserMapper
 * @Date : 2021/3/10
 */
@Mapper
@Repository
public interface UserOrderMapper {
    /**
     * @author 学相伴-飞哥
     * @description 保存订单
     * @params [user]
     * @date 2021/3/10 17:14
     */
    @Insert("insert into user_order(create_time,yearmonth) values(#{createTime},#{yearmonth})")
    @Options(useGeneratedKeys = true,keyColumn = "order_id",keyProperty = "orderid")
    void addUserOrder(UserOrder userOrder);

    @Select("select * from  user_order")
    List<UserOrder> listuserorder();
}