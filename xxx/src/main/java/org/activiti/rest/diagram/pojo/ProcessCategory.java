package org.activiti.rest.diagram.pojo;

import java.util.Date;

/**
 * 流程类别
 * 
 * @author Administrator
 */
public class ProcessCategory {

	private long id;
	/**
	 * 流程类别标识
	 */
	private String categorySign;
	/**
	 * 流程类别名称
	 */
	private String categoryName;
	/**
	 * 创建人
	 */
	private String createPerson;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 备用字段1
	 */
	private String cdefine1;
	/**
	 * 备用字段2
	 */
	private String cdefine2;
	/**
	 * 备用字段3
	 */
	private String cdefine3;
	/**
	 * 备用字段4
	 */
	private int cdefine4;
	/**
	 * 备用字段5
	 */
	private Date cdefine5;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategorySign() {
		return categorySign;
	}

	public void setCategorySign(String categorySign) {
		this.categorySign = categorySign;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public int getCdefine4() {
		return cdefine4;
	}

	public void setCdefine4(int cdefine4) {
		this.cdefine4 = cdefine4;
	}

	public Date getCdefine5() {
		return cdefine5;
	}

	public void setCdefine5(Date cdefine5) {
		this.cdefine5 = cdefine5;
	}
}
