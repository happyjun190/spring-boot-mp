package com.mp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 配置
 * Created by wushenjun on 2018/5/31.
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                //.host("localhost:8080")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mp.web"))//controller路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("wechat mp api details")
                .description("API文档详细描述：微信公众号接口文档")
                .termsOfServiceUrl("http://www.github.com/kongchen/swagger-maven-plugin")
                .build();
    }
}
