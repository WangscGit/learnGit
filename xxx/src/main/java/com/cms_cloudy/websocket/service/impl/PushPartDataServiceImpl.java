package com.cms_cloudy.websocket.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cms_cloudy.websocket.dao.IPushPartDataDao;
import com.cms_cloudy.websocket.pojo.PushPartData;
import com.cms_cloudy.websocket.pojo.PushpartDetail;
import com.cms_cloudy.websocket.service.IPushPartDataService;

@Component(value="IPushPartDataService")
public class PushPartDataServiceImpl implements IPushPartDataService {

	@Autowired 
	private IPushPartDataDao pushPartDataDao;
	public List<PushPartData> selectPushPartData(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return pushPartDataDao.selectPushPartData(map);
	}
	public void insertPushPartData(PushPartData pushData) {
		// TODO Auto-generated method stub
		pushPartDataDao.insertPushPartData(pushData);
	}
	public int deletePushPartData(int id) {
		// TODO Auto-generated method stub
		return pushPartDataDao.deletePushPartData(id);
	}
	public void updatePushPartDatar(PushPartData pushData) {
		// TODO Auto-generated method stub
		pushPartDataDao.updatePushPartDatar(pushData);
	}
	public List<PushPartData> selectHotSearchData(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return pushPartDataDao.selectHotSearchData(map);
	}
	public List<PushPartData> selectPushNewPart(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return pushPartDataDao.selectPushNewPart(map);
	}
	public List<Map<String, Object>> selectFirstPartData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return pushPartDataDao.selectFirstPartData(map);
	}
	public void insertPushPartDetail(PushpartDetail detail) {
		// TODO Auto-generated method stub
		pushPartDataDao.insertPushPartDetail(detail);
	}
	public int deletePushPartDetail(int id) {
		// TODO Auto-generated method stub
		return pushPartDataDao.deletePushPartDetail(id);
	}
	public List<PushpartDetail> selectPushPartDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return pushPartDataDao.selectPushPartDetail(map);
	}
	public List<PushPartData> selectDistinctPushPart(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return pushPartDataDao.selectDistinctPushPart(map);
	}
	public List<PushPartData> selectDistinctPushPartToshow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return pushPartDataDao.selectDistinctPushPartToshow(map);
	}
	public List<PushPartData> selectDistinctPushPartToHot(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return pushPartDataDao.selectDistinctPushPartToHot(map);
	}
	public int selectDistinctSum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return pushPartDataDao.selectDistinctSum(map);
	}
	public int deletePartEvaluationByPartNumber(int id) {
		// TODO Auto-generated method stub
		return pushPartDataDao.deletePartEvaluationByPartNumber(id);
	}
	public int deletePushPartDataByPartId(int id) {
		// TODO Auto-generated method stub
		return pushPartDataDao.deletePushPartDataByPartId(id);
	}
	public int deletePartHistoryByPartNumber(int id) {
		// TODO Auto-generated method stub
		return pushPartDataDao.deletePartHistoryByPartNumber(id);
	}

}
