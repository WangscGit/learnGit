package com.cms_cloudy.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cms_cloudy.user.dao.UserGroupDao;
import com.cms_cloudy.user.pojo.HrGroup;
import com.cms_cloudy.user.pojo.HrGroupUser;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.UserGroupService;

@Component("UserGroupService")
public class UserGroupServiceImpl implements UserGroupService {
    @Autowired
    private UserGroupDao userGroupDao;
	public void insertUserGroup(HrGroupUser relation) {
		userGroupDao.insertUserGroup(relation);
	}

	public List<HrUser> selectUserByGroupId(int groupId) {
		return userGroupDao.selectUserByGroupId(groupId);
	}

	public List<HrGroup> selectGroupByUserId(int userId) {
		return userGroupDao.selectGroupByUserId(userId);
	}

}
