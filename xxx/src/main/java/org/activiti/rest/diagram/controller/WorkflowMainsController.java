package org.activiti.rest.diagram.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.NativeHistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.rest.diagram.pojo.ProcUser;
import org.activiti.rest.diagram.pojo.TaskUser;
import org.activiti.rest.diagram.pojo.WorkflowTaskEntitiy;
import org.activiti.rest.diagram.pojo.WorlkflowMainEntity;
import org.activiti.rest.diagram.services.ITaskUserService;
import org.activiti.rest.diagram.services.IWorkflowBaseService;
import org.activiti.rest.diagram.services.IWorlkflowMainEntityService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.cms_cloudy.component.service.IPartDataService;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.user.controller.UserController;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.UserService;
import com.cms_cloudy.util.ProjectConstants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/WorkflowMainsController")
public class WorkflowMainsController extends BaseController{
	@Autowired
	private IWorkflowBaseService workflowBaseService;
	@Autowired
	private ITaskUserService iTaskUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private IWorlkflowMainEntityService iWorlkflowMainEntityService;
	@Autowired
	private IPartDataService iPartDataService;
	//流程引擎初始化
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	private static final Logger logger = Logger.getLogger(UserController.class);
	Map<String,Object> mapJson = new HashMap<String,Object>();
	String msgJson="";
	@RequestMapping(value="/getDefXmlByDeployId.do")
	public void getDefXmlByDeployId(HttpServletRequest request,HttpServletResponse response){
	     String deployId = "225002";
//		 String  workFlowXml = workflowBaseDao.getDefXmlByDeployId(deployId);
//	     System.out.println(workFlowXml);
	}
	/**
	 * 流程设计界面查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectBasicDeploymentProcess.do")
	public void selectBasicDeploymentProcess(HttpServletRequest request,HttpServletResponse response){
		try {
			String pageNo=request.getParameter("pageNo");
			String searchMsg = request.getParameter("searchMsg");
			String pageSize="5";
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("pageNo", pageNo);
			paramMap.put("pageSize", pageSize);
			paramMap.put("searchMsg", searchMsg==null?"":searchMsg);
			Map<String,Object> resultMap = workflowBaseService.selectBasicDeploymentProcess(paramMap);
			if(null != resultMap && resultMap.size()>0){
				resultMap.put("pageNo", pageNo);
				msgJson = JSON.toJSONString(resultMap);
				outputJson(response,msgJson);
			}else{
				msgJson = JSONArray.fromObject(null).toString();
				outputJson(response,msgJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 启动流程实例
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="/startProcessInstance.do")
	public void startProcessInstance(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
		String processKey = request.getParameter("processKey");
		String id = request.getParameter("id");
//		.startProcessInstanceById("process:3:32513");
		ProcessInstance processInstance = processEngine.getRuntimeService()
                                                                   .startProcessInstanceByKey(processKey); //使用流程定义的Key，启动流程。key对应的是HelloWord.bpmn当中的ID属性.;使用key的方式启动流程，可以找到最新版本的流程，进行启动,
		WorlkflowMainEntity processEntity = new WorlkflowMainEntity();
		processEntity.setProcessInstanceId(processInstance.getProcessInstanceId());
		processEntity.setId(Long.valueOf(id));
		processEntity.setProcessState(ProjectConstants.PROCESS_STATE_SHENPIZHONG);
		workflowBaseService.updateWorkflowMain(processEntity);
		WorlkflowMainEntity processMain = workflowBaseService.selectWorkflowBaseServiceById(Long.valueOf(id));
		//向任务表添加过程数据
		insertWorkflowTask(request,response,processMain,processInstance);
		mapJson.put("result", "success");
		msgJson = JSONObject.fromObject(mapJson).toString();
		outputJson(response,msgJson);
	    }catch(Exception e){
	    	mapJson.put("result", "fail");
			msgJson = JSONObject.fromObject(mapJson).toString();
			outputJson(response,msgJson);
		}
	}
	/**
	 * 流程任务添加
	 * @param request
	 * @param main
	 * @param processInstance
	 */
	@RequestMapping(value="/insertWorkflowTask.do")
	public void insertWorkflowTask(HttpServletRequest request,HttpServletResponse response,WorlkflowMainEntity main,ProcessInstance processInstance){
		WorkflowTaskEntitiy taskEntity = new WorkflowTaskEntitiy();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    HrUser user = getUserInfo(request);
	    
	    taskEntity.setProcessMainId(main.getId());
		taskEntity.setProcessAssignees("admin");
		//流程发起时间
		taskEntity.setProcessTaskStarttime(sdf.format(new Date()));
		if(null != user && StringUtils.isNotEmpty(user.getLoginName())){
			taskEntity.setProcessTaskPerson(user.getLoginName());
	      }else{
	    	  taskEntity.setProcessTaskPerson("admin");
	      }
		taskEntity.setProcessTaskState(ProjectConstants.PROCESS_APROVESTATE_DAIBAN);
		taskEntity.setCdefine2(processInstance.getProcessInstanceId());
		taskEntity.setCdefine3(processInstance.getProcessDefinitionId());
		workflowBaseService.insertWorkflowTaskEntitiy(taskEntity);;
	}
	/**
	 * 流程监控页面数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectProcessHistory.do")
	public void selectProcessHistory(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String startTimeBegin=request.getParameter("startTimeBegin");
			String startTimeEnd=request.getParameter("startTimeEnd");
			String startUser=request.getParameter("startUser");
			String lastTimeBegin=request.getParameter("lastTimeBegin");
			String lastTimeEnd=request.getParameter("lastTimeEnd");
			String lastUser=request.getParameter("lastUser");
			String pageNo=request.getParameter("pageNo");
			String pageSize=request.getParameter("pageSize");
			Map<String,Object> resultMap=new HashMap<String, Object>();
			List<WorkflowTaskEntitiy> workFlowList = new ArrayList<WorkflowTaskEntitiy>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			HistoryService historyService = processEngine.getHistoryService();
			HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService
					.createHistoricProcessInstanceQuery();
			Calendar calendar = Calendar.getInstance();
		    
			if(StringUtils.isNotEmpty(startTimeBegin)){//启动时间
				
				historicProcessInstanceQuery=historicProcessInstanceQuery.startedAfter(sdf1.parse(startTimeBegin));
			}
			if(StringUtils.isNotEmpty(startTimeEnd)){//启动时间
				calendar.setTime(sdf1.parse(startTimeEnd));
				calendar.add(Calendar.DATE, 1);
				historicProcessInstanceQuery=historicProcessInstanceQuery.startedBefore(calendar.getTime());
			}
			if(StringUtils.isNotEmpty(startUser)){//启动人
				historicProcessInstanceQuery=historicProcessInstanceQuery.startedBy(startUser);
			}
			//历史的个人任务
			NativeHistoricTaskInstanceQuery nativeHistoricTaskInstanceQuery=historyService.createNativeHistoricTaskInstanceQuery();
			ManagementService managementService=processEngine.getManagementService();
			StringBuffer sf=new StringBuffer();
			sf.append("select his.* from ");
			sf.append(managementService.getTableName(HistoricTaskInstance.class));
			sf.append(" his where 1=1 ");
			if(StringUtils.isNotEmpty(lastTimeBegin)){//创建时间
				sf.append("and his.START_TIME_>='"+sdf.format(sdf1.parse(lastTimeBegin))+"' ");
			}
			if(StringUtils.isNotEmpty(lastTimeEnd)){//创建时间
				calendar.setTime(sdf1.parse(lastTimeEnd));
				calendar.add(Calendar.DATE, 1);
				sf.append("and his.START_TIME_<='"+sdf.format(calendar.getTime())+"' ");
			}
			if(StringUtils.isNotEmpty(lastUser)){//执行人
				TaskUser tu=new TaskUser();
				tu.setUserLoginName(lastUser);
				List<TaskUser> l=iTaskUserService.selectTaskUserList(tu);
				String s="(";
				String insId="(";
				for(TaskUser t:l){
					s+="'"+t.getTaskDefKey()+"',";
					insId+="'"+t.getProcessInstId()+"'";
				}
				s=s.substring(0,s.length()-1)+")";
				insId=insId.substring(0,insId.length()-1)+")";
				if(l.size()>0){
					sf.append(" and his.TASK_DEF_KEY_ in"+s);
					sf.append(" and his.PROC_INST_ID_ in"+insId);
				}else{
					sf.append(" and his.TASK_DEF_KEY_ ='bucunzai'");
					sf.append(" and his.PROC_INST_ID_ ='bucunzai'");
				}
			}
			// 所有流程实例
			long startTime=System.currentTimeMillis();
			List<HistoricProcessInstance> hList1 = historicProcessInstanceQuery.list();
			List<HistoricProcessInstance> hList=new ArrayList<HistoricProcessInstance>();
			if(hList1.size()<Integer.valueOf(pageSize)){
				hList=hList1;
			}else if(Integer.valueOf(pageNo)*Integer.valueOf(pageSize)>hList1.size()){
				hList=hList1.subList((Integer.valueOf(pageNo)-1)*Integer.valueOf(pageSize),hList1.size() );
			}else{
				hList=hList1.subList((Integer.valueOf(pageNo)-1)*Integer.valueOf(pageSize), Integer.valueOf(pageNo)*Integer.valueOf(pageSize));
			}
			for (int i = 0; i < hList.size(); i++) {
				WorkflowTaskEntitiy wte = new WorkflowTaskEntitiy();
				HistoricProcessInstance h = hList.get(i);
//				if(i==0){
//					sf.append(" and his.PROC_INST_ID_ =#{processInstanceId} ");
//					sf.append("order by his.ID_ desc");
//				}
//				List<HistoricTaskInstance> htiList = nativeHistoricTaskInstanceQuery.sql(sf.toString())
//						.parameter("processInstanceId", h.getId())
//						.list();
//				
//				if(htiList.size() == 0&&(StringUtils.isNotEmpty(lastUser)||StringUtils.isNotEmpty(lastTimeEnd)||StringUtils.isNotEmpty(lastTimeBegin))){
//					continue;//有条件时，查不到任务list时不需要把流程数据传到页面
//				}
				
				wte.setCdefine2(h.getId());// 流程实例id
				wte.setCdefine3(h.getProcessDefinitionId());// 流程定义id
				wte.setCreateTime(sdf.format(h.getStartTime()));// 流程开始时间
				wte.setEndTime(h.getEndTime() == null ? "" : sdf.format(h.getEndTime()));// 流程结束时间
				wte.setProcessState(h.getEndTime() == null ? "审批中" : "已审批");
				wte.setProcessCreatePerson(h.getStartUserId());// 流程启动人
				wte.setProcessKey(h.getName()==null?"":h.getName());//流程名称
//				for (int j = 0; j < htiList.size(); j++) {
//					HistoricTaskInstance hti = htiList.get(j);
//					if (hti.getEndTime() != null) {
//						wte.setLastTime(sdf.format(hti.getEndTime()));
//						TaskUser t=new TaskUser();
//						t.setProcessInstId(hti.getProcessInstanceId());
//						t.setTaskDefKey(hti.getTaskDefinitionKey());
//						List<TaskUser> l=iTaskUserService.selectTaskUserList(t);
//						if(l.size()>0){
//							wte.setLastUserName(l.get(0).getUserLoginName());
//						}else{
//							wte.setLastUserName("");
//						}
//					} else if (j == htiList.size() - 1) {
//						wte.setLastTime(sdf.format(h.getStartTime()));
//						wte.setLastUserName(h.getStartUserId());
//					}
//				}
//				if (htiList.size() == 0) {
//					wte.setLastTime(sdf.format(h.getStartTime()));
//					wte.setLastUserName(h.getStartUserId());
//				}
				
				//获取业务数据
				
				WorlkflowMainEntity wme=new WorlkflowMainEntity();
				wme.setProcessDefinitionId(h.getProcessDefinitionId());
				WorlkflowMainEntity l= iWorlkflowMainEntityService.getWfmByProdefId(wme);
				if(l!=null&&StringUtils.isNotEmpty(l.getCdefine3())){
					if(l.getCdefine3().equals("0")){//根据标题判断是哪种申请
						wte.setTitle("元器件申请");
						WorkflowTaskEntitiy wte1 = new WorkflowTaskEntitiy();
						wte1.setCdefine2(h.getId());
						WorkflowTaskEntitiy workTe = iWorlkflowMainEntityService.getWteByProInsId(wte1);
						if(workTe==null){
							continue;
						}
						Map<String,Object> map=iPartDataService.selectPartDataById("select * from part_data where id="+workTe.getCdefine4(),null);
						if(map==null){
							wte.setItem("");
						}else{
							wte.setItem(map.get("Part_Type").toString()+map.get("ITEM").toString());
						}
						
					}else{
						wte.setTitle("");
					}
				}
				workFlowList.add(wte);
			}
			
//			List<WorkflowTaskEntitiy> pageList=new ArrayList<WorkflowTaskEntitiy>();
//			if(workFlowList.size()<Integer.valueOf(pageSize)){
//				pageList=workFlowList;
//			}else if(Integer.valueOf(pageNo)*Integer.valueOf(pageSize)>workFlowList.size()){
//				pageList=workFlowList.subList((Integer.valueOf(pageNo)-1)*Integer.valueOf(pageSize),workFlowList.size() );
//			}else{
//				pageList=workFlowList.subList((Integer.valueOf(pageNo)-1)*Integer.valueOf(pageSize), Integer.valueOf(pageNo)*Integer.valueOf(pageSize));
//			}
			resultMap.put("cou", hList1.size());
			resultMap.put("wfList", workFlowList);
			msgJson = JSON.toJSONString(resultMap);
			long endTime=System.currentTimeMillis();
			System.out.println(endTime-startTime);
			outputJson(response, msgJson);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 流程监控的统计图
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/countTaskByUser.do")
	public void countTaskByUser(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> cList = workflowBaseService.countTaskByUser(paramMap);
			if (cList != null) {
				List<String> userList = new ArrayList<String>();
				List<Map<String, Object>> chartList1 = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> chartList2 = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> taskCount = (List<Map<String, Object>>) cList.get("taskCount");
				for (int i = 0; i < taskCount.size(); i++) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					Map<String, Object> map2 = new HashMap<String, Object>();
					userList.add((String) taskCount.get(i).get("name"));
					map1.put("name", (String) taskCount.get(i).get("name"));
					map1.put("value",taskCount.get(i).get("cou"));
					map2.put("name", (String) taskCount.get(i).get("name"));
					if(taskCount.get(i).get("num")==null){
						map2.put("value", 0);
					}else{
						map2.put("value", taskCount.get(i).get("num"));
					}
					chartList1.add(map1);
					chartList2.add(map2);
				}
				resultMap.put("chartList1", chartList1);
				resultMap.put("chartList2", chartList2);
				resultMap.put("userList", userList);
			}
			msgJson = JSON.toJSONString(resultMap);

			outputJson(response, msgJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化元器件申请审批页面 获取workFlowtaskId和partDataId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/initPartPage.do")
	public void initPartPage(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String processInstanceId = request.getParameter("processInstanceId");
			String taskId=request.getParameter("taskId");
			WorkflowTaskEntitiy wte = new WorkflowTaskEntitiy();
			wte.setCdefine2(processInstanceId);
			WorkflowTaskEntitiy res = iWorlkflowMainEntityService.getWteByProInsId(wte);
			Task nowTask=processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();//当前任务
			res.setCdefine5(nowTask.getTaskDefinitionKey());
			resultMap.put("wte", res);
			msgJson = JSON.toJSONString(resultMap);
			outputJson(response, msgJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化元器件申请审批页面 获取workFlowtaskId和partDataId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/initSpBtn.do")
	public void initSpBtn(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String processInstanceId = request.getParameter("processInstanceId");
			String taskId=request.getParameter("taskId");
			//获取所有userTask
			RepositoryService repositoryService= processEngine.getRepositoryService();
			HistoricProcessInstance hisP=processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(hisP.getProcessDefinitionId());
			List<ActivityImpl> activityList=processDefinitionEntity.getActivities();//所有的节点
			Task nowTask=processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();//当前任务
			if(nowTask==null){
				resultMap.put("message", "0");
				msgJson = JSON.toJSONString(resultMap);
				outputJson(response, msgJson);
			}
			//如果只有一条路线 不显示退回按钮
			for(ActivityImpl a:activityList){
				if(a.getId().equals(nowTask.getTaskDefinitionKey())){
					List<PvmTransition> l=a.getOutgoingTransitions();
					if(l==null||l.size()<2){
						resultMap.put("showDisagreeBtn", 0);
					}else{
						resultMap.put("showDisagreeBtn", 1);
					}
				}
			}
			
			msgJson = JSON.toJSONString(resultMap);
			outputJson(response, msgJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 流程待办/已办任务查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectProcessTask.do")
	public void selectProcessTask(HttpServletRequest request,HttpServletResponse response){
		try{
			String createTimeBegin = request.getParameter("createTimeBegin");
			String createTimeFinish=request.getParameter("createTimeFinish");
			String workFlowName=request.getParameter("workFlowName");
			String taskName=request.getParameter("taskName");
			String processCreatePerson=request.getParameter("processCreatePerson");
			String state=request.getParameter("state");//待办、已办标识
			String pageNo=request.getParameter("pageNo");//当前页
			String pageSize=request.getParameter("pageSize");//一页多少个
			if(StringUtils.isEmpty(state)){
				msgJson = JSONArray.fromObject(null).toString();
				outputJson(response,msgJson);
				return;
			}
			WorkflowTaskEntitiy taskEntity = new WorkflowTaskEntitiy();
			taskEntity.setProcessCreatePerson(processCreatePerson);
			HrUser user = getUserInfo(request);
			if(null != user){
				taskEntity.setProcessAssignees(user.getLoginName()+"");
			}else{
				taskEntity.setProcessAssignees("admin");
			}
			taskEntity.setWorkFlowName(workFlowName);
			taskEntity.setTaskName(taskName);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isNotEmpty(createTimeBegin)){
				taskEntity.setCreateTimeBegin(sdf.parse(createTimeBegin));
			}
			if(StringUtils.isNotEmpty(createTimeFinish)){
				taskEntity.setCreateTimeFinish(sdf.parse(createTimeFinish));
			}
			taskEntity.setPageSize(Integer.valueOf(pageSize));
			taskEntity.setPageNum(Integer.valueOf(pageNo));
			Map<String,Object> map=workflowBaseService.getProcessTask(taskEntity,state);
			msgJson = JSON.toJSONString(map);
			outputJson(response,msgJson);
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error("流程任务查询："+e);
		}
	}
	/**
	 * 流程待办任务数量查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectProcessTaskNum.do")
	public void selectProcessTaskNum(HttpServletRequest request,HttpServletResponse response){
		try {
			String msgJson = "";
			HrUser user = getUserInfo(request);
			if (user == null) {
				msgJson = JSONArray.fromObject(0).toString();
				outputJson(response, msgJson);
				return;
			}
			TaskUser tu=new TaskUser();
			tu.setUserLoginName(user.getLoginName());
			tu.setIsFinish("0");
			List<TaskUser> tList=iTaskUserService.selectTaskUserList(tu);
			Integer l = tList.size();
			msgJson = JSONArray.fromObject(l).toString();
			outputJson(response, msgJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 审批页的审批历史
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="getTaskHistory")
	public void getTaskHistory(HttpServletRequest request,HttpServletResponse response){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String msgJson = "";
			HrUser user = getUserInfo(request);
			if (user == null) {
				msgJson = JSONArray.fromObject(0).toString();
				outputJson(response, msgJson);
				return;
			}
			
			Map<String,Object> resultMap=new HashMap<String,Object>();
			String processInstanceId=request.getParameter("processInstanceId");
			HistoryService historyService=processEngine.getHistoryService();
			TaskService taskService = processEngine.getTaskService();
			
			//获取userTask历史
			List<WorkflowTaskEntitiy> wfList=new ArrayList<WorkflowTaskEntitiy>();
			List<HistoricTaskInstance> hisTaskList=historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
			for(int i=0;i<hisTaskList.size();i++){
				
				HistoricTaskInstance historicTaskInstance=hisTaskList.get(i);
				if(historicTaskInstance.getEndTime()==null){
					continue;
				}
				TaskUser tu=new TaskUser();
				tu.setProcessInstId(historicTaskInstance.getProcessInstanceId());
				tu.setTaskDefKey(historicTaskInstance.getTaskDefinitionKey());
				tu.setIsFinish("1");
				tu.setIsOneself("1");
				tu.setActTaskId(historicTaskInstance.getId());
				List<TaskUser> tuList=iTaskUserService.selectTaskUserList(tu);
				for(int j=0;j<tuList.size();j++){
					TaskUser t=tuList.get(j);
					WorkflowTaskEntitiy wfte=new WorkflowTaskEntitiy();
					String isAgree=t.getIsAgree().equals("0")?"不同意。":"同意。";
					String comment=t.getComment();
					wfte.setProcessAproveAdvice(isAgree+comment);//审批意见
					wfte.setTaskName(historicTaskInstance.getName());//任务名称
					wfte.setCreateTime(sdf.format(t.getCreateTime()));//任务开始时间
					wfte.setEndTime(sdf.format(t.getEndTime()));//审批结束时间
					
					List<HrUser> uList=userService.selectUserByName(t.getUserLoginName());
					if(uList!=null&&uList.size()>0){
						wfte.setProcessAssignees(uList.get(0).getUserName());//执行人
					}
					wfList.add(wfte);
				}
				
			}
			resultMap.put("wfList", wfList);
			msgJson = JSON.toJSONString(resultMap);
			outputJson(response, msgJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//流程审批页转办
	@RequestMapping(value = "zhuanbanProcessTask")
	public void zhuanbanProcessTask(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 任务表ID
			String id = request.getParameter("id");
			// 流程指派人
			String processAssignees = request.getParameter("processAssigns");
			// 流程主表ID
			String processInstanceId = request.getParameter("processInstanceId");
			Map<String, String> map = new HashMap<String, String>();
			// 转办任务
			if (StringUtils.isNotEmpty(processAssignees)) {
				HrUser user = getUserInfo(request);
				TaskUser taskUser=new TaskUser();
				taskUser.setProcessInstId(processInstanceId);
				taskUser.setTaskDefKey(processEngine.getTaskService().createTaskQuery().taskId(id).singleResult().getTaskDefinitionKey());
				taskUser.setUserLoginName(user.getLoginName());
				taskUser.setActTaskId(id);
				List<TaskUser> list=iTaskUserService.selectTaskUserList(taskUser);
				if(list!=null&&list.size()>0){
					iTaskUserService.deleteTaskUser(taskUser);//先删除再添加
					TaskUser t=list.get(0);
					String users[]=processAssignees.split(",");
					for(String s:users){
						//转办接受人在此节点有审批任务且未审批时：
						taskUser.setUserLoginName(s);
						taskUser.setIsFinish("0");
						List<TaskUser> alist=iTaskUserService.selectTaskUserList(taskUser);
						if(alist!=null&&alist.size()>0){
							continue;
						}
						t.setUserLoginName(s);
						iTaskUserService.insertTaskUser(t);
					}
					
				}
			}
			map.put("result", "success");
			String msg = JSON.toJSONString(map);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//流程审批
	@RequestMapping(value="agreeProcessTask")
	public void agreeProcessTask(HttpServletRequest request,HttpServletResponse response){
		try {
			// 任务表ID
			String id = request.getParameter("id");
			// 流程指派人
			String processAssignees = request.getParameter("processAssigns");
			// 流程指派组
			String processGroups = request.getParameter("processGroups");
			// 审批意见
			String aproveAdvice = request.getParameter("aproveAdvice");
			// 目标节点
			String nodeOption = request.getParameter("nodeOption");
			// 单个节点人员之间审批的关系 and or两个值
			String typeOption = request.getParameter("typeOption");
			// 跳转类型
			String jumpType = request.getParameter("jumpType");
			// 流程实例ID
			String processInstanceId = request.getParameter("processInstanceId");
			// 同意或者打回
			String state = request.getParameter("state");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "dissuccess");
			if (StringUtils.isEmpty(state)) {
				outputJson(response, JSON.toJSONString(map));
				return;
			}
			// 查询该任务ID
			Task task = processEngine.getTaskService().createTaskQuery().taskId(id).singleResult();
			// 获取当前登录人的TaskUser，多个执行人，且执行人之间的关系为and时，需要每个人都审批才能完成此任务。
			HrUser user = getUserInfo(request);
			TaskUser queryUser = new TaskUser();
			queryUser.setProcessInstId(processInstanceId);
			queryUser.setTaskDefKey(task.getTaskDefinitionKey());
			queryUser.setUserLoginName(user.getLoginName());
			queryUser.setIsFinish("0");
			List<TaskUser> tList = iTaskUserService.selectTaskUserList(queryUser);

			if (tList.size() > 0) {// 在taskUser表完成此人的个人任务
				TaskUser nowTaskUser = tList.get(0);
				nowTaskUser.setIsFinish("1");
				nowTaskUser.setIsOneself("1");
				nowTaskUser.setEndTime(new Date());
				nowTaskUser.setIsAgree(state.equals("agree")?"1":"0");
				nowTaskUser.setComment(aproveAdvice);
				iTaskUserService.updateTaskUser(nowTaskUser);
			}
			
			if (state.equals("agree")) {//同意时节点的人分and、or条件，不同意时判断流程图路线
				queryUser.setUserLoginName(null);// 根据taskKey和流程定义id查询节点执行人员
				tList = iTaskUserService.selectTaskUserList(queryUser);
				if (tList.size() > 0 && tList.get(0).getUtType().equals("and")) {
					if (tList.size() >= 1) {// 此任务有多个执行人且关系为and
						for (int i = 0; i < tList.size(); i++) {// 判断执行人是否都完成任务
							if (tList.get(i).getIsFinish().equals("0")) {// 存在未完成的执行人,不用完成此流程任务
								map.put("result", "success");
								map.put("taskId", id);
								String msg = JSON.toJSONString(map);
								outputJson(response, msg);
								return;
							}
						}
					}
				}
			}
			//自由跳转时，跳到流程图内任意审批节点
			if(null != jumpType && jumpType.equals("freeStyle")&&state.equals("agree")){
				String taskKey=nodeOption;
				
				RepositoryService repositoryService= processEngine.getRepositoryService();
				HistoryService historyService=processEngine.getHistoryService();
				//获取所有userTask
				BpmnModel model = repositoryService.getBpmnModel(historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId());
				if(model != null) {
				    Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();  
				    for(FlowElement e : flowElements) {
				    	if(e.getClass().toString().endsWith("UserTask")){//userTask
				    		if(e.getId().equals(taskKey)){
				    			List<Task> taskL=processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
				    			for(int i=0;i<taskL.size();i++){//有并行网关时
				    				Task t=taskL.get(i);
				    				if(t.getTaskDefinitionKey().equals(taskKey)){//设置执行人
				    					typeOption = saveTaskUser(processAssignees, typeOption, processInstanceId, t);
				    				}else{//完成节点任务
				    					completeUserTask(processInstanceId, state, t.getId());
				    				}
				    				if(i==taskL.size()-1){//循环完并行任务时，返回页面。
				    					map.put("result", "success");
				    					String msg = JSON.toJSONString(map);
				    					outputJson(response, msg);
				    					return;
				    				}
				    			}
				    		}else{
				    			List<Task> taskL=processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
				    			for(int i=0;i<taskL.size();i++){
				    				Task t=taskL.get(i);
				    				completeUserTask(processInstanceId, state, t.getId());
				    			}
				    		}
				    	}
				    }  
				}
			}
			
			//完成任务
			completeUserTask(processInstanceId, state, id);
			// 查看流程是否结束
			Date date = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult().getEndTime();
			if (date != null) {// 此流程已结束，获取sate更改业务数据状态
				if (state.equals("agree")) {// 同意时
					
				} else if (state.equals("disagree")) {// 不同意时
					
				}
				WorkflowTaskEntitiy taskEntity = new WorkflowTaskEntitiy();
				taskEntity.setCdefine2(processInstanceId);
				workflowBaseService.updateTaskEntity(taskEntity);
			}
			// 获取下一个任务并插入执行人
			List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId)
					.list();
			for (int i = 0; i < taskList.size(); i++) {
				Task t = taskList.get(i);
				//保存taskUser
				if(StringUtils.isNotEmpty(processAssignees)){
					typeOption = saveTaskUser(processAssignees, typeOption, processInstanceId, t);
				}
			}
			map.put("result", "success");
			map.put("taskId", id);
			String msg = JSON.toJSONString(map);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	public String saveTaskUser(String processAssignees, String typeOption, String processInstanceId, Task task1) {
		TaskUser querytu = new TaskUser();
		Map<String, Object> map = new HashMap<String, Object>();
		querytu.setTaskDefKey(task1.getTaskDefinitionKey());
		querytu.setProcessInstId(processInstanceId);
		querytu.setActTaskId(task1.getId());
		if(StringUtils.isEmpty(processAssignees)){
			return typeOption;
		}
		String[] users = processAssignees.split(",");
		for (String s : users) {
			querytu.setUserLoginName(s);
			List<TaskUser> l = iTaskUserService.selectTaskUserList(querytu);
			if (l == null || l.size() == 0) {
				TaskUser insertTu = new TaskUser();
				WorkflowTaskEntitiy taskEntity = new WorkflowTaskEntitiy();
				taskEntity.setCdefine2(processInstanceId);
				map.put("task", taskEntity);
				map.put("pageNo", 1);
				map.put("pageSize", 5);
				List<Map<String,Object>> taskList = (List<Map<String, Object>>) this.workflowBaseService.selectProcessTaskByPage(map).get("list");
				insertTu.setCreateTime(new Date());
				insertTu.setIsFinish("0");
				insertTu.setProcessInstId(processInstanceId);
				insertTu.setTaskDefKey(task1.getTaskDefinitionKey());
				insertTu.setUserLoginName(s);
				insertTu.setUtType(StringUtils.isEmpty(typeOption) ? "or" : typeOption);
				insertTu.setTaskId(Long.valueOf(taskList.get(0).get("id").toString()));
				insertTu.setActTaskId(task1.getId());
				iTaskUserService.insertTaskUser(insertTu);
			} else {// 多个选择下个节点执行人关系时保持一致
				typeOption = l.get(0).getUtType();
			}
		}
		return typeOption;
	}
	public void completeUserTask(String processInstanceId, String state, String taskId) {
		Map<String, Object> vMap = new HashMap<String, Object>();
		vMap.put("message", state);
		// 完成当前任务
		processEngine.getTaskService().complete(taskId, vMap);
		if(!state.equals("agree")){//不同意时获取下一个userTask，看是否是已执行的userTask
			List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
			List<HistoricTaskInstance> hList=processEngine.getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
			for(int i=0;i<taskList.size();i++){
				Task task=taskList.get(i);
				for(int j=0;j<hList.size();j++){
					if(task.getTaskDefinitionKey().equals(hList.get(j).getTaskDefinitionKey())){
						TaskUser tk=new TaskUser();
						tk.setActTaskId(hList.get(j).getId());
						List<TaskUser> tList=iTaskUserService.selectTaskUserList(tk);
						for(int k=0;k<tList.size();k++){
							TaskUser t=tList.get(k);
							TaskUser newt=new TaskUser();
							newt.setIsFinish("0");
							newt.setCreateTime(new Date());
							newt.setProcessInstId(task.getProcessInstanceId());
							newt.setTaskDefKey(task.getTaskDefinitionKey());
							newt.setTaskId(t.getTaskId());
							newt.setUserLoginName(t.getUserLoginName());
							newt.setUserName(t.getUserName());
							newt.setUtType(t.getUtType());
							newt.setActTaskId(task.getId());
							iTaskUserService.insertTaskUser(newt);
						}
						break;
					}
				}
			}
		}
		//删除已分配未完成任务的人
		TaskUser taskU = new TaskUser();
		taskU.setActTaskId(taskId);
		taskU.setIsFinish("0");
		List<TaskUser> tul = iTaskUserService.selectTaskUserList(taskU);
		for (int l = 0; l < tul.size(); l++) {
			TaskUser t=tul.get(l);
			t.setIsFinish("1");
			iTaskUserService.updateTaskUser(t);
		}
	}
	/**完成该节点任务**/
	/**
     * 完成的任务
     * @param taskId
     */
    @RequestMapping(value="/completeMyProcessTask.do")
    public void completeMyProcessTask(HttpServletRequest request,HttpServletResponse response){
//    	String id = request.getParameter("taskId");
//    	//String processInstanceId = request.getParameter("processInstanceId");
//    	try {
//    		//WorkflowTaskEntitiy detail = workflowBaseService.selectWorkflowTaskServiceById(Long.valueOf(detailId));
//    	//任务ID
//    	Map<String, Object> paramMap = new HashMap<String, Object>();
//    	//设置流程变量day=3
//    	paramMap.put("input", "1");
//    	processEngine.getTaskService().complete(id);
//    	/**判断流程是否结束，查询正在执行的对象类**/
//		//ProcessInstance rpi = processEngine.getRuntimeService()
////				                                                    .createProcessInstanceQuery()
////				                                                    .processInstanceId(processInstanceId)
////				                                                    .singleResult();
//		//如果该流程实例为空则该流程已经结束,更新本地表状态
////		if(null == rpi){
////			main.setProcessState(ProjectConstants.PROCESS_STATE_WANCHENG);
////			workflowBaseService.updateWorkflowMainToState(main);
//			//detail.setProcessTaskState(ProjectConstants.PROCESS_APROVESTATE_YIBAN);
//			//workflowBaseService.updateWorkflowTaskEntitiyToState(detail);
////		}
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("result", "success");
//		String msg = JSON.toJSONString(map);
//		outputJson(response,msg);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }
    
    /**
	 * 流程已办任务查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectDoneProcessTask.do")
	public void selectDoneProcessTask(HttpServletRequest request,HttpServletResponse response){
		WorkflowTaskEntitiy taskEntity = new WorkflowTaskEntitiy();
		try{
			HrUser user = getUserInfo(request);
			if(null != user){
				taskEntity.setProcessAssignees(user.getLoginName()+"");
			}else{
				taskEntity.setProcessAssignees("admin");
			}
		taskEntity.setProcessTaskState(ProjectConstants.PROCESS_APROVESTATE_YIBAN);
		List<WorkflowTaskEntitiy> taskList = workflowBaseService.selectWorkflowTaskEntitiyList(taskEntity);
		if(null != taskList && taskList.size()>0){
	    	msgJson = JSONArray.fromObject(taskList).toString();
			outputJson(response,msgJson);
	    }else{
	    	msgJson = JSONArray.fromObject(null).toString();
			outputJson(response,msgJson);
	    }
        }catch(Exception e){
        	logger.error("流程任务查询："+e);
		}
	}
    /**
     * 获得流程设置展示图
     * @param request
     * @param response
     */
    @RequestMapping(value="/getPngForSetting.do")
    public void getPngForSetting(HttpServletRequest request,HttpServletResponse response){
    	try {
    	String processDefinitionId = request.getParameter("processDefinitionId");
    	//记录有多少个用户任务
    	//获得图片信息
    	ProcessDefinition processDefinition = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()//创建一个流程实例查询
                .processDefinitionId(processDefinitionId)
                .orderByProcessDefinitionVersion().asc()//按版本升序排序
                .singleResult();//返回一个封装流程定义的集合
    	BpmnModel models = processEngine.getRepositoryService().getBpmnModel(processDefinition.getId());
    	List<Map<String,Object>> utList=new ArrayList<Map<String,Object>>();
    	if(models != null) {
    	    Collection<FlowElement> flowElements = models.getMainProcess().getFlowElements();  
    	    for(FlowElement e : flowElements) {
    	    	if(e.getClass().toString().equals("class org.activiti.bpmn.model.UserTask")){
    	    		Map<String,Object> map=new HashMap<String,Object>();
    	    		map.put("id", e.getId());
    	    		map.put("name", e.getName());
    	    		//查询默认人员
    	    		ProcUser pu=new ProcUser();
    	    		pu.setTaskDefKey(e.getId());
    	    		pu.setProcDefId(processDefinitionId);
    	    		List<ProcUser> pList=iTaskUserService.selectProcUserList(pu);
    	    		map.put("pList",pList);
    	    		utList.add(map);
    	    	}
    	    } 
    	}
    	mapJson.put("pngName", processDefinition.getDiagramResourceName());
    	mapJson.put("utList", utList);
    	String msg = JSONObject.fromObject(mapJson).toString();
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 保存流程配置人员信息
     * @param request
     * @param response
     */
    @RequestMapping(value="/saveProcUser.do")
    public void saveProcUser(HttpServletRequest request,HttpServletResponse response){
    	try {
    	String procUserArr = request.getParameter("procUserArr");
    	List<ProcUser> pList=JSON.parseArray(procUserArr, ProcUser.class);
    	for(int i=0;i<pList.size();i++){
    		iTaskUserService.deleteProcUser(pList.get(i));
    		iTaskUserService.insertProcUser(pList.get(i));
    	}
    	mapJson.put("message", "保存成功！");
    	String msg = JSONObject.fromObject(mapJson).toString();
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
//    String attrName = req.getParameter("attrName");
//    String pid = req.getParameter("pid");
//    JSONArray arr = new JSONArray();
//    if(!"".equals(pid)){
//    	Project project = projectService.getProjectById(Integer.valueOf(pid));
//    	 List list = this.projectService.getAttrValueLook(attrName,project);
//    	    String msg = "";
//    	    if ((list != null) || (!list.isEmpty()))
//    	    {
//    	      for (int i = 0; i < list.size(); i++)
//    	      {
//    	        JSONObject obj = new JSONObject();
//    	        obj.put("text", ((Map)list.get(i)).get("F_ATTR_VALUE"));
//    	        obj.put("value", ((Map)list.get(i)).get("F_ID"));
//    	        obj.put("isDefault", "1");
//    	        arr.add(obj);
//    	      }
//    	      msg = arr.toString();
//    	    }else{
//    	      msg = arr.fromObject(null).toString();
//    	    }
//
//    	    outputJson(res, msg);
//    }
	/**
     * 删除流程实例以及对应业务数据
     * @param request
     * @param response
     */
    public void deleteProcess(HttpServletRequest request,HttpServletResponse response){
    	  String processInstanceId = "201";  
          processEngine.getRuntimeService().deleteProcessInstance(processInstanceId, "不准逃课！");  
          String deploymentId = "1";  
          // 第二个参数代表级联操作  
          processEngine.getRepositoryService().deleteDeployment(deploymentId, true);  
          // 删除所有相关的activiti信息  
    }
    /**
	 * 流程管理界面初始化
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectProcessManager.do")
	public void selectProcessManager(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String pageNo = request.getParameter("pageNo");
		String processName = request.getParameter("processName");
		String processState = request.getParameter("processState");
		String processTaskPerson = request.getParameter("processTaskPerson");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String item = request.getParameter("item");
		String formTypeFormManage = request.getParameter("formTypeFormManage");
		String pageSize = "5";
		WorkflowTaskEntitiy taskEntity = new WorkflowTaskEntitiy();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			HrUser user = getUserInfo(request);
			if(null != user){
				//taskEntity.setProcessAssignees(user.getLoginName()+"");
			}else{
				//taskEntity.setProcessAssignees("admin");
			}
			//taskEntity.setProcessTaskState(ProjectConstants.PROCESS_APROVESTATE_DAIBAN);
			taskEntity.setProcessName(processName);
			taskEntity.setProcessTaskState(processState);
			taskEntity.setProcessTaskPerson(processTaskPerson);
			taskEntity.setItem(item);
			taskEntity.setProcesCategory(formTypeFormManage);
			if(null != startTime && !"".equals(startTime)){
				taskEntity.setCreateTimeBegin(sdf.parse(startTime));
			}
            if(null != endTime && !"".equals(endTime)){
    			taskEntity.setCreateTimeFinish(sdf.parse(endTime));
			}
		map.put("task", taskEntity);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		Map<String,Object> mapJson = workflowBaseService.selectProcessTaskByPage(map);
		if(null != mapJson){
			mapJson.put("pageNo", pageNo);
	    	String msgJson = JSONObject.fromObject(mapJson).toString();
			outputJson(response,msgJson);
	    }else{
	    	String msgJson = JSONObject.fromObject(null).toString();
			outputJson(response,msgJson);
	    }
        }catch(Exception e){
        	logger.error("流程任务查询："+e);
		}
	}
	/**
	 * 流程表单绑定流程模版 type=0:元器件申请
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/settingFormType.do")
	public void settingFormType(HttpServletRequest request,HttpServletResponse response){
		String ids= request.getParameter("ids");
		String type= request.getParameter("type");
		List<Object> idList = JSON.parseArray(ids);
		Set<String> set = new HashSet<String>();  
		try {
		for(int s=0;s<idList.size();s++){
			WorlkflowMainEntity main = new WorlkflowMainEntity();
			main.setId(Long.valueOf(idList.get(s).toString()));
			List<WorlkflowMainEntity> list = workflowBaseService.selectProcessMain(main);
			if(null != list){
				set.add(list.get(0).getProcessKey());
			}
		}
		if(set.size() != 1){
			String msg = JSON.toJSONString("所选数据流程KEY必须相同！");
			outputJson(response,msg);
		}else{
			for(int x=0;x<idList.size();x++){
				WorlkflowMainEntity main = new WorlkflowMainEntity();
				main.setId(Long.valueOf(idList.get(x).toString()));
				List<WorlkflowMainEntity> list = workflowBaseService.selectProcessMain(main);
				WorlkflowMainEntity processMain = list.get(0);
				processMain.setCdefine3(type);
				workflowBaseService.updateProcessMain(processMain);
			}
			String msg = JSON.toJSONString("操作成功！");
			outputJson(response,msg);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除流程模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteProcessChart.do")
	public void deleteProcessChart(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map.put("id", request.getParameter("id"));
			map.put("formType", request.getParameter("formType"));
			map.put("deploymentId", request.getParameter("deploymentId"));	
			ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery() 
                    .deploymentId( request.getParameter("deploymentId"))
                    .singleResult(); 
             String path =request.getSession().getServletContext().getRealPath("")+"\\"+"workflowImg"+"\\"+processDefinition.getDiagramResourceName();
             File file = new File(path);
             if(file.exists()){
            	 file.delete();
             }
			workflowBaseService.deleteProcessTemplate(map);
			 String msg = JSON.toJSONString("删除成功！");
		     outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 流程设计-清除数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/cleanProcessData.do")
	public void cleanProcessData(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map.put("id", request.getParameter("id"));
			map.put("formType", request.getParameter("formType"));
			workflowBaseService.cleanProcessData(map);
			String msg = JSON.toJSONString("删除成功！");
			outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 点击审批页同意，弹窗的构造
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/createAgreeDia.do")
	public void createAgreeDia(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> resultMap=new HashMap<String,Object>();
			String msg="";
			String processInstanceId=request.getParameter("processInstanceId");
			String taskId=request.getParameter("taskId");
			if(StringUtils.isEmpty(processInstanceId)||StringUtils.isEmpty(taskId)){
				resultMap.put("message", "dissuccess");
				msg = JSON.toJSONString(resultMap);
				outputJson(response,msg);
				return;
			}
			HrUser user=getUserInfo(request);
			if(user==null){
				outputJson(response,msg);
				return;
			}
			RepositoryService repositoryService= processEngine.getRepositoryService();
			HistoryService historyService=processEngine.getHistoryService();
			String processDefinitionId=historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();
			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);  
			//获取已完成的userTask
			List<HistoricTaskInstance> hisList=historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
			
			//获取所有userTask
			List<ActivityImpl> activityList=processDefinitionEntity.getActivities();//所有的节点
			Task nowTask=processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();//当前任务
			List<Map<String,Object>> unbeginList=new ArrayList<Map<String,Object>>();//未开始的任务
			BpmnModel model = repositoryService.getBpmnModel(historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId());
			//判断当前节点是否为最后一个userTask
			int y=0;
			String taskKey="";
			for(ActivityImpl a:activityList){
				if(a.getId().equals(nowTask.getTaskDefinitionKey())){
					List<PvmTransition> l=a.getOutgoingTransitions();
					for(PvmTransition pvm:l){
						if(pvm.getProperty("conditionText")!=null&&!(pvm.getProperty("conditionText").toString().replace(" ", "").equals("${message=='agree'}"))){//只看同意时的路线
							continue;
						}
						PvmActivity  pvmActivity= pvm.getDestination();//下一个节点
						List<PvmTransition> nextOut=pvmActivity.getOutgoingTransitions();
						if(nextOut.size()==0){//最后一个审批节点
							y=1;
						}else if(!pvmActivity.getProperty("type").equals("userTask")){//节点为网关时
							for(PvmTransition nextPvm:nextOut){//暂时网关之后只有一条线 一个UserTask
								PvmActivity  nextPvmActivity= nextPvm.getDestination();
								taskKey=nextPvmActivity.getId();
								break;
							}
						}else if(pvmActivity.getProperty("type").equals("userTask")){
							taskKey=pvmActivity.getId();
						}

					}
				}else if(a.getProperty("type").equals("userTask")){
					for(int i=0;i<hisList.size();i++){//未开始的UserTask
		    			if(hisList.get(i).getTaskDefinitionKey().equals(a.getId())){
		    				break;
		    			}
		    			if(i==hisList.size()-1){
		    				Map<String,Object> map=new HashMap<String,Object>();
		    				map.put("taskDefinitionKey", a.getId());
		    				if(model!=null){
		    					map.put("name", model.getMainProcess().getFlowElement(a.getId()).getName());//userTask Name
		    				}
		    				unbeginList.add(map);
		    			}
		    		}
				}
				
			}
			//获取配置好的执行人，没有就在页面可选择
			List<ProcUser> tuList=null;
			if(y==1){//当前任务节点为最后一个userTask
				tuList=new ArrayList<ProcUser>();
				resultMap.put("isLast", "1");
			}else{//查找下一个节点执行人
				ProcUser pu=new ProcUser();
				pu.setTaskDefKey(taskKey);
				pu.setProcDefId(processDefinitionId);
				tuList=iTaskUserService.selectProcUserList(pu);
				resultMap.put("isLast", "0");
			}
			//获取当前任务的执行人的关系
			TaskUser tUser=new TaskUser();
			tUser.setProcessInstId(processInstanceId);
			tUser.setTaskDefKey(nowTask.getTaskDefinitionKey());
			tUser.setUserLoginName(user.getLoginName());
			TaskUser resUser=iTaskUserService.selectTaskUserList(tUser).get(0);
			resultMap.put("message", "success");
			resultMap.put("unbeginList", unbeginList);
			resultMap.put("tuList", tuList);
			resultMap.put("type", resUser.getUtType());
			msg = JSON.toJSONString(resultMap);
			outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 已完成任务详情
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getyibanProDetail.do")
	public void getyibanProDetail(HttpServletRequest request,HttpServletResponse response){
		try {
			String taskId = request.getParameter("taskId");// 任务id
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			HistoricTaskInstance hti = processEngine.getHistoryService().createHistoricTaskInstanceQuery()
					.taskId(taskId).singleResult();
			HistoricProcessInstance h = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
					.processInstanceId(hti.getProcessInstanceId()).singleResult();
			WorkflowTaskEntitiy wte = new WorkflowTaskEntitiy();
			wte.setProcessNodeTaskname(hti.getName());
			wte.setProcessTaskState("已完成");// 已完成
			wte.setProcessTaskPerson(h.getStartUserId());
			wte.setProcessTaskStarttime(sdf.format(hti.getStartTime()));
			if(hti.getEndTime()==null){
				wte.setProcessTaskExpirtime("");
			}else{
				wte.setProcessTaskExpirtime(sdf.format(hti.getEndTime()));
			}
			List<Comment> cList = processEngine.getTaskService().getTaskComments(hti.getId());// 审批意见
			if (cList.size() > 0) {
				wte.setProcessAproveAdvice(cList.get(0).getFullMessage());
			}
			wte.setCdefine1(taskId);
			wte.setCdefine2(hti.getProcessInstanceId());
			wte.setCdefine3(hti.getProcessDefinitionId());
			ProcessDefinition pd = processEngine.getRepositoryService().createProcessDefinitionQuery()
					.processDefinitionId(hti.getProcessDefinitionId()).singleResult();
			wte.setProcessKey(pd.getKey());
			wte.setProcessName(pd.getName());
			wte.setTitle("");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("wte", wte);
			String msg = JSON.toJSONString(resultMap);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 已办任务撤回
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/retractTask.do")
	public void retractTask(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String processInstanceId = request.getParameter("processInstanceId");// 流程定义Id
			String taskDefinitionKey = request.getParameter("taskDefinitionKey");// 流程定义的taskKey
			String taskId=request.getParameter("taskId");//流程任务id
			TaskUser tu = new TaskUser();
			tu.setProcessInstId(processInstanceId);
			tu.setTaskDefKey(taskDefinitionKey);
			tu.setActTaskId(taskId);
			HrUser user = getUserInfo(request);
			if (user == null) {
				resultMap.put("message", "0");
				String msg = JSON.toJSONString(resultMap);
				outputJson(response, msg);
			}
			tu.setUserLoginName(user.getLoginName());
			List<TaskUser> l=iTaskUserService.selectTaskUserList(tu);
			if(l!=null){
				TaskUser t=l.get(0);
				t.setIsFinish("0");
				iTaskUserService.updateTaskUser(t);
			}
			resultMap.put("message", "1");
			String msg = JSON.toJSONString(resultMap);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}