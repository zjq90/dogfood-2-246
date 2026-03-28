package com.weibo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot 应用启动类
 * 豆瓣社交平台主入口
 * 
 * @author weibo Team
 */
@SpringBootApplication
@MapperScan("com.weibo.mapper")
@EnableTransactionManagement
public class WeiboApplication {

    /**
     * 应用主入口
     * 
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(WeiboApplication.class, args);
        System.out.println("======================================");
        System.out.println("   豆瓣 Spring Boot 应用启动成功!   ");
        System.out.println("   访问地址: http://localhost:8080     ");
        System.out.println("======================================");
    }
}
