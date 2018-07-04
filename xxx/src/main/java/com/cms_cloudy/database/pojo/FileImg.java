package com.cms_cloudy.database.pojo;

import java.util.Date;

public class FileImg {
	private long id;
	//图片在项目中的路径
	private String imgUrl;
	//图片存储时名字
	private String imgName;
	//上传时图片名字
	private String imgSname;
	//添加人
	private String createUser;
	//添加时间
	private Date createTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgSname() {
		return imgSname;
	}
	public void setImgSname(String imgSname) {
		this.imgSname = imgSname;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
