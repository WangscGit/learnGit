package com.cms_cloudy.user.pojo;

import java.util.Date;
import java.util.List;

/**用户组表**/
public class HrGroup {
    
	private long groupId;
	/**
	 * 组名称
	 */
	private String groupName;
	/**
	 * 组状态 
	 */
     private Integer groupState;
     
     /**
 	 * 组编号 
 	 */
     private Integer groupIndex;
	/**
	 * 创建人
	 */
	private String createuser;
	/**
	 * 创建人姓名
	 */
	private String createuserName;
	/**
	 * 修改人姓名
	 */
	private String modifyuserName;
	/**
	 * 修改人
	 */
	private String modifyuser;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 修改时间
	 */
	private Date modifytime;
	/**
	 * 用户与用户组之间是多对多关系
	 */
	private List<HrUser> userList;
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getModifyuser() {
		return modifyuser;
	}
	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	public Integer getGroupState() {
		return groupState;
	}
	public void setGroupState(Integer groupState) {
		this.groupState = groupState;
	}
	public List<HrUser> getUserList() {
		return userList;
	}
	public void setUserList(List<HrUser> userList) {
		this.userList = userList;
	}
	public Integer getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}
	public String getCreateuserName() {
		return createuserName;
	}
	public void setCreateuserName(String createuserName) {
		this.createuserName = createuserName;
	}
	public String getModifyuserName() {
		return modifyuserName;
	}
	public void setModifyuserName(String modifyuserName) {
		this.modifyuserName = modifyuserName;
	}
	
}
