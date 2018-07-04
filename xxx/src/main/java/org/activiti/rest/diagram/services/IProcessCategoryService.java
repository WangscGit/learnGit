package org.activiti.rest.diagram.services;

import java.util.List;
import java.util.Map;

import org.activiti.rest.diagram.pojo.ProcessCategory;

public interface IProcessCategoryService {

	/**
	 * 流程类别信息查询
	 * 
	 * @param map
	 * @return
	 */
	public List<ProcessCategory> selectProcessCategoryList(Map<String, Object> map);

	/**
	 * 流程类别信息添加
	 * 
	 * @param pc
	 */
	public void insertProcessCategory(ProcessCategory pc);

	/**
	 * 流程类别信息更新
	 * 
	 * @param pc
	 */
	public void updateProcessCategory(ProcessCategory pc);

	/**
	 * 流程类别信息删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteProcessCategory(long id);
}
