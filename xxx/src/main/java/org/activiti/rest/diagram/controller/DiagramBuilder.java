package org.activiti.rest.diagram.controller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 流程
 * 1.如果是单例流程(没有分支和聚合),那么流程实例ID和执行对象ID是相同.
 * 2.一个流程流程实例只有一个，执行对象可以有多个.
 * 3.流程实例指的是从开始到结束的最大分支.
 * 4.输入流部署方式名称需要与部署文件名相同
 * 排他网关
 * 1、一个排他网关对应一个以上的顺序流
 * 2、由排他网关流程的顺序流都有个conditionExpression元素，在内部维护返回bolean类型的决策结果
 * 3、决策网关只会返回一条结果，当流程执行到排他网关时，流程引擎会自动检索网关出口，从上到下检索如果发现第一条决策结果为true或者没有设置条件的(默认为成立)，则流出
 * 接收任务(receiveTask 即等待活动)
 * 1、在任务创建后，流程将进入等待状态，直到引擎接收了一个特定的消息，才会继续执行
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/builder")
public class DiagramBuilder {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**生成activiti表结构**/
    @RequestMapping(value="/createActTables.do")
	public void createActTables(){
		// 引擎配置  
        ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();  
        pec.setJdbcDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
        pec.setJdbcUrl("jdbc:sqlserver://localhost:1433;DatabaseName=CMS_Cloudy_database;integratedSecurity=false");  
        pec.setJdbcUsername("sa");  
        pec.setJdbcPassword("123456");  
           
        /** 
         * DB_SCHEMA_UPDATE_FALSE 不能自动创建表，需要表存在 
         * create-drop 先删除表再创建表 
         * DB_SCHEMA_UPDATE_TRUE 如何表不存在，自动创建和更新表   
         */  
        pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);  
	}
    /** 部署流程定义 */
    @RequestMapping(value="/deployProcess.do")
    public void deploymentProcessDefinition() {
        /**
         * RepositoryService是Activiti的仓库服务类,流程定义和部署对象相关的Service
         * 所谓的仓库指流程定义文档的两个文件：bpmn文件和流程图片
         */
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 创建一个部署对象DeploymentBuilder，用来定义流程部署的相关参数
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        // 添加部署的名称
        deploymentBuilder.name("activiti入门程序");
        // 添加HelloWord.bpmn和HelloWord.png
        deploymentBuilder.addClasspathResource("diagrams/HelloWord.bpmn");
        deploymentBuilder.addClasspathResource("diagrams/HelloWord.png");
        // 部署流程定义
        Deployment deployment = deploymentBuilder.deploy();
  
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署名称：" + deployment.getName());//activiti入门程序
    }
    /** 部署流程定义 */
    @RequestMapping(value="/deployProcess_zip.do")
    public void deploymentProcessDefinition_zip(){
    	InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloWord.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deploy = processEngine.getRepositoryService()
        		                                                      .createDeployment()//创建一个部署对象
        		                                                      .name("流程定义")
        		                                                      .addZipInputStream(zipInputStream)
                                                                      .deploy();//完成部署
        System.out.println("部署ID"+deploy.getId());
        System.out.println("部署名称"+deploy.getId());
    }
	/**启动流程实例**/
    @RequestMapping(value="/startProcess.do")
    public void startProcessInstance(@Param(value="testProcess1") String processKey){
    	processKey = "myProcess";
    	Map<String,Object> map = new HashMap<String,Object>();  
    	map.put("userIds", "admin,jjlin,jay");
    	ProcessInstance pi = processEngine.getRuntimeService()
    			                                                     .startProcessInstanceByKey(processKey,map); //使用流程定义的Key，启动流程。key对应的是HelloWord.bpmn当中的ID属性.;使用key的方式启动流程，可以找到最新版本的流程，进行启动,
          System.out.println("流程实例ID："+pi.getId());
          System.out.println("流程定义ID："+pi.getProcessDefinitionId());
//          System.out.println("流程实例ID："+pi.getId());
//          System.out.println("流程实例ID："+pi.getId());
    }
    /**查询当前办理人任务**/
    @RequestMapping(value="findPersonalTask")
    @Test
    public void findMyPersonalTask(String assignee){
    	 assignee = "周杰伦";
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
    /**
     * 查看流程图片
     * @param deploymentId
     */
    @RequestMapping(value="viewPic")
    public void viewPic(String deploymentId){
    	deploymentId = "42501";
    	//获得图片资源名称
    	List<String> list = processEngine.getRepositoryService()
    			                                              .getDeploymentResourceNames(deploymentId);
        //定义图片资源名称
    	String resourceName = "";
    	if(list != null && list.size()>0){
    		for(String name : list){
    			 if(name.indexOf(".png") >= 0){
    				 resourceName = name;
    			 }
    		}
    	}
    	//获得图片的输入流
    	InputStream in = processEngine.getRepositoryService()
    			                                             .getResourceAsStream(deploymentId, resourceName);
    	//定义图片存放位置
    	File file = new File("C:"+File.separator+resourceName);
    	try {
    		//将输入流读写到磁盘下
			FileUtils.copyInputStreamToFile(in,file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * 查询流程定义
     * @param request
     * @param response
     */
    @RequestMapping(value="findProcessDefinition")
    public void findProcessDefinition(HttpServletRequest request, HttpServletResponse response){
    	List<ProcessDefinition> list = processEngine.getRepositoryService()
    			                                                                 .createProcessDefinitionQuery()//创建一个流程实例查询
//    			                                                                 .deploymentId("")//部署对象ID查询
//    			                                                                .processDefinitionId("")//流程定义ID查询
//    			                                                                .processDefinitionKey("")//流程定义KEY查询
//    			                                                                .processDefinitionNameLike("")//流程定义名称模糊查询
    			                                                                 .orderByProcessDefinitionVersion().asc()//按版本升序排序
//    			                                                                 .orderByProcessDefinitionName().desc()//按名称降序排序
    			                                                                 .list();//返回一个封装流程定义的集合
//    	                                                                         .singleResult();//返回唯一结果集
//    			                                                                 .count();
//    	                                                                          .listPage(0, 10);//分页查询
        if(list != null && list.size()>0){
        	for(ProcessDefinition pd:list){
        		System.out.println("流程定义ID"+pd.getId());
        		System.out.println("流程定义NAME"+pd.getName());
        		System.out.println("流程定义KEY"+pd.getKey());
        		System.out.println("流程定义版本"+pd.getVersion());
        		System.out.println("资源名称bpmn文件"+pd.getResourceName());
        		System.out.println("资源名称PNG文件"+pd.getDiagramResourceName());
        		System.out.println("资源对象ID"+pd.getDeploymentId());
        	}
        }
    }
    /**
     * 查询流程定义最后版本
     * @param request
     * @param response
     */
    @RequestMapping(value="findLastVersionProcessDefinition")
    public void findLastVersionProcessDefinition(HttpServletRequest request,HttpServletResponse response){
    	List<ProcessDefinition> list = processEngine.getRepositoryService()
    			                                                                  .createProcessDefinitionQuery()
    			                                                                  .orderByProcessDefinitionVersion().asc()
    			                                                                  .list();
    	/**
    	 * 当map数组的key相同时，后面的值会覆盖前面的值
    	 */
    	Map<String,ProcessDefinition> map = new LinkedHashMap<String,ProcessDefinition>();
        if(list != null && list.size()>0){
        	for(ProcessDefinition pd:list){
        		map.put(pd.getKey(), pd);
        	}
        }
        List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(map.values());
        if(pdList != null && pdList.size()>0){
        	for(ProcessDefinition pd:pdList){
        		System.out.println("流程定义ID"+pd.getId());
        		System.out.println("流程定义NAME"+pd.getName());
        		System.out.println("流程定义KEY"+pd.getKey());
        		System.out.println("流程定义版本"+pd.getVersion());
        		System.out.println("资源名称bpmn文件"+pd.getResourceName());
        		System.out.println("资源名称PNG文件"+pd.getDiagramResourceName());
        		System.out.println("资源对象ID"+pd.getDeploymentId());
        	}
        }
    }
    /**
     * 不管流程有没有启动级联删除部署对象
     * @param request
     * @param response
     */
    @RequestMapping(value="deleteDeployment")
    public void deleteDeployment(HttpServletRequest request,HttpServletResponse response){
    	    processEngine.getRepositoryService()
    	                             .deleteDeployment("deploymentId",true);
            System.out.println("删除成功!");
    }
    /**
     * 删除所有版本的流程定义对象08
     * @param request
     * @param response
     */
    @RequestMapping(value="deleteAllProcessDefinition")
    public void deleteAllProcessDefinition(HttpServletRequest request,HttpServletResponse response){
    	String processKey = "hellpWord";
    	List<ProcessDefinition> list = processEngine.getRepositoryService()
    			                                                                 .createProcessDefinitionQuery()
    			                                                                 .processDefinitionKey(processKey)
    			                                                                 .list();
    	if(null != list && list.size()>0){
    		for(ProcessDefinition pd:list){
    			processEngine.getRepositoryService()
    			                         .deleteDeployment(pd.getDeploymentId());
    		}
    	}
    }
    /**
     * 流程图状态查询
     * false:结束true:进行中
     * @param processInstanceId
     */
    @RequestMapping(value="ifEndInding")
    public boolean ifEndInding(String processInstanceId){
    	ProcessInstance process = processEngine.getRuntimeService()
    	                        .createProcessInstanceQuery()
    	                        .processInstanceId(processInstanceId)
    	                        .singleResult();
    	if(null == process){
    		 return false;
    	}else{
    		 return true;
    	}
    }
    /**
     * 向某个任务节点中添加人员
     * @param loginName
     * @param taskId
     */
    @RequestMapping(value="addUserForTask")
    public void addUserForTask(String loginName,String taskId){
    	//向组任务中添加成员
    	 taskId ="22504";
    	 loginName ="admin";
    	processEngine.getTaskService()
    	             .addCandidateUser(taskId, loginName);
    }
    /**
     *  查找历史流程实例通过流程实例ID
     */
    public void findHistoryProcessInstance(HttpServletRequest request,HttpServletResponse response){
    	 String processInstanceId = "666";
    	 HistoricProcessInstance processInstance = processEngine.getHistoryService()
    			                                                                            .createHistoricProcessInstanceQuery()
    			                                                                            .processInstanceId(processInstanceId)
    			                                                                            .singleResult();
    	 System.out.println("历史流程ID"+processInstance.getId() + "流程定义ID" + processInstance.getProcessDefinitionId() + "开始时间" + processInstance.getStartTime() + "结束时间"+ processInstance.getEndTime()  + "耗时(秒)" + processInstance.getDurationInMillis());
    }
    /**
     * 查询历史流程任务通过执行人
     * @param request
     * @param response
     */
    public void findHistoryHistoryTask(HttpServletRequest request,HttpServletResponse response){
    	 String assigneeTask = "jjlin";
    	List<HistoricTaskInstance> list = processEngine.getHistoryService()
    	                        .createHistoricTaskInstanceQuery()
    	                        .taskAssignee(assigneeTask)
    	                        .list();
    	if(null != list && list.size()>0){
    		for(HistoricTaskInstance processInstance:list){
    	    	 System.out.println("历史流程ID"+processInstance.getId() + "流程定义ID" + processInstance.getProcessDefinitionId() + "开始时间" + processInstance.getStartTime() + "结束时间"+ processInstance.getEndTime()  + "耗时(秒)" + processInstance.getDurationInMillis());
    		}
    	}
    }
}
