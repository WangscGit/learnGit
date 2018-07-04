package org.activiti.rest.diagram.services;

import java.util.List;
import org.activiti.rest.diagram.pojo.ProcessConfigure;

public interface IProcessConfigureService {
	/** 获取流程配置信息 **/
	public List<ProcessConfigure> selectProcessConfigureList(ProcessConfigure param);

	/** 修改流程配置信息 **/
	public void updateProcessConfigure(ProcessConfigure processConfigure);

	/** 添加流程配置信息 **/
	public void insertProcessConfigure(ProcessConfigure processConfigure);

	/** 删除流程配置信息 **/
	public void deleteProcessConfigure(ProcessConfigure processConfigure);

}
