package com.sharding.jdbc.demo.entity;
import lombok.Data;
import java.util.Date;
/**
 * @author: 学相伴-飞哥
 * @description: User
 * @Date : 2021/3/10
 */
@Data
public class UserOrder {
    // 主键
    private Long orderid;
    // 订单编号
    private String ordernumber;
    // 用户ID
    private Long userid;
    // 产品id
    private Long productid;
    // 创建时间
    private Date createTime;

    private String yearmonth;
}