package com.cms_cloudy.upload.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cms_cloudy.upload.dao.IFileUploadDao;
import com.cms_cloudy.upload.pojo.FileUploadEntity;
import com.cms_cloudy.upload.service.IFileUploadService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Component("IFileUploadService")
public class FileUploadServiceImpl implements IFileUploadService {

	@Autowired
	private IFileUploadDao fileUploadDao;
	@Resource  
    public JdbcTemplate jdbcTemplate; 
	public List<FileUploadEntity> selectFileUploadList(Map<String, Object> map) {
		return fileUploadDao.selectFileUploadList(map);
	}

	public void inserttFileUpload(FileUploadEntity file) {
		fileUploadDao.inserttFileUpload(file);
	}

	public int deleteFileUpload(int id) {
		return fileUploadDao.deleteFileUpload(id);
	}

	public PageInfo<FileUploadEntity> selectFileUploadListByPage(Map<String, Object> map) {
		PageHelper.startPage(Integer.valueOf(map.get("pageNo").toString()), Integer.valueOf(map.get("pageSize").toString()));
		List<FileUploadEntity> fileList = selectFileUploadList(map);
		if(fileList.size()>0){
			PageInfo<FileUploadEntity> page = new PageInfo<FileUploadEntity>(fileList);
			return page;
		}
		return null;
	}

	public void updateFileUpload(FileUploadEntity file) {
		fileUploadDao.updateFileUpload(file);
	}

	public List<FileUploadEntity> selectFileUploadListByName(Map<String, Object> map) {
		return fileUploadDao.selectFileUploadListByName(map);
	}

	public List<FileUploadEntity> selectFileByPath(List<Object> str) {
		StringBuffer buf = new StringBuffer();
		buf.append("select ID,Name,FilePath,Size,Uploader,UploadDate from File_Upload where 1=1 and ");
		int i=0;
		for(Object path:str){
			if(i == str.size()-1){
				buf.append(" FilePath= "+"'"+str.get(i).toString()+"'");
			}else{
				buf.append(" FilePath= "+"'"+str.get(i).toString()+"'"+" or ");
			}
			i++;
		}
		buf.append(" order by ID desc");
		List<FileUploadEntity> listFile = fileUploadDao.selectFileByPath(buf.toString()); 
		return listFile;
	}

	public List<FileUploadEntity> selectFileOrderByName(Map<String, Object> map) {
		return fileUploadDao.selectFileOrderByName(map);
	}

	public FileUploadEntity selectLatestData(Map<String, Object> map) {
		return fileUploadDao.selectLatestData(map);
	}

	public void updateFileUploadByName(Map<String,Object> updateMap) {
		fileUploadDao.updateFileUploadByName(updateMap);
		fileUploadDao.inserttFileUpload((FileUploadEntity) updateMap.get("insertEntity"));
	}

}
