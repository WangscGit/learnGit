package org.activiti.rest.util;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CustomUserEntityManagerFactory implements SessionFactory{
    
	 private CustomUserEntityManager userEntityManager;

	   
	    public Class<?> getSessionType() {
	        // 返回原始的UserManager类型
	        return UserIdentityManager.class;
	    }
	    public Session openSession() {
	        // 返回自定义的UserManager实例
	        return userEntityManager;
	    }
	    @Autowired
	    public void setCustomUserEntityManager(CustomUserEntityManager userEntityManager) {
	        this.userEntityManager = userEntityManager;
	    }
}
