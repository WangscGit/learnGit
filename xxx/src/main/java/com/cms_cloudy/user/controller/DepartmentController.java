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
import com.cms_cloudy.user.pojo.HrDepartment;
import com.cms_cloudy.user.pojo.HrPosition;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.IDepartmentService;
import com.cms_cloudy.user.service.IPositionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

/**
 * 部门管理controller
 * @author WangSc
 *
 */
@Controller
@RequestMapping(value="/departmentController")
public class DepartmentController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(DepartmentController.class);
	@Autowired
   private IDepartmentService departmentService; 
	@Autowired
	private IPositionService positonService;
	/**
	 * 部门信息查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectDepartmentList.do")
	public void selectDepartmentList(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
	    Map<String,Object> mapJson = new HashMap<String,Object>();
	    List<HrDepartment> list = new ArrayList<HrDepartment>();
	    PageInfo<HrDepartment> page = null;
		String pageNo = request.getParameter("pageNo");
		// 分页初始化
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = "1";
		}
		String pageSize = "5";
		try {
		PageHelper.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
		list = departmentService.selectDepartmentList(map);
		 if(list.size()>0){
				page = new PageInfo<HrDepartment>(list);
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
			logger.error("部门信息查询:"+e);
		}
	}
	/**
	 * 部门信息添加
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertDepartment.do")
	public void insertDepartment(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> mapJson = new HashMap<String,Object>();
			HrUser userInfo =  getUserInfo(request);
			String departmentName = request.getParameter("departmentName");
			String departmentNo = request.getParameter("departmentNo");
			String departmentMaster = request.getParameter("departmentMaster");
			String departmentDescript = request.getParameter("departmentDescript");
			 departmentName = java.net.URLDecoder.decode(departmentName, "UTF-8");
			 departmentNo = java.net.URLDecoder.decode(departmentNo, "UTF-8");
			 departmentMaster = java.net.URLDecoder.decode(departmentMaster, "UTF-8");
			 departmentDescript = java.net.URLDecoder.decode(departmentDescript, "UTF-8");
			String loginName = "";
	        if(null == userInfo){
	        	loginName = "admin";
	        }else{
	        	loginName = userInfo.getLoginName();
	        }
	        HrDepartment department = new HrDepartment();
	        department.setDepartmentName(departmentName);
	        department.setDepartmentNo(departmentNo);
	        department.setDepartmentMaster(departmentMaster);
	        department.setDepartmentDescript(departmentDescript);
	        department.setCreatetime(new Date());
	        department.setCreateuser(loginName);
	        departmentService.insertDepartment(department);
	        mapJson.put("result", "add");
	        String msg = JSONObject.fromObject(mapJson).toString();
			outputJson(response,msg);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("部门信息添加:"+e);
			}
	}
	/**
	 * 部门信息更新
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateDepartment.do")
	public void updateDepartment(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> mapJson = new HashMap<String,Object>();
			Map<String,Object> map = new HashMap<String,Object>();
			HrUser userInfo =  getUserInfo(request);
			String lang = "zh";
			if (null != request.getSession().getAttribute("lang")) {
				lang = request.getSession().getAttribute("lang").toString();
			}
			String departmentName = request.getParameter("departmentName");
			String departmentNo = request.getParameter("departmentNo");
			String departmentMaster = request.getParameter("departmentMaster");
			String departmentDescript = request.getParameter("departmentDescript");
			departmentName = java.net.URLDecoder.decode(departmentName, "UTF-8");
			departmentNo = java.net.URLDecoder.decode(departmentNo, "UTF-8");
			departmentMaster = java.net.URLDecoder.decode(departmentMaster, "UTF-8");
			departmentDescript = java.net.URLDecoder.decode(departmentDescript, "UTF-8");
			String id = request.getParameter("id");
			List<Object> ids = JSON.parseArray(id);
			String loginName = "";
	        if(null == userInfo){
	        	loginName = "admin";
	        }else{
	        	loginName = userInfo.getLoginName();
	        }
	        map.put("id", ids.get(0));
	        map.put("checkName", departmentName);
	        List<HrDepartment> departmentList =departmentService.checkDepartmentName(map);
	        if(null != departmentList && departmentList.size()>0){
	        	mapJson.put("result", "zh".equals(lang)?"部门名称不能重复！":"The name of the department cannot be repeated.");
		        String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response,msg);
				return;
	        }
	        HrDepartment department =departmentService.selectDepartmentList(map).get(0);
	        department.setDepartmentName(departmentName);
	        department.setDepartmentNo(departmentNo);
	        department.setDepartmentMaster(departmentMaster);
	        department.setDepartmentDescript(departmentDescript);
	        department.setModifyuser(loginName);
	        department.setModifytime(new Date());;
	        departmentService.updateDepartment(department);
	        mapJson.put("result", "update");
	        String msg = JSONObject.fromObject(mapJson).toString();
			outputJson(response,msg);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("部门信息修改:"+e);
			}
	}
	/**
	 * 部门信息删除
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteDepartment.do")
	public void deleteDepartment(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		 try {
		List<Object> ids = JSON.parseArray(id);
		  for(Object bomId:ids){
			  departmentService.deleteDepartment(Integer.valueOf(bomId.toString()));
		  }
	      String msg = JSONObject.fromObject(null).toString();
		  outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("部门信息删除:"+e);
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
			List<HrDepartment> hrDepartment = departmentService.selectDepartmentList(map);
			if(hrDepartment.size()>0){
				map.put("list",hrDepartment);
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
	/**
	 * 查询全部部门和职位
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectDeptAndPositon.do")
	public void selectDeptAndPositon(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapJson = new HashMap<String,Object>();
		try {
		List<HrDepartment> deptList = departmentService.selectDepartmentList(map);
		List<HrPosition> positionList = positonService.selectPositionList(map);
		mapJson.put("deptList", deptList);
		mapJson.put("positionList", positionList);
		String msg = JSONObject.fromObject(mapJson).toString();
	    outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
