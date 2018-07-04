package org.activiti.rest.diagram.services.impl;

import java.util.List;

import org.activiti.rest.diagram.dao.ITaskUserDao;
import org.activiti.rest.diagram.pojo.ProcUser;
import org.activiti.rest.diagram.pojo.TaskUser;
import org.activiti.rest.diagram.services.ITaskUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("iTaskUserService")
public class TaskUserServiceImpl implements ITaskUserService {
	@Autowired
	private ITaskUserDao iTaskUserDao;
	public List<TaskUser> selectTaskUserList(TaskUser param) {
		return iTaskUserDao.selectTaskUserList(param);
	}

	public void updateTaskUser(TaskUser taskUser) {
		iTaskUserDao.updateTaskUser(taskUser);
	}

	public void insertTaskUser(TaskUser taskUser) {
		iTaskUserDao.insertTaskUser(taskUser);
	}

	public void deleteTaskUser(TaskUser taskUser) {
		iTaskUserDao.deleteTaskUser(taskUser);
	}

	public void deleteTaskUserByTaskid(TaskUser taskUser) {
		iTaskUserDao.deleteTaskUserByTaskid(taskUser);
	}

	public List<ProcUser> selectProcUserList(ProcUser procUser) {
		return iTaskUserDao.selectProcUserList(procUser);
	}

	public void insertProcUser(ProcUser procUser) {
		iTaskUserDao.insertProcUser(procUser);
	}

	public void deleteProcUser(ProcUser procUser) {
		iTaskUserDao.deleteProcUser(procUser);
	}

}
