package com.heibaiying.springbootbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootBaseApplication {

    /*@SpringBootApplication 注解是一个复合注解,里面包含了@ComponentScan注解
    默认开启包扫描，扫描与主程序所在包及其子包，对于本工程而言 默认扫描 com.heibaiying.springbootbase*/
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBaseApplication.class, args);
    }

}

