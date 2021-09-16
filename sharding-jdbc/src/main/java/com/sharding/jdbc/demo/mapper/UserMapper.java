package com.sharding.jdbc.demo.mapper;

import com.sharding.jdbc.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * @author: 学相伴-飞哥
 * @description: UserMapper
 * @Date : 2021/3/10
 */
public interface UserMapper {
    /**
     * @author 学相伴-飞哥
     * @description 保存用户
     * @params [user]
     * @date 2021/3/10 17:14
     */
    @Insert("insert into c_user(nickname,password,sex,birthday) values(#{nickname},#{password},#{sex},#{birthday})")
    void addUser(User user);
    /**
     * @author 学相伴-飞哥
     * @description 保存用户
     * @params [user]
     * @date 2021/3/10 17:14
     */
    @Select("select * from c_user")
    List<User> findUsers();
}