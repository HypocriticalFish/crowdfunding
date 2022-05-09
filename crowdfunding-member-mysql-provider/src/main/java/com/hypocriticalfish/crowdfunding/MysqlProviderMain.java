package com.hypocriticalfish.crowdfunding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/7 20:16
 * @Description
 */
@MapperScan("com.hypocriticalfish.crowdfunding.mapper")
@SpringBootApplication
public class MysqlProviderMain {
    public static void main(String[] args) {
        SpringApplication.run(MysqlProviderMain.class, args);
    }
}
