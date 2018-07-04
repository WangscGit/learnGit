package com.cms_cloudy.websocket.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.cms_cloudy.component.pojo.PartEvaluationEntity;
import com.cms_cloudy.component.service.IPartDataService;
import com.cms_cloudy.component.service.IPartEvaluationService;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.websocket.pojo.PushPartData;
import com.cms_cloudy.websocket.service.IPushPartDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *热门数据统计及推送 
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/pushPartDataController")
public class PushPartDataController extends BaseController{

	private static final Logger logger = Logger.getLogger(PushPartDataController.class);

	@Autowired
	private IPushPartDataService pushPartDataService;
	@Autowired
	private IPartDataService iPartDataService;
	@Autowired
	private IPartEvaluationService partEvaluationService;
	/**
	 * 热门数据初始化
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectHotSearchData.do")
	public void selectHotSearchData(HttpServletRequest request, HttpServletResponse response){
		try {
		Map<String,Object> map = new HashMap<String,Object>();
		List<PushPartData> list = pushPartDataService.selectDistinctPushPartToshow(map);
		List<PushPartData> distinctList = pushPartDataService.selectDistinctPushPart(new HashMap<String,Object>());
		if(distinctList.size()>0){
				for(int sx=0;sx<distinctList.size();sx++){
					Map<String,Object> mapNum = new HashMap<String,Object>();
					mapNum.put("inputContent", distinctList.get(sx).getInputContent());
//					mapNum.put("type", 2);
					int sum = pushPartDataService.selectDistinctSum(mapNum);
					List<PushPartData> lists = pushPartDataService.selectPushPartData(mapNum);
					PushPartData data = lists.get(0);
					data.setTimes(sum);
					list.add(data);
				}
		}
		if(list.size()>0){
	    	Collections.<PushPartData> sort(list, new BeanComparator("times"));// 进行排序
	    	if(list.size()>6){
	    		while(true){
	    			list.remove(0);
	    			if(list.size() == 6){
	    				break;
	    			}
	    		}
	    	}
			String msg = JSONArray.fromObject(list).toString();
			outputJson(response,msg);
		}else{
			String msg = JSONArray.fromObject(null).toString();
			outputJson(response,msg);
		}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("热门数据初始化失败！");
		}
	}
	/**
	 * 单个用户/全部用户浏览数据初始化
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectHotSearchFromSelf.do")
	public void selectHotSearchFromSelf(HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String,Object> pushMap = new HashMap<String,Object>();
			List<PushPartData> pushList = new ArrayList<PushPartData>();
			List<Map<String,Object>> pushPart = new ArrayList<Map<String,Object>>();
			HrUser user = getUserInfo(request);
			List<PushPartData> distinctList = pushPartDataService.selectDistinctPushPart(new HashMap<String,Object>());
			if(null == user){
				List<PushPartData> list = pushPartDataService.selectDistinctPushPartToshow(pushMap);
				pushList.addAll(list);
				if(distinctList.size()>0){
					for(int sx=0;sx<distinctList.size();sx++){
						Map<String,Object> mapNum = new HashMap<String,Object>();
						mapNum.put("inputContent", distinctList.get(sx).getInputContent());
//						mapNum.put("type", 2);
						int sum = pushPartDataService.selectDistinctSum(mapNum);
						List<PushPartData> lists = pushPartDataService.selectPushPartData(mapNum);
						PushPartData data = lists.get(0);
						data.setTimes(sum);
						pushList.add(data);
					}
				}
		    	Collections.<PushPartData> sort(pushList, new BeanComparator("times"));// 进行排序
		    	if(pushList.size()>6){
			    		while(true){
			    			pushList.remove(0);
			    			if(pushList.size() == 6){
			    				break;
			    			}
			    		}
		    	}
				if(pushList.size()>0){
					for(PushPartData data:pushList){
							String content =data.getInputContent();
							//模糊查找评论最好的器件
							List<Map<String,Object>> partList = iPartDataService.selectHotSearchFromSelf(content);
							if(null != partList && partList.size() > 0){
								Map<String,Object> evaMap = new HashMap<String,Object>();
								int evaluation = 0;
								evaMap.put("PartNumber", partList.get(0).get("PartNumber"));
								List<PartEvaluationEntity> partEva = partEvaluationService.selectPartEvaluationList(evaMap);
								if(null != partEva && partEva.size()>0){
									for(PartEvaluationEntity evaLevel:partEva){
										evaluation += evaLevel.getVotes();
									}
									evaluation = (int) Math.ceil(evaluation/partEva.size());//评价平均值
								}else{
									evaluation = 0;
								}
								partList.get(0).put("Votes", evaluation);
								pushPart.addAll(partList);
							}else{
								List<Map<String,Object>> noEVAList = iPartDataService.selectHotSearchFromPHTB(content);
								if(null != noEVAList && noEVAList.size()>0){
									noEVAList.get(0).put("Votes", 0);
									pushPart.addAll(noEVAList);
								}
							}
					}
					String msg = JSONArray.fromObject(pushPart).toString();
					outputJson(response,msg);
				}else{
					String msg = JSONArray.fromObject(null).toString();
					outputJson(response,msg);
				}
			}else{
				pushMap.put("userId", user.getUserId());
				pushMap.put("type", "2");
				List<PushPartData> list2 = pushPartDataService.selectDistinctPushPartToHot(pushMap);
				pushList.addAll(list2);
				if(distinctList.size()>0){
					for(int sx=0;sx<distinctList.size();sx++){
						Map<String,Object> mapNum = new HashMap<String,Object>();
						mapNum.put("inputContent", distinctList.get(sx).getInputContent());
						mapNum.put("type", 2);
						int sum = pushPartDataService.selectDistinctSum(mapNum);
						System.out.println(distinctList.get(sx).getInputContent());
						List<PushPartData> lists = pushPartDataService.selectPushPartData(mapNum);
						if(null != lists && lists.size()>0){
							PushPartData data = lists.get(0);
							data.setTimes(sum);
							pushList.add(data);
						}
					}
				}
				if(pushList.size()>0){
			    	Collections.<PushPartData> sort(pushList, new BeanComparator("times"));// 进行排序
			    	if(pushList.size()>6){
			    		while(true){
			    			pushList.remove(0);
			    			if(pushList.size() == 6){
			    				break;
			    			}
			    		}
			    	}
					for(PushPartData data:pushList){
							String content =data.getInputContent();
							//模糊查找评论最好的器件
							List<Map<String,Object>> partList = iPartDataService.selectHotSearchFromSelf(content);
							if(null != partList && partList.size() > 0){
								Map<String,Object> evaMap = new HashMap<String,Object>();
								int evaluation = 0;
								evaMap.put("PartNumber", partList.get(0).get("PartNumber"));
								List<PartEvaluationEntity> partEva = partEvaluationService.selectPartEvaluationList(evaMap);
								if(null != partEva && partEva.size()>0){
									for(PartEvaluationEntity evaLevel:partEva){
										evaluation += evaLevel.getVotes();
									}
									evaluation = (int) Math.ceil(evaluation/partEva.size());//评价平均值
								}else{
									evaluation = 0;
								}
								partList.get(0).put("Votes", evaluation);
								pushPart.addAll(partList);
							}
					}
					String msg = JSONArray.fromObject(pushPart).toString();
					outputJson(response,msg);
				}else{
					if(null != selectAllData()){
						pushPart.addAll(selectAllData());
					}
					if(null != pushPart && pushPart.size()>0){
						String msg = JSONArray.fromObject(pushPart).toString();
						outputJson(response,msg);
					}else{
						String msg = JSONArray.fromObject(null).toString();
						outputJson(response,msg);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("单个用户/全部用户浏览数据初始化！");
		}
	}
	/**
	 * 最新推荐推送---
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectPushNewPart.do")
	public void  selectPushNewPart(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> pushMap = new HashMap<String,Object>();
		Map<String,Object> partMap = new HashMap<String,Object>();
		List<Map<String,Object>> hotList = new ArrayList<Map<String,Object>>();
		try {
	    pushMap.put("type", "1");
		List<PushPartData> list = pushPartDataService.selectPushNewPart(pushMap);
        if(null != list && list.size()>0){
            for(PushPartData pushData:list){
                partMap.put("id", pushData.getPartId());
        		List<Map<String,Object>> partList = pushPartDataService.selectFirstPartData(partMap);
        		if(null != partList && partList.size()>0){
        			Map<String,Object> evaMap = new HashMap<String,Object>();
    				int evaluation = 0;
    				evaMap.put("PartNumber", partList.get(0).get("PartNumber"));
    				List<PartEvaluationEntity> partEva = partEvaluationService.selectPartEvaluationList(evaMap);
    				if(null != partEva && partEva.size()>0){
    					for(PartEvaluationEntity evaLevel:partEva){
    						evaluation += evaLevel.getVotes();
    					}
    					evaluation = (int) Math.ceil(evaluation/partEva.size());//评价平均值
    				}else{
    					evaluation = 0;
    				}
    				partList.get(0).put("Votes", evaluation);
    				hotList.addAll(partList);
        		}
            }
            String msg = JSONArray.fromObject(hotList).toString();
			outputJson(response,msg);
        }else{
        	String msg = JSONObject.fromObject(null).toString();
			outputJson(response,msg);
        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("最新推荐推送---！");
		}
	}
	/**
	 * 推送数据保存
	 */
	@RequestMapping(value="/insertPushDatas.do")
	public void insertPushDatas(HttpServletRequest request,HttpServletResponse response){
		try {
		Map<String,Object> partMap = new HashMap<String,Object>();
		List<Map<String,Object>> partList = pushPartDataService.selectFirstPartData(partMap);
		if(null != partList &&  partList.size()>0){
			partMap.put("type", "1");
			List<PushPartData> pushPartList = pushPartDataService.selectPushNewPart(partMap);
			if(null != pushPartList && pushPartList.size()==3){
				pushPartDataService.deletePushPartData(Integer.valueOf(String.valueOf(pushPartList.get(2).getId())));
			}
			PushPartData pushData = new PushPartData();
			HrUser user = getUserInfo(request);
			pushData.setInputContent(partList.get(0).get("item").toString());
			pushData.setPartId(Long.valueOf(partList.get(0).get("id").toString()));
			pushData.setType('1');
			if(null != user){
				pushData.setUserId(user.getUserId());
			}
			pushPartDataService.insertPushPartData(pushData);
		}
		String msg = JSONObject.fromObject(null).toString();
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("推送数据保存---！");
		}
	}
	/**
	 * 跳转至详情页
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/goMinuByPush.do")
	public void goMinuByPush(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> partMap = new HashMap<String,Object>();
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		String item = request.getParameter("item");
		try {
		partMap.put("item", item);
		List<Map<String,Object>> partList = pushPartDataService.selectFirstPartData(partMap);
	    if(null != partList && partList.size()>0){
	    	jsonMap = partList.get(0);
	    	String msg = JSONObject.fromObject(jsonMap).toString();
			outputJson(response,msg);
	    }else{
	    	String msg = JSONObject.fromObject(null).toString();
			outputJson(response,msg);
	    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("跳转至详情页！");
		}
	}
	/**
	 * 足迹数据处理
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/setDataAcquisition.do")
	public void setDataAcquisition(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> partMap = new HashMap<String,Object>();
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		String partNumber = request.getParameter("data");
		String type = request.getParameter("type");
		try {
		if(type.equals("minu") || type.equals("follow")){
			this.saveDataAcquisition(request, partNumber, partMap, map);
		}else if("select".equals(type)){
			List<Object> partNumberList=JSON.parseArray(partNumber);
			if(null != partNumberList && partNumberList.size()>0){
				for(int i=0;i<partNumberList.size();i++){
					this.saveDataAcquisition(request, partNumberList.get(i).toString().split(",")[0], partMap, map);
				}
			}
		}
		String msg = JSON.toJSONString(jsonMap);
		outputJson(response,msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveDataAcquisition(HttpServletRequest request,String partNumber,Map<String,Object> partMap,Map<String,Object> map){
		partMap.put("PartNumber", partNumber);
		List<Map<String,Object>> partList = pushPartDataService.selectFirstPartData(partMap);
		if(null != partList && partList.size()>0){
			HrUser user = getUserInfo(request);
			map.put("inputContent", partList.get(0).get("item"));
			if(null != user){
				map.put("userId", user.getUserId());
			}else{
				map.put("noUser", 0);
			}
			map.put("type", "2");
	    	List<PushPartData> pustList = pushPartDataService.selectPushPartData(map);
	    	if(null == pustList || pustList.size() == 0){
	    		PushPartData data = new PushPartData();
	        	data.setInputContent(partList.get(0).get("item").toString());
	        	data.setPartId(Long.valueOf(partList.get(0).get("id").toString()));
	        	data.setType('2');
	        	data.setTimes(1);
	        	if(null != user){
	        		data.setUserId(user.getUserId());
	        	}
	        	pushPartDataService.insertPushPartData(data);
	    	}else{
	    		PushPartData data = pustList.get(0);
	    		data.setTimes(data.getTimes()+1);
	    		if(null != user){
	        		data.setUserId(user.getUserId());
	        	}
	    		pushPartDataService.updatePushPartDatar(data);
	    	}
	}
  }

	/** 查询全部集合数据 **/
public List<Map<String,Object>> selectAllData() {
	Map<String,Object> pushMap = new HashMap<String,Object>();
	List<PushPartData> pushList = new ArrayList<PushPartData>();
	List<Map<String,Object>> pushPart = new ArrayList<Map<String,Object>>();
	List<PushPartData> distinctList = pushPartDataService.selectDistinctPushPart(new HashMap<String,Object>());
		List<PushPartData> list = pushPartDataService.selectDistinctPushPartToshow(pushMap);
		pushList.addAll(list);
		if(distinctList.size()>0){
			for(int sx=0;sx<distinctList.size();sx++){
				Map<String,Object> mapNum = new HashMap<String,Object>();
				mapNum.put("inputContent", distinctList.get(sx).getInputContent());
//				mapNum.put("type", 2);
				int sum = pushPartDataService.selectDistinctSum(mapNum);
				List<PushPartData> lists = pushPartDataService.selectPushPartData(mapNum);
				PushPartData data = lists.get(0);
				data.setTimes(sum);
				pushList.add(data);
			}
		}
    	Collections.<PushPartData> sort(pushList, new BeanComparator("times"));// 进行排序
    	if(pushList.size()>6){
	    		while(true){
	    			pushList.remove(0);
	    			if(pushList.size() == 6){
	    				break;
	    			}
	    		}
    	}
		if(pushList.size()>0){
			for(PushPartData data:pushList){
					String content =data.getInputContent();
					//模糊查找评论最好的器件
					List<Map<String,Object>> partList = iPartDataService.selectHotSearchFromSelf(content);
					if(null != partList && partList.size() > 0){
						Map<String,Object> evaMap = new HashMap<String,Object>();
						int evaluation = 0;
						evaMap.put("PartNumber", partList.get(0).get("PartNumber"));
						List<PartEvaluationEntity> partEva = partEvaluationService.selectPartEvaluationList(evaMap);
						if(null != partEva && partEva.size()>0){
							for(PartEvaluationEntity evaLevel:partEva){
								evaluation += evaLevel.getVotes();
							}
							evaluation = (int) Math.ceil(evaluation/partEva.size());//评价平均值
						}else{
							evaluation = 0;
						}
						partList.get(0).put("Votes", evaluation);
						pushPart.addAll(partList);
					}
			}
		}else{
			pushPart = null;
		}
		return pushPart;
	}
}
