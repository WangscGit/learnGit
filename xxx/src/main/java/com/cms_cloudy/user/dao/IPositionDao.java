package com.cms_cloudy.user.dao;

import java.util.List;
import java.util.Map;

import com.cms_cloudy.user.pojo.HrPosition;

public interface IPositionDao {

	/**职位信息查询**/
	public List<HrPosition> selectPositionList(Map<String,Object> map);
    /**职位信息添加**/
	public void insertPosition(HrPosition position); 
	/**职位信息更新**/
    public void updatePosition(HrPosition position);
    /**职位信息删除**/ 
    public int deletePosition(int id);
    /**职位唯一性校验**/
    public List<HrPosition> checkPositionName(Map<String,Object> map);
}
