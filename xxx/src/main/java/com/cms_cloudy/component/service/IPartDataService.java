package com.cms_cloudy.component.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cms_cloudy.database.pojo.PartTypeTree;
import com.cms_cloudy.product.pojo.ProductBomPn;
import com.cms_cloudy.user.pojo.HrUser;

public interface IPartDataService {

	/** 查询主数据表属性值供详情页显示 **/
	public Map<String, Object> selectPartDataByAtt(String fieldName, int id);

	/** 动态查询partdata字段 */
	public Map<String, Object> selectPartData(Map<String, Object> paramMap);

	/** 获取partData中的partType */
	public List<PartTypeTree> getPartType(Map<String, Object> map);

	/** 修改partData */
	public void updatePartData(Map<String, Object> map);

	/** 删除partData */
	public void deletePartData(List<String> ids);

	/** 添加partData */
	public Long insertPartData(Map<String, Object> map);

	/** 添加元器件修改记录 **/
	public void insertPartHistories(Map<String, Object> map);

	/** 统计在一定时间内新增partData的partType数量 */
	public List<Map<String, Object>> countAddPartData(Map<String, Object> paramMap);

	/** 通过id获取partData，并获取要修改添加的字段信息 */
	public Map<String, Object> selectPartDataById(String sql,HrUser user);

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

	/** 查询全部的器件数据 **/
	public List<Map<String, Object>> selectAllData(Map<String, Object> map);

	/** 查询器件信息 **/
	public List<Map<String, Object>> selectPartDataBySearchInp(String searchInp);

	/** 查询器件信息 **/
	public List<Map<String, Object>> selectHotSearchFromSelf(String searchInp);

	/** 查询器件信息 **/
	public List<Map<String, Object>> selectHotSearchFromPHTB(@Param(value = "searchInp") String searchInp);

	/** 更新器件信息 **/
	public void updatePartState(Map<String, Object> map);

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
	/** 根据PartCode验证partdata是否重复 **/
	public long checkPartCode(String partNumber);
	/** 根据partdata id删除数据**/
	public void deleteProductBomPnByPartNumber(long id);
}
