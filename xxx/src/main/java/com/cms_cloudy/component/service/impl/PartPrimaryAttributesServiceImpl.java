package com.cms_cloudy.component.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.cms_cloudy.component.dao.IPartDataDao;
import com.cms_cloudy.component.dao.IPartPrimaryAttributesDao;
import com.cms_cloudy.component.pojo.FieldSelect;
import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.cms_cloudy.component.service.IPartPrimaryAttributesService;
import com.cms_cloudy.user.dao.IHrRightsDao;
import com.cms_cloudy.user.pojo.ParameterConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Component("IPartPrimaryAttributesService")
public class PartPrimaryAttributesServiceImpl implements IPartPrimaryAttributesService {

	@Autowired
	private IPartPrimaryAttributesDao partPrimaryAttributesDao;
	@Autowired
	private IPartDataDao iPartDataDao;
	@Autowired
	private IHrRightsDao iHrRightsDao;
	@Resource  
    public JdbcTemplate jdbcTemplate; 
	public PageInfo<PartPrimaryAttributes> selectPartPrimaryAttributesByPage(Map<String, Object> map) {
		PageHelper.startPage(Integer.valueOf(map.get("pageNo").toString()), Integer.valueOf(map.get("pageSize").toString()));
		List<PartPrimaryAttributes> list = partPrimaryAttributesDao.selectPartPrimaryAttributesList(map);
		for(PartPrimaryAttributes aa:list){
			System.out.println(aa.getFieldName());
		}
		PageInfo<PartPrimaryAttributes> page = new PageInfo<PartPrimaryAttributes>(list);
			return page;
	}
	public void insertPartPrimaryAttributes(PartPrimaryAttributes primaryAttr) {
		partPrimaryAttributesDao.insertPartPrimaryAttributes(primaryAttr);
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("ALTER TABLE [Part_Data] ADD");
//		if(primaryAttr.getIsNull().equals("yes")){
//			buffer.append(" "+ primaryAttr.getFieldName()+" " +primaryAttr.getDataType());
//		}else{
//			buffer.append(" "+ primaryAttr.getFieldName()+" " +primaryAttr.getDataType()+" "+"is not null");
//		}
//		jdbcTemplate.execute(buffer.toString());
	}
	public int updatePartPrimaryAttributes(PartPrimaryAttributes primaryAttr) {
		return partPrimaryAttributesDao.updatePartPrimaryAttributes(primaryAttr);
	}
	public int deletePartPrimaryAttributes(int id) {
		return partPrimaryAttributesDao.deletePartPrimaryAttributes(id);
	}
	public List<PartPrimaryAttributes> selectPartPrimaryAttributesByList(Map<String, Object> map) {
		List<PartPrimaryAttributes> list = partPrimaryAttributesDao.selectPartPrimaryAttributesList(map);
		return list;
	}
	public List<PartPrimaryAttributes> selectFieldAndName(Map<String, Object> map) {
		return partPrimaryAttributesDao.selectFieldAndName(map);
	}
	public List<Map<String, Object>>  selectDataByFieldName(Map<String, Object> map) {
		if(null != map.get("sql") && StringUtils.isNoneEmpty(map.get("sql").toString())){
			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			buffer.append(map.get("sql").toString());
			buffer.append(" from Part_Data where 1=1");
			if(null != map.get("condition")){
				List<Integer> ids = (List<Integer>)map.get("condition");
				if(ids.size()>0){
					buffer.append(" and id in (");
					for(int x=0;x<ids.size();x++){
						if(ids.size()-1 == x){
							buffer.append(ids.get(x));
						}else{
							buffer.append(ids.get(x)+",");
						}
					}
					buffer.append(")");
				}
			}
			 List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
			return list;
		}
		return null;
	}
	public List<PartPrimaryAttributes> selectPartPrimaryAttributesList(Map<String, Object> map) {
		return partPrimaryAttributesDao.selectPartPrimaryAttributesList(map);
	}
	public void insertOrUpdatePartData() {
		Map<String,Object> map = new HashMap<String,Object>();
		List list = jdbcTemplate.queryForList("SELECT * FROM sysobjects where name='part_data'");
		List<PartPrimaryAttributes> lists = partPrimaryAttributesDao.selectPartPrimaryAttributesList(map);
		if(list.size()<=0){
			StringBuffer buffer = new StringBuffer();
			buffer.append("CREATE TABLE [dbo].[Part_Data](");
			buffer.append("\r\n");
			buffer.append("[id] [bigint] IDENTITY(1,1) NOT NULL,");
			buffer.append("\r\n");
			buffer.append("CONSTRAINT [PK_Part_Data] PRIMARY KEY CLUSTERED(");
			buffer.append("\r\n");
			buffer.append("[id] ASC");
			buffer.append("\r\n");
			buffer.append(")WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]");
			buffer.append("\r\n");
			buffer.append(") ON [PRIMARY]");
			jdbcTemplate.execute(buffer.toString());
			//批量添加字段
			if(lists.size()>0){
				for(PartPrimaryAttributes attr:lists){
					if( true == attr.isUsed()){
						StringBuffer buffers = new StringBuffer();
			    		buffers.append("ALTER TABLE [Part_Data] ADD");
			    		if("false".equals(attr.getIsNull())){
				    		buffers.append(" "+ attr.getFieldName()+" " +attr.getDataType()+ " "+"NOT NULL");
			    		}else{
				    		buffers.append(" "+ attr.getFieldName()+" " +attr.getDataType());
			    		}
			    		jdbcTemplate.execute(buffers.toString());
					}
				}
			}
		}
		insertOrUpdatePartHistory();//同时创建历史记录表
	}
	public void updatePartAttrSeqNo(PartPrimaryAttributes primaryAttr) {
		partPrimaryAttributesDao.updatePartAttrSeqNo(primaryAttr);
	}
	public void updateOrUpdatePartData() {
		Map<String,Object> map = new HashMap<String,Object>();
		List<PartPrimaryAttributes> lists = partPrimaryAttributesDao.selectPartPrimaryAttributesList(map);
		//批量添加字段
		if(lists.size()>0){
			for(PartPrimaryAttributes attr:lists){
				if( false == attr.isUsed()){
					StringBuffer buffers = new StringBuffer();
		    		buffers.append("ALTER TABLE Part_Data ADD");
		    		if(attr.getDataType().equals("selectList")){
		    			buffers.append(" "+ attr.getFieldName()+" " +"nvarchar(500)");
		    		}else{
		    			buffers.append(" "+ attr.getFieldName()+" " +attr.getDataType());
		    		}
		    		if("false".equals(attr.getIsNull())){
		    			if(attr.getDataType().equals("bit")){
		    				buffers.append(" default 'true' not null");
		    			}else{
		    				buffers.append(" default 0 not null");
		    			}
		    		}
		    		jdbcTemplate.execute(buffers.toString());
		    		attr.setUsed(true);
		    		partPrimaryAttributesDao.updatePartPrimaryAttributes(attr);
		    		updatePartHIstiries(attr);
				}
			}
		}
	}

	
	/**
	 * 向主数据表添加字段的同时向历史表添加字段
	 */
	public void updatePartHIstiries(PartPrimaryAttributes attr) {
		StringBuffer buffers = new StringBuffer();
		buffers.append("ALTER TABLE Part_Histories ADD");
		if(attr.getDataType().equals("selectList")){
			buffers.append(" "+ attr.getFieldName()+" " +"nvarchar(500)");
		}else{
			buffers.append(" "+ attr.getFieldName()+" " +attr.getDataType());
		}
		jdbcTemplate.execute(buffers.toString());
	}
	/**
	 * 自定创建元器件历史表
	 */
	public void insertOrUpdatePartHistory() {
		Map<String,Object> map = new HashMap<String,Object>();
		List list = jdbcTemplate.queryForList("SELECT * FROM sysobjects where name='Part_Histories'");
		List<PartPrimaryAttributes> lists = partPrimaryAttributesDao.selectPartPrimaryAttributesList(map);
		if(list.size()<=0){
			StringBuffer buffer = new StringBuffer();
			buffer.append("CREATE TABLE [dbo].[Part_Histories](");
			buffer.append("\r\n");
			buffer.append("[VersionID] [bigint] NOT NULL,");
			buffer.append("\r\n");
//			buffer.append(" CONSTRAINT [PK_Part_Histories] PRIMARY KEY CLUSTERED");
//			buffer.append("\r\n");
//			buffer.append("([VersionID] ASC");
//			buffer.append("\r\n");
//			buffer.append(")WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]");
//			buffer.append("\r\n");
			buffer.append(") ON [PRIMARY]");
			jdbcTemplate.execute(buffer.toString());
			//批量添加字段
			if(lists.size()>0){
				for(PartPrimaryAttributes attr:lists){
					if( true == attr.isUsed()){
						StringBuffer buffers = new StringBuffer();
			    		buffers.append("ALTER TABLE [Part_Histories] ADD");
			    		if("否".equals(attr.getIsNull())){
				    		buffers.append(" "+ attr.getFieldName()+" " +attr.getDataType()+ " "+"NOT NULL");
			    		}else{
				    		buffers.append(" "+ attr.getFieldName()+" " +attr.getDataType());
			    		}
			    		jdbcTemplate.execute(buffers.toString());
					}
				}
			}
		}
	}
	public List<Map<String, Object>> selectHIstiriesByFieldName(Map<String, Object> map) {
				if(null != map.get("sql") && StringUtils.isNoneEmpty(map.get("sql").toString())){
					StringBuffer buffer = new StringBuffer();
					buffer.append("select ");
					buffer.append(map.get("sql").toString());
					buffer.append(" from Part_Histories");
				    List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
					if(list.size()>0){
					    return list;
					}else{
						return null;
					}
				}else{
					StringBuffer buffer = new StringBuffer();
					buffer.append("select *");
					buffer.append(" from Part_Histories where PartNumber = '"+map.get("PartNumber")+"' order by VersionID asc");
				    List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
					if(list.size()>0){
					    return list;
					}else{
						return null;
					}
				}
	}
	public List<Map<String, Object>> selectHIstoriesDataByParam(Map<String, Object> map) {
		if(null != map.get("sql") && StringUtils.isNoneEmpty(map.get("sql").toString())){
			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			buffer.append(map.get("sql").toString());
			buffer.append(" from Part_Histories where VersionID = '"+map.get("VersionID")+"' and PartNumber = '"+map.get("PartNumber")+"'");
		    List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
			if(list.size()>0){
			    return list;
			}else{
				return null;
			}
		}
		return null;
	}
	public List<Map<String, Object>> selectPartsDataByParam(Map<String, Object> map) {
		if(null != map.get("sql") && StringUtils.isNoneEmpty(map.get("sql").toString())){
			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			buffer.append(map.get("sql").toString());
			buffer.append(" from Part_Data where PartNumber = '"+map.get("PartNumber")+"'");
		    List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
			if(list.size()>0){
			    return list;
			}else{
				return null;
			}
		}
		return null;
	}
	public List<Map<String, Object>> selectPartsDataByPartCode(Map<String, Object> map) {
		if(null != map.get("sql") && StringUtils.isNoneEmpty(map.get("sql").toString())){
			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			buffer.append(map.get("sql").toString());
			buffer.append(" from Part_Data where PartCode = '"+map.get("PartCode")+"'");
		    List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
			if(list.size()>0){
			    return list;
			}else{
				return null;
			}
		}
		return null;
	}
	public List<PartPrimaryAttributes> selectSeachField(Map<String, Object> map) {
		return partPrimaryAttributesDao.selectSeachField(map) ;
	}
	public List<Map<String, Object>> selectPartDateByPartNumber(Map<String, Object> map) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select ");
		buffer.append("*");
		buffer.append(" from Part_Data where PartNumber = '"+map.get("PartNumber")+"'");
	    List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
		if(list.size()>0){
		    return list;
		}else{
			return null;
		}
	}
	public List<Map<String, Object>> selectPartDateById(Map<String, Object> map) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select ");
		buffer.append("*");
		buffer.append(" from Part_Data where id = '"+map.get("id")+"'");
	    List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
		if(list.size()>0){
		    return list;
		}else{
			return null;
		}
	}
	public Map<String, Object> selectPartDatasAllForRedis(Map<String, Object> queryMap) {
		 Map<String,Object> mapSql = new HashMap<String,Object>();
  	     Map<String,Object> map = new HashMap<String,Object>();
  	     Map<String,Object> mapReturn = new HashMap<String,Object>();
   		 String showName = "";
		 String queryField = "";
   		 map.put("isUsed", true);
     	 List<PartPrimaryAttributes> list = partPrimaryAttributesDao.selectFieldAndName(map);
     		if(list.size()>0){
				for(PartPrimaryAttributes attr:list){
		    		if(StringUtils.isNoneEmpty(attr.getShowName())){
		    			showName+=attr.getShowName()+",";
		    		}
		            if(StringUtils.isNoneEmpty(attr.getFieldName())){
		            	queryField += attr.getFieldName()+",";
		    		}
			    }
				queryField = queryField.substring(0,queryField.length()-1);
				mapSql.put("sql", queryField);
				mapSql.put("condition", (List<String>)queryMap.get("idList"));
				List<Map<String, Object>> partDataList = this.selectDataByFieldName(mapSql);
				mapReturn.put("partDataList", partDataList);
				mapReturn.put("showName", showName);
				return mapReturn;
     		}
    		return null;
	}
	public Map<String,Object> selectAddOrUpdateField(Map<String, Object> map) {
		// 获取字段信息，根据id获取partData
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<PartPrimaryAttributes> filedList=partPrimaryAttributesDao.selectImportantField();
		resultMap.put("filedList", filedList);
		//拼接查询sql（根据id查询partData）
		String id=(String) map.get("id");
		if(id!=null){
			StringBuffer sql=new StringBuffer("select ");
			for(int i=0;i<filedList.size();i++){
				sql.append(filedList.get(i).getFieldName()+",");
			}
			sql.delete(sql.length()-1, sql.length());
			sql.append(" from part_data where id="+id+"");
			Map<String,Object> partMap=iPartDataDao.selectPartDataById(sql.toString());
			resultMap.put("partMap", partMap);
		}
		return resultMap;
	}
	public int getMaxVersionId(String partNumber) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			buffer.append("max(VersionID)");
			buffer.append(" from Part_Histories where PartNumber = '"+partNumber+"'");
		    int versionId = jdbcTemplate.queryForInt(buffer.toString());
		   return versionId+1;
	}
	public List<Map<String,Object>> selectTableField() {
		return partPrimaryAttributesDao.selectTableField();
	}
	public long selectPrimaryCount(Map<String, Object> map) {
		return partPrimaryAttributesDao.selectPrimaryCount(map);
	}
	public void updateMinudata(Map<String, Object> map) {
		StringBuffer buf = new StringBuffer();
		if(StringUtils.isNotEmpty(map.get("type").toString())){
			StringBuffer bufType = new StringBuffer();
			bufType.append("update Part_PrimaryAttributes set type = '"+map.get("type")+"' where FieldName = '"+map.get("fieldName")+"'");
			buf.append("update Part_Data set "+map.get("fieldName")+" = '"+map.get("value")+"' where PartNumber = '"+map.get("partNumber")+"'");
			jdbcTemplate.execute(bufType.toString());
			jdbcTemplate.execute(buf.toString());
		}else{
			buf.append("update Part_Data set "+map.get("fieldName")+" = '"+map.get("value")+"' where PartNumber = '"+map.get("partNumber")+"'");
			jdbcTemplate.execute(buf.toString());
		}
	}
	public Map<String,Object> selectProperiesByName(Map<String, Object> map) {
		return partPrimaryAttributesDao.selectProperiesByName(map);
	}
	public void updateState(Map<String, Object> map) {
		partPrimaryAttributesDao.updateState(map);
	}
	public void updateAlternativePart(Map<String, Object> map) {
		partPrimaryAttributesDao.updateAlternativePart(map);
	}
	public void insertFieldSelect(FieldSelect fieldSelect) {
		partPrimaryAttributesDao.insertFieldSelect(fieldSelect);
	}
	public void deleteFieldSelect(String fieldName) {
		partPrimaryAttributesDao.deleteFieldSelect(fieldName);
	}
	public List<FieldSelect> getFieldSelectByFieldName(Map<String, Object> map) {
		return partPrimaryAttributesDao.getFieldSelectByFieldName(map);
	}
	public void updateFieldAttr(PartPrimaryAttributes fieldAttr) {
		partPrimaryAttributesDao.updateFieldAttr(fieldAttr);
	}
	public List<Map<String, Object>> selectPartGopage() {
		return partPrimaryAttributesDao.selectPartGopage();
	}
	public List<Map<String, Object>> selectPartToUpdataAlternativePart(int id) {
		return partPrimaryAttributesDao.selectPartToUpdataAlternativePart(id);
	}
	public List<PartPrimaryAttributes> selectFixedInsertField(Map<String, Object> map) {
		return partPrimaryAttributesDao.selectFixedInsertField(map);
	}
	public List<PartPrimaryAttributes> selectInsertField(Map<String, Object> map) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("parameterName", "sjgj");
		List<ParameterConfig> pcList=iHrRightsDao.selectParamConfig(paramMap);
		List<PartPrimaryAttributes> l=partPrimaryAttributesDao.selectInsertField(map);
		if(pcList.size()>0){
			if(pcList.get(0).getParameterValue().equals("OrCAD")){
				for(int i=0;i<l.size();i++){
					if(l.get(i).getFieldName().equals("Sym_for_ADR")){
						l.remove(i);
						break;
					}
				}
			}else{
				for(int i=0;i<l.size();i++){
					if(l.get(i).getFieldName().equals("Sym_for_CAP")){
						l.remove(i);
						break;
					}
				}
			}
		}
		return l;
	}
	public List<PartPrimaryAttributes> selectApplyField(Map<String, Object> map) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("parameterName", "sjgj");
		List<ParameterConfig> pcList=iHrRightsDao.selectParamConfig(paramMap);
		List<PartPrimaryAttributes> l=partPrimaryAttributesDao.selectApplyField(map);
		if(pcList.size()>0){
			if(pcList.get(0).getParameterValue().equals("OrCAD")){
				for(int i=0;i<l.size();i++){
					if(l.get(i).getFieldName().equals("Sym_for_ADR")){
						l.remove(i);
						break;
					}
				}
			}else{
				for(int i=0;i<l.size();i++){
					if(l.get(i).getFieldName().equals("Sym_for_CAP")){
						l.remove(i);
						break;
					}
				}
			}
		}
		return l;
	}
	public List<Map<String, Object>> selectCompareBom(Map<String, Object> map) {
		return partPrimaryAttributesDao.selectCompareBom(map);
	}
	public List<PartPrimaryAttributes> selectUsedField(Map<String, Object> map) {
		return partPrimaryAttributesDao.selectUsedField(map);
	}
}
//批量添加字段
//if(list.size()>0){
//	for(PartPrimaryAttributes attr:list){
//		if( true == attr.isUsed()){
//			StringBuffer buffers = new StringBuffer();
//    		buffers.append("ALTER TABLE [Part_Data] ADD");
//    		buffers.append(" "+ attr.getFieldName()+" " +attr.getDataType());
//    		jdbcTemplate.execute(buffers.toString());
//		}
//	}
//}
