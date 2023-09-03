package com.atrs.airticketreservationsystem.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        //redis://192.168.80.128:6379   redis://8.140.17.235:6379
        config.useSingleServer().setAddress("redis://8.140.17.235:6379").setPassword("123456");
        return Redisson.create(config);
    }
}