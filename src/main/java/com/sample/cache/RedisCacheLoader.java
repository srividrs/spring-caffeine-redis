package com.sample.cache;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.CacheWriter;
import com.github.benmanes.caffeine.cache.RemovalCause;

//@Component
public class RedisCacheLoader<K,V> implements CacheLoader<K, V>, CacheWriter<K, V>{
	
	@Autowired
	@Qualifier("redisTemplate")
	RedisTemplate<K, V> template;
	
	String cacheName;
	
	public RedisCacheLoader(String cacheName) {
		this.cacheName = cacheName;
	}
	
	@Override
	public void write(K key, V value) {
		getClientName();
		System.out.println("Writing Key & Value into Redis------");
		template.opsForValue().set(key, value);
		testSleep();
		template.convertAndSend("CACHE_INVALIDATE",cacheName+":"+key.toString());
	}

	@Override
	public void delete(K key, V value, @NonNull RemovalCause cause) {
		getClientName();
		System.out.println("Deleting Key & Value from Redis-------");
		try {
			template.opsForValue().set(key, null);
		}catch(Exception e) {
			e.printStackTrace();
		}
		testSleep();
		template.convertAndSend("CACHE_INVALIDATE",cacheName+":"+key.toString());
	}

	@Override
	public V load(K key) throws Exception {
		getClientName();
		System.out.println("Loading data from Redis--------");
		V v =  template.opsForValue().get(key);
		testSleep();
		if(v == null)
			System.out.println("Value from Redis is null");
		return v;
	}

	void getClientName(){
		RedisServerCommands conn = template.getConnectionFactory().getConnection().serverCommands();
		System.out.println(conn.getClientName());
	}
	void testSleep() {
	//  need to send cache name 
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			System.out.println("Awake------");
	}
}
