package com.cms_cloudy.database.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cms_cloudy.component.dao.IPartDataDao;
import com.cms_cloudy.database.dao.IPartClassDao;
import com.cms_cloudy.database.pojo.FileImg;
import com.cms_cloudy.database.pojo.PartClass;
import com.cms_cloudy.database.pojo.PartDefineAttributes;
import com.cms_cloudy.database.pojo.PartTypeTree;
import com.cms_cloudy.database.service.IPartClassService;
import com.cms_cloudy.util.PropertiesUtil;

@Component("IPartClassService")
public class PartClassServiceImpl implements IPartClassService {

	@Autowired
	private IPartClassDao iPartClassDao;
	@Autowired
	private IPartDataDao iPartDataDao;
	@Resource
    private  JdbcTemplate jdbcTemplate;
	public List<Map<String,Object>> selectPartClass(Map<String, Object> map) {
		List<Map<String,Object>> partList=iPartClassDao.selectPartClass(map);
		return partList;
	}

	public void insertPartClass(PartClass p) {
		iPartClassDao.insertPartClass(p);
	}

	public void updatePartClass(PartClass p) {
		 iPartClassDao.updatePartClass(p);
	}

	public void deletePartClass(int id) {
		 iPartClassDao.deletePartClass(id);
	}

	public void insertPartDefineAttributes(PartDefineAttributes p) {
		iPartClassDao.insertPartDefineAttributes(p);
	}

	public void updatePartDefineAttributes(PartDefineAttributes p) {
		iPartClassDao.updatePartDefineAttributes(p);
	}

	public int selectPartDefineAttributes(int classId) {
		return iPartClassDao.selectPartDefineAttributes(classId);
	}

	public void deletePartDefineAttributes(int classId) {
		iPartClassDao.deletePartDefineAttributes(classId);
	}

	public int childNumIsRepeat(Map<String, Object> paramMap) {
		return iPartClassDao.childNumIsRepeat(paramMap);
	}

	public List<Map<String, Object>> getPnCode(Map<String, Object> parentNumMap) {
		return iPartClassDao.getPnCode(parentNumMap);
	}

	public List<Map<String,Object>> getNodeByPnCode(Map<String, Object> parentNumMap) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		StringBuffer whereSql=new StringBuffer();
		whereSql.append("where  1=1");
		if(parentNumMap!=null&&parentNumMap.size()>0){
			String item=(String) parentNumMap.get("item");
			String tempPartMark=(String) parentNumMap.get("tempPartMark");
//			String filed1=(String) parentNumMap.get("filed1");
//			String filed2=(String) parentNumMap.get("filed2");
//			String filed3=(String) parentNumMap.get("filed3");
			String str=(String) parentNumMap.get("str");
			String startDate=(String) parentNumMap.get("startDate");
			String endDate=(String) parentNumMap.get("endDate");
			
			if(StringUtils.isNotEmpty(item)){
				whereSql.append(" and (pd.item like ");
				item=item.replace("%CE%A9", "Ω");
				whereSql.append("'%"+item+"%'");
				whereSql.append(" or pd.Part_Type like " );
				whereSql.append("'%"+item+"%'");
				whereSql.append(" or pd.Manufacturer like " );
				whereSql.append("'%"+item+"%'");
				whereSql.append(" or pd.KeyComponent like " );
				whereSql.append("'%"+item+"%'");
				whereSql.append(" or pd.Datesheet like " );
				whereSql.append("'%"+item+"%' )");
			}
			if(StringUtils.isNotEmpty(tempPartMark)){
				whereSql.append(" and pd.tempPartMark='"+tempPartMark+"' ");
			}
			if(StringUtils.isNotEmpty(startDate)){
				whereSql.append(" and pd.CreateDate >='"+startDate+"' ");
			}
			if(StringUtils.isNotEmpty(endDate)){
				whereSql.append(" and pd.CreateDate <='"+endDate+"' ");
			}

			if(StringUtils.isNotEmpty(str)){
				whereSql.append(" and "+str);
			}
		}
		whereSql.append(" and (pd.process_state is NULL or  pd.process_state not in ('审批中','未启动')) ");
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		paramMap.put("sql", whereSql.toString());
		List<PartTypeTree> partTypeList=iPartDataDao.getPartType(paramMap);
		getSheetTree(list,partTypeList);
		
		Collections.sort(list, new Comparator<Map<String,Object>>(){
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				String p1=(String) o1.get("id");
				String p2=(String) o2.get("id");
				int pnum1=0;
				int pnum2=0;
				if(!StringUtils.isEmpty(p1)){
					if(p1.length()>=6){
						p1=p1.substring(0, 6);
					}
					pnum1=Integer.valueOf(p1);
				}
				if(!StringUtils.isEmpty(p2)){
					if(p2.length()>=6){
						p2=p2.substring(0, 6);
					}
					pnum2=Integer.valueOf(p2);
				}
				return pnum1-pnum2;
			}
			
		});
		return list;
	}

	private void getSheetTree(List<Map<String,Object>> l,List<PartTypeTree> partTypeList) {
		Map<String,Object> pmap=new HashMap<String,Object>();
		List<Map<String,Object>> list=iPartClassDao.selectPartClass(pmap);
		List<PartTypeTree> pList=getPartTreeList(partTypeList);
		List<Map<String,Object>> pMapList=new ArrayList<Map<String,Object>>();
		List<PartTypeTree> nodeList=new ArrayList<PartTypeTree>();
		for(int i=0;i<pList.size();i++){
			PartTypeTree ppt=new PartTypeTree();
			if(StringUtils.isEmpty(pList.get(i).getParentNum())){
				ppt=pList.get(i);
				nodeList.add(ppt);
			}
		}
		
		getZtreeNodes(pMapList,nodeList);
		Iterator<Map<String, Object>> iterator = list.iterator();
		while(iterator.hasNext()){
			Map<String,Object> partClass=iterator.next();
			partClass.put("name", partClass.get("name")+"※0");
//			if(partClass.get("pId").equals("0")){//分类信息的根节点
//				partClass.put("pId",firstMap.get("pId"));
//			}
			for(int j=0;j<pMapList.size();j++){
				if(partClass.get("id").equals(pMapList.get(j).get("id"))){
					iterator.remove();
					break;
				}
			}
		}
		list.addAll(0, pMapList);
		l.addAll(l.size(), list);
	}
	public void getZtreeNodes(List<Map<String,Object>> pMapList,List<PartTypeTree> nodeList){
		for(int i=0;i<nodeList.size();i++){
			PartTypeTree ppt=nodeList.get(i);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("name", ppt.getPartType()+"※"+ppt.getCoun());
			map.put("id", ppt.getPnCode());
			map.put("pId", ppt.getParentNum());
			map.put("tempPartMark", ppt.isTempPartMark());
			map.put("imgUrl", ppt.getImgUrl());
			pMapList.add(map);
			if(ppt.getPartList()!=null&&ppt.getPartList().size()>0){
				getZtreeNodes(pMapList,ppt.getPartList());
			}
		}
	}
	//获取分类树
	public List<PartTypeTree> getPartTreeList(List<PartTypeTree> partTypeList) {
		List<PartTypeTree> nodesList=new ArrayList<PartTypeTree>();
		//构造分类信息表中没有的节点
		PartTypeTree p=new PartTypeTree();
		p.setPnCode("99999");
		p.setCoun(0);
		p.setPartType("NoPartType");
		p.setPartList(new ArrayList<PartTypeTree>());
		nodesList.add(p);
		
		int len=partTypeList.size();
		for(int i=0;i<len;i++){
			PartTypeTree ptt=partTypeList.get(i);
			if(StringUtils.isEmpty(ptt.getParentNum())){//跟节点，或者分类表中无数据的节点
				if(StringUtils.isEmpty(ptt.getPnCode())){//分类表中无数据的节点,构造树
					PartTypeTree np=new PartTypeTree();
					np.setParentNum("99999");
					np.setCoun(ptt.getCoun());
					np.setPartType(ptt.getPartType());
					np.setPnCode("00000");
					nodesList.get(0).setCoun(nodesList.get(0).getCoun()+np.getCoun());
					nodesList.get(0).getPartList().add(np);
					continue;
				}
				int lock=0;
				for(int j=0;j<nodesList.size();j++){
					if(ptt.getPnCode().equals(nodesList.get(j).getPnCode())){
						nodesList.get(j).setCoun(nodesList.get(j).getCoun()+ptt.getCoun());
						lock=1;
						break;
					}
				}
				if(lock==0){
					nodesList.add(ptt);
					continue;
				}
			}
			findAllParentNode(nodesList,ptt);
		}
		return nodesList;
	}
	//递归获取父节点
	public void findAllParentNode(List<PartTypeTree> nodesList,PartTypeTree partTypeTree){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("pnCode", partTypeTree.getParentNum());
		List<Map<String,Object>> list=iPartClassDao.getNodeByPnCode(paramMap);
		if(list==null||list.size()==0){
			return;
		}
		String pnCode=(String) list.get(0).get("PnCode");
		String parentNum=(String)list.get(0).get("parentNum");
		PartTypeTree ptt=new PartTypeTree();
		ptt.setParentNum(parentNum);
		ptt.setPnCode(pnCode);
		ptt.setPartType((String)list.get(0).get("partType"));
		ptt.setImgUrl((String)list.get(0).get("imgUrl"));
		ptt.setCoun(partTypeTree.getCoun());
		ptt.setPartList(new ArrayList<PartTypeTree>());
		int lock=0;
		int lock1=0;
		for(int i=0;i<nodesList.size();i++){
			PartTypeTree oneMap=nodesList.get(i);
			if(StringUtils.isEmpty(oneMap.getPnCode())){//分类信息表中没有的分类
				continue;
			}
			if(oneMap.getPnCode().equals(pnCode)){
				oneMap.setCoun(0);
				for(int j=0;j<oneMap.getPartList().size();j++){
					if(oneMap.getPartList().get(j).getPnCode().equals(partTypeTree.getPnCode())){
						oneMap.getPartList().get(j).setCoun(partTypeTree.getCoun());
						lock1=1;
						break;
					}
				}
				if(lock1==0){
					oneMap.getPartList().add(partTypeTree);
				}
				for(int j=0;j<oneMap.getPartList().size();j++){
					oneMap.setCoun(oneMap.getCoun()+oneMap.getPartList().get(j).getCoun());
				}
				if(StringUtils.isEmpty(oneMap.getParentNum())){
					return;
				}else{
					findAllParentNode(nodesList,oneMap);
				}
				lock=1;
				break;
			}
		}
		
		if(lock==0){
			ptt.getPartList().add(partTypeTree);
			nodesList.add(ptt);
			if(StringUtils.isEmpty(parentNum)){//根节点
				return;
			}else{
				findAllParentNode(nodesList,ptt);
			}
		}

	}
	
	public List<PartTypeTree> getNodeByCoun(Map<String, Object> parentNumMap) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		if(parentNumMap!=null&&parentNumMap.size()>0){
			String item=(String) parentNumMap.get("item");
			String tempPartMark=(String) parentNumMap.get("tempPartMark");
//			String filed1=(String) parentNumMap.get("filed1");
//			String filed2=(String) parentNumMap.get("filed2");
//			String filed3=(String) parentNumMap.get("filed3");
			String str=(String) parentNumMap.get("str");
			String startDate=(String) parentNumMap.get("startDate");
			String endDate=(String) parentNumMap.get("endDate");
			StringBuffer whereSql=new StringBuffer();
			whereSql.append("where 1=1 ");
			if(StringUtils.isNotEmpty(item)){
				whereSql.append(" and (pd.item like ");
				item=item.replace("%CE%A9", "Ω");
				whereSql.append("'%"+item+"%'");
				whereSql.append(" or pd.Part_Type like " );
				whereSql.append("'%"+item+"%'");
				whereSql.append(" or pd.Manufacturer like " );
				whereSql.append("'%"+item+"%'");
				whereSql.append(" or pd.KeyComponent like " );
				whereSql.append("'%"+item+"%'");
				whereSql.append(" or pd.Datesheet like " );
				whereSql.append("'%"+item+"%' )");
			}
			if(StringUtils.isNotEmpty(tempPartMark)){
				whereSql.append(" and pd.tempPartMark='"+tempPartMark+"' ");
			}
			if(StringUtils.isNotEmpty(startDate)){
				whereSql.append(" and pd.CreateDate >='"+startDate+"' ");
			}
			if(StringUtils.isNotEmpty(endDate)){
				whereSql.append(" and pd.CreateDate <='"+endDate+"' ");
			}
//			if(StringUtils.isNotEmpty(filed1)){
//				whereSql.append(" and "+filed1);
//			}
//			if(StringUtils.isNotEmpty(filed2)){
//				whereSql.append(" and "+filed2);
//			}
//			if(StringUtils.isNotEmpty(filed3)){
//				whereSql.append(" and "+filed3);
//			}
			if(StringUtils.isNotEmpty(str)){
				whereSql.append(" and "+str);
			}
			paramMap.put("sql", whereSql.toString());
		}
		
		List<PartTypeTree> partTypeList=iPartDataDao.getPartType(paramMap);
		List<PartTypeTree> pList=new ArrayList<PartTypeTree>();
		pList=getPartTreeListCoun(partTypeList);
		return pList;
	}

	// 获取二级树
	public List<PartTypeTree> getPartTreeListCoun(List<PartTypeTree> partTypeList) {
		List<PartTypeTree> pList = new ArrayList<PartTypeTree>();
		// 获取根节点pnCode
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("parentNum", "");
		List<Map<String, Object>> firstNodeList = iPartClassDao.getPnCode(m);
		List<String> pnList = new ArrayList<String>();
		for (int i = 0; i < firstNodeList.size(); i++) {
			pnList.add((String) firstNodeList.get(i).get("pnCode"));
		}
		int len = partTypeList.size();
		for (int i = 0; i < len; i++) {
			PartTypeTree ptt = partTypeList.get(i);
			ptt.setPartList(new ArrayList<PartTypeTree>());
			String parentNum = ptt.getParentNum();
			PartTypeTree pTree = new PartTypeTree();
			if (StringUtils.isEmpty(parentNum)) {
				pList.add(ptt);
				continue;
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pnCode", parentNum);
				findSecNode(map, pTree, pnList);
				if (pTree.getPartType() == null) {
					pList.add(ptt);
				} else {
					for (int j = 0; j < pList.size(); j++) {// 判断集合中是否已存在此二级节点，
						PartTypeTree temp = pList.get(j);
						if(temp.getPartType()==null){
							continue;
						}
						if (temp.getPartType().equals(pTree.getPartType())) {
							temp.setCoun(temp.getCoun() + ptt.getCoun());
							temp.getPartList().add(ptt);
							break;
						} else if (j == pList.size() - 1) {
							pTree.setCoun(pTree.getCoun() + ptt.getCoun());
							pTree.getPartList().add(ptt);
							pList.add(pTree);
							break;
						}
					}
				}
				// pList中没值时，加入pTree
				if (pList.size() == 0 && pTree.getPartType() != null) {
					pTree.setCoun(pTree.getCoun() + ptt.getCoun());
					pTree.getPartList().add(ptt);
					pList.add(pTree);
				}
			}

		}
		return pList;
	}

	// 递归查询二级节点
	public void findSecNode(Map<String, Object> map, PartTypeTree ptt, List<String> pnList) {
		List<Map<String, Object>> list = iPartClassDao.getNodeByPnCode(map);
		String parentNum = (String) list.get(0).get("parentNum");
		if (StringUtils.isNotEmpty(parentNum)) {
			if (pnList.contains(parentNum)) {
				ptt.setParentNum(parentNum);
				ptt.setPnCode((String) list.get(0).get("PnCode"));
				ptt.setPartType((String) list.get(0).get("partType"));
				ptt.setPartList(new ArrayList<PartTypeTree>());
				return;
			} else {
				Map<String, Object> pnMap = new HashMap<String, Object>();
				pnMap.put("pnCode", parentNum);
				findSecNode(pnMap, ptt, pnList);
			}
		}
	}

	public void insertFileImg(FileImg fileImg) {
		iPartClassDao.insertFileImg(fileImg);
	}

	public void deleteFileImg(long partClassId) {
		iPartClassDao.deleteFileImg(partClassId);
	}

	public List<FileImg> selectImgByName(Map<String, String> map) {
		return iPartClassDao.selectImgByName(map);
	}
	//生成物料编码
	public  synchronized String getPartNumber(List<Long> ids){
		StringBuffer partNumber=new StringBuffer();
		int index=0;//记录第几个业务码取第几个id
		PropertiesUtil properties=new PropertiesUtil(Thread.currentThread().getContextClassLoader().getResource("code_rules.properties").getPath());
		List<Object> keyList=properties.getKeyList();//properties的按顺序取得key
		for(Object o:keyList){
			if(o.toString().indexOf("tableFiled")!=-1){//业务码
				Long id=0l;
				//获取业务id
				if(ids.size()==0){
					continue;
				}
				if(ids.size()>index){
					id=ids.get(index);
					index++;
				}else{
					id=ids.get(index-1);
				}
				/**************************************/
				//获取业务码的值
				try{
					String value=properties.getProperty(o.toString());
					String[] values=value.split(",");
					String sql="select "+values[1]+" from "+values[0]+" where id="+id;
					Map<String,Object> map=jdbcTemplate.queryForMap(sql);
					if(map==null){
						continue;
					}else{
						partNumber.append(map.get(values[1])==null?"":map.get(values[1]));
					}
				}catch(Exception e){
					e.printStackTrace();
					throw new RuntimeException("配置文件中业务码格式有误");
				}
			}else if(o.toString().indexOf("flowCode")!=-1){//流水码
				try{
					String value=properties.getProperty(o.toString());
					String sql="select max(max_number) maxNumber from flow_code";//查询最大值
					Map<String,Object> map=jdbcTemplate.queryForMap(sql);
					Object maxNumber =map.get("maxNumber");
//					Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
//					if(!pattern.matcher(value).matches()){
//						throw new RuntimeException("配置文件中流水码格式有误");
//					}
					int i=Integer.valueOf(value);
					String max="";
					String zero="";
					for(;i>0;i--){
						max+="9";
						zero+="0";
					}
					if(Integer.valueOf(max)<Integer.valueOf(maxNumber.toString())){//数据库存的最大值大于配置的位数最大值时从1开始累计
						partNumber.append(zero.substring(0, zero.length()-1)+"1");
						String updateSql="update flow_code  set max_number=1";
						jdbcTemplate.update(updateSql);
					}else{
						Integer in=(Integer.valueOf(maxNumber.toString())+1);
						String s=in.toString();
						while(s.length()<Integer.valueOf(value)){
							s="0"+s;
						}
						partNumber.append(s);
						String updateSql="update flow_code  set max_number="+in;
						jdbcTemplate.update(updateSql);
					}
				}catch(Exception e){
					throw new RuntimeException("配置文件中流水码格式有误");
				}
			}else if(o.toString().indexOf("dateCode")!=-1){//日期码
				try{
					String value=properties.getProperty(o.toString());
					SimpleDateFormat sdf=new SimpleDateFormat(value);
					partNumber.append(sdf.format(new Date()));
				}catch(Exception e){
					throw new RuntimeException("配置文件中日期码格式有误");
				}
			}else{//固定码、分隔符
				String value=properties.getProperty(o.toString());
				partNumber.append(value);
			}
		}
		return partNumber.toString().replace(" ", "");
	}
}
