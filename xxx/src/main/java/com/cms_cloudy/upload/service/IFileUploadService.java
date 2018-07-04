package com.cms_cloudy.upload.service;

import java.util.List;
import java.util.Map;

import com.cms_cloudy.upload.pojo.FileUploadEntity;
import com.github.pagehelper.PageInfo;
/**
 * 文件上传service
 * @author WangSc
 */
public interface IFileUploadService {

	/**分页查询文件表**/
    public PageInfo<FileUploadEntity> selectFileUploadListByPage(Map<String,Object> map);
	/**查询上传文件列表**/
	public List<FileUploadEntity> selectFileUploadList(Map<String,Object> map);
   /**文件上传**/
	public void inserttFileUpload(FileUploadEntity file);
   /**上传文件删除**/
	public int deleteFileUpload(int id);
	/**上传文件的修改1**/
	public void updateFileUpload(FileUploadEntity file);
	/**上传文件的修改2**/
	public void updateFileUploadByName(Map<String,Object> updateMap);
	/**根据名称查询上传文件信息**/
	public List<FileUploadEntity>  selectFileUploadListByName(Map<String,Object> map);
	/**文件分类查询**/
	public List<FileUploadEntity> selectFileByPath(List<Object> str);
	/**上传文件表查询----排序方式：名称**/
	public List<FileUploadEntity> selectFileOrderByName(Map<String, Object> map);
	/**查询最新上传的一条数据**/
	public  FileUploadEntity selectLatestData(Map<String, Object> map);
}
