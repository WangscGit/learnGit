package org.activiti.rest.diagram.pojo;

import java.io.Serializable;

public class ProcessEntity implements Serializable{

	private static final long serialVersionUID = 6757393795687480331L;//可以在设置好javabean流程变量后在添加其他属性：如sex
	private Integer id;
	private String name;
	private Integer sex;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
