package org.activiti.rest.diagram.pojo;
public class ProcessConfigure {
	private Long id;
	//流程定义id
	private String processDefId;
	//流程分类信息
	private String processType;
	//基本属性是否显示 1是 0否
	private String normalAttrs;
	//质量属性是否显示 1是 0否
	private String qualityAttrs;
	//设计属性是否显示 1是 0否
	private String designAttrs;
	//采购属性是否显示 1是 0否
	private String purchaseAttrs;
	//审批节点key
	private String taskKey;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProcessDefId() {
		return processDefId;
	}
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getNormalAttrs() {
		return normalAttrs;
	}
	public void setNormalAttrs(String normalAttrs) {
		this.normalAttrs = normalAttrs;
	}
	public String getQualityAttrs() {
		return qualityAttrs;
	}
	public void setQualityAttrs(String qualityAttrs) {
		this.qualityAttrs = qualityAttrs;
	}
	public String getDesignAttrs() {
		return designAttrs;
	}
	public void setDesignAttrs(String designAttrs) {
		this.designAttrs = designAttrs;
	}
	public String getPurchaseAttrs() {
		return purchaseAttrs;
	}
	public void setPurchaseAttrs(String purchaseAttrs) {
		this.purchaseAttrs = purchaseAttrs;
	}
	public String getTaskKey() {
		return taskKey;
	}
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	
}
