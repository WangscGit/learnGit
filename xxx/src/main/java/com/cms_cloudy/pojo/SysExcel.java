package com.cms_cloudy.pojo;

import java.util.Date;

public class SysExcel {

	/**
	 * 员工编号
	 */
	private String employeeNumber;
	/**
	 * 用户编号
	 */
	private  String userNumber;
	/**
	 * 用户名(中文名)
	 */
	private String userName;
	/**
	 * 登录名(英文名)
	 */
	private String loginName;
	/**
	 * 职位
	 */
	private String position;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 电话
	 */
	private String telephone;
	/**
	 * 手机
	 */
	private String mobilePhone;
	/**
	 * 部门
	 */
	private String department;
	/**
	 * 创建人
	 */
	private String createuser;
	/**
	 * 创建 时间
	 */
	private Date createtime;;
	/**
	 * 是否离职(0：在职1：离职)
	 */
	private int isOrNot;
	public SysExcel(String string, String string2, String string3,
			String string4, String string5, String string6, String string7,
			String string8, String string9, String string10, Date date, int i) {
		this.employeeNumber=string;
		 this.userNumber=string2;
		this.userName=string3;
		this.loginName=string4;
		this.position=string5;
		this.email=string6;
		 this.telephone=string7;
	this.mobilePhone=string8;
		 this.department=string9;
		 this.createuser=string10;
		this.createtime=date;
		 this.isOrNot=i;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public int getIsOrNot() {
		return isOrNot;
	}
	public void setIsOrNot(int isOrNot) {
		this.isOrNot = isOrNot;
	}
	

	
}
