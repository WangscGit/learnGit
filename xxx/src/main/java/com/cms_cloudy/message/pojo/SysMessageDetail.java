package com.cms_cloudy.message.pojo;

import java.util.Date;

/**
 * 消息阅读状态表
 * 
 * @author Administrator
 *
 */
public class SysMessageDetail {

	private long id;

	/**
	 * 消息主表ID
	 */
	private long msgMainId;

	/**
	 * 消息接收人
	 */
	private String receiver;

	/**
	 * 阅读状态 0未读 1已读
	 */
	private Integer state;

	/**
	 * 阅读时间
	 */
	private Date readTime;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMsgMainId() {
		return msgMainId;
	}

	public void setMsgMainId(long msgMainId) {
		this.msgMainId = msgMainId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

}
