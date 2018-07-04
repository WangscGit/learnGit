package com.cms_cloudy.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cms_cloudy.user.dao.UserDao;
import com.cms_cloudy.user.pojo.HrGroupUser;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.UserService;

@org.springframework.stereotype.Component("UserService")
public class UserServiceImpl implements  UserService{
	@Autowired
	private UserDao userDao;
	public List<HrUser> selectUser(Integer id) {
		return userDao.selectUser(id);
	}
	public void insertUser(HrUser user) {
		userDao.insertUser(user);
	}
	public List<HrUser> selectUserByPage(Map<String,Object> map) {
		return userDao.selectUserByPage(map);
	}
	public long countUser() {
		return userDao.countUser();
	}
	public int updateUser(HrUser user) {
		return userDao.updateUser(user);
	}
	public List<HrUser> selectUserByName(String userName) {
		return userDao.selectUserByName(userName);
	}
	public int deleteUser(int id) {
		return userDao.deleteUser(id);
	}
	public List<HrUser> selectAllUser(Map<String, Object> map) {
		return userDao.selectAllUser(map);
	}
	public List<HrUser> selectUserToExport(Map<String,Object> map) {
		return userDao.selectUserToExport(map);
	}
	public List<HrGroupUser> selectAllUserByGroup(Map<String, Object> map) {
		return userDao.selectAllUserByGroup(map);
	}
	public List<HrUser> selectUserByGroup(Map<String, Object> map) {
		return userDao.selectUserByGroup(map);
	}
	public void saveGroupUser(List<Object> list,Long groupId){
		//先删除组里的所有用户再重新添加
		userDao.deleteUserGroup(groupId);
		for(int i=0;i<list.size();i++){
			HrGroupUser hgu=new HrGroupUser();
			hgu.setGroupId(Integer.valueOf(groupId.toString()));
			hgu.setUserId(Integer.valueOf(list.get(i).toString()));
			userDao.insertHrGroupUser(hgu);
		}
		
	}
	public int deleteUserFromGroup(int userId) {
		// TODO Auto-generated method stub
		return userDao.deleteUserFromGroup(userId);
	}
}
