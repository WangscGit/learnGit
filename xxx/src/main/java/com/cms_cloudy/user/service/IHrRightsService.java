package com.cms_cloudy.user.service;

import java.util.List;
import java.util.Map;
import com.cms_cloudy.user.pojo.HrRights;
import com.cms_cloudy.user.pojo.HrRightsGroup;
import com.cms_cloudy.user.pojo.ParameterConfig;
import com.cms_cloudy.user.pojo.RightsTree;

public interface IHrRightsService {
	
	/**
	 * 查询权限信息
	 */
	public  List<RightsTree> selectHrRightsList(Map<String,Object> paramMap);
	
	/**
	 * 根据Id删除权限
	 * @return 
	 */
	public int deleteHrRights(Map<String,Object> paramMap);
	
	/**
	 * 添加权限数据
	 */
	public int insertHrRights(HrRights hrRights);
	
	/**
	 * 修改权限节点
	 */
	public void updateHrRights(HrRights hrRights);
	
	/**
	 * 添加权限用户关系
	 */
	public void insertHrRightsGroup(HrRightsGroup hrRightsGroup);
	
	/**
	 * 删除权限用户关系
	 */
	public void deleteHrRightsGroup(HrRightsGroup hrRightsGroup);
	
	/**
	 * 获取所有权限，组包含的权限在树中默认选中
	 */
	public List<RightsTree> selectHrRightsByGroupId(Map<String,Object> paramMap);
	
	/**
	 * 根据登录名获取权限
	 */
	public List<HrRights> selectUserRights(Map<String,Object> paramMap);
	/**
	 * 根据参数名查询系统参数
	 */
	public List<ParameterConfig> selectParamConfig(Map<String,Object> paramMap);
	/**
	 * 根据参数名删除系统参数
	 */
	public void deleteParamConfig(ParameterConfig parameterConfig);
	/**
	 * 添加系统参数
	 */
	public void insertParamConfig(ParameterConfig parameterConfig);
}
