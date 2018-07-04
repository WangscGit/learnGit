package com.cms_cloudy.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cms_cloudy.user.dao.IPositionDao;
import com.cms_cloudy.user.pojo.HrPosition;
import com.cms_cloudy.user.service.IPositionService;

@Component("IPositionService")
public class PositionServiceImpl implements IPositionService {

	@Autowired
	private IPositionDao positonDao;

	public List<HrPosition> selectPositionList(Map<String, Object> map) {
		return positonDao.selectPositionList(map);
	}

	public void insertPosition(HrPosition position) {
		positonDao.insertPosition(position);
	}

	public void updatePosition(HrPosition position) {
		positonDao.updatePosition(position);
	}

	public int deletePosition(int id) {
		return positonDao.deletePosition(id);
	}

	public List<HrPosition> checkPositionName(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return positonDao.checkPositionName(map);
	}
}
