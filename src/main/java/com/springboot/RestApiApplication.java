package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author landyl
 * @create 4:38 PM 03/07/2018
 */
@EnableAutoConfiguration
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages = "com.springboot" )
@PropertySource("classpath:config/test.yml")
public class RestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class,args);
    }
}
