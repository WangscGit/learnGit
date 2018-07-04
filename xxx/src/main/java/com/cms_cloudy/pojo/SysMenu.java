package com.cms_cloudy.pojo;

import java.util.Date;

/**菜单表1**/
public class SysMenu {
   
	/**主键ID**/
	private long fatherId;
	/**菜单名称**/
	private String menuName;
	/**菜单说明**/
	private String menuNote;
	/**创建人**/
	private String createuser;
	/**创建时间**/
	private Date createtime;
	/**更新人**/
	private String modifyuser;
	/**更新时间**/
	private Date modifytime;
	/**菜单URl**/
	private String cdefine1;
	/**是否可用**/
	private Integer cdefine2;
	/**备用字段3**/
	private String cdefine3;
	/**父菜单ID**/
	private Integer cdefine4;
	/**备用字段5**/
	private Date cdefine5;
	public long getFatherId() {
		return fatherId;
	}
	public void setFatherId(long fatherId) {
		this.fatherId = fatherId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuNote() {
		return menuNote;
	}
	public void setMenuNote(String menuNote) {
		this.menuNote = menuNote;
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
	public Integer getCdefine2() {
		return cdefine2;
	}
	public void setCdefine2(Integer cdefine2) {
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