package com.cms_cloudy.user.service;

import java.util.List;
import java.util.Map;

import com.cms_cloudy.user.pojo.HrGroupUser;
import com.cms_cloudy.user.pojo.HrUser;


public interface UserService {
	public List<HrUser> selectUser(Integer id);
	/**
	 * 添加用户信息
	 * @param user
	 */
	public void insertUser(HrUser user);
	/**
	 * 分页查询人员信息
	 * @param map
	 * @return
	 */
	public List<HrUser> selectUserByPage(Map<String,Object> map);
	/**
	 * 查询用户人数
	 * @return
	 */
	public long countUser();
	/**
	 * 修改用户信息
	 */
	public int updateUser(HrUser user);
	/**
	 * 根据登录名查询用户信息
	 * @param userName
	 * @return
	 */
	public List<HrUser> selectUserByName(String userName);
	/**
	 * 删除用户
	 * @param id
	 */
	public int deleteUser(int id);
	/**
	 * 查询全部用户
	 * @param map
	 * @return
	 */
	public List<HrUser> selectAllUser(Map<String,Object> map);
	/**
	 * 导出数据
	 * @return
	 */
	public List<HrUser> selectUserToExport(Map<String,Object> map);
	/**
	 * 通过组查询用户ID
	 * @param map
	 * @return
	 */
	public List<HrGroupUser> selectAllUserByGroup(Map<String,Object> map);
	/**
	 * 通过组查询用户
	 * @param map
	 * @return
	 */
	public List<HrUser> selectUserByGroup(Map<String,Object> map);
	
	/**
	 * 保存用户组信息
	 */
	public void saveGroupUser(List<Object> list,Long groupId);
	/**
	 * 人员组关系删除
	 * @return
	 */
	public int deleteUserFromGroup(int userId);
}
