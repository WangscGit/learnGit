package org.activiti.rest.diagram.pojo;

import java.util.Date;
import java.util.List;
/**
 * 一个个人任务对应多个TaskUser。
 */
public class TaskUser {
	
	private long id;
	/**
	 * 流程定义节点id
	 */
	private String taskDefKey;
	/**
	 * 流程实例id
	 */
	private String processInstId;
	
	/**
	 * 用户登录名
	 */
	private String userLoginName;
	/**
	 * 个人任务执行人员and、or关系，只有and、or两个值，单人时存or
	 */
	private String utType;
	/**
	 * 个人是否完成此userTask 0未完成  1已完成
	 */
	private String isFinish;
	/**
	 * 分配给个人任务的时间
	 */
	private Date createTime;
	/**
	 * 任务办理的完成时间
	 */
	private Date endTime;
	/**
	 * 任务表ID
	 */
	private Long taskId;
	/**
	 * 个人审批意见
	 */
	private String comment;
	/**
	 * 是否同意
	 */
	private String isAgree;
	/**
	 * 用户真实姓名
	 */
	private String userName;
	/**
	 * 流程表任务id（一个审批执同一个人执行多次时，用此字段加以区分）
	 */
	private String actTaskId;
	/**
	 * 是否本人完成的此项任务 0否 1是
	 */
	private String isOneself;
	/**
	 * 当前任务查询
	 */
	private List<String> queryList;
	
	
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
	public String getProcessInstId() {
		return processInstId;
	}
	public void setProcessInstId(String processInstId) {
		this.processInstId = processInstId;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public String getUtType() {
		return utType;
	}
	public void setUtType(String utType) {
		this.utType = utType;
	}
	public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getActTaskId() {
		return actTaskId;
	}
	public void setActTaskId(String actTaskId) {
		this.actTaskId = actTaskId;
	}
	public String getIsOneself() {
		return isOneself;
	}
	public void setIsOneself(String isOneself) {
		this.isOneself = isOneself;
	}
	public List<String> getQueryList() {
		return queryList;
	}
	public void setQueryList(List<String> queryList) {
		this.queryList = queryList;
	}
	
}
