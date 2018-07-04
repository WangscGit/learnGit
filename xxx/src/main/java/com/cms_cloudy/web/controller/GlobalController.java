package com.cms_cloudy.web.controller;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.alibaba.fastjson.JSON;
import com.cms_cloudy.controller.BaseController;
@Controller
@RequestMapping(value="/globalController")
public class GlobalController extends BaseController implements HandlerInterceptor{
    
	@RequestMapping(value="/changeLanguage.do")
    public void changeLanguage(HttpServletRequest request, HttpServletResponse response){
		String lang = request.getParameter("language");
            if(lang.equals("zh")){
                Locale locale = new Locale("zh", "CN"); 
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale); 
            }
            else if(lang.equals("en")){
                Locale locale = new Locale("en", "US"); 
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            }
            else 
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,LocaleContextHolder.getLocale());
//            if(lang.equals("zh")){
//                Locale locale = new Locale("zh", "CN"); 
//                (new CookieLocaleResolver()).setLocale (request, response, locale);
//            }
//            else if(lang.equals("en")){
//                Locale locale = new Locale("en", "US"); 
//                (new CookieLocaleResolver()).setLocale (request, response, locale);
//            }
//            else 
//            	(new CookieLocaleResolver()).setLocale (request, response, LocaleContextHolder.getLocale());
		try {
			request.getSession().setAttribute("lang", lang);
			String msg = JSON.toJSONString(null);
			outputJson(response,msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 String language = request.getParameter("lang");  
	        if (language != null&&language.equals("zh")) {  
	            Locale locale = new Locale("zh", "CN");  
	            (new CookieLocaleResolver()).setLocale (request, response, locale);  
	            request.setAttribute("lang", language);  
	        } else if (language != null&&language.equals("en")) {  
	            Locale locale = new Locale("en", "US");  
	            (new CookieLocaleResolver()).setLocale (request, response, locale);  
	            request.setAttribute("lang", language);  
	        } else {  
	            (new CookieLocaleResolver()).setLocale (request, response,  
	                    LocaleContextHolder.getLocale());  
	            language = LocaleContextHolder.getLocale().getLanguage();  
	            request.setAttribute("lang", language);  
	        }  
	        return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	@RequestMapping(value="/test", method = {RequestMethod.GET})
    public String test(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(value="langType", defaultValue="zh") String langType){
		if(!model.containsAttribute("contentModel")){
            
            /*if(langType.equals("zh")){
                Locale locale = new Locale("zh", "CN"); 
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale); 
            }
            else if(langType.equals("en")){
                Locale locale = new Locale("en", "US"); 
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            }
            else 
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,LocaleContextHolder.getLocale());*/
            
            if(langType.equals("zh")){
                Locale locale = new Locale("zh", "CN"); 
                //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
                (new CookieLocaleResolver()).setLocale (request, response, locale);
            }
            else if(langType.equals("en")){
                Locale locale = new Locale("en", "US"); 
                //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
                (new CookieLocaleResolver()).setLocale (request, response, locale);
            }
            else 
                //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,LocaleContextHolder.getLocale());
                (new CookieLocaleResolver()).setLocale (request, response, LocaleContextHolder.getLocale());
            
        }
        return "loginpage/index";
    }
	public void mixCookie(HttpServletRequest request,HttpServletResponse response,String lang){
		Cookie[] cookies = request.getCookies();
        	Cookie cookie = new Cookie("lang", lang);
            cookie.setMaxAge(3600 * 60);
            cookie.setPath("/");
            System.out.println("已添加===============");
            response.addCookie(cookie);
             cookies = request.getCookies();
            for(Cookie cookiess : cookies){
                if(cookiess.getName().equals("lang")){
                    System.out.println("原值为:"+cookiess.getValue());
                    cookiess.setValue(lang);
                    cookiess.setPath("/");
                    cookiess.setMaxAge(3600 * 60);// 设置为30min
                    System.out.println("被修改的cookie名字为:"+cookiess.getName()+",新值为:"+cookiess.getValue());
                    response.addCookie(cookiess);
                    break;
            }
        }
	}
}
