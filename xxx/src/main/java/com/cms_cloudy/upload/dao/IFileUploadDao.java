package com.cms_cloudy.upload.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cms_cloudy.upload.pojo.FileUploadEntity;

/**
* 文件上传dao
* @author WangSc
*/
public interface IFileUploadDao {

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
	/**根绝选择节点加载上传数据 */
	public List<FileUploadEntity> selectFileByPath(@Param(value="sql")String sql);
	/**上传文件表查询----排序方式：名称**/
	public List<FileUploadEntity> selectFileOrderByName(Map<String, Object> map);
	/**查询最新上传的一条数据**/
	public  FileUploadEntity selectLatestData(Map<String, Object> map);
}
