package com.fb.oauthsystem.website.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fb.oauthsystem.util.TimeGeter;
import com.fb.oauthsystem.website.entity.UserEntity;
import com.fb.oauthsystem.website.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FB
 * @Time 2019年5月11日21点43分
 * @Description 用来进行oauth对应的controller
 */
@RestController
public class OauthController {

    @Value("${project.settings.oauth.secretKey}")
    private String secret;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/oauth")
    String getOauth(@RequestParam String reUrl, HttpSession session){
        if (session.isNew()||session.getAttribute("uid")==null){
            return "oauthLogin.html";
        }
        if (reUrl==null||(!reUrl.contains("http"))){
            return "oauthReUrlError.html";
        }

        String uid = (String) session.getAttribute("uid");
        Map jsonToken = new HashMap();
        jsonToken.put("uid",uid);
        String jwt = generateOauthCode(uid,24*60*60);
        return jwt;
    }

    String generateOauthCode(String uid, int limitTime){
        String jwt = null;

        Map header = new HashMap();
        header.put("type","JWT");header.put("alg","HS256");

        Map payLoad = new HashMap();
        payLoad.put("type","oauth");
        payLoad.put("uid",uid);
        payLoad.put("generateTime",TimeGeter.getTimeStampInSecond());
        payLoad.put("limitTime",TimeGeter.getTimeStampInSecond()+limitTime);

        ObjectMapper mapper = new ObjectMapper();
        String headerJson = null; String signatureJson = null;
        String payLoadJson = null;
        try {
            String json;

            //获得head的json
            json = mapper.writeValueAsString(header);
            headerJson = Base64.getEncoder().encodeToString(json.getBytes());

            json = mapper.writeValueAsString(payLoad);
            payLoadJson = Base64.getEncoder().encodeToString(json.getBytes());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

        jwt = headerJson+"."+payLoadJson;
        jwt = jwt+"."+DigestUtils.md5DigestAsHex(jwt.getBytes());
        return jwt;
    }

    @RequestMapping("/oauth/getUser")
    Map getUserInformation(@RequestParam String token, @RequestParam String reUrl){
        String[] str = token.split("\\.");
        Map jsonMap = new HashMap();
        ObjectMapper mapper = new ObjectMapper();
        if (str.length==3&&str[2].equals(DigestUtils.md5DigestAsHex((str[0]+"."+str[1]).getBytes()))){
            System.out.println("-验证成功-");
            String payLoad = new String(Base64.getDecoder().decode(str[1]));
            try {
                Map payLoadMap = mapper.readValue(payLoad,Map.class);
                if ((Integer)(payLoadMap.get("limitTime"))>TimeGeter.getTimeStampInSecond()){
                    UserEntity user = userMapper.selectUserByUid((String) payLoadMap.get("uid"));
                    Map userInfo = new HashMap();
                    if (user!=null){
                        userInfo.put("uid",user.getUid());
                        userInfo.put("userName",user.getUserName());
                        userInfo.put("userInfo",user.getInfo());
                        userInfo.put("birthday",user.getBirthday());
                        try {
                            String userBase64 = Base64.getEncoder().encodeToString(mapper.writeValueAsString(userInfo).getBytes());
                            jsonMap.put("userInfo",userBase64);
                            //为了方便看效果
                            System.out.println(new String(Base64.getDecoder().decode(userBase64)));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            jsonMap.put("result",1);
        }else{
            System.out.println("验证失败:");
            System.out.println("header："+ new String(Base64.getDecoder().decode(str[0])));
            System.out.println("payLoad："+ new String(Base64.getDecoder().decode(str[1])));
            System.out.println("hs256:"+DigestUtils.md5DigestAsHex((str[0]+"."+str[1]).getBytes()));
            jsonMap.put("userInfo",null);
            jsonMap.put("result",0);
        }
        return jsonMap;
    }
}
