package org.activiti.rest.diagram.services.impl;

import org.activiti.rest.diagram.dao.IWorlkflowMainEntityDao;
import org.activiti.rest.diagram.pojo.WorkflowTaskEntitiy;
import org.activiti.rest.diagram.pojo.WorlkflowMainEntity;
import org.activiti.rest.diagram.services.IWorlkflowMainEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component(value="iWorlkflowMainEntityService")
public class WorlkflowMainEntityServiceImpl implements IWorlkflowMainEntityService {
	@Autowired
	private IWorlkflowMainEntityDao iWorldflowMainEntityDao;
	public WorlkflowMainEntity getWfmByProdefId(WorlkflowMainEntity wme) {
		return iWorldflowMainEntityDao.getWfmByProdefId(wme);
	}

	public WorkflowTaskEntitiy getWteByProInsId(WorkflowTaskEntitiy wte) {
		return iWorldflowMainEntityDao.getWteByProInsId(wte);
	}

	public WorkflowTaskEntitiy getWteByProInstanceId(WorkflowTaskEntitiy wte) {
		return iWorldflowMainEntityDao.getWteByProInstanceId(wte);
	}

}
