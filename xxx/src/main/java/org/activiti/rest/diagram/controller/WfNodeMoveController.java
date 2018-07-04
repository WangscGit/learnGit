package org.activiti.rest.diagram.controller;

/**
 * 转移到指定的节点
 * @author Administrator
 *
 */
public class WfNodeMoveController extends WorkflowfBaseController{

	/**
	 * 转移到指定的节点。<br>
	 * 指定任务ID,转移到的节点，转移成功保存的相关情况。
	 * 
	 * @param taskId	任务ID
	 * @param toNode	转移到的节点
	 * @param variables	转移到的任务变量,产生的任务为全局变量。
	 * @throws ActivityRequiredException
	 */
//	public void transTo(String taskId, String toNode, Map<String, Object> variables) throws ActivityRequiredException
//	{
//		TaskEntity task = getTask(taskId);
//		// 获取流程定义对象
//		ProcessDefinitionEntity processDefinition = getProcessDefinitionEntity(task.getProcessDefinitionId());
//		// 获取当前流程的活动实例
//		ActivityImpl curActi = processDefinition.findActivity(task.getTaskDefinitionKey());
//		//后续的节点设置
//		BpmNodeSet bpmNodeSet=null;
//		// 取得目标节点定义
//		ActivityImpl destAct=null;
//		//是否需要去除当前任务的后续跳转线
//		boolean isNeedRemoveTran=false;
//		//仅完成当前任务
//		if(JumpRule.RULE_INVALID.equals(toNode)){
//			isNeedRemoveTran=true;
//		}else{
//			//1.正常按定义规则跳转
//			if(StringUtils.isEmpty(toNode)){
//				//取得后续的节点是否为汇总结点
//				for(PvmTransition tran:curActi.getOutgoingTransitions()){
//					String destActId=tran.getDestination().getId();
//					bpmNodeSet=bpmNodeSetService.getByActDefIdJoinTaskKey(task.getProcessDefinitionId(), destActId);
//					if(bpmNodeSet!=null){
//						destAct=(ActivityImpl)tran.getDestination();
//						break;
//					}
//				}
//			}else{
//				destAct= processDefinition.findActivity(toNode);
//			}
//			
//			if (curActi == destAct)
//				throw new ActivitiException("不能跳转到本节点!");
//			//若目标节点为空，则代表没有进行自由跳转，同时后续的节点也不为汇总节点
//			if (destAct == null){
//				//完成当前任务
////				try{
//					/*List<Task> informTasks = taskService.createTaskQuery().executionId(task.getExecutionId()).taskDescription("通知任务").list();
//					for(Task t : informTasks){
//						((TaskEntity)t).setProcessInstanceId(null);
//						taskService.
//					}*/
//					taskService.complete(task.getId());
////				}catch(ActivitiOptimisticLockingException e){
////					taskService.complete(task.getId(), variables);
////				}
//				return;
//			}
//			
//			
//			//检查目标任务是否为汇总节点，若为汇总节点，需要拿到当前任务的令牌，检查该令牌对应的TaskFork的已经汇总的任务个数，若目前的任务为最后一个汇总，则删除TaskFork，并且任务进行下一步的跳转，
//			//若目前任务不是最后一个汇总，则需要更新汇总完成的任务个数，并且只是完成当前任务，不作跳转。	
//			if(bpmNodeSet==null){
//				bpmNodeSet=bpmNodeSetService.getByActDefIdJoinTaskKey(task.getProcessDefinitionId(), destAct.getId());
//			}
//			if(bpmNodeSet!=null){//目前该目标任务为分发汇总
//				//取当前任务的分发令牌
//				String token=(String)taskService.getVariableLocal(task.getId(),TaskFork.TAKEN_VAR_NAME);
//				if(token!=null){
//					TaskFork taskFork =taskForkService.getByInstIdJoinTaskKeyForkToken(task.getProcessInstanceId(), destAct.getId(), token);
//					if(taskFork!=null){
//						if(taskFork.getFininshCount()<taskFork.getForkCount()-1){
//							//更新完成任务的个数
//							taskFork.setFininshCount(taskFork.getFininshCount()+1);
//							taskForkService.update(taskFork);
//							//更新Token
//							String[]tokenSplits=token.split("[_]");
//							if(tokenSplits.length==2){//若为最外层的汇总，格式如T_1,则需要删除该令牌
//								taskService.setVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME, null);
//							}
//							isNeedRemoveTran=true;
//						}else{
//							String executionId = task.getExecutionId();
//							taskForkService.delById(taskFork.getTaskForkId());
//							//更新Token
//							String[]tokenSplits=token.split("[_]");
//							if(tokenSplits.length==2){//若为最外层的汇总，格式如T_1,则需要删除该令牌
//								taskService.setVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME, null);
//								String instanceId = task.getProcessInstanceId();
//								ExecutionEntity ent = (ExecutionEntity)this.executionExtDao.getById(executionId);
//								ActivityImpl curAct = processDefinition.findActivity(ent.getActivityId());
//								ExecutionEntity processInstance = (ExecutionEntity)this.executionExtDao.getById(instanceId);
//								processInstance.setActivity(curAct);
//								
//								this.executionExtDao.update(processInstance);
//								
//								//将任务的excution的id更新为流程实例ID
//								taskDao.updTaskExecution(taskId);
//								
//								//删除token变量。
//								executionDao.delTokenVarByTaskId(taskId,TaskFork.TAKEN_VAR_NAME);
//								
//								this.executionDao.delVarsByExecutionId(executionId);
//								
//								//删除execution。
//								executionDao.delExecutionById(executionId);
//								
//							}else if(tokenSplits.length>=3){//更新token，转换如：T_1_1转成T_1
//								String newToken=token.substring(0, token.lastIndexOf("_"+tokenSplits[tokenSplits.length-1]));
//								
//								this.executionStackDao.udpTaskTokenByTaskIdNodeId(newToken, task.getId(), taskFork.getForkTaskKey());
//								taskService.setVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME, newToken);
//							}
//
//							
//
//						}
//						
//					}
//				}
//			}
//		}// end of if(JumpRule.RULE_INVALID.equals(toNode))
//		
//		//记录原来的跳转线
//		List<PvmTransition> backTransList = new ArrayList<PvmTransition>();
//		backTransList.addAll(curActi.getOutgoingTransitions());
//		try{
//			//进行锁定
//			lockTransto.lock();
//			// 清除当前任务的外出节点
//			curActi.getOutgoingTransitions().clear();
//			if(!isNeedRemoveTran){
//				//创建一个连接
//				TransitionImpl transitionImpl = curActi.createOutgoingTransition();
//				transitionImpl.setDestination(destAct);
//			}
//			//完成当前任务
//			taskService.complete(task.getId(), variables);
//			
//		}
//		finally{
//			// 删除创建的输出
//			curActi.getOutgoingTransitions().clear();
//			//加回原来的旧连接
//			curActi.getOutgoingTransitions().addAll(backTransList);
//			//解除锁定。
//			lockTransto.unlock();
//
//		}
//		
//	}
}
