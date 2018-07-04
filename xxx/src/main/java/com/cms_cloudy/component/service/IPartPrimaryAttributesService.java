package com.cms_cloudy.component.service;

import java.util.List;
import java.util.Map;
import com.cms_cloudy.component.pojo.FieldSelect;
import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.github.pagehelper.PageInfo;

public interface IPartPrimaryAttributesService {
  
	/**查询字段信息表**/
	public PageInfo<PartPrimaryAttributes> selectPartPrimaryAttributesByPage(Map<String,Object> map);
	/**查询字段信息表返回List**/
	public List<PartPrimaryAttributes> selectPartPrimaryAttributesList(Map<String,Object> map);
   /**字段信息表数据添加**/
	public void insertPartPrimaryAttributes(PartPrimaryAttributes primaryAttr);
	/**字段信息表数据更新**/
	public int updatePartPrimaryAttributes(PartPrimaryAttributes primaryAttr);
	/**字段信息表删除**/
	public int deletePartPrimaryAttributes(int id);
	/**查询字段信息表2**/
	public List<PartPrimaryAttributes> selectPartPrimaryAttributesByList(Map<String,Object> map);
	/**查询字段名以及展示名**/
    public List<PartPrimaryAttributes> selectFieldAndName(Map<String,Object> map);
    /**根据字段定义表查询出来的字段，进行元器件主数据查询**/
    public List<Map<String, Object>> selectDataByFieldName(Map<String,Object> map);
    /**根据字段定义表查询出来的字段，进行元器件历史记录数据查询**/
    public List<Map<String, Object>> selectHIstiriesByFieldName(Map<String,Object> map);
    /**元器件主表添加**/
    public void insertOrUpdatePartData();
    /**元器件主表更新**/
    public void updateOrUpdatePartData();
    /**更新顺序号**/
    public void updatePartAttrSeqNo(PartPrimaryAttributes primaryAttr);
   /**根据版本号和物料编码查询历史记录数据**/
    public List<Map<String, Object>> selectHIstoriesDataByParam(Map<String,Object> map);
    /**根据物料编码查询主数据内容**/
    public List<Map<String, Object>> selectPartsDataByParam(Map<String,Object> map);
    /**获取查询字段**/
    public List<PartPrimaryAttributes> selectSeachField(Map<String,Object> map);
   /**根据物料编码查询主数据表**/
    public List<Map<String, Object>> selectPartDateByPartNumber(Map<String,Object> map);
    /**根据id查询主数据表**/
    public List<Map<String, Object>> selectPartDateById(Map<String,Object> map);
    /**查询全部的元器件主数据存到redis里**/
    public Map<String, Object> selectPartDatasAllForRedis(Map<String, Object> queryMap);
    /**获取添加修改字段**/
    public Map<String, Object> selectAddOrUpdateField(Map<String,Object> map);
    /**获取历史记录表内最大版本号**/
    public int getMaxVersionId(String partNumber);
    /**获取表格字段**/
    public List<Map<String,Object>> selectTableField();
    /**根据类型统计总数**/
    public long selectPrimaryCount(Map<String,Object> map);
    /**更新详情页数据**/
    public void updateMinudata(Map<String,Object> map);
    /**特殊属性查询**/
    public Map<String,Object> selectProperiesByName(Map<String,Object> map);
    /**更新主数据状态**/
    public void updateState(Map<String,Object> map);
    /**更新可替代料字段**/
    public void updateAlternativePart(Map<String,Object> map);
    /**新增下拉列表值**/
    public void insertFieldSelect(FieldSelect fieldSelect);
    /**根据字段名删除下拉列表值**/
    public void deleteFieldSelect(String fieldName);
    /**根据字段名获取下拉列表值**/
    public List<FieldSelect> getFieldSelectByFieldName(Map<String,Object> map);
    /**字段信息修改**/
    public void updateFieldAttr(PartPrimaryAttributes fieldAttr);   
    /**首页跳转至详情页**/
    public List<Map<String,Object>> selectPartGopage();
    /**查询待更新可替代料的器件数据**/
    public List<Map<String,Object>> selectPartToUpdataAlternativePart(int id);
    /**查询添加、修改页面的几个固定字段**/
    public List<PartPrimaryAttributes> selectFixedInsertField(Map<String,Object> map);
    /**查询添加、修改页面除固定字段外的添加字段**/
    public List<PartPrimaryAttributes> selectInsertField(Map<String,Object> map);
    /**查询器件申请添加、修改页面除固定字段外的添加字段**/
    public List<PartPrimaryAttributes> selectApplyField(Map<String,Object> map);
    /** BOM对比结果查询 **/
	public List<Map<String, Object>> selectCompareBom(Map<String, Object> map);
	/**查询使用的所有字段**/
	public List<PartPrimaryAttributes> selectUsedField(Map<String,Object> map);
	
	public List<Map<String, Object>> selectPartsDataByPartCode(Map<String, Object> map);
}
