package com.cms_cloudy.component.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cms_cloudy.component.dao.IUserPnDao;
import com.cms_cloudy.component.pojo.UserPn;
import com.cms_cloudy.component.service.IUserPnService;

@Component("IUserPnService")
public class UserPnServiceImpl implements IUserPnService {

	@Autowired
	private IUserPnDao userPnDao;
	
	public List<UserPn> selectUserPnList(Map<String, Object> map) {
		return userPnDao.selectUserPnList(map);
	}

	public void insertUserPn(UserPn userPn) {
		userPnDao.insertUserPn(userPn);
	}

	public int deleteUserPn(Map<String,Object> map) {
		return userPnDao.deleteUserPn(map);
	}

}
