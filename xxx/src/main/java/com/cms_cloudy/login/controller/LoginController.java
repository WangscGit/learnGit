package com.cms_cloudy.login.controller;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.login.util.RSAUtil;
import com.cms_cloudy.user.pojo.HrRights;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.IHrRightsService;
import com.cms_cloudy.user.service.impl.UserServiceImpl;
import com.cms_cloudy.web.controller.SessionListener;

@Controller
@RequestMapping(value="/login")
public class LoginController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
	@Autowired  
	private UserServiceImpl userService;
	@Autowired
	private IHrRightsService iHrRightsService;
	private static String RSAC = "RSA/ECB/PKCS1Padding";
	String jsonMsg="";
	Map<String,Object> jsonMap = new HashMap<String,Object>();
	@RequestMapping(value="/goPages")
	public ModelAndView goPages(){
		System.out.println("+++++++++1");
		return new ModelAndView("loginpage/cms-1");
	}
	@RequestMapping(value="/loginCheck/{areaId}")
	public void loginCheck(@PathVariable Integer areaId,
            HttpServletRequest request, HttpServletResponse response){
		try {
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("passWord");
			//解密RSA
			RSAPrivateKey prik = (RSAPrivateKey) request.getSession().getAttribute("privateKey");
			Cipher cipher = Cipher.getInstance(RSAC);
			cipher.init(Cipher.DECRYPT_MODE, prik);
			StringBuilder sb = new StringBuilder(new String(cipher.doFinal(Hex.decode(password))));
			password = sb.toString();
			List<HrUser> list = userService.selectUserByName(loginName);
			if(null != list && list.size()>0){
				if(!password.equals(list.get(0).getPassword())){
					jsonMap.put("result", "fail");
					jsonMsg = JSONObject.toJSONString(jsonMap);
					outputJson(response,jsonMsg);
					return;
				}
			}
			if(null == list || list.size()<=0){
				jsonMap.put("result", "notfound");
				jsonMsg = JSONObject.toJSONString(jsonMap);
				outputJson(response,jsonMsg);
				return;
			}
				jsonMap.put("result", "success");
				jsonMsg = JSONObject.toJSONString(jsonMap);
				
				HrUser hruser = new HrUser();
				hruser = list.get(0);
				Vector<Map<String,HttpSession>> onLineUsers = new SessionListener().getOnLineList();
				if(onLineUsers.size() == -1){//在线人数控制
					jsonMap.put("result", "morePeople");
					jsonMsg = JSONObject.toJSONString(jsonMap);
					outputJson(response,jsonMsg);
					return;
				}
				for (int x = 0; x < onLineUsers.size(); x++) {
					Map<String,HttpSession> onLineUser = onLineUsers.get(x);
					Set<String> key = onLineUser.keySet(); 
					if(key.iterator().next().equals(hruser.getLoginName())){//该账户已在其它地方登陆
						jsonMap.put("result", "onLine");
						jsonMap.put("loginUser", hruser);
						jsonMsg = JSONObject.toJSONString(jsonMap);
						outputJson(response,jsonMsg);
						return;
					}
                }
				System.out.println("在线人数："+onLineUsers.size());
				logger.debug("用户：" + list.get(0).getUserName() + " 登录成功！");
				request.getSession().setAttribute("user",hruser);
				//把权限信息保存到session
				saveRightsToSession(request,response);
				outputJson(response,jsonMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 判断用户是否登录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/hasLogined")
    public void hasLogined(HttpServletRequest request, HttpServletResponse response){
		String lang="zh";
		if(null != request.getSession().getAttribute("lang")){
			lang = request.getSession().getAttribute("lang").toString();
		} 
		HrUser user = getUserInfo(request);
         try {
         if(null == user){
        	 jsonMap.put("result", "no");
        	 jsonMap.put("lang", lang);
		     jsonMsg = JSONObject.toJSONString(jsonMap);
		     outputJson(response,jsonMsg);
         }else{
        	 jsonMap.put("result", "yes");
        	 jsonMap.put("lang", lang);
        	 jsonMap.put("user", user);
		     jsonMsg = JSONObject.toJSONString(jsonMap);
		     outputJson(response,jsonMsg); 
         }
		} catch (Exception e) {
			e.printStackTrace();
         }
	}
	//清空登录信息
	@RequestMapping(value="/deleteSession")
	public void deleteSession(HttpServletRequest request, HttpServletResponse response){
		String make = request.getParameter("make");
		if(!"".equals(make)){
			if("user".equals(make)){
				request.getSession().setAttribute("user",null);
				request.getSession().setAttribute("menuRightList",null);
				request.getSession().setAttribute("dataRightList",null);
			}
		}
		  jsonMsg = JSONObject.toJSONString(jsonMap);
		  try {
			outputJson(response,jsonMsg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.equals("deleteSession"+e);
		} 
	}
	//根据登陆人初始化菜单
	@RequestMapping(value="/menuInit")
	public void menuInit(HttpServletRequest request, HttpServletResponse response){
		String loginName = request.getParameter("loginName");
		List<HrUser> user = userService.selectUserByName(loginName);
		HrUser userInfo = user.get(0);
		
	}
	
	//登录成功后把菜单权限和数据权限存入session
	public void saveRightsToSession(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		HrUser user=getUserInfo(request);
		paramMap.put("loginName", user.getLoginName());
		paramMap.put("isDataRights", "False");
		paramMap.put("type","0");
		//菜单权限
		List<HrRights> menuRightList=iHrRightsService.selectUserRights(paramMap);
		request.getSession().setAttribute("menuRightList", menuRightList);
		paramMap.put("isDataRights", "True");
		//数据权限
		List<HrRights> dataRightList=iHrRightsService.selectUserRights(paramMap);
		request.getSession().setAttribute("dataRightList", dataRightList);
	}
	/**
	 * 获取私(公)钥
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getPublicKey.do")
	public void getPublicKey(HttpServletRequest request, HttpServletResponse response){
		 Map<String,String> result = new HashMap<String,String>();  
		RSAUtil rsa = new RSAUtil();
		  KeyPair keyPair;
		try {
		  keyPair = rsa.generateKeyPair();
	      RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
          RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
	      String publicKeyExponent = publicKey.getPublicExponent().toString(16);// 16进制
          String publicKeyModulus = publicKey.getModulus().toString(16);// 16进制
          request.getSession().setAttribute("privateKey", privateKey);
          result.put("pubexponent", publicKeyExponent);
          result.put("pubmodules", publicKeyModulus);
          System.out.println(result);
          String msg = JSON.toJSONString(result);
          outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 强制登陆
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/compulsionLogin.do")
	public void compulsionLogin(HttpServletRequest request, HttpServletResponse response){
		try {
		String user = request.getParameter("hrUser");
    	HrUser hrUser = JSON.parseObject(user, HrUser.class);
		Vector<Map<String,HttpSession>> onLineUsers = new SessionListener().getOnLineList();//获取所有在线的用户
		for (int x = 0; x < onLineUsers.size(); x++) {
			Map<String,HttpSession> onLineUser = onLineUsers.get(x);
			if(onLineUser.keySet().iterator().next().equals(hrUser.getLoginName())){//该账户已在其它地方登陆
				onLineUser.values().iterator().next().invalidate();//清除已登录账户的session
			}
        }
		request.getSession().setAttribute("user",hrUser);//当前请求下设置用户session
		this.saveRightsToSession(request, response);//权限信息维护
		String msg = JSON.toJSONString("");
		outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * session永久有效
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/setMaxInactiveInterval.do")
	public void setMaxInactiveInterval(HttpServletRequest request, HttpServletResponse response){
		try {
			request.getSession().setMaxInactiveInterval(-1);//session永久有效
			String msg = JSON.toJSONString("");
			outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
