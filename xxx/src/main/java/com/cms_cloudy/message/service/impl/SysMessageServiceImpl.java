package com.cms_cloudy.message.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cms_cloudy.message.dao.ISysMessageDao;
import com.cms_cloudy.message.pojo.SysMessage;
import com.cms_cloudy.message.pojo.SysMessageDetail;
import com.cms_cloudy.message.service.ISysMessageService;

@Component("iSysMessageService")
public class SysMessageServiceImpl implements ISysMessageService {

	@Autowired
	private ISysMessageDao sysMessageDao;
	
	public List<SysMessage> selectMessageList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return sysMessageDao.selectMessageList(map);
	}

	public void insertMessage(SysMessage msg) {
		// TODO Auto-generated method stub
		sysMessageDao.insertMessage(msg);
	}

	public void updateMessage(SysMessage msg) {
		// TODO Auto-generated method stub
		sysMessageDao.updateMessage(msg);
	}

	public int deleteMessage(int id) {
		// TODO Auto-generated method stub
		return sysMessageDao.deleteMessage(id);
	}

	public long selectCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sysMessageDao.selectCount(map);
	}

	public List<SysMessage> selectMsgList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sysMessageDao.selectMsgList(map);
	}

	public void insertMessageDetail(SysMessageDetail detail) {
		// TODO Auto-generated method stub
		sysMessageDao.insertMessageDetail(detail);
	}

	public void updateMessageDetail(SysMessageDetail detail) {
		// TODO Auto-generated method stub
		sysMessageDao.updateMessageDetail(detail);
	}

	public int deleteMessageDetail(int mainId) {
		// TODO Auto-generated method stub
		return sysMessageDao.deleteMessageDetail(mainId);
	}

	public long selectCountByState(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sysMessageDao.selectCountByState(map);
	}

	public List<SysMessageDetail> selectMsgDetailList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sysMessageDao.selectMsgDetailList(map);
	}

	public Map<String,Object> selectMaxId() {
		// TODO Auto-generated method stub
		return sysMessageDao.selectMaxId();
	}

}
