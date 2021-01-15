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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
	@Autowired
	CacheService cacheService;
	
	@GetMapping("/key/{keyId}")
	public String get(@PathVariable(name = "keyId") String key) {
		System.out.println("Controller Get Random: "+key);
		return cacheService.getRandom(key);
	}
	
	@DeleteMapping("/key/{keyId}")
	public boolean delete(@PathVariable(name = "keyId") String key) {
		System.out.println("Controller Get Random: "+key);
		return cacheService.delete(key);
	}
//	@PostMapping("/key")
//	public String set(String key) {
//		return cacheService.;
//	}

}
