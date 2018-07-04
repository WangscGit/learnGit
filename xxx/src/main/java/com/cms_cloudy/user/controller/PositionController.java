package com.cms_cloudy.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.user.pojo.HrPosition;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.IPositionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

/**
 * 职位管理controller
 * @author WangSc
 *
 */
@Controller
@RequestMapping(value="/positionController")
public class PositionController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(PositionController.class);
	@Autowired
	private IPositionService positonService;
	
	/**
	 * 职位信息查询
	 */
	@RequestMapping(value="/selectPositionList.do")
	public void selectPositionList(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
	    Map<String,Object> mapJson = new HashMap<String,Object>();
	    List<HrPosition> list = new ArrayList<HrPosition>();
	    PageInfo<HrPosition> page = null;
		String pageNo = request.getParameter("pageNo");
		// 分页初始化
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = "1";
		}
		String pageSize = "5";
		try {
		PageHelper.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
		list = positonService.selectPositionList(map);
		 if(list.size()>0){
				page = new PageInfo<HrPosition>(list);
				mapJson.put("list", page.getList());
				mapJson.put("count", page.getTotal());
				mapJson.put("pageNo", pageNo);
				mapJson.put("pageSize", pageSize);
		    	String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
		 }else{
			 mapJson.put("list", null);
		     String msg = JSONObject.fromObject(mapJson).toString();
			 outputJson(response, msg);
		 }
		}catch(Exception e){
			e.printStackTrace();
			logger.error("职位信息查询:"+e);
		}
	}
	/**
	 * 职位信息添加
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertPosition.do")
	public void insertPosition(HttpServletRequest request,HttpServletResponse response){
	try {
		Map<String,Object> mapJson = new HashMap<String,Object>();
		HrUser userInfo =  getUserInfo(request);
		String positionName = request.getParameter("positionName");
		String positionRemark = request.getParameter("positionRemark");
		positionName = java.net.URLDecoder.decode(positionName, "UTF-8");
		positionRemark = java.net.URLDecoder.decode(positionRemark, "UTF-8");
		String loginName = "";
        if(null == userInfo){
        	loginName = "admin";
        }else{
        	loginName = userInfo.getLoginName();
        }
        HrPosition position = new HrPosition();
        position.setPositionName(positionName);
        position.setPositionRemark(positionRemark);
        position.setCreateuser(loginName);
        position.setCreatetime(new Date());
        positonService.insertPosition(position);
        mapJson.put("result", "add");
        String msg = JSONObject.fromObject(mapJson).toString();
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("职位信息添加:"+e);
		}
	}
	@RequestMapping(value="/updatePosition.do")
	public void updatePosition(HttpServletRequest request,HttpServletResponse response){
		try {
			String lang = "zh";
			if (null != request.getSession().getAttribute("lang")) {
				lang = request.getSession().getAttribute("lang").toString();
			}
			Map<String,Object> mapJson = new HashMap<String,Object>();
			Map<String,Object> map = new HashMap<String,Object>();
			HrUser userInfo =  getUserInfo(request);
			String positionName = request.getParameter("positionName");
			String positionRemark = request.getParameter("positionRemark");
			positionName = java.net.URLDecoder.decode(positionName, "UTF-8");
			positionRemark = java.net.URLDecoder.decode(positionRemark, "UTF-8");
			String id = request.getParameter("id");
			List<Object> ids = JSON.parseArray(id);
			String loginName = "";
	        if(null == userInfo){
	        	loginName = "admin";
	        }else{
	        	loginName = userInfo.getLoginName();
	        }
	        map.put("id", ids.get(0));
	        map.put("checkName", positionName);
	        List<HrPosition> positionList = positonService.checkPositionName(map);
	        if(null != positionList && positionList.size()>0){
	        	mapJson.put("result", "zh".equals(lang)?"职位名称不能重复！":"The name of the position cannot be repeated.");
		        String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response,msg);
				return;
	        }
	        HrPosition position = positonService.selectPositionList(map).get(0);
	        position.setPositionName(positionName);
	        position.setPositionRemark(positionRemark);
	        position.setModifyuser(loginName);
	        position.setModifytime(new Date());
	        positonService.updatePosition(position);
	        mapJson.put("result", "update");
	        String msg = JSONObject.fromObject(mapJson).toString();
			outputJson(response,msg);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("职位信息更新:"+e);
			}
	}
	/**
	 * 职位信息删除
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deletePosition.do")
	public void deletePosition(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		 try {
		List<Object> ids = JSON.parseArray(id);
		  for(Object bomId:ids){
			  positonService.deletePosition(Integer.valueOf(bomId.toString()));
		  }
	      String msg = JSONObject.fromObject(null).toString();
		  outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("职位信息删除:"+e);
		}
	}
	/**
	 * 职位信息修改前
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateBefore.do")
	public void updateBefore(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String id = request.getParameter("id");
		List<Object> ids = JSON.parseArray(id);
		try {
			map.put("id", ids.get(0));
			List<HrPosition> hrPosition = positonService.selectPositionList(map);
			if(hrPosition.size()>0){
				map.put("list",hrPosition);
				String msg = JSONObject.fromObject(map).toString();
				outputJson(response,msg);
			}else{
				map.put("list", null);
				String msg = JSONObject.fromObject(map).toString();
				outputJson(response,msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
