package com.cms_cloudy.upload.service;

import java.util.List;
import java.util.Map;

import com.cms_cloudy.upload.pojo.ExportExcel;

public interface IExportExcelService {

	public List<ExportExcel> selectDataById(Map<String,Object> map);
	public void insertData(ExportExcel ex);
	public void updateData(ExportExcel ex);
	public int deleteData(int id);
	/**元器件导入**/
	public List<String> insertPartData(Map<String,Object> map,List<String> repeatCode);
	/**根据物料编码查询主数据**/
	public List<Map<String, Object>> selectPartDateByPartNumber(Map<String, Object> map);
	/**元器件更新导入**/
	public List<String> updatePartData(Map<String,Object> map,List<String> repeatCode);
}
