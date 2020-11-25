package com.simpleapp.redis.springboot.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

    private static final String CACHE_PREFIX = "myCachePrefix_";

    public static String getCacheKey(String relevant) {
        return CACHE_PREFIX + relevant;
    }

    @Cacheable(cacheNames = "myCache", key = "T(com.simpleapp.redis.springboot.Service.CacheService).getCacheKey(#relevant)")
    public String cacheThis(String relevant, String unrelevantTrackingId){
        log.info("Not returning from cache. Tracking ID: {}!", unrelevantTrackingId);
        return "myCache Value";
    }

    @CacheEvict(cacheNames = "myCache", key = "T(com.simpleapp.redis.springboot.Service.CacheService).getCacheKey(#relevant)")
    public void flushCache(String relevant) {
        log.info("Flushing everything for '{}'!", relevant);
    }
}
