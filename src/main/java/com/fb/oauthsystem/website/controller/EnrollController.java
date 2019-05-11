package com.fb.oauthsystem.website.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fb.oauthsystem.util.RandomGeter;
import com.fb.oauthsystem.util.TimeGeter;
import com.fb.oauthsystem.website.entity.UserEntity;
import com.fb.oauthsystem.website.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FB
 * @Time 2019年5月11日21点43分
 * @Description 用来进行用户注册的controller
 */
@RestController
public class EnrollController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("enroll")
    Map enrollPage(@RequestParam String userJson){
        Map returnMap = new HashMap<Object,Object>();
        returnMap.put("result","-1");
        ObjectMapper mapper = new ObjectMapper();
        UserEntity user;
        try {
            user = mapper.readValue(userJson, UserEntity.class);
            user.setUid(TimeGeter.getTimeString("yyyyMMddhh")+ RandomGeter.getRandomNumber(4));
            user.setReg_time(TimeGeter.getTimeString("yyyy-MM-dd hh:mm:ss"));
        } catch (IOException e) {
            e.printStackTrace();
            return returnMap;
        }
        userMapper.insertNewUserByMap(user);
        returnMap.put("result",1);
        returnMap.put("uid",user.getUid());
        return returnMap;
    }
}
