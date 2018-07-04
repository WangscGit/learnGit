package com.cms_cloudy.component.pojo;

import java.util.Date;

/**元器件评价表**/
public class PartEvaluationEntity {

	private long id;
	/**
	 * 物料编码
	 */
	private String partNumber;
	/**
	 * 用户名(登录名)
	 */
	private String userName;
	/**
	 * 评论内容
	 */
	private String evaContent;
	/**
	 * 评论时间
	 */
	private Date createTime;
	/**
	 * 星级
	 */
	private int votes;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEvaContent() {
		return evaContent;
	}
	public void setEvaContent(String evaContent) {
		this.evaContent = evaContent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
}
