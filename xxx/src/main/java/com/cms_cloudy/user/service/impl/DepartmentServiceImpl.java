package com.cms_cloudy.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cms_cloudy.user.dao.IDepartmentDao;
import com.cms_cloudy.user.pojo.HrDepartment;
import com.cms_cloudy.user.pojo.HrPosition;
import com.cms_cloudy.user.service.IDepartmentService;

@Component("IDepartmentService")
public class DepartmentServiceImpl implements IDepartmentService {

	@Autowired
	private IDepartmentDao departmentDao;

	public List<HrDepartment> selectDepartmentList(Map<String, Object> map) {
		return departmentDao.selectDepartmentList(map);
	}

	public void insertDepartment(HrDepartment department) {
		departmentDao.insertDepartment(department);
	}

	public void updateDepartment(HrDepartment department) {
		departmentDao.updateDepartment(department);
	}

	public int deleteDepartment(int id) {
		return departmentDao.deleteDepartment(id);
	}

	public List<HrDepartment> checkDepartmentName(Map<String, Object> map) {
		return departmentDao.checkDepartmentName(map);
	}
	
}
