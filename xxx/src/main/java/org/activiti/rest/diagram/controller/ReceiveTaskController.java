package org.activiti.rest.diagram.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 当前任务(一般指机器自动完成，但需要耗费一定时间的工作)完成后，向后推移流程调用runtimeService.signal（"执行ID"），传递接收执行对象的ID。
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/receiveTask")
public class ReceiveTaskController {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**启动流程_receive**/
	@RequestMapping(value="/startReceiveTask")
	public void startProcessForReceivetask(){
		String processKey="ddd";
		ProcessInstance processInstance = processEngine.getRuntimeService()
		                            .startProcessInstanceByKey("ddd");
		/**判断流程是否结束，查询正在执行的对象类**/
		ProcessInstance rpi = processEngine.getRuntimeService()
				                                                    .createProcessInstanceQuery()
				                                                    .processInstanceId(processInstance.getId())
				                                                    .singleResult();
		//说明流程实例结束了
		if(null == rpi){
			/**查询历史，获取流程的相关信息**/
			HistoricProcessInstance hpi = processEngine.getHistoryService()
					                                           .createHistoricProcessInstanceQuery()
					                                           .processInstanceId(processInstance.getId())
		                                                       .singleResult();
	    	 System.out.println("历史流程ID"+hpi.getId() + "流程定义ID" + hpi.getProcessDefinitionId() + "开始时间" + hpi.getStartTime() + "结束时间"+ hpi.getEndTime()  + "耗时(秒)" + hpi.getDurationInMillis());
		}
		
		//查询执行对象消息类型不是userTask并不会在任务表中产生数据
		 Execution ex = processEngine.getRuntimeService()
		                        .createExecutionQuery()
		                        .processInstanceId(processInstance.getId())
		                        .activityId("recive1")
		                        .singleResult();
		 //使用流程变量设置参数
		 processEngine.getRuntimeService()
		                         .setVariable(ex.getId(), "jjlin单曲个数", 222222);
          //向后执行一步，如果流程处于等待状态,使得流程继续执行
		 processEngine.getRuntimeService()
		                          .signal(ex.getId());
		 //查询第二个节点的执行对象
		 Execution ex2 = processEngine.getRuntimeService()
        		                                           .createExecutionQuery()
        		                                           .processInstanceId(processInstance.getId())
        		                                           .activityId("recive2")
        		                                           .singleResult();
		 //获得流程变量参数
		 Integer value = (Integer)processEngine.getRuntimeService()
		                         .getVariable(ex2.getId(), "jjlin单曲个数");
		 System.out.println("给JAY发送消息：单曲个数是："+value);
		 //向后执行一步，如果该流程处于等待状态,使得流程继续执行
		 processEngine.getRuntimeService()
		                         .signal(ex2.getId());
         
	}
	
}
