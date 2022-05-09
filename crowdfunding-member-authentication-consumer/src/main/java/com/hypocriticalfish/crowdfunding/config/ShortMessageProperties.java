package com.hypocriticalfish.crowdfunding.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/8 16:33
 * @Description
 */
@Component
@ConfigurationProperties(prefix = "short.message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortMessageProperties {
    private String appcode;
    private String templateId;

}
