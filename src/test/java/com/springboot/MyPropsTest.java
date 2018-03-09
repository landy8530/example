package com.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

/**
 * @author landyl
 * @create 6:22 PM 03/08/2018
 */



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MockServletContext.class)// 指定spring-boot的启动类
@WebAppConfiguration
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages = "com.springboot" )
public class MyPropsTest {
    @Autowired
    private MyProps myProps;

    @Test
    public void propsTest() throws JsonProcessingException {
        System.out.println("simpleProp: " + myProps.getSimpleProp());
        System.out.println("arrayProps: " + Arrays.toString(myProps.getArrayProps()));
        System.out.println("listProp1: " + myProps.getListProp1());
        System.out.println("listProp2: " + myProps.getListProp2());
        System.out.println("mapProps: " + myProps.getMapProps());
    }
}
