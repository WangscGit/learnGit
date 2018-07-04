package com.cms_cloudy.database.pojo;

import java.util.List;

public class PartTypeTree {
	private String partType;
	private long coun;
	private String parentNum;
	private String pnCode;
	private boolean tempPartMark;
	private String imgUrl;
	private List<PartTypeTree> partList;
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	public String getParentNum() {
		return parentNum;
	}
	public void setParentNum(String parentNum) {
		this.parentNum = parentNum;
	}
	public String getPnCode() {
		return pnCode;
	}
	public void setPnCode(String pnCode) {
		this.pnCode = pnCode;
	}
	public List<PartTypeTree> getPartList() {
		return partList;
	}
	public void setPartList(List<PartTypeTree> partList) {
		this.partList = partList;
	}
	public long getCoun() {
		return coun;
	}
	public void setCoun(long coun) {
		this.coun = coun;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parentNum == null) ? 0 : parentNum.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartTypeTree other = (PartTypeTree) obj;
		if (parentNum == null) {
			if (other.parentNum != null)
				return false;
		} else if (!parentNum.equals(other.parentNum))
			return false;
		return true;
	}
	public boolean isTempPartMark() {
		return tempPartMark;
	}
	public void setTempPartMark(boolean tempPartMark) {
		this.tempPartMark = tempPartMark;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
