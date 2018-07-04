package com.cms_cloudy.user.service;

import java.util.List;

import com.cms_cloudy.user.pojo.HrGroup;
import com.cms_cloudy.user.pojo.HrGroupUser;
import com.cms_cloudy.user.pojo.HrUser;


public interface UserGroupService {
	
	/**
	 * 用户与用户组关系表数据添加
	 * @param relation
	 */
	public void insertUserGroup(HrGroupUser relation);
	/**
	 * 根据用户组ID，查询这个组下所有的用户
	 * @param groupId
	 * @return
	 */
    public List<HrUser> selectUserByGroupId(int groupId);
    /**
     * 根据用户ID，查询用户所对应的用户组
     * @param userId
     * @return
     */
    public List<HrGroup> selectGroupByUserId(int userId);
}
