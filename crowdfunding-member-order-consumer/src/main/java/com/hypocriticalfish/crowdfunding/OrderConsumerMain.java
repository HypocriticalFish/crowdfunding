package com.hypocriticalfish.crowdfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 21:38
 * @Description
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class OrderConsumerMain {

    public static void main(String[] args) {

        SpringApplication.run(OrderConsumerMain.class, args);
    }
}
