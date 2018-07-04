package com.cms_cloudy.upload.cn.nuohy.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.upload.cn.nuohy.config.Configurations;
import com.cms_cloudy.upload.cn.nuohy.util.IoUtil;
import com.cms_cloudy.upload.cn.nuohy.util.Range;
import com.cms_cloudy.upload.pojo.FileUploadEntity;
import com.cms_cloudy.upload.service.IFileUploadService;
import com.cms_cloudy.user.pojo.HrUser;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value="/streamcontroller")
public class StreamController extends BaseController{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -8619685235661387895L;
	/** when the has increased to 10kb, then flush it to the hard-disk. */
	static final int BUFFER_LENGTH = 10240;
	static final String START_FIELD = "start";
	public static final String CONTENT_RANGE_HEADER = "content-range";
	
	public static int uploadSpeed;
	@Autowired
	private IFileUploadService fileUploadService;
	@RequestMapping(method=RequestMethod.GET,value="/upload")//-2
	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		System.out.println("---streamcontroller---doGet----");
		StreamController.uploadSpeed = 7500/Configurations.getUploadSpeed();
		
		doOptions(req, resp);
		final String token = req.getParameter(TokenController.TOKEN_FIELD);	//文件名和大小的hashcode编码
		final String size = req.getParameter(TokenController.FILE_SIZE_FIELD);	//文件大小
		final String fileName = req.getParameter(TokenController.FILE_NAME_FIELD);	//文件名
		final PrintWriter writer = resp.getWriter();
		
		/** TODO: validate your token. */
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		try {
//			String path1 = req.getParameter("param1");
//			String urlSS = req.getSession().getServletContext().getRealPath("/")+"WEB-INF"+File.separator+path1+File.separator+fileName;
//			File f = IoUtil.getTokenedFile(token);		//创建空文件，以及上级文件夹名
//			start = f.length();
//			/** file size is 0 bytes. */
//			if (token.endsWith("_0") && "0".equals(size) && 0 == start)
//				f.renameTo(IoUtil.getFile(fileName));
//		} catch (FileNotFoundException fne) {
//			message = "Error: " + fne.getMessage();
//			success = false;
		} finally {
			try {
				if (success)
					json.put(START_FIELD, start);
				json.put(TokenController.SUCCESS, success);
				json.put(TokenController.MESSAGE, message);
			} catch (Exception e) {}
			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/upload")
	public void savedopost(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		System.out.println("---streamcontroller---doPost----");
		HrUser user = this.getUserInfo(req);//当前登录人
		String createuser = "";
		if(null != user){
			createuser= user.getLoginName();
		}else{
			createuser = "admin";
		}
		doOptions(req, resp);
		
		final String token = req.getParameter(TokenController.TOKEN_FIELD);		//文件名和大小的hashcode编码
		final String fileName =  req.getParameter(TokenController.FILE_NAME_FIELD);	//文件名
		Range range = IoUtil.parseRange(req);
		
		OutputStream out = null;
		InputStream content = null;
		final PrintWriter writer = resp.getWriter();
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		File f = IoUtil.getTokenedFile(token);
		try {
			if (f.length() != range.getFrom()) {
			}
			out = new FileOutputStream(f, true);
			content = req.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[BUFFER_LENGTH];
			while ((read = content.read(bytes)) != -1){
				out.write(bytes, 0, read);
				try {
					Thread.sleep(uploadSpeed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			start = f.length();
		} catch (Exception se) {
			se.printStackTrace();
		} finally {
			IoUtil.close(out);
			IoUtil.close(content);
			/** rename the file */
			Map<String,String> map = new HashMap<String,String>();
			
				/** fix the `renameTo` bug */
				try {
					String cgDir = Configurations.getCGConfig("FileDirect", "");
					String cgSubDir = Configurations.getCGConfig("SubDir", "");
					String yearAndMonth = IoUtil.getYMDpath();
					String uuid = UUID.randomUUID().toString();
					
					String url = cgSubDir+"/"+yearAndMonth+"/"+uuid +"."+ fileName.split("\\.")[fileName.split("\\.").length-1];
					String path1 = req.getParameter("param1");
					url = req.getSession().getServletContext().getRealPath("/")+"WEB-INF"+File.separator+path1+File.separator+fileName;
					if(!"cms_server/Datasheet".equals(path1) && !"cms_server/Design/SYM/ADRSYM".equals(path1)){
						 Map<String,Object> maps = new HashMap<String,Object>();
					     maps.put("Name", fileName);
					     maps.put("FilePath", path1);
						List<FileUploadEntity> fileList= fileUploadService.selectFileUploadList(maps);
						String filename="";
						if(fileList.size()>0){
							String rootPath = req.getSession().getServletContext().getRealPath("/");
							url = rootPath+"WEB-INF"+File.separator+"temporary"+File.separator+new Date().getTime()+File.separator+fileName;//临时路径
							File temporary = new File(url);
							if(!temporary.exists()){
								temporary.mkdir();
							}
							//将相同名称的文件存到临时session中
							List<Map<String,Object>> sessionList = new ArrayList<Map<String,Object>>();
							 sessionList = (List<Map<String,Object>>) req.getSession().getAttribute("uploadListSession");
							if(null == sessionList || "null".equals(sessionList)){
								sessionList = new ArrayList<Map<String,Object>>();
							}
							Map<String,Object> newMap = new HashMap<String,Object>();
							newMap.put("fileName", fileName);
							newMap.put("size", req.getParameter(TokenController.FILE_SIZE_FIELD));
							newMap.put("path", path1);
							newMap.put("linshiPath", url);
							sessionList.add(newMap);
							req.getSession().setAttribute("uploadListSession",sessionList);
							//存储完成
							//filename = getFileName(fileName,fileList);名称自动递增
						}else{
							FileUploadEntity entitys = new FileUploadEntity();
							 if(!filename.equals("")){
								 entitys.setName(filename);
							 }else{
								 entitys.setName(fileName);
							 }
						     entitys.setFilePath(path1);
						     entitys.setSize(req.getParameter(TokenController.FILE_SIZE_FIELD));
						     entitys.setUploadDate(new Date());
						     entitys.setUploader(createuser);
							fileUploadService.inserttFileUpload(entitys);
						}
					}else{
						String[] dirName = fileName.split("/");
						if(dirName.length == 1){
							String names = dirName[0];
							 Map<String,Object> maps = new HashMap<String,Object>();
						     maps.put("Name", names);
						     maps.put("FilePath", path1);
							List<FileUploadEntity> fileList= fileUploadService.selectFileUploadList(maps);
							String filename="";
							if(fileList.size()>0){
								String rootPath = req.getSession().getServletContext().getRealPath("/");
								url = rootPath+"WEB-INF"+File.separator+"temporary"+File.separator+new Date().getTime()+File.separator+fileName;//临时路径
								File temporary = new File(url);
								if(!temporary.exists()){
									temporary.mkdir();
								}
								//将相同名称的文件存到临时session中
								List<Map<String,Object>> sessionList = new ArrayList<Map<String,Object>>();
								 sessionList = (List<Map<String,Object>>) req.getSession().getAttribute("uploadListSession");
								if(null == sessionList || "null".equals(sessionList)){
									sessionList = new ArrayList<Map<String,Object>>();
								}
								Map<String,Object> newMap = new HashMap<String,Object>();
								newMap.put("fileName", fileName);
								newMap.put("size", req.getParameter(TokenController.FILE_SIZE_FIELD));
								newMap.put("path", path1);
								newMap.put("linshiPath", url);
								sessionList.add(newMap);
								req.getSession().setAttribute("uploadListSession",sessionList);
							}else{
								FileUploadEntity entitys = new FileUploadEntity();
								 if(!filename.equals("")){
									 entitys.setName(filename);
								 }else{
									 entitys.setName(names);
								 }
							     entitys.setFilePath(path1);
							     entitys.setSize(req.getParameter(TokenController.FILE_SIZE_FIELD));
							     entitys.setUploadDate(new Date());
							     entitys.setUploader(createuser);
								fileUploadService.inserttFileUpload(entitys);
							}
						}else{
							String rootPath = req.getSession().getServletContext().getRealPath("/");
							String docUrl =rootPath +"WEB-INF"+File.separator+path1+File.separator+dirName[0];
							File fCheck = new File(docUrl);
							List<Map<String,Object>> check = new ArrayList<Map<String,Object>>();
							check = (List<Map<String,Object>>) req.getSession().getAttribute("docSession");
							if(fCheck.exists() && null == check){
								url = rootPath+"WEB-INF"+File.separator+"temporary"+File.separator+fileName;//临时路径
								//将相同名称的文件存到临时session中
								List<Map<String,Object>> sessionList = new ArrayList<Map<String,Object>>();
								 sessionList = (List<Map<String,Object>>) req.getSession().getAttribute("uploadListSession");
								if(null == sessionList || "null".equals(sessionList)){
									sessionList = new ArrayList<Map<String,Object>>();
								}
								Map<String,Object> newMap = new HashMap<String,Object>();
								newMap.put("fileName", dirName[0]);
								newMap.put("name", fileName);
								newMap.put("size", req.getParameter(TokenController.FILE_SIZE_FIELD));
								newMap.put("path", path1);
								newMap.put("linshiPath",  url);
								newMap.put("docPath",  rootPath+"WEB-INF"+File.separator+"temporary"+File.separator+dirName[0]);
								newMap.put("type", "doc");
								sessionList.add(newMap);
								req.getSession().setAttribute("uploadListSession",sessionList);
						    }else{
						    	
						    	List<Map<String,Object>> sessionList = new ArrayList<Map<String,Object>>();
								Map<String,Object> newMap = new HashMap<String,Object>();
								newMap.put("add", "add");
								sessionList.add(newMap);
								req.getSession().setAttribute("docSession",sessionList);
								 Map<String,Object> maps = new HashMap<String,Object>();
								maps.put("Name", dirName[0]);
							    maps.put("FilePath", path1);
								List<FileUploadEntity> fileList= fileUploadService.selectFileUploadList(maps);
								if(null == fileList || fileList.size() == 0){
									 FileUploadEntity entitys = new FileUploadEntity();
									 entitys.setName(dirName[0]);
								     entitys.setFilePath(path1);
								     entitys.setSize(req.getParameter(TokenController.FILE_SIZE_FIELD));
								     entitys.setUploadDate(new Date());
								     entitys.setUploader(createuser);
									fileUploadService.inserttFileUpload(entitys);
								}else{
									FileUploadEntity entitys = fileList.get(0);
									String kbSize = entitys.getSize();
									if(kbSize.indexOf("KB")>-1 || kbSize.indexOf("kb")>-1){
										kbSize = kbSize.substring(0, kbSize.length()-2);
									}
									 int curSize = Integer.valueOf(kbSize)+Integer.valueOf(req.getParameter(TokenController.FILE_SIZE_FIELD));
									 entitys.setName(dirName[0]);
								     entitys.setFilePath(path1);
								     entitys.setSize(String.valueOf(curSize));
								     entitys.setUploadDate(new Date());
								     entitys.setUploader(createuser);
									fileUploadService.updateFileUpload(entitys);
								}
						   }
					   }
					}
					Path pathtrue = f.toPath().resolveSibling(url);
					File parentFile = pathtrue.toFile().getParentFile();
					if(!parentFile.exists()){
						parentFile.mkdirs();
					}
					File delFile = new File(pathtrue.toString());
					if(delFile.exists()){
						delFile.delete();
					}
					Files.move(f.toPath(), pathtrue);
					if("cms_server/Datasheet".equals(path1) || "cms_server/Design/SYM/ADRSYM".equals(path1)){
						String[] sss = f.getPath().split("\\\\");
						String sssxxx = sss[8];
						File fList = new File(req.getSession().getServletContext().getRealPath("/")+"WEB-INF");
						try {
							String pathReals = req.getSession().getServletContext().getRealPath("/")+"WEB-INF"+File.separator+sssxxx;
							File fff = new File(pathReals);
							if(fff.exists()){
								deletefile(pathReals);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}						
					}
				} catch (IOException e) {
					e.printStackTrace();
					success = false;
					message = "Rename file error: " + e.getMessage();
				}
			try {
				if (success){
					json.put(START_FIELD, start);
					json.put("map", map);
				}
				json.put(TokenController.SUCCESS, success);
				json.put(TokenController.MESSAGE, message);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			writer.write(json.toString());
			IoUtil.close(writer);
		}
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
	public String getFileName(String filename,List<FileUploadEntity> fileList){
			String[] fileNames = filename.split("\\.");
			int num = fileList.size();
			filename=fileNames[0]+"_"+num+"."+fileNames[1];
		return filename;
	}
	public static boolean deletefile(String delpath) throws Exception {
        try {

            File file = new File(delpath);
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "\\" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                        System.out.println(delfile.getAbsolutePath() + "删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + "\\" + filelist[i]);
                    }
                }
                System.out.println(file.getAbsolutePath() + "删除成功");
                file.delete();
            }

        } catch (FileNotFoundException e) {
            System.out.println("deletefile() Exception:" + e.getMessage());
        }
        return true;
    }
}
