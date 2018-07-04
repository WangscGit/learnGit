package com.cms_cloudy.upload.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cms_cloudy.database.service.IPartClassService;
import com.cms_cloudy.upload.dao.IExportExcelDao;
import com.cms_cloudy.upload.pojo.ExportExcel;
import com.cms_cloudy.upload.service.IExportExcelService;

@Component("IExportExcelService")
public class ExportExcelServiceImpl implements IExportExcelService {

	@Autowired
	private IExportExcelDao exportExcelDao;
	@Autowired
	private IPartClassService partClassService;
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<ExportExcel> selectDataById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return exportExcelDao.selectDataById(map);
	}

	public void insertData(ExportExcel ex) {
		// TODO Auto-generated method stub
		exportExcelDao.insertData(ex);
	}

	public void updateData(ExportExcel ex) {
		// TODO Auto-generated method stub
		exportExcelDao.updateData(ex);
	}

	public int deleteData(int id) {
		// TODO Auto-generated method stub
		return exportExcelDao.deleteData(id);
	}

	@SuppressWarnings("unchecked")
	public List<String> insertPartData(Map<String, Object> map, List<String> repeatCode) {
		// 判断编码是否重复
		if (null != map.get("PartCode") && !"".equals(map.get("PartCode").toString())) {
			String checkSql = "select id from Part_Data where PartCode = " + "'" + map.get("PartCode").toString() + "'";
			List<Map<String, Object>> repeatQuy = this.jdbcTemplate.queryForList(checkSql);
			// 如果重复将其放到错误数据集合
			if (null != repeatQuy && repeatQuy.size() > 0) {
				for (int v = 0; v < repeatQuy.size(); v++) {
					repeatCode.add(repeatQuy.get(v).get("id").toString());
				}
			}else{
				StringBuffer sql = new StringBuffer("insert into  part_data  ( ");
				List<Object> fieldList = (List<Object>) map.get("fieldName");
				for (int i = 0; i < fieldList.size(); i++) {
					if (i == fieldList.size() - 1) {
						sql.append(fieldList.get(i).toString());
					} else {
						sql.append(fieldList.get(i).toString() + ", ");
					}
				}
				sql.append(") ");
				sql.append("values( ");
				List<Object> fieldValue = (List<Object>) map.get("fieldValue");
				for (int x = 0; x < fieldValue.size(); x++) {
					if (x == fieldValue.size() - 1) {
						sql.append("'" + fieldValue.get(x).toString() + "'");
					} else {
						sql.append("'" + fieldValue.get(x).toString() + "'");
						sql.append(",");
					}
				}
				sql.append(" )");
				this.jdbcTemplate.execute(sql.toString());
			}
		} else {
			StringBuffer sql = new StringBuffer("insert into  part_data  ( ");
			List<Object> fieldList = (List<Object>) map.get("fieldName");
			for (int i = 0; i < fieldList.size(); i++) {
				if (i == fieldList.size() - 1) {
					sql.append(fieldList.get(i).toString());
				} else {
					sql.append(fieldList.get(i).toString() + ", ");
				}
			}
			sql.append(") ");
			sql.append("values( ");
			List<Object> fieldValue = (List<Object>) map.get("fieldValue");
			for (int x = 0; x < fieldValue.size(); x++) {
				if (x == fieldValue.size() - 1) {
					sql.append("'" + fieldValue.get(x).toString() + "'");
				} else {
					sql.append("'" + fieldValue.get(x).toString() + "'");
					sql.append(",");
				}
			}
			sql.append(" )");
			this.jdbcTemplate.execute(sql.toString());
		}
		return repeatCode;
	}

	// 分类信息添加----数据来源----器件导入
	public void addPartClass(Map<String, Object> map) {
		Map<String, Object> querymap = new HashMap<String, Object>();
		Object pcType1 = map.get("pcType1");
		Object pcType2 = map.get("pcType2");
		Object pcType3 = map.get("pcType3");
		Object pcType4 = map.get("pcType4");
		Object pcType5 = map.get("pcType5");
		if (null != pcType1 && !"".equals(pcType1.toString())) {
			querymap.put("parentNode", "getAllParent");
			List<Map<String, Object>> pcList = partClassService.selectPartClass(querymap);

		}
	}

	// 器件添加----数据来源----器件导入
	public void addPartData(Map<String, Object> map) {
		// 物料编码为空时自动生成物料编码
		if (null == map.get("PartNumber") || "".equals(map.get("PartNumber").toString())) {
			// 生成物料编码
			Map<String, Object> mapPN = new HashMap<String, Object>();
			mapPN.put("partType", map.get("Part_Type"));
			mapPN.put("tempPartMark", map.get("TempPartMark"));
			List<Map<String, Object>> l = partClassService.selectPartClass(mapPN);
			List<Long> oids = new ArrayList<Long>();
			oids.add((Long) l.get(0).get("oid"));
			String partNumber = partClassService.getPartNumber(oids);
			map.put("PartNumber", partNumber);
		}
		StringBuffer sql = new StringBuffer("insert into  part_data  ( ");
		List<Object> fieldList = (List<Object>) map.get("fieldName");
		for (int i = 0; i < fieldList.size(); i++) {
			if (i == fieldList.size() - 1) {
				sql.append(fieldList.get(i).toString());
			} else {
				sql.append(fieldList.get(i).toString() + ", ");
			}
		}
		sql.append(") ");
		sql.append("values( ");
		List<Object> fieldValue = (List<Object>) map.get("fieldValue");
		for (int x = 0; x < fieldValue.size(); x++) {
			if (x == fieldValue.size() - 1) {
				sql.append("'" + fieldValue.get(x).toString() + "'");
			} else {
				sql.append("'" + fieldValue.get(x).toString() + "'");
				sql.append(",");
			}
		}
		sql.append(" )");
		this.jdbcTemplate.execute(sql.toString());
	}

	public List<Map<String, Object>> selectPartDateByPartNumber(Map<String, Object> map) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select ");
		buffer.append("*");
		buffer.append(" from Part_Data where PartNumber = '" + map.get("PartNumber") + "'");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	public List<String> updatePartData(Map<String, Object> map, List<String> repeatCode) {
		// 判断编码是否重复
	   if(null == map.get("PartCode") || "".equals(map.get("PartCode").toString())){
			repeatCode.add("noPartCode");//物料编码为空-不进行更新
	   }else{
			String checkSql = "select id from Part_Data where PartCode = " + "'" + map.get("PartCode").toString() + "'";
			List<Map<String, Object>> repeatQuy = this.jdbcTemplate.queryForList(checkSql);
			// 如果重复将其放到错误数据集合
			if (null == repeatQuy || repeatQuy.size() == 0) {
				repeatCode.add("NotFoundPart");//匹配不到该物料编码-不进行更新
			} else {
				// 根据页面传递的map拼接sql
				StringBuffer sql = new StringBuffer("update part_data set ");
				Set<String> set = map.keySet();
				Iterator<String> i = set.iterator();
				while (i.hasNext()) {
					String paramKey = i.next();
					if (paramKey.equals("id") || paramKey.equals("PartCode")) {
						continue;
					}
					sql.append(paramKey);
					sql.append(" = '");
					sql.append(map.get(paramKey));
					sql.append("' ,");

				}
				sql.delete(sql.length() - 1, sql.length());
				sql.append("where PartCode= ");
				sql.append("'"+map.get("PartCode")+"'");
				this.jdbcTemplate.execute(sql.toString());
			}
		}
		return repeatCode;
	}
}
