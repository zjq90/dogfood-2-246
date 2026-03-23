package com.weibo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot应用启动类
 */
@SpringBootApplication
@MapperScan("com.weibo.mapper")
@EnableTransactionManagement
public class weiboApplication {

    public static void main(String[] args) {
        SpringApplication.run(weiboApplication.class, args);
        System.out.println("======================================");
        System.out.println("   weibo Spring Boot 应用启动成功!   ");
        System.out.println("   访问地址: http://localhost:8080     ");
        System.out.println("======================================");
    }
}
