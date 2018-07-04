package com.cms_cloudy.pojo;

import java.util.Date;

/**页面元素表**/
public class SysMenuElement {

	/**主键ID**/
	private long childId;
	/**备用字段0**/
	private long fatherId;
	/**页面元素编码**/
	private String pageElement;
	/**说明**/
	private String elementNote;
	/**创建人**/
	private String createuser;
	/**创建时间**/
	private Date createtime;
	/**更新人**/
	private String modifyuser;
	/**更新时间**/
	private Date modifytime;
	/**备用字段1**/
	private String cdefine1;
	/**备用字段2**/
	private String cdefine2;
	/**备用字段3**/
	private String cdefine3;
	/**备用字段4**/
	private Integer cdefine4;
	/**备用字段5**/
	private Date cdefine5;
	public long getChildId() {
		return childId;
	}
	public void setChildId(long childId) {
		this.childId = childId;
	}
	public long getFatherId() {
		return fatherId;
	}
	public void setFatherId(long fatherId) {
		this.fatherId = fatherId;
	}

	public String getPageElement() {
		return pageElement;
	}
	public void setPageElement(String pageElement) {
		this.pageElement = pageElement;
	}
	public String getElementNote() {
		return elementNote;
	}
	public void setElementNote(String elementNote) {
		this.elementNote = elementNote;
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
	public String getCdefine1() {
		return cdefine1;
	}
	public void setCdefine1(String cdefine1) {
		this.cdefine1 = cdefine1;
	}
	public String getCdefine2() {
		return cdefine2;
	}
	public void setCdefine2(String cdefine2) {
		this.cdefine2 = cdefine2;
	}
	public String getCdefine3() {
		return cdefine3;
	}
	public void setCdefine3(String cdefine3) {
		this.cdefine3 = cdefine3;
	}
	public Integer getCdefine4() {
		return cdefine4;
	}
	public void setCdefine4(Integer cdefine4) {
		this.cdefine4 = cdefine4;
	}
	public Date getCdefine5() {
		return cdefine5;
	}
	public void setCdefine5(Date cdefine5) {
		this.cdefine5 = cdefine5;
	}
}
