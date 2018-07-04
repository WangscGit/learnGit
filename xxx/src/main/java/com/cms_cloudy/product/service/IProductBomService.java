package com.cms_cloudy.product.service;

import java.util.List;
import java.util.Map;
import com.cms_cloudy.product.pojo.ProductBomEntity;
import com.cms_cloudy.product.pojo.ProductPn;

public interface IProductBomService {

	// 添加productPn产品元器件关系表数据
	public int insertProductPn(ProductPn productPn);

	// 验证productPn是否已添加
	public Map<String, Object> checkProductPn(Map<String, Object> paramMap);

	/** 产品节点树查询 **/
	public List<ProductBomEntity> selectProductBomList(Map<String, Object> map);

	/** 子节点查询全部父级节点 **/
	public List<ProductBomEntity> selectAllParentData(Map<String, Object> map);

	/** 产品树购建 **/
	public void insertProductBom(ProductBomEntity productBom);

	/** 产品树节点修改 **/
	public void updateProductBom(ProductBomEntity productBom);

	/** 产品树节点删除 **/
	public int deleteProductBom(int bomId);

	/** 产品主数据关系表删除 **/
	public int deleteProductPn(int pnId);

	/** 产品主数据关系表 **/
	public List<ProductPn> selectProductPnList(Map<String, Object> map);

	/** 产品追溯父节点查询 **/
	public List<Map<String, Object>> selectAllParent(String[] ids);

	/** 父节点查询所有子节点 **/
	public List<ProductBomEntity> selectAllChildData(Map<String, Object> map);

	// 修改productPn中coun
	public void updateProductPnCoun(ProductPn productPn);

	// 根据产品Id和物料编码删除信息
	public int deleteProductPnByProductId(Map<String, Object> map);

	// product_bom_pn表关联查询
	public List<Map<String, Object>> bomPnList(Map<String, Object> map);

	// bom对比页面初始化
	public List<Map<String, Object>> selectCompareBomList(Map map);
	
	//根据产品ID以及bom名称进行product_bom_pn表数据删除
	public int deleteProductBomPnByCondition(Map<String,Object> map);
}
