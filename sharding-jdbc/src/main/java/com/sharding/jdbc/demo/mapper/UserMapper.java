package com.sharding.jdbc.demo.mapper;

import com.sharding.jdbc.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
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
    @Insert("insert into ksd_user(nickname,password,sex,birthday,age) values(#{nickname},#{password},#{sex},#{birthday},#{age})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void addUser(User user);
    /**
     * @author 学相伴-飞哥
     * @description 保存用户
     * @params [user]
     * @date 2021/3/10 17:14
     */
    @Select("select * from ksd_user order by create_time desc,id desc limit 0,10")
    List<User> findUsers();
    @Select("select u.* from ksd_user u INNER JOIN user_order o on u.nickname=o.nickname where u.nickname=#{nickname} order by u.create_time desc,u.id desc limit 0,10")
    List<User> listuserorder(String nickName);
}