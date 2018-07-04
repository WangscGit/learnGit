package com.cms_cloudy.message.pojo;

import java.util.Date;

/**
 * 消息表
 * @author Administrator
 *
 */
public class SysMessage {

	private long id;
	/**
	 * 消息类型
	 */
	private String msgType;
	/**
	 * 消息标题
	 */
	private String msgTittle;
	/**
	 * 消息内容
	 */
	private String msgContent;
	/**
	 * 消息优先级
	 */
	private String msgLevel;
	/**
	 * 发起人
	 */
	private String launchPerson;
	/**
	 * 接收人
	 */
	private String receiverPerson;
	/**
	 * 是否阅读
	 */
	private boolean whetherRead;
	/**
	 * 发起时间
	 */
	private Date launchTime;
	/**
	 * 阅读时间
	 */
	private Date readTime;
	/**
	 * 跳转路径
	 */
	private String cdefine1;
	/**
	 * 修改人
	 */
	private String cdefine2;
	/**
	 * 流程任务实例ID
	 */
	private String cdefine3;
	/**
	 * 备用字段4
	 */
	private  int cdefine4;
	/**
	 * 修改时间
	 */
	private Date cdefine5;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgTittle() {
		return msgTittle;
	}
	public void setMsgTittle(String msgTittle) {
		this.msgTittle = msgTittle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgLevel() {
		return msgLevel;
	}
	public void setMsgLevel(String msgLevel) {
		this.msgLevel = msgLevel;
	}
	public String getLaunchPerson() {
		return launchPerson;
	}
	public void setLaunchPerson(String launchPerson) {
		this.launchPerson = launchPerson;
	}
	public String getReceiverPerson() {
		return receiverPerson;
	}
	public void setReceiverPerson(String receiverPerson) {
		this.receiverPerson = receiverPerson;
	}
	public boolean isWhetherRead() {
		return whetherRead;
	}
	public void setWhetherRead(boolean whetherRead) {
		this.whetherRead = whetherRead;
	}
	public Date getLaunchTime() {
		return launchTime;
	}
	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
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
