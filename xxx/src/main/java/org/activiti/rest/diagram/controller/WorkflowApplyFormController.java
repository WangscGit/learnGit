package org.activiti.rest.diagram.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.rest.diagram.pojo.TaskUser;
import org.activiti.rest.diagram.pojo.WorkflowTaskEntitiy;
import org.activiti.rest.diagram.pojo.WorlkflowMainEntity;
import org.activiti.rest.diagram.services.ITaskUserService;
import org.activiti.rest.diagram.services.IWorkflowBaseService;
import org.activiti.rest.diagram.services.IWorlkflowMainEntityService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.cms_cloudy.component.service.IPartDataService;
import com.cms_cloudy.component.service.IPartPrimaryAttributesService;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.controller.ExportExcel;
import com.cms_cloudy.database.pojo.FileImg;
import com.cms_cloudy.database.service.IPartClassService;
import com.cms_cloudy.message.pojo.SysMessage;
import com.cms_cloudy.message.pojo.SysMessageDetail;
import com.cms_cloudy.message.service.ISysMessageService;
import com.cms_cloudy.upload.controller.FileExportController;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.UserService;
import com.cms_cloudy.util.ProjectConstants;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/WorkflowApplyFormController")
public class WorkflowApplyFormController extends BaseController {
	@Autowired
	private IPartDataService iPartDataService;
	@Autowired
	private IPartClassService iPartClassService;
	@Autowired
	private IWorkflowBaseService workflowBaseService;
	@Autowired
	private ISysMessageService sysMessageService;
	@Autowired
	private ITaskUserService iTaskUserService;
	@Autowired
	private IPartPrimaryAttributesService partPrimaryAttributesService;
	@Autowired
	private IPartDataService partDataService;
	@Autowired
	private IWorlkflowMainEntityService iWorlkflowMainEntityService;
	@Autowired
	private UserService userService;
	// 流程引擎初始化
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 器件申请单保存或者修改
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/savepartApplyPage.do")
	public void savepartApplyPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			String lock = request.getParameter("lock");
			String formType = request.getParameter("formType");
			String taskId = request.getParameter("taskId");
			String jsonData = request.getParameter("jsonData");
			String processAssignees = request.getParameter("processAssignees");// 启动时流程执行人处理
			String typeOption = request.getParameter("typeOption");// 人员审批关系
			Map<String, Object> dataMap = (Map<String, Object>) JSON.parse(jsonData);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> processMap = new HashMap<String, Object>();
			Map<String, Object> businessMap = new HashMap<String, Object>();
			List<WorlkflowMainEntity> mainList = null;
			WorlkflowMainEntity processMain = null;
			HrUser user = getUserInfo(request);
			if (user == null) {
				resultMap.put("message", "2");
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
				return;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(new Date());
			Map<String, String> imgMap = uploadPCImg(request);// 保存图片，获取路径
			dataMap.putAll(imgMap);
			if (lock.equals("2") || lock.equals("4")) {
				WorkflowTaskEntitiy t = workflowBaseService.selectWorkflowTaskServiceById(Long.valueOf(taskId));
				if (null == t) {
					resultMap.put("message", "3");
					String msg = JSON.toJSONString(resultMap);
					outputMsg(response, msg);
					return;
				} else {
					processMain = new WorlkflowMainEntity();
					processMain.setId(t.getProcessMainId());
					mainList = workflowBaseService.selectProcessMain(processMain);
				}
			} else {
				processMain = new WorlkflowMainEntity();
				processMain.setCdefine3(formType);
				mainList = workflowBaseService.selectProcessMain(processMain);
			}
			if (null == mainList || mainList.size() <= 0) {
				resultMap.put("message", "3");
				String msg = JSON.toJSONString(resultMap);
				outputMsg(response, msg);
				return;
			}
			processMain = mainList.get(0);
			if (lock.equals("1")) {// 添加
				dataMap.put("state", "1");// 检出
				dataMap.put("Modifier", user.getLoginName());
				dataMap.put("ModifyDate", dateString);
				dataMap.put("Creator", user.getLoginName());
				dataMap.put("CreateDate", dateString);
				dataMap.put("process_state", ProjectConstants.PROCESS_STATE_WEIQIDONG);
				dataMap.put("PartNumber", FileExportController.getUUID());
				Long id = iPartDataService.insertPartData(dataMap);
				if (id == 0l) {
					resultMap.put("message", "0");
					String jsonString = JSON.toJSONString(resultMap);
					outputMsg(response, jsonString);
					return;
				}
				resultMap.put("message", "1");
				resultMap.put("id", id);
				// ----
				businessMap.put("user", user);
				businessMap.put("id", id);// 器件表ID
				businessMap.put("processMain", processMain);
				businessMap.put("lock", lock);
				saveWorkflowTask(businessMap);
				// ----
				Map<String, Object> AssigneesMap = new HashMap<String, Object>();
				long newTask = workflowBaseService.selectMaxId("WF_TASK_ENTITY");
				AssigneesMap.put("processAssignees", processAssignees);
				AssigneesMap.put("typeOption", typeOption);
				AssigneesMap.put("newTask", newTask);
				saveAssigent(AssigneesMap);
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
			} else if (lock.equals("2")) {// 修改
				// 修改前，从数据库查出该器件基本信息
				Map<String, Object> updateDataBefore = new HashMap<String, Object>();
				if (null != dataMap && StringUtils.isNotEmpty(dataMap.get("id").toString())) {
					updateDataBefore = partPrimaryAttributesService.selectPartDateById(dataMap).get(0);
				}
				if(!updateDataBefore.get("PartCode").equals(dataMap.get("PartCode"))){
					long l=iPartDataService.checkPartCode((String) dataMap.get("PartCode"));
					if(l!=0l){
						resultMap.put("message", "0");
						String jsonString = JSON.toJSONString(resultMap);
						outputMsg(response, jsonString);
						return;
					}
				}
				dataMap.put("state", "1");// 检出
				dataMap.put("Modifier", user.getLoginName());
				dataMap.put("ModifyDate", dateString);
				dataMap.put("process_state", ProjectConstants.PROCESS_STATE_WEIQIDONG);
				
				iPartDataService.updatePartData(dataMap);
				// -----
				businessMap.put("user", user);
				businessMap.put("id", Long.valueOf(taskId));// 流程字表ID
				businessMap.put("processMain", processMain);
				businessMap.put("lock", lock);
				saveWorkflowTask(businessMap);
				// -----
				Map<String, Object> AssigneesMap = new HashMap<String, Object>();
				AssigneesMap.put("processAssignees", processAssignees);
				AssigneesMap.put("typeOption", typeOption);
				AssigneesMap.put("newTask", Long.valueOf(taskId));
				saveAssigent(AssigneesMap);
				resultMap.put("message", "1");
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
			} else if (lock.equals("3")) {// 添加并提交审批
				dataMap.put("state", "1");// 检出
				dataMap.put("Modifier", user.getLoginName());
				dataMap.put("ModifyDate", dateString);
				dataMap.put("Creator", user.getLoginName());
				dataMap.put("CreateDate", dateString);
				dataMap.put("process_state", ProjectConstants.PROCESS_STATE_SHENPIZHONG);
				dataMap.put("PartNumber", FileExportController.getUUID());
				Long id = iPartDataService.insertPartData(dataMap);
				if (id == 0l) {
					resultMap.put("message", "0");
					String jsonString = JSON.toJSONString(resultMap);
					outputMsg(response, jsonString);
					return;
				}
				processMap.put("businessId", id);
				processMap.put("processDefinitionId", processMain.getProcessDefinitionId());
				processMap.put("processMainid", processMain.getId());
				processMap.put("HrUser", user);
				processMap.put("processAssignees", processAssignees);
				processMap.put("typeOption", typeOption);
				startProcessInstance(processMap);// 启动流程
				resultMap.put("message", "1");
				resultMap.put("id", id);
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
			} else {// 修改并提交审批
				// 修改前，从数据库查出该器件基本信息
				Map<String, Object> updateDataBefore = new HashMap<String, Object>();
				if (null != dataMap && StringUtils.isNotEmpty(dataMap.get("id").toString())) {
					updateDataBefore = partPrimaryAttributesService.selectPartDateById(dataMap).get(0);
				}
				if(!updateDataBefore.get("PartCode").equals(dataMap.get("PartCode"))){
					long l=iPartDataService.checkPartCode((String) dataMap.get("PartCode"));
					if(l!=0l){
						resultMap.put("message", "0");
						String jsonString = JSON.toJSONString(resultMap);
						outputMsg(response, jsonString);
						return;
					}
				}
				dataMap.put("state", "1");// 检出
				dataMap.put("Modifier", user.getLoginName());
				dataMap.put("ModifyDate", dateString);
				dataMap.put("process_state", ProjectConstants.PROCESS_STATE_WEIQIDONG);
				iPartDataService.updatePartData(dataMap);
				// -----
				processMap.put("businessId", Long.valueOf(dataMap.get("id").toString()));
				processMap.put("processDefinitionId", processMain.getProcessDefinitionId());
				processMap.put("processMainid", processMain.getId());
				processMap.put("HrUser", user);
				processMap.put("id", Long.valueOf(taskId));// 流程字表ID
				processMap.put("lock", lock);
				processMap.put("processAssignees", processAssignees);
				processMap.put("typeOption", typeOption);
				startProcessInstance(processMap);// 启动流程
				// -----
				resultMap.put("message", "1");
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 审批页器件申请单保存
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/savepartDataFromShenpiPage.do")
	public void savepartDataFromShenpiPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jsonData = request.getParameter("jsonData");
			Map<String, Object> dataMap = (Map<String, Object>) JSON.parse(jsonData);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			HrUser user = getUserInfo(request);
			if (user == null) {
				resultMap.put("message", "2");
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
				return;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(new Date());
			Map<String, String> imgMap = uploadPCImg(request);// 保存图片，获取路径
			dataMap.putAll(imgMap);
			
			dataMap.put("state", "1");// 检出
			dataMap.put("Modifier", user.getLoginName());
			dataMap.put("ModifyDate", dateString);
			// 修改前，从数据库查出该器件基本信息
			Map<String, Object> updateDataBefore = new HashMap<String, Object>();
			if (null != dataMap && StringUtils.isNotEmpty(dataMap.get("id").toString())) {
				updateDataBefore = partPrimaryAttributesService.selectPartDateById(dataMap).get(0);
			}
			if(!updateDataBefore.get("PartCode").equals(dataMap.get("PartCode"))){
				long l=iPartDataService.checkPartCode((String) dataMap.get("PartCode"));
				if(l!=0l){
					resultMap.put("message", "0");
					String jsonString = JSON.toJSONString(resultMap);
					outputMsg(response, jsonString);
					return;
				}
			}
			iPartDataService.updatePartData(dataMap);
			resultMap.put("message", "1");
			String jsonString = JSON.toJSONString(resultMap);
			outputMsg(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 启动流程实例
	public void startProcessInstance(Map<String, Object> processMap) {
		try {
			String processDefinitionId = processMap.get("processDefinitionId").toString();
			String id = processMap.get("processMainid").toString();
			String typeOption = null == processMap.get("typeOption") ? "" : processMap.get("typeOption").toString();
			String partId = processMap.get("businessId").toString();
			HrUser user = (HrUser) processMap.get("HrUser");
			long taskId = 0;
			if (null != processMap.get("id")) {
				taskId = (Long) processMap.get("id");
			}
			String lock = (String) processMap.get("lock");
			String processAssignees = (String) processMap.get("processAssignees");
			// 设置流程启动人
			processEngine.getIdentityService().setAuthenticatedUserId(user.getLoginName());
			// 启动流程
			ProcessInstance processInstance = processEngine.getRuntimeService()
					.startProcessInstanceById(processDefinitionId);
			WorlkflowMainEntity processEntity = new WorlkflowMainEntity();
			processEntity.setProcessInstanceId(processInstance.getProcessInstanceId());
			processEntity.setId(Long.valueOf(id));
			processEntity.setProcessState(ProjectConstants.PROCESS_STATE_SHENPIZHONG);
			workflowBaseService.updateWorkflowMain(processEntity);
			WorlkflowMainEntity processMain = workflowBaseService.selectWorkflowBaseServiceById(Long.valueOf(id));
			if (null != lock && !"".equals(lock)) {
				updateWorkflowTask(user, partId, processMain, processInstance, taskId, typeOption, processAssignees);
			} else {
				// 向任务表添加过程数据
				insertWorkflowTask(user, partId, processMain, processInstance, typeOption, processAssignees);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 新增审批任务
	public void insertWorkflowTask(HrUser user, String partId, WorlkflowMainEntity main,
			ProcessInstance processInstance, String typeOption, String processAssignees) {
		WorkflowTaskEntitiy taskEntity = new WorkflowTaskEntitiy();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		taskEntity.setProcessMainId(main.getId());
		taskEntity.setProcessAssignees("admin");
		// 流程发起时间
		taskEntity.setProcessTaskStarttime(sdf.format(new Date()));
		if (null != user && StringUtils.isNotEmpty(user.getLoginName())) {
			taskEntity.setProcessTaskPerson(user.getLoginName());
		} else {
			taskEntity.setProcessTaskPerson("admin");
		}
		taskEntity.setProcessTaskState(ProjectConstants.PROCESS_STATE_SHENPIZHONG);
		taskEntity.setCdefine2(processInstance.getProcessInstanceId());
		taskEntity.setCdefine3(processInstance.getProcessDefinitionId());
		taskEntity.setCdefine4(partId);// 器件申请主表ID
		workflowBaseService.insertWorkflowTaskEntitiy(taskEntity);
		// 流程启动后，获取下一个userTask并设置执行人
		List<Task> taskList = processEngine.getTaskService().createTaskQuery()
				.processInstanceId(processInstance.getProcessInstanceId()).list();
		long newTask = workflowBaseService.selectMaxId("WF_TASK_ENTITY");
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			TaskUser tu = new TaskUser();
			tu.setCreateTime(new Date());
			tu.setIsFinish("0");
			tu.setProcessInstId(task.getProcessInstanceId());
			tu.setTaskDefKey(task.getTaskDefinitionKey());
			tu.setUtType(typeOption);
			tu.setTaskId(newTask);
			tu.setActTaskId(task.getId());
			String[] users = processAssignees.split(",");
			for (String s : users) {
				tu.setUserLoginName(s);
				List<HrUser> ulist = userService.selectUserByName(s);
				tu.setUserName(ulist.get(0).getUserName());
				iTaskUserService.insertTaskUser(tu);
			}
		}
	}

	// 修改流程任务
	public void updateWorkflowTask(HrUser user, String partId, WorlkflowMainEntity main,
			ProcessInstance processInstance, long taskId, String typeOption, String processAssignees) {
		WorkflowTaskEntitiy taskEntity = workflowBaseService.selectWorkflowTaskServiceById(Long.valueOf(taskId));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		taskEntity.setProcessMainId(main.getId());
		taskEntity.setProcessAssignees("admin");
		// 流程发起时间
		taskEntity.setProcessTaskStarttime(sdf.format(new Date()));
		if (null != user && StringUtils.isNotEmpty(user.getLoginName())) {
			taskEntity.setProcessTaskPerson(user.getLoginName());
		} else {
			taskEntity.setProcessTaskPerson("admin");
		}
		taskEntity.setProcessTaskState(ProjectConstants.PROCESS_STATE_SHENPIZHONG);
		taskEntity.setCdefine2(processInstance.getProcessInstanceId());
		taskEntity.setCdefine3(processInstance.getProcessDefinitionId());
		workflowBaseService.updateProcessTask(taskEntity);
		// 流程启动后，获取下一个userTask并设置执行人
		List<Task> taskList = processEngine.getTaskService().createTaskQuery()
				.processInstanceId(processInstance.getProcessInstanceId()).list();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			TaskUser tu = new TaskUser();
			tu.setTaskId(taskId);
			iTaskUserService.deleteTaskUserByTaskid(tu);
			tu.setCreateTime(new Date());
			tu.setIsFinish("0");
			tu.setProcessInstId(task.getProcessInstanceId());
			tu.setTaskDefKey(task.getTaskDefinitionKey());
			tu.setUtType(typeOption);
			tu.setActTaskId(task.getId());
			String[] users = processAssignees.split(",");
			for (String s : users) {
				tu.setUserLoginName(s);
				List<HrUser> ulist = userService.selectUserByName(s);
				tu.setUserName(ulist.get(0).getUserName());
				iTaskUserService.insertTaskUser(tu);
			}
		}
	}

	// 编辑保存任务
	public void saveWorkflowTask(Map<String, Object> map) {
		HrUser user = (HrUser) map.get("user");
		long id = (Long) map.get("id");
		WorlkflowMainEntity main = (WorlkflowMainEntity) map.get("processMain");
		String type = (String) map.get("lock");

		WorkflowTaskEntitiy taskEntity = new WorkflowTaskEntitiy();
		if (!"1".equals(type)) {
			taskEntity = workflowBaseService.selectWorkflowTaskServiceById(id);
		}
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		taskEntity.setProcessMainId(main.getId());
		taskEntity.setProcessAssignees("admin");
		// 流程发起时间
		// taskEntity.setProcessTaskStarttime(sdf.format(new Date()));
		if (null != user && StringUtils.isNotEmpty(user.getLoginName())) {
			// taskEntity.setProcessTaskPerson(user.getLoginName());
		} else {
			// taskEntity.setProcessTaskPerson("admin");
		}
		taskEntity.setProcessTaskState(ProjectConstants.PROCESS_STATE_WEIQIDONG);
		// taskEntity.setCdefine2(processInstance.getProcessInstanceId());
		// taskEntity.setCdefine3(processInstance.getProcessDefinitionId());
		if ("1".equals(type)) {
			taskEntity.setCdefine4(String.valueOf(id));// 器件申请主表ID
			workflowBaseService.insertWorkflowTaskEntitiy(taskEntity);
		} else {
			workflowBaseService.updateProcessTask(taskEntity);
		}
	}

	// 上传图片
	public Map<String, String> uploadPCImg(HttpServletRequest request) throws FileNotFoundException, IOException {
		HrUser hrUser = getUserInfo(request);
		if (hrUser == null) {
			return null;
		}
		// 图片上传
		// 从配置文件读取存放路径
		Properties prop = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(
				Thread.currentThread().getContextClassLoader().getResource("param.properties").getPath()));
		prop.load(in); /// 加载属性列表
		String path = prop.getProperty("upload_path");
		in.close();
		String imgUrl = request.getSession().getServletContext().getRealPath("/") + "uploadImg";
		File file = new File(imgUrl);
		if (!file.exists()) {// 文件夹不存在时创建文件夹,项目路径下
			file.mkdirs();
		}
		File file1 = new File(path);
		if (!file1.exists()) {// 文件夹不存在时创建文件夹，配置文件中路径下
			file1.mkdirs();
		}
		String lock = request.getParameter("lock");
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
		Iterator<String> i = mr.getFileNames();
		Map<String, String> urlMap = new HashMap<String, String>();
		while (i.hasNext()) {
			FileImg fileImg = new FileImg();
			String name = i.next();
			MultipartFile f = mr.getFile(name);
			if (f.isEmpty()) {// 添加时不选图片时默认图片。修改时不选图片不改图片信息
				if (lock.equals("2")) {
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("imgName", "47abbfca91334243a8b73dc5964410af.PNG");
				List<FileImg> list = iPartClassService.selectImgByName(map);
				if (list.size() > 0) {
					urlMap.put(name.substring(0, name.length() - 1), list.get(0).getImgUrl());
					continue;
				}
				continue;
			}
			String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "."
					+ (f.getOriginalFilename().split("\\."))[1];
			InputStream is = f.getInputStream();
			FileOutputStream fos = new FileOutputStream(imgUrl + "/" + fileName);// 项目路径下
			FileOutputStream fs = new FileOutputStream(path + "/" + fileName);// 配置文件中路径下
			byte[] bytes = new byte[1024];
			int len;
			while ((len = is.read(bytes)) != -1) {
				fos.write(bytes, 0, len);
				fs.write(bytes, 0, len);
			}
			fos.flush();
			fs.flush();
			fos.close();
			fs.close();
			is.close();
			// 将图片信息保存到数据库
			fileImg.setImgSname(f.getOriginalFilename());
			fileImg.setImgName(fileName);
			fileImg.setCreateTime(new Date());
			fileImg.setCreateUser(hrUser.getUserName());
			fileImg.setImgUrl("uploadImg" + File.separator + fileName);
			iPartClassService.insertFileImg(fileImg);
			urlMap.put(name.substring(0, name.length() - 1), fileImg.getImgUrl());
		}
		return urlMap;
	}

	/**
	 * 获得流程设置展示图
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getPngForApply.do")
	public void getPngForApply(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> mapJson = new HashMap<String, Object>();
			String formType = request.getParameter("formType");
			String taskId = request.getParameter("taskId");
			List<WorlkflowMainEntity> mainList = null;
			WorlkflowMainEntity processMain = null;
			if (null == taskId || "".equals(taskId)) {
				processMain = new WorlkflowMainEntity();
				processMain.setCdefine3(formType);
				mainList = workflowBaseService.selectProcessMain(processMain);
			} else {
				WorkflowTaskEntitiy task = workflowBaseService.selectWorkflowTaskServiceById(Long.valueOf(taskId));
				if (null == task) {
					mapJson.put("pngName", "0");
					String msg = JSON.toJSONString(mapJson);
					outputJson(response, msg);
					return;
				}
				processMain = new WorlkflowMainEntity();
				processMain.setId(task.getProcessMainId());
				;
				mainList = workflowBaseService.selectProcessMain(processMain);
			}
			if (null == mainList || mainList.size() <= 0) {
				mapJson.put("pngName", "0");
				String msg = JSON.toJSONString(mapJson);
				outputJson(response, msg);
				return;
			}
			processMain = mainList.get(0);
			String deployId = processMain.getDeploymentId();
			// 获得图片信息
			ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()// 创建一个流程实例查询
					.deploymentId(deployId).orderByProcessDefinitionVersion().asc()// 按版本升序排序
					.singleResult();// 返回一个封装流程定义的集合
			mapJson.put("pngName", processDefinition.getDiagramResourceName());
			String msg = JSONObject.fromObject(mapJson).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 催办
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/pressDo.do")
	public void pressDo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String taskId = request.getParameter("taskId");
		String formType = request.getParameter("formType");
		try {
			HrUser user = this.getUserInfo(request);
			if (null == user) {
				map.put("user", null);
				map.put("msg", "请先登录！");
				String msg = JSON.toJSONString(map);
				outputJson(response, msg);
				return;
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if ("0".equals(formType)) {
					formType = "元器件申请";
				}
				String message = formType + " " + sdf.format(new Date()) + " " + user.getLoginName();
				map.put("user", user);
				map.put("taskId", taskId);
				map.put("msgContent", "流程：" + message + " 到达你处 请尽快办理！");
				String msg = JSON.toJSONString(map);
				outputJson(response, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 流程催办消息信息添加
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertpressMessage.do")
	public void insertpressMessage(HttpServletRequest request, HttpServletResponse response) {
		List<String> msgList = new ArrayList<String>();
		String contendMsg = request.getParameter("msgContent");
		String formType = request.getParameter("formType");
		String taskId = request.getParameter("taskId");
		HrUser user = this.getUserInfo(request);
		String loginName = "admin";
		String receivers = "";
		if (null != user) {
			loginName = user.getLoginName();
		}
		if ("0".equals(formType)) {
			formType = "元器件申请";
		}
		try {
			TaskUser param = new TaskUser();
			WorkflowTaskEntitiy taskEntity = workflowBaseService.selectWorkflowTaskServiceById(Long.valueOf(taskId));
			List<Task> tasks = processEngine.getTaskService().createTaskQuery()
					.processInstanceId(taskEntity.getCdefine2()).list();
			List<String> queryList = new ArrayList<String>();
			for (int x = 0; x < tasks.size(); x++) {
				queryList.add(tasks.get(x).getId());
			}
			param.setQueryList(queryList);
			List<TaskUser> taskList = iTaskUserService.selectTaskUserList(param);
			SysMessage sysMsg = new SysMessage();
			sysMsg.setMsgContent(contendMsg);
			sysMsg.setLaunchPerson(loginName);
			sysMsg.setLaunchTime(new Date());
			sysMsg.setReceiverPerson("");// ----------------
			sysMsg.setWhetherRead(false);
			sysMsg.setMsgTittle("流程催办：" + formType);
			sysMsg.setMsgType("审核信息");
			sysMsg.setMsgLevel("一般");
			sysMsg.setCdefine1("pages/workflowPage/cms-flow-daiBan.jsp");
			sysMsg.setCdefine3(null != taskList && taskList.size() > 0 ? taskList.get(0).getProcessInstId() : "");// 流程实例ID
			sysMessageService.insertMessage(sysMsg);
			Map<String, Object> msgId = sysMessageService.selectMaxId();
			for (int i = 0; i < taskList.size(); i++) {
				if ("0".equals(taskList.get(i).getIsFinish())) {
					SysMessageDetail detail = new SysMessageDetail();
					detail.setMsgMainId(Long.valueOf(msgId.get("IDS").toString()));
					detail.setReceiver(taskList.get(i).getUserLoginName());
					detail.setState(0);
					sysMessageService.insertMessageDetail(detail);
					Map<String, Object> mapSql = new HashMap<String, Object>();
					mapSql.put("receiverPerson", taskList.get(i).getUserLoginName());
					mapSql.put("whetherRead", "0");
					long count = sysMessageService.selectCountByState(mapSql);
					msgList.add("pressMessageCount" + "," + taskList.get(i).getUserLoginName() + "," + count);
				}
			}
			String msg = JSON.toJSONString(msgList);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 流程管理页面----作废
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteTask.do")
	public void deleteTask(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String lang = "zh";
		if (null != request.getSession().getAttribute("lang")) {
			lang = request.getSession().getAttribute("lang").toString();
		}
		map.put("taskId", request.getParameter("taskId"));
		map.put("businessId", request.getParameter("businessId"));
		map.put("processInstanceId", request.getParameter("processInstanceId"));
		map.put("formType", request.getParameter("formType"));
		try {
			WorkflowTaskEntitiy task = workflowBaseService
					.selectWorkflowTaskServiceById(Long.valueOf(request.getParameter("taskId")));
			if ("未启动".equals(task.getProcessTaskState())) {
				workflowBaseService.deleteProcessTask(task);
				String msg = "";
				if ("zh".equals(lang)) {
					msg = JSON.toJSONString("删除成功！");
				} else {
					msg = JSON.toJSONString("Delete the success！");
				}
				outputJson(response, msg);
				return;
			}
				workflowBaseService.deleteTask(map);
				String msg = "";
				if ("zh".equals(lang)) {
					msg = JSON.toJSONString("删除成功！");
				} else {
					msg = JSON.toJSONString("Delete the success！");
				}
				outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 流程任务查询
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/processStateQuery.do")
	public void processStateQuery(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String lang = "zh";
		if (null != request.getSession().getAttribute("lang")) {
			lang = request.getSession().getAttribute("lang").toString();
		}
		String taskId = request.getParameter("taskId");
		try {
			WorkflowTaskEntitiy task = workflowBaseService.selectWorkflowTaskServiceById(Long.valueOf(taskId));
			map.put("task", task);
			map.put("lang", lang);
			String msg = JSON.toJSONString(map);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询历史活动
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/findHisActivitiList.do")
	public void findHisActivitiList(HttpServletRequest request, HttpServletResponse response) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 获取userTask历史
		List<WorkflowTaskEntitiy> wfList = new ArrayList<WorkflowTaskEntitiy>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String processInstanceId = request.getParameter("processInstanceId");
		String taskId = request.getParameter("taskId");
		try {
			WorkflowTaskEntitiy task = workflowBaseService.selectWorkflowTaskServiceById(Long.valueOf(taskId));
			if ("未启动".equals(task.getProcessTaskState())) {
				String msg = JSON.toJSONString("noStart");
				outputJson(response, msg);
				return;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			List<HistoricActivityInstance> list = processEngine.getHistoryService()
					.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
					.orderByHistoricActivityInstanceStartTime().asc().list();
			for (int x = 0; x < list.size(); x++) {
				HistoricActivityInstance historicTaskInstance = list.get(x);
				// 开始节点
				if (historicTaskInstance.getActivityType().equals("startEvent")) {
					WorkflowTaskEntitiy wfte = new WorkflowTaskEntitiy();
					wfte.setTaskName(historicTaskInstance.getActivityName());// 任务名称
					wfte.setCreateTime(sdf.format(historicTaskInstance.getStartTime()));// 任务开始时间
					wfte.setEndTime(null == historicTaskInstance.getEndTime() ? ""
							: sdf.format(historicTaskInstance.getEndTime()));// 审批结束时间
					wfte.setProcessAssignees("");// 执行人
					wfte.setProcessAproveAdvice("");// 执行意见
					wfList.add(wfte);
				}
				if (!"userTask".equals(historicTaskInstance.getActivityType())) {
					continue;
				}
				TaskUser tu = new TaskUser();
				tu.setProcessInstId(historicTaskInstance.getProcessInstanceId());
				tu.setTaskDefKey(historicTaskInstance.getActivityId());
				tu.setIsFinish("1");
				tu.setIsOneself("1");
				tu.setActTaskId(historicTaskInstance.getTaskId());
				List<TaskUser> tuList = iTaskUserService.selectTaskUserList(tu);
				for (int j = 0; j < tuList.size(); j++) {
					TaskUser t = tuList.get(j);
					WorkflowTaskEntitiy wfte = new WorkflowTaskEntitiy();
					String isAgree = null != t.getIsAgree() && t.getIsAgree().equals("1") ? "同意:" : "不同意:";
					String comment = null == t.getComment() ? "" : t.getComment();
					wfte.setProcessAproveAdvice(isAgree + comment);// 审批意见
					wfte.setTaskName(historicTaskInstance.getActivityName());// 任务名称
					wfte.setCreateTime(sdf.format(historicTaskInstance.getStartTime()));// 任务开始时间
					wfte.setEndTime(null == historicTaskInstance.getEndTime() ? ""
							: sdf.format(historicTaskInstance.getEndTime()));// 审批结束时间

					List<HrUser> uList = userService.selectUserByName(t.getUserLoginName());
					if (uList != null && uList.size() > 0) {
						wfte.setProcessAssignees(uList.get(0).getUserName());// 执行人
					}
					wfList.add(wfte);
				}
				// 开始结束节点
				if (null == tuList || tuList.size() <= 0) {
					WorkflowTaskEntitiy wfte = new WorkflowTaskEntitiy();
					wfte.setTaskName(historicTaskInstance.getActivityName());// 任务名称
					wfte.setCreateTime(sdf.format(historicTaskInstance.getStartTime()));// 任务开始时间
					wfte.setEndTime(null == historicTaskInstance.getEndTime() ? ""
							: sdf.format(historicTaskInstance.getEndTime()));// 审批结束时间
					wfte.setProcessAssignees("");// 执行人
					wfte.setProcessAproveAdvice("");// 执行意见
					wfList.add(wfte);
				}
			}
			if (list.get(list.size() - 1).getActivityType().equals("endEvent")) {
				WorkflowTaskEntitiy wfte = new WorkflowTaskEntitiy();
				wfte.setTaskName(list.get(list.size() - 1).getActivityName());// 任务名称
				wfte.setCreateTime(sdf.format(list.get(list.size() - 1).getStartTime()));// 任务开始时间
				wfte.setEndTime(null == list.get(list.size() - 1).getEndTime() ? ""
						: sdf.format(list.get(list.size() - 1).getEndTime()));// 审批结束时间
				wfte.setProcessAssignees("");// 执行人
				wfte.setProcessAproveAdvice("");// 执行意见
				wfList.add(wfte);
			}
			resultMap.put("wfList", wfList);
			String msg = JSON.toJSONString(resultMap);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 报表下载
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportProcessInformation.do")
	public void exportProcessInformation(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String ids = request.getParameter("ids");
		List<Object> idList = JSON.parseArray(ids);
		map.put("idList", idList);
		try {
			Map<String, Object> resultMap = workflowBaseService.selectProcessInformationToExport(map);
			HSSFWorkbook wb = (new ExportExcel()).exportExcel(resultMap);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=\"" + new String("流程申请信息".getBytes("gb2312"), "ISO8859-1") + ".xls" + "\"");
			OutputStream ouputStream;
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveAssigent(Map<String, Object> map) {
		if (null == map.get("processAssignees") || "".equals(map.get("processAssignees").toString())) {
			return;
		}
		TaskUser tu = new TaskUser();
		tu.setTaskId(Long.valueOf(map.get("newTask").toString()));
		iTaskUserService.deleteTaskUserByTaskid(tu);
		tu.setCreateTime(new Date());
		tu.setIsFinish("0");
		tu.setProcessInstId("");
		tu.setTaskDefKey("");
		tu.setUtType(map.get("typeOption").toString());
		String[] users = map.get("processAssignees").toString().split(",");
		for (String s : users) {
			tu.setUserLoginName(s);
			iTaskUserService.insertTaskUser(tu);
		}
	}

	/**
	 * 元器件申请修改页面的partdata字段
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectApplyEditPageField.do")
	public void selectApplyEditPageField(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String partDataId = request.getParameter("id");// 元器件id
			String tId = request.getParameter("tId");// 流程任务表id
			if (StringUtils.isEmpty(partDataId)) {
				resultMap.put("message", "id为空");
				String msg = JSON.toJSONString(resultMap);
				outputJson(response, msg);
				return;
			}
			String sql = "select * from part_data where id=" + partDataId;
			Map<String, Object> partMap = partDataService.selectPartDataById(sql.toString(),null);
			if(partMap==null){
				resultMap.put("message", "数据有误");
				String msg = JSON.toJSONString(resultMap);
				outputJson(response, msg);
				return;
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFixedInsertField(paramMap);
			List<PartPrimaryAttributes> inList = partPrimaryAttributesService.selectApplyField(paramMap);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partType", partMap.get("Part_Type"));
			map.put("tempPartMark", partMap.get("TempPartMark"));
			Map<String, Object> m = partPrimaryAttributesService.selectProperiesByName(map);
			// 判断中英文
			String langType = (String) request.getSession().getAttribute("lang");
			if (StringUtils.isEmpty(langType)) {
				langType = "zh";
			}
			langType = langType.trim();
			for (PartPrimaryAttributes ppa : inList) {
				if (null != m && null != m.get(ppa.getFieldName()) && !"".equals(m.get(ppa.getFieldName()))) {
					ppa.setShowName(m.get(ppa.getFieldName()).toString());
				}
				if (langType.equals("en")) {
					ppa.setShowName(ppa.getEnglishName());
				}
			}
			if (langType.equals("en")) {
				for (PartPrimaryAttributes ppa : list) {
					ppa.setShowName(ppa.getEnglishName());
				}
			}
			TaskUser param = new TaskUser();
			if (!"0".equals(tId)) {
				WorkflowTaskEntitiy task = workflowBaseService.selectWorkflowTaskServiceById(Long.valueOf(tId));
				if (!"未启动".equals(task.getProcessTaskState())) {
					List<Task> tasks = processEngine.getTaskService().createTaskQuery()
							.processInstanceId(task.getCdefine2()).list();
					String TDK = "";
					for (int rx = 0; rx < tasks.size(); rx++) {
						param.setTaskDefKey(tasks.get(rx).getTaskDefinitionKey());
						TDK += tasks.get(rx).getTaskDefinitionKey() + ",";
					}
					if (StringUtils.isNotEmpty(TDK)) {
						resultMap.put("TDK", TDK.substring(0, TDK.length() - 1));
					}
				}
				param.setTaskId(Long.valueOf(tId));
				param.setIsFinish("0");
				List<TaskUser> taskList = iTaskUserService.selectTaskUserList(param);
				String typePerson = "";
				String assignePerson = "";
				for (int r = 0; r < taskList.size(); r++) {
					typePerson = taskList.get(r).getUtType();
					assignePerson += taskList.get(r).getUserLoginName() + ",";
				}
				if (StringUtils.isNotEmpty(assignePerson)) {
					assignePerson = assignePerson.substring(0, assignePerson.length() - 1);
				}
				resultMap.put("typePerson", typePerson.equals("") ? "or" : typePerson);
				resultMap.put("assignePerson", assignePerson);
			}
			resultMap.put("message", "成功！");
			resultMap.put("fixedInsertList", list);
			resultMap.put("insertList", inList);
			resultMap.put("partData", partMap);
			resultMap.put("lang", langType);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 流程抄送消息信息添加
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertCCMessage.do")
	public void insertCCMessage(HttpServletRequest request, HttpServletResponse response) {
		List<String> msgList = new ArrayList<String>();
		String processCC = request.getParameter("processCC");
		String receiverCC = request.getParameter("receiverCC");
		String[] receiverList = receiverCC.split(",");
		String processInstanceId = request.getParameter("processInstanceId");
		try {
			HrUser user = this.getUserInfo(request);
			String loginName = "admin";
			if (null != user) {
				loginName = user.getLoginName();
			}
			WorkflowTaskEntitiy wte = new WorkflowTaskEntitiy();
			wte.setCdefine2(processInstanceId);
			WorkflowTaskEntitiy res = iWorlkflowMainEntityService.getWteByProInsId(wte);
			WorlkflowMainEntity processMain = new WorlkflowMainEntity();
			processMain.setId(res.getProcessMainId());
			List<WorlkflowMainEntity> mainList = workflowBaseService.selectProcessMain(processMain);
			SysMessage sysMsg = new SysMessage();
			sysMsg.setMsgContent(processCC);
			sysMsg.setLaunchPerson(loginName);
			sysMsg.setLaunchTime(new Date());
			sysMsg.setReceiverPerson(receiverCC);
			sysMsg.setWhetherRead(false);
			sysMsg.setMsgTittle(mainList.get(0).getCdefine3().equals("0") ? "流程抄送：" + "元器件申请" : "");
			sysMsg.setMsgType("审核信息");
			sysMsg.setMsgLevel("一般");
			sysMsg.setCdefine1("pages/workflowPage/cms-flow-manage-look.jsp?taskId=" + res.getId() + "&ft="
					+ mainList.get(0).getCdefine3() + "&partId=" + res.getCdefine4() + "");
			sysMsg.setCdefine3(processInstanceId);// 流程实例ID存储
			sysMessageService.insertMessage(sysMsg);
			Map<String, Object> msgId = sysMessageService.selectMaxId();
			for (int x = 0; x < receiverList.length; x++) {
				SysMessageDetail detail = new SysMessageDetail();
				detail.setMsgMainId(Long.valueOf(msgId.get("IDS").toString()));
				detail.setReceiver(receiverList[x].toString());
				detail.setState(0);
				sysMessageService.insertMessageDetail(detail);
				Map<String, Object> mapSql = new HashMap<String, Object>();
				mapSql.put("receiverPerson", receiverList[x]);
				mapSql.put("whetherRead", "0");
				long count = sysMessageService.selectCountByState(mapSql);
				msgList.add("pressMessageCount" + "," + receiverList[x] + "," + count);
			}
			String msg = JSON.toJSONString(msgList);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 元器件首页提交审批
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/submitApproval.do")
	public void submitApproval(HttpServletRequest request, HttpServletResponse response) {
		HrUser user = this.getUserInfo(request);
		String lang = "zh";
		if (null != request.getSession().getAttribute("lang")) {
			lang = request.getSession().getAttribute("lang").toString();
		}
		Map<String, Object> businessMap = new HashMap<String, Object>();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		WorlkflowMainEntity processMain = new WorlkflowMainEntity();
		List<WorlkflowMainEntity> mainList = null;
		String formType = request.getParameter("formType");
		String partId = request.getParameter("partId");
		List<Object> partIdList = JSON.parseArray(partId);
		long newTask = 0;
		try {
			if (null == user) {
				jsonMap.put("message", "zh".equals(lang) ? "请先登录！" : "Please login. ");
				String msg = JSON.toJSONString(jsonMap);
				outputJson(response, msg);
			}
			processMain.setCdefine3(formType);
			mainList = workflowBaseService.selectProcessMain(processMain);
			if (null == mainList || mainList.size() <= 0) {
				jsonMap.put("message",
						"zh".equals(lang) ? "找不到流程模板，请联系管理员！" : "The process template can not be found. ");
				String msg = JSON.toJSONString(jsonMap);
				outputJson(response, msg);
				return;
			}
			processMain = mainList.get(0);
			if ("0".equals(formType)) {
				businessMap.put("user", user);
				businessMap.put("id", Long.valueOf(partIdList.get(0).toString()));// 器件表ID
				businessMap.put("processMain", processMain);
				businessMap.put("lock", "1");
				saveWorkflowTask(businessMap);
				Map<String, Object> AssigneesMap = new HashMap<String, Object>();
				newTask = workflowBaseService.selectMaxId("WF_TASK_ENTITY");
				AssigneesMap.put("processAssignees", "");
				AssigneesMap.put("typeOption", "or");
				AssigneesMap.put("newTask", newTask);
				saveAssigent(AssigneesMap);
			}
			jsonMap.put("taskId", newTask);
			String msg = JSON.toJSONString(jsonMap);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   /**
    * 器件首页--------批量提交审批
    * @param request
    * @param response
    */
	@RequestMapping(value = "/batchSubmitApproval.do")
	public void batchSubmitApproval(HttpServletRequest request, HttpServletResponse response) {
		HrUser user = this.getUserInfo(request);
		String approveType = request.getParameter("approveType");//审批关系
		String receiverPerson = request.getParameter("receiverPerson");//审批人
		String formType = request.getParameter("formType");//申请类型
		String ids = request.getParameter("ids");//器件ID
		List<Object> partList = JSON.parseArray(ids);//审批的元器件集合
		String lang = "zh";
		if (null != request.getSession().getAttribute("lang")) {
			lang = request.getSession().getAttribute("lang").toString();
		}
		Map<String, Object> processMap = new HashMap<String, Object>();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		WorlkflowMainEntity processMain = new WorlkflowMainEntity();
		List<WorlkflowMainEntity> mainList = null;
		try {
			if (null == user) {
				jsonMap.put("message", "zh".equals(lang) ? "请先登录！" : "Please login. ");
				String msg = JSON.toJSONString(jsonMap);
				outputJson(response, msg);
			}
			processMain.setCdefine3(formType);
			mainList = workflowBaseService.selectProcessMain(processMain);
			if (null == mainList || mainList.size() <= 0) {
				jsonMap.put("message",
						"zh".equals(lang) ? "找不到流程模板，请联系管理员！" : "The process template can not be found. ");
				String msg = JSON.toJSONString(jsonMap);
				outputJson(response, msg);
				return;
			}
			processMain = mainList.get(0);//流程模板获取
			if ("0".equals(formType)) {
				for (int x = 0; x < partList.size(); x++) {
					processMap.put("businessId", Long.valueOf(partList.get(x).toString()));
					processMap.put("processDefinitionId", processMain.getProcessDefinitionId());
					processMap.put("processMainid", processMain.getId());
					processMap.put("HrUser", user);
					processMap.put("processAssignees", receiverPerson);
					processMap.put("typeOption", approveType);
					startProcessInstance(processMap);// 启动流程
					Map<String, Object> partMap = new HashMap<String, Object>();
					partMap.put("id", Long.valueOf(partList.get(x).toString()));
					partMap.put("state", "审批中");
					this.iPartDataService.updatePartState(partMap);//对应的器件状态修改
				}
			}
			jsonMap.put("message", "zh".equals(lang) ? "操作成功！" : "Successful operation. ");
			String msg = JSON.toJSONString(jsonMap);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}