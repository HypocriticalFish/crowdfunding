package com.hypocriticalfish.test;

import com.hypocariticalfish.crowdfunding.RedisProviderMain;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/7 22:40
 * @Description
 */
@SpringBootTest(classes = RedisProviderMain.class)
@Slf4j
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    final private Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Test
    public void testSet() {

        ValueOperations<String, String> operations = redisTemplate.opsForValue();


        operations.set("apple", "red");
        logger.info("设置成功！");
    }
}
