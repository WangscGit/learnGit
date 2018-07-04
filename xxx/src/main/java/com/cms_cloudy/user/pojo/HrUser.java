package com.cms_cloudy.user.pojo;

import java.util.Date;
/**用户表**/
public class HrUser {

	private long userId;
//	/**
//	 * 组Id
//	 */
//	private long groupId;
	/**
	 * 登录名(英文名)
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
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
	private Date createtime;
	/**
	 * 修改人
	 */
	private String modifyuser;
	/**
	 * 修改时间
	 */
	private Date modifytime;
	/**
	 * 是否离职(0：在职1：离职)
	 */
	private int isOrNot;
	/**
	 * 性别（0:男1：女）
	 */
	private int sex;
//	/**
//	 * 用户与用户组之间是多对多关系
//	 */
//	private List<HrGroup> groupList;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getModifyuser() {
		return modifyuser;
	}
	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}
	public Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	public int getIsOrNot() {
		return isOrNot;
	}
	public void setIsOrNot(int isOrNot) {
		this.isOrNot = isOrNot;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
//	public List<HrGroup> getGroupList() {
//		return groupList;
//	}
//	public void setGroupList(List<HrGroup> groupList) {
//		this.groupList = groupList;
//	}
//	public long getGroupId() {
//		return groupId;
//	}
//	public void setGroupId(long groupId) {
//		this.groupId = groupId;
//	}
}
