package org.activiti.rest.diagram.services.impl;

import java.util.List;
import java.util.Map;

import org.activiti.rest.diagram.dao.IProcessCategoryDao;
import org.activiti.rest.diagram.pojo.ProcessCategory;
import org.activiti.rest.diagram.services.IProcessCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("IProcessCategoryService")
public class ProcessCategoryServiceImpl implements IProcessCategoryService {

	@Autowired
	private IProcessCategoryDao processCategoryDao;

	public List<ProcessCategory> selectProcessCategoryList(Map<String, Object> map) {
		return processCategoryDao.selectProcessCategoryList(map);
	}

	public void insertProcessCategory(ProcessCategory pc) {
		processCategoryDao.insertProcessCategory(pc);
	}

	public void updateProcessCategory(ProcessCategory pc) {
		processCategoryDao.updateProcessCategory(pc);
	}

	public int deleteProcessCategory(long id) {
		return processCategoryDao.deleteProcessCategory(id);
	}

}
