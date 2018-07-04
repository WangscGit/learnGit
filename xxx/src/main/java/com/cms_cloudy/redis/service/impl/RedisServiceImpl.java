package com.cms_cloudy.redis.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cms_cloudy.redis.RedisUtil;
import com.cms_cloudy.redis.service.IRedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component("IRedisService")
public class RedisServiceImpl implements IRedisService{
	@Autowired 
	public RedisUtil<Object> redisCache;
	public List<Map> selectPartDataByRedis(Map<String, Object> map) {
		List<Map> list = null;
		Object listInRedis = null;
		Object  jsonInRedis=null;
		if(null != map.get("key")){
			jsonInRedis= redisCache.getCacheObjects(map.get("key").toString());
			if(jsonInRedis != null){
				listInRedis= JSON.parseArray(jsonInRedis.toString(), Map.class);
			}
			if (listInRedis instanceof List) {
				list = (List<Map>) listInRedis;
			}
			return list;
		}else{
			return null;
		}
	}

	public void deleteRedisByKey(String key) {
		  JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);  
	       Jedis jedis = null;  
	        try {  
	            jedis = jedisPool.getResource();  
	            Set<String> set = jedis.keys(key +"*");  
	            Iterator<String> it = set.iterator();  
	             while(it.hasNext()){ 
	                String keyStr = it.next();  
	                jedis.del(keyStr);  
	              }
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (jedis != null)  
	                jedis.close();  
	        }  
	        jedisPool.destroy();  
	}

}
