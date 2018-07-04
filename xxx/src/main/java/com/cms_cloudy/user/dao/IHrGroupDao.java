package com.cms_cloudy.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cms_cloudy.user.pojo.HrGroup;


public interface IHrGroupDao {
	/**
	 * 添加组
	 */
	public void insertGroup(HrGroup hrGroup);
	/**
	 * 根据组ID查询组信息
	 */
	public  HrGroup selectOneGroup(Map<String,Object> paramMap);
	/**
	 * 查询所有用户信息
	 */
	public List<HrGroup> selectAllGroup(Map<String,Object> paramMap);
    /**
     * 添加组前验证是否已存在
     */
	public int checkGroupForinsert(@Param(value="groupName")String groupName);
	
	/**
	 * 根据Id删除组数据
	 */
	public void deleteGroup(Map<String,Object> paramMap);
	/**
	 * 修改组数据
	 */
	public void updateGroup(HrGroup hrGroup);
	/**
	 * 树结构组
	 */
	public List<Map<Object, Object>> selectAllGroupTree(Map<Object, Object> map);
	
	/**
	 * 当前用户所在组查询
	 */
	public List<Map<Object, Object>> selectGroupByUserId(@Param(value="userId")long userId);
	
	/**
	 * 根据UserId删除Hr_user_group
	 */
	public void deleteUserGroupRelation(Map<String,Object> map);
	
	/**
	 * 添加---Hr_user_group
	 * @param map
	 */
	public void insertHrGroupUser(Map<String,Object> map);
	
	/**
	 * 通过页面URL获取权限组
	 * @param String
	 */
	public List<String> getGroupIds(@Param(value="url")String url);
}
