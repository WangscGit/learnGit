package org.activiti.rest.diagram.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.rest.diagram.dao.ITaskUserDao;
import org.activiti.rest.diagram.dao.IWorkflowBaseDao;
import org.activiti.rest.diagram.pojo.WorkflowTaskEntitiy;
import org.activiti.rest.diagram.pojo.WorlkflowMainEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component("IWorkflowBaseDao")
public class WorkflowBaseDaoImpl implements IWorkflowBaseDao{
	
    //流程引擎初始化
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Autowired
	private ITaskUserDao iTaskUserDao;
	@Resource  
    JdbcTemplate jdbcTemplate;
	/** 
     * 取得流程定义的XML 
     *  
     * @param deployId 
     * @return 
     */  
    public String getDefXmlByDeployId(String deployId){  
        String sql = "select a.* from ACT_GE_BYTEARRAY a where ID_= ? ";  
        final LobHandler lobHandler = new DefaultLobHandler(); // reusable  
        final ByteArrayOutputStream contentOs = new ByteArrayOutputStream();  
        String defXml = null;  
        try{  
        	jdbcTemplate.query(sql, new Object[]{deployId },new AbstractLobStreamingResultSetExtractor<Object>(){  
                        public void streamData(ResultSet rs) throws SQLException, IOException{  
                            FileCopyUtils.copy(lobHandler.getBlobAsBinaryStream(rs, "BYTES_"), contentOs);  
                        }  
                }  
            );  
            defXml = new String(contentOs.toByteArray(), "UTF-8");  
        } catch (Exception ex){
            ex.printStackTrace();  
        }
        return defXml;  
    }
	public void insertWorkflowMain(WorlkflowMainEntity processEntity) {
	 String sql = "INSERT [dbo].[WF_MAIN_ENTITY] ([process_definition_id], [process_instance_id], [process_state], [cdefine1], [cdefine2], [cdefine3], [cdefine4], [cdefine5], [cdefine6], [cdefine7], [cdefine8], [model_id], [deployment_id], [process_task_id], [process_name], [process_key], [process_description], [process_create_time], [process_version], [process_create_person]) VALUES (?,?,?,?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	Object[] args = new Object[20];
	args[0] = processEntity.getProcessDefinitionId();
	args[1]=processEntity.getProcessInstanceId();
	args[2]=processEntity.getProcessState();
	args[3]=processEntity.getCdefine1();
	args[4]=processEntity.getCdefine2();
	args[5]=processEntity.getCdefine3();
	args[6]=processEntity.getCdefine4();
	args[7]=processEntity.getCdefine5();
	args[8]=processEntity.getCdefine6();
	args[9]=processEntity.getCdefine7();
	args[10]=processEntity.getCdefine8();
	args[11]=processEntity.getModelId();
    args[12]=processEntity.getDeploymentId();
    args[13]=processEntity.getProcessTaskId();
    args[14]=processEntity.getProcessName();
    args[15]=processEntity.getProcessKey();
    args[16]=processEntity.getProcessDescription();
    args[17]=processEntity.getProcessCreateTime();
    args[18]=processEntity.getProcessVersion();
    args[19]=processEntity.getProcessCreatePerson();
	 jdbcTemplate.update(sql,args);
//	 String idSql = "SELECT LAST_INSERT_ID()";
//	  int id = getJdbcTemplate().queryForInt(idSql);
//	  stage.setId(id);
	}
	public List<WorlkflowMainEntity> selectBasicDeploymentProcess(WorlkflowMainEntity processEntity)  {
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select * from WF_MAIN_ENTITY");
		if(null != processEntity && !"".equals(processEntity.getProcessKey()) && null != processEntity.getProcessKey()){
		 sqlBuff.append(" where process_key like '%"+processEntity.getProcessKey()+"%' or process_name like '%"+processEntity.getProcessKey()+"%'");			
		}
		sqlBuff.append(" order by id desc");
		
	    List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlBuff.toString());
	    List<WorlkflowMainEntity> lists = new ArrayList<WorlkflowMainEntity>();
	    if ((list == null) || (list.size() <= 0))
	    {
	      return null;
	    }else{
	    	for(int i =0;i<list.size();i++){
	    		WorlkflowMainEntity process= new WorlkflowMainEntity() ;
	    		Map<String, Object> map = (Map<String, Object>)list.get(i);
	    		 process = mapToMainbean(process,map);
	    		lists.add(process);
	    	}
	    }
	    return lists;
	}
	public List<WorlkflowMainEntity> selectBasicDeploymentProcessByPage(Map<String,Object> paramMap)  {
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append( "select top "+paramMap.get("pageSize")+" o.* from (select row_number() over(order by id desc) as rownumber,* from WF_MAIN_ENTITY) as o where 1=1 and");
	    if(null != paramMap.get("searchMsg") && !"".equals(paramMap.get("searchMsg").toString())){
			sqlBuff.append(" process_key like '%"+paramMap.get("searchMsg").toString()+"%' or process_name like '%"+paramMap.get("searchMsg").toString()+"%' and");			
	    }
		sqlBuff.append(" rownumber>"+(Integer.valueOf(paramMap.get("pageNo").toString())-1)*Integer.valueOf(paramMap.get("pageSize").toString()));
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlBuff.toString());
	    List<WorlkflowMainEntity> lists = new ArrayList<WorlkflowMainEntity>();
	    if ((list == null) || (list.size() <= 0))
	    {
	      return null;
	    }else{
	    	for(int i =0;i<list.size();i++){
	    		WorlkflowMainEntity process= new WorlkflowMainEntity() ;
	    		Map<String, Object> map = (Map<String, Object>)list.get(i);
	    		if(null != map.get("cdefine3") && !"".equals(map.get("cdefine3").toString())){
	    			map = processCategoryHandle(map);
	    		}else{
	    			map.put("cdefine3", "");
	    		}
	    		 process = mapToMainbean(process,map);
	    		lists.add(process);
	    	}
	    }
	    return lists;
	}
	public List<ProcessDefinition> selectProcessDefinitionList(String deploymentId) {
		List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()//创建一个流程实例查询
                .deploymentId(deploymentId)//部署对象ID查询
                .orderByProcessDefinitionVersion().desc()//按版本升序排序
                .list();//返回一个封装流程定义的集合
		return list;
	}
	public void updateWorkflowMain(WorlkflowMainEntity processEntity) {
		 String sql = "UPDATE WF_MAIN_ENTITY SET process_instance_id = '"+processEntity.getProcessInstanceId()+"' , process_state = '"+processEntity.getProcessState()+"'WHERE id = " + processEntity.getId();
		 jdbcTemplate.update(sql);
	}
	public WorlkflowMainEntity selectWorkflowBaseServiceById(Long id) {
		String sql = "select * from WF_MAIN_ENTITY WHERE id = '"+id+"'";
		 List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
	    List<WorlkflowMainEntity> lists = new ArrayList<WorlkflowMainEntity>();
	    if ((list == null) || (list.size() <= 0))
	    {
	      return null;
	    }else{
	    	for(int i =0;i<list.size();i++){
	    		WorlkflowMainEntity process= new WorlkflowMainEntity() ;
	    		Map<String, Object> map = (Map<String, Object>)list.get(i);
	    		 process = mapToMainbean(process,map);
	    		lists.add(process);
	    	}
	    }
	    return lists.get(0);
	}  
     public WorlkflowMainEntity mapToMainbean(WorlkflowMainEntity process,Map<String, Object> map){
    	process.setId(Integer.parseInt(map.get("id").toString()));
 		process.setProcessName(map.get("process_name")==null ? "" :map.get("process_name").toString());
 		process.setModelId(map.get("model_id")==null?"":map.get("model_id").toString());
 		process.setDeploymentId(map.get("deployment_id")==null?"":map.get("deployment_id").toString());
 		process.setProcessDefinitionId(map.get("process_definition_id")==null?"":map.get("process_definition_id").toString());
 		process.setProcessInstanceId(map.get("process_instance_id")==null?"":map.get("process_instance_id").toString());
 		process.setProcessTaskId(map.get("process_task_id")==null?"":map.get("process_task_id").toString());
 		process.setProcessKey(map.get("process_key")==null?"":map.get("process_key").toString());
 		process.setProcessDescription(map.get("process_description")==null?"":map.get("process_description").toString());
 		process.setProcessCreateTime(map.get("process_create_time")==null?"":map.get("process_create_time").toString());
 		process.setProcessVersion(map.get("process_version")==null?"":map.get("process_version").toString());
 		process.setProcessState(map.get("process_state")==null?"":map.get("process_state").toString());
 		process.setProcessCreatePerson(map.get("process_create_person")==null?"":map.get("process_create_person").toString());
 		process.setCdefine1(map.get("cdefine1")==null?"":map.get("cdefine1").toString());
 		process.setCdefine2(map.get("cdefine2")==null?"":map.get("cdefine2").toString());
 		process.setCdefine3(map.get("cdefine3")==null?"":map.get("cdefine3").toString());
 		process.setCdefine4(map.get("cdefine4")==null?"":map.get("cdefine4").toString());
 		process.setCdefine5(map.get("cdefine5")==null?"":map.get("cdefine5").toString());
 		process.setCdefine6(Integer.parseInt(map.get("cdefine6").toString()));
 		process.setCdefine7(Integer.parseInt(map.get("cdefine7").toString()));
 		process.setCdefine8(Integer.parseInt(map.get("cdefine8").toString()));
 		process.setCategorySign(map.get("categorySign")==null?"":map.get("categorySign").toString());
    	 return process;
     }
     public WorkflowTaskEntitiy mapToTaskbean(WorkflowTaskEntitiy task,Map<String, Object> map){
    	    task.setId(Integer.parseInt(map.get("id").toString()));
    	    task.setProcessMainId(Integer.parseInt(map.get("Process_Main_Id").toString()));
    	    task.setProcessNodeTaskname(map.get("process_Node_Taskname")==null?"":map.get("process_Node_Taskname").toString());
    	    task.setProcessTaskState(map.get("process_Task_State")==null?"":map.get("process_Task_State").toString());
    	    task.setProcessTaskPerson(map.get("process_Task_Person")==null?"":map.get("process_Task_Person").toString());
    	    task.setProcessAssignees(map.get("process_Assignees")==null?"":map.get("process_Assignees").toString());
    	    task.setProcessGroups(map.get("process_Groups")==null?"":map.get("process_Groups").toString());
    	    task.setProcessTaskStarttime(map.get("process_Task_Starttime")==null?"":map.get("process_Task_Starttime").toString());
    	    task.setProcessTaskExpirtime(map.get("process_Task_Expirtime")==null?"":map.get("process_Task_Expirtime").toString());
    	    task.setProcessButton(map.get("process_Button")==null?"":map.get("process_Button").toString());
    	    task.setProcessUrl(map.get("process_Url")==null?"":map.get("process_Url").toString());
    	    task.setProcessAproveAdvice(map.get("process_Aprove_Advice")==null?"":map.get("process_Aprove_Advice").toString());
    	    task.setCdefine1(map.get("cdefine1")==null?"":map.get("cdefine1").toString());
    	    task.setCdefine2(map.get("cdefine2")==null?"":map.get("cdefine2").toString());
    	    task.setCdefine3(map.get("cdefine3")==null?"":map.get("cdefine3").toString());
    	    task.setCdefine4(map.get("cdefine4")==null?"":map.get("cdefine4").toString());
    	    task.setCdefine5(map.get("cdefine5")==null?"":map.get("cdefine5").toString());
    	    task.setCdefine6(Integer.parseInt(map.get("cdefine6").toString()));
    	    task.setCdefine7(Integer.parseInt(map.get("cdefine7").toString()));
    	    task.setCdefine8(Integer.parseInt(map.get("cdefine8").toString()));
    	    /**冗余字段**/
    	    task.setProcessKey(map.get("process_Key")==null?"":map.get("process_Key").toString());
    	    task.setProcessName(map.get("process_Name")==null?"":map.get("process_Name").toString());
    	    task.setProcessCreatePerson(map.get("process_Create_Person")==null?"":map.get("process_Create_Person").toString());
    	    task.setTaskName(map.get("taskName")==null?"":map.get("taskName").toString());
    	 return task;
     }
	public void insertWorkflowTaskEntitiy(WorkflowTaskEntitiy taskEntity) {
		 String sql = "INSERT [dbo].[WF_TASK_ENTITY] ([process_main_id], [process_node_taskname], [process_task_state], [process_task_person], [process_assignees], [process_groups], [process_task_starttime], [process_task_expirtime], [process_button], [process_url], [process_aprove_advice], [cdefine1], [cdefine2], [cdefine3], [cdefine4], [cdefine5], [cdefine6], [cdefine7], [cdefine8]) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] args = new Object[19];
			args[0] = taskEntity.getProcessMainId();
			args[1] = taskEntity.getProcessNodeTaskname();
	        args[2] = taskEntity.getProcessTaskState();
			args[3] = taskEntity.getProcessTaskPerson();
			args[4] = taskEntity.getProcessAssignees();
			args[5] = taskEntity.getProcessGroups();
			args[6] = taskEntity.getProcessTaskStarttime();
			args[7] = taskEntity.getProcessTaskExpirtime();
			args[8] = taskEntity.getProcessButton();
			args[9] = taskEntity.getProcessUrl();
			args[10] = taskEntity.getProcessAproveAdvice();
			args[11] = taskEntity.getCdefine1();
			args[12] = taskEntity.getCdefine2();
			args[13] = taskEntity.getCdefine3();
			args[14] = taskEntity.getCdefine4();
			args[15] = taskEntity.getCdefine5();
			args[16] = taskEntity.getCdefine6();
			args[17] = taskEntity.getCdefine7();
			args[18] = taskEntity.getCdefine8();
			 jdbcTemplate.update(sql,args);
	}
	public List<WorkflowTaskEntitiy> selectWorkflowTaskEntitiyList(WorkflowTaskEntitiy taskEntity) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,m.process_key as process_key,m.cdefine3 as taskName,m.process_name as process_name,m.process_create_person as process_Task_Person from WF_TASK_ENTITY t left join  WF_MAIN_ENTITY m on t.process_main_id = m.id where 1=1");
		if(null != taskEntity && !"".equals(taskEntity.getProcessTaskState()) && null != taskEntity.getProcessTaskState()){
			sql.append(" and t.process_Task_State like  '%" + taskEntity.getProcessTaskState() + "%'");
		}
		List param = new ArrayList();
		if(StringUtils.isNotEmpty(taskEntity.getProcessAssignees())){
		     sql.append( " AND T.process_Assignees like '%" +taskEntity.getProcessAssignees()+ "%'");
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
			    List<WorkflowTaskEntitiy> lists = new ArrayList<WorkflowTaskEntitiy>();
			    if ((list == null) || (list.size() <= 0))
			    {
			      return null;
			    }else{
			    	for(int i =0;i<list.size();i++){
			    		WorkflowTaskEntitiy process= new WorkflowTaskEntitiy() ;
			    		Map<String, Object> map = (Map<String, Object>)list.get(i);
			    		 process = mapToTaskbean(process,map);
			    		lists.add(process);
			    	}
			    }
			   return lists;
	}
	public Map<String,Object> countTaskByUser(Map<String,Object> paramMap){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//人员审批节点数量统计
		StringBuffer sql=new StringBuffer();
		sql.append("select count(aht.ID_) cou,tu.user_Login_Name,hu.user_name name, cast(sum(datediff(hour,tu.create_Time,tu.end_Time))/(count(aht.ID_)*1.0) as decimal(18,2)) num from ACT_HI_TASKINST aht,task_user tu,hr_user hu where aht.TASK_DEF_KEY_=tu.task_Def_Key and aht.PROC_INST_ID_=tu.process_Inst_Id and tu.user_Login_Name=hu.login_name  group by tu.user_Login_Name,hu.user_name");
		List<Map<String,Object>> numList=jdbcTemplate.queryForList(sql.toString());
		resultMap.put("taskCount", numList);
		return resultMap;
	}
	public void updateWorkflowTaskEntity(WorkflowTaskEntitiy task) {
		String sql = "update  WF_TASK_ENTITY set process_assignees = '"+task.getProcessAssignees()+"',cdefine1 = '"+task.getCdefine1()+"', process_aprove_advice = '"+task.getProcessAproveAdvice()+"'  where id = '"+task.getId()+"'";
		jdbcTemplate.update(sql);
	}
	public void updateWorkflowMainToState(WorlkflowMainEntity main) {
		String sql = "UPDATE WF_MAIN_ENTITY SET process_state = '"+main.getProcessState()+"' WHERE id = " + main.getId();
		 jdbcTemplate.update(sql);
	}
	public void updateWorkflowTaskEntitiyToState(WorkflowTaskEntitiy main) {
		String sql = "UPDATE WF_TASK_ENTITY SET process_task_state = '"+main.getProcessTaskState()+"' WHERE id = " + main.getId();
		jdbcTemplate.update(sql);
	}
	public WorkflowTaskEntitiy selectWorkflowTaskServiceById(Long id) {
				String sql = "select * from dbo.WF_TASK_ENTITY WHERE id = '"+id+"'";
				 List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			    List<WorkflowTaskEntitiy> lists = new ArrayList<WorkflowTaskEntitiy>();
			    if ((list == null) || (list.size() <= 0))
			    {
			      return null;
			    }else{
			    	for(int i =0;i<list.size();i++){
			    		WorkflowTaskEntitiy process= new WorkflowTaskEntitiy() ;
			    		Map<String, Object> map = (Map<String, Object>)list.get(i);
			    		 process = mapToTaskbean(process,map);
			    		lists.add(process);
			    	}
			    }
			    return lists.get(0);
	}
	public List<WorlkflowMainEntity> selectProcessMain(WorlkflowMainEntity main) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from WF_MAIN_ENTITY where 1=1");
		if(null != main && 0 != main.getId()){
			sql.append(" and id = '"+main.getId()+"'");
		}
		if(null != main && null != main.getCdefine3() && !"".equals(main.getCdefine3())){
			sql.append(" and cdefine3 = '"+main.getCdefine3()+"'");
		}
		sql.append("order by id desc");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
	    List<WorlkflowMainEntity> lists = new ArrayList<WorlkflowMainEntity>();
	    if ((list == null) || (list.size() <= 0))
	    {
	      return null;
	    }else{
	    	for(int i =0;i<list.size();i++){
	    		WorlkflowMainEntity process= new WorlkflowMainEntity() ;
	    		Map<String, Object> map = (Map<String, Object>)list.get(i);
	    		 process = mapToMainbean(process,map);
	    		lists.add(process);
	    	}
	    }
	    return lists;
	}
	public void updateProcessMain(WorlkflowMainEntity main) {
		StringBuffer sql = new StringBuffer();
		StringBuffer update = new StringBuffer();
		sql.append("update WF_MAIN_ENTITY set ");
		if(null != main && null != main.getCdefine3() && !"".equals(main.getCdefine3())){
			update.append(" cdefine3 = '"+main.getCdefine3()+"',");
		}
		String s = update.toString().substring(0,update.toString().length()-1);
		sql.append(s);
		sql.append(" where id = '"+main.getId()+"'");
		jdbcTemplate.update(sql.toString());
	}
	public void updateProcessTask(WorkflowTaskEntitiy task) {
		StringBuffer sql = new StringBuffer();
		StringBuffer update = new StringBuffer();
		sql.append("update WF_TASK_ENTITY set ");
		if(null != task && 0 != task.getProcessMainId()){
			update.append(" process_main_id = '"+task.getProcessMainId()+"',");
		}
		if(null != task && null != task.getProcessAssignees() && !"".equals(task.getProcessAssignees())){
			update.append(" process_assignees = '"+task.getProcessAssignees()+"',");
		}
		if(null != task && null != task.getProcessTaskState() && !"".equals(task.getProcessTaskState())){
			update.append(" process_task_state = '"+task.getProcessTaskState()+"',");
		}
		if(null != task && null != task.getCdefine2() && !"".equals(task.getCdefine2())){
			update.append(" cdefine2 = '"+task.getCdefine2()+"',");
		}
		if(null != task && null != task.getCdefine3() && !"".equals(task.getCdefine3())){
			update.append(" cdefine3 = '"+task.getCdefine3()+"',");
		}
		if(null != task && null != task.getCdefine4() && !"".equals(task.getCdefine4())){
			update.append(" cdefine4 = '"+task.getCdefine4()+"',");
		}
		if(null != task && null != task.getProcessTaskStarttime() && !"".equals(task.getProcessTaskStarttime())){
			update.append(" process_task_starttime = '"+task.getProcessTaskStarttime()+"',");
		}
		if(null != task && null != task.getProcessTaskPerson() && !"".equals(task.getProcessTaskPerson())){
			update.append(" process_task_person = '"+task.getProcessTaskPerson()+"',");
		}
		String s = update.toString().substring(0,update.toString().length()-1);
		sql.append(s);
		sql.append(" where id = '"+task.getId()+"'");
		if(StringUtils.isNotEmpty(task.getCdefine4()) && 0!= task.getProcessMainId()){//改变业务表流程状态
			String mainSql = "select cdefine3 from WF_MAIN_ENTITY where id = "+task.getProcessMainId();
			List<Map<String,Object>> mainList = jdbcTemplate.queryForList(mainSql);
			if(null != mainList && mainList.size()>0){
				if("0".equals(mainList.get(0).get("cdefine3"))){//类别：元器件申请
					String businessSql = "update Part_Data set process_state ='"+"审批中"+"' where id="+task.getCdefine4();
			    	jdbcTemplate.update(businessSql);
				}
			}
		}
		jdbcTemplate.update(sql.toString());
	}
	public Map<String, Object> selectProcessTaskByPage(Map<String, Object> map) {
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd"); //HH表示24小时制；  
		Map<String, Object> mapResult = new HashMap<String,Object>();
		WorkflowTaskEntitiy taskEntity = (WorkflowTaskEntitiy) map.get("task");
		StringBuffer sqlCount= new StringBuffer();
		StringBuffer sqlList= new StringBuffer();
		sqlCount.append("select count(1) as count from WF_TASK_ENTITY T left join  WF_MAIN_ENTITY m on t.process_main_id = m.id left join  Part_Data p on p.id = t.cdefine4 where 1=1");
		sqlList.append( "select top "+map.get("pageSize")+" o.* from (select row_number() over(order by t.id desc) as rownumber,t.*,p.ITEM as item,m.process_key as process_key,m.cdefine3 as taskName,m.process_name as process_name,m.process_create_person as processTaskPerson from WF_TASK_ENTITY t left join  WF_MAIN_ENTITY m on t.process_main_id = m.id left join  Part_Data p on p.id = t.cdefine4 where 1=1");
		if(null != taskEntity){
			if(null != taskEntity && !"".equals(taskEntity.getProcessTaskState()) && null != taskEntity.getProcessTaskState()){
				sqlCount.append(" and t.process_Task_State like  '%" + taskEntity.getProcessTaskState() + "%'");
				sqlList.append(" and t.process_Task_State like  '%" + taskEntity.getProcessTaskState() + "%'");
			}
			if(null != taskEntity && !"".equals(taskEntity.getProcessName()) && null != taskEntity.getProcessName()){
				String PCSql = "select category_sign from Process_Category where category_name like  '%" + taskEntity.getProcessName() + "%'";
				List<Map<String,Object>> pcl = this.jdbcTemplate.queryForList(PCSql);
				String categorySign = "";
				for(int n=0;n<pcl.size();n++){
					String str = pcl.get(n).get("category_sign").toString().equals("partProcess")?"0":pcl.get(n).get("category_sign").toString();
					categorySign += "'"+str+"'"+",";
				}
				if(StringUtils.isNotEmpty(categorySign)){
					categorySign = categorySign.substring(0,categorySign.length()-1);
					sqlCount.append(" and p.ITEM like  '%" + taskEntity.getProcessName() + "%' or m.cdefine3 in ("+categorySign+")");
					sqlList.append("  and p.ITEM like  '%" + taskEntity.getProcessName() + "%' or m.cdefine3 in ("+categorySign+")");
				}else{
					sqlCount.append(" and p.ITEM like  '%" + taskEntity.getProcessName() + "%'");
					sqlList.append("  and p.ITEM like  '%" + taskEntity.getProcessName() + "%'");
				}
//				sqlCount.append(" and m.process_name like  '%" + taskEntity.getProcessName() + "%'");
//				sqlList.append(" and m.process_name like  '%" + taskEntity.getProcessName() + "%'");
			}
			if(null != taskEntity && !"".equals(taskEntity.getProcesCategory()) && null != taskEntity.getProcesCategory()){
				sqlCount.append(" and m.cdefine3 like  '%" + taskEntity.getProcesCategory() + "%'");
				sqlList.append(" and m.cdefine3 like  '%" + taskEntity.getProcesCategory() + "%'");
			}
			if(null != taskEntity && !"".equals(taskEntity.getItem()) && null != taskEntity.getItem()){
				sqlCount.append(" and p.ITEM like  '%" + taskEntity.getItem() + "%'");
				sqlList.append(" and p.ITEM like  '%" + taskEntity.getItem() + "%'");
			}
			if(null != taskEntity && !"".equals(taskEntity.getProcessTaskPerson()) && null != taskEntity.getProcessTaskPerson()){
				sqlCount.append(" and t.process_task_person like  '%" + taskEntity.getProcessTaskPerson() + "%'");
				sqlList.append(" and t.process_task_person like  '%" + taskEntity.getProcessTaskPerson() + "%'");
			}
			if(StringUtils.isNotEmpty(taskEntity.getProcessAssignees())){
				sqlCount.append( " AND T.process_Assignees like '%" +taskEntity.getProcessAssignees()+ "%'");
				sqlList.append( " AND T.process_Assignees like '%" +taskEntity.getProcessAssignees()+ "%'");
			}
			if(null != taskEntity && null!=taskEntity.getCreateTimeBegin()){
				sqlCount.append( " AND T.process_task_starttime >= '" +dFormat.format(taskEntity.getCreateTimeBegin())+ "'");
				sqlList.append( " AND T.process_task_starttime >= '" +dFormat.format(taskEntity.getCreateTimeBegin())+ "'");
			}	
			if(null != taskEntity && null!=taskEntity.getCreateTimeFinish()){
				sqlCount.append( " AND T.process_task_starttime <= '" +dFormat.format(taskEntity.getCreateTimeFinish())+" 24:60:60"+ "'");
				sqlList.append( " AND T.process_task_starttime <= '" +dFormat.format(taskEntity.getCreateTimeFinish())+" 24:60:60"+ "'");
			}	
			if(null != taskEntity && null!=taskEntity.getCdefine2()){
				sqlCount.append( " AND T.cdefine2 = '" +taskEntity.getCdefine2()+ "'");
				sqlList.append( " AND T.cdefine2 = '" +taskEntity.getCdefine2()+ "'");
			}	
			if(0!=taskEntity.getId()){
				sqlCount.append( " AND T.id like '%" +taskEntity.getId()+ "%'");
				sqlList.append( " AND T.id like '%" +taskEntity.getId()+ "%'");
			}	
		}
		sqlList.append(") as o where 1=1 and");
		sqlList.append(" rownumber>"+(Integer.valueOf(map.get("pageNo").toString())-1)*Integer.valueOf(map.get("pageSize").toString()));
        long count = jdbcTemplate.queryForObject(sqlCount.toString(), Long.class); 
		if(0 == count){
			return null;
		}else{
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sqlList.toString());
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
			for(int sx=0;sx<list.size();sx++){
				Map<String,Object> paramMap = list.get(sx);
				if(null != paramMap.get("taskName") && !"".equals(paramMap.get("taskName").toString())){
					paramMap = processCategoryNameHandle(paramMap);
				}else{
					paramMap.put("categoryName", "");
				}
				resultList.add(paramMap);
			}
			mapResult.put("list", resultList);
			mapResult.put("count", count);
			return mapResult;
		}
	}
	public Map<String,Object> getProcessTask(WorkflowTaskEntitiy po){
		TaskService taskService = processEngine.getTaskService();
		ManagementService managementService=processEngine.getManagementService();
		StringBuffer sf=new StringBuffer();
		sf.append("select rut.* from ");
		sf.append(managementService.getTableName(Task.class));
		sf.append(" rut ");
		//获取当前登录人的待办任务
		if(StringUtils.isNotEmpty(po.getProcessAssignees())){
			sf.append(",task_user tu where  rut.TASK_DEF_KEY_=tu.task_def_key and rut.PROC_INST_ID_=tu.process_inst_id and tu.is_finish='0' and tu.user_login_name='"+po.getProcessAssignees()+"'");
		}else{
			sf.append("where 1=1 ");
		}
		if(po.getCreateTimeBegin()!=null){//创建时间起始
			sf.append("and rut.CREATE_TIME_>=#{createTimeBegin} ");
		}
		if(po.getCreateTimeFinish()!=null){//创建时间结束
			sf.append("and rut.CREATE_TIME_<=#{createTimeFinish} ");
		}
		if(StringUtils.isNotEmpty(po.getWorkFlowName())){//流程定义key
			sf.append("and rut.PROC_DEF_ID_ like #{workFlowName} ");
		}
		if(StringUtils.isNotEmpty(po.getTaskName())){//任务名
			sf.append("and rut.NAME_ like #{taskName} ");
		}
		if(StringUtils.isNotEmpty(po.getProcessCreatePerson())){//流程启动人
			HistoryService historyService=processEngine.getHistoryService();
			List<HistoricProcessInstance> hList = historyService.createHistoricProcessInstanceQuery().startedBy(po.getProcessCreatePerson()).unfinished().list();
			if(hList.size()<=0){
				sf.append("and rut.PROC_INST_ID_ ='a'");//没有该人员启动的流程时
			}
			if(hList.size()>0){
				sf.append("and rut.PROC_INST_ID_ in(");
			}
			for(HistoricProcessInstance h:hList){
				sf.append("'"+h.getId()+"',");
			}
			if(hList.size()>0){
				sf.delete(sf.length()-1, sf.length());
				sf.append(") ");
			}
		}
		sf.append(" order by rut.CREATE_TIME_ desc");
		List<Task> taskList=taskService.createNativeTaskQuery().sql(sf.toString())
		.parameter("createTimeBegin", po.getCreateTimeBegin())
		.parameter("createTimeFinish", po.getCreateTimeFinish())
		.parameter("workFlowName", "%"+po.getWorkFlowName()+"%")
		.parameter("taskName","%"+po.getTaskName()+"%").list();
		
		List<Task> pageList=new ArrayList<Task>();
		if(taskList.size()<Integer.valueOf(po.getPageSize())){
			pageList=taskList;
		}else if(Integer.valueOf(po.getPageNum())*Integer.valueOf(po.getPageSize())>taskList.size()){
			pageList=taskList.subList((Integer.valueOf(po.getPageNum())-1)*Integer.valueOf(po.getPageSize()),taskList.size() );
		}else{
			pageList=taskList.subList((Integer.valueOf(po.getPageNum())-1)*Integer.valueOf(po.getPageSize()), Integer.valueOf(po.getPageNum())*Integer.valueOf(po.getPageSize()));
		}
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("taskList", pageList);
		resultMap.put("cou",Long.valueOf(taskList.size()));
		return resultMap;
	}
	public Map<String,Object> getFinishProcessTask(WorkflowTaskEntitiy po){
		TaskService taskService = processEngine.getTaskService();
		ManagementService managementService=processEngine.getManagementService();
		StringBuffer sf=new StringBuffer();
		sf.append("select rut.* from ");
		sf.append(managementService.getTableName(HistoricTaskInstance.class));
		sf.append(" rut  ");
		//获取当前登录人的已办任务
		if(StringUtils.isNotEmpty(po.getProcessAssignees())){
			sf.append(",task_user tu where  rut.id_=tu.act_task_id and tu.is_finish='1' and tu.is_Oneself='1' and tu.user_login_name='"+po.getProcessAssignees()+"'");
		}else{
			sf.append("where 1=1 ");
		}
		if(po.getCreateTimeBegin()!=null){//创建时间起始
			sf.append("and rut.START_TIME_>=#{createTimeBegin} ");
		}
		if(po.getCreateTimeFinish()!=null){//创建时间结束
			sf.append("and rut.START_TIME_<=#{createTimeFinish} ");
		}
		if(StringUtils.isNotEmpty(po.getWorkFlowName())){//流程定义key
			sf.append("and rut.PROC_DEF_ID_ like #{workFlowName} ");
		}
		if(StringUtils.isNotEmpty(po.getTaskName())){//任务名
			sf.append("and rut.NAME_ like #{taskName} ");
		}
		if(StringUtils.isNotEmpty(po.getProcessCreatePerson())){//流程启动人
			HistoryService historyService=processEngine.getHistoryService();
			List<HistoricProcessInstance> hList = historyService.createHistoricProcessInstanceQuery().startedBy(po.getProcessCreatePerson()).list();
			if(hList.size()<=0){
				sf.append("and rut.PROC_INST_ID_ ='a'");//没有该人员启动的流程时
			}
			if(hList.size()>0){
				sf.append("and rut.PROC_INST_ID_ in(");
			}
			for(HistoricProcessInstance h:hList){
				sf.append("'"+h.getId()+"',");
			}
			if(hList.size()>0){
				sf.delete(sf.length()-1, sf.length());
				sf.append(") ");
			}
		}
		sf.append(" order by rut.START_TIME_ desc");
		List<Task> taskList=taskService.createNativeTaskQuery().sql(sf.toString())
		.parameter("createTimeBegin", po.getCreateTimeBegin())
		.parameter("createTimeFinish", po.getCreateTimeFinish())
		.parameter("workFlowName", "%"+po.getWorkFlowName()+"%")
		.parameter("taskName", "%"+po.getTaskName()+"%")
		.list();
		
		List<Task> pageList=new ArrayList<Task>();
		if(taskList.size()<Integer.valueOf(po.getPageSize())){
			pageList=taskList;
		}else if(Integer.valueOf(po.getPageNum())*Integer.valueOf(po.getPageSize())>taskList.size()){
			pageList=taskList.subList((Integer.valueOf(po.getPageNum())-1)*Integer.valueOf(po.getPageSize()),taskList.size() );
		}else{
			pageList=taskList.subList((Integer.valueOf(po.getPageNum())-1)*Integer.valueOf(po.getPageSize()), Integer.valueOf(po.getPageNum())*Integer.valueOf(po.getPageSize()));
		}
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("taskList", pageList);
		resultMap.put("cou",Long.valueOf(taskList.size()));
		return resultMap;
		
	}
    /** 
     * 把修改过的xml更新至回流程定义中 
     *  
     * @param deployId 
     * @param defXml 
     */
//    public void writeDefXml(final String deployId, String defXml) {  
//        try {  
//            LobHandler lobHandler = new DefaultLobHandler();  
//            final byte[] btyesXml = defXml.getBytes("UTF-8");  
//            String sql = "update ACT_GE_BYTEARRAY set BYTES_=? where NAME_ LIKE '%bpmn20.xml' and DEPLOYMENT_ID_= ? ";  
//            jdbcTemplate.execute(sql, action)(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {  
//                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {  
//                    lobCreator.setBlobAsBytes(ps, 1, btyesXml);  
//                    ps.setString(2, deployId);  
//                }  
//            });  
//        } catch (Exception ex) {  
//            ex.printStackTrace();  
//        }  
//    }
	public void deleteTask(Map<String, Object> map) {
		if(null == jdbcTemplate){
			jdbcTemplate = (JdbcTemplate) map.get("JdbcTemplate");
		}
		ProcessInstance rpi = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(map.get("processInstanceId").toString()).singleResult();
		if(null != rpi){//流程任务未结束
			processEngine.getRuntimeService().deleteProcessInstance(map.get("processInstanceId").toString(), "deleteTask");
			processEngine.getHistoryService().deleteHistoricProcessInstance(map.get("processInstanceId").toString());
		}else{//流程任务已经结束
			processEngine.getHistoryService().deleteHistoricProcessInstance(map.get("processInstanceId").toString());
		}
	    String taskSql = "delete from WF_TASK_ENTITY where id = "+map.get("taskId")+"";
	    String businessSql = "";
	    String msgList = "select * from Sys_Message where cdefine3="+map.get("processInstanceId").toString();
	    String msgSql = "";
	    String msgdetailSql = "";
	    String userSql = "";
	    if("0".equals(map.get("formType").toString())){//元器件申请
	    	businessSql = "update Part_Data set process_state ='"+"作废"+"' where id="+map.get("businessId");
	        List<Map<String, Object>> messageList = jdbcTemplate.queryForList(msgList);
	        for(int x=0;x<messageList.size();x++){
	        	msgdetailSql = "delete from Sys_Message_Detail where msg_main_id="+messageList.get(x).get("id");//消息表删除
	    	    jdbcTemplate.execute(msgdetailSql);
	        }
	    	msgSql = "delete from Sys_Message where cdefine3="+map.get("processInstanceId").toString();//消息表删除
	    	userSql = "delete from task_user where process_inst_id="+map.get("processInstanceId").toString();//执行人存储表删除
		    jdbcTemplate.execute(msgSql);//消息表删除
		    jdbcTemplate.execute(businessSql);//业务表更新
		    jdbcTemplate.execute(userSql);//执行人存储表删除
	    }
	    jdbcTemplate.execute(taskSql);
	}
	public void deleteProcessTemplate(Map<String, Object> map) {
		processEngine.getRepositoryService().deleteDeployment(map.get("deploymentId").toString(), true);
	    String businessSql = "";
	    String msgSql = "";
	    String msgdetailSql = "";
	    String userSql = "";
	    String mainSql = "delete from WF_MAIN_ENTITY where id="+map.get("id");
	    String detailSql = "delete from WF_TASK_ENTITY where process_main_id="+map.get("id");
		if(null != map.get("formType")){
			String deteilListSql = "select cdefine4,cdefine2 from WF_TASK_ENTITY where process_main_id = "+map.get("id");
    		List<Map<String, Object>> list = jdbcTemplate.queryForList(deteilListSql);
	    	if("0".equals(map.get("formType"))){
	    		for(int x=0;x<list.size();x++){
	    			if(null != list.get(x).get("cdefine4") && !"".equals(list.get(x).get("cdefine4").toString())){
	    		    	businessSql = "update Part_Data set process_state ='"+"未审批"+"' where id="+list.get(x).get("cdefine4");
	    		    	jdbcTemplate.execute(businessSql);
	    			}
	    			if(null != list.get(x).get("cdefine2") && !"".equals(list.get(x).get("cdefine2").toString())){
	    			      String msgList = "select * from Sys_Message where cdefine3="+list.get(x).get("cdefine2").toString();
	    				  List<Map<String, Object>> messageList = jdbcTemplate.queryForList(msgList);
	    			        for(int s=0;s<messageList.size();s++){
	    			        	msgdetailSql = "delete from Sys_Message_Detail where msg_main_id="+messageList.get(s).get("id");//消息从表删除
	    			    	    jdbcTemplate.execute(msgdetailSql);
	    			        }
	    				msgSql = "delete from Sys_Message where cdefine3="+list.get(x).get("cdefine2");//消息表删除
	    				userSql = "delete from task_user where process_inst_id="+list.get(x).get("cdefine2");//任务执行人表删除
	    				jdbcTemplate.execute(msgSql);
	    				jdbcTemplate.execute(userSql);
	    			}
	    		}
	    	}
	    }
		jdbcTemplate.execute(detailSql);
		jdbcTemplate.execute(mainSql);
	}
	public void cleanProcessData(Map<String, Object> map) {
		String deteilListSql = "select cdefine4,id,cdefine2 from WF_TASK_ENTITY where process_main_id = "+map.get("id");
	    String detailSql = "delete from WF_TASK_ENTITY where process_main_id="+map.get("id");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(deteilListSql);
		if(null != map.get("formType")){
			for(int x=0;x<list.size();x++){
				if(null == list.get(x).get("cdefine2") || "".equals(list.get(x).get("cdefine2").toString())){
					continue;
				}
				Map<String, Object> mapParam = new HashMap<String,Object>();
				mapParam.put("taskId", list.get(x).get("id"));
				mapParam.put("businessId", list.get(x).get("cdefine4"));
				mapParam.put("processInstanceId", list.get(x).get("cdefine2"));
				mapParam.put("formType", map.get("formType"));
				deleteTask(mapParam);
			}
		}
		jdbcTemplate.execute(detailSql);
	}
	public Map<String, Object> selectProcessInformationToExport(Map<String, Object> map) {
		List<Object> idList = (List<Object>) map.get("idList");
		List<Map<String,Object>> listAll = new ArrayList<Map<String,Object>>();
		String fieldName = "process_name,process_task_person,process_task_starttime,process_task_state,processType,PartNumber,Part_Type,ITEM,KeyComponent,Datesheet,Manufacturer,Weight,DirInOROut,Country,TempPartMark,Specification,PackageType";
		String showName = "流程名称,申请人,申请时间,流程状态,流程类型,元器件编码,元器件名称,规格型号,质量等级,数据手册,生产厂家,重量,目录内外,国别,临时物料标识,技术条件,引脚形式";
		for(int x=0;x<idList.size();x++){
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append("select m.process_name,t.process_task_person,t.process_task_starttime,t.process_task_state,m.cdefine3 as processType,");
			sqlBuff.append("p.PartNumber,p.Part_Type,p.ITEM,p.KeyComponent,p.Datesheet,p.Manufacturer,p.Weight,p.DirInOROut,p.Country,p.TempPartMark,p.Specification,p.PackageType ");
			sqlBuff.append("from WF_TASK_ENTITY t left join WF_MAIN_ENTITY m on m.id=t.process_main_id left join Part_Data p on p.id=t.cdefine4 ");
			sqlBuff.append("where t.id = "+idList.get(x));
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sqlBuff.toString());
			listAll.addAll(list);
		}
		map.put("dataList", listAll);
		map.put("fieldName",fieldName);
		map.put("showName",showName);
		return map;
	}
	public void deleteProcessTask(WorkflowTaskEntitiy task) {
		 String taskSql = "delete from WF_TASK_ENTITY where id = "+task.getId()+"";
		 String businessSql = "";
		 String mainSql = "select cdefine3 from WF_MAIN_ENTITY WHERE ID = "+task.getProcessMainId();
		 List<Map<String,Object>> list = jdbcTemplate.queryForList(mainSql);
		    if("0".equals(list.get(0).get("cdefine3").toString())){//元器件申请
		    	businessSql = "update Part_Data set process_state ='"+"作废"+"' where id="+task.getCdefine4();
		    }
		    jdbcTemplate.execute(taskSql);
		    jdbcTemplate.execute(businessSql);
	}
	public long selectMaxId(String tableName) {
		// TODO Auto-generated method stub
		String sql = "select MAX(ID) FROM "+tableName;
		long id = jdbcTemplate.queryForLong(sql);
		return id;
	}
	public void updateTaskEntity(WorkflowTaskEntitiy task) {
		String sql = "select id,cdefine4,process_main_id from WF_TASK_ENTITY where cdefine2 = "+task.getCdefine2();
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        for(int i=0;i<list.size();i++){
        	String uSql = "update WF_TASK_ENTITY set process_task_state = '"+"审批通过"+"' where id="+list.get(i).get("id");
        	String mainSql = "select cdefine3 from WF_MAIN_ENTITY where id="+list.get(i).get("process_main_id");
        	List<Map<String,Object>> mainList = jdbcTemplate.queryForList(mainSql);
        	if(null != mainList && mainList.size()>0){
        		if(mainList.get(0).get("cdefine3").equals("0")){//类别：元器件申请
        			String partSql = "update part_data set process_state = '"+"审批通过"+"' where id="+list.get(i).get("cdefine4");
        			jdbcTemplate.execute(partSql);
        		}
        	}
        	jdbcTemplate.execute(uSql);
        }
	}
	public Map<String,Object> processCategoryHandle(Map<String,Object> map){
		if(map.get("cdefine3").toString().equals("0")){
			map.put("cdefine3", "partProcess");
			String sql = "select category_name from Process_Category where category_sign = "+"'"+map.get("cdefine3")+"'";
        	List<Map<String,Object>> mainList = jdbcTemplate.queryForList(sql);
			map.put("cdefine3", mainList.get(0).get("category_name"));
			map.put("categorySign", "partProcess");
		}else{
			String sql = "select category_name from Process_Category where category_sign = "+"'"+map.get("cdefine3")+"'";
        	List<Map<String,Object>> mainList = jdbcTemplate.queryForList(sql);
        	if(null != mainList && mainList.size()>0){
        		map.put("cdefine3", mainList.get(0).get("category_name"));
        		map.put("categorySign", map.get("cdefine3"));
        	}else{
        		map.put("cdefine3", "");
        		map.put("categorySign", "partProcess");
        	}
		}
		return map;
	}
	public Map<String,Object> processCategoryNameHandle(Map<String,Object> map){
		if(map.get("taskName").toString().equals("0")){
			map.put("taskName", "partProcess");
			String sql = "select category_name from Process_Category where category_sign = "+"'"+map.get("taskName")+"'";
        	List<Map<String,Object>> mainList = jdbcTemplate.queryForList(sql);
			map.put("categoryName", mainList.get(0).get("category_name"));
			map.put("taskName", "0");
		}else{
			String sql = "select category_name from Process_Category where category_sign = "+"'"+map.get("taskName")+"'";
        	List<Map<String,Object>> mainList = jdbcTemplate.queryForList(sql);
        	if(null != mainList && mainList.size()>0){
        		map.put("categoryName", mainList.get(0).get("category_name"));
        	}else{
        		map.put("categoryName", "");
        	}
		}
		return map;
	}
	public void deleteProcessTaskDetail(Map<String,Object> map){
		long mainId = Long.valueOf(map.get("mainId").toString());
		String formType = map.get("formType").toString();
		JdbcTemplate jdbc = (org.springframework.jdbc.core.JdbcTemplate) map.get("jdbcTemplate");
		if("0".equals(formType)){
			String mainSql = "select cdefine4,id,cdefine2 from WF_TASK_ENTITY where cdefine4 = "+"'"+mainId+"'";
		    List<Map<String, Object>> list = jdbc.queryForList(mainSql);
			Map<String, Object> mapParam = new HashMap<String,Object>();
			mapParam.put("JdbcTemplate", jdbc);
		    for(int x=0;x<list.size();x++){
				mapParam.put("taskId", list.get(x).get("id"));
				mapParam.put("businessId", list.get(x).get("cdefine4"));
				mapParam.put("processInstanceId", list.get(x).get("cdefine2"));
				mapParam.put("formType", formType);
				this.deleteTask(mapParam);
			}
		}
	}
}
