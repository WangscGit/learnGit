package com.cms_cloudy.upload.dao;

import java.util.List;
import java.util.Map;

import com.cms_cloudy.upload.pojo.ExportExcel;

public interface IExportExcelDao {

	public List<ExportExcel> selectDataById(Map<String,Object> map);
	public void insertData(ExportExcel ex);
	public void updateData(ExportExcel ex);
	public int deleteData(int id);
}
