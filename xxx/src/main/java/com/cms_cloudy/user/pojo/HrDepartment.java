package com.cms_cloudy.user.pojo;

import java.util.Date;

/**部门信息**/
public class HrDepartment {

	private long id;
    /**
     * 部门名称
     */
	private String departmentName;
	/**
	 * 部门编号
	 */
	private String departmentNo;
	/**
	 * 部门主管
	 */
	private String departmentMaster;
	/**
	 * 部门描述
	 */
	private String departmentDescript;
	/**
	 * 创建人
	 */
	private String createuser;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}
	public String getDepartmentMaster() {
		return departmentMaster;
	}
	public void setDepartmentMaster(String departmentMaster) {
		this.departmentMaster = departmentMaster;
	}
	public String getDepartmentDescript() {
		return departmentDescript;
	}
	public void setDepartmentDescript(String departmentDescript) {
		this.departmentDescript = departmentDescript;
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
}
