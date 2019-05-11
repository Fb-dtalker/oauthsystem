package com.fb.oauthsystem.website.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author FB
 * @Time 2019年5月11日21点43分
 * @Description 对应sql表的用户entity接口类
 */
@EntityScan
@Data
@Getter
@Setter
public class UserEntity {

    private String uid;
    private String userName;
    private String passWord;
    private String birthday;
    private String info;
    private String reg_time;

}
