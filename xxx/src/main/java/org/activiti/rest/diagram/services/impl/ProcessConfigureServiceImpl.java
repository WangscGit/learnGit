package org.activiti.rest.diagram.services.impl;

import java.util.List;
import org.activiti.rest.diagram.dao.IProcessConfigureDao;
import org.activiti.rest.diagram.pojo.ProcessConfigure;
import org.activiti.rest.diagram.services.IProcessConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("iProcessConfigureService")
public class ProcessConfigureServiceImpl implements IProcessConfigureService{
	@Autowired
	private IProcessConfigureDao iProcessConfigureDao;
	
	public List<ProcessConfigure> selectProcessConfigureList(ProcessConfigure param) {
		return iProcessConfigureDao.selectProcessConfigureList(param);
	}

	public void updateProcessConfigure(ProcessConfigure processConfigure) {
		iProcessConfigureDao.updateProcessConfigure(processConfigure);
	}

	public void insertProcessConfigure(ProcessConfigure processConfigure) {
		iProcessConfigureDao.insertProcessConfigure(processConfigure);
	}

	public void deleteProcessConfigure(ProcessConfigure processConfigure) {
		iProcessConfigureDao.deleteProcessConfigure(processConfigure);
	}
	
}
