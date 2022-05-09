package com.hypocriticalfish.crowdfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 14:31
 * @Description
 */
// 启用Feign客户端功能
@EnableFeignClients
@SpringBootApplication
public class ProjectMain {

    public static void main(String[] args) {
        SpringApplication.run(ProjectMain.class, args);
    }

}