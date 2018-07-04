package com.cms_cloudy.component.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.cms_cloudy.database.pojo.PartTypeTree;
import com.cms_cloudy.product.pojo.ProductBomPn;

public interface IPartDataDao {
	/** 动态查询partdata字段 */
	public List<Map<String, Object>> selectPartData(@Param(value = "sql") String sql);

	/** 获取partData中的partType */
	public List<PartTypeTree> getPartType(Map<String, Object> map);

	/** 修改partData */
	public void updatePartData(@Param(value = "sql") String sql);

	/** 删除partData */
	public void deletePartData(@Param(value = "sql") String sql);

	/** 添加partData */
	public void insertPartData(PartPrimaryAttributes partPrimaryAttributes);

	/** 获取partData的总数 */
	public long countPartData(@Param(value = "sql") String sql);

	/** 通过id获取partData */
	public Map<String, Object> selectPartDataById(@Param(value = "sql") String sql);

	/** 获取生产厂家 */
	public List<String> selectManufacturer(@Param(value = "sql") String sql);

	/** 获取质量等级 */
	public List<String> selectKeyComponent(@Param(value = "sql") String sql);

	/** 统计在一定时间内新增partData的partType数量 */
	public List<Map<String, Object>> countAddPartData(Map<String, Object> paramMap);

	/** 统计partData总数 */
	public long countAllPartData(Map<String, Object> paramMap);

	/** 根据时间统计产品中元器件总数 */
	public long countProductPnByTime(Map<String, Object> paramMap);

	/** 根据时间统计产品中元器件类型 */
	public List<PartTypeTree> selectPtByProSelTime(Map<String, Object> paramMap);

	/** 统计产品中元器件国产的数量 */
	public long countPpCountry(Map<String, Object> paramMap);

	/** 统计产品中元器件目录内的数量 */
	public long countPptempPartMark(Map<String, Object> paramMap);

	/** 元器件导出信息查询 **/
	public List<Map<String, Object>> selectProductBomByPn(Map<String, Object> map);

	/** 元器件导出信息查询 **/
	public List<Map<String, Object>> selectProductBomByBomPn(Map<String, Object> map);

	/** 产品追溯分析 **/
	public List<Map<String, Object>> selectDataByPartNumber(Map<String, Object> map);

	/** 查询器件信息 **/
	public List<Map<String, Object>> selectPartDataBySearchInp(@Param(value = "searchInp") String searchInp);

	/** 查询器件信息 **/
	public List<Map<String, Object>> selectHotSearchFromSelf(@Param(value = "searchInp") String searchInp);

	/** 查询器件信息 **/
	public List<Map<String, Object>> selectHotSearchFromPHTB(@Param(value = "searchInp") String searchInp);

	/** 添加productBomPn **/
	public void insertproductBomPn(ProductBomPn productBomPn);

	/** 根据文件名验证productBomPn **/
	public List<ProductBomPn> checkProductBomByExcelName(Map<String, String> map);

	/** 修改productBomPn **/
	public void updateProductBomPn(ProductBomPn productBomPn);

	/** 删除productBomPn **/
	public void deleteProductBomPn(ProductBomPn productBomPn);

	/** 根据excelName和productId查询数据 **/
	public List<Map<String, Object>> selectAllfieldFromProductBomPn(ProductBomPn productBomPn);

	/** 根据excelName和productId查询此文件之后的版本 **/
	public List<ProductBomPn> selectVersion(Map<String, String> map);

	/** 修改productBomPn版本 **/
	public void updatepbpVersion(ProductBomPn productBomPn);

	/** 获取所有的根目录 **/
	public List<Map<String, String>> getDirInOROut();

	/** 根据excelName、productId、partCode查询数据 **/
	public List<Map<String, Object>> selectfieldByCode(ProductBomPn productBomPn);

	/** 根据excelName、product查询去重的partCode **/
	public List<Map<String, Object>> selectPartCodeByBomPn(ProductBomPn productBomPn);
	/** 根据partdata id删除数据**/
	public void deleteProductBomPnByPartNumber(long id);
}
