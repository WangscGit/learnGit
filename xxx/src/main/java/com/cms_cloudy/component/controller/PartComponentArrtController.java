package com.cms_cloudy.component.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.cms_cloudy.component.dao.IUserPnDao;
import com.cms_cloudy.component.pojo.PartEvaluationEntity;
import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.cms_cloudy.component.pojo.TreeTableEntity;
import com.cms_cloudy.component.pojo.UserPn;
import com.cms_cloudy.component.service.IPartDataService;
import com.cms_cloudy.component.service.IPartEvaluationService;
import com.cms_cloudy.component.service.IPartPrimaryAttributesService;
import com.cms_cloudy.component.service.IUserPnService;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.redis.service.IRedisService;
import com.cms_cloudy.upload.pojo.FileUploadEntity;
import com.cms_cloudy.upload.service.IFileUploadService;
import com.cms_cloudy.user.controller.UserController;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.util.PropertiesUtils;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * 元器件管理Controller
 * @author WangSc
 */
@Controller
@RequestMapping(value="/partComponentArrt")
public class PartComponentArrtController extends BaseController{
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private IUserPnService userPnService;
	@Autowired
	private IPartEvaluationService partEvaluationService;
	@Autowired
	private IPartPrimaryAttributesService partPrimaryAttributesService;
	@Autowired
	private IPartDataService partDataService;
	@Autowired 
	public IRedisService redisService;
	@Autowired
	private IUserPnDao userPnDao;
	@Autowired
	private IPartDataService iPartDataService;
    @Autowired
    private IFileUploadService fileUploadService;
	public static String GROUP_NAME = "";
	public static String English_NAME = "";
	static
{
	try {
		GROUP_NAME = PropertiesUtils.getProperties().getProperty(
			  "GROUP_NAME");
		English_NAME = PropertiesUtils.getProperties().getProperty(
				"English_NAME");
	} catch (IOException e) {
		e.printStackTrace();
		logger.error(" 字段定义类别初始化失败", e);
	}
}
	/**
	 * 查询常用库(我的收藏)集合
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectUserPnList.do")
	public void selectUserPnList(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<UserPn> userPnList = userPnService.selectUserPnList(map);
			String msg = JSONArray.fromObject(userPnList).toString();
			outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" 查询常用库(我的收藏)集合", e);
		}
	}
	/**
	 * 添加常用库(我的收藏)
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertUserPn.do")
	public void insertUserPn(HttpServletRequest request,HttpServletResponse response){
		try {
			String partNumber = request.getParameter("partNumber");
			String lang="zh";
			if(null != request.getSession().getAttribute("lang")){
				lang = request.getSession().getAttribute("lang").toString();
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (StringUtils.isEmpty(partNumber)) {
				if("zh".equals(lang)){
					resultMap.put("message", "parentNumber不能为空！");
				}else{
					resultMap.put("message", "parentNumber Can't be empty！");
				}
				resultMap.put("resultCode", 0);
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			UserPn userPn = new UserPn();
			userPn.setPartNumber(partNumber);
			// userid,从session中获取
			HrUser user=getUserInfo(request);
			if(user==null){
				if("zh".equals(lang)){
					resultMap.put("message", "请先登录！");
				}else{
					resultMap.put("message", "Please sign in first！");
				}
				resultMap.put("resultCode", 0);
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			userPn.setUserId(user.getUserId());
			userPn.setSelectedTime(new Date());
			// 添加前验证  userid、partNumber不能同时重复
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("userId", userPn.getUserId());
			map.put("partNumber", partNumber);
			int coun=userPnDao.isCollection(map);
			if(coun>0){
				if("zh".equals(lang)){
					resultMap.put("message", "已收藏！");
				}else{
					resultMap.put("message", "Already collected！");
				}
				resultMap.put("resultCode", 0);
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			userPnService.insertUserPn(userPn);
			if("zh".equals(lang)){
				resultMap.put("message", "收藏成功！");
			}else{
				resultMap.put("message", "Collection success！");
			}
			resultMap.put("resultCode", 1);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" 添加常用库(我的收藏)异常", e);
		}
	}
	/**
	 * 删除常用库(我的收藏)
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteUserPn.do")
	public void deleteUserPn(HttpServletRequest request,HttpServletResponse response){
		try {
			String partNumbers = request.getParameter("partNumbers");
			Map<String,Object> resultMap=new HashMap<String, Object>();
			if(StringUtils.isEmpty(partNumbers)){
				resultMap.put("message", "请选择一条数据！");
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			List<String> l=JSON.parseArray(partNumbers, String.class);
			// 从session获取userId
			Long userId=getUserInfo(request).getUserId();
			for(int i=0;i<l.size();i++){
				String partNumber=l.get(i);
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("partNumber", partNumber);
				map.put("userId", userId);
				userPnService.deleteUserPn(map);
			}
			resultMap.put("message", "删除成功！");
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response,jsonString);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除常用库(我的收藏)", e);
		}
	}
	/**
	 * 查询元器件评价
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectPartEvaluationList.do")
	public void selectPartEvaluationList(HttpServletRequest request,HttpServletResponse response){
		String PartNumber = request.getParameter("PartNumber");
		String pages = request.getParameter("page");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		// 分页初始化
		Integer pageNo = 1;
		List<Map<String, Object>> mapData;
		if (StringUtils.isNotEmpty(pages)) {
			pageNo = Integer.valueOf(pages);
		}
		Integer pageSize = 5;
		if (StringUtils.isNotEmpty(request.getParameter("pageNo"))) {
			pageNo = Integer.valueOf(request.getParameter("pageNo"));
		}
		if (StringUtils.isNotEmpty(request.getParameter("pageSize"))) {
			pageSize = Integer.valueOf(request.getParameter("pageSize"));
		}
		map.put("PartNumber", PartNumber);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		try {
			PageInfo<PartEvaluationEntity> page = partEvaluationService.selectPartEvaluationListByPage(map);
			if (null != page && page.getSize() > 0) {
				List<PartEvaluationEntity> partEvaluationList = page.getList();
				mapData = partPrimaryAttributesService.selectPartDateByPartNumber(map);
				map.put("Votes", 5);// 好评
				int bestEva = partEvaluationService.selectPartEvaluationCount(map);
				map.put("Votes", 3);// 中评
				int betterEva = 0;
				map.put("Votes", 1);// 差评
				int badEva = partEvaluationService.selectPartEvaluationCount(map);
				betterEva = Integer.valueOf(page.getTotal() + "") - (bestEva + badEva);// 中评
				mapJson.put("list", partEvaluationList);
				mapJson.put("total", partEvaluationList.size());
				mapJson.put("bestEva", bestEva);
				mapJson.put("betterEva", betterEva);
				mapJson.put("badEva", badEva);
				mapJson.put("count", page.getTotal());
				mapJson.put("pageCount", page.getPages());
				mapJson.put("currentPage", pageNo);
				mapJson.put("pageSize", pageSize);
				mapJson.put("mapData", mapData);
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			} else {
				mapJson.put("list", null);
				String msg = JSONArray.fromObject(mapJson).toString();
				outputJson(response, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询元器件评价", e);
		}
	}
	/**
	 * 添加元器件评价表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertPartEvaluation.do")
	public void insertPartEvaluation(HttpServletRequest request,HttpServletResponse response){
		String Votes = request.getParameter("Votes");
		String evaContent = request.getParameter("evaContent");
		String PartNumber = request.getParameter("PartNumber");
		PartEvaluationEntity partEvaluation = new PartEvaluationEntity();
		try {
			evaContent = java.net.URLDecoder.decode(evaContent, "UTF-8");
			partEvaluation.setCreateTime(new Date());
			partEvaluation.setEvaContent(evaContent);
			partEvaluation.setPartNumber(PartNumber);
			HrUser user = getUserInfo(request);
			if (null != user) {
				partEvaluation.setUserName(user.getLoginName());
			} else {
				partEvaluation.setUserName("admin");
			}
			partEvaluation.setVotes(Integer.valueOf(Votes));
			partEvaluationService.insertPartEvaluation(partEvaluation);
			String msg = JSONObject.fromObject(partEvaluation).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加元器件评价表", e);
		}
	}
	/**
	 * 更新元器件评价表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updatePartEvaluation.do")
	public void updatePartEvaluation(HttpServletRequest request,HttpServletResponse response){
		String Votes = request.getParameter("Votes");
		String evaContent = request.getParameter("evaContent");
		String id = request.getParameter("id");
		PartEvaluationEntity partEvaluation = new PartEvaluationEntity();
		try {
			evaContent = java.net.URLDecoder.decode(evaContent, "UTF-8");
			partEvaluation.setEvaContent(evaContent);
			partEvaluation.setVotes(Integer.valueOf(Votes));
			partEvaluation.setId(Long.valueOf(id));
			partEvaluationService.updatePartEvaluation(partEvaluation);
			String msg = JSONObject.fromObject(partEvaluation).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加元器件评价表", e);
		}
	}
	/**
	 * 删除元器件评价表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deletePartEvaluation.do")
	public void deletePartEvaluation(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("partEvaluationId");
		try {
			partEvaluationService.deletePartEvaluation(Integer.valueOf(id));
		String msg = JSONObject.fromObject(null).toString();
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除元器件评价表", e);
		}
	}
	/**
	 * 详细页树形结构
	 */
	@RequestMapping(value="/selectFieldByTreetable.do")
	public void selectFieldByTreetable(HttpServletRequest request,HttpServletResponse response){
		try {
		String lang="zh";
		if(null != request.getSession().getAttribute("lang")){
			lang = request.getSession().getAttribute("lang").toString();
		}
		String PartNumber = request.getParameter("PartNumber");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type",1);
		map.put("isUsed", true);
		map.put("minu", "Datesheet");
		map.put("hidePicture", "true");
		List<PartPrimaryAttributes> listField = partPrimaryAttributesService.selectPartPrimaryAttributesByList(map);
		partPrimaryAttributesService.selectPrimaryCount(map);
		List<TreeTableEntity> listTree = buildTree(listField,PartNumber,lang);
		String msg = JSONArray.fromObject(listTree).toString();
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("详细页树形结构", e);
		}
	}
    /**
     * 构建树形结构list
     * @return 返回树形结构List列表
     */
    public List<TreeTableEntity> buildTree(List<PartPrimaryAttributes> resultNodes,String PartNumber,String lang) {
    	List<TreeTableEntity> listTree = new ArrayList<TreeTableEntity>();//树形结构排序之后list内容
    	Map<String,Object> map = new HashMap<String,Object>();
    	Map<String,Object> mapProper = new HashMap<String,Object>();
    	//获取主数据表内容
	  	map.put("PartNumber", PartNumber);
    	List<Map<String, Object>> list = partPrimaryAttributesService.selectPartDateByPartNumber(map);
     	if(null == list || list.size()<=0){
		  	Map<String, Object> mapSql = new HashMap<String,Object>();
		  	List<Map<String, Object>> partDataList = iPartDataService.selectAllData(mapSql);
		  	map.put("PartNumber", partDataList.get(0).get("PartNumber"));
		  	list = partPrimaryAttributesService.selectPartDateByPartNumber(map);
		  	}
     	String[] attrList = new String[GROUP_NAME.length()];
     	if("zh".equals(lang)){
     		attrList = GROUP_NAME.split(",");
     	}else{
     		attrList = English_NAME.split(",");
     	}
        for (int i= 0 ; i<attrList.length;i++) {
        	TreeTableEntity tree = new TreeTableEntity();
		  	String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        	tree.setId(uuid);
        	tree.setParentId("");
        	tree.setAttrName(attrList[i]);
        	tree.setAttrContent("");
        	tree.setType(i+1);
        	listTree.add(tree);
        	for(PartPrimaryAttributes att:resultNodes){
        		if(att.getType() == i+1){
        		  	TreeTableEntity treeDetail = new TreeTableEntity();
        		  	
        		  	treeDetail.setDataType(att.getDataType());
        		  	treeDetail.setFsList(att.getFsList());
        		  	
        		  	String uuids = UUID.randomUUID().toString().replaceAll("-", "");
        		  	treeDetail.setId(uuids);
        		  	treeDetail.setParentId(tree.getId());
		        	Map<String,Object> mapData = partDataService.selectPartDataByAtt(att.getFieldName(), Integer.valueOf(list.get(0).get("ID").toString()));
        		  	mapProper.put("partType", list.get(0).get("part_Type"));
        		  	if(null != list.get(0).get("TempPartMark") && list.get(0).get("TempPartMark").toString().equals("false")){//目录内外
        		  		Map<String,Object> def = partPrimaryAttributesService.selectProperiesByName(mapProper);
            		  	String str=att.getFieldName();
            		  	if(null != def && null != def.get(str) && !"".equals(def.get(str)) ){
                		  	treeDetail.setAttrName(def.get(str).toString());
            		  	}else{
            		  		if("zh".equals(lang)){
                    		  	treeDetail.setAttrName(att.getShowName());
            		  		}else{
                    		  	treeDetail.setAttrName(att.getEnglishName());
            		  		}
            		  	}
        		  	}else{
        		  		if("zh".equals(lang)){
                		  	treeDetail.setAttrName(att.getShowName());
        		  		}else{
                		  	treeDetail.setAttrName(att.getEnglishName());
        		  		}
        		  	}
        		  	treeDetail.setAttrContent(null == mapData.get(att.getFieldName())?"" : mapData.get(att.getFieldName()).toString());
        		  	treeDetail.setFieldName(att.getFieldName());
        		  	treeDetail.setType(i+1);
        		  	listTree.add(treeDetail);
        		}
        	}
        }
        return listTree;
    }
    /**
     * 历史任务Main数据查询
     * @param request
     * @param response
     */
	@RequestMapping(value="/selectPartHistoriesMainList.do")
    public void selectPartHistoriesMainList(HttpServletRequest request,HttpServletResponse response){
		String partNumber = request.getParameter("partNumber");
		Map<String,Object> mapSql= new HashMap<String,Object>();
		Map<String,Object> mapJson= new HashMap<String,Object>();
		mapSql.put("PartNumber", partNumber);
		try {
	   	    List<Map<String, Object>> mapHistories = partPrimaryAttributesService.selectHIstiriesByFieldName(mapSql);
	   	    if(null != mapHistories && mapHistories.size()>0){
		   		mapJson.put("data", mapHistories);
		   		String msg = JSONObject.fromObject(mapJson).toString();
		   		outputJson(response,msg);
	    }else{
	   		mapJson.put("data", null);
	   		String msg = JSONObject.fromObject(mapJson).toString();
	   		outputJson(response,msg);
	    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("元器件修改记录查询", e);
		}
    }
	/**
	 * 对比数据查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectCompartDataByPart.do")
	public void selectCompartDataByPart(HttpServletRequest request,HttpServletResponse response){
		   String compareNums = request.getParameter("compareNums");
		   List<Map<String, Object>> jsonList = new LinkedList<Map<String, Object>>();
		   Map<String, Object> map = new HashMap<String, Object>();
		   try {
		   if(StringUtils.isNotEmpty(compareNums)){
			   List<String> strList = JSON.parseArray(compareNums, String.class);
		        for(String partNumber:strList){
		        	map.put("PartNumber", partNumber);
		        	List<Map<String, Object>> list = partPrimaryAttributesService.selectPartDateByPartNumber(map);
		        	if(null !=list && list.size()>0){
		        		jsonList.add(list.get(0));
		        	}
		        }
		        String msg = JSONArray.fromObject(jsonList).toString();
			     outputJson(response,msg);
		   }
		     String msg = JSONArray.fromObject(null).toString();
		     outputJson(response,msg);
		   } catch (Exception e) {
				e.printStackTrace();
			}
	}
	/**
	 * 通过redis查询元器件主数据
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/selectPartDataByRedis.do")
	public void selectPartDataByRedis(HttpServletRequest request,HttpServletResponse response){
	   String type = request.getParameter("type");
	   String compareNums = request.getParameter("compareNums");
	   try {
		if(StringUtils.isNotEmpty(type)){
			if("compare".equals(type)){
				if(StringUtils.isNotEmpty(compareNums)){
				List<String> strList = JSON.parseArray(compareNums, String.class);
				   List<Map> jsonList = new LinkedList<Map>();
				   if(strList.size()>0){
					   Map<String,Object> map = new HashMap<String,Object>();
					   map.put("key", "partDataAllList");
						List<Map> list = redisService.selectPartDataByRedis(map);
						if(list.size()>0){
							   for(String partNumber:strList){
								    for(int i=0;i<list.size();i++){
								    	if(partNumber.equals(list.get(i).get("PartNumber"))){
								    		jsonList.add(list.get(i));
								    	}
								    }
							   }
							   String msg = JSONArray.fromObject(jsonList).toString();
							   outputJson(response,msg);
						}
			          }
		             }
		            }
	               }
		  String msg = JSONArray.fromObject(null).toString();
		  outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("resource")
	public void deleteRedisByKey(){
	      JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);  
	       Jedis jedis = null;  
	        try {  
	            jedis = jedisPool.getResource();  
	           String pre_str="partDataAllList";   
	            Set<String> set = jedis.keys(pre_str +"*");  
	            Iterator<String> it = set.iterator();  
	             while(it.hasNext()){ 
	                String keyStr = it.next();  
	                System.out.println(keyStr);  
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
	/**
	 * 元器件对比
	 * @author WangSc
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectPartDataToCompare.do")
	public void selectPartDataToCompare(HttpServletRequest request,HttpServletResponse response){
		String partNumber = request.getParameter("partNumber");
		String lang = "zh";
		if(null != request.getSession().getAttribute("lang")){
			 lang = request.getSession().getAttribute("lang").toString();
		}
		List<Map<String, Object>> mapLinked = new LinkedList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapSql = new HashMap<String,Object>();
		Map<String,Object> mapJson = new HashMap<String,Object>();
		map.put("isUsed", true);
		String showName = "id,";
		String queryField = "id,";
		try {
		if(!"null".equals(partNumber) && StringUtils.isNotEmpty(partNumber)){
			List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFieldAndName(map);
			if(list.size()>0){
				for(PartPrimaryAttributes attr:list){
					if("zh".equals(lang)){
						if(StringUtils.isNoneEmpty(attr.getShowName())){
			    			showName+=attr.getShowName()+",";
			    		}
					}else{
						if(StringUtils.isNoneEmpty(attr.getEnglishName())){
			    			showName+=attr.getEnglishName()+",";
			    		}
					}
		            if(StringUtils.isNoneEmpty(attr.getFieldName())){
		            	queryField += attr.getFieldName()+",";
		    		}
			    }
			}
			queryField = queryField.substring(0,queryField.length()-1);
			   List<String> partNumbers = JSON.parseArray(partNumber, String.class);
			   List<List<String>> dataList = new ArrayList<List<String>>();
			for(int i = 0;i<partNumbers.size();i++){
				mapSql.put("PartNumber", partNumbers.get(i));
				mapSql.put("sql", queryField);
				if(null != partPrimaryAttributesService.selectPartsDataByParam(mapSql) && partPrimaryAttributesService.selectPartsDataByParam(mapSql).size()>0){
			   	    Map<String, Object> mapData = partPrimaryAttributesService.selectPartsDataByParam(mapSql).get(0);
			   	    Map<String, Object> mapValue = getMapValue(mapData);
			   	   // String data = mapData.values().toString();
		   	       //String strDataNew = data.substring(1,data.length()-1); 
		   	    	List<String> mapValues = (List<String>) mapValue.get("value");
			   	    mapLinked.add(mapData);
			   	    dataList.add(mapValues);
				}
			}
			String[] showNames = showName.split(",");
			String[] queryFields = queryField.split(",");
			mapJson.put("showName", showNames);
			mapJson.put("queryFields", queryFields);
			mapJson.put("dataList", dataList);
			mapJson.put("mapLinked", mapLinked);
			String msg = JSONObject.fromObject(mapJson).toString();
		    outputJson(response,msg);
		}else{
			mapJson.put("showName", null);
			mapJson.put("dataList", null);
			mapJson.put("mapLinked", null);
			String msg = JSONObject.fromObject(mapJson).toString();
		    outputJson(response,msg);
		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("元器件对比", e);
		}
	}
    /**
     * 元器件修改记录查询
     * @param request
     * @param response
     */
	@RequestMapping(value="/selectPartHistoriesDetailList.do")
    public void selectPartHistoriesDetailList(HttpServletRequest request,HttpServletResponse response){
		String VersionID = request.getParameter("VersionID");
		String PartNumber = request.getParameter("PartNumber");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapSql= new HashMap<String,Object>();
		Map<String,Object> mapJson= new HashMap<String,Object>();
		String lang = "zh";
		if(null != request.getSession().getAttribute("lang")){
			 lang = request.getSession().getAttribute("lang").toString();
		}
		map.put("isUsed", true);
		try {
		List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFieldAndName(map);
		String showName = "";
		String queryField = "";
	    if(list.size()>0){
	    	for(PartPrimaryAttributes attr:list){
	    		if(lang.equals("zh")){
	    			if(StringUtils.isNoneEmpty(attr.getShowName())){
		    			showName+=attr.getShowName()+",";
		    		}
	    		}else{
	    			    showName+=attr.getEnglishName()+",";
	    		}
	            if(StringUtils.isNoneEmpty(attr.getFieldName())){
	            	queryField += attr.getFieldName()+",";
	    		}
		    }
	    	   queryField = queryField.substring(0,queryField.length()-1);
	   	    mapSql.put("sql", queryField);
	   	    mapSql.put("VersionID", VersionID);
	   	    mapSql.put("PartNumber", PartNumber);
	   	    List<Map<String, Object>> mapHistories = partPrimaryAttributesService.selectHIstoriesDataByParam(mapSql);
	   	    List<Map<String, Object>> mapData = partPrimaryAttributesService.selectPartsDataByParam(mapSql);
	   	    if(mapHistories.size()>0 && mapData.size()>0){
					List<List<String>> dataHistory = new ArrayList<List<String>>();
					List<List<String>> dataPart = new ArrayList<List<String>>();
					Map<String, Object> historyValue = getMapValue(mapHistories.get(0));
					Map<String, Object> partValue = getMapValue(mapData.get(0));
					List<String> historyValues = (List<String>) historyValue.get("value");
					List<String> partValues = (List<String>) partValue.get("value");
					dataHistory.add(historyValues);
					dataPart.add(partValues);
					String showNames[] = showName.split(",");
					String queryFields[] = queryField.split(",");
					mapJson.put("showNames", showNames);
					mapJson.put("queryFields", queryFields);
					mapJson.put("dataHistory", dataHistory);
					mapJson.put("dataPart", dataPart);
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
	   	    }else{
	   	 	     mapJson.put("showNames", null);
	   		     mapJson.put("dataHistory", null);
	   		     mapJson.put("dataPart", null);
		   		String msg = JSONObject.fromObject(mapJson).toString();
		   		outputJson(response,msg);
	   	    }
	    }else{
	    	    mapJson.put("showNames", null);
	   		    mapJson.put("dataHistory", null);
	   		    mapJson.put("dataPart", null);
		   		String msg = JSONObject.fromObject(mapJson).toString();
		   		outputJson(response,msg);
	    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("元器件修改记录查询", e);
		}
    }
	/**
	 * 器件分析折线图
	 * @param request
	 * @param response
	 */
	@RequestMapping("/comparePartsByECharts.do")
	public void comparePartsByECharts(HttpServletRequest request,HttpServletResponse response){
	    String partNumber = request.getParameter("PartNumber");
	    String lang="zh";
		if(null != request.getSession().getAttribute("lang")){
			lang = request.getSession().getAttribute("lang").toString();
		}
	    Map<String,Object> mapSql= new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		mapSql.put("PartNumber", partNumber);
		try {
	   	    List<Map<String, Object>> mapHistories = partPrimaryAttributesService.selectHIstiriesByFieldName(mapSql);
	   	   String[] dates = new String[null == mapHistories?0:mapHistories.size()];
	   	   int[] state = new int[null == mapHistories?0:mapHistories.size()];
	   	    if(null != mapHistories){
		   	    for(int i=0;i<mapHistories.size();i++){
		   	    	if(StringUtils.isNotEmpty(mapHistories.get(i).get("ModifyDate").toString()) && null != mapHistories.get(i).get("ModifyDate")){
		   	    		Date date = sdf.parse(mapHistories.get(i).get("ModifyDate").toString());
		   	    		dates[i] = sdf2.format(date);
		   	    	}
		   	    	if(null != mapHistories.get(i).get("Lifecycle_Status") && StringUtils.isNotEmpty(mapHistories.get(i).get("Lifecycle_Status").toString())){
		   	    		if("New(新器件)".equals(mapHistories.get(i).get("Lifecycle_Status").toString())){
		   	    		   state[i] = 0; 
		   	    		}else if("Production(在产)".equals(mapHistories.get(i).get("Lifecycle_Status").toString())){
		   	    			state[i] = 1;
		   	    		}else if("NRND(限用器件)".equals(mapHistories.get(i).get("Lifecycle_Status").toString())){
		   	    			state[i] = 2;
		   	    		}else if("EOL(停产)".equals(mapHistories.get(i).get("Lifecycle_Status").toString())){
		   	    			state[i] = 3;
		   	    		}else if("Obsolete(失效)".equals(mapHistories.get(i).get("Lifecycle_Status").toString())){
		   	    			state[i] = 4;
		   	    		}else{
		   	    			state[i] = 5;
		   	    		}
		   	    	}
		   	    }
		   	}	
	        //自定义横坐标
	        String[] xAxis = dates;
	        String[] legend = new String[1];
	        if("zh".equals(lang)){
	        	legend[0] ="器件分析";
	        }else{
	        	legend[0] ="Device analysis";
	        }
	        int[] datas = state;
//	        String[] datas = {"0","1","2","3","4","5","5","4","3","2","1","0"};
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        resultMap.put("xAxis", xAxis);
	        resultMap.put("legend", legend);
	        resultMap.put("datas", datas);
	        String msg = "";
	        if(xAxis == null ||xAxis.length == 0){
	        	msg = JSON.toJSONString(null);
	        }else{
	   	     msg = JSON.toJSONString(resultMap);
	        }
	    outputJson(response,msg);
		}catch(Exception e){
			e.printStackTrace();
	    }
	}
	/**
	 * 查询未分类字段
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getFiledAndDataType.do")
	public void getFiledAndDataType(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> mapSql= new HashMap<String,Object>();
		Map<String,Object> mapJson= new HashMap<String,Object>();
		String lang="zh";
		if(null != request.getSession().getAttribute("lang")){
			lang = request.getSession().getAttribute("lang").toString();
		}
		mapSql.put("type", 0);
		mapSql.put("isUsed", true);
		mapSql.put("minu", "Datesheet");
		try {
		List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFieldAndName(mapSql);
		mapJson.put("list", list);
		mapJson.put("lang", lang);
		String msg = JSON.toJSONString(mapJson);
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存详情页数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saveMinuData.do")
    @SuppressWarnings("rawtypes")
	public void saveMinuData(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map  = new HashMap<String,Object>();
		Map<String,Object> mapSql  = new HashMap<String,Object>();
		Map<String,Object> mapJson  = new HashMap<String,Object>();
		String jsonArr = request.getParameter("data");
		String partNumber = request.getParameter("partNumber");
		try {
			//修改前，从数据库查出该器件基本信息
			Map<String,Object> updateDataBefore = new HashMap<String,Object>(); 
			if(StringUtils.isNotEmpty(partNumber)){
			mapSql.put("PartNumber", partNumber);
			updateDataBefore = partPrimaryAttributesService.selectPartDateByPartNumber(mapSql).get(0);
			}
			if("2".equals(updateDataBefore.get("state").toString())){
				mapJson.put("result", "fail");
				String msg = JSON.toJSONString(mapJson);
				outputJson(response,msg);
				return;
			}
		List<Map> obj= JSON.parseArray(jsonArr, Map.class);
		if(null != obj && obj.size()>0){
			if(null !=obj.get(obj.size()-1).get("fieldName") && "Datesheet".equals(obj.get(obj.size()-1).get("fieldName").toString())){
				obj.remove(obj.size()-1);
			}
		}
		for(Map attr:obj){
			if(null != attr.get("fieldName")){
				if(StringUtils.isNotEmpty(attr.get("type").toString())){
					map.put("type", attr.get("type"));
					map.put("value", attr.get("value"));
					map.put("fieldName", attr.get("fieldName"));
					map.put("partNumber", partNumber);
					partPrimaryAttributesService.updateMinudata(map);
				}else{
					map.put("type", "");
					map.put("value", attr.get("value"));
					map.put("fieldName", attr.get("fieldName"));
					map.put("partNumber", partNumber);
					partPrimaryAttributesService.updateMinudata(map);
				}
			}
		}
		String msg = JSON.toJSONString("");
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
	/**
	 * 保存详情页数据并将数据存入历史表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saveHistory.do")
    @SuppressWarnings("rawtypes")
	public void saveHistory(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map  = new HashMap<String,Object>();
		Map<String,Object> mapSql  = new HashMap<String,Object>();
		Map<String,Object> mapJson  = new HashMap<String,Object>();
		String jsonArr = request.getParameter("data");
		String partNumber = request.getParameter("partNumber");
		try {
		List<Map> obj= JSON.parseArray(jsonArr, Map.class);
		//修改前，从数据库查出该器件基本信息
		Map<String,Object> updateDataBefore = new HashMap<String,Object>(); 
		if(StringUtils.isNotEmpty(partNumber)){
		mapSql.put("PartNumber", partNumber);
		updateDataBefore = partPrimaryAttributesService.selectPartDateByPartNumber(mapSql).get(0);
		}
		if("2".equals(updateDataBefore.get("state").toString())){
			mapJson.put("result", "fail");
			String msg = JSON.toJSONString(mapJson);
			outputJson(response,msg);
			return;
		}
		//
		if(null != obj && obj.size()>0){
				if(null !=obj.get(obj.size()-1).get("fieldName") && "Datesheet".equals(obj.get(obj.size()-1).get("fieldName").toString())){
					obj.remove(obj.size()-1);
				}
		}
		for(Map attr:obj){
			if(null != attr.get("fieldName")){
				if(StringUtils.isNotEmpty(attr.get("type").toString())){
					map.put("type", attr.get("type"));
					map.put("value", attr.get("value"));
					map.put("fieldName", attr.get("fieldName"));
					map.put("partNumber", partNumber);
					partPrimaryAttributesService.updateMinudata(map);
				}else{
					map.put("type", "");
					map.put("value", attr.get("value"));
					map.put("fieldName", attr.get("fieldName"));
					map.put("partNumber", partNumber);
					partPrimaryAttributesService.updateMinudata(map);
				}
			}
		}
		//修改后，向元器件修改历史记录表添加一条记录
		 if(null != updateDataBefore){
			 insertPartHistories(updateDataBefore);
	     }
		 //更新状态为检入
		    Map<String,Object> mapSqls = new HashMap<String,Object>(); 
		    mapSqls.put("partNumber", partNumber);
		    mapSqls.put("state", "2");
		    partPrimaryAttributesService.updateState(mapSqls);
		String msg = JSON.toJSONString("");
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 元器件修改记录添加
	 * Wangsc
	 * @param map
	 */
	public void insertPartHistories(Map<String,Object> map){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//获得最大版本号
        int versionId = partPrimaryAttributesService.getMaxVersionId(map.get("PartNumber").toString());
        map.put("VersionID", versionId);
         if(null != map.get("id").toString() || !"".equals(map.get("id").toString())){
             map.remove("id");
         }
         map.put("Modifier","admin");
         map.put("ModifyDate", sdf.format(new Date()));
         iPartDataService.insertPartHistories(map);
	}
	/**
	 * 状态查询
	 */
	@RequestMapping(value="/searchState.do")
	public void searchState(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> updateDataBefore = new HashMap<String,Object>(); 
		Map<String,Object> mapSql = new HashMap<String,Object>();
		Map<String,Object> mapJson = new HashMap<String,Object>(); 
		String partNumber = request.getParameter("partNumber");
		try {
		if(StringUtils.isNotEmpty(partNumber)){
		mapSql.put("PartNumber", partNumber);
		updateDataBefore = partPrimaryAttributesService.selectPartDateByPartNumber(mapSql).get(0);
		if("1".equals(updateDataBefore.get("state").toString())){
			mapJson.put("result", "fail");
			String msg = JSON.toJSONString(mapJson);
			outputJson(response,msg);
		}else{
			mapSql.put("state", "1");
			mapSql.put("partNumber", partNumber);
			partPrimaryAttributesService.updateState(mapSql);
			mapJson.put("result", "success");
			String msg = JSON.toJSONString(mapJson);
			outputJson(response,msg);
		}
		}else{
			mapJson.put("result", "null");
			String msg = JSON.toJSONString(mapJson);
			outputJson(response,msg);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新状态
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateState.do")
	public void updateState(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> mapSql = new HashMap<String,Object>(); 
		String partNumber = request.getParameter("partNumber");
		mapSql.put("partNumber", partNumber);
		mapSql.put("state", "1");
		try {
		partPrimaryAttributesService.updateState(mapSql);
		String msg = JSON.toJSONString(null);
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 可替代料显示
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectPartDataToReplace.do")
	public void selectPartDataToReplace(HttpServletRequest request,HttpServletResponse response){
		String partNumber = request.getParameter("partNumber");
		String lang="zh";
		if(null != request.getSession().getAttribute("lang")){
			lang = request.getSession().getAttribute("lang").toString();
		}
		List<Map<String, Object>> mapLinked = new LinkedList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapMain = new HashMap<String,Object>();
		Map<String,Object> mapSql = new HashMap<String,Object>();
		Map<String,Object> mapJson = new HashMap<String,Object>();
		map.put("isUsed", true);
		map.put("fieldShowCol", "show");
		String showName = "id,";
		String queryField = "id,";
		try {
		if(!"null".equals(partNumber) && StringUtils.isNotEmpty(partNumber)){
			mapMain.put("PartNumber", partNumber);
			List<Map<String, Object>> mainPart = partPrimaryAttributesService.selectPartDateByPartNumber(mapMain);
			if(mainPart.size()>0 && null != mainPart.get(0) && null != mainPart.get(0).get("Alternative_Part") && !"".equals(mainPart.get(0).get("Alternative_Part").toString())){
				List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFieldAndName(map);
				if(list.size()>0){
					for(PartPrimaryAttributes attr:list){
						if("zh".equals(lang)){
							if(StringUtils.isNoneEmpty(attr.getShowName())){
				    			showName+=attr.getShowName()+",";
				    		}
						}else{
							if(StringUtils.isNoneEmpty(attr.getEnglishName())){
				    			showName+=attr.getEnglishName()+",";
				    		}
						}
			            if(StringUtils.isNoneEmpty(attr.getFieldName())){
			            	queryField += attr.getFieldName()+",";
			    		}
				    }
				}
				queryField = queryField.substring(0,queryField.length()-1);
				String[] partNumbers = mainPart.get(0).get("Alternative_Part").toString().split(",");
				 //  List<String> partNumbers = JSON.parseArray(partNumber, String.class);
				List<List<String>> dataList = new ArrayList<List<String>>();
				for(int i = 0;i<partNumbers.length;i++){
					mapSql.put("PartCode", partNumbers[i]);
					mapSql.put("sql", queryField);
					List<Map<String, Object>> partList=partPrimaryAttributesService.selectPartsDataByPartCode(mapSql);
					if(null !=partList  && partList.size()>0){
						 Map<String, Object> mapData = partList.get(0);
					   	    Map<String, Object> mapValue = getMapValue(mapData);
				   	    	List<String> mapValues = (List<String>) mapValue.get("value");
					   	    mapLinked.add(mapData);
					   	    dataList.add(mapValues);
					}
				}
				String[] showNames = showName.split(",");
				String[] queryFields = queryField.split(",");
				mapJson.put("showName", showNames);
				mapJson.put("dataList", dataList);
				mapJson.put("mapLinked", mapLinked);
				mapJson.put("queryFields", queryFields);
				String msg = JSONObject.fromObject(mapJson).toString();
			    outputJson(response,msg);
			}else{
				mapJson.put("showName", null);
				mapJson.put("dataList", null);
				mapJson.put("mapLinked", null);
				String msg = JSONObject.fromObject(mapJson).toString();
			    outputJson(response,msg);
			}
		}else{
			mapJson.put("showName", null);
			mapJson.put("dataList", null);
			mapJson.put("mapLinked", null);
			String msg = JSONObject.fromObject(mapJson).toString();
		    outputJson(response,msg);
		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("可替代料:", e);
		}
	}
	/**
	 * 删除可替代料
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteOneReplace.do")
	public void deleteOneReplace(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapJson = new HashMap<String,Object>();
		String partNumberMain = request.getParameter("partNumberMain");
		String partNumberDetail = request.getParameter("partNumberDetail");
		map.put("PartNumber", partNumberMain);
	    try {
		List<Map<String, Object>> mainPart = partPrimaryAttributesService.selectPartDateByPartNumber(map);
		if(mainPart.size()>0 && null != mainPart.get(0) && null != mainPart.get(0).get("Alternative_Part") && !"".equals(mainPart.get(0).get("Alternative_Part").toString())){
			String[] alternativePart = mainPart.get(0).get("Alternative_Part").toString().split(",");
			for(int i=0;i<alternativePart.length;i++){
				if(alternativePart[i].equals(partNumberDetail)){
					alternativePart[i]="";
				}
			}
			String alternativePartNew = "";
			for(int x=0;x<alternativePart.length;x++){
                  if(!"".equals(alternativePart[x])){
                	  if(x == alternativePart.length-1){
                    	  alternativePartNew+=alternativePart[x];
                	  }else{
                		  alternativePartNew+=alternativePart[x]+",";
                	  }
                  }
			}
			map.put("alternativePart", alternativePartNew);
			map.put("PartNumber", partNumberMain);
			partPrimaryAttributesService.updateAlternativePart(map);
			mapJson.put("result", "success");
			String msg = JSONObject.fromObject(mapJson).toString();
		   outputJson(response,msg);
		}else{
			mapJson.put("result", "fail");
			String msg = JSONObject.fromObject(mapJson).toString();
		   outputJson(response,msg);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 数据手册选择框
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectDatasheets.do")
	public void selectDatasheets(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			Map<String, Object> mapSql=new HashMap<String, Object>();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> resultMap=new HashMap<String, Object>();
			map.put("id", "-1");
			map.put("pId", "0");
			map.put("name", "数据手册");
			map.put("path", "");
			list.add(map);
			mapSql.put("FilePath", "cms_server/Datasheet");
			List<FileUploadEntity> hgList=fileUploadService.selectFileUploadList(mapSql);
			if(hgList.size()>0){
				for(int i=0;i<hgList.size();i++){
					Map<String, Object> maps=new HashMap<String, Object>();
					maps.put("id", hgList.get(i).getId());
					maps.put("pId", "-1");
					maps.put("name", hgList.get(i).getName());
					maps.put("path", hgList.get(i).getFilePath());
					list.add(maps);
				}
				resultMap.put("message", "成功！");
				resultMap.put("list", list);
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
			}else{
				resultMap.put("message", "成功！");
				resultMap.put("list", null);
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/displayPDF.do")
    public void displayPDF(HttpServletRequest request,HttpServletResponse response) {
        try {
        	List<Object> list = (List<Object>) request.getSession().getAttribute("pdfName");
        	String num = (String) request.getSession().getAttribute("pdfNum");
        	if("".equals(num) || null == num){
        		num="0";
        		request.getSession().setAttribute("pdfNum", num);
        	}else{
        		num=String.valueOf(Integer.valueOf(num)+1);
        		request.getSession().setAttribute("pdfNum", num);
        	}
        	String path = getAbsoluteBasePath(request)+"WEB-INF/cms_server/Datasheet/"+list.get(Integer.valueOf(num));
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment;fileName=test.pdf");
            response.setContentType("multipart/form-data");
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
            if(Integer.valueOf(num) == list.size()-1){
        		request.getSession().setAttribute("pdfNum", null);
            }
//            request.getSession().setAttribute("pdfName", null);//销毁session
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
	@RequestMapping("/setPdfnameSession.do")
	public void setPdfnameSession(HttpServletRequest request,HttpServletResponse response) {
		try {
			String[] name = request.getParameter("files").split(",");
			String msg = "";
		    List<Object> list = new ArrayList<Object>();
		    for(int i=0;i<name.length;i++){
		    	list.add(name[i]);
		    }
		    String path = getAbsoluteBasePath(request)+"WEB-INF/cms_server/Datasheet/"+list.get(Integer.valueOf(0));
		    File file = new File(path);
		    if(!file.exists()){
				 msg = JSON.toJSONString("notFound");
		    }else{
				 msg = JSONArray.fromObject(list).toString();
				 request.getSession().setAttribute("pdfName",list);
		    }
		   outputJson(response,msg);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	   /**
		 *  获得项目的根路径
		 * @param request
		 * @return
		 */
		public String getAbsoluteBasePath(HttpServletRequest request) {
			return request.getSession().getServletContext().getRealPath("/");
		}
		/**
		 * 查询器件信息
		 * @param request
		 * @param response
		 */
		@RequestMapping(value="/selectPartData.do")
		public void selectPartData(HttpServletRequest request,HttpServletResponse response){
			Map<String, Object> map=new HashMap<String, Object>();
			Map<String, Object> mapJson=new HashMap<String, Object>();
			String PartNumber = request.getParameter("PartNumber");
			String option = request.getParameter("option");
			map.put("PartNumber", PartNumber);
			try {
			List<Map<String, Object>> list = partPrimaryAttributesService.selectPartDateByPartNumber(map);
        	if(null != list && list.size()>0){
        		if(StringUtils.isNotEmpty(option)){//详情页下拉框动态值显示
        			if(null == list.get(0).get(option)){
        				String msg = JSON.toJSONString(null);
        				outputJson(response,msg);
        			}else{
        				String value = list.get(0).get(option).toString();
            			mapJson.put("value", value);
            			String msg = JSON.toJSONString(mapJson);
        				outputJson(response,msg);
        			}
        		}else{
        			String msg = JSON.toJSONString(list);
    				outputJson(response,msg);
        		}
        	}else{
        		String msg = JSON.toJSONString(null);
				outputJson(response,msg);
        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * Map对象遍历
		 * @param map
		 */
		public Map<String, Object>  getMapValue(Map<String, Object> map){
			Map<String, Object> mapReturn = new HashMap<String,Object>();
			List<String> key = new ArrayList<String>();
			List<String> value = new ArrayList<String>();
			Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
			while (entries.hasNext()) {  
			    Map.Entry<String, Object> entry = entries.next();  
			    key.add(entry.getKey());
			    value.add(null ==entry.getValue()?"":entry.getValue().toString());
			}
			mapReturn.put("key", key);
			mapReturn.put("value", value);
			return mapReturn;
		}
}
