package com.cms_cloudy.database.pojo;


/**元器件分类表(旧CMS物料编码定义主表)**/
public class PartClass {

	private long id;
	/**
	 * 父级编码
	 */
	private String parentNum;
	/**
	 * 子级编码
	 */
	private String childNum;
	/**
	 * 编码代号
	 */
	private String pnCode;
	/**
	 * 物料类型
	 */
	private String partType;
	/**
	 * 英文名--用于物料编码
	 */
	private String enName;
	/**
	 * 目录内外
	 */
	private boolean tempPartMark;
	/**
	 * 图片id
	 */
	private long imgId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getParentNum() {
		return parentNum;
	}
	public void setParentNum(String parentNum) {
		this.parentNum = parentNum;
	}
	public String getChildNum() {
		return childNum;
	}
	public void setChildNum(String childNum) {
		this.childNum = childNum;
	}
	public String getPnCode() {
		return pnCode;
	}
	public void setPnCode(String pnCode) {
		this.pnCode = pnCode;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	public boolean isTempPartMark() {
		return tempPartMark;
	}
	public void setTempPartMark(boolean tempPartMark) {
		this.tempPartMark = tempPartMark;
	}
	public long getImgId() {
		return imgId;
	}
	public void setImgId(long imgId) {
		this.imgId = imgId;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	
}
