package com.sample.cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

//@Component
public class CacheMessageListener implements MessageListener {
	//@Autowired
	//@Qualifier("redisTemplate")
	RedisTemplate redisTemplate;
	//@Autowired
	CacheManager cacheManager;
	
	String clientId;
	@Autowired
 public CacheMessageListener(RedisTemplate redisTemplate, CacheManager cacheManager) {
 super();
 this.redisTemplate = redisTemplate;
 this.cacheManager = cacheManager;
 }
	
	
 
 @Override
 public void onMessage(Message message, byte[] pattern) {
// cacheManager.getCache("random").evictIfPresent("0");
	 String cacheMessage = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
	 System.out.println("recevice a redis topic message, clear local cache"+ cacheMessage);
	 String[] values = cacheMessage.split(":");
	// CaffeineCache cache = (CaffeineCache) cacheManager.getCache(values[0]);
	// cache.evict(values[0]);
 //.get(values[1])..evictIfPresent(values[1]);
 //redisCaffeineCacheManager.clearLocal(cacheMessage.getCacheName(), cacheMessage.getKey());
 }
}