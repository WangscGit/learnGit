package org.activiti.rest.diagram.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.rest.diagram.pojo.ProcUser;
import org.activiti.rest.diagram.pojo.ProcessConfigure;
import org.activiti.rest.diagram.pojo.WorkflowTaskEntitiy;
import org.activiti.rest.diagram.pojo.WorlkflowMainEntity;
import org.activiti.rest.diagram.services.IProcessConfigureService;
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


@Controller
@RequestMapping(value="/ProcessConfigureController")
public class ProcessConfigureController extends BaseController{
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
	@Autowired
	private IProcessConfigureService iProcessConfigureService;
	//流程引擎初始化
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	/**
	 * 初始化元器件申请流程配置弹窗
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/initConfigureDia.do")
	public void initConfigureDia(HttpServletRequest request, HttpServletResponse response) {
		try {
			String processDefId = request.getParameter("processDefId");
//			String processType = request.getParameter("processType");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			HrUser user = getUserInfo(request);
			if (user == null||StringUtils.isEmpty(processDefId)) {
				resultMap.put("message", "2");
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			//获取所有userTask
			List<Map<String,String>> userTaskList=new ArrayList<Map<String,String>>();
			RepositoryService repositoryService= processEngine.getRepositoryService();
			BpmnModel model = repositoryService.getBpmnModel(processDefId);
			if(model != null) {
			    Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();  
			    for(FlowElement e : flowElements) {
			    	if(e.getClass().toString().endsWith("UserTask")){//userTask
			    		Map<String,String> map=new HashMap<String,String>();
			    		map.put("key", e.getId());
			    		map.put("name", e.getName());
			    		userTaskList.add(map);
			    	}
			    }  
			}
			//添加一个开始节点用于申请页面配置
			Map<String,String> map=new HashMap<String,String>();
			map.put("key", "startEvent");
    		map.put("name", "元器件申请页");
    		userTaskList.add(0, map);
			resultMap.put("message", "1");
			resultMap.put("userTaskList", userTaskList);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取某个节点的配置信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getProcessConfigure.do")
	public void getProcessConfigure(HttpServletRequest request, HttpServletResponse response) {
		try {
			String taskKey = request.getParameter("taskKey");//审批节点key
			String processDefId = request.getParameter("processDefId");
			String tId=request.getParameter("tId");//WF_TASK_ENTITY id
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			HrUser user = getUserInfo(request);
			if (user == null||StringUtils.isEmpty(taskKey)) {
				resultMap.put("message", "2");
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			ProcessConfigure pc=new ProcessConfigure();
			pc.setTaskKey(taskKey);
			if(StringUtils.isEmpty(processDefId)){//此时获取最新的流程定义
				if(StringUtils.isEmpty(tId)){//添加页面
					List<ProcessDefinition> list=processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey("partProcess").orderByProcessDefinitionVersion().desc().list();
					if(list.size()==0){
						return;
					}
					pc.setProcessDefId(list.get(0).getId());
					//添加页面时，还要获取配置好的下一步执行人
					BpmnModel models = processEngine.getRepositoryService().getBpmnModel(list.get(0).getId());
					if(models != null) {
			    	    Collection<FlowElement> flowElements = models.getMainProcess().getFlowElements();  
			    	    for(FlowElement e : flowElements) {
			    	    	if(e.getClass().toString().equals("class org.activiti.bpmn.model.UserTask")){
			    	    		//查询默认人员
			    	    		ProcUser pu=new ProcUser();
			    	    		pu.setTaskDefKey(e.getId());
			    	    		pu.setProcDefId(list.get(0).getId());
			    	    		List<ProcUser> pList=iTaskUserService.selectProcUserList(pu);
			    	    		resultMap.put("pList", pList);
			    	    		break;
			    	    	}
			    	    } 
			    	}
				}else{//查看、编辑页面
					WorkflowTaskEntitiy wte =new WorkflowTaskEntitiy();
					wte.setId(Long.valueOf(tId));
					wte=iWorlkflowMainEntityService.getWteByProInstanceId(wte);
					if(null != wte){
						WorlkflowMainEntity wme=new WorlkflowMainEntity();
						wme.setId(wte.getProcessMainId());
						wme=iWorlkflowMainEntityService.getWfmByProdefId(wme);
						ProcessDefinition  pd=processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(wme.getProcessDefinitionId()).singleResult();
						pc.setProcessDefId(pd.getId());
					}
				}
			}else{
				pc.setProcessDefId(processDefId);
			}
			List<ProcessConfigure> list=iProcessConfigureService.selectProcessConfigureList(pc);
			if(list.size()>0){
				resultMap.put("processConfigure", list.get(0));
			}else{
				resultMap.put("processConfigure",'0');
			}
			resultMap.put("message", "1");
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 添加或修改流程配置信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertOrUpdateProcessConfigure.do")
	public void insertOrUpdateProcessConfigure(HttpServletRequest request, HttpServletResponse response) {
		try {
			String processDefId = request.getParameter("processDefId");//流程定义id
			String taskKey = request.getParameter("taskKey");//审批节点key
			String normalAttrs=request.getParameter("normalAttrs");
			String qualityAttrs=request.getParameter("qualityAttrs");
			String designAttrs=request.getParameter("designAttrs");
			String purchaseAttrs=request.getParameter("purchaseAttrs");
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			HrUser user = getUserInfo(request);
			if (user == null||StringUtils.isEmpty(processDefId)||StringUtils.isEmpty(taskKey)) {
				resultMap.put("message", "2");
				String jsonString = JSON.toJSONString(resultMap);
				outputJson(response, jsonString);
				return;
			}
			//先删除再添加
			ProcessConfigure pc=new ProcessConfigure();
			pc.setTaskKey(taskKey);
			pc.setProcessDefId(processDefId);
			iProcessConfigureService.deleteProcessConfigure(pc);
			pc.setNormalAttrs(normalAttrs);
			pc.setProcessType("0");
			pc.setDesignAttrs(designAttrs);
			pc.setPurchaseAttrs(purchaseAttrs);
			pc.setQualityAttrs(qualityAttrs);
			iProcessConfigureService.insertProcessConfigure(pc);
			resultMap.put("message", "1");
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}