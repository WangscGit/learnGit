package com.cms_cloudy.database.dao;

import java.util.List;
import java.util.Map;
import com.cms_cloudy.database.pojo.FileImg;
import com.cms_cloudy.database.pojo.PartClass;
import com.cms_cloudy.database.pojo.PartDefineAttributes;

public interface IPartClassDao {

	/**查询元器件分类表**/
	public List<Map<String, Object>> selectPartClass(Map<String,Object> map);
	/**添加元器件分类**/
	public void insertPartClass(PartClass p);
	/**修改元器件分类**/
	public void updatePartClass(PartClass p);
	/**删除元器件分类**/
	public void deletePartClass(int id);
	/**添加元器件分类细目**/
	public void insertPartDefineAttributes(PartDefineAttributes p);
	/**修改元器件分类细目**/
	public void updatePartDefineAttributes(PartDefineAttributes p);
	/**查询元器件分类中的细目**/
	public int selectPartDefineAttributes(int classId);
	/**删除元器件分类中的细目**/
	public void deletePartDefineAttributes(int classId);
	/**判断childNum在同一级下是否重复**/
	public int childNumIsRepeat(Map<String,Object> paramMap);
	/** 获取不包含选中节点及子节点的pnCode **/
	public List<Map<String,Object>> getPnCode(Map<String,Object> parentNumMap);
	/** 根据pnCodes获取节点信息 **/
	public List<Map<String,Object>> getNodeByPnCode(Map<String,Object> parentNumMap);
	/** 添加图片信息 **/
	public void insertFileImg(FileImg fileImg);
	/** 根据partclassId删除图片信息 **/
	public void deleteFileImg(long partClassId);
	/** 根据文件名获取图片信息 **/
	public List<FileImg> selectImgByName(Map<String,String> map);
}
