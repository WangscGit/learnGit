package org.activiti.rest.util;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CustomGroupEntityManagerFactory implements SessionFactory{
	 private CustomGroupEntityManager  groupEntityManager;

	    public Class<?> getSessionType() {
	        // 返回原始的GroupEntityManager类型
	        return GroupIdentityManager.class;
	    }
	    public Session openSession() {
	        // 返回自定义的GroupEntityManager实例
	        return groupEntityManager;
	    }
	    @Autowired
	    public void setCustomGroupEntityManager(CustomGroupEntityManager  groupEntityManager) {
	        this.groupEntityManager = groupEntityManager;
	    }
}
