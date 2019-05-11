package com.fb.oauthsystem.website.mapper;

import com.fb.oauthsystem.website.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.sql.ResultSet;
import java.util.Map;


/**
 * @author HASEE
 */
public interface UserMapper {
    @Insert("insert into user_table(uid,username,password,birthday,info,reg_time) values (#{uid},#{userName},#{passWord},#{birthday},#{info},#{reg_time})")
    int insertNewUserByMap(UserEntity user);

    @Select("select * from user_table where uid = #{uid}")
    UserEntity selectUserByUid(String uid);

    @Select("select * from user_table where username = #{userName} and password = #{passWord}")
    UserEntity selectUserByUsernameAndPassword(String userName, String passWord);

}
