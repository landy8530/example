package com.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author landyl
 * @create 4:27 PM 03/07/2018
 * Spring boot结合Maven实现不同环境的配置
 */
@RestController
public class SampleController {
    @Autowired
    private Environment env;

    @RequestMapping("/sample")
    public String sample(){

        return "spring boot success ! and profile is ==>"+
                env.getProperty("spring.profiles.active")+"=====>"+
                env.getProperty("ftp");
    }
    @RequestMapping("/hello")
    public String hello(){
        return "hello world" ;
    }
}
