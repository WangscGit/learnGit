package org.activiti.rest.diagram.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.rest.diagram.services.IWorkflowBaseService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 流程动态图片
 * @author WANGSC
 *
 */
@Controller
@RequestMapping(value="/flowPng")
public class DiagramPngController {
	@Autowired
	private HistoryService  historyService;
	@Autowired
	private RuntimeService   runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProcessEngine processEngine ;
	@Autowired
	private IWorkflowBaseService workflowBaseService;
	/** 
	 * 流程是否已经结束 
	 *  
	 * @param processInstanceId 流程实例ID 
	 * @return 
	 */
	@RequestMapping(value="/isFinished.do")  
	public boolean isFinished(String processInstanceId) {  
	    return historyService.createHistoricProcessInstanceQuery().finished()  
	            .processInstanceId(processInstanceId).count() > 0;  
	}  
	  
	/** 
	 * 流程跟踪图片 
	 *  
	 * @param processDefinitionId 
	 *            流程定义ID 
	 * @param executionId 
	 *            流程运行ID 
	 * @param out 
	 *            输出流 
	 * @throws Exception 
	 */  
	@RequestMapping(value="/processTracking.do")  
	public void processTracking(HttpServletRequest request,HttpServletResponse response, 
	        OutputStream out) throws Exception {  
		String taskId = request.getParameter("taskId");
		Task task=processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		String processDefinitionId = task.getProcessDefinitionId();
		String executionId=task.getExecutionId();
	    // 当前活动节点、活动线  
	    List<String> activeActivityIds = new ArrayList<String>(), highLightedFlows = new ArrayList<String>();  
	  
	    /** 
	     * 获得当前活动的节点 
	     */  
	    if (this.isFinished(executionId)) {// 如果流程已经结束，则得到结束节点  
	        activeActivityIds.add(historyService  
	                .createHistoricActivityInstanceQuery()  
	                .executionId(executionId).activityType("endEvent")  
	                .singleResult().getActivityId());  
	    } else {// 如果流程没有结束，则取当前活动节点  
	        // 根据流程实例ID获得当前处于活动状态的ActivityId合集  
	        activeActivityIds = runtimeService  
	                .getActiveActivityIds(executionId);  
	    }  
	    /** 
	     * 获得当前活动的节点-结束 
	     */  
	  
	    /** 
	     * 获得活动的线 
	     */  
	    // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）  
	    List<HistoricActivityInstance> historicActivityInstances = historyService  
	            .createHistoricActivityInstanceQuery().executionId(executionId)  
	            .orderByHistoricActivityInstanceStartTime().asc().list();  
	    // 计算活动线  
	    highLightedFlows = this  
	            .getHighLightedFlows(  
	                    (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
	                            .getDeployedProcessDefinition(processDefinitionId),  
	                    historicActivityInstances);  
	    /** 
	     * 获得活动的线-结束 
	     */  
	  
	    /** 
	     * 绘制图形 
	     */  
	    if (null != activeActivityIds) {  
	        InputStream imageStream = null;  
            response.setContentType("application/x-download");
	        try {  
	            // 获得流程引擎配置  
	            ProcessEngineConfiguration processEngineConfiguration = processEngine  
	                    .getProcessEngineConfiguration();  
	            // 根据流程定义ID获得BpmnModel  
	            BpmnModel bpmnModel = repositoryService  
	                    .getBpmnModel(processDefinitionId);  
	            // 输出资源内容到相应对象  
	            imageStream = new DefaultProcessDiagramGenerator()  
	                    .generateDiagram(bpmnModel, "png", activeActivityIds,  
	                            highLightedFlows, processEngineConfiguration  
	                                    .getActivityFontName(),  
	                            processEngineConfiguration.getLabelFontName(),  
	                            processEngineConfiguration.getClassLoader(),  
	                            1.0); 
	            IOUtils.copy(imageStream, out);  
	        } finally {  
	            IOUtils.closeQuietly(imageStream);  
	        }  
	    }  
	}  
	  
	/** 
	 * 获得高亮线 
	 *  
	 * @param processDefinitionEntity 
	 *            流程定义实体 
	 * @param historicActivityInstances 
	 *            历史活动实体 
	 * @return 线ID集合 
	 */  
	@RequestMapping(value="/getHighLightedFlows.do")  
	public List<String> getHighLightedFlows(  
	        ProcessDefinitionEntity processDefinitionEntity,  
	        List<HistoricActivityInstance> historicActivityInstances) {  
	  
	    List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId  
	    for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历  
	        ActivityImpl activityImpl = processDefinitionEntity  
	                .findActivity(historicActivityInstances.get(i)  
	                        .getActivityId());// 得 到节点定义的详细信息  
	        List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点  
	        if ((i + 1) >= historicActivityInstances.size()) {  
	            break;  
	        }  
	        ActivityImpl sameActivityImpl1 = processDefinitionEntity  
	                .findActivity(historicActivityInstances.get(i + 1)  
	                        .getActivityId());// 将后面第一个节点放在时间相同节点的集合里  
	        sameStartTimeNodes.add(sameActivityImpl1);  
	        for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {  
	            HistoricActivityInstance activityImpl1 = historicActivityInstances  
	                    .get(j);// 后续第一个节点  
	            HistoricActivityInstance activityImpl2 = historicActivityInstances  
	                    .get(j + 1);// 后续第二个节点  
	            if (activityImpl1.getStartTime().equals(  
	                    activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存  
	                ActivityImpl sameActivityImpl2 = processDefinitionEntity  
	                        .findActivity(activityImpl2.getActivityId());  
	                sameStartTimeNodes.add(sameActivityImpl2);  
	            } else {// 有不相同跳出循环  
	                break;  
	            }  
	        }  
	        List<PvmTransition> pvmTransitions = activityImpl  
	                .getOutgoingTransitions();// 取出节点的所有出去的线  
	        for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历  
	            ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition  
	                    .getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示  
	            if (sameStartTimeNodes.contains(pvmActivityImpl)) {  
	                highFlows.add(pvmTransition.getId());  
	            }  
	        }  
	    }  
	    return highFlows;  
	}  
	/**
	 * 流程实例ID查询
	 * @param dfId
	 * @return
	 */
    public String findMyPersonalTask(String dfId){
   	List<Task> list = processEngine.getTaskService()//调用任务管理service
   	                         .createTaskQuery()//创建任务查询对象
   	                         .processInstanceId(dfId)
   	                         .orderByTaskCreateTime().asc()
   	                         .list();
   	   if(list.size()>0){
          return list.get(0).getExecutionId();
   	   }else{
   		   return "-1";
   	   }
   }
}
