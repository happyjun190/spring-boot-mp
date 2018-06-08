package com.mp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wushenjun on 2018/6/8.
 */
@Data
@ConfigurationProperties(prefix = "wechat.properties")
public class SysMpProperties {
    private String appId;
    private String secret;
    private String token;
    private String aesKey;
}
