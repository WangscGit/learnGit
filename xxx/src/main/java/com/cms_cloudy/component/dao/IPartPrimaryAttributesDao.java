package com.cms_cloudy.component.dao;

import java.util.List;
import java.util.Map;
import com.cms_cloudy.component.pojo.FieldSelect;
import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.github.pagehelper.PageInfo;

public interface IPartPrimaryAttributesDao {
	/**查询字段信息表返回List**/
	public List<PartPrimaryAttributes> selectPartPrimaryAttributesList(Map<String,Object> map);
	/**查询字段信息表**/
	public PageInfo<PartPrimaryAttributes> selectPartPrimaryAttributesByPage(Map<String,Object> map);
    /**字段信息表数据添加**/
    public void insertPartPrimaryAttributes(PartPrimaryAttributes primaryAttr);
    /**字段信息表数据更新**/
	public int updatePartPrimaryAttributes(PartPrimaryAttributes primaryAttr);
	/**字段信息表删除**/
	public int deletePartPrimaryAttributes(int id);
	/**查询字段名以及展示名**/
    public List<PartPrimaryAttributes> selectFieldAndName(Map<String,Object> map);
    /**更新顺序号**/
    public void updatePartAttrSeqNo(PartPrimaryAttributes primaryAttr);
    /**获取查询字段**/
    public List<PartPrimaryAttributes> selectSeachField(Map<String,Object> map);
    /**获取添加修改字段**/
    public List<PartPrimaryAttributes> selectAddOrUpdateField(Map<String,Object> map);
    /**获取表格字段**/
    public List<Map<String,Object>> selectTableField(); 
    /**根据类型统计总数**/
    public long selectPrimaryCount(Map<String,Object> map);
    /**获取固定的5个字段**/
    public List<PartPrimaryAttributes> selectImportantField();
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
   /**BOM对比结果查询**/
   public List<Map<String,Object>> selectCompareBom(Map<String,Object> map);
   /**查询器件申请添加、修改页面除固定字段外的添加字段**/
   public List<PartPrimaryAttributes> selectApplyField(Map<String,Object> map);
   /**查询使用的所有字段**/
   public List<PartPrimaryAttributes> selectUsedField(Map<String,Object> map);
}
