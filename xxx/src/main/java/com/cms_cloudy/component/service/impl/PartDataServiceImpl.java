package com.cms_cloudy.component.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.rest.diagram.dao.impl.WorkflowBaseDaoImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cms_cloudy.component.dao.IPartDataDao;
import com.cms_cloudy.component.dao.IPartPrimaryAttributesDao;
import com.cms_cloudy.component.dao.IUserPnDao;
import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.cms_cloudy.component.service.IPartDataService;
import com.cms_cloudy.database.pojo.PartTypeTree;
import com.cms_cloudy.product.dao.IProductBomDao;
import com.cms_cloudy.product.pojo.ProductBomPn;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.websocket.dao.IPushPartDataDao;
import com.github.pagehelper.PageHelper;

@Component("IPartDataService")
public class PartDataServiceImpl implements IPartDataService {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	private IPartDataDao iPartDataDao;
	@Autowired
	private IPartPrimaryAttributesDao partPrimaryAttributesDao;
	@Autowired
	private IUserPnDao userPnDao;
	@Autowired
	private IProductBomDao iProductBomDao;
	@Autowired
	private IPushPartDataDao iPushPartDataDao;
	//流程引擎初始化
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	public Map<String, Object> selectPartData(Map<String, Object> paramMap) {
		Map<String, Object> imap = new HashMap<String, Object>();
		imap.put("isUsed", "1");
		List<PartPrimaryAttributes> list = partPrimaryAttributesDao.selectFieldAndName(imap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select pd.id,");
		for (int i = 0; i < list.size(); i++) {
			sql.append("pd." + list.get(i).getFieldName() + ",");
		}
		sql.delete(sql.length() - 1, sql.length());
		sql.append(" from part_data pd left join user_pn up on pd.PartNumber=up.PartNumber   where 1=1 ");
		String item = (String) paramMap.get("item");
		String partTypes = (String) paramMap.get("partTypes");// 进入页面默认展示第一个节点下的数据
		String partType = (String) paramMap.get("partType");
		// String filed1=(String) paramMap.get("filed1");
		// String filed2=(String) paramMap.get("filed2");
		// String filed3=(String) paramMap.get("filed3");
		String str = (String) paramMap.get("str");
		String manufacturer = (String) paramMap.get("manufacturer");
		Long userId = (Long) paramMap.get("userId");
		String collection = (String) paramMap.get("collection");
		String isPush = (String) paramMap.get("isPush");
		String tempPartMark = (String) paramMap.get("tempPartMark");
		String keyComponent = (String) paramMap.get("keyComponent");
		String PartNumber1 = (String) paramMap.get("PartNumber1");
		String Part_Type1 = (String) paramMap.get("Part_Type1");
		String ITEM1 = (String) paramMap.get("ITEM1");
		String Manufacturer1 = (String) paramMap.get("Manufacturer1");
		String KeyComponent1 = (String) paramMap.get("KeyComponent1");
		String Datesheet1 = (String) paramMap.get("Datesheet1");
		if (StringUtils.isNotEmpty(partTypes)) {
			String ps[] = partTypes.split(",");
			sql.append("and part_Type in (");
			for (int i = 0; i < ps.length; i++) {
				sql.append("'" + ps[i] + "'" + ",");
			}
			sql.delete(sql.length() - 1, sql.length());
			sql.append(")");
		}
		if (StringUtils.isNotEmpty(item)) {
			item = item.replace("%CE%A9", "Ω");
			sql.append(" and (item like ");
			sql.append("'%" + item + "%'");
			sql.append(" or Part_Type like ");
			sql.append("'%" + item + "%'");
			sql.append(" or Manufacturer like ");
			sql.append("'%" + item + "%'");
			sql.append(" or KeyComponent like ");
			sql.append("'%" + item + "%'");
			sql.append(" or Datesheet like ");
			sql.append("'%" + item + "%' ");
			sql.append(" or pd.PartCode like ");
			sql.append("'%" + item + "%' )");
		}
		if (StringUtils.isNotEmpty(manufacturer)) {
			sql.append(" and manufacturer = ");
			sql.append("'" + manufacturer + "'");
		}

		if (StringUtils.isNotEmpty(partType)) {
			sql.append(" and part_Type = ");
			sql.append("'" + partType + "'");
		}
		// if(StringUtils.isNotEmpty(filed1)){
		// sql.append(" and "+filed1);
		// }
		// if(StringUtils.isNotEmpty(filed2)){
		// sql.append(" and "+filed2);
		// }
		// if(StringUtils.isNotEmpty(filed3)){
		// sql.append(" and "+filed3);
		// }
		if (StringUtils.isNotEmpty(str)) {
			sql.append(" and " + str.replace("\u0000", ""));
		}
		if (userId != null && !userId.equals("") && collection != null) {
			sql.append(" and up.userId=" + userId);
		}
		if (StringUtils.isNotEmpty(tempPartMark)) {
			sql.append(" and pd.tempPartMark='" + tempPartMark + "'");
		}
		if (StringUtils.isNotEmpty(keyComponent)) {
			sql.append(" and pd.keyComponent='" + keyComponent + "'");
		}
		////////////////////////////////////////
		if (StringUtils.isNotEmpty(PartNumber1)) {
			sql.append(" and pd.PartNumber like ");
			sql.append("'%" + PartNumber1 + "%'");
		}
		if (StringUtils.isNotEmpty(Part_Type1)) {
			sql.append(" and pd.Part_Type like ");
			sql.append("'%" + Part_Type1 + "%'");
		}
		if (StringUtils.isNotEmpty(ITEM1)) {
			sql.append(" and pd.ITEM like ");
			sql.append("'%" + ITEM1 + "%'");
		}
		if (StringUtils.isNotEmpty(Manufacturer1)) {
			sql.append(" and pd.Manufacturer like ");
			sql.append("'%" + Manufacturer1 + "%'");
		}
		if (StringUtils.isNotEmpty(KeyComponent1)) {
			sql.append(" and pd.KeyComponent like ");
			sql.append("'%" + KeyComponent1 + "%'");
		}
		if (StringUtils.isNotEmpty(Datesheet1)) {
			sql.append(" and pd.Datesheet like ");
			sql.append("'%" + Datesheet1 + "%'");
		}
		sql.append(" and (pd.process_state is NULL or  pd.process_state not in ('审批中','未启动')) ");
		// 查询count总数
		String s[] = sql.toString().split("from");
		StringBuffer countSql = new StringBuffer("select count(*) from");
		countSql.append(s[1]);
		long count = iPartDataDao.countPartData(countSql.toString());
		// 查询生产厂家和质量等级
		List<String> manuList = new ArrayList<String>();
		List<String> keyComponentList = new ArrayList<String>();
		if (StringUtils.isEmpty(isPush)) {
			StringBuffer manufacturerSql = new StringBuffer("select distinct manufacturer from ");
			manufacturerSql.append(s[1]);
			manuList = iPartDataDao.selectManufacturer(manufacturerSql.toString());

			StringBuffer keyComponentSql = new StringBuffer("select distinct keyComponent from ");
			keyComponentSql.append(s[1]);
			keyComponentList = iPartDataDao.selectKeyComponent(keyComponentSql.toString());
		}
		resultMap.put("count", count);
		resultMap.put("manuList", manuList);
		resultMap.put("keyComponentList", keyComponentList);
		sql.append(" order by pd.ModifyDate desc");
		// 分页拦截器 设置当前页、每页多少数据
		PageHelper.startPage(Integer.valueOf(paramMap.get("pageNo").toString()),
				Integer.valueOf(paramMap.get("pageSize").toString()));
		List<Map<String, Object>> partDataList = iPartDataDao.selectPartData(sql.toString());

		// 判断当前用户是否收藏此partData
		for (int i = 0; i < partDataList.size(); i++) {
			Map<String, Object> partDataMap = partDataList.get(i);
			String partNumber = (String) partDataMap.get("PartNumber");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("partNumber", partNumber);
			int coun = userPnDao.isCollection(map);
			if (coun > 0) {
				partDataMap.put("isCollection", true);
			} else {
				partDataMap.put("isCollection", false);
			}
		}
		resultMap.put("partDataList", partDataList);
		return resultMap;
	}

	public Map<String, Object> selectPartDataByAtt(String fieldName, int id) {
		// String sql = "select '"+fieldName+"' from dbo.Part_Data where ID =
		// '"+id+"'";
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(fieldName);
		sql.append(" from dbo.Part_Data where ID = " + id + "");
		Map<String, Object> map = jdbcTemplate.queryForMap(sql.toString());
		return map;
	}

	public List<PartTypeTree> getPartType(Map<String, Object> map) {
		return iPartDataDao.getPartType(map);
	}

	public void updatePartData(Map<String, Object> map) {
		// 根据页面传递的map拼接sql
		StringBuffer sql = new StringBuffer("update part_data set ");
		Set<String> set = map.keySet();
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String paramKey = i.next();
			if (paramKey.equals("id")) {
				continue;
			}
			sql.append(paramKey);
			sql.append(" = '");
			sql.append(map.get(paramKey));
			sql.append("' ,");

		}
		sql.delete(sql.length() - 1, sql.length());
		sql.append("where id=");
		sql.append(map.get("id"));
		iPartDataDao.updatePartData(sql.toString());
	}

	public void deletePartData(List<String> ids) {
		StringBuffer sql = new StringBuffer("delete from  part_data where id in ( ");
		for (int i = 0; i < ids.size(); i++) {
			sql.append(ids.get(i));
			sql.append(",");
			iPushPartDataDao.deletePartEvaluationByPartNumber(Integer.valueOf(ids.get(i)));
			iPushPartDataDao.deletePartHistoryByPartNumber(Integer.valueOf(ids.get(i)));
			iProductBomDao.deleteProductPnByPartNumber(Long.valueOf(ids.get(i)));
			iPartDataDao.deleteProductBomPnByPartNumber(Long.valueOf(ids.get(i)));
			List<Map<String, Object>> partList = partPrimaryAttributesDao
					.selectPartToUpdataAlternativePart(Integer.valueOf(ids.get(i)));
			updateAlternativePart(partList);// 可替代料更新
			//流程任务信息删除
			WorkflowBaseDaoImpl WorkflowBaseDao = new WorkflowBaseDaoImpl();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainId", Long.valueOf(ids.get(i)));
			map.put("formType", "0");
			map.put("jdbcTemplate", this.jdbcTemplate);
			WorkflowBaseDao.deleteProcessTaskDetail(map);
		}
		sql.delete(sql.length() - 1, sql.length());
		sql.append(")");
		iPartDataDao.deletePartData(sql.toString());

	}

	public Long insertPartData(Map<String, Object> map) {
		// 根据页面传递的map拼接sql
		StringBuffer sql = new StringBuffer("insert into  part_data  (");
		StringBuffer values = new StringBuffer("(");
		Set<String> set = map.keySet();
		Iterator<String> i = set.iterator();
		String partNumber = "";
		while (i.hasNext()) {
			String paramKey = i.next();
			if (map.get(paramKey) != null) {
				sql.append(paramKey);
				sql.append(",");
				values.append("'" + map.get(paramKey) + "'");
				values.append(",");
			}
			if (paramKey.equals("PartCode")) {
				partNumber = (String) map.get("PartCode");
			}
		}
		sql.delete(sql.length() - 1, sql.length());
		values.delete(values.length() - 1, values.length());
		sql.append(")");
		values.append(")");
		sql.append(" values ");
		sql.append(values);

		// 由于需要返回id，需要有实体类方便返回id。实体类某一属性保存sql，id作为返回值
		PartPrimaryAttributes p = new PartPrimaryAttributes();
		p.setFieldName(sql.toString());
		long l=checkPartCode(partNumber);
		if(l!=0l){
			return 0l;
		}
		iPartDataDao.insertPartData(p);
		return p.getId();
	}

	public long checkPartCode(String partNumber) {
		long l = iPartDataDao.countPartData("select count(id) from part_data where PartCode = '" + partNumber + "'");
		if (l != 0l) {
			return l;
		}
		return 0l;
	}

	public void insertPartHistories(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer("insert into  Part_Histories  (");
		StringBuffer values = new StringBuffer("(");
		Set<String> set = map.keySet();
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String paramKey = i.next();
			if (map.get(paramKey) != null && !map.get(paramKey).equals("")) {
				sql.append(paramKey);
				sql.append(",");
				values.append("'" + map.get(paramKey) + "'");
				values.append(",");
			}
		}
		sql.delete(sql.length() - 1, sql.length());
		values.delete(values.length() - 1, values.length());
		sql.append(")");
		values.append(")");
		sql.append(" values ");
		sql.append(values);
		jdbcTemplate.execute(sql.toString());
	}

	public List<Map<String, Object>> countAddPartData(Map<String, Object> paramMap) {
		return iPartDataDao.countAddPartData(paramMap);
	}

	public long countAllPartData(Map<String, Object> paramMap) {
		return iPartDataDao.countAllPartData(paramMap);
	}

	public long countProductPnByTime(Map<String, Object> paramMap) {
		return iPartDataDao.countProductPnByTime(paramMap);
	}

	public List<PartTypeTree> selectPtByProSelTime(Map<String, Object> paramMap) {
		return iPartDataDao.selectPtByProSelTime(paramMap);
	}

	public long countPpCountry(Map<String, Object> paramMap) {
		return iPartDataDao.countPpCountry(paramMap);
	}

	public long countPptempPartMark(Map<String, Object> paramMap) {
		return iPartDataDao.countPptempPartMark(paramMap);
	}

	public List<Map<String, Object>> selectProductBomByPn(Map<String, Object> map) {
		return iPartDataDao.selectProductBomByPn(map);
	}

	public List<Map<String, Object>> selectDataByPartNumber(Map<String, Object> map) {
		return iPartDataDao.selectDataByPartNumber(map);
	}

	public List<Map<String, Object>> selectAllData(Map<String, Object> map) {
		String sql = "select * from part_data";
		return this.jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> selectPartDataBySearchInp(String searchInp) {
		return iPartDataDao.selectPartDataBySearchInp(searchInp);
	}

	public List<Map<String, Object>> selectHotSearchFromSelf(String searchInp) {
		return iPartDataDao.selectHotSearchFromSelf(searchInp);
	}

	// 可替代料更新
	public void updateAlternativePart(List<Map<String, Object>> list) {
		Map<String, Object> mapSql = new HashMap<String, Object>();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String main = map.get("pNumber").toString();
				String detail[] = map.get("Alternative_Part").toString().split(",");
				String partNumber = map.get("PartNumber").toString();
				String Alternative_Part = "";
				for (int j = 0; j < detail.length; j++) {
					if (!detail[j].equals(main)) {
						if (j == detail.length - 1) {
							Alternative_Part += detail[j];
						} else {
							Alternative_Part += detail[j] + ",";
						}
					}
				}
				if (StringUtils.isNotEmpty(Alternative_Part)) {
					String lastChar = Alternative_Part.substring(Alternative_Part.length() - 1);
					if (lastChar.equals(",")) {
						Alternative_Part = Alternative_Part.substring(0, Alternative_Part.length() - 1);
					}
				}
				mapSql.put("alternativePart", Alternative_Part);
				mapSql.put("PartNumber", partNumber);
				partPrimaryAttributesDao.updateAlternativePart(mapSql);
			}
		}
	}

	public Map<String, Object> selectPartDataById(String sql,HrUser user) {
		Map<String, Object> partDataMap = iPartDataDao.selectPartDataById(sql);
		// 判断当前用户是否收藏此partData
		if(null != user){
			String partNumber = (String) partDataMap.get("PartNumber");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getUserId());
			map.put("partNumber", partNumber);
			int coun = userPnDao.isCollection(map);
			if (coun > 0) {
				partDataMap.put("isCollection", true);
			} else {
				partDataMap.put("isCollection", false);
			}
		}
		return partDataMap;
	}

	public void updatePartState(Map<String, Object> map) {
		String sql = "";
		if (null != map && null != map.get("state")) {
			sql = "update part_data set process_state = '" + "审批中" + "' where id=" + map.get("id");
		}
		this.jdbcTemplate.update(sql);
	}

	public List<Map<String, Object>> selectProductBomByBomPn(Map<String, Object> map) {
		return iPartDataDao.selectProductBomByBomPn(map);
	}

	public void insertproductBomPn(ProductBomPn productBomPn) {
		iPartDataDao.insertproductBomPn(productBomPn);
	}

	public List<ProductBomPn> checkProductBomByExcelName(Map<String, String> map) {
		return iPartDataDao.checkProductBomByExcelName(map);
	}

	public void updateProductBomPn(ProductBomPn productBomPn) {
		iPartDataDao.updateProductBomPn(productBomPn);
	}

	public void deleteProductBomPn(ProductBomPn productBomPn) {
		iPartDataDao.deleteProductBomPn(productBomPn);
	}

	public List<Map<String, Object>> selectAllfieldFromProductBomPn(ProductBomPn productBomPn) {
		return iPartDataDao.selectAllfieldFromProductBomPn(productBomPn);
	}

	public List<ProductBomPn> selectVersion(Map<String, String> map) {
		return iPartDataDao.selectVersion(map);
	}

	public void updatepbpVersion(ProductBomPn productBomPn) {
		iPartDataDao.updatepbpVersion(productBomPn);
	}

	public List<Map<String, String>> getDirInOROut() {
		return iPartDataDao.getDirInOROut();
	}

	public List<Map<String, Object>> selectfieldByCode(ProductBomPn productBomPn) {
		return iPartDataDao.selectfieldByCode(productBomPn);
	}

	public List<Map<String, Object>> selectPartCodeByBomPn(ProductBomPn productBomPn) {
		return iPartDataDao.selectPartCodeByBomPn(productBomPn);
	}

	public List<Map<String, Object>> selectHotSearchFromPHTB(String searchInp) {
		return iPartDataDao.selectHotSearchFromPHTB(searchInp);
	}

	public void deleteProductBomPnByPartNumber(long id) {
		iPartDataDao.deleteProductBomPnByPartNumber(id);
	}
}
