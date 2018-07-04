package com.cms_cloudy.user.pojo;

import java.util.Date;

public class HrRights {
	//id
	private int id;
	//权限名
	private String rightsName;
	//权限标识
	private String rightsNote;
	//父节点id
	private int parentId;
	//是否数据权限
	private boolean isDataRights;
	//菜单链接地址
	private String url;
	//添加时间
	private Date createDate;
	//添加人
	private String createUser;
	//修改人
	private String modifyUser;
	//修改时间
	private Date modifyDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRightsName() {
		return rightsName;
	}
	public void setRightsName(String rightsName) {
		this.rightsName = rightsName;
	}
	public String getRightsNote() {
		return rightsNote;
	}
	public void setRightsNote(String rightsNote) {
		this.rightsNote = rightsNote;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public boolean isDataRights() {
		return isDataRights;
	}
	public void setDataRights(boolean isDataRights) {
		this.isDataRights = isDataRights;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}
