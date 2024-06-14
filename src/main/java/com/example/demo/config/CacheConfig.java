package com.example.demo.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

// https://stackoverflow.com/questions/23708905/application-cache-v-s-hibernate-second-level-cache-which-to-use
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfiguration(Duration.ofMinutes(3)))
                .withCacheConfiguration("allMovieReadDto", cacheConfiguration(Duration.ofMinutes(5)))
                .withCacheConfiguration("pageMovieReadDto", cacheConfiguration(Duration.ofMinutes(5)))
                .transactionAware()
                .build();
    }

    private RedisCacheConfiguration cacheConfiguration(Duration duration) {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(duration);
    }
}
