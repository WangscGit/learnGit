package com.cms_cloudy.user.pojo;

/**用户组与用户关系表**/
public class HrGroupUser {

	private int id;
	/**
	 * 组ID
	 */
	private int groupId;
	/**
	 * 用户ID
	 */
	private int userId;
	/**
	 * 组名称
	 */
	private String groupName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
