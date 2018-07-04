package com.cms_cloudy.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FileController {

	private static final Logger logger = Logger.getLogger(FileController.class);
	 public void checkPcbFile(MultipartHttpServletRequest request, String filePath)
			    throws Exception
			  {
			    try
			    {
			      String path =filePath;
			      if (!createDir(path))
			      {
			    	  logger.error("创建项目文件目录失败");
			        throw new IOException();
			      }

			      Iterator it = request.getFileNames();

			      while (it.hasNext())
			      {
			        String name = (String)it.next();

			        MultipartFile multipartFile = request.getFile(name);
			        String original = multipartFile.getOriginalFilename();
			        if ((original != null) && (!"".equals(original)))
			        {
			          InputStream is = multipartFile.getInputStream();
			          if (is != null)
			          {
			            FileOutputStream fs = new FileOutputStream(path + "/" + original);
			            byte[] buffer = new byte[1048576];
			            int bytesum = 0;
			            int byteread = 0;
			            while ((byteread = is.read(buffer)) != -1)
			            {
			              bytesum += byteread;
			              fs.write(buffer, 0, byteread);
			              fs.flush();
			            }
			            fs.close();
			            is.close();
			          }
			        }
			      } } catch (IOException e) { logger.error("存储物理文件失败", e);
			      throw e;
			    }
			  }
	 /**
	    * 上传管理模块-----上传文件到指定目录
	    * @param request
	    * @param filePath
	    * @return
	    * @throws Exception
	    */
	   public Map<String,Object> getFileElement(MultipartHttpServletRequest request, String filePath)
			    throws Exception
			  {
		   Map<String,Object> map = new HashMap<String,Object>();
			    try
			    {
			      String path =filePath;
			      if (!createDir(path))
			      {
			    	  logger.error("创建项目文件目录失败");
			        throw new IOException();
			      }

			      Iterator it = request.getFileNames();

			      while (it.hasNext())
			      {
			        String name = (String)it.next();

			        MultipartFile multipartFile = request.getFile(name);
			        String original = multipartFile.getOriginalFilename();
			        map.put("fileName", original);
			        map.put("fileSize", multipartFile.getSize());
			        map.put("fileUrl", path);
			        if ((original != null) && (!"".equals(original)))
			        {
			          InputStream is = multipartFile.getInputStream();
			          if (is != null)
			          {
			            FileOutputStream fs = new FileOutputStream(path + "/" + original);
			            byte[] buffer = new byte[1048576];
			            int bytesum = 0;
			            int byteread = 0;
			            while ((byteread = is.read(buffer)) != -1)
			            {
			              bytesum += byteread;
			              fs.write(buffer, 0, byteread);
			              fs.flush();
			            }
			            fs.close();
			            is.close();
			          }
			        }
			      }
			      return map;  
			    } catch (IOException e) { logger.error("存储物理文件失败", e);
			      throw e;
			    }
			  }
	   /**
	    * 器件模块-----导入文件到指定目录
	    * @param request
	    * @param filePath
	    * @return
	    * @throws Exception
	    */
	   public Map<String,Object> getPartFileElement(MultipartHttpServletRequest request, String filePath)
			    throws Exception
			  {
		   Map<String,Object> map = new HashMap<String,Object>();
			    try
			    {
			      String path =filePath;
			      if (!createDir(path))
			      {
			    	  logger.error("创建项目文件目录失败");
			        throw new IOException();
			      }

			      Iterator it = request.getFileNames();

			      while (it.hasNext())
			      {
			        String name = (String)it.next();

			        MultipartFile multipartFile = request.getFile(name);
			        String original = multipartFile.getOriginalFilename();
			        map.put("fileName", original);
			        map.put("fileSize", multipartFile.getSize());
			        map.put("fileUrl", path);
			        if ((original != null) && (!"".equals(original)))
			        {
			          InputStream is = multipartFile.getInputStream();
			          if (is != null)
			          {
			            FileOutputStream fs = new FileOutputStream(path + "/" + original);
			            byte[] buffer = new byte[1048576];
			            int bytesum = 0;
			            int byteread = 0;
			            while ((byteread = is.read(buffer)) != -1)
			            {
			              bytesum += byteread;
			              fs.write(buffer, 0, byteread);
			              fs.flush();
			            }
			            fs.close();
			            is.close();
			          }
			        }
			      }
			      return map;  
			    } catch (IOException e) { logger.error("存储物理文件失败", e);
			      throw e;
			    }
			  }
	  /**
	   * 目录创建
	   * @param dir
	   * @return
	   */
	  public boolean createDir(String dir)
	  {
	    boolean flag = true;
	    File file = new File(dir);
	    if (!file.exists())
	    {
	      return file.mkdirs();
	    }
	    return flag;
	  }
}
