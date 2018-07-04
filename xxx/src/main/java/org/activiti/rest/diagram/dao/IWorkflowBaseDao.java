package org.activiti.rest.diagram.dao;

import java.util.List;
import java.util.Map;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.rest.diagram.pojo.WorkflowTaskEntitiy;
import org.activiti.rest.diagram.pojo.WorlkflowMainEntity;

public interface IWorkflowBaseDao {
	
	  public String getDefXmlByDeployId(String deployId);
	  /**存储流程信息**/
	  public void insertWorkflowMain(WorlkflowMainEntity processEntity);
	  /**查询流程部署信息**/
	  public List<WorlkflowMainEntity> selectBasicDeploymentProcess(WorlkflowMainEntity processEntity);
	  /**流程定义表查询**/
	  public List<ProcessDefinition> selectProcessDefinitionList(String deploymentId);
	  /**更新流程存储表**/
	  public void updateWorkflowMain(WorlkflowMainEntity processEntity);
	  /**根据ID查询流程主表**/
	  public WorlkflowMainEntity selectWorkflowBaseServiceById(Long id);
	  /**添加任务流程表**/
      public void insertWorkflowTaskEntitiy(WorkflowTaskEntitiy taskEntity);
      /**流程任务查询**/
      public List<WorkflowTaskEntitiy> selectWorkflowTaskEntitiyList(WorkflowTaskEntitiy taskEntity);
      /**更新流程任务表**/
      public void updateWorkflowTaskEntity(WorkflowTaskEntitiy task);
      /**更新流程状态(已完成)**/
      public void updateWorkflowMainToState(WorlkflowMainEntity main);
      /**更新流程状态(已办)**/
      public void updateWorkflowTaskEntitiyToState(WorkflowTaskEntitiy main);
      /**根据ID查询流程任务表**/
	  public WorkflowTaskEntitiy selectWorkflowTaskServiceById(Long id);
	  
	  public List<WorlkflowMainEntity> selectBasicDeploymentProcessByPage(Map<String,Object> paramMap);
	  /**查询流程模版**/
      public List<WorlkflowMainEntity> selectProcessMain(WorlkflowMainEntity main);
      /**修改流程模版**/
      public void updateProcessMain(WorlkflowMainEntity main);
      /**修改流程任务**/
      public void updateProcessTask(WorkflowTaskEntitiy task);
      /**分页查询流程管理信息**/
      public Map<String,Object> selectProcessTaskByPage(Map<String,Object> map);
      /**待办任务列表**/
      public Map<String, Object> getProcessTask(WorkflowTaskEntitiy po);
      /**流程管理----作废**/
      public void deleteTask(Map<String,Object> map);
      /**获取已完成任务**/
      public Map<String, Object> getFinishProcessTask(WorkflowTaskEntitiy po);
      /**删除流程模板**/
      public void deleteProcessTemplate(Map<String,Object> map);
      /**清除流程数据**/
      public void cleanProcessData(Map<String,Object> map);
      /**流程监控统计图**/
      public Map<String,Object> countTaskByUser(Map<String,Object> paramMap);
      /**流程信息下载**/
      public Map<String,Object> selectProcessInformationToExport(Map<String,Object> map);
      /**删除流程任务**/
      public void deleteProcessTask(WorkflowTaskEntitiy task);
      /**查询最大的ID**/
      public long selectMaxId(String tableName);
      /**更新任务信息**/
      public void updateTaskEntity(WorkflowTaskEntitiy task);
      /**通过业务ID删除流程任务**/
      public void deleteProcessTaskDetail(Map<String,Object> map);
}
