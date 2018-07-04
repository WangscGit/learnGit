package com.cms_cloudy.controller;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.UserService;

@Controller
public class BaseController {
	@Autowired  
	private UserService uu;  
	protected void outputMsg(HttpServletResponse res, String msg)
		    throws Exception
		  {
		    PrintWriter out = res.getWriter();
		    res.setContentType("text/html");
		    res.setCharacterEncoding("utf-8");
		    out.write(msg);
		    out.flush();
		    out.close();
		  }

		  protected void outputJson(HttpServletResponse res, String msg) throws Exception
		  {
		    PrintWriter out = res.getWriter();
		    res.setContentType("text/json");
		    res.setCharacterEncoding("utf-8");
		    out.write(msg);
		    out.flush();
		    out.close();
		  }

		  protected HrUser getUserInfo(HttpServletRequest req)
		  {
			HrUser user = null;
			
		    user = (HrUser)req.getSession().getAttribute("user");
		    if(user==null){
		    	return null;
		    }
		    List<HrUser> l=uu.selectUserByName(user.getLoginName());
		    if(l==null||l.size()==0){
		    	return null;
		    }
		    return l.get(0);
		  }
		  protected Date parseDate(String msg)
		  {
            if(StringUtils.isNotEmpty(msg)){
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
					Date date = sdf.parse(msg);
					return date;
				} catch (ParseException e) {
					e.printStackTrace();
				}
            }
            return null;
          }
		  protected String formatDate( Date date)
		  {
			  if(null != date){
				  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				  try {
					  String msg = sdf.format(date);
					  return msg;
				  } catch (Exception e) {
					  e.printStackTrace();
				  }
			  }
			  return "";
		  }
		  protected String formatDateToSqlServer(Date date){
			  if(null != date){
				  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				  try {
					  String msg = sdf.format(date);
					  return msg;
				  } catch (Exception e) {
					  e.printStackTrace();
				  }
			  }
			  return "";
		  }
		  protected Date parseDateToSqlServer(String dataStr){
			  if(StringUtils.isNotEmpty(dataStr)){
	            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	                try {
						Date date = sdf.parse(dataStr);
						return date;
					} catch (ParseException e) {
						e.printStackTrace();
					}
	            }
	            return null;
		  }
}
