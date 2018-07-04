package com.cms_cloudy.pojo;

/**菜单权限关系表**/
public class SysMenuRelation {

	/**主键ID**/
	private long id;
	/**字菜单表ID**/
	private long childId;
	/**组表ID**/
	private long groupId;
	/**备注**/
	private String remark;
	/**备用字段1**/
	private String cdefine1;
	/**备用字段2**/
	private Integer cdefine2;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getChildId() {
		return childId;
	}
	public void setChildId(long childId) {
		this.childId = childId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCdefine1() {
		return cdefine1;
	}
	public void setCdefine1(String cdefine1) {
		this.cdefine1 = cdefine1;
	}
	public Integer getCdefine2() {
		return cdefine2;
	}
	public void setCdefine2(Integer cdefine2) {
		this.cdefine2 = cdefine2;
	}
}
