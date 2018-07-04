package org.activiti.rest.diagram.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.rest.diagram.pojo.ProcessCategory;
import org.activiti.rest.diagram.services.IProcessCategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.user.controller.UserController;
import com.cms_cloudy.user.pojo.HrUser;

@Controller
@RequestMapping(value = "/ProcessCategoryController")
public class ProcessCategoryController extends BaseController {

	@Autowired
	private IProcessCategoryService processCategoryService;
	private static final Logger logger = Logger.getLogger(UserController.class);

	/**
	 * 流程类别信息查询
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectProcessCategoryList.do")
	public void selectProcessCategoryList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> JsonMap = new HashMap<String, Object>();
		try {
			List<ProcessCategory> pcList = processCategoryService.selectProcessCategoryList(paramMap);
			JsonMap.put("list", pcList);
			String msg = JSON.toJSONString(JsonMap);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 流程类别信息添加
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertProcessCategory.do")
	public void insertProcessCategory(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			HrUser user = this.getUserInfo(request);
			String name = request.getParameter("name");
			String sign = request.getParameter("sign");
			String remark = request.getParameter("remark");
			map.put("sign", sign);
			List<ProcessCategory> pcList = processCategoryService.selectProcessCategoryList(map);
			if(pcList.size()>0){//类别标识唯一
				String msg = JSON.toJSONString("existence");
				outputJson(response, msg);
				return;
			}
			ProcessCategory pc = new ProcessCategory();
			pc.setCategoryName(name);
			pc.setCategorySign(sign);
			pc.setRemark(remark);
			pc.setCreatePerson(null == user ? "admin" : user.getLoginName());
			pc.setCreateTime(new Date());
			processCategoryService.insertProcessCategory(pc);
			String msg = JSON.toJSONString(pc);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 流程类别信息更新
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateProcessCategory.do")
	public void updateProcessCategory(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String sign = request.getParameter("sign");
			String remark = request.getParameter("remark");
			map.put("id", id);
			ProcessCategory pc = processCategoryService.selectProcessCategoryList(map).get(0);
			pc.setCategoryName(name);
			pc.setCategorySign(sign);
			pc.setRemark(remark);
			processCategoryService.updateProcessCategory(pc);
			String msg = JSON.toJSONString(pc);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 流程类别信息删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteProcessCategory.do")
	public void deleteProcessCategory(HttpServletRequest request, HttpServletResponse response) {
		try {
		String id = request.getParameter("ids");
         List<Object> idList = JSON.parseArray(id);
         for(int x=0;x<idList.size();x++){
        	 processCategoryService.deleteProcessCategory(Long.valueOf(idList.get(x).toString()));
         }
         String msg = JSON.toJSONString("删除成功！");
		outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
