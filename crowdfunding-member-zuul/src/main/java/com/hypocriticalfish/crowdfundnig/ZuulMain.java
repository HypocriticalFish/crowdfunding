package com.hypocriticalfish.crowdfundnig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/8 0:07
 * @Description
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulMain {

    public static void main(String[] args) {

        SpringApplication.run(ZuulMain.class, args);
    }
}
