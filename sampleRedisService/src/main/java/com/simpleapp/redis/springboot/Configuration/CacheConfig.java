package com.simpleapp.redis.springboot.Configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Configuration
@EnableConfigurationProperties(RedisConfigurationProperties.class)
@Slf4j
public class CacheConfig {

    private static RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(timeoutInSeconds));
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisConfigurationProperties props) {
        log.info("Redis (/Lettuce) configuration enabled. With cache timeout " + props.getTimeoutSeconds() + " seconds.");

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(props.getRedisHost());
        redisStandaloneConfiguration.setPort(props.getRedisPort());
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(RedisConfigurationProperties props) {
        return createCacheConfiguration(props.getTimeoutSeconds());
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisConfigurationProperties props) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        for (Entry<String, Long> cacheNameAndTimeout: props.getCacheExpirations().entrySet()) {
            cacheConfigurations.put(cacheNameAndTimeout.getKey(), createCacheConfiguration(cacheNameAndTimeout.getValue()));
        }
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration(props)).
                withInitialCacheConfigurations(cacheConfigurations).build();
    }
}
