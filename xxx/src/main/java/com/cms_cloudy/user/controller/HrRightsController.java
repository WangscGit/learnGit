package com.cms_cloudy.user.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.user.pojo.HrRights;
import com.cms_cloudy.user.pojo.HrRightsGroup;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.pojo.ParameterConfig;
import com.cms_cloudy.user.pojo.RightsTree;
import com.cms_cloudy.user.service.IHrRightsService;

@Controller
@RequestMapping(value="/HrRightsController")
public class HrRightsController extends BaseController{
	@Autowired
	private IHrRightsService iHrRightsService;
	
	//添加权限
	@RequestMapping(value="/insertHrRights.do")
	public void insertHrRights(HttpServletRequest request, HttpServletResponse response){
		try {
			String lang="zh";
			if(null != request.getSession().getAttribute("lang")){
				lang = request.getSession().getAttribute("lang").toString();
			}
			String jsonData=request.getParameter("jsonData");
			Map<String,Object> resultMap=new HashMap<String, Object>();
			if(StringUtils.isEmpty(jsonData)){
				resultMap.put("message", lang.equals("zh")?"数据为空！":"Data is empty");
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			HrRights hr=JSON.parseObject(jsonData, HrRights.class);
			int coun=iHrRightsService.insertHrRights(hr);
			if(coun==0){
				resultMap.put("message", lang.equals("zh")?"数据已存在！":"The data has already existed!");
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			resultMap.put("message", lang.equals("zh")?"添加成功！":"Add success!");
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//权限树
	@RequestMapping(value="/selectHrRightsList.do")
	public void selectHrRightsList(HttpServletRequest request, HttpServletResponse response){
		try {
			List<RightsTree> list = new ArrayList<RightsTree>();
			list = iHrRightsService.selectHrRightsList(new HashMap<String,Object>());
			String jsonString =JSON.toJSONString(list);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//删除权限节点  
	@RequestMapping(value="/deleteHrRights.do")
	public void deleteHrRights(HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			String id =request.getParameter("id");
			if(StringUtils.isEmpty(id)){
				resultMap.put("message", "请选择一条数据！");
				String jsonString =JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			Map<String, Object> map= new HashMap<String,Object>();
			map.put("id", Integer.valueOf(id));
			int coun=iHrRightsService.deleteHrRights(map);
			if(coun>0){
				resultMap.put("message", "包含子节点，请先删除子节点！");
				String jsonString =JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			String jsonString =JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//修改权限节点
	@RequestMapping(value="/updateHrRights.do")
	public void updateHrRights(HttpServletRequest request, HttpServletResponse response){
		try {
			HrUser user = getUserInfo(request);
			Map<String,Object> resultMap=new HashMap<String, Object>();
			String jsonData =request.getParameter("jsonData");
			if(StringUtils.isEmpty(jsonData)){
				resultMap.put("message", "请选择一条数据！");
				String jsonString =JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			HrRights hrr=JSON.parseObject(jsonData,HrRights.class);
			hrr.setModifyDate(new Date());
			hrr.setModifyUser(user.getLoginName());
			iHrRightsService.updateHrRights(hrr);
			resultMap.put("message", "修改成功");
			String jsonString =JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//添加组权限关系 , 同时删除去掉的权限关系
	@RequestMapping(value="/insertHrRightsGroup.do")
	public void insertHrRightsGroup(HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			String removeRights=request.getParameter("removeRights");
			String addRights=request.getParameter("addRights");
			String groupId=request.getParameter("groupId");
			if(StringUtils.isEmpty(groupId)||groupId.equals("0")){
				resultMap.put("message", "请选择一条数据！");
				String jsonString =JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			Integer gid=Integer.parseInt(groupId);
			//删除权限组关系
			if(StringUtils.isNotEmpty(removeRights)){
				List<Object> removeList=JSON.parseArray(removeRights);
				for(int i=0;i<removeList.size();i++){
					HrRightsGroup hrRightsGroup=new HrRightsGroup();
					hrRightsGroup.setRightsId(Integer.parseInt(removeList.get(i).toString()));
					hrRightsGroup.setGroupId(gid);
					iHrRightsService.deleteHrRightsGroup(hrRightsGroup);
				}
			}
			//添加权限组关系
			if(StringUtils.isNotEmpty(addRights)){
				List<Object> addList=JSON.parseArray(addRights);
				for(int i=0;i<addList.size();i++){
					HrRightsGroup hrRightsGroup=new HrRightsGroup();
					hrRightsGroup.setRightsId(Integer.parseInt(addList.get(i).toString()));
					hrRightsGroup.setGroupId(gid);
					iHrRightsService.insertHrRightsGroup(hrRightsGroup);
				}
			}
			resultMap.put("message", "成功！");
			String jsonString =JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取所有权限，组包含的权限在树中默认选中
	@RequestMapping(value="/selectHrRightsByGroupId.do")
	public void selectHrRightsByGroupId(HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			String groupId=request.getParameter("groupId");
			Map<String,Object> paramMap=new HashMap<String,Object>();
			if(StringUtils.isEmpty(groupId)){
				groupId="0";
			}
			paramMap.put("groupId", groupId);
			List<RightsTree> rightsList=iHrRightsService.selectHrRightsByGroupId(paramMap);
			resultMap.put("rightsList", rightsList);
			String jsonString =JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取session中的菜单权限信息
	@RequestMapping(value="/getMenuRightsFromSession.do")
	public void getMenuRightsFromSession(HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			List<HrRights> menuRightList=(List<HrRights>) request.getSession().getAttribute("menuRightList");
			resultMap.put("menuRightList", menuRightList);
			String jsonString =JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//获取session中的数据权限信息
	@RequestMapping(value="/getDataRightsFromSession.do")
	public void getDataRightsFromSession(HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			List<HrRights> dataRightList=(List<HrRights>) request.getSession().getAttribute("dataRightList");
			resultMap.put("dataRightList",dataRightList);
			String jsonString =JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存系统参数
	 * 
	 */
	@RequestMapping(value = "/saveParam.do")
	public void saveParam(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			String paramConfig = request.getParameter("paramConfig");
			ParameterConfig pc=new ParameterConfig();
			pc.setParameterName("sjgj");
			iHrRightsService.deleteParamConfig(pc);
			pc.setParameterValue(paramConfig);
			iHrRightsService.insertParamConfig(pc);
			String jsonString =JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询系统参数
	 * 
	 */
	@RequestMapping(value = "/selectParam.do")
	public void selectParam(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("parameterName", "sjgj");
			List<ParameterConfig> list=iHrRightsService.selectParamConfig(paramMap);
			Map<String,Object> resultMap=new HashMap<String, Object>();
			resultMap.put("parameter", list);
			String jsonString =JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
