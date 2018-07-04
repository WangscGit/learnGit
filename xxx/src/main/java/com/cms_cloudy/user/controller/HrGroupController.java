package com.cms_cloudy.user.controller;

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
import com.cms_cloudy.user.pojo.HrGroup;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.IHrGroupService;
import com.cms_cloudy.user.service.UserService;

@Controller
@RequestMapping(value = "/HrGroupController")
public class HrGroupController extends BaseController {
	@Autowired
	private IHrGroupService iHrGroupService;
	@Autowired
	private UserService userService;

	// 添加组
	@RequestMapping(value = "/insertGroup.do")
	public void insertGroup(HttpServletRequest request, HttpServletResponse response) {
		try {
			String group = request.getParameter("group");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (StringUtils.isEmpty(group)) {
				resultMap.put("message", "数据为空！");
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			// 从session中获取
			HrUser user = getUserInfo(request);
			HrGroup hg = JSON.parseObject(group, HrGroup.class);
			hg.setCreateuser(String.valueOf(user.getUserId()));
			hg.setCreatetime(new Date());
			hg.setModifytime(new Date());
			hg.setModifyuser(String.valueOf(user.getUserId()));
			hg.setCreateuserName(user.getUserName());
			hg.setModifyuserName(user.getUserName());
			int coun = iHrGroupService.insertGroup(hg);
			String lang = "zh";
			if (null != request.getSession().getAttribute("lang")) {
				lang = request.getSession().getAttribute("lang").toString();
			}
			if (coun == 0) {
				resultMap.put("isSucess", "0");
				resultMap.put("message", lang.equals("zh") ? "数据已存在！" : "The data has already existed!");
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			resultMap.put("message", lang.equals("zh") ? "添加成功！" : "Add success!");
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询所有组
	@RequestMapping(value = "/selectAllGroup.do")
	public void selectAllGroup(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String state=request.getParameter("state");
			List<HrGroup> hgList = iHrGroupService.selectAllGroup(new HashMap<String, Object>());
			if(state!=null&&!state.equals("-1")){
				List<Map<Object, Object>> selectedGroup = iHrGroupService.selectGroupByUserId(Long.valueOf(state));
				resultMap.put("selectedGroup", selectedGroup);
			}
			resultMap.put("message", "成功！");
			resultMap.put("hgList", hgList);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 分页查询所有组
	@RequestMapping(value = "/selectAllGroupByPage.do")
	public void selectAllGroupByPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String pageNo = request.getParameter("pageNo");
			String pageSize = "10";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pageNo", pageNo);
			paramMap.put("pageSize", pageSize);
			// HrGroup hg=JSON.parseObject(jsonData, HrGroup.class);
			List<HrGroup> hgList = iHrGroupService.selectAllGroupByPage(paramMap);
			resultMap.put("message", "成功！");
			resultMap.put("list", hgList);
			resultMap.put("pageNo", pageNo);
			resultMap.put("pageSize", pageSize);
			resultMap.put("count", iHrGroupService.selectAllGroup(new HashMap<String, Object>()).size());
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除组 TODO 同时删除用户组关系、权限组关系
	@RequestMapping(value = "/deleteGroup.do")
	public void deleteGroup(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String ids = request.getParameter("ids");
			if (StringUtils.isEmpty(ids)) {
				resultMap.put("message", "请选择一条数据！");
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			List<Object> groupIds = JSON.parseArray(ids);
			iHrGroupService.deleteGroup(groupIds);
			resultMap.put("message", "删除成功");
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 修改组信息
	@RequestMapping(value = "/updateGroup.do")
	public void updateGroup(HttpServletRequest request, HttpServletResponse response) {
		try {
			String group = request.getParameter("group");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (StringUtils.isEmpty(group)) {
				resultMap.put("message", "请选择一条数据");
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			HrUser user = getUserInfo(request);
			HrGroup hg = JSON.parseObject(group, HrGroup.class);
			hg.setModifytime(new Date());
			hg.setModifyuser(user.getLoginName());
			hg.setModifyuserName(user.getUserName());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("groupId", hg.getGroupId());
			HrGroup h = iHrGroupService.selectOneGroup(map);
			String lang = "zh";
			if (null != request.getSession().getAttribute("lang")) {
				lang = request.getSession().getAttribute("lang").toString();
			}
			if (!h.getGroupName().equals(hg.getGroupName())) {
				int coun = iHrGroupService.checkGroupForinsert(hg.getGroupName());
				if (coun > 0) {
					resultMap.put("isSucess", "0");
					resultMap.put("message", lang.equals("zh") ? "数据已存在！" : "The data has already existed!");
					String jsonString = JSON.toJSONString(resultMap);
					outputJson(response, jsonString);
					return;
				}
			}
			iHrGroupService.updateGroup(hg);
			resultMap.put("message", lang.equals("zh") ? "修改成功！" : "Update success!");
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 显示成员弹窗，所有user用户
	@RequestMapping(value = "/showAllUserDia.do")
	public void showAllUserDia(HttpServletRequest request, HttpServletResponse response) {
		try {
			String groupId = request.getParameter("groupId");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<HrUser> list = userService.selectAllUser(new HashMap<String, Object>());
			resultMap.put("userList", list);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("groupId", Long.valueOf(groupId));
			List<HrUser> groupUserList = userService.selectUserByGroup(map);
			resultMap.put("groupUserList", groupUserList);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 显示成员弹窗，所有user用户
	@RequestMapping(value = "/saveGroupUser.do")
	public void saveGroupUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String userIds = request.getParameter("userIds");
			String groupId = request.getParameter("groupId");
			List<Object> userIdList = JSON.parseArray(userIds);
			userService.saveGroupUser(userIdList, Long.valueOf(groupId));
			resultMap.put("message", "添加成功");
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 树结构展示组
	@RequestMapping(value = "/selectAllGroupTree.do")
	public void selectAllGroupTree(HttpServletRequest request, HttpServletResponse response) {
		try {
			String lang = "zh";
			if (null != request.getSession().getAttribute("lang")) {
				lang = request.getSession().getAttribute("lang").toString();
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			Map<Object, Object> paramMap = new HashMap<Object, Object>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			map.put("id", "-1");
			map.put("pId", "0");
			if ("zh".equals(lang)) {
				map.put("name", "电子设计中心");
			} else {
				map.put("name", "Design center");
			}
			//有权操作产品设计模块权限组ID获取
	    	List<String> groupIdList = iHrGroupService.getGroupIds("pages/productPage/cms-productBom.jsp");
	    	paramMap.put("groupIdList", groupIdList);
			List<Map<Object, Object>> hgList = iHrGroupService.selectAllGroupTree(paramMap);
			hgList.add(map);
			resultMap.put("message", "成功！");
			resultMap.put("list", hgList);
			String jsonString = JSON.toJSONString(hgList);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
