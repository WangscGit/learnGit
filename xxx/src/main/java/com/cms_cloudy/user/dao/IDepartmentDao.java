package com.cms_cloudy.user.dao;

import java.util.List;
import java.util.Map;

import com.cms_cloudy.user.pojo.HrDepartment;
import com.cms_cloudy.user.pojo.HrPosition;

public interface IDepartmentDao {

	/**部门信息查询**/
	public List<HrDepartment> selectDepartmentList(Map<String,Object> map);
    /**部门信息添加**/
	public void insertDepartment(HrDepartment department); 
	/**部门信息更新**/
    public void updateDepartment(HrDepartment department);
    /**部门信息删除**/ 
    public int deleteDepartment(int id);
    /**部门唯一性校验**/
    public List<HrDepartment> checkDepartmentName(Map<String,Object> map);
}
