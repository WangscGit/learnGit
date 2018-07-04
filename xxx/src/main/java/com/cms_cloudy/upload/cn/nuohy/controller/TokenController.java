package com.cms_cloudy.upload.cn.nuohy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cms_cloudy.upload.cn.nuohy.config.Configurations;
import net.sf.json.JSONObject;


@Controller
@RequestMapping(value="/tokencontroller")
public class TokenController {
	static final String FILE_NAME_FIELD = "name";
	static final String FILE_SIZE_FIELD = "size";
	static final String TOKEN_FIELD = "token";
	static final String SERVER_FIELD = "server";
	static final String SUCCESS = "success";
	static final String MESSAGE = "message";
	
	@RequestMapping(value="/tk")//1
	public void firstdoget(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		StreamController.uploadSpeed = 7500/Configurations.getUploadSpeed();
		doOptions(req, resp);
		System.out.println("---TokenServlet---");
		String name = req.getParameter(TokenController.FILE_NAME_FIELD);
		String size = req.getParameter(FILE_SIZE_FIELD);	//文件大小
		System.out.println("name:"+name+"       size:"+size);
		String token = name;	//利用文件名和文件大小重新生成编码作为临时文件的名字
		PrintWriter writer = resp.getWriter();
		JSONObject json = new JSONObject();
		try {
			json.put(TOKEN_FIELD, token);
			if (Configurations.isCrossed())
				json.put(SERVER_FIELD, Configurations.getCrossServer());
			json.put(SUCCESS, true);
			json.put(MESSAGE, "");
		} catch (Exception e) {
		}
		/** TODO: save the token. */
		
		writer.write(json.toString());
	}
	
	
	
	
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
    	req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		//resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
	}
	

}
