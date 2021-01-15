package com.sample.cache;

import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
	@Cacheable(cacheNames = {"random"})
	public String getRandom(String key) {
		System.out.println(" Service Get Random: "+key);
		return UUID.randomUUID().toString();
	}
	
	@CacheEvict(cacheNames = {"random"})
	public boolean delete(String key) {
		System.out.println(" Service Delete Random: "+key);
		return true;
	}
}
