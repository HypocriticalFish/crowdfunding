package com.hypocriticalfish.crowdfunding.test;

import com.hypocriticalfish.crowdfunding.util.CrowdfundingUtil;
import com.hypocriticalfish.crowdfunding.util.HttpUtils;
import com.hypocriticalfish.crowdfunding.util.ResultEntity;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/8 14:45
 * @Description
 */
@SpringBootTest
public class ShortMessageTest {
    @Test
    public void testSendMessage(){
        ResultEntity<String> sendMessageResultEntity = CrowdfundingUtil.sendShortMessage("18157708634", "c91e49f1530242858bed37c751f5ed07", "0002");
    }
}
