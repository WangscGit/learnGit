package org.activiti.rest.diagram.controller;

import com.cms_cloudy.controller.BaseController;

public class WorkflowReturnController extends BaseController{
	
	
	/** 
	  * 将节点之后的节点删除然后指向新的节点。  
	  * @param actDefId   流程定义ID 
	  * @param nodeId   流程节点ID 
	  * @param aryDestination 需要跳转的节点 
	  * @return Map<String,Object> 返回节点和需要恢复节点的集合。 
	  */
//	public Map<String,Object> prepareFreeFlow(String actDefId,String nodeId,String[] aryDestination){
//		 Map<String,Object> map=new HashMap<String, Object>();  
//		    
//		  //修改流程定义  
//		  ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(actDefId);  
//		    
//		  ActivityImpl curAct= processDefinition.findActivity(nodeId);  
//		  List<PvmTransition> outTrans= curAct.getOutgoingTransitions();  
//		  try{  
////		   List<PvmTransition> cloneOutTrans=(List<PvmTransition>) FileUtil.cloneObject(outTrans);  
//		   map.put("outTrans", outTrans);  
//		  }  
//		  catch(Exception ex){  
//		     
//		  }  
//		    
//		  /** 
//		   * 解决通过选择自由跳转指向同步节点导致的流程终止的问题。 
//		   * 在目标节点中删除指向自己的流转。 
//		   */  
//		  for(Iterator<PvmTransition> it=outTrans.iterator();it.hasNext();){  
//		   PvmTransition transition=it.next();  
//		   PvmActivity activity= transition.getDestination();  
//		   List<PvmTransition> inTrans= activity.getIncomingTransitions();  
//		   for(Iterator<PvmTransition> itIn=inTrans.iterator();itIn.hasNext();){  
//		    PvmTransition inTransition=itIn.next();  
//		    if(inTransition.getSource().getId().equals(curAct.getId())){  
//		     itIn.remove();  
//		    }  
//		   }  
//		  }  
//		    
//		    
//		  curAct.getOutgoingTransitions().clear();  
//		    
//		  if(aryDestination!=null && aryDestination.length>0){  
//		   for(String dest:aryDestination){  
//		    //创建一个连接  
//		    ActivityImpl destAct= processDefinition.findActivity(dest);  
//		    TransitionImpl transitionImpl = curAct.createOutgoingTransition();  
//		    transitionImpl.setDestination(destAct);  
//		   }  
//		  }  
//		  map.put("activity", curAct);  
//		  return map;  
//    }
//	/** 
//	  * 将临时节点清除掉，加回原来的节点。 
//	  * @param map  
//	  * void 
//	  */  
//	 @SuppressWarnings("unchecked")  
//	 private void restore(Map<String,Object> map){  
//	  ActivityImpl curAct=(ActivityImpl) map.get("activity");  
//	  List<PvmTransition> outTrans=(List<PvmTransition>) map.get("outTrans");  
//	  curAct.getOutgoingTransitions().clear();  
//	  curAct.getOutgoingTransitions().addAll(outTrans);  
//	 }  
//	  
//	 /** 
//	  * 通过指定目标节点，实现任务的跳转 
//	  * @param taskId 任务ID 
//	  * @param destNodeIds 跳至的目标节点ID 
//	  * @param vars 流程变量 
//	  */  
//	 public synchronized void completeTask(String taskId,String[] destNodeIds,Map<String,Object> vars) {  
//	  TaskEntity task=(TaskEntity)taskService.createTaskQuery().taskId(taskId).singleResult();  
//	    
//	  String curNodeId=task.getTaskDefinitionKey();  
//	  String actDefId=task.getProcessDefinitionId();  
//	    
//	  Map<String,Object> activityMap= prepareFreeFlow(actDefId, curNodeId, destNodeIds);  
//	  try{  
//	   taskService.complete(taskId);  
//	  }  
//	  catch(Exception ex){  
//	   throw new RuntimeException(ex);  
//	  }  
//	  finally{  
//	   //恢复  
//	   restore(activityMap);  
//	  } 
//	 }
}
