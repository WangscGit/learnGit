package com.cms_cloudy.component.dao;

import java.util.List;
import java.util.Map;

import com.cms_cloudy.component.pojo.PartEvaluationEntity;
/**
 * 元器件评价DAO
 * @author WangSc
 *
 */
public interface IPartEvaluationDao {

	  	/**查询元器件评价表**/
		public List<PartEvaluationEntity> selectPartEvaluationList(Map<String,Object> map);
	    /**添加元器件评价表**/
		public void insertPartEvaluation(PartEvaluationEntity partEvaluation);
	    /**删除元器件评价表**/
	    public int deletePartEvaluation(int id);
	    /**根据条件查询评论数**/
	    public int selectPartEvaluationCount(Map<String,Object> map);
	    /**修改评价**/
	    public void updatePartEvaluation(PartEvaluationEntity partEvaluation);
}
