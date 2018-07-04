package com.cms_cloudy.message.service;

import java.util.List;
import java.util.Map;

import com.cms_cloudy.message.pojo.SysMessage;
import com.cms_cloudy.message.pojo.SysMessageDetail;

public interface ISysMessageService {

	// 消息信息查询
	public List<SysMessage> selectMessageList(Map<String, Object> map);

	// 消息信息添加
	public void insertMessage(SysMessage msg);

	// 消息信息更新
	public void updateMessage(SysMessage msg);

	// 消息信息删除
	public int deleteMessage(int id);

	// 查询消息个数
	public long selectCount(Map<String, Object> map);

	// 消息主子表级联查询
	public List<SysMessage> selectMsgList(Map<String, Object> map);

	// 消息从表信息添加
	public void insertMessageDetail(SysMessageDetail detail);

	// 消息从表信息更新
	public void updateMessageDetail(SysMessageDetail detail);

	// 消息从表信息删除
	public int deleteMessageDetail(int mainId);

	// 消息从表消息个数查询
	public long selectCountByState(Map<String, Object> map);
	
	//消息从表查询
	public List<SysMessageDetail> selectMsgDetailList(Map<String, Object> map);
	
	//消息从表最新ID查询
	public Map<String,Object> selectMaxId();
}
