package com.cms_cloudy.user.pojo;

import java.util.Date;
/**职位表**/
public class HrPosition {

	private long id;
	/**
	 * 职位名称
	 */
	private String positionName;
    /**
     * 职位描述
     */
	private String positionRemark;
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
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPositionRemark() {
		return positionRemark;
	}
	public void setPositionRemark(String positionRemark) {
		this.positionRemark = positionRemark;
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
