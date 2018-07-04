package com.cms_cloudy.redis.service;

import java.util.List;
import java.util.Map;
/**
 * Redis服务Service
 * @author WangSc
 *
 */
public interface IRedisService {

	/**根据KEY查询存到REDIS服务里的数据**/
	public List<Map> selectPartDataByRedis(Map<String,Object> map);
    /**批量删除以某字符串前缀的key的REDIS缓存**/
	public void deleteRedisByKey(String key);
}
