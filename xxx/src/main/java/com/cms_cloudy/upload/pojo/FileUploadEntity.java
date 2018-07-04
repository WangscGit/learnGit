package com.cms_cloudy.upload.pojo;

import java.util.Date;

/**文件上传实体类**/
public class FileUploadEntity {

	private long id;
	/**
	 * 文件名称
	 */
	private String Name;
	/**
	 * 文件路径
	 */
	private String filePath;
	/**
	 * 文件大小
	 */
    private String Size;
    /**
     * 上传人
     */
    private String uploader;
    /**
     * 上传日期
     */
    private Date uploadDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSize() {
		return Size;
	}
	public void setSize(String size) {
		Size = size;
	}
}
