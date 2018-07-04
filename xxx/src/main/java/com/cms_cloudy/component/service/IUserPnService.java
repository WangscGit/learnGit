package com.cms_cloudy.component.service;

import java.util.List;
import java.util.Map;

import com.cms_cloudy.component.pojo.UserPn;

/**
 * 常用库Service
 * @author WangSc
 *
 */
public interface IUserPnService {

	/**查询常用库(我的收藏)集合**/
	public List<UserPn> selectUserPnList(Map<String,Object> map);
	/**添加常用库(我的收藏)**/
	public void insertUserPn(UserPn userPn);
	/**删除常用库(我的收藏)**/
	public int deleteUserPn(Map<String,Object> map);
}
