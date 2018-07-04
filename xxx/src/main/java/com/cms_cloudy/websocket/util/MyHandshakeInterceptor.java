package com.cms_cloudy.websocket.util;

import java.util.Map;  

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;  
import org.springframework.http.server.ServerHttpResponse;  
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;  
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.cms_cloudy.user.pojo.HrUser;  

 /**
  *  获取session中的登陆用户信息，用以区分WebSocketSession,对指定用户进行推送信息。  
  * @author Administrator
  */
public class MyHandshakeInterceptor extends HttpSessionHandshakeInterceptor{  
  
    @Override  
    public boolean beforeHandshake(ServerHttpRequest request,  
            ServerHttpResponse response, WebSocketHandler wsHandler,  
            Map<String, Object> attributes) throws Exception {  
        System.out.println("Before Handshake");  
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                //使用userCd区分WebSocketHandler，以便定向发送消息
//            	String userCd = session.getAttribute("USER_CD").toString();
            	HrUser user = null;
    		    user = (HrUser)session.getAttribute("user");
    		    if(null != user){
    		    	attributes.put("WS_USER_CD",user.getLoginName());
    		    }else{
    		    	attributes.put("WS_USER_CD","");
    		    }
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);  
    }  
  
    @Override  
    public void afterHandshake(ServerHttpRequest request,  
            ServerHttpResponse response, WebSocketHandler wsHandler,  
            Exception ex) {  
        System.out.println("After Handshake");  
        super.afterHandshake(request, response, wsHandler, ex);  
    }  
}