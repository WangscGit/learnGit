package com.cms_cloudy.upload.pojo;

public class ExportExcel {

	private String id;
	/**字段名称**/
	private String fieldName;
	/**文件路径**/
	private String filePath;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
