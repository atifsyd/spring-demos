package com.simpleapp.redis.springboot;

import ch.qos.logback.classic.Logger;
import com.simpleapp.redis.springboot.Service.CacheService;
import com.simpleapp.redis.springboot.Service.ControlledCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@Slf4j
@EnableCaching
public class SpringbootApplication implements CommandLineRunner {

	@Autowired
	CacheService cacheService;

	@Autowired
	ControlledCacheService controlledCacheService;

	private String getFromControlledCache(String param) {
		String fromCache = controlledCacheService.getFromCache(param);
		if(fromCache == null) {
			log.info("Cache is empty. Adding data to cache now");
			String newValue = controlledCacheService.populateCache(param, UUID.randomUUID().toString());
			log.info("Populate Cache with: {}", newValue);
			return newValue;
		}
		log.info("Returning from cache: {}", fromCache);
		return fromCache;
	}

	@Override
	public void run(String... args) throws RuntimeException {
		// Testing cache for cache Service
		String firstString = cacheService.cacheThis("data1", UUID.randomUUID().toString());
		log.info("First: {}", firstString);
		String secondString = cacheService.cacheThis("data1", UUID.randomUUID().toString());
		log.info("Second: {}", secondString);
		String thirdString = cacheService.cacheThis("data2", UUID.randomUUID().toString());
		log.info("Third: {}", thirdString);
		String fourthString = cacheService.cacheThis("data2", UUID.randomUUID().toString());
		log.info("Fourth: {}", fourthString);

		// Testing cache for Controlled Cache Service
		log.info("Starting controlled cache: -------");
		String controlledFirst = getFromControlledCache("firstData");
		log.info("Controlled First: {}", controlledFirst);
		String controlledSecond = getFromControlledCache("secondData");
		log.info("Controlled Second: {}", controlledSecond);
		getFromControlledCache("firstData");
		getFromControlledCache("secondData");
		getFromControlledCache("thirdData");

		// Testing Cache purging functionality
		log.info("Clearing all from Cache");
		List<String> cacheParams = new ArrayList<>(List.of("data1", "data2", "firstData", "secondData", "thirdData"));
		for (String cacheParam: cacheParams) {
			cacheService.flushCache(cacheParam);
			controlledCacheService.removeFromCache(cacheParam);
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
