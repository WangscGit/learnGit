package com.cms_cloudy.user.pojo;

public class ParameterConfig {
	//id
	private int id;
	//系统参数名
	private String parameterName;
	//系统参数值
	private String parameterValue;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}
