package com.cms_cloudy.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cms_cloudy.user.dao.IHrRightsDao;
import com.cms_cloudy.user.pojo.HrRights;
import com.cms_cloudy.user.pojo.HrRightsGroup;
import com.cms_cloudy.user.pojo.ParameterConfig;
import com.cms_cloudy.user.pojo.RightsTree;
import com.cms_cloudy.user.service.IHrRightsService;
@Component("iHrRightsService")
public class HrRightsServiceImpl implements IHrRightsService {
	@Autowired
	private IHrRightsDao iHrRightsDao;
	
	public List<RightsTree> selectHrRightsList(Map<String, Object> paramMap) {
		return iHrRightsDao.selectHrRightsList(paramMap);
	}

	public int deleteHrRights(Map<String, Object> paramMap) {
		// 删除前验证是否有子节点
		int coun=iHrRightsDao.checkHrRightsForDelete(paramMap);
		if(coun>0){
			return coun;
		}
		iHrRightsDao.deleteHrRights(paramMap);
		//删除权限组关系
		HrRightsGroup hrRightsGroup=new HrRightsGroup();
		hrRightsGroup.setRightsId((Integer)paramMap.get("id"));
		iHrRightsDao.deleteHrRightsGroup(hrRightsGroup);
		return 0;
	}

	public int insertHrRights(HrRights hrRights) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("rightsName", hrRights.getRightsName());
		int coun=iHrRightsDao.checkHrRightsForInsert(map);
		if(coun>0){
			return 0;
		}else{
			iHrRightsDao.insertHrRights(hrRights);
			return hrRights.getId();
		}
	}

	public void updateHrRights(HrRights hrRights) {
		iHrRightsDao.updateHrRights(hrRights);
	}

	public void insertHrRightsGroup(HrRightsGroup hrRightsGroup) {
		iHrRightsDao.insertHrRightsGroup(hrRightsGroup);
	}

	public void deleteHrRightsGroup(HrRightsGroup hrRightsGroup) {
		iHrRightsDao.deleteHrRightsGroup(hrRightsGroup);
	}

	public List<RightsTree> selectHrRightsByGroupId(Map<String, Object> paramMap) {
		return iHrRightsDao.selectHrRightsByGroupId(paramMap);
	}

	public List<HrRights> selectUserRights(Map<String, Object> paramMap) {
		List<HrRights> rightList=iHrRightsDao.selectUserRights(paramMap);
		return rightList;
	}

	public List<ParameterConfig> selectParamConfig(Map<String, Object> paramMap) {
		return iHrRightsDao.selectParamConfig(paramMap);
	}

	public void deleteParamConfig(ParameterConfig parameterConfig) {
		iHrRightsDao.deleteParamConfig(parameterConfig);
	}

	public void insertParamConfig(ParameterConfig parameterConfig) {
		iHrRightsDao.insertParamConfig(parameterConfig);
	}

}
