package com.hypocriticalfish.crowdfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/7 23:23
 * @Description
 */
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
public class AuthenticationConsumerMain {

    public static void main(String[] args) {

        SpringApplication.run(AuthenticationConsumerMain.class, args);
    }
}
