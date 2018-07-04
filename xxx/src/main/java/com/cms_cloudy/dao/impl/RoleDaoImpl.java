package com.cms_cloudy.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cms_cloudy.dao.RoleDao;
@Component("RoleDao")
public class RoleDaoImpl implements RoleDao {

	/*自动注入这里spring管理了mybatis的sqlsessionfactory 
	 */  
	@Autowired  
	private SqlSessionTemplate sqlSession; 
}
