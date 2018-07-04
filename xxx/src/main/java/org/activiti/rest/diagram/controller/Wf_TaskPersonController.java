package org.activiti.rest.diagram.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 设置流程表达式时可以用#或者$？？？？？？
 * 流程版本 不会叠加。
 * 流程办理人表(jj--jay--eason)
 * 单节点，多用户流程：必须设置下图红框属性，Multi-instance type 代表是单用户还是多用户，？？？？？
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/taskPerson")
public class Wf_TaskPersonController {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /**
     * 启动流程
     */
	@RequestMapping(value="/startProcessForPersontask")
	public void  startProcessForPersontask(){
		     String processKey = "assignProcess1";
		     //启动流程的时候设置流程变量，流程变量设定的就是当前任务节点所需的执行人，对应的是Assignment中的#{userId}
		     Map<String,Object> map = new HashMap<String,Object>();
		     map.put("gid", "jjlin");
		       processEngine.getRuntimeService()
		                                .startProcessInstanceByKey(processKey);
	}
	
	  /**查询当前办理人任务**/
    @RequestMapping(value="findPersonalTask")
    @Test
    public void findMyPersonalTask(String assignee){
    	 assignee = "taskProcess";
    	List<Task> list = processEngine.getTaskService()//调用任务管理service
    	                         .createTaskQuery()//创建任务查询对象
    	                         .taskAssignee(assignee) //指定个人任务查询，指定办理人
//    	                         .taskCandidateUser("")//组任务的办理人
//    	                         .processDefinitionId("流程定义ID查询任务")
//    	                         .processInstanceId("流程实例ID查询任务")
//    	                         .executionId("执行ID查询任务")
    	                         //排序
    	                         .orderByTaskCreateTime().asc()
    	                         .list();//返回任务list集合
//    	                         .count()
//    	                         .listPage(0, 10)
//    	                         .singleResult()
    	if(list != null && list.size()>0){
    		for(Task task:list){
    			System.out.println("任务ID"+task.getId());
    			System.out.println("任务名称"+task.getName());
    			System.out.println("任务的创建时间"+task.getCreateTime());
    			System.out.println("任务的办理人"+task.getAssignee());
    			System.out.println("流程实例ID"+task.getProcessInstanceId());
    			System.out.println("流程定义ID"+task.getProcessDefinitionId());
    			System.out.println("流程执行对象ID"+task.getExecutionId());
    		}
    	}
    }
    /**查询当前人的组任务**/
    @RequestMapping(value="findMyGroupTask")
    public void findMyGroupTask(String andidateUser){
    	String user = "58";
    	List<Task> list = processEngine.getTaskService()//调用任务管理service
    	                         .createTaskQuery()//创建任务查询对象
    	                         .taskCandidateUser(user)//组任务的办理人
    	                         //排序
    	                         .orderByTaskCreateTime().asc()
    	                         .list();//返回任务list集合
    	if(list != null && list.size()>0){
    		for(Task task:list){
    			System.out.println("任务ID"+task.getId());
    			System.out.println("任务名称"+task.getName());
    			System.out.println("任务的创建时间"+task.getCreateTime());
    			System.out.println("任务的办理人"+task.getAssignee());
    			System.out.println("流程实例ID"+task.getProcessInstanceId());
    			System.out.println("流程定义ID"+task.getProcessDefinitionId());
    			System.out.println("流程执行对象ID"+task.getExecutionId());
    		}
    	}
    }
    /**查询正在执行的任务办理人表**/
    @RequestMapping(value="findRunPersonTask")
    public void findRunPersonTask(){
    	String taskId = "";
    	List<IdentityLink> indentifLink = processEngine.getTaskService()
    	                        .getIdentityLinksForTask(taskId);
    	if(null != indentifLink && indentifLink.size()>0){
    		for(IdentityLink link:indentifLink){
    		  	System.out.println("组ID："+link.getGroupId());
    	    	System.out.println("类型："+link.getType());
    	    	System.out.println("用户ID："+link.getUserId());
    	    	System.out.println("任务ID："+link.getTaskId());
    	    	System.out.println("实例ID："+link.getProcessInstanceId());
        	}
    	}
    }
    /**查询历史任务办理人表**/
    @RequestMapping(value="findHistoricTask")
    public void findHistoricTask(){
    	String processInstanceId ="";
    	List<HistoricIdentityLink> indentifLink = processEngine.getHistoryService()
    	                        .getHistoricIdentityLinksForProcessInstance(processInstanceId);
    	if(null != indentifLink && indentifLink.size()>0){
    		for(HistoricIdentityLink link:indentifLink){
    		  	System.out.println("组ID："+link.getGroupId());
    	    	System.out.println("类型："+link.getType());
    	    	System.out.println("用户ID："+link.getUserId());
    	    	System.out.println("任务ID："+link.getTaskId());
    	    	System.out.println("实例ID："+link.getProcessInstanceId());
        	}
    	}
    }
    /**拾取任务，将组任务分给个人任务，指定任务办理人表中办理人字段**/
    @RequestMapping(value="/claim.do")
    public void claim(){
    	
    	String taskId = "32505";
    	//可以是组任务中得人员，也可以是非组任务中的人员,此时通过type类行为participant来指定任务的办理人
    	//在开发中，可以将每一个任务的办理人规定好，例如张三的领导是李四和王五，
    	//这样张三提交任务，由李四或者王五去查询组任务，可以看到对应张三的申请，李四或者王五通过认领任务(clarm)方式，由某一个人去完成你的任务、
    	String userId = "周杰伦";
    	processEngine.getTaskService()
    	                        .claim(taskId, userId);
    }
    /**将个人任务回退到组任务，前提之前一定是组任务**/
    @RequestMapping(value="/setAssignee.do")
    public void setAssignee(){
    	String taskId = "32505";
    	processEngine.getTaskService()
    	                        .setAssignee(taskId, null);
    }
    /**向组任务中添加人员**/
     public void addGroupUser(){
    	 String taskId = "";
    	 String userId = "";
    	 processEngine.getTaskService()
    	                         .addCandidateUser(taskId, userId);
     }
     /**删除组任务中的人员**/
     public void deleteGroupUser(){
    	 String taskId = "";
    	 String userId = "";
    	 processEngine.getTaskService()
    	                          .deleteCandidateUser(taskId, userId);
     }
    /**
     * 完成的任务
     * @param taskId
     */
    @RequestMapping(value="completeMyProcessTask")
    public void completeMyProcessTask(String taskId){
    	taskId = "112503";
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("message", "重要");
    	//任务ID
    	processEngine.getTaskService()
    	                        .complete(taskId, map);;
    	System.out.println("任务完成：任务ID："+taskId);
    }
    /**设置个人任务，从一个人到另一个人(认领任务)**/
    public void setAssigneeTask(){
    	String taskId = "任务ID";
    	String userId = "用户ID";
    	processEngine.getTaskService()
    	                        .setAssignee(taskId, userId);
    }
}
