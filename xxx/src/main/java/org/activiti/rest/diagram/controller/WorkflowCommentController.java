package org.activiti.rest.diagram.controller;


import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class WorkflowCommentController {
public static final Logger logger = LoggerFactory.getLogger(WorkflowCommentController.class);
	
	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ManagementService managementService;
	@Autowired
	private IdentityService identityService;
	
	/**
	 * 流程发布
	 * 可以发布多次，会取最新版本的流程
	 * @param flowName 需要发布的流程名称
	 * @return 状态码
	 */
	@RequestMapping(value="/process/release",method=RequestMethod.GET)
	public String release(String flowName){
		logger.info("-> 开始发布流程：{}。",flowName);
		
//		repositoryService.createDeployment()
//		  .addClasspathResource(Constant.DIAGRAMS.concat(File.separator).concat(flowName).concat(Constant.BPMN))
//		  .deploy();
		
		logger.info("-> 流程：{}已成功发布。",flowName);
		 String result = "success";
		return result;
	}
	
	/**
	 * 查看所有已发布流程(不管流程是否已启动)
	 * @return 已发布流程集合
	 */
//	@RequestMapping(value="/process/release/list",method=RequestMethod.GET)
//	public ListResult<Map<String,Object>> releaseList(){
//		logger.info("-> 查询所有已发布的流程。");
//		
//		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
//		
//		List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
//		for (ProcessDefinition pd : list) {
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("id", pd.getId());
//			map.put("key", pd.getKey());
//			maps.add(map);
//		}
//		
//		logger.info("-> 查询结果：{}。",list);
//		ListResult<Map<String,Object>> r = new ListResult<Map<String,Object>>();
//		r.setCode(ErrorCode.SUCCESS);
//		r.setData(maps);
//		return r;
//	}
	
	/**
	 * 流程启动，单个流程可以启动多个流程实例
	 * @param flowName 需要启动的流程名称
	 * @return 状态码
	 */
//	@RequestMapping(value="/process/start",method=RequestMethod.GET)
//	public Result start(String flowName){
//		logger.info("-> 开始启动流程：{}。",flowName);
//		
//		/*
//		 * 还可以传递参数，很多时候某个流程节点并没有写定具体的参数，
//		 * 比如员工提交审批申请时才手动勾选需要审批的人员。
//		 */
//		runtimeService.startProcessInstanceByKey(flowName);
//		
//		logger.info("-> 流程：{}已成功启动。",flowName);
//		
//		Result r = new Result(ErrorCode.SUCCESS);
//		return r;
//	}
	
	/**
	 * 查看所有已启动的流程实例
	 * @return 流程实例集合
	 */
//	@RequestMapping(value="/process/start/list",method=RequestMethod.GET)
//	public ListResult<Map<String,Object>> startList(){
//		logger.info("-> 开始查询所有已启动的流程实例。");
//		
//		/*
//		 * 还可以传递参数，很多时候某个流程节点并没有写定具体的参数，
//		 * 比如员工提交审批申请时才手动勾选需要审批的人员。
//		 */
//		List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
//		
//		List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
//		for (ProcessInstance pi : list) {
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("id", pi.getId());
//			maps.add(map);
//		}
//		
//		logger.info("-> 查询结果：{}。",list);
//		ListResult<Map<String,Object>> r = new ListResult<Map<String,Object>>();
//		r.setCode(ErrorCode.SUCCESS);
//		r.setData(maps);
//		return r;
//	}
	
	/**
	 * 查看个人待完成的任务
	 * @param 用户名
	 * @return 用户待完成任务集合
	 */
//	@RequestMapping(value="/user/task/undo",method=RequestMethod.GET)
//	@ResponseBody
//	public ListResult<Map<String,Object>> userTaskUndo(String userName){
//		logger.info("-> 开始查询用户:{}待完成的任务。",userName);
//		
//		// 还可以查看用户已提交的任务，已完成的任务等等。
//		List<Task> list = taskService.createTaskQuery().taskDefinitionKey(userName).list();
//		
//		List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
//		for (Task task : list) {
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("id", task.getId());
//			map.put("name", task.getName());
//			maps.add(map);
//		}
//		
//		logger.info("-> 查询结果：{}。",list);
//		ListResult<Map<String,Object>> r = new ListResult<Map<String,Object>>();
//		r.setCode(ErrorCode.SUCCESS);
//		r.setData(maps);
//		return r;
//	}
	
	/**
	 * 任务完成
	 * @param 任务ID
	 * @return 状态码
	 */
//	@RequestMapping(value="/task/complete",method=RequestMethod.GET)
//	public Result taskComplete(String taskId){
//		logger.info("-> 任务:{}审批完成。",taskId);
//		
//		taskService.complete(taskId);
//		
//		logger.info("-> 操作成功。");
//		Result r = new Result(ErrorCode.SUCCESS);
//		return r;
//	}
}
