package org.activiti.rest.diagram.controller;

import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.rest.diagram.pojo.ProcessEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 1.流程变量对应的是每个流程实例(各个流程实例间的变量互补影响)
 * 2.设置local类型的流程变量 只有本节点 能 获取到 要想 其他 节点 获取 到 需要 查询历史 变量 表
 * 3.不设置local并且流程变量名称相同的话，后者的值会替代前者的值
 * @author Wangsc
 *
 */
@Controller
@RequestMapping(value="/processVariables")
public class ProcessVariablesController {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 通过输入流部署流程
	 */
	@RequestMapping(value="/deployProcessByStream.do")
	 public void deploymentProcessForStream(){
		 
		  InputStream streamProceebpmn = this.getClass().getResourceAsStream("/diagrams/streamProcess.bpmn");
		  InputStream streamProceepng = this.getClass().getResourceAsStream("/diagrams/streamProcess.png");
	     Deployment deploy =  processEngine.getRepositoryService()
	                             .createDeployment()
	                             .addInputStream("streamProcess.bpmn", streamProceebpmn)//名称必须与原文件名相同
	                             .addInputStream("streamProcess.png", streamProceepng)
	                             .deploy();
	     System.out.println("部署ID"+deploy.getId());
	     System.out.println("部署名称"+deploy.getName());
	                             
	}
	/**
	 * 启动流程
	 */
	@RequestMapping(value="/startProcessForKey.do")
	public void startProcessForKey(){
		ProcessInstance instance = processEngine.getRuntimeService()
		                        .startProcessInstanceByKey("");
	}
	/**
	 * 设置流程变量
	 */
	@RequestMapping(value="/setProcessVaraibles.do")
	public void setProcessVaraibles(HttpServletRequest request,HttpServletResponse response){
		//正在执行的任务
		String taskId = "65002";
//		Map<String,Object> map = new HashMap<String,Object>();
//	    map.put("请假天数", 3);
//	    map.put("请假日期", new Date());
//	    map.put("请假原因", "回家探亲");
//	    processEngine.getTaskService().setVariableLocal(taskId, "请假天数", 3);//local指的是与当前任务ID绑定
//	    processEngine.getTaskService().setVariable(taskId, "请假日期", new Date());
//	    processEngine.getTaskService().setVariable(taskId, "请假原因", "回家探亲");
        /**使用javebean的方式设置流程变量**/
		/**
		 * 当一个javabean(实现序列号 )放置到流程变量中，需求javabean的属性不能再发生变化
		 * 如果发生变化在获取的时候抛出异常
		 * 解决方案：	private static final long serialVersionUID = 6757393795687480331L;
		 * 实现implements Serializable接口
		 */
		ProcessEntity process = new ProcessEntity();
		process.setId(666);
        process.setName("xxx");	
        processEngine.getTaskService().setVariable(taskId, "人员信息",process);
	    System.out.println("设置流程变量成功!");
	}
	/**
	 * 获取流程变量
	 */
	@RequestMapping(value="/getProcessVaraibles.do")
	public void getProcessVaraibles(HttpServletRequest request,HttpServletResponse response){
		String taskId = "65002";
//	    Integer days = (Integer)processEngine.getTaskService().getVariable(taskId, "请假天数");//local指的是与当前任务ID绑定
//	    Date date = (Date)processEngine.getTaskService().getVariable(taskId, "请假日期");
//	    String reason = (String)processEngine.getTaskService().getVariable(taskId, "请假原因");
		/**javabean方式获得流程变量**/
		ProcessEntity process = (ProcessEntity)processEngine.getTaskService().getVariable(taskId,  "人员信息");
	    System.out.println(process.getId()+ " "+process.getName()  );
	}
    /**
     *  模拟放/取流程变量
     */
	public void test (){
		//流程实例正在执行
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//流程任务正在执行
		TaskService taskService = processEngine.getTaskService();
		runtimeService.setVariable("executionId", "variableName", "variableValue");//使用执行对象ID，流程变量名称来设置流程变量的值(一次只能设置一个)
//	    runtimeService.setVariables("executionId", Map);//使用执行对象ID，通过Map集合的方式，设置多个流程变量值(Key是流程变量名称,value是流程变量的值)
	    
		taskService.setVariable("taskId", "variableName", "variableValue");//使用任务ID 和 流程变量明层 来设置 流程变量的 值
		//设置多个和runtimeService意义相同
		
		runtimeService.startProcessInstanceByKey("ProcessKey", "variableValues");//启动流程的时候 也可以设置 多个 流程 变量 (MAP)
//		taskService.complete("taskId", "variabless“); 完成任务时 也可以 设置 流程 多个 变量  (MAP)
		//获得设置完成的流程变量
		runtimeService.getVariable("executionId", "variableName");//通过执行对象ID 和 流程 变量 名称  来 获取 流程 变量 的 值 
		runtimeService.getVariables("executionId");//通过执行流程ID 来获取 流程变量的map数组 
//		runtimeService.getVariables("executionId","variableName");//通过执行流程ID和变量名称 来获取 流程变量的值 

		//taskService与runtimeService获得方式相同
	}
	/**
	 * 查询流程变量的历史表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="findHistoryProcessVariables.do")
	public void findHistoryProcessVariables(HttpServletRequest request,HttpServletResponse response){
		     List<HistoricVariableInstance> hic = processEngine.getHistoryService()
		                             .createHistoricVariableInstanceQuery()
		                             .variableName("请假天数")
		                             .list();
		     if(null != hic && hic.size()>0){
		    	 for(HistoricVariableInstance in:hic){
		    		 System.out.println(in.getProcessInstanceId() + in.getVariableName() + in.getTaskId() + in.getTime());
		    	 }
		     }
		                             
	}
	
}
