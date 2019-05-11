package com.fb.oauthsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.fb.oauthsystem.website.mapper")
public class OauthsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthsystemApplication.class, args);
    }

}
