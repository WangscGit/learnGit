package com.cms_cloudy.upload.cn.nuohy.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.upload.cn.nuohy.config.Configurations;
import com.cms_cloudy.upload.cn.nuohy.util.IoUtil;
import com.cms_cloudy.upload.pojo.FileUploadEntity;
import com.cms_cloudy.upload.service.IFileUploadService;
import com.cms_cloudy.user.pojo.HrUser;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value="/formDatacontroller")
public class FormDataController extends BaseController{
	static final String FILE_FIELD = "file";
	/** when the has read to 10M, then flush it to the hard-disk. */
	public static final int BUFFER_LENGTH = 1024 * 1024 * 10;
	static final int MAX_FILE_SIZE = 1024 * 1024 * 100;
	@Autowired
	private IFileUploadService fileUploadService;
	
	@RequestMapping(value="/fd")
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		System.out.println("--------formDatacontroller----fd---------");
		HrUser user = this.getUserInfo(req);//当前登录人
		String createuser = "";
		if(null != user){
			createuser= user.getLoginName();
		}else{
			createuser = "admin";
		}
		StreamController.uploadSpeed = (7500/Configurations.getUploadSpeed())/2;
		doOptions(req, resp);
		req.setCharacterEncoding("UTF-8");
		PrintWriter writer = resp.getWriter();
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (!isMultipart) {
			writer.println("ERROR: It's not Multipart form.");
			return;
		}
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		ServletFileUpload upload = new ServletFileUpload();
		InputStream in = null;
		String token = null;
		String bufferfile = "";
		Map<String,String> map = new HashMap<String,String> ();
		try {
			FileItemIterator iter = upload.getItemIterator(req);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				in = item.openStream();
				if (item.isFormField()) {
					String value = Streams.asString(in);
					if (TokenController.TOKEN_FIELD.equals(name)) {
						token = value;
					}
					System.out.println(name + ":" + value);
				} else {
					String uuid = UUID.randomUUID().toString();
					System.out.println("========String fileName = item.getName();========");
					String realName = req.getParameter(TokenController.FILE_NAME_FIELD);
					String fileName = item.getName();
					String path1 = req.getParameter("param1");
					String newFileName = uuid +"."+ fileName.split("\\.")[fileName.split("\\.").length-1];
					String cgDir = Configurations.getCGConfig("FileDirect", "");
					String cgSubDir = Configurations.getCGConfig("SubDir", "");
					String yearAndMonth = IoUtil.getYMDpath();
					String url = cgSubDir+"/"+yearAndMonth+"/" + newFileName;
					url = req.getSession().getServletContext().getRealPath("/")+"WEB-INF"+File.separator+path1+File.separator+realName;
					File file = new File(url);
					String filename="";
					if(file.exists()){
						filename = getFileName(realName);
						url = req.getSession().getServletContext().getRealPath("/")+"WEB-INF"+File.separator+path1+File.separator+filename;
					}
					if(!"cms_server/Datasheet".equals(path1) && !"cms_server/Design/SYM/ADRSYM".equals(path1)){
						FileUploadEntity entitys = new FileUploadEntity();
						 if(!filename.equals("")){
							 entitys.setName(filename);
						 }else{
							 entitys.setName(item.getName());
						 }
					     entitys.setFilePath(path1);
					     entitys.setSize(file.length()+"");
					     entitys.setUploadDate(new Date());
					     entitys.setUploader(createuser);
						fileUploadService.inserttFileUpload(entitys);
					}
					start = IoUtil.streaming(in, token, newFileName,url);
					map.put("name", fileName);
					map.put("uuid", uuid);
					bufferfile = uuid +"."+ fileName.split("\\.")[fileName.split("\\.").length-1];
				}
			}
		} catch (FileUploadException fne) {
			success = false;
			message = "Error: " + fne.getLocalizedMessage();
		} finally {
			try {
				if (success){
					json.put(StreamController.START_FIELD, start);
				}
				json.put("map", map);
				json.put(TokenController.SUCCESS, success);
				json.put(TokenController.MESSAGE, message);
			} catch (Exception e) {}
			writer.write(json.toString());
			System.out.println(json.toString());
			//关闭流并且删除缓存文件
			IoUtil.close(in);
			IoUtil.close(writer);
			IoUtil.getFile(bufferfile).delete();
		}
		
	}
	
	
	
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=utf-8");
		
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.addHeader("Access-Control-Allow-Headers", "POWERED-BY-FANTONG");
		//resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
	}
	
	public String getFileName(String filename){
		 Map<String,Object> maps = new HashMap<String,Object>();
	     maps.put("Name", filename);
		List<FileUploadEntity> fileList= fileUploadService.selectFileUploadList(maps);
		if(null != fileList && fileList.size()>0){
			String[] fileNames = filename.split("\\.");
			int num = fileList.size();
			filename=fileNames[0]+"_"+num+"."+fileNames[1];
		}
		return filename;
	}
}
