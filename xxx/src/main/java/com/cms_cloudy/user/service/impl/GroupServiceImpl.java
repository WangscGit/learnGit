package com.cms_cloudy.user.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cms_cloudy.user.dao.IHrGroupDao;
import com.cms_cloudy.user.dao.IHrRightsDao;
import com.cms_cloudy.user.dao.UserDao;
import com.cms_cloudy.user.pojo.HrGroup;
import com.cms_cloudy.user.pojo.HrRightsGroup;
import com.cms_cloudy.user.service.IHrGroupService;
import com.github.pagehelper.PageHelper;
@Component("iGroupService")
public class GroupServiceImpl implements IHrGroupService {
	@Autowired
	private IHrGroupDao iHrGroupDao;
	@Autowired
	private IHrRightsDao iHrRightsDao;
	@Autowired
	private UserDao userDao;
	public HrGroup selectOneGroup(Map<String, Object> paramMap) {
		return iHrGroupDao.selectOneGroup(paramMap);
	}

	public List<HrGroup> selectAllGroup(Map<String, Object> paramMap) {
		return iHrGroupDao.selectAllGroup(paramMap);
	}

	public int deleteGroup(List<Object> list) {
		for(int i=0;i<list.size();i++){
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("groupId", list.get(i));
			//删除组信息
			iHrGroupDao.deleteGroup(paramMap);
			//删除权限组关系
			HrRightsGroup hrRightsGroup=new HrRightsGroup();
			hrRightsGroup.setGroupId(Integer.parseInt(list.get(i).toString()));
			iHrRightsDao.deleteHrRightsGroup(hrRightsGroup);
			//删除组用户关系
			userDao.deleteUserGroup(Long.valueOf(list.get(i).toString()));
		}
		return 1;
	}

	public void updateGroup(HrGroup hrGroup) {
		iHrGroupDao.updateGroup(hrGroup);
	}

	public int insertGroup(HrGroup hrGroup) {
		int coun=iHrGroupDao.checkGroupForinsert(hrGroup.getGroupName());
		if(coun>0){
			return 0;
		}
		iHrGroupDao.insertGroup(hrGroup);
		return 1;
	}

	public List<HrGroup> selectAllGroupByPage(Map<String, Object> paramMap) {
		PageHelper.startPage(Integer.valueOf(paramMap.get("pageNo").toString()), Integer.valueOf(paramMap.get("pageSize").toString()));
		return iHrGroupDao.selectAllGroup(paramMap);
	}

	public List<Map<Object, Object>> selectAllGroupTree(Map<Object, Object> map) {
		// TODO Auto-generated method stub
		return iHrGroupDao.selectAllGroupTree(map);
	}

	public int checkGroupForinsert(String groupName) {
		return iHrGroupDao.checkGroupForinsert(groupName);
	}

	public List<Map<Object, Object>> selectGroupByUserId(long userId) {
		return iHrGroupDao.selectGroupByUserId(userId);
	}

	public void deleteUserGroupRelation(Map<String, Object> map) {
		iHrGroupDao.deleteUserGroupRelation(map);
	}

	public void insertHrGroupUser(Map<String, Object> map) {
		iHrGroupDao.insertHrGroupUser(map);
	}

	public List<String> getGroupIds(String url) {
		return iHrGroupDao.getGroupIds(url);
	}
}
