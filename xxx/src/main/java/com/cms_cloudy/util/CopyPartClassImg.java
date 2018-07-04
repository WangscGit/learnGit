package com.cms_cloudy.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;

/**
 * Servlet implementation class CopyPartClassImg
 */
@Controller
public class CopyPartClassImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletConfig config;
    @Override
	public void init(ServletConfig config) throws ServletException {
    	this.config=config;
    	ImgThread it=new ImgThread();
    	new Thread(it).start();
	}
    
	class ImgThread implements Runnable {
    	// Tomcat启动结束后执行
    	public void run() {
    		// 子线程:把其他路径下的图片复制到项目路径下
    		//从配置文件读取存放路径
    		InputStream in = null;
    		FileInputStream fis=null;
    		FileOutputStream fos=null;
    		try {
				Properties prop = new Properties();
				in = new BufferedInputStream (new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("param.properties").getPath()));
				prop.load(in);    ///加载属性列表
				String path=prop.getProperty("upload_path");//配置文件中配置的路径
				in.close();
				String imgUrl=config.getServletContext().getRealPath("/")+"uploadImg";
				File file=new File(imgUrl);
				if (!file.exists()){//文件夹不存在时创建文件夹,项目路径下
					file.mkdirs();
				}
				File file1 = new File(path);
				if(!file1.exists()){
					return;
				}
				//获取配置路径下所有文件
				File[] listFiles=file1.listFiles();
				for(File f:listFiles){
					if(f.isDirectory()){
						continue;
					}
					File fi=new File(imgUrl+"/"+f.getName());
					if(fi.exists()){
						continue;
					}
					fis=new FileInputStream(f);
					fos=new FileOutputStream(fi);
					byte [] bytes=new byte[1024];
				    int len;
				    while((len=fis.read(bytes))!=-1){
				    	fos.write(bytes, 0, len);
				    }
				    fos.flush();
				    fis.close();
					fos.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
    		  
    	  }
    	}
	/**
     * @see HttpServlet#HttpServlet()
     */
    public CopyPartClassImg() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
