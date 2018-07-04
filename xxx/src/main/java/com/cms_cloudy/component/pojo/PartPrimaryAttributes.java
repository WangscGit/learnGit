package com.cms_cloudy.component.pojo;

import java.util.List;

/**字段定义表**/
public class PartPrimaryAttributes {

	private long id;
	/**
	 * 字段名称
	 */
	private String fieldName;
	/**
	 * 显示名称
	 */
	private String showName;
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 是否为空
	 */
    private String IsNull;
    /**
	 * 字段描述
	 */
    private String description;
    /**
	 * 是否可用
	 */
    private boolean isUsed;
    /**
	 * 是否显示
	 */
    private boolean isDisplay;
    /**
	 * 是否更新
	 */
    private boolean isUpdate;
    /**
	 * 是否稽核
	 */
    private boolean isAudit;
    /**
	 * 是否查找
	 */
    private boolean isSearch;
    /**
	 * 是否匹配
	 */
    private boolean isMatch;
    /**
     * 顺序号
     */
    private int seqNo;
    /**
     * 详细页属性分组 ( 0未分组  1基本属性 2质量属性 3设计属性 4采购属性)
     */
    private int type;
    /**
     * 是否添加
     */
    private boolean isInsert;
    /**
     * 是否流程申请时的添加字段
     */
    private boolean isApply;
    /**
     * 英文名
     */
    private String englishName;
    
    private List<FieldSelect> fsList;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getIsNull() {
		return IsNull;
	}
	public void setIsNull(String isNull) {
		IsNull = isNull;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	public boolean isDisplay() {
		return isDisplay;
	}
	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
	public boolean isUpdate() {
		return isUpdate;
	}
	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	public boolean isAudit() {
		return isAudit;
	}
	public void setAudit(boolean isAudit) {
		this.isAudit = isAudit;
	}
	public boolean isSearch() {
		return isSearch;
	}
	public void setSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}
	public boolean isMatch() {
		return isMatch;
	}
	public void setMatch(boolean isMatch) {
		this.isMatch = isMatch;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<FieldSelect> getFsList() {
		return fsList;
	}
	public void setFsList(List<FieldSelect> fsList) {
		this.fsList = fsList;
	}
	public boolean isInsert() {
		return isInsert;
	}
	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public boolean isApply() {
		return isApply;
	}
	public void setApply(boolean isApply) {
		this.isApply = isApply;
	}
	
}
