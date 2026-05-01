package com.waimai.skyserver;

import com.waimai.skycommon.properties.JwtProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.waimai.skyserver", "com.waimai.skycommon"})
@EnableTransactionManagement // 开启事务
@EnableCaching //开启缓存注解功能
@MapperScan({"com.waimai.skyserver.mapper"})
@EnableScheduling // 开启定时任务功能
public class SkyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyServerApplication.class, args);
    }

}
