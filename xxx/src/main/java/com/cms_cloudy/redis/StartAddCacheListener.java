package com.cms_cloudy.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.cms_cloudy.component.service.IPartPrimaryAttributesService;
import net.sf.json.JSONArray;
/**
 * 监听器，用于项目启动的时候初始化信息
 * @author WangSc
 */
@Component(value="ApplicationListenerContextRefreshedEvent")
public class StartAddCacheListener implements ApplicationListener<ContextRefreshedEvent>
{
 //日志
 private final Logger log= Logger.getLogger(StartAddCacheListener.class);
 @Autowired 
 public RedisUtil<Object> redisCache;
 @Autowired
private IPartPrimaryAttributesService partPrimaryAttributesService;
 /**
  * 启动服务查询加载redis
  */
 public void onApplicationEvent(ContextRefreshedEvent event) 
 {
//	   Object  jsonInRedis  = redisCache.getCacheObjects("partDataAllList");
//       if(null == jsonInRedis){
//   		       Map<String, Object> partDataAllList = partPrimaryAttributesService.selectPartDatasAllForRedis();
//   		       //初始化数据
//				if(null !=partDataAllList &&  null != partDataAllList.get("partDataList")){
//					String jsonList = JSONArray.fromObject(partDataAllList.get("partDataList")).toString();
//		            redisCache.setCacheObject("partDataAllList", jsonList);
//				}
//				if(null !=partDataAllList &&  null != partDataAllList.get("showName")){
//		               redisCache.setCacheObject("showName", partDataAllList.get("showName"));
//				}
//			}
  }
}