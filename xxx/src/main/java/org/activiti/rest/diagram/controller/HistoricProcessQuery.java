package org.activiti.rest.diagram.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.springframework.web.bind.annotation.RequestMapping;

public class HistoricProcessQuery {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /**
     *  历史流程实例查询
     */
	@RequestMapping(value="findHistoryProcessInstance.do")
    public void findHistoryProcessInstance(HttpServletRequest request,HttpServletResponse response){
    	 String processInstanceId = "666";
    	 HistoricProcessInstance processInstance = processEngine.getHistoryService()
    			                                                                            .createHistoricProcessInstanceQuery()
    			                                                                            .processInstanceId(processInstanceId)
    			                                                                            .singleResult();
    	 System.out.println("历史流程ID"+processInstance.getId() + "流程定义ID" + processInstance.getProcessDefinitionId() + "开始时间" + processInstance.getStartTime() + "结束时间"+ processInstance.getEndTime()  + "耗时(秒)" + processInstance.getDurationInMillis());
    }
	/**查询历史任务**/
	@RequestMapping(value="findHistoryTask.do")
    public void findHistoryTask(HttpServletRequest request,HttpServletResponse response){
//    	 String assigneeTask = "jjlin";
   	 String processInstanceId = "666";
    	List<HistoricTaskInstance> list = processEngine.getHistoryService()
    	                        .createHistoricTaskInstanceQuery()
//    	                        .taskAssignee(assigneeTask)
    	                        .processInstanceId(processInstanceId)
    	                        .orderByHistoricTaskInstanceStartTime().asc()
    	                        .list();
    	if(null != list && list.size()>0){
    		for(HistoricTaskInstance processInstance:list){
    	    	 System.out.println("历史流程ID"+processInstance.getId() + "流程定义ID" + processInstance.getProcessDefinitionId() + "开始时间" + processInstance.getStartTime() + "结束时间"+ processInstance.getEndTime()  + "耗时(秒)" + processInstance.getDurationInMillis());
    		}
    	}
    }
    /**
     * 查询历史活动
     * @param request
     * @param response
     */
	@RequestMapping(value="findHistoryActivity.do")
    public void findHistoryActivity(HttpServletRequest request,HttpServletResponse response){
    	String processInstanceId = "";
    	List<HistoricActivityInstance> list = processEngine.getHistoryService()
    	                        .createHistoricActivityInstanceQuery()
    	                        .processInstanceId(processInstanceId)
    	                        .orderByHistoricActivityInstanceStartTime().asc()
    	                        .list();
    	if(null != list && list.size()>0){
    		for(HistoricActivityInstance processInstance:list){
   	    	 System.out.println("历史流程ID"+processInstance.getId() + "流程定义ID" + processInstance.getProcessDefinitionId() + "开始时间" + processInstance.getStartTime() + "结束时间"+ processInstance.getEndTime()  + "耗时(秒)" + processInstance.getDurationInMillis());
    		}
    	}
    }
    /**查询历史流程变量**/
	@RequestMapping(value="findHistoryProcessVariables.do")
	public void findHistoryVariables(HttpServletRequest request,HttpServletResponse response){
    	String processInstanceId = "";
		     List<HistoricVariableInstance> hic = processEngine.getHistoryService()
		                             .createHistoricVariableInstanceQuery()
//		                             .variableName("请假天数")
		                             .processInstanceId(processInstanceId)
		                             .list();
		     if(null != hic && hic.size()>0){
		    	 for(HistoricVariableInstance in:hic){
		    		 System.out.println(in.getProcessInstanceId() + in.getVariableName() + in.getTaskId() + in.getTime());
		    	 }
		     }
		                             
	}
}
