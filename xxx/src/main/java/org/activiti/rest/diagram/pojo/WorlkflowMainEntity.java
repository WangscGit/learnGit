package org.activiti.rest.diagram.pojo;

public class WorlkflowMainEntity {

	private long id;
	/**
	 * 流程定义ID
	 */
	private String processDefinitionId;
	/**
	 * 流程实例Id
	 */
	private String processInstanceId;
	/**
	 * 部署ID
	 */
	private String deploymentId;
	/**
	 * 流程任务ID
	 */
	private String processTaskId;
	/**
	 * modelID
	 */
	private String modelId;
	/**
	 * 流程名称
	 */
	private String processName;
	/**
	 * 流程启动的KEY
	 */
	private String processKey;
	/**
	 * 流程描述
	 */
	private String processDescription;
	/**
	 * 流程创建人
	 */
	private String processCreatePerson;
	/**
	 * 流程创建时间
	 */
	private String processCreateTime;
	/**
	 * 流程版本
	 */
	private String processVersion;
    /**
     * 流程状态
     */
	private String processState;
	/**
	 * 备用字段(XML名称)
	 */
	private String cdefine1;
	/**
	 * 备用字段(图片名称)
	 */
	private String cdefine2;
	/**
	 * 流程模版绑定流程表单
	 */
	private String cdefine3;
	/**
	 * 备用字段
	 */
	private String cdefine4;
	/**
	 * 备用字段
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
	/**
	 * 冗余字段
	 */
	//流程类别标识
	private String categorySign;
//	/**
//	 * 备用字段
//	 */
//	private Date cdefine9;
//	/**
//	 * 备用字段
//	 */
//	private Date cdefine10;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessState() {
		return processState;
	}
	public void setProcessState(String processState) {
		this.processState = processState;
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
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getProcessTaskId() {
		return processTaskId;
	}
	public void setProcessTaskId(String processTaskId) {
		this.processTaskId = processTaskId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public String getProcessDescription() {
		return processDescription;
	}
	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}
	public String getProcessCreateTime() {
		return processCreateTime;
	}
	public void setProcessCreateTime(String processCreateTime) {
		this.processCreateTime = processCreateTime;
	}
	public String getProcessVersion() {
		return processVersion;
	}
	public void setProcessVersion(String processVersion) {
		this.processVersion = processVersion;
	}
	public String getProcessCreatePerson() {
		return processCreatePerson;
	}
	public void setProcessCreatePerson(String processCreatePerson) {
		this.processCreatePerson = processCreatePerson;
	}
	public String getCategorySign() {
		return categorySign;
	}
	public void setCategorySign(String categorySign) {
		this.categorySign = categorySign;
	}
}
