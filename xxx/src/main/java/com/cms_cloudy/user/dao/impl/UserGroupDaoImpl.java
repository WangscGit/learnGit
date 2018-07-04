package com.cms_cloudy.user.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cms_cloudy.user.dao.UserGroupDao;
import com.cms_cloudy.user.pojo.HrGroup;
import com.cms_cloudy.user.pojo.HrGroupUser;
import com.cms_cloudy.user.pojo.HrUser;

@Component("UserGroupDao")
public class UserGroupDaoImpl implements UserGroupDao {
	/*自动注入这里spring管理了mybatis的sqlsessionfactory 
	 */  
	@Autowired  
	private SqlSessionTemplate sqlSession; 
	public void insertUserGroup(HrGroupUser relation) {
		sqlSession.insert("com.cms_cloudy.dao.UserGroupDao.insertUserGroup", relation);
	}

	public List<HrUser> selectUserByGroupId(int groupId) {
		return sqlSession.selectList("com.cms_cloudy.dao.UserGroupDao.selectUserByGroupId", groupId);
	}

	public List<HrGroup> selectGroupByUserId(int userId) {
		return sqlSession.selectList("com.cms_cloudy.dao.UserGroupDao.selectGroupByUserId", userId);
	}

}
