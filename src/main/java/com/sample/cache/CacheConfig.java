/*
 * @author srividya.ramaswamy
 * @since Jan 12, 2021
 *
 * COPYRIGHT NOTICE:
 * Copyright (c) 2009 Infosys Technologies Limited, Electronic City,
 * Hosur Road, Bangalore - 560 100, India.
 * All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Infosys Technologies Ltd. ("Confidential Information"). You shall
 * not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered
 * into with Infosys.
 */


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
	
	//@Autowired
	//RedisCacheLoader loader;
	
	@Autowired
	@Qualifier("redisTemplate")
	RedisTemplate redisTemplate;
	
	
//	@Bean
//	public CacheManager cacheManager() {
//		SimpleCacheManager manager = new SimpleCacheManager();
//		manager.setCaches(Arrays.asList(
//	 new CaffeineCache("random", Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).writer(randomDataCacheLoader()).build(randomDataCacheLoader()))//.build(cacheLoader());
//	));
//		return manager;
//	}
//	
	
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
	
	
//	@Bean
//	public Caffeine caffeineConfig() {
//	    return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
//	}
	
//	@Bean
//	public CacheManager cacheManager(Caffeine caffeine) {
//	    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
//	    caffeineCacheManager.setCaffeine(caffeine);
//	    return caffeineCacheManager;
//	}
	
//	@Bean
//	public Caffeine caffeineConfig() {
//	    Caffeine caff =  Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).writer(cacheWriter());//.build(cacheLoader());
//	    caff.build(cacheLoader());
//	    return caff;
//	}
//	
//	@Bean
//	public LoadingCache loadingCache() {
//	    Caffeine caff =  Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).writer(cacheWriter());//.build(cacheLoader());
//	    return caff.build(cacheLoader());
//	    //return caff;
//	}

//	@Bean
//	public CacheManager cacheManager(Caffeine caffeine) {
//	    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
//	    caffeineCacheManager.setCaffeine(caffeine);
//	    return caffeineCacheManager;
//	}
	
	
//	public <K,V> CacheWriter<K,V> cacheWriter() {
//		return new CacheWriter<K, V>() {
//
//			@Override
//			public void write(K key, V value) {
//				System.out.println("Writing Key & Value");
//				
//			}
//
//			@Override
//			public void delete(K key, V value, @NonNull RemovalCause cause) {
//				System.out.println("Deleting Key & Value");
//
//			}
//		};
//	}
	
//	public <K,V> CacheLoader<K, V> cacheLoader(){
//		return new CacheLoader<K, V>() {
//			
//			@Override
//			public @Nullable V load(@NonNull K key) throws Exception {
//				System.out.println("Loading cache --------");
//				if(key.toString().equals("0")) {
//					System.out.println("Returning from level2 cache --------");
//					return (@Nullable V) UUID.randomUUID().toString();
//
//				}
//					else {
//						System.out.println("Returning NULL from level2 cache --------");
//					return null;
//					}
//			}
//		};
//	}
	
	
	
//	@Bean
//	public RedisConnectionFactory standaloneConnectionFactory() {
//		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
//		config.setPassword(redisPassword);
//		return new JedisConnectionFactory(config);
//	}
//	 @Bean
//	    public RedisTemplate<Object, Object> redisTemplate(final RedisConnectionFactory connectionFactory) {
//	        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//	        template.setConnectionFactory(connectionFactory);
//	        return template;
//	    }

   

}
