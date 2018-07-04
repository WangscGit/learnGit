package com.cms_cloudy.upload.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.alibaba.fastjson.JSON;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.upload.pojo.FileUploadEntity;
import com.cms_cloudy.upload.service.IFileUploadService;
import com.cms_cloudy.upload.util.FileUtil;
import com.cms_cloudy.user.pojo.HrUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;

/**
 * 文件上传Controller
 * @author WangSc
 */
@Controller
@RequestMapping(value="/fileUpload")
public class FileUploadController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(FileUploadController.class);
   @Autowired
	private IFileUploadService fileUploadService;
 
   /**
    * 文件上传分页查询
    * @param request
    * @param response
    */
   @RequestMapping(value="/selectFileUploadList.do")
   public void selectFileUploadList(HttpServletRequest request,HttpServletResponse response){
	    request.getSession().setAttribute("uploadListSession", null);
		request.getSession().setAttribute("docSession", null);
	   Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		List<FileUploadEntity> fileList = null;
		PageInfo<FileUploadEntity> page = null;
		String pageNo = request.getParameter("pageNo");
		// 分页初始化
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = "1";
		}
		String pageSize = "15";
     	String path = request.getParameter("path");
		 try {
		PageHelper.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
	    if("\"undefined\"".equals(path) && !"".equals(path)){
			 fileList = fileUploadService.selectFileUploadList(map);
	    }else{
	    	List<Object> str = JSON.parseArray(path);
	    	fileList = fileUploadService.selectFileByPath(str);
	    }
        if(null != fileList){
        	page = new PageInfo<FileUploadEntity>(fileList);
        	mapJson.put("fileList", page.getList());
        	mapJson.put("count", page.getTotal());
			mapJson.put("pageNo", pageNo);
            String msg = JSONObject.fromObject(mapJson).toString();
		    outputJson(response,msg);
        }else{
        	mapJson.put("fileList", null);
            String msg = JSONObject.fromObject(mapJson).toString();
		    outputJson(response,msg);
        }
		} catch (Exception e) {
				e.printStackTrace();
				logger.error("文件上传分页查询：", e);
		}
   }
   /**
    * 添加上传文件
    * @param request
    * @param response
 * @throws IOException 
    */
   @RequestMapping(value="/inserttFileUpload.do")
   public void inserttFileUpload(HttpServletRequest request,HttpServletResponse response) throws IOException{
	   String savePath = getAbsoluteBasePath(request);
       //保存文件的路径
       savePath = savePath + "uploadFile/";
       File f1 = new File(savePath);
       System.out.println(savePath);
       //如果文件不存在,就新建一个
       if (!f1.exists()) {
           f1.mkdirs();
       }
       //这个是文件上传需要的类,具体去百度看看,现在只管使用就好
       DiskFileItemFactory fac = new DiskFileItemFactory();
       ServletFileUpload upload = new ServletFileUpload(fac);
       upload.setHeaderEncoding("utf-8");
       List fileList = null;
       try {
           fileList = upload.parseRequest(request);
       } catch (FileUploadException ex) {
           return;
       }
       //迭代器,搜索前端发送过来的文件
       Iterator<FileItem> it = fileList.iterator();
       String name = "";
       String extName = "";
       while (it.hasNext()) {
           FileItem item = it.next();
           //判断该表单项是否是普通类型
           if (!item.isFormField()) {
               name = item.getName();
               long size = item.getSize();
               String type = item.getContentType();
               System.out.println(size + " " + type);
               if (name == null || name.trim().equals("")) {
                   continue;
               }
               // 扩展名格式： extName就是文件的后缀,例如 .txt
               if (name.lastIndexOf(".") >= 0) {
                   extName = name.substring(name.lastIndexOf("."));
               }
               File file = null;
               do {
                   // 生成文件名：
                   name = UUID.randomUUID().toString();
                   file = new File(savePath + name + extName);
               } while (file.exists());
               File saveFile = new File(savePath + name + extName);
               try {
                   item.write(saveFile);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }
       response.getWriter().print(name + extName);
		
   }
   /**
    * 删除上传文件
    * @param request
    * @param response
    */
   @RequestMapping(value="/deleteFileUpload.do")
   public void deleteFileUpload(HttpServletRequest request,HttpServletResponse response){
	   String ids= request.getParameter("ids");
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileUploadEntity> fileList = null;
	   List<Object> idList = JSON.parseArray(ids);
	   try {
	   if(idList.size()>0){
		   for(int i=0;i<idList.size();i++){
			   map.put("id", idList.get(i).toString());
			   fileList = fileUploadService.selectFileUploadList(map);
			   String name = fileList.get(0).getName();
			   String path = getAbsoluteBasePath(request)+"WEB-INF"+File.separator+fileList.get(0).getFilePath();
			  File f = new File(path+File.separator+name);
			  if(f.isDirectory()){
				  if(f.exists()){
					  deletefileMulu(path+File.separator+name);
				  }
			  }else{
				  if(f.exists()){
					   deleteFile(path,name);
				  }
			  }
			   fileUploadService.deleteFileUpload(Integer.valueOf(idList.get(i).toString()));
			   String msg = JSONObject.fromObject(null).toString();
			   outputJson(response,msg);
		   }
	   }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除上传文件", e);
		}
   }
   public void deleteFile(String path,String fileName){
	   File folder = new File(path);
		File[] files = folder.listFiles();
		for(File file:files){
			if(file.getName().equals(fileName)){
				file.delete();
			}
		}
   }
   
   /**
	 *  获得项目的根路径
	 * @param request
	 * @return
	 */
	public String getAbsoluteBasePath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

@RequestMapping(value="/uploadFile.do")  
@ResponseBody  
public void uploadFile(HttpServletRequest req,HttpServletResponse response) throws IOException{  
     //设置文件保存的本地路径
	 try{
	HrUser user = this.getUserInfo(req);//当前登录人
	String createuser = "";
	if(null != user){
		createuser= user.getLoginName();
	}else{
		createuser = "admin";
	}
	String paths = req.getParameter("path");
	 MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)req;
	 Map<String,MultipartFile> map = multipartHttpServletRequest.getFileMap();
	 String path = getAbsoluteBasePath(req)+"WEB-INF"+File.separator+paths;
	 File file=new File(path);
	 if(!file.exists()){
	  file.mkdirs();
	 }
	  for(Map.Entry<String,MultipartFile> entity:map.entrySet()){
	  MultipartFile multipartFile=entity.getValue();
     String dis = multipartFile.getOriginalFilename();
     String diss = multipartFile.getSize()+"";
     Map<String,Object> maps = new HashMap<String,Object>();
     maps.put("Name", dis);
	List<FileUploadEntity> fileList= fileUploadService.selectFileUploadList(maps);
	if(null != fileList && fileList.size()>0){
		String[] fileNames = dis.split("\\.");
		int num = fileList.size();
		dis=fileNames[0]+"_"+num+"."+fileNames[1];
	}
     FileUploadEntity entitys = new FileUploadEntity();
     entitys.setName(dis);
      entitys.setFilePath(paths);
     entitys.setSize(diss);
     entitys.setUploadDate(new Date());
     entitys.setUploader(createuser);
		fileUploadService.inserttFileUpload(entitys);
	  File ff = new File(path,dis);
	  multipartFile.transferTo(ff);
	  }
	  String msg  = JSONObject.fromObject(null).toString();
	  outputMsg(response,msg);
	 }catch (Exception e){
	  e.printStackTrace();
	 }
} 
/**
 * 是否存在相同的文件名
 * @param req
 * @param response
 */
@RequestMapping(value="/checkFileIsExis.do")  
public void checkFileIsExis(HttpServletRequest req,HttpServletResponse response){
	Map<String, Object> map = new HashMap<String, Object>();
	Map<String, Object> mapJson = new HashMap<String, Object>();
	map.put("Name", req.getParameter("fileName"));
		 try {
		List<FileUploadEntity> fileList = fileUploadService.selectFileUploadList(map);
		   if(fileList.size()>0){
			   mapJson.put("result", "yes");
			   String msg = JSONObject.fromObject(mapJson).toString();
			   outputJson(response,msg);
		   }else{
			   mapJson.put("result", "no");
			   String msg = JSONObject.fromObject(mapJson).toString();
			   outputJson(response,msg);
		   }
		 }
		 catch(Exception e){
			  e.printStackTrace();
		 }
}
/**
 * 操作异常上传文件
 * @param req
 * @param response
 */
@RequestMapping(value="/changFile.do")  
public void changFile(HttpServletRequest req,HttpServletResponse response){
	Map<String, Object> map = new HashMap<String, Object>();
	Map<String, Object> mapJson = new HashMap<String, Object>();
	String Name = req.getParameter("fileName");
	String path = req.getParameter("path");
	String type = req.getParameter("type");
	map.put("Name", req.getParameter("fileName"));
		 try {
	     if(type.equals("chang")){
	 		List<FileUploadEntity> fileListOld = fileUploadService.selectFileUploadList(map);
	 		List<FileUploadEntity> fileListNew = fileUploadService.selectFileUploadListByName(map);
	 		FileUploadEntity fileOld = fileListOld.get(0);
	 		FileUploadEntity fileNew = fileListNew.get(0);
	 		delete(fileOld.getFilePath(),Name);
	 		String str = fileNew.getName();
	 		fileNew.setName(Name);
	 		fileUploadService.updateFileUpload(fileNew);
	 		File newName = new File(fileOld.getFilePath()+File.separator+str);
	 		File oldName = new File(fileOld.getFilePath()+File.separator+Name);
	 		newName.renameTo(oldName); //动态修改文件名称
	 		outputJson(response,"");
	     }else if(type.equals("delete")){
	    	 List<FileUploadEntity> fileListNew = fileUploadService.selectFileUploadListByName(map);
	    	 FileUploadEntity newFile = fileListNew.get(0);
	    	 fileUploadService.deleteFileUpload(Integer.valueOf(newFile.getId()+""));
		 		delete(newFile.getFilePath(),newFile.getName());
		 		outputJson(response,"");
	     }
		List<FileUploadEntity> fileList = fileUploadService.selectFileUploadList(map);
		   if(fileList.size()>0){
			   mapJson.put("result", "yes");
			   String msg = JSONObject.fromObject(mapJson).toString();
			   outputJson(response,msg);
		   }else{
			   mapJson.put("result", "no");
			   String msg = JSONObject.fromObject(mapJson).toString();
			   outputJson(response,msg);
		   }
		 }
		 catch(Exception e){
			  e.printStackTrace();
		 }
}
//删除某个文件
public void delete(String path,String fileName){
	File folder = new File(path);
	File[] files = folder.listFiles();
	for(int x=0;x<files.length;x++){
		File file = files[x];
		if(file.getName().equals(fileName)){
			file.delete();
		}
	}
}
public static boolean deletefileMulu(String delpath) throws Exception {
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
                	deletefileMulu(delpath + "\\" + filelist[i]);
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
/**
 * 上传文件名是否相同
 * @param req
 * @param response
 */
@RequestMapping(value="/checkNameAlike.do")
 public void checkNameAlike(HttpServletRequest req,HttpServletResponse response){
	 try {
	 List<Map<String,Object>> list = (List<Map<String, Object>>) req.getSession().getAttribute("uploadListSession");
	 if(null == list || list.size()<=0){
		 list = null;
	 }
	 String msg = JSON.toJSONString(list);
	 outputJson(response,msg);
	} catch (Exception e) {
		e.printStackTrace();
	}
 }
/**
 * 保存相同名称上传文件
 * @param req
 * @param response
 */
@RequestMapping(value="/saveAlikeFile.do")
public void saveAlikeFile(HttpServletRequest req,HttpServletResponse response){
	 Map<String,Object> maps = new HashMap<String,Object>();
	 String dataList = req.getParameter("dataList");
	 List<Map> list = new ArrayList<Map>();
	 String rootPath = this.getAbsoluteBasePath(req);
		FileUtil util = new FileUtil();//文件工具类
		HrUser user = this.getUserInfo(req);// 当前登录人
		String createuser = "";
		if (null != user) {
			createuser = user.getLoginName();
		} else {
			createuser = "";
		}
	 try {
		 list = JSON.parseArray(dataList, Map.class);
	 for(Map<String,Object> map:list){
        	String fileName = map.get("fileName").toString();
        	String path = map.get("path").toString();
        	String size = map.get("size").toString();
        	String linshiPath = map.get("linshiPath").toString();
        	String startName = "";
        	String endName =  "";
        	String newFileName = "";
        	if(null != map.get("type")){
        		newFileName = newVersion(rootPath+"WEB-INF"+File.separator+path+File.separator,fileName,"");
            	//maps.put("fileName", fileName+"_");
        	}else{
        		startName = fileName.substring(0, fileName.lastIndexOf("."));
        		endName = fileName.substring(fileName.lastIndexOf(".") + 1);
        		newFileName = newVersion(rootPath+"WEB-INF"+File.separator+path+File.separator,startName,endName);
//        		maps.put("startName", startName+"_");
//        		maps.put("endName", endName);
        	}
//		    maps.put("FilePath", path);
//			List<FileUploadEntity> fileList= fileUploadService.selectFileUploadList(maps);
//			int num = 0;
//			if(null != map.get("type")){
//				  num = Integer.valueOf(fileList.size())+Integer.valueOf(1);
//			}else{
//				  num = Integer.valueOf(fileList.size());
//			}
//			if(null != map.get("type")){
//				newFileName = fileName+"_"+num;
//			}else{
//				newFileName = startName+"_"+num+"."+endName;
//			}
			String url = rootPath+"WEB-INF"+File.separator+path+File.separator+newFileName;
			File file0 = new File(rootPath+"WEB-INF"+File.separator+path+File.separator+fileName);
			File file1 = new File(linshiPath);
			File file2 = new File(url);
			file0.renameTo(file2);//修改文件(文件夹)名称
			if(null != map.get("type")){
				if(!file0.exists()){
					file0.mkdirs();
				}
//	        	String docPath = map.get("docPath").toString();
//				util.copy(docPath, url);//复制
//				final long total = new GetFolderSize()
//		                .getTotalSizeOfFile(docPath);
//				size = String.valueOf(total);//整个文件夹大小
				util.copy(linshiPath, rootPath+"WEB-INF"+File.separator+path+File.separator+fileName);//复制
			}else{
				String tempParentPath = file1.getParentFile().toString();
//				File file3 = new File(tempParentPath+File.separator+newFileName);
//				file1.renameTo(file3);//文件重新命名
				String path2 =file2.getParentFile().toString();
				file2 = new File(path2);
				if(!file2.exists()){
					file2.mkdirs();
				}
				util.copy(tempParentPath, path2);
			}
				 FileUploadEntity entitys = new FileUploadEntity();
				 entitys.setName(fileName);
			     entitys.setFilePath(path);
			     entitys.setSize(size);
			     entitys.setUploadDate(new Date());
			     entitys.setUploader(createuser);
			     Map<String,Object> updateMap = new HashMap<String,Object>();
			     updateMap.put("wrName", fileName);
			     updateMap.put("upName", newFileName);
			     updateMap.put("path", path);
			     updateMap.put("insertEntity", entitys);
			     this.fileUploadService.updateFileUploadByName(updateMap);
			if(null != map.get("type")){
				break;
			}
	 }
	 deletefileMulu(rootPath+"WEB-INF"+File.separator+"temporary");//删除临时目录
	 //清除session---uploadListSession");//docSession
	 req.getSession().setAttribute("uploadListSession", null);
	 req.getSession().setAttribute("docSession", null);
	 String msg = JSON.toJSONString(null);
	 outputJson(response,msg);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
/**
 * 覆盖相同名称上传文件
 * @param req
 * @param response
 */
@RequestMapping(value="/coverAlikeFile.do")
public void coverAlikeFile(HttpServletRequest req,HttpServletResponse response){
	 Map<String,Object> maps = new HashMap<String,Object>();
	 String dataList = req.getParameter("dataList");
	 List<Map> list = new ArrayList<Map>();
	 String rootPath = this.getAbsoluteBasePath(req);
		FileUtil util = new FileUtil();//文件工具类
		HrUser user = this.getUserInfo(req);// 当前登录人
		String createuser = "";
		if (null != user) {
			createuser = user.getLoginName();
		} else {
			createuser = "";
		}
	 try {
	  list = JSON.parseArray(dataList, Map.class);
	 for(Map<String,Object> map:list){
        	String fileName = map.get("fileName").toString();
        	String path = map.get("path").toString();
        	String size = map.get("size").toString();
        	String linshiPath = map.get("linshiPath").toString();
        	maps.put("Name", fileName);
		    maps.put("FilePath", path);
			List<FileUploadEntity> fileList= fileUploadService.selectFileUploadList(maps);
			fileUploadService.deleteFileUpload(Integer.valueOf(fileList.get(0).getId()+""));//删除源数据
			String url = rootPath+"WEB-INF"+File.separator+path+File.separator+fileName;
			File file1 = new File(linshiPath);
			File file2 = new File(url);
			if(null != map.get("type")){
				deletefileMulu(url);//删除源文件
				if(!file2.exists()){
					file2.mkdirs();
				}
//	        	String docPath = map.get("docPath").toString();
//				util.copy(docPath, url);//复制
//				final long total = new GetFolderSize()
//		                .getTotalSizeOfFile(docPath);
//				size = String.valueOf(total);//整个文件夹大小
				util.copy(linshiPath, url);//复制
			}else{
				String path2 =file2.getParentFile().toString();
				this.delete(path2, fileName);//删除源文件
				file2 = new File(path2);
				if(!file2.exists()){
					file2.mkdirs();
				}
				util.copy(file1.getParentFile().toString(), path2);
			}
				 FileUploadEntity entitys = new FileUploadEntity();
				 entitys.setName(fileName);
			     entitys.setFilePath(path);
			     entitys.setSize(size);
			     entitys.setUploadDate(new Date());
			     entitys.setUploader(createuser);
				fileUploadService.inserttFileUpload(entitys);
			if(null != map.get("type")){
				break;
			}
	 }
	 deletefileMulu(rootPath+"WEB-INF"+File.separator+"temporary");//删除临时目录
	 //清除session---uploadListSession");//docSession
	 req.getSession().setAttribute("uploadListSession", null);
	 req.getSession().setAttribute("docSession", null);
	 String msg = JSON.toJSONString(null);
	 outputJson(response,msg);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
/**
 * 删除相同名称上传文件(取消上传)
 * @param req
 * @param response
 */
@RequestMapping(value="/deleteAlikeFile.do")
public void deleteAlikeFile(HttpServletRequest req,HttpServletResponse response){
	 String rootPath = this.getAbsoluteBasePath(req);
	 req.getSession().setAttribute("uploadListSession", null);
	 req.getSession().setAttribute("docSession", null);
	 try {
		deletefileMulu(rootPath+"WEB-INF"+File.separator+"temporary");
		String msg = JSON.toJSONString(null);
		outputJson(response,msg);
	} catch (Exception e) {
		e.printStackTrace();
	}
   }
/**
 * 查询上传文件
 * @param request
 * @param response
 */
@RequestMapping(value="/selectAllFile.do")
public void selectAllFile(HttpServletRequest request,HttpServletResponse response){
	String filepath = request.getParameter("path");
	Map<String, Object> map = new HashMap<String, Object>();
	Map<String, Object> resultMap = new HashMap<String, Object>();
	map.put("FilePath", filepath);
	List<FileUploadEntity> fileList = null;
	try{
	fileList = fileUploadService.selectFileOrderByName(map);
	FileUploadEntity fileEntity = fileUploadService.selectLatestData(map);
	resultMap.put("list", fileList);
	resultMap.put("pojo", fileEntity);
	String msg = JSON.toJSONString(resultMap);
	outputJson(response,msg);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
/**
 * 数据手册单独上传
 * @param updateFiles
 * @param request
 * @param response
 * @throws IOException
 */
@RequestMapping(value = "/batchUploadFile.do")
@ResponseBody
public void batchUploadFile(@RequestParam("dateSheetUpload") MultipartFile[] updateFiles, String uploadpath,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			if(StringUtils.isEmpty(uploadpath)){
				uploadpath="cms_server/Datasheet";
			}
			String path = getAbsoluteBasePath(request) + "WEB-INF" + File.separator;
			HrUser user = this.getUserInfo(request);// 当前登录人
			List<Map<String,Object>> alikeFileList = new ArrayList<Map<String,Object>>();
			Map<String,Object> resultMap = new HashMap<String,Object>();
			String resultMsg = "";
			String createuser = "";
			if (null != user) {
				createuser = user.getLoginName();
			} else {
				createuser = "admin";
			}
			for (int x = 0; x < updateFiles.length; x++) {
				MultipartFile multipartFile = updateFiles[x];
				path = path + uploadpath;
				String fileName = multipartFile.getOriginalFilename();// 文件名称
				String fileSize = multipartFile.getSize() + "";
				Map<String, Object> maps = new HashMap<String, Object>();
				maps.put("Name", fileName);
				maps.put("FilePath", uploadpath);
				List<FileUploadEntity> fileList = fileUploadService.selectFileUploadList(maps);// 是否存在相同文件判断
				if (null != fileList && fileList.size() > 0) {
					path = getAbsoluteBasePath(request) + "WEB-INF"+File.separator+"temporary"+File.separator+new Date().getTime();//临时路径
				}
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				if (null == fileList || fileList.size() <= 0) {
					FileUploadEntity entitys = new FileUploadEntity();
					entitys.setName(fileName);
					entitys.setFilePath(uploadpath);
					entitys.setSize(fileSize);
					entitys.setUploadDate(new Date());
					entitys.setUploader(createuser);
					fileUploadService.inserttFileUpload(entitys);
				} else {
					Map<String,Object> newMap = new HashMap<String,Object>();
					newMap.put("fileName", fileName);
					newMap.put("size", fileSize);
					newMap.put("path", uploadpath);
					newMap.put("linshiPath", path+File.separator+fileName);
					alikeFileList.add(newMap);
					resultMsg += fileName+",";
				}
				File ff = new File(path, fileName);
				multipartFile.transferTo(ff);
			}
			if(alikeFileList.size()==0){
				resultMsg = "上传成功！";
			}else{
				resultMsg = resultMsg.substring(0,resultMsg.length()-1);
			}
			resultMap.put("list", alikeFileList);
			resultMap.put("resultMsg", resultMsg);
			String msg = JSONObject.fromObject(resultMap).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * 上传管理批量文件上传(非文件夹)
 */
@RequestMapping(value = "/batchUploadFilesFromManage.do")
@ResponseBody
public void batchUploadFilesFromManage(@RequestParam("batchUploadFilesInp") MultipartFile[] updateFiles,
		HttpServletRequest request, HttpServletResponse response){
	  String filePath = request.getParameter("filePath");
	  try {
			String path = getAbsoluteBasePath(request) + "WEB-INF" + File.separator;
			HrUser user = this.getUserInfo(request);// 当前登录人
			List<Map<String,Object>> alikeFileList = new ArrayList<Map<String,Object>>();
			Map<String,Object> resultMap = new HashMap<String,Object>();
			String resultMsg = "";
			String createuser = "";
			if (null != user) {
				createuser = user.getLoginName();
			} else {
				createuser = "admin";
			}
			for (int x = 0; x < updateFiles.length; x++) {
				MultipartFile multipartFile = updateFiles[x];
				path = path + filePath;
				String fileName = multipartFile.getOriginalFilename();// 文件名称
				String fileSize = multipartFile.getSize() + "";
				Map<String, Object> maps = new HashMap<String, Object>();
				maps.put("Name", fileName);
				maps.put("FilePath", filePath);
				List<FileUploadEntity> fileList = fileUploadService.selectFileUploadList(maps);// 是否存在相同文件判断
				if (null != fileList && fileList.size() > 0) {
					path = getAbsoluteBasePath(request) + "WEB-INF"+File.separator+"temporary"+File.separator+new Date().getTime();//临时路径
				}
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				if (null == fileList || fileList.size() <= 0) {
					FileUploadEntity entitys = new FileUploadEntity();
					entitys.setName(fileName);
					entitys.setFilePath(filePath);
					entitys.setSize(fileSize);
					entitys.setUploadDate(new Date());
					entitys.setUploader(createuser);
					fileUploadService.inserttFileUpload(entitys);
				} else {//存在相同文件名
					Map<String,Object> newMap = new HashMap<String,Object>();
					newMap.put("fileName", fileName);
					newMap.put("size", fileSize);
					newMap.put("path", filePath);
					newMap.put("linshiPath", path+File.separator+fileName);
					alikeFileList.add(newMap);
					resultMsg += fileName+",";
				}
				File ff = new File(path, fileName);
				multipartFile.transferTo(ff);//上传至项目路径
				path = getAbsoluteBasePath(request) + "WEB-INF" + File.separator;
			}
			if(alikeFileList.size()==0){
				resultMsg = "上传成功！";
			}else{
				resultMsg = resultMsg.substring(0,resultMsg.length()-1);
			}
			resultMap.put("list", alikeFileList);
			resultMap.put("resultMsg", resultMsg);
			String msg = JSONObject.fromObject(resultMap).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
}
/**
 * 上传文件夹
 */
@RequestMapping(value = "/uploadFolderForm.do")
@ResponseBody
public void uploadFolderForm(@RequestParam("uploadFolderInp") MultipartFile[] updateFiles,
		HttpServletRequest request, HttpServletResponse response){
	String filePath = request.getParameter("filePath");
	try {
		String path = getAbsoluteBasePath(request) + "WEB-INF" + File.separator;
		HrUser user = this.getUserInfo(request);// 当前登录人
		List<Map<String,Object>> alikeFileList = new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		long fileSize = 0;
		long curentTime = new Date().getTime();
		String resultMsg = "";
		String createuser = "";
		if (null != user) {
			createuser = user.getLoginName();
		} else {
			createuser = "admin";
		}
		for (int x = 0; x < updateFiles.length; x++) {
			MultipartFile multipartFile = updateFiles[x];
			CommonsMultipartFile cf= (CommonsMultipartFile)multipartFile; 
	        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
			String basePath = fi.getName();
			String fileName = basePath.split("/")[0];// 文件名称"发票"
			fileSize += multipartFile.getSize();
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("Name", fileName);
			maps.put("FilePath", filePath);
			List<FileUploadEntity> fileList = fileUploadService.selectFileUploadList(maps);// 是否存在相同文件判断
			if (null != fileList && fileList.size() > 0) {
				path = path + "temporary"+File.separator+curentTime+ File.separator +basePath;//临时路径
			}else{
				path = path +  filePath + File.separator + basePath;//正式路径
			}
			File fileWay = new File(path);
			File file = new File(fileWay.getParent());
			if (!file.exists()) {
				file.mkdirs();
			}
			if(x == updateFiles.length-1){//上传完最后一个文件后 进行数据同步
				if (null == fileList || fileList.size() <= 0) {
					FileUploadEntity entitys = new FileUploadEntity();
					entitys.setName(fileName);
					entitys.setFilePath(filePath);
					entitys.setSize(String.valueOf(fileSize));
					entitys.setUploadDate(new Date());
					entitys.setUploader(createuser);
					fileUploadService.inserttFileUpload(entitys);
				} else {//存在相同文件名
					Map<String,Object> newMap = new HashMap<String,Object>();
					newMap.put("fileName", fileName);
					newMap.put("size", fileSize);
					newMap.put("path", filePath);
					newMap.put("linshiPath", getAbsoluteBasePath(request) + "WEB-INF" + File.separator+"temporary"+File.separator+curentTime+ File.separator+fileName);
					newMap.put("type", "folder");
					alikeFileList.add(newMap);
					resultMsg += fileName+",";
				}
			}
			File ff = new File(fileWay.getParent(), fileWay.getName());
			multipartFile.transferTo(ff);//上传至项目路径
			path = getAbsoluteBasePath(request) + "WEB-INF" + File.separator;
		}
		if(alikeFileList.size()==0){
			resultMsg = "上传成功！";
		}else{
			resultMsg = resultMsg.substring(0,resultMsg.length()-1);
		}
		resultMap.put("list", alikeFileList);
		resultMap.put("resultMsg", resultMsg);
		String msg = JSONObject.fromObject(resultMap).toString();
		outputJson(response, msg);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
//文件保存操作 文件名获取
public String newVersion(String path, String filename, String extentNam){
	int x=1;
	String newFileName = "";
	if(StringUtils.isEmpty(extentNam)){//文件夹
		 newFileName = filename;
		while(true){
			File file = new File(path+newFileName);
			if(file.exists() && file.isDirectory()){
				newFileName = filename+"_"+x;
				x++;
			}else{
				break;
			}
		}
	}else{//文件
		 newFileName = filename+"."+extentNam;
		while(true){
			File file = new File(path+newFileName);
			if(!file.exists()){
				break;
			}else{
				newFileName = filename+"_"+x+"."+extentNam;
				x++;
			}
		}
	}
	return newFileName;
}
//public String NewVersion(string path, string filename, string extentName)
//{
//	String uploadFolder = System.AppDomain.CurrentDomain.BaseDirectory;
//    if (!uploadFolder.EndsWith("\\")) uploadFolder += "\\";
//    String dirCopyname = "";
//    if (!String.IsNullOrEmpty(path))
//    {
//        dirCopyname = filename + extentName;
//        int i = 0;
//        while (File.Exists(uploadFolder + path + dirCopyname))
//        {
//            i++;
//            dirCopyname = filename + "_" + i + extentName;
//        }
//        if (File.Exists(uploadFolder + path + filename + extentName)) File.Copy(uploadFolder + path + filename + extentName, uploadFolder + path + dirCopyname);
//    }
//    return dirCopyname;
//}
}
