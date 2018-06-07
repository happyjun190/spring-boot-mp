package com.mp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wushenjun on 2018/5/31.
 */
@SpringBootApplication
public class SpringBootMPApplication {
    private static final Logger logger = LoggerFactory.getLogger(SpringBootMPApplication.class);

    public static void main(String[] args) {
        logger.info("Start  SpringBootMPApplication.");
        SpringApplication.run(SpringBootMPApplication.class, args);
    }
}
