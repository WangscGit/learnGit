package com.cms_cloudy.user.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.controller.ExportExcel;
import com.cms_cloudy.controller.FileController;
import com.cms_cloudy.controller.ReadExcel;
import com.cms_cloudy.user.pojo.HrDepartment;
import com.cms_cloudy.user.pojo.HrGroupUser;
import com.cms_cloudy.user.pojo.HrPosition;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.IDepartmentService;
import com.cms_cloudy.user.service.IHrGroupService;
import com.cms_cloudy.user.service.IPositionService;
import com.cms_cloudy.user.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

/* 
 *http://blog.csdn.net/zhujianli1314/article/details/43193183 JSONʵ��
 */  
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{
	@Autowired  
	private UserService userService;  
	@Autowired
	private IPositionService positonService;
	@Autowired
    private IDepartmentService departmentService;
	@Autowired
	private IHrGroupService iHrGroupService;
	private static final Logger logger = Logger.getLogger(UserController.class);
	Map<String,Object> mapJson = new HashMap<String,Object>();
	String msgJson="";
	@RequestMapping(value="/select.do")  
	public String select(Integer id,Map<String,Object> map){  
	List<HrUser>list= new ArrayList<HrUser>();  
	list=userService.selectUser(1);  
	map.put("mylist",list);  
	return"list";  
	}
	/** 
	 * 用户删除
	 */  
	@RequestMapping(value="/deleteUser.do")  
	public void deleteUser(HttpServletRequest request, HttpServletResponse response){  
		Map<String,Object> mapJson = new HashMap<String,Object>();
		String msgJson="";
		String id = request.getParameter("id");
		List<Object> ids = JSON.parseArray(id);
		for(Object userId:ids){
			userService.deleteUserFromGroup(Integer.valueOf(userId.toString()));//删除人员组关系表
			userService.deleteUser(Integer.valueOf(userId.toString())); //删除人员
		}
		mapJson.put("result", "success");
		msgJson = JSONObject.fromObject(mapJson).toString();
		try {
			outputJson(response,msgJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	/**
	 * 用户删除
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/inserUser.do")  
	public void inserUser( HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> mapJson = new HashMap<String,Object>();
		String msgJson="";
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("passWord");
		String email = request.getParameter("email");
		HrUser userInfo = this.getUserInfo(request);
		String currentLoginName= "";
		if(null == userInfo){
			currentLoginName = "admin";
		}else{
			currentLoginName = userInfo.getLoginName();

		}
		List<HrUser> hruser = userService.selectUserByName(loginName);
		try {
			email = java.net.URLDecoder.decode(email, "UTF-8");
			//����û�
		    if(hruser.size()>0){
		    	mapJson.put("result", "repeart");
				msgJson = JSONObject.fromObject(mapJson).toString();
			}else{
				HrUser user = new HrUser();
				user.setLoginName(loginName);
				user.setPassword(password);
				user.setEmail(email);
				user.setCreatetime(new Date());
				user.setCreateuser(currentLoginName);
				userService.insertUser(user);
				mapJson.put("result", "insert");
			}
			msgJson = JSONObject.fromObject(mapJson).toString();
			outputJson(response,msgJson);
		} catch (Exception e) {
			mapJson.put("result", "fail");
			msgJson = JSONObject.fromObject(mapJson).toString();
			try {
			outputJson(response,msgJson);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	/**
	 * 添加或者修改用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/inertOrUpdate.do")  
	public void inertOrUpdate( HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> mapJson = new HashMap<String,Object>();
		HrUser userInfo =  getUserInfo(request);
		String currentLoginName= "";
		if(null == userInfo){
			currentLoginName = "admin";
		}else{
			currentLoginName = userInfo.getLoginName();

		}
		String employeeNumber = request.getParameter("employeeNumber");
		String userNumber = request.getParameter("userNumber");
		String userName = request.getParameter("userName");
		String position = request.getParameter("position");
		String email = request.getParameter("email");
		String loginName = request.getParameter("loginName");
		String passWord = request.getParameter("passWord");
		String telephone = request.getParameter("telephone");
		String mobilePhone = request.getParameter("mobilePhone");
		String department = request.getParameter("department");
		String isOrNot = request.getParameter("isOrNot");
		String type = request.getParameter("type");
		String password=request.getParameter("password");
		String groupVal=request.getParameter("groupVal");
		String groupText=request.getParameter("groupText");
		try {
			if(!StringUtils.isEmpty(email)){
				email = java.net.URLDecoder.decode(email, "UTF-8");
			}if(!StringUtils.isEmpty(employeeNumber)){
				employeeNumber = java.net.URLDecoder.decode(employeeNumber, "UTF-8");
			}if(!StringUtils.isEmpty(userNumber)){
				userNumber = java.net.URLDecoder.decode(userNumber, "UTF-8");
			}if(!StringUtils.isEmpty(userName)){
				userName = java.net.URLDecoder.decode(userName, "UTF-8");
			}if(!StringUtils.isEmpty(loginName)){
				loginName = java.net.URLDecoder.decode(loginName, "UTF-8");
			}if(!StringUtils.isEmpty(position)){
				position = java.net.URLDecoder.decode(position, "UTF-8");
			}if(!StringUtils.isEmpty(telephone)){
				telephone = java.net.URLDecoder.decode(telephone, "UTF-8");
			}if(!StringUtils.isEmpty(mobilePhone)){
				mobilePhone = java.net.URLDecoder.decode(mobilePhone, "UTF-8");
			}if(!StringUtils.isEmpty(department)){
				department = java.net.URLDecoder.decode(department, "UTF-8");
			}if(!StringUtils.isEmpty(isOrNot)){
				isOrNot = java.net.URLDecoder.decode(isOrNot, "UTF-8");
			}
			if(!StringUtils.isEmpty(password)){
				password=java.net.URLDecoder.decode(password, "UTF-8");
			}
			List<HrUser> hruser = userService.selectUserByName(loginName);
			if("add".equals(type) && hruser.size()>0){
				mapJson.put("result", "repeart");
				String msgJson = JSONObject.fromObject(mapJson).toString();
				outputJson(response,msgJson);
				return;
			}
			List<Object> groupId = JSON.parseArray(groupVal);
			List<Object> groupName = JSON.parseArray(groupText);
			if(hruser.size()>0){
				for(HrUser users:hruser){
					users.setEmployeeNumber(employeeNumber);
					users.setUserNumber(userNumber);
					users.setUserName(userName);
					users.setPosition(position);
					users.setTelephone(telephone);
					users.setMobilePhone(mobilePhone);
					users.setDepartment(department);
					//users.setPassword(passWord);//不修改密码
					users.setModifytime(new Date());
					users.setModifyuser(currentLoginName);
					users.setIsOrNot(Integer.valueOf(isOrNot));
					users.setEmail(email);
					userService.updateUser(users);	
					mapJson.put("result", "update");
					updateUserGroup(groupId,groupName,users.getUserId());
				}
			}else{
				HrUser user = new HrUser();
				user.setEmployeeNumber(employeeNumber);
				user.setUserNumber(userNumber);
				user.setUserName(userName);
				user.setLoginName(loginName);
				user.setPosition(position);
				user.setTelephone(telephone);
				user.setMobilePhone(mobilePhone);
				user.setDepartment(department);
				user.setCreatetime(new Date());
				user.setCreateuser(currentLoginName);
				user.setIsOrNot(Integer.valueOf(0));
				if(!StringUtils.isEmpty(passWord)){
					user.setPassword(passWord);
				}else{
					user.setPassword(1+"");
				}
				user.setEmail(email);
				userService.insertUser(user);
				mapJson.put("result", "insert");
				user = userService.selectUserByName(loginName).get(0);
				updateUserGroup(groupId,groupName,user.getUserId());
			}
			String msgJson = JSONObject.fromObject(mapJson).toString();
			outputJson(response,msgJson);
		} catch (Exception e) {
			mapJson.put("result", "fail");
			msgJson = JSONObject.fromObject(mapJson).toString();
			try {
				outputJson(response,msgJson);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	/** 
	 * 查询用户
	 */  
	@RequestMapping(value="/selectUserByPage.do") 
	public void selectUserByPage(HttpServletRequest request, HttpServletResponse response){
		String currentPage = request.getParameter("currentPage");
		String limit = request.getParameter("limit");
		long totalCount = userService.countUser();
		if(StringUtils.isEmpty(currentPage)){
			currentPage = "1";
		}
		if(StringUtils.isEmpty(limit)){
			limit = "10";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("fromNum", Integer.valueOf(limit));
		map.put("endNum", Integer.valueOf(currentPage));
		map.put("loginName", "2");
		List<HrUser> hruser = userService.selectUserByPage(map);
		//��ҳ��
		double totalPages = Math.ceil(totalCount/Long.valueOf(limit));
	}
	/** 
	 * 分页查询用户列表
	 */  
	@RequestMapping(value="/selectAllUser.do") 
	public void selectAllUser(HttpServletRequest request, HttpServletResponse response){
	    Map<String,Object> map = new HashMap<String,Object>();
	    Map<String,Object> mapJson = new HashMap<String,Object>();
		List<HrUser> list = new ArrayList<HrUser>();
		PageInfo<HrUser> page = null;
		String pageNo = request.getParameter("pageNo");
		String loginName = request.getParameter("loginName");
		// 分页初始化
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = "1";
		}
		String pageSize = "5";
		try {
		PageHelper.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
	    if(!"".equals(loginName)){
		    map.put("loginName", loginName);
	    }
	    list = userService.selectAllUser(map);
	    if(list.size()>0){
			page = new PageInfo<HrUser>(list);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	 * 导入EXCEL
	 */  
	@RequestMapping(value="/readExcel.do") 
	public void readExcel(HttpServletRequest request, HttpServletResponse response){
		ReadExcel excel = new ReadExcel();
        FileController files = new FileController();
		 Map<String,Object> mapJson = new HashMap<String,Object>();
        String date = System.currentTimeMillis()+"";
        //临时目录
	    String dir = getAbsoluteBasePath(request)+"WEB-INF"+File.separator+"userExport"+File.separator+date;
		 MultipartHttpServletRequest mr ;
		String fpath = "";
        if (request instanceof MultipartHttpServletRequest) {
           mr = (MultipartHttpServletRequest) request;
           try {
			files.checkPcbFile(mr, dir);
		    File file = new File(dir);
			File[] fileList = file.listFiles();
			String path = dir+"\\"+fileList[0].getName();
			List<List<Object>> list = excel.readExcel(new File(path));
			if(list.size()<=1){
				 String msg = "Excel内容不能为空，请修改！";
				 mapJson.put("message", msg);
				 msgJson = JSONObject.fromObject(mapJson).toString();
				 outputMsg(response,msgJson);
				 return;
			}
			Set<String> sett= new HashSet<String>();
			int result = checkUserLoginname(list,sett);
			if(list.size()-result>1){
				 String msg = "Excel内账号不能重复，请修改！";
				 mapJson.put("message", msg);
				 msgJson = JSONObject.fromObject(mapJson).toString();
				 outputMsg(response,msgJson);
				 return;
			}
			int userName = -1;
			int loginName = -1;
			for(int i=0;i<list.get(0).size();i++){
				Object obj = list.get(0).get(i);
				if(obj.equals("中文名")){
					userName = i;
				}
				if(obj.equals("账号")){
					loginName = i;
				}
			}
			if(userName == -1){
				 String msg = "缺少中文名字段以及对应的值，请修改！";
				 mapJson.put("message", msg);
				 msgJson = JSONObject.fromObject(mapJson).toString();
				 outputMsg(response,msgJson);
				 return;
			}
			if(loginName == -1){
				String msg = "缺少帐号字段以及对应的值，请修改！";
				 mapJson.put("message", msg);
				 msgJson = JSONObject.fromObject(mapJson).toString();
				 outputMsg(response,msgJson);
				 return;
			}
			 for(int i=1;i<list.size();i++){
				 String s = list.get(i)+"";
				 String str = s.substring(1,s.length()-1);
				 String[] str1= str.split(",");
				 List<Object> obj = list.get(i);
				 if(StringUtils.isEmpty(obj.get(1))){
					 String msg = "用户名字段对应的值不能为空，请修改！";
					 mapJson.put("message", msg);
					 msgJson = JSONObject.fromObject(mapJson).toString();
					 outputMsg(response,msgJson);
					 return;
				 }
				 if(StringUtils.isEmpty(obj.get(2))){
					 String msg = "账号字段对应的值不能为空，请修改！";
					 mapJson.put("message", msg);
					 msgJson = JSONObject.fromObject(mapJson).toString();
					 outputMsg(response,msgJson);
					 return;
				 }
				 Map<String,Object> map = new HashMap<String,Object>();
				 map.put("exportName", obj.get(2));
				 List<HrUser> userList = userService.selectAllUser(map);
				 if(userList.size()>0){
					 String msg = "账号："+obj.get(2)+"在数据库中已存在，请修改！";
					 mapJson.put("message", msg);
					 msgJson = JSONObject.fromObject(mapJson).toString();
					 outputMsg(response,msgJson);
					 return;
				 }
			 }
//			 String temp = "";
//		        for (int i = 2; i < list.size() - 1; i++)
//		        {
//		            temp = ls.get(i);
//		            for (int j = i + 1; j < list.size(); j++)
//		            {
//		                if (temp.equals(list.get(j)))
//		                {
//		                	 mapJson.put("result", "ripeatExcel");
//							 msgJson = JSONObject.fromObject(mapJson).toString();
//							 outputMsg(response,msgJson);
//							 return;
//		                }
//		            }
//		        }
			 for(int i=1;i<list.size();i++){
				 List<Object> obj = list.get(i);
				 HrUser user = new HrUser();
				 user.setEmployeeNumber(null==obj.get(3)?"":obj.get(3).toString());
				 user.setUserNumber(null==obj.get(4)?"":obj.get(4).toString());
				 user.setUserName(null==obj.get(1)?"":obj.get(1).toString());
				 user.setLoginName(null==obj.get(2)?"":obj.get(2).toString());
				 Map<String,Object> mapPosition = new HashMap<String,Object>();
				 mapPosition.put("id", null==obj.get(5) || "请选择".equals(obj.get(5))?"":obj.get(5).toString());
				 List<HrPosition> posiion = positonService.selectPositionList(mapPosition);
				 user.setPosition(null!=posiion && posiion.size()>0?null==obj.get(5)?"":obj.get(5).toString():"请选择");
				 user.setEmail(null==obj.get(6)?"":obj.get(6).toString());
				 user.setTelephone(null==obj.get(7)?"":obj.get(7).toString());
				 user.setMobilePhone(null==obj.get(8)?"":obj.get(8).toString());
				 Map<String,Object> mapDepartment = new HashMap<String,Object>();
				 mapDepartment.put("id", null==obj.get(9) || "请选择".equals(obj.get(9))?"":obj.get(9).toString());
				 List<HrDepartment> department  = departmentService.selectDepartmentList(mapDepartment);
				 user.setDepartment(null !=department && department.size()>0?null==obj.get(9)?"":obj.get(9).toString():"请选择");
				 if("".equals(null==obj.get(10)?"":obj.get(9).toString())){
			     user.setIsOrNot(0); 
				 }
				 else if("离职".equals(null==obj.get(10)?"":obj.get(10).toString())){
				     user.setIsOrNot(1); 
				 }else if("在职".equals(null==obj.get(10)?"":obj.get(10).toString())){
				     user.setIsOrNot(0); 
				 }else{
				     user.setIsOrNot(0); 
				 }
				 user.setCreateuser(getUserInfo(request).getLoginName());
				 user.setPassword("cfcd208495d565ef66e7dff9f98764da");
				 user.setCreatetime(new Date());
				 userService.insertUser(user);
			 }
			//删除临时目录
			 String dirDel = getAbsoluteBasePath(request)+"WEB-INF"+File.separator+"userExport";
			 deletefileMulu(dirDel);
			 mapJson.put("message", "导入成功！");
			 msgJson = JSONObject.fromObject(mapJson).toString();
			 outputMsg(response,msgJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
       }
	}
	/**
	 * 用户表导出
	 * @param response
	 */
	@RequestMapping(value="/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
	 	try {
	 	String ids = request.getParameter("ids");
	 	List<String> idList = JSON.parseArray(ids,String.class);
		String[] headers = {"员工编号","用户编号","中文名","英文名","任职职位","邮箱","电话","手机","所在部门","创建者","创建时间","是否离职"};
	 	Map<String,Object> map = new HashMap<String,Object>();
	 	map.put("filterField", null == idList || idList.size() == 0 ? null : idList);
	 	List<HrUser> list = userService.selectUserToExport(map);
	     HSSFWorkbook wb = (new ExportExcel()).exportUserExcel(list,headers);
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("text/html;charset=UTF-8");
	        response.setContentType("application/vnd.ms-excel");  
	        response.setHeader("Content-disposition", "attachment;filename=\""+ new String("用户信息".getBytes( "gb2312" ), "ISO8859-1" )+ ".xls" + "\"" ); 
	        OutputStream ouputStream;
			ouputStream = response.getOutputStream();
	        wb.write(ouputStream);  
	        ouputStream.flush();  
	        ouputStream.close();  
		   } catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 组成员展示
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectAllUserByGroup.do")
	public void selectAllUserByGroup(HttpServletRequest request, HttpServletResponse response){
		    Map<String,Object> map = new HashMap<String,Object>();
		    Map<String,Object> mapUser = new HashMap<String,Object>();
		    Map<String,Object> mapJson = new HashMap<String,Object>();
			List<HrUser> list = new ArrayList<HrUser>();
			String groupId = request.getParameter("groupId");
			String loginName = request.getParameter("loginName");
			String rights = request.getParameter("rights");
			try {
		    if(!"".equals(groupId)){
			    map.put("groupId", groupId);
			    List<HrGroupUser> groupList =  userService.selectAllUserByGroup(map);
			    if(groupList.size()>0){
			    	for(HrGroupUser userId:groupList){
			    		mapUser.put("id", userId.getUserId());
			    		List<HrUser>  user = userService.selectAllUser(mapUser);
			    		list.addAll(user);
			    	}
					mapJson.put("list", list);
			    	String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
		    }else{
		    	mapJson.put("list", null);
		    	String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
		    }
		    }else{
		    	if(rights.equals("true")){
		    		//有权操作产品设计模块权限组ID获取
			    	List<String> groupIdList = iHrGroupService.getGroupIds("pages/productPage/cms-productBom.jsp");
			    	mapUser.put("groupIdList", groupIdList);
		    	}
		    	mapUser.put("loginName", loginName);
		    	list = userService.selectAllUser(mapUser);
		    	if(list.size()>0){
		    		mapJson.put("list", list);
			    	String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
		    	}else{
		    		mapJson.put("list", null);
			    	String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
		    	}
		    }
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	/**
	 * 部门人员展示
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectAllUserByDept.do")
	public void selectAllUserByDept(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapJson = new HashMap<String,Object>();
		String depeId = request.getParameter("deptId");
		List<HrUser> list = new ArrayList<HrUser>();
		try {
			    map.put("deptId", depeId);
				list = userService.selectAllUser(map);
				if(list.size()>0){
					mapJson.put("list", list);
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
				}else{
					mapJson.put("list", null);
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 校验新增英文名是否已经存在
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/checkLoginName.do")
	public void checkLoginName(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String loginName = request.getParameter("loginName");
		try {
		List<HrUser> hruser = userService.selectUserByName(loginName);
        if(hruser.size()>0){
        	map.put("result", "no");
        	String msg = JSONObject.fromObject(map).toString();
			outputJson(response,msg);
        }else{
        	map.put("result", "yes");
        	String msg = JSONObject.fromObject(map).toString();
			outputJson(response,msg);
        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *修改前
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
			List<HrUser> hruser = userService.selectAllUser(map);
			if(hruser.size()>0){
				map.put("list",hruser);
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
	 *  获得项目的根路径
	 * @param request
	 * @return
	 */
	public String getAbsoluteBasePath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
    }
	//删除目录
			public static boolean deletefileMulu(String delpath) throws Exception {
			    try {

			        File file = new File(delpath);
			        // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
			        if (!file.isDirectory()) {
			            file.delete();
			        } else if (file.isDirectory()) {
			            String[] filelist = file.list();
			            for (int i = 0; i < filelist.length; i++) {
			                File delfile = new File(delpath + "\\" + filelist[i]);
			                if (!delfile.isDirectory()) {
			                    delfile.delete();
			                    System.out.println(delfile.getAbsolutePath() + "删除文件成功");
			                } else if (delfile.isDirectory()) {
			                	deletefileMulu(delpath + "\\" + filelist[i]);
			                }
			            }
			            System.out.println(file.getAbsolutePath() + "删除成功");
			            file.delete();
			        }

			    } catch (FileNotFoundException e) {
			        System.out.println("deletefile() Exception:" + e.getMessage());
			    }
			    return true;
			}
			/**
			 * 密码重置
			 * @param request
			 * @param response
			 */		
	@RequestMapping(value="/resetPassword.do")
	public void resetPassword(HttpServletRequest request, HttpServletResponse response){
		 Map<String,Object> map = new HashMap<String,Object>();
		 String loginName = request.getParameter("loginNameReset");
		 map.put("exportName", loginName);
		 try {
		 List<HrUser> userList = userService.selectAllUser(map);
		if(null == userList || userList.size()<=0){
			String msg = JSON.toJSONString(null);
			outputJson(response,msg);
		}else{
			HrUser user = userList.get(0);
			user.setPassword("cfcd208495d565ef66e7dff9f98764da");
			userService.updateUser(user);
			String msg = JSON.toJSONString(user);
			outputJson(response,msg);
		}
		 } catch (Exception e) {
			e.printStackTrace();
	     }
	}
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/modifyPassword.do")
	public void modifyPassword(HttpServletRequest request, HttpServletResponse response){
		 Map<String,Object> map = new HashMap<String,Object>();
		String loginName = request.getParameter("pubLoginName");
		String passWord = request.getParameter("passWord");
		 map.put("exportName", loginName);
		 try {
		 List<HrUser> userList = userService.selectAllUser(map);
		 if(null == userList || userList.size()<=0){
				String msg = JSON.toJSONString(null);
				outputJson(response,msg);
			}else{
				HrUser user = userList.get(0);
				user.setPassword(passWord);
				userService.updateUser(user);
				request.getSession().setAttribute("user",null);
				request.getSession().setAttribute("menuRightList",null);
				request.getSession().setAttribute("dataRightList",null);
				String msg = JSON.toJSONString(user);
				outputJson(response,msg);
			}
		 } catch (Exception e) {
			e.printStackTrace();
	     }
	}
	@RequestMapping(value="/selectAllUserForSelect.do") 
	public void selectAllUserForSelect(HttpServletRequest request, HttpServletResponse response){
		    Map<String,Object> map = new HashMap<String,Object>();
		    Map<String,Object> mapUser = new HashMap<String,Object>();
		    Map<String,Object> mapJson = new HashMap<String,Object>();
			List<HrUser> list = new ArrayList<HrUser>();
			PageInfo<HrUser> page = null;
			String pageNo = request.getParameter("pageNo");
			String loginName = request.getParameter("loginName");
			String groupId = request.getParameter("groupId");
			// 分页初始化
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = "1";
			}
			String pageSize = "6";
			try {
			PageHelper.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
		    if(!"".equals(loginName) && null != loginName){
			    map.put("loginName", loginName);
			    list = userService.selectAllUser(map);
			    if(list.size()>0){
					page = new PageInfo<HrUser>(list);
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
		    }
		    if(!"".equals(groupId)){
		    	 map.put("groupId", groupId);
				    List<HrGroupUser> groupList =  userService.selectAllUserByGroup(map);
				    if(groupList.size()>0){
				    	String userIds = "";
				    	for(int l=0;l<groupList.size();l++){
				    		HrGroupUser userId =groupList.get(l);
				    		mapUser.put("id", userId.getUserId());
				    		List<HrUser>  user = userService.selectAllUser(mapUser);
				    		list.addAll(user);
				    	}
				    	PageInfo<HrGroupUser> pages  = new PageInfo<HrGroupUser>(groupList);
						mapJson.put("list", list);
						mapJson.put("count", pages.getTotal());
						mapJson.put("pageNo", pageNo);
						mapJson.put("pageSize", pageSize);
				    	String msg = JSONObject.fromObject(mapJson).toString();
						outputJson(response, msg);
			      }else{
			    	mapJson.put("list", null);
			    	String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
			    }
		    }
		    list = userService.selectAllUser(mapUser);
		    if(null == list || list.size()<=0){
		    	mapJson.put("list", null);
		    	String msg = JSONObject.fromObject(null).toString();
				outputJson(response, msg);
		    }
    		page = new PageInfo<HrUser>(list);
			mapJson.put("list", page.getList());
			mapJson.put("count", page.getTotal());
			mapJson.put("pageNo", pageNo);
			mapJson.put("pageSize", pageSize);
	    	String msg = JSONObject.fromObject(mapJson).toString();
			outputJson(response, msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	@RequestMapping(value="/selectAllUserForDBSelect.do") 
	public void selectAllUserForDBSelect(HttpServletRequest request, HttpServletResponse response){
		    Map<String,Object> map = new HashMap<String,Object>();
		    Map<String,Object> mapUser = new HashMap<String,Object>();
		    Map<String,Object> mapJson = new HashMap<String,Object>();
			List<HrUser> list = new ArrayList<HrUser>();
			PageInfo<HrUser> page = null;
			String loginName = request.getParameter("loginName");
			String groupId = request.getParameter("groupId");
			try {
		    if(!"".equals(loginName) && null != loginName){
			    map.put("loginName", loginName);
			    list = userService.selectAllUser(map);
			    if(list.size()>0){
					page = new PageInfo<HrUser>(list);
					mapJson.put("list", page.getList());
					mapJson.put("count", page.getTotal());
			    	String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
			    }else{
			    	mapJson.put("list", new ArrayList<HrUser>());
			    	String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
			    }
		    }
		    if(!"".equals(groupId)){
		    	 map.put("groupId", groupId);
				    List<HrGroupUser> groupList =  userService.selectAllUserByGroup(map);
				    if(groupList.size()>0){
				    	String userIds = "";
				    	for(int l=0;l<groupList.size();l++){
				    		HrGroupUser userId =groupList.get(l);
				    		mapUser.put("id", userId.getUserId());
				    		List<HrUser>  user = userService.selectAllUser(mapUser);
				    		list.addAll(user);
				    	}
				    	PageInfo<HrGroupUser> pages  = new PageInfo<HrGroupUser>(groupList);
						mapJson.put("list", list);
						mapJson.put("count", pages.getTotal());
				    	String msg = JSONObject.fromObject(mapJson).toString();
						outputJson(response, msg);
			      }else{
			    	mapJson.put("list", new ArrayList<HrUser>());
			    	String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
			    }
		    }
		    list = userService.selectAllUser(mapUser);
		    if(null == list || list.size()<=0){
		    	mapJson.put("list", new ArrayList<HrUser>());
		    	String msg = JSONObject.fromObject(null).toString();
				outputJson(response, msg);
		    }
			mapJson.put("list", list);
			mapJson.put("count",list.size());
	    	String msg = JSONObject.fromObject(mapJson).toString();
			outputJson(response, msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	/**
	 * 消息发布---接收人列表加载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectAllUserToSend.do") 
	public void selectAllUserToSend(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		try {
		List<HrUser> list =userService.selectAllUser(new HashMap<String,Object>());
		resultMap.put("userList", list);
		String jsonString =JSON.toJSONString(resultMap);
		outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 检查Excel内的账号是否重复
	 * @param sheet
	 * @param index
	 */
	public int  checkUserLoginname(List<List<Object>> sheet,Set<String> sett){
		int rows = 0;
		 try {
			rows = sheet.size();
			for(int i=0;i<rows;i++){
				if(i == 0){
					continue;
				}
				List<Object> objList = sheet.get(i);
				for(int x=0;x<objList.size();x++){
					sett.add(objList.get(2).toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return sett.size();
	}
	//更新用户组表信息
	public void updateUserGroup(List<Object> groupIds,List<Object> groupNames,long userId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		iHrGroupService.deleteUserGroupRelation(map);//删除 该用户所在的组
		if(null != groupIds && groupIds.size() > 0){
			for(int x=0;x<groupIds.size();x++){
				map.put("groupId", groupIds.get(x));
				map.put("groupName", groupNames.get(x));
				iHrGroupService.insertHrGroupUser(map);// 添加用户所在组
			}
		}
	}
}
