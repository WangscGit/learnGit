package org.activiti.rest.diagram.services;

import java.util.List;
import org.activiti.rest.diagram.pojo.ProcUser;
import org.activiti.rest.diagram.pojo.TaskUser;

public interface ITaskUserService {
	/** 获取任务人员信息 **/
	public List<TaskUser> selectTaskUserList(TaskUser param);

	/** 修改任务人员信息 **/
	public void updateTaskUser(TaskUser taskUser);

	/** 添加任务人员信息 **/
	public void insertTaskUser(TaskUser taskUser);

	/** 删除任务人员信息 **/
	public void deleteTaskUser(TaskUser taskUser);

	/** 根据任务ID删除任务人员信息 **/
	public void deleteTaskUserByTaskid(TaskUser taskUser);
	
	/** 获取流程配置人员信息 **/
	public List<ProcUser> selectProcUserList(ProcUser procUser);
	
	/** 添加流程配置人员信息 **/
	public void insertProcUser(ProcUser procUser);
	
	/** 删除流程配置人员信息 **/
	public void deleteProcUser(ProcUser procUser);
}
