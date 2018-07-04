package org.activiti.rest.diagram.pojo;

public class ProcUser {
	private long id;
	/**
	 * 流程定义节点id
	 */
	private String taskDefKey;
	/**
	 * 流程定义id
	 */
	private String procDefId;
	/**
	 * 用户登录名
	 */
	private String loginName;
	/**
	 * 用户真实姓名
	 */
	private String userName;
	
	/**
	 * 个人任务执行人员and、or关系，只有and、or两个值，单人时存or
	 */
	private String utType;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUtType() {
		return utType;
	}
	public void setUtType(String utType) {
		this.utType = utType;
	}
	
}
