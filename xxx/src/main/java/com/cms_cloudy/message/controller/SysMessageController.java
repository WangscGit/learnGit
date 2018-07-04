package com.cms_cloudy.message.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.message.pojo.SysMessage;
import com.cms_cloudy.message.pojo.SysMessageDetail;
import com.cms_cloudy.message.service.ISysMessageService;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.UserService;

@Controller
@RequestMapping(value="/sysMessageController")
public class SysMessageController extends BaseController{

	@Autowired
	private ISysMessageService sysMessageService;
	
	@Autowired  
	private UserService userService;
	
	/**
	 * 消息信息查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectMessageList.do")
	public void selectMessageList(HttpServletRequest request,HttpServletResponse response){
		String whetherRead = request.getParameter("whetherRead");
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
		HrUser user = this.getUserInfo(request);
		    if(null == user){
		    	 msg = JSON.toJSONString(null);
		    	 outputJson(response,msg);
		    	 return;
		  }
		if("false".equals(whetherRead)){
			map.put("whetherRead", '0');
		}
		map.put("receiverPerson", user.getLoginName());
		List<SysMessage> list = sysMessageService.selectMsgList(map);
	   if(null != list && list.size()>0){
		   msg = JSON.toJSONString(list);
	   }else{
		   msg = JSON.toJSONString(null);
	   }
		 outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 消息信息添加
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertMessage.do")
	public void insertMessage(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<String> msgList = new ArrayList<String>();
			HrUser user = this.getUserInfo(request);
			String loginName = "admin";
			if (null != user) {
				loginName = user.getLoginName();
			}
			String titleMsg = request.getParameter("msgTittle");
			String typeMsg = request.getParameter("msgType");
			String contendMsg = request.getParameter("msgContent");
			String receiver = request.getParameter("receiverPerson");
			List<Object> receiverList = JSON.parseArray(receiver);
			String msgLevel = request.getParameter("msgLevel");
			SysMessage sysMsg = new SysMessage();
			sysMsg.setMsgContent(contendMsg);
			sysMsg.setLaunchPerson(loginName);
			sysMsg.setLaunchTime(new Date());
			sysMsg.setReceiverPerson("");// ----------------
			sysMsg.setWhetherRead(false);
			sysMsg.setMsgTittle(titleMsg);
			sysMsg.setMsgType(typeMsg);
			sysMsg.setMsgLevel(msgLevel);
			sysMsg.setCdefine1("javascript:void(0)");
			sysMessageService.insertMessage(sysMsg);
			Map<String,Object> msgId = sysMessageService.selectMaxId();
			for (int x = 0; x < receiverList.size(); x++) {
				SysMessageDetail detail = new SysMessageDetail();
				detail.setMsgMainId(Long.valueOf(msgId.get("IDS").toString()));
				detail.setReceiver(receiverList.get(x).toString());
				detail.setState(0);
				sysMessageService.insertMessageDetail(detail);
				Map<String, Object> mapSql = new HashMap<String, Object>();
				mapSql.put("receiverPerson", String.valueOf(receiverList.get(x)));
				mapSql.put("whetherRead", "0");
				long count = sysMessageService.selectCountByState(mapSql);
				msgList.add("pressMessageCount" + "," + receiverList.get(x) + "," + count);
			}
			String msg = JSON.toJSONString(msgList);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 消息信息更新
	 * @param request
	 * @param response
	 * @throws ParseException 
	 */
	@RequestMapping(value="/updateMessage.do")
	public void updateMessage(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		Map<String,Object> mapSql = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		map.put("id", id);
		try {
		List<SysMessageDetail> list = sysMessageService.selectMsgDetailList(map);
		if(null == list || list.size()<=0){
			String msgJson = JSON.toJSONString(null);
			outputJson(response,msgJson);
			return;
		}
		SysMessageDetail msg = list.get(0);
		if(type.equals("0")){
			msg.setReadTime(new Date());
			msg.setState(1);;
		}else{
			msg.setReadTime(null);
			msg.setState(0);
		}
		HrUser user = this.getUserInfo(request);
		sysMessageService.updateMessageDetail(msg);
	    mapSql.put("receiverPerson", user.getLoginName());
	    mapSql.put("whetherRead", "0");
	    long count = sysMessageService.selectCountByState(mapSql);
		String msgJson = JSON.toJSONString(count);
			outputJson(response,msgJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 消息信息删除
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteMessage.do")
	public void deleteMessage(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		try {
		sysMessageService.deleteMessageDetail(Integer.valueOf(id));
		sysMessageService.deleteMessage(Integer.valueOf(id));
		String msg = JSON.toJSONString(id);
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 消息数量查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectMessageCount.do")
	public void selectMessageCount(HttpServletRequest request,HttpServletResponse response){
		try {
		HrUser user = this.getUserInfo(request);
		Map<String,Object> mapSql = new HashMap<String,Object>();
	    mapSql.put("receiverPerson", user.getLoginName());
	    mapSql.put("whetherRead", "0");
	    long count = sysMessageService.selectCountByState(mapSql);
    	String msg = JSON.toJSONString(count);
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 消息发布列表查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/selectSendMessageList.do")
	public void selectSendMessageList(HttpServletRequest request,HttpServletResponse response){
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
		HrUser user = this.getUserInfo(request);
		    if(null == user){
		    	 msg = JSON.toJSONString(null);
		    	 outputJson(response,msg);
		    	 return;
		  }
		map.put("launchPerson", user.getLoginName());
		List<SysMessage> list = sysMessageService.selectMessageList(map);
	   if(null != list && list.size()>0){
		   msg = JSON.toJSONString(list);
	   }else{
		   msg = JSON.toJSONString(null);
	   }
		 outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 消息发布----修改前
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateSendMsgBefore.do")
	public void updateSendMsgBefore(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> resultMap=new HashMap<String, Object>();
		String id = request.getParameter("id");
		String msg = "";
		map.put("id", id);
		try {
		  List<SysMessage> list = sysMessageService.selectMessageList(map);
		if(null != list && list.size()>0){
			resultMap.put("mainId", list.get(0).getId());
			List<SysMessage> receiverList = sysMessageService.selectMsgList(resultMap);
		    resultMap.put("message", list.get(0));
		    resultMap.put("receiverList",receiverList);
			msg = JSON.toJSONString(resultMap);
		}else{
			msg = JSON.toJSONString(null);
		}
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 消息发布----提交修改信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/modifyMsg.do")
	public void modifyMsg(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<String> msgList = new ArrayList<String>();
			Map<String,Object> map = new HashMap<String,Object>();
			HrUser user = this.getUserInfo(request);
			String loginName = "admin";
			if (null != user) {
				loginName = user.getLoginName();
			}
			String titleMsg = request.getParameter("msgTittle");
			String id = request.getParameter("id");
			String typeMsg = request.getParameter("msgType");
			String contendMsg = request.getParameter("msgContent");
			String receiver = request.getParameter("receiverPerson");
			List<Object> receiverList = JSON.parseArray(receiver);
			String msgLevel = request.getParameter("msgLevel");
			map.put("id", id);
			List<SysMessage> list = sysMessageService.selectMessageList(map);
			if(null == list || list.size()<=0){
				String msg = JSON.toJSONString(null);
				outputJson(response, msg);
			}
			SysMessage sysMsg = list.get(0);
			sysMsg.setMsgContent(contendMsg);
			sysMsg.setReceiverPerson("");// ----------------
			sysMsg.setWhetherRead(false);
			sysMsg.setMsgTittle(titleMsg);
			sysMsg.setMsgType(typeMsg);
			sysMsg.setMsgLevel(msgLevel);
			sysMsg.setCdefine1("javascript:void(0)");
			sysMsg.setCdefine2(loginName);
			sysMsg.setCdefine5(new Date());
			sysMessageService.updateMessage(sysMsg);
			//sysMessageService.deleteMessageDetail(Integer.valueOf(String.valueOf(sysMsg.getId())));
			for (int x = 0; x < receiverList.size(); x++) {
				SysMessageDetail detail = new SysMessageDetail();
				detail.setMsgMainId(sysMsg.getId());
				detail.setReceiver(receiverList.get(x).toString());
				detail.setState(0);
				sysMessageService.insertMessageDetail(detail);
				Map<String, Object> mapSql = new HashMap<String, Object>();
				mapSql.put("receiverPerson", String.valueOf(receiverList.get(x)));
				mapSql.put("whetherRead", "0");
				long count = sysMessageService.selectCountByState(mapSql);
				msgList.add("pressMessageCount" + "," + receiverList.get(x) + "," + count);
			}
			String msg = JSON.toJSONString(msgList);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
