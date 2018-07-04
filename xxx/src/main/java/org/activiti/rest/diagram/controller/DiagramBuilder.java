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
 * ����
 * 1.����ǵ�������(û�з�֧�;ۺ�),��ô����ʵ��ID��ִ�ж���ID����ͬ.
 * 2.һ����������ʵ��ֻ��һ����ִ�ж�������ж��.
 * 3.����ʵ��ָ���Ǵӿ�ʼ������������֧.
 * 4.����������ʽ������Ҫ�벿���ļ�����ͬ
 * ��������
 * 1��һ���������ض�Ӧһ�����ϵ�˳����
 * 2���������������̵�˳�������и�conditionExpressionԪ�أ����ڲ�ά������bolean���͵ľ��߽��
 * 3����������ֻ�᷵��һ�������������ִ�е���������ʱ������������Զ��������س��ڣ����ϵ��¼���������ֵ�һ�����߽��Ϊtrue����û������������(Ĭ��Ϊ����)��������
 * ��������(receiveTask ���ȴ��)
 * 1�������񴴽������̽�����ȴ�״̬��ֱ�����������һ���ض�����Ϣ���Ż����ִ��
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/builder")
public class DiagramBuilder {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**����activiti��ṹ**/
    @RequestMapping(value="/createActTables.do")
	public void createActTables(){
		// ��������  
        ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();  
        pec.setJdbcDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
        pec.setJdbcUrl("jdbc:sqlserver://localhost:1433;DatabaseName=CMS_Cloudy_database;integratedSecurity=false");  
        pec.setJdbcUsername("sa");  
        pec.setJdbcPassword("123456");  
           
        /** 
         * DB_SCHEMA_UPDATE_FALSE �����Զ���������Ҫ����� 
         * create-drop ��ɾ�����ٴ����� 
         * DB_SCHEMA_UPDATE_TRUE ��α����ڣ��Զ������͸��±�   
         */  
        pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);  
	}
    /** �������̶��� */
    @RequestMapping(value="/deployProcess.do")
    public void deploymentProcessDefinition() {
        /**
         * RepositoryService��Activiti�Ĳֿ������,���̶���Ͳ��������ص�Service
         * ��ν�Ĳֿ�ָ���̶����ĵ��������ļ���bpmn�ļ�������ͼƬ
         */
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // ����һ���������DeploymentBuilder�������������̲������ز���
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        // ��Ӳ��������
        deploymentBuilder.name("activiti���ų���");
        // ���HelloWord.bpmn��HelloWord.png
        deploymentBuilder.addClasspathResource("diagrams/HelloWord.bpmn");
        deploymentBuilder.addClasspathResource("diagrams/HelloWord.png");
        // �������̶���
        Deployment deployment = deploymentBuilder.deploy();
  
        System.out.println("����ID��" + deployment.getId());//1
        System.out.println("�������ƣ�" + deployment.getName());//activiti���ų���
    }
    /** �������̶��� */
    @RequestMapping(value="/deployProcess_zip.do")
    public void deploymentProcessDefinition_zip(){
    	InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloWord.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deploy = processEngine.getRepositoryService()
        		                                                      .createDeployment()//����һ���������
        		                                                      .name("���̶���")
        		                                                      .addZipInputStream(zipInputStream)
                                                                      .deploy();//��ɲ���
        System.out.println("����ID"+deploy.getId());
        System.out.println("��������"+deploy.getId());
    }
	/**��������ʵ��**/
    @RequestMapping(value="/startProcess.do")
    public void startProcessInstance(@Param(value="testProcess1") String processKey){
    	processKey = "myProcess";
    	Map<String,Object> map = new HashMap<String,Object>();  
    	map.put("userIds", "admin,jjlin,jay");
    	ProcessInstance pi = processEngine.getRuntimeService()
    			                                                     .startProcessInstanceByKey(processKey,map); //ʹ�����̶����Key���������̡�key��Ӧ����HelloWord.bpmn���е�ID����.;ʹ��key�ķ�ʽ�������̣������ҵ����°汾�����̣���������,
          System.out.println("����ʵ��ID��"+pi.getId());
          System.out.println("���̶���ID��"+pi.getProcessDefinitionId());
//          System.out.println("����ʵ��ID��"+pi.getId());
//          System.out.println("����ʵ��ID��"+pi.getId());
    }
    /**��ѯ��ǰ����������**/
    @RequestMapping(value="findPersonalTask")
    @Test
    public void findMyPersonalTask(String assignee){
    	 assignee = "�ܽ���";
    	List<Task> list = processEngine.getTaskService()//�����������service
    	                         .createTaskQuery()//���������ѯ����
    	                         .taskAssignee(assignee) //ָ�����������ѯ��ָ��������
//    	                         .taskCandidateUser("")//������İ�����
//    	                         .processDefinitionId("���̶���ID��ѯ����")
//    	                         .processInstanceId("����ʵ��ID��ѯ����")
//    	                         .executionId("ִ��ID��ѯ����")
    	                         //����
    	                         .orderByTaskCreateTime().asc()
    	                         .list();//��������list����
//    	                         .count()
//    	                         .listPage(0, 10)
//    	                         .singleResult()
    	if(list != null && list.size()>0){
    		for(Task task:list){
    			System.out.println("����ID"+task.getId());
    			System.out.println("��������"+task.getName());
    			System.out.println("����Ĵ���ʱ��"+task.getCreateTime());
    			System.out.println("����İ�����"+task.getAssignee());
    			System.out.println("����ʵ��ID"+task.getProcessInstanceId());
    			System.out.println("���̶���ID"+task.getProcessDefinitionId());
    			System.out.println("����ִ�ж���ID"+task.getExecutionId());
    		}
    	}
    }
    /**
     * ��ɵ�����
     * @param taskId
     */
    @RequestMapping(value="completeMyProcessTask")
    public void completeMyProcessTask(String taskId){
    	taskId = "112503";
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("message", "��Ҫ");
    	//����ID
    	processEngine.getTaskService()
    	                        .complete(taskId, map);;
    	System.out.println("������ɣ�����ID��"+taskId);
    }
    /**
     * �鿴����ͼƬ
     * @param deploymentId
     */
    @RequestMapping(value="viewPic")
    public void viewPic(String deploymentId){
    	deploymentId = "42501";
    	//���ͼƬ��Դ����
    	List<String> list = processEngine.getRepositoryService()
    			                                              .getDeploymentResourceNames(deploymentId);
        //����ͼƬ��Դ����
    	String resourceName = "";
    	if(list != null && list.size()>0){
    		for(String name : list){
    			 if(name.indexOf(".png") >= 0){
    				 resourceName = name;
    			 }
    		}
    	}
    	//���ͼƬ��������
    	InputStream in = processEngine.getRepositoryService()
    			                                             .getResourceAsStream(deploymentId, resourceName);
    	//����ͼƬ���λ��
    	File file = new File("C:"+File.separator+resourceName);
    	try {
    		//����������д��������
			FileUtils.copyInputStreamToFile(in,file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * ��ѯ���̶���
     * @param request
     * @param response
     */
    @RequestMapping(value="findProcessDefinition")
    public void findProcessDefinition(HttpServletRequest request, HttpServletResponse response){
    	List<ProcessDefinition> list = processEngine.getRepositoryService()
    			                                                                 .createProcessDefinitionQuery()//����һ������ʵ����ѯ
//    			                                                                 .deploymentId("")//�������ID��ѯ
//    			                                                                .processDefinitionId("")//���̶���ID��ѯ
//    			                                                                .processDefinitionKey("")//���̶���KEY��ѯ
//    			                                                                .processDefinitionNameLike("")//���̶�������ģ����ѯ
    			                                                                 .orderByProcessDefinitionVersion().asc()//���汾��������
//    			                                                                 .orderByProcessDefinitionName().desc()//�����ƽ�������
    			                                                                 .list();//����һ����װ���̶���ļ���
//    	                                                                         .singleResult();//����Ψһ�����
//    			                                                                 .count();
//    	                                                                          .listPage(0, 10);//��ҳ��ѯ
        if(list != null && list.size()>0){
        	for(ProcessDefinition pd:list){
        		System.out.println("���̶���ID"+pd.getId());
        		System.out.println("���̶���NAME"+pd.getName());
        		System.out.println("���̶���KEY"+pd.getKey());
        		System.out.println("���̶���汾"+pd.getVersion());
        		System.out.println("��Դ����bpmn�ļ�"+pd.getResourceName());
        		System.out.println("��Դ����PNG�ļ�"+pd.getDiagramResourceName());
        		System.out.println("��Դ����ID"+pd.getDeploymentId());
        	}
        }
    }
    /**
     * ��ѯ���̶������汾
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
    	 * ��map�����key��ͬʱ�������ֵ�Ḳ��ǰ���ֵ
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
        		System.out.println("���̶���ID"+pd.getId());
        		System.out.println("���̶���NAME"+pd.getName());
        		System.out.println("���̶���KEY"+pd.getKey());
        		System.out.println("���̶���汾"+pd.getVersion());
        		System.out.println("��Դ����bpmn�ļ�"+pd.getResourceName());
        		System.out.println("��Դ����PNG�ļ�"+pd.getDiagramResourceName());
        		System.out.println("��Դ����ID"+pd.getDeploymentId());
        	}
        }
    }
    /**
     * ����������û����������ɾ���������
     * @param request
     * @param response
     */
    @RequestMapping(value="deleteDeployment")
    public void deleteDeployment(HttpServletRequest request,HttpServletResponse response){
    	    processEngine.getRepositoryService()
    	                             .deleteDeployment("deploymentId",true);
            System.out.println("ɾ���ɹ�!");
    }
    /**
     * ɾ�����а汾�����̶������08
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
     * ����ͼ״̬��ѯ
     * false:����true:������
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
     * ��ĳ������ڵ��������Ա
     * @param loginName
     * @param taskId
     */
    @RequestMapping(value="addUserForTask")
    public void addUserForTask(String loginName,String taskId){
    	//������������ӳ�Ա
    	 taskId ="22504";
    	 loginName ="admin";
    	processEngine.getTaskService()
    	             .addCandidateUser(taskId, loginName);
    }
    /**
     *  ������ʷ����ʵ��ͨ������ʵ��ID
     */
    public void findHistoryProcessInstance(HttpServletRequest request,HttpServletResponse response){
    	 String processInstanceId = "666";
    	 HistoricProcessInstance processInstance = processEngine.getHistoryService()
    			                                                                            .createHistoricProcessInstanceQuery()
    			                                                                            .processInstanceId(processInstanceId)
    			                                                                            .singleResult();
    	 System.out.println("��ʷ����ID"+processInstance.getId() + "���̶���ID" + processInstance.getProcessDefinitionId() + "��ʼʱ��" + processInstance.getStartTime() + "����ʱ��"+ processInstance.getEndTime()  + "��ʱ(��)" + processInstance.getDurationInMillis());
    }
    /**
     * ��ѯ��ʷ��������ͨ��ִ����
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
    	    	 System.out.println("��ʷ����ID"+processInstance.getId() + "���̶���ID" + processInstance.getProcessDefinitionId() + "��ʼʱ��" + processInstance.getStartTime() + "����ʱ��"+ processInstance.getEndTime()  + "��ʱ(��)" + processInstance.getDurationInMillis());
    		}
    	}
    }
}
