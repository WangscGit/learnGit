package com.cms_cloudy.websocket.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.cms_cloudy.component.pojo.PartEvaluationEntity;
import com.cms_cloudy.component.service.IPartEvaluationService;
import com.cms_cloudy.websocket.pojo.PushPartData;
import com.cms_cloudy.websocket.service.IPushPartDataService;

import net.sf.json.JSONArray;

/**
 *  消息处理类，需要实现WebSocketHandler接口。定义静态变量users集合，记录当前登录用户的WebSocketSession信息
 * @author WangSc
 */
public class MyHandler extends TextWebSocketHandler implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final ArrayList<WebSocketSession> users;
    private Logger logger = LoggerFactory.getLogger(MyHandler.class);
    @Autowired
	private IPushPartDataService pushPartDataService;
	@Autowired
	private IPartEvaluationService partEvaluationService;
    static {
        users = new ArrayList<WebSocketSession>();
    }
	
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String[] type = message.getPayload().split(",");
        if(type.length>0 && type[0].equals("pressMessageCount")){//流程催办消息发送
        sendMessageToUser(type[1],message);
        }else if(type.length>0 && type[0].equals("compulsionLogin")){//强制登陆
        	sendMessageToUser(type[1],message);//先给已登录的账号发送异地登录提示框
        }else{
        	Map<String,Object> pushMap = new HashMap<String,Object>();
    		Map<String,Object> partMap = new HashMap<String,Object>();
    		List<Map<String,Object>> hotList = new ArrayList<Map<String,Object>>();
    	    pushMap.put("type", "1");
    		List<PushPartData> list = pushPartDataService.selectPushNewPart(pushMap);
            if(null != list && list.size()>0){
                for(PushPartData pushData:list){
                    partMap.put("id", pushData.getPartId());
            		List<Map<String,Object>> partList = pushPartDataService.selectFirstPartData(partMap);
            		Map<String,Object> evaMap = new HashMap<String,Object>();
    				int evaluation = 0;
    				evaMap.put("PartNumber", partList.get(0).get("PartNumber"));
    				List<PartEvaluationEntity> partEva = partEvaluationService.selectPartEvaluationList(evaMap);
    				if(null != partEva && partEva.size()>0){
    					for(PartEvaluationEntity evaLevel:partEva){
    						evaluation += evaLevel.getVotes();
    					}
    					evaluation = (int) Math.ceil(evaluation/partEva.size());//评价平均值
    				}else{
    					evaluation = 0;
    				}
    				partList.get(0).put("Votes", evaluation);
    				hotList.addAll(partList);
                }
            }
            String jsonList = JSONArray.fromObject(hotList).toString();
            TextMessage returnMessage = new TextMessage(jsonList);  //+" received at server"
            sendMessageToUsers(returnMessage);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connect to the websocket success......");
        // 从session中取在线用户Cd
        users.add(session);
       /* String userName = (String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME);
        if(userName!= null){
            //查询未读消息
            int count = webSocketService.getUnReadNews((String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME));
 
            session.sendMessage(new TextMessage(count + ""));
        }*/
        
        
       /* String userCd = (String) session.getAttributes().get("WS_USER_CD");
        if(userCd != null) {
        	// 得到DB该用户未读消息
        	
        	// 发送给指定cd用户
        	session.sendMessage(new TextMessage("<a href='../rebook/exApplyCenter?remark=1'>这是未读信息</a>"));
        	
        	logger.info("------in------");
        }*/
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        System.out.println("websocket connection closed......");
        users.remove(session);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	System.out.println("websocket connection closed......");
        users.remove(session);
    }
    
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 发消息（无论是否在线）
     *
     * @param userCd
     * @param message
     */
    public void sendMessageToUser(String userCd, TextMessage message) {
        for (WebSocketSession user : users) {
            if (userCd.equals(user.getAttributes().get("WS_USER_CD"))) { // 应从session取CD对比
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //break;
            }
        }
    }
}