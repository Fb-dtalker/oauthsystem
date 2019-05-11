package com.fb.oauthsystem.website;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HASEE
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    String testPage(){
        return "测试页面";
    }
}
