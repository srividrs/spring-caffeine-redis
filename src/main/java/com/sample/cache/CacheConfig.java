

package com.sample.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {
	
		
	@Autowired
	@Qualifier("redisTemplate")
	RedisTemplate redisTemplate;
	
	
	@Bean
	public CaffeineCache randomCache() {
		 return new CaffeineCache("random", Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).writer(randomDataCacheLoader()).build(randomDataCacheLoader()));//.build(cacheLoader());

	}
	
	@Bean
	public <K,V> RedisCacheLoader<K, V> randomDataCacheLoader() {
		return new RedisCacheLoader<K, V>("random");
	}
	
	
	@Bean
	 public RedisMessageListenerContainer redisMessageListenerContainer() {
	 RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
	 redisMessageListenerContainer.setConnectionFactory(redisTemplate.getConnectionFactory());
	// CacheMessageListener cacheMessageListener = new CacheMessageListener(redisTemplate,cacheManager());
	 CacheMessageListener cacheMessageListener = new CacheMessageListener(redisTemplate,null);
	 redisMessageListenerContainer.addMessageListener(cacheMessageListener, new ChannelTopic("CACHE_INVALIDATE"));
	 return redisMessageListenerContainer;
	 }

   

}
