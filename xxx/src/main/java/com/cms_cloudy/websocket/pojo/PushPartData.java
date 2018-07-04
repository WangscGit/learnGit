package com.cms_cloudy.websocket.pojo;

public class PushPartData {

	private long id;
	/**
	 * 器件ID
	 */
	private long partId;
	/**
	 * 用户ID
	 */
	private long userId;
	/**
	 * 搜索框内容
	 */
	private String inputContent;
	/**
	 * 数据顺序号
	 */
	private int seqNo;
	/**
	 * 数据来源(0:搜索框;1:器件构建(推送);2:最多浏览)
	 */
	private char type;
	/**
	 * 搜索次数
	 */
	private int times;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPartId() {
		return partId;
	}
	public void setPartId(long partId) {
		this.partId = partId;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public String getInputContent() {
		return inputContent;
	}
	public void setInputContent(String inputContent) {
		this.inputContent = inputContent;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
