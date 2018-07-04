package com.cms_cloudy.upload.cn.nuohy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/index")
public class UploadController {
	
	@RequestMapping(value="/turnupload")
	public String turnupload(){
		
		System.out.println("==============turnupload=============");
		
		return "/index";
	}
	
	
	@RequestMapping(value="/turnupload2")
	public String turnupload2(){
		
		System.out.println("==============turnupload2=============");
		
		return "/index2";
	}
	
	
	/*@RequestMapping(value="/uploadPhoto")
	public void uploadPhoto(MultipartFile myphoto,HttpServletRequest request,HttpServletResponse response){
		System.out.println("-------文件名："+myphoto.getOriginalFilename());
		String newPhotoName = Mytools.uploadHead(request, myphoto, 1);
		System.out.println("----newPhotoName---->"+newPhotoName);
		//MyUtil.writeToJson(status, response);
	}*/
	
}
