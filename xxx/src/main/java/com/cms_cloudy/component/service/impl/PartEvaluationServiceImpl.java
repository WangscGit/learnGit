package com.cms_cloudy.component.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cms_cloudy.component.dao.IPartEvaluationDao;
import com.cms_cloudy.component.pojo.PartEvaluationEntity;
import com.cms_cloudy.component.service.IPartEvaluationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Component("IPartEvaluationService")
public class PartEvaluationServiceImpl implements IPartEvaluationService {

	@Autowired
	private IPartEvaluationDao partEvaluationDao;
	public List<PartEvaluationEntity> selectPartEvaluationList(Map<String, Object> map) {
		return partEvaluationDao.selectPartEvaluationList(map);
	}

	public void insertPartEvaluation(PartEvaluationEntity partEvaluation) {
		partEvaluationDao.insertPartEvaluation(partEvaluation);
	}

	public int deletePartEvaluation(int id) {
		return partEvaluationDao.deletePartEvaluation(id);
	}

	public int selectPartEvaluationCount(Map<String, Object> map) {
		return partEvaluationDao.selectPartEvaluationCount(map);
	}

	public PageInfo<PartEvaluationEntity> selectPartEvaluationListByPage(Map<String, Object> map) {
		PageHelper.startPage(Integer.valueOf(map.get("pageNo").toString()), Integer.valueOf(map.get("pageSize").toString()));
		List<PartEvaluationEntity> partEvaluationList = partEvaluationDao.selectPartEvaluationList(map);
		if(partEvaluationList.size()>0){
			PageInfo<PartEvaluationEntity> page = new PageInfo<PartEvaluationEntity>(partEvaluationList);
			return page;
		}else{
			return null;
		}
	}

	public void updatePartEvaluation(PartEvaluationEntity partEvaluation) {
		partEvaluationDao.updatePartEvaluation(partEvaluation);
	}
}
