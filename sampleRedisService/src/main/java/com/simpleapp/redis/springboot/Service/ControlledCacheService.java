package com.simpleapp.redis.springboot.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ControlledCacheService {

    private static final String CONTROLLED_PREFIX = "myControlledPrefix_";

    public static String getCacheKey(String relevant) {
        return CONTROLLED_PREFIX + relevant;
    }

    @Cacheable(cacheNames = "myControlledCache", key = "T(com.simpleapp.redis.springboot.Service.ControlledCacheService).getCacheKey(#relevant)")
    public String getFromCache(String relevant) {
        return null;
    }

    @CachePut(cacheNames = "myControlledCache", key = "T(com.simpleapp.redis.springboot.Service.ControlledCacheService).getCacheKey(#relevant)")
    public String populateCache(String relevant, String unrelevantTrackingId) {
        return "my custom cache data";
    }

    @CacheEvict(cacheNames = "myControlledCache", key = "T(com.simpleapp.redis.springboot.Service.ControlledCacheService).getCacheKey(#relevant)")
    public void removeFromCache(String relevant) {
        log.info("Flushing all cache of '{}'", relevant);
    }
}
