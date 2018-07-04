package org.activiti.rest.util;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.cms_cloudy.user.pojo.HrGroup;
import com.cms_cloudy.user.pojo.HrUser;


public class ActivitiUtils {  
	private static ProcessEngine processEngine;

    /**
     * 单例模式获取引擎对象
     */
    public static ProcessEngine getProcessEngine() {
        if (processEngine == null) {
      /*
       * 使用默认的配置文件名称（activiti.cfg.xml）创建引擎对象
       */
            processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
        }
        return processEngine;
    }
    public static UserEntity  toActivitiUser(HrUser bUser){  
        UserEntity userEntity = new UserEntity();  
        userEntity.setId(bUser.getUserId()+"");  
        userEntity.setFirstName(bUser.getLoginName());  
        userEntity.setLastName(bUser.getUserName());  
        userEntity.setPassword(bUser.getPassword());  
        userEntity.setEmail(bUser.getEmail());  
        userEntity.setRevision(1);  
        return userEntity;  
    }  
      
    public static GroupEntity  toActivitiGroup(HrGroup bGroup){  
        GroupEntity groupEntity = new GroupEntity();  
        groupEntity.setRevision(1);  
        groupEntity.setType("assignment");  
  
        groupEntity.setId(bGroup.getGroupId()+"");  
        groupEntity.setName(bGroup.getGroupName());  
        return groupEntity;  
    }  
      
    public static List<org.activiti.engine.identity.Group> toActivitiGroups(List<HrGroup> bGroups){  
          
        List<org.activiti.engine.identity.Group> groupEntitys = new ArrayList<org.activiti.engine.identity.Group>();  
          
        for (HrGroup bGroup : bGroups) {  
            GroupEntity groupEntity = toActivitiGroup(bGroup);  
            groupEntitys.add(groupEntity);  
        }  
        return groupEntitys;  
    }  
} 
