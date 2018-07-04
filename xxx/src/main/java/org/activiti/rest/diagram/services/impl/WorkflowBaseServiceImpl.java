package org.activiti.rest.diagram.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.rest.diagram.dao.IWorkflowBaseDao;
import org.activiti.rest.diagram.dao.IWorlkflowMainEntityDao;
import org.activiti.rest.diagram.pojo.TaskUser;
import org.activiti.rest.diagram.pojo.WorkflowTaskEntitiy;
import org.activiti.rest.diagram.pojo.WorlkflowMainEntity;
import org.activiti.rest.diagram.services.ITaskUserService;
import org.activiti.rest.diagram.services.IWorkflowBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cms_cloudy.component.dao.IPartDataDao;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.UserService;

@Component(value = "IWorkflowBaseService")
public class WorkflowBaseServiceImpl implements IWorkflowBaseService {
	@Autowired
	private IWorkflowBaseDao workflowBaseDao;
	@Autowired
	private UserService userService;
	@Autowired
	private ITaskUserService taskUserService;
	@Autowired
	private IWorlkflowMainEntityDao iWorlkflowMainEntityDao;
	@Autowired
	private IPartDataDao iPartDataDao;
	// 流程引擎初始化
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	public void insertWorkflowMain(WorlkflowMainEntity processEntity) {
		workflowBaseDao.insertWorkflowMain(processEntity);
	}

	public Map<String, Object> selectBasicDeploymentProcess(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		WorlkflowMainEntity wfMain = new WorlkflowMainEntity();
		if (null != paramMap.get("searchMsg") && !"".equals(paramMap.get("searchMsg").toString())) {
			wfMain.setProcessKey(paramMap.get("searchMsg").toString());
		}
		List<WorlkflowMainEntity> listc = workflowBaseDao.selectBasicDeploymentProcess(wfMain);
		if (null == listc) {
			return null;
		} else {
			map.put("coun", listc.size());
		}
		List<WorlkflowMainEntity> list = workflowBaseDao.selectBasicDeploymentProcessByPage(paramMap);
		map.put("list", list);
		return map;
	}

	public List<ProcessDefinition> selectProcessDefinitionList(String deploymentId) {
		return workflowBaseDao.selectProcessDefinitionList(deploymentId);
	}

	public void updateWorkflowMain(WorlkflowMainEntity processEntity) {
		workflowBaseDao.updateWorkflowMain(processEntity);
	}

	public WorlkflowMainEntity selectWorkflowBaseServiceById(Long id) {
		return workflowBaseDao.selectWorkflowBaseServiceById(id);
	}

	public void insertWorkflowTaskEntitiy(WorkflowTaskEntitiy taskEntity) {
		workflowBaseDao.insertWorkflowTaskEntitiy(taskEntity);
	}

	public List<WorkflowTaskEntitiy> selectWorkflowTaskEntitiyList(WorkflowTaskEntitiy taskEntity) {
		return workflowBaseDao.selectWorkflowTaskEntitiyList(taskEntity);
	}

	public void updateWorkflowTaskEntity(WorkflowTaskEntitiy task) {
		workflowBaseDao.updateWorkflowTaskEntity(task);
	}

	public void updateWorkflowMainToState(WorlkflowMainEntity main) {
		workflowBaseDao.updateWorkflowMainToState(main);
	}

	public void updateWorkflowTaskEntitiyToState(WorkflowTaskEntitiy main) {
		workflowBaseDao.updateWorkflowTaskEntitiyToState(main);
	}

	public WorkflowTaskEntitiy selectWorkflowTaskServiceById(Long id) {
		return workflowBaseDao.selectWorkflowTaskServiceById(id);
	}

	public Map<String, Object> getProcessTask(WorkflowTaskEntitiy po, String state) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Task> taskList = new ArrayList<Task>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		long cou = 0;
		if (state.equals("0")) {// 待办任务
			Map<String, Object> map = workflowBaseDao.getProcessTask(po);
			taskList = (List<Task>) map.get("taskList");
			cou = (Long) map.get("cou");
		} else if (state.equals("1")) {// 已办任务
			Map<String, Object> map = workflowBaseDao.getFinishProcessTask(po);
			taskList = (List<Task>) map.get("taskList");
			cou = (Long) map.get("cou");
		}
		HistoryService historyService = processEngine.getHistoryService();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// Task转WorkflowTaskEntitiy
		List<WorkflowTaskEntitiy> wfList = new ArrayList<WorkflowTaskEntitiy>();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			if (task.isSuspended()) {// 挂起的流程不在待办页显示
				continue;
			}
			WorkflowTaskEntitiy wf = new WorkflowTaskEntitiy();
			wf.setPageNum(po.getPageNum());// 当前页
			wf.setPageSize(po.getPageSize());// 每页数量
			HistoricProcessInstance h = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(task.getProcessInstanceId()).singleResult();
			ProcessDefinition p = repositoryService.getProcessDefinition(task.getProcessDefinitionId());
			List<HrUser> ul = new ArrayList<HrUser>();
			if (StringUtils.isNotEmpty(h.getStartUserId())) {// 根据登录名获取人员信息
				ul = userService.selectUserByName(h.getStartUserId());
			}
			wf.setProcessCreatePerson(ul.size() > 0 ? ul.get(0).getUserName() : "");// 流程启动人
			wf.setProcessTaskStarttime(sdf.format(h.getStartTime()));// 流程发起时间
			wf.setProcessKey(p.getKey());
			wf.setTaskName(task.getName() == null ? "" : task.getName());
			wf.setCdefine5(task.getTaskDefinitionKey());
			// 获取任务的完成状态
			TaskUser queryTaskUser = new TaskUser();
			queryTaskUser.setTaskDefKey(task.getTaskDefinitionKey());
			queryTaskUser.setProcessInstId(task.getProcessInstanceId());
			queryTaskUser.setUserLoginName(po.getProcessAssignees());
			queryTaskUser.setActTaskId(task.getId());
			List<TaskUser> l = taskUserService.selectTaskUserList(queryTaskUser);
			if (l.size() > 0) {
				wf.setProcessTaskState(l.get(0).getIsFinish().equals("1") ? "已完成" : "待办");
			} else {
				wf.setProcessTaskState("");
			}
			// 获取当前任务的所有执行人，判断节点是否审批完
			queryTaskUser.setUserLoginName(null);
			l = taskUserService.selectTaskUserList(queryTaskUser);
			for (int j = 0; j < l.size(); j++) {
				TaskUser t = l.get(j);
				if (t.getUtType().equals("and")) {
					if (t.getIsFinish().equals("0")) {// and情况下有人未审批时，任务节点状态就展示正在审批
						wf.setProcessState("正在审批");
						break;
					} else {
						wf.setProcessState("审批完成");
					}
				}
				if (t.getUtType().equals("or") && t.getIsFinish().equals("1")) {
					wf.setProcessState("审批完成");
					break;
				}
			}
			wf.setId(Long.valueOf(task.getId()));
			wf.setCdefine2(task.getProcessInstanceId());
			wf.setProcessName(p.getName());// 流程名称
			wf.setWorkFlowName(p.getKey());
			// 获取业务数据，构建title，业务id
			WorlkflowMainEntity wme = new WorlkflowMainEntity();
			wme.setProcessDefinitionId(task.getProcessDefinitionId());
			WorlkflowMainEntity res = iWorlkflowMainEntityDao.getWfmByProdefId(wme);
			if (res != null) {
				wf.setTitle(res.getCdefine3());
			} else {
				wf.setTitle("");
			}
			if (res.getCdefine3().equals("0")) {// 元器件申请
				WorkflowTaskEntitiy wte = new WorkflowTaskEntitiy();
				wte.setCdefine2(task.getProcessInstanceId());
				WorkflowTaskEntitiy workTe = iWorlkflowMainEntityDao.getWteByProInsId(wte);
				if (workTe == null) {
					wf.setItem("");
				} else {
					Map<String, Object> map = iPartDataDao
							.selectPartDataById("select * from part_data where id=" + workTe.getCdefine4());
					if (map == null) {
						wf.setItem("");
					} else {
						wf.setItem(map.get("Part_Type").toString() + map.get("ITEM").toString());
					}
				}
			}
			wfList.add(wf);
		}
		resultMap.put("taskList", wfList);
		resultMap.put("cou", cou);
		return resultMap;
	}

	public List<WorlkflowMainEntity> selectProcessMain(WorlkflowMainEntity main) {
		return workflowBaseDao.selectProcessMain(main);
	}

	public void updateProcessMain(WorlkflowMainEntity main) {
		workflowBaseDao.updateProcessMain(main);
	}

	public void updateProcessTask(WorkflowTaskEntitiy task) {
		workflowBaseDao.updateProcessTask(task);
	}

	public Map<String, Object> selectProcessTaskByPage(Map<String, Object> map) {
		return workflowBaseDao.selectProcessTaskByPage(map);
	}

	public void deleteTask(Map<String, Object> map) {
		workflowBaseDao.deleteTask(map);
	}

	public Map<String, Object> countTaskByUser(Map<String, Object> paramMap) {
		return workflowBaseDao.countTaskByUser(paramMap);
	}

	public void deleteProcessTemplate(Map<String, Object> map) {
		workflowBaseDao.deleteProcessTemplate(map);
	}

	public void cleanProcessData(Map<String, Object> map) {
		workflowBaseDao.cleanProcessData(map);
	}

	public Map<String, Object> selectProcessInformationToExport(Map<String, Object> map) {
		return workflowBaseDao.selectProcessInformationToExport(map);
	}

	public void deleteProcessTask(WorkflowTaskEntitiy task) {
		workflowBaseDao.deleteProcessTask(task);
	}

	public long selectMaxId(String tableName) {
		return workflowBaseDao.selectMaxId(tableName);
	}

	public void updateTaskEntity(WorkflowTaskEntitiy task) {
		workflowBaseDao.updateTaskEntity(task);
	}
}
