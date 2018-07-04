package com.cms_cloudy.component.pojo;

import java.util.List;

public class TreeTableEntity {

	private String id;
	private String parentId;
	private int type;
	private String attrName;
	private String attrContent;
	private String fieldName;
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 下拉列表值
	 */
	private List<FieldSelect> fsList;
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrContent() {
		return attrContent;
	}
	public void setAttrContent(String attrContent) {
		this.attrContent = attrContent;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public List<FieldSelect> getFsList() {
		return fsList;
	}
	public void setFsList(List<FieldSelect> fsList) {
		this.fsList = fsList;
	}
	
}
