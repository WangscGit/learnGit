package org.activiti.rest.diagram.services;

import org.activiti.rest.diagram.pojo.WorkflowTaskEntitiy;
import org.activiti.rest.diagram.pojo.WorlkflowMainEntity;

public interface IWorlkflowMainEntityService {
	/**
	 * 通过流程定义id获取WorlkflowMainEntity 
	 */
	public WorlkflowMainEntity getWfmByProdefId(WorlkflowMainEntity wme);
	/**
	 * 通过流程实例id获取WorkflowTaskEntitiy 
	 */
	public WorkflowTaskEntitiy getWteByProInsId(WorkflowTaskEntitiy wte);
	
	/**
	 * 通过流程实例ID(实体类ID)查询任务表
	 * @param wte
	 * @return
	 */
	public WorkflowTaskEntitiy getWteByProInstanceId(WorkflowTaskEntitiy wte);
}
