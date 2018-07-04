package com.cms_cloudy.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.user.pojo.HrUser;  
@Controller
@RequestMapping(value="/sessionListener")
public class SessionListener extends BaseController implements ServletContextAttributeListener, HttpSessionAttributeListener {  
	  
    private static Vector<Map<String,HttpSession>> onLineList = new Vector<Map<String,HttpSession>>();//用于保存在线用户
    public static Vector<Map<String,HttpSession>> getOnLineList() {  
        return onLineList;  
    }  
  
        //session创建后触发   ，将用户信息添加到静态的集合中  
    public void attributeAdded(HttpSessionBindingEvent arg0) {  
        if(arg0.getName().equals("user")){  
        	Map<String,HttpSession> map = new HashMap<String,HttpSession>();
        	HrUser user = (HrUser)arg0.getValue();
        	map.put(user.getLoginName(), arg0.getSession());
        	onLineList.add(map);
        }  
    }  
        //清除session时触发  
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		if (arg0.getName().equals("user")) {
			HrUser user = (HrUser) arg0.getValue();
			for (int x = 0; x < onLineList.size(); x++) {
				Map<String, HttpSession> mapMsg = onLineList.get(x);
				Set<String> set = mapMsg.keySet();
				if (set.iterator().next().equals(user.getLoginName())) {
					onLineList.remove(x);
				}
			}
		}
		// onLineList.remove(map);
	}  
        //session被替换时触发  
    public void attributeReplaced(HttpSessionBindingEvent arg0) {  
        if(arg0.getName().equals("user")){  
        	Map<String,HttpSession> map = new HashMap<String,HttpSession>();
        	HrUser user = (HrUser)arg0.getValue();
        	map.put(user.getLoginName(), arg0.getSession());
        	onLineList.add(map); 
        }  
  
    }  
  
    public void attributeAdded(ServletContextAttributeEvent arg0) {   
  
    }  
    public void attributeRemoved(ServletContextAttributeEvent arg0) {  
  
    }  
    public void attributeReplaced(ServletContextAttributeEvent arg0) {  
  
    }  
    /**
     * 服务器定时刷新
     * @param request
     * @param response
     */
	@RequestMapping(value="/sessionCheckFrompage.do")
    public synchronized void sessionCheckFrompage(HttpServletRequest request, HttpServletResponse response){
		try {
			String msg = JSON.toJSONString(null);
			outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}  
