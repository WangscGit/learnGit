package com.cms_cloudy.component.pojo;

import java.util.Date;

/**常用库(我的收藏)表**/
public class UserPn {

	private long id;
	/****
	 * 用户ID
	 */
	private long userId;
	/**
	 * 物料编码
	 */
	private String partNumber;
	/**
	 * 收藏时间
	 */
	private Date selectedTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public Date getSelectedTime() {
		return selectedTime;
	}
	public void setSelectedTime(Date selectedTime) {
		this.selectedTime = selectedTime;
	}
}
