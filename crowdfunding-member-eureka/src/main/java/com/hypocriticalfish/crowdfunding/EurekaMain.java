package com.hypocriticalfish.crowdfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/7 19:40
 * @Description
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaMain {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain.class, args);
    }
}
