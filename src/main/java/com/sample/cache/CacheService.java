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
