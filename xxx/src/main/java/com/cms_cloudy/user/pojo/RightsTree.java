package com.cms_cloudy.user.pojo;

import java.util.Date;

public class RightsTree {
	//id
	private int id;
	//权限名
	private String name;
	//权限标识
	private String rightsNote;
	//父节点id
	private int pId;
	//是否数据权限
	private boolean isDataRights;
	//菜单链接地址
	private String menuUrl;
	//添加时间
	private Date createDate;
	//添加人
	private String createUser;
	//修改人
	private String modifyUser;
	//修改时间
	private Date modifyDate;
	//是否选中
	private boolean checked;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRightsNote() {
		return rightsNote;
	}
	public void setRightsNote(String rightsNote) {
		this.rightsNote = rightsNote;
	}
	
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public boolean isDataRights() {
		return isDataRights;
	}
	public void setDataRights(boolean isDataRights) {
		this.isDataRights = isDataRights;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
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
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
