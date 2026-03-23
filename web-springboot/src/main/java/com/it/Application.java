package com.it;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan("com.it.mapper")
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("======================================");
        System.out.println("   weibo Spring Boot 搴旂敤鍚姩鎴愬姛!   ");
        System.out.println("   Access address: http://localhost:8080     ");
        System.out.println("======================================");
    }
}
