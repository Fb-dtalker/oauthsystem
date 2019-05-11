package com.fb.oauthsystem.website.controller;

import com.fb.oauthsystem.website.entity.UserEntity;
import com.fb.oauthsystem.website.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FB
 * @Time 2019年5月11日21点43分
 * @Description 用来进行用户登录的controller
 */
@RestController
public class LogInOutController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/login")
    Map login(@RequestParam String userName, @RequestParam String passWord, HttpServletRequest request){
        HttpSession session = request.getSession();
        Map map = new HashMap();
        map.put("result","-1");
        UserEntity user = userMapper.selectUserByUsernameAndPassword(userName,passWord);
        if (user!=null){
            session.setAttribute("userState", user);
            session.setAttribute("uid",user.getUid());
            map.put("uid",user.getUid());
            map.put("result","1");
        }
        return map;
    }

    @RequestMapping("/checkState")
    Map checkState(HttpServletRequest request){
        HttpSession session = request.getSession();
        Map map = new HashMap();
        map.put("state","未登陆");
        if ((!session.isNew())&&session.getAttribute("uid")!=null) {
            System.out.println("已经登陆过了");
            map.put("uid", session.getAttribute("uid"));
            map.put("state","已登陆");
        }
        return map;
    }

    @RequestMapping("/logout")
    Map logout(HttpSession session){
        Map map = new HashMap();
        map.put("result","1");
        session.invalidate();
        return map;
    }
}
