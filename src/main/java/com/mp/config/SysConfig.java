package com.mp.config;

import com.mp.util.SysMpPropUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by wushenjun on 2018/6/8.
 */
@Configuration
@EnableConfigurationProperties({SysMpProperties.class})
public class SysConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysMpProperties sysMpProperties;
    @PostConstruct
    public void postConstruct(){
        SysMpPropUtils.appId = sysMpProperties.getAppId();
        SysMpPropUtils.secret = sysMpProperties.getSecret();
        SysMpPropUtils.token = sysMpProperties.getToken();
        SysMpPropUtils.aesKey = sysMpProperties.getAesKey();
    }
}
