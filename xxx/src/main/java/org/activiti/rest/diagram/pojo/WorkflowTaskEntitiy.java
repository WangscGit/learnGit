package org.activiti.rest.diagram.pojo;

import java.util.Date;

/**
 * 对应流程实例
 *
 */
public class WorkflowTaskEntitiy {
	private long id;
	/**
	 * 流程主表ID
	 */
	private long processMainId;
	/**
	 * 流程节点名称
	 */
	private String processNodeTaskname;
	/**
	 * 流程任务状态
	 */
	private String processTaskState;
	/**
	 * 流程发起人
	 */
	private String processTaskPerson;
	/**
	 * 流程执行人
	 */
	private String processAssignees;
	/**
	 * 流程指派组(角色)
	 */
	private String processGroups;
	/**
	 * 流程发起时间
	 */
	private String processTaskStarttime;
	/**
	 * 流程到期时间
	 */
	private String processTaskExpirtime;
	/**
	 * 流程过程按钮
	 */
	private String processButton;
	/**
	 * 流程执行URL
	 */
	private String processUrl;
	/**
	 * 审批意见
	 */
	private String processAproveAdvice;
	/**
	 * 任务ID
	 */
	private String cdefine1;
	/**
	 * 流程实例ID
	 */
	private String cdefine2;
	/**
	 * 流程定义ID
	 */
	private String cdefine3;
	/**
	 * 器件申请主表ID
	 */
	private String cdefine4;
	/**
	 * ProcessTaskDefinitionKey 流程节点key
	 */
	private String cdefine5;
	/**
	 * 备用字段
	 */
	private int cdefine6;
	/**
	 * 备用字段
	 */
	private int cdefine7;
	/**
	 * 备用字段
	 */
	private int cdefine8;
	/*************** 冗余字段 **********/
	private String processKey;
	private String processName;
	// 当前页
	private int pageNum;
	// 每页记录数
	private int pageSize;
	// 任务创建时间起始
	private Date createTimeBegin;
	// 任务创建时间结束
	private Date createTimeFinish;
	// 流程名ProcessDefinitionKey
	private String workFlowName;
	// 流程启动人
	private String processCreatePerson;
	// 任务名称
	private String taskName;
	// 流程分类信息
	private String title;
	// 任务创建时间
	private String createTime;
	// 任务结束时间
	private String endTime;
	// 流程状态
	private String processState;
	// 流程当前时间最后一个审批人
	private String lastUserName;
	// 流程当前时间最后审批时间
	private String lastTime;
	// 元器件规格型号
	private String item;
	// 流程类别
	private String procesCategory;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProcessMainId() {
		return processMainId;
	}

	public void setProcessMainId(long processMainId) {
		this.processMainId = processMainId;
	}

	public String getProcessNodeTaskname() {
		return processNodeTaskname;
	}

	public void setProcessNodeTaskname(String processNodeTaskname) {
		this.processNodeTaskname = processNodeTaskname;
	}

	public String getProcessTaskState() {
		return processTaskState;
	}

	public void setProcessTaskState(String processTaskState) {
		this.processTaskState = processTaskState;
	}

	public String getProcessTaskPerson() {
		return processTaskPerson;
	}

	public void setProcessTaskPerson(String processTaskPerson) {
		this.processTaskPerson = processTaskPerson;
	}

	public String getProcessAssignees() {
		return processAssignees;
	}

	public void setProcessAssignees(String processAssignees) {
		this.processAssignees = processAssignees;
	}

	public String getProcessGroups() {
		return processGroups;
	}

	public void setProcessGroups(String processGroups) {
		this.processGroups = processGroups;
	}

	public String getProcessTaskStarttime() {
		return processTaskStarttime;
	}

	public void setProcessTaskStarttime(String processTaskStarttime) {
		this.processTaskStarttime = processTaskStarttime;
	}

	public String getProcessTaskExpirtime() {
		return processTaskExpirtime;
	}

	public void setProcessTaskExpirtime(String processTaskExpirtime) {
		this.processTaskExpirtime = processTaskExpirtime;
	}

	public String getProcessButton() {
		return processButton;
	}

	public void setProcessButton(String processButton) {
		this.processButton = processButton;
	}

	public String getProcessUrl() {
		return processUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	public String getProcessAproveAdvice() {
		return processAproveAdvice;
	}

	public void setProcessAproveAdvice(String processAproveAdvice) {
		this.processAproveAdvice = processAproveAdvice;
	}

	public String getCdefine1() {
		return cdefine1;
	}

	public void setCdefine1(String cdefine1) {
		this.cdefine1 = cdefine1;
	}

	public String getCdefine2() {
		return cdefine2;
	}

	public void setCdefine2(String cdefine2) {
		this.cdefine2 = cdefine2;
	}

	public String getCdefine3() {
		return cdefine3;
	}

	public void setCdefine3(String cdefine3) {
		this.cdefine3 = cdefine3;
	}

	public String getCdefine4() {
		return cdefine4;
	}

	public void setCdefine4(String cdefine4) {
		this.cdefine4 = cdefine4;
	}

	public String getCdefine5() {
		return cdefine5;
	}

	public void setCdefine5(String cdefine5) {
		this.cdefine5 = cdefine5;
	}

	public int getCdefine6() {
		return cdefine6;
	}

	public void setCdefine6(int cdefine6) {
		this.cdefine6 = cdefine6;
	}

	public int getCdefine7() {
		return cdefine7;
	}

	public void setCdefine7(int cdefine7) {
		this.cdefine7 = cdefine7;
	}

	public int getCdefine8() {
		return cdefine8;
	}

	public void setCdefine8(int cdefine8) {
		this.cdefine8 = cdefine8;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessCreatePerson() {
		return processCreatePerson;
	}

	public void setProcessCreatePerson(String processCreatePerson) {
		this.processCreatePerson = processCreatePerson;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Date getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public Date getCreateTimeFinish() {
		return createTimeFinish;
	}

	public void setCreateTimeFinish(Date createTimeFinish) {
		this.createTimeFinish = createTimeFinish;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public String getLastUserName() {
		return lastUserName;
	}

	public void setLastUserName(String lastUserName) {
		this.lastUserName = lastUserName;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getProcesCategory() {
		return procesCategory;
	}

	public void setProcesCategory(String procesCategory) {
		this.procesCategory = procesCategory;
	}
}
