package com.waimai.skyserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc OpenAPI 配置类
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("苍穹外卖项目接口文档")
                        .description("苍穹外卖项目 API 文档，基于 SpringDoc OpenAPI 3.0")
                        .version("2.0")
                        .contact(new Contact()
                                .name("开发团队")
                                .email("dev@example.com")));
    }
}
