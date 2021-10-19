package com.sharding.jdbc.demo.entity;
import lombok.Data;
/**
 * @author: 学相伴-飞哥
 * @description: User
 * @Date : 2021/3/10
 */
@Data
public class User {
    // 主键
    private Integer id;
    // 昵称
    private String nickname;
    // 密码
    private String password;
    // 性
    private Integer sex;
    // 性
    private String birthday;

    private Integer age;
}