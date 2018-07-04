package org.activiti.rest.diagram.dao.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

@SuppressWarnings("serial")
public class Wf_askListennerImpl implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		//可以设置个人任务也可以设置组任务
		delegateTask.setAssignee("linjunjie");
	}

}
