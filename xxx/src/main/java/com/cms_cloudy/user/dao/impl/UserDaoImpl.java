package com.cms_cloudy.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.cms_cloudy.user.dao.UserDao;
import com.cms_cloudy.user.pojo.HrGroupUser;
import com.cms_cloudy.user.pojo.HrUser;


/** 
 * repository 说明就是一个dao层的注解 
 */  
@org.springframework.stereotype.Component("UserDao")
public class UserDaoImpl implements UserDao{
	/*自动注入这里spring管理了mybatis的sqlsessionfactory 
	 */  
	@Autowired  
	private SqlSessionTemplate sqlSession; 
	/** 
	 * 查询用户信息 
	 * @param id 
	 * @return 
	 */  
	public List<HrUser> selectUser(Integer id) { 
	    return sqlSession.selectList("com.cms_cloudy.user.dao.UserDao.selectUser", id) ;  
	}
	public void insertUser(HrUser user) {
		sqlSession.insert("com.cms_cloudy.user.dao.UserDao.insertUser", user);
	}
	public List<HrUser> selectUserByPage(Map<String,Object> map) {
		
		return sqlSession.selectList("com.cms_cloudy.user.dao.UserDao.selectUserByPage",map);
	}
	public long countUser() {
		return sqlSession.selectOne("com.cms_cloudy.user.dao.UserDao.countUser");
	}
	public int updateUser(HrUser user) {
		return sqlSession.update("com.cms_cloudy.user.dao.UserDao.updateUser",user);
	}
	public List<HrUser> selectUserByName(String userName) {
		return sqlSession.selectList("com.cms_cloudy.user.dao.UserDao.selectUserByName",userName);
	}
	public int deleteUser(int id) {
		return sqlSession.delete("com.cms_cloudy.user.dao.UserDao.deleteUser",id);
	}
	public List<HrUser> selectAllUser(Map<String, Object> map) {
		return sqlSession.selectList("com.cms_cloudy.user.dao.UserDao.selectAllUser",map);
	}
	public List<HrUser> selectUserToExport(Map<String, Object> map) {
		return sqlSession.selectList("com.cms_cloudy.user.dao.UserDao.selectUserToExport",map);
	}
	public List<HrGroupUser> selectAllUserByGroup(Map<String, Object> map) {
		return sqlSession.selectList("com.cms_cloudy.user.dao.UserDao.selectAllUserByGroup");
	}
	public List<HrUser> selectUserByGroup(Map<String, Object> map) {
		return sqlSession.selectList("com.cms_cloudy.user.dao.UserDao.selectUserByGroup");
	}
	public void deleteUserGroup(long groupId) {
		sqlSession.selectList("com.cms_cloudy.user.dao.UserDao.deleteUserGroup");
	}
	public void insertHrGroupUser(HrGroupUser hgu) {
		sqlSession.selectList("com.cms_cloudy.user.dao.UserDao.insertHrGroupUser");
	}
	public int deleteUserFromGroup(int userId) {
		// TODO Auto-generated method stub
		return sqlSession.delete("com.cms_cloudy.user.dao.UserDao.deleteUserFromGroup",userId);
	} 
}
