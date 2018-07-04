package com.cms_cloudy.database.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.database.pojo.FileImg;
import com.cms_cloudy.database.pojo.PartClass;
import com.cms_cloudy.database.pojo.PartDefineAttributes;
import com.cms_cloudy.database.service.IPartClassService;
import com.cms_cloudy.user.pojo.HrUser;
@Controller
@RequestMapping(value = "/partClassController")
public class PartClassController extends BaseController {
	@Autowired
	private IPartClassService iPartClassService;

	/**
	 * 查询元器件分类
	 */
	@RequestMapping(value = "/selectAllPartClass.do")
	public void selectAllPartClass(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list = iPartClassService.selectPartClass(map);
			String jsonString =JSON.toJSONString(list);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 添加元器件分类 **/
	@RequestMapping(value = "/insertOrUpdatePartClass.do")
	public void insertOrUpdatePartClass(HttpServletRequest request, HttpServletResponse response) {
		try {
			String lang="zh";
			if(null != request.getSession().getAttribute("lang")){
				lang = request.getSession().getAttribute("lang").toString();
			}
			String json = request.getParameter("json");
			String nodes=request.getParameter("nodes");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (json == null || json.equals("")) {
				resultMap.put("isSuc", "0");
				resultMap.put("message", lang.equals("zh")?"数据为空！":"Data is empty");
				outputMsg(response, JSON.toJSONString(resultMap));
				return;
			}
			PartClass partClass = JSON.parseObject(request.getParameter("json"), PartClass.class);
			
			//  验证childNum 在更改后的父级是否重复
			String isCheckCNum=request.getParameter("isCheckCNum");
			if(isCheckCNum!=null&&isCheckCNum.equals("0")){
				Map<String,Object> paramMap=new HashMap<String, Object>();
				paramMap.put("parentNum", partClass.getParentNum());
				paramMap.put("childNum", partClass.getChildNum());
				if(iPartClassService.childNumIsRepeat(paramMap)>0){
					resultMap.put("isSuc", "0");
					resultMap.put("message",lang.equals("zh")?"第二级重复":"Second level repetition");
					outputMsg(response, JSON.toJSONString(resultMap));
					return;
				}
			}
			// childnum 不能为空 
			if(StringUtils.isEmpty(partClass.getChildNum())){
				resultMap.put("isSuc", "0");
				resultMap.put("message", lang.equals("zh")?"第二级不能为空":"Second level can not be empty");
				outputMsg(response, JSON.toJSONString(resultMap));
				return;
			}
			PartDefineAttributes pda = JSON.parseObject(request.getParameter("json"), PartDefineAttributes.class);
			long imgId=uploadPCImg(request);//上传图片
			partClass.setImgId(imgId);
			
			if (partClass.getId() != 0) {// 修改节点信息
				if (StringUtils.isNotEmpty(partClass.getParentNum())) {
					partClass.setPnCode(partClass.getParentNum() + partClass.getChildNum());
				} else {
					partClass.setPnCode(partClass.getChildNum());
				}
				if(partClass.getPnCode().substring(0, 2).equals("02")){//目录外
					partClass.setTempPartMark(true);
				}else{//目录内
					partClass.setTempPartMark(false);
				}
				iPartClassService.updatePartClass(partClass);
				if (iPartClassService.selectPartDefineAttributes((int)partClass.getId()) == 0) {
					pda.setClassId(partClass.getId());
					iPartClassService.insertPartDefineAttributes(pda);
				} else {
					pda.setClassId(partClass.getId());
					iPartClassService.updatePartDefineAttributes(pda);
				}
				if(nodes!=null&&!nodes.equals("")){//结构修改时
					List<Object> listMap=JSON.parseArray(nodes);
					changeNodeStructure((List<Map<String,Object>>)((Map<String,Object>)listMap.get(0)).get("children"),partClass.getPnCode());
				}
				resultMap.put("message", lang.equals("zh")?"修改成功！":"Amend the success");
			} else {//添加节点
				// 拼接pnCode parentNum+childNum
				if (StringUtils.isNotEmpty(partClass.getParentNum())) {
					partClass.setPnCode(partClass.getParentNum() + partClass.getChildNum());
				} else {
					partClass.setPnCode(partClass.getChildNum());
				}
				if(partClass.getPnCode().substring(0, 2).equals("02")){//目录外
					partClass.setTempPartMark(true);
				}else{//目录内
					partClass.setTempPartMark(false);
				}
				iPartClassService.insertPartClass(partClass);
				// 添加特殊属性值
				pda.setClassId(partClass.getId());
				iPartClassService.insertPartDefineAttributes(pda);
				resultMap.put("message", lang.equals("zh")?"添加成功！":"Add success");
			}
			outputMsg(response, JSON.toJSONString(resultMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long uploadPCImg(HttpServletRequest request) throws FileNotFoundException, IOException {
		HrUser hrUser=getUserInfo(request);
		if(hrUser==null){
			return 0;
		}
		//图片上传
		//从配置文件读取存放路径
		Properties prop = new Properties();
		InputStream in = new BufferedInputStream (new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("param.properties").getPath()));
		prop.load(in);    ///加载属性列表
		String path=prop.getProperty("upload_path");
		in.close();
		String imgUrl=request.getSession().getServletContext().getRealPath("/")+"uploadImg";
		File file = new File(imgUrl);
		if (!file.exists()){//文件夹不存在时创建文件夹,项目路径下
			file.mkdirs();
		}
		File file1 = new File(path);
		if (!file1.exists()){//文件夹不存在时创建文件夹，配置文件中路径下
			file1.mkdirs();
		}
//		String lock=request.getParameter("lock");
		MultipartHttpServletRequest mr=(MultipartHttpServletRequest) request;
		Iterator<String> i=mr.getFileNames();
		long imgId=0;
		while(i.hasNext()){
			FileImg fileImg=new FileImg();
			String name=i.next();
			MultipartFile f=mr.getFile(name);
			if(f.isEmpty()){//添加时不选图片时默认图片。修改时不选图片不改图片信息
//				if(lock.equals("2")){
//					return 0;
//				}
//				Map<String,String> map=new HashMap<String,String>();
//				map.put("imgName", "47abbfca91334243a8b73dc5964410af.PNG");
//				List<FileImg> list=iPartClassService.selectImgByName(map);
//				if(list.size()>0){
//					return list.get(0).getId();
//				}
				return 0;
			}
			String fileName=UUID.randomUUID().toString().replaceAll("-", "")+"."+"png";
			InputStream is = f.getInputStream();
			String img1=imgUrl + "/" + fileName;
		    String img2=path + "/" + fileName;
			FileOutputStream fos = new FileOutputStream(img1);//项目路径下
			FileOutputStream fs = new FileOutputStream(img2);//配置文件中路径下
			byte [] bytes=new byte[1024];
		    int len;
		    while((len=is.read(bytes))!=-1){
		    	fos.write(bytes, 0, len);
		    	fs.write(bytes, 0, len);
		    }
		    fos.flush();
		    fs.flush();
		    fos.close();
		    fs.close();
		    is.close();
//		    //压缩图片大小
//		    Thumbnails.of(img1).size(16,16).toFile(img1);
//		    Thumbnails.of(img1).size(16,16).toFile(img2);
			//将图片信息保存到数据库
			fileImg.setImgSname(f.getOriginalFilename());
			fileImg.setImgName(fileName);
			fileImg.setCreateTime(new Date());
			fileImg.setCreateUser(hrUser.getUserName());
			fileImg.setImgUrl("uploadImg"+File.separator+fileName);
			iPartClassService.insertFileImg(fileImg);
			imgId=fileImg.getId();
			
		}
		return imgId;
	}

	/** 删除元器件分类 **/
	@RequestMapping(value = "/deletePartClass.do")
	public void deletePartClass(HttpServletRequest request, HttpServletResponse response) {
		try {
			String nodes = request.getParameter("nodes");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (nodes == null || nodes.equals("")) {
				resultMap.put("message", "数据为空！");
				outputJson(response, JSON.toJSONString(resultMap));
				return;
			}
			List<Object> listMap=JSON.parseArray(nodes);
			deleteNodes((Map<String,Object>)listMap.get(0));
			resultMap.put("message", "删除成功！");
			outputJson(response, JSON.toJSONString(resultMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 获取不包含选中节点及子节点的pnCode **/
	@RequestMapping(value = "/getPnCode.do")
	public void getPnCode(HttpServletRequest request, HttpServletResponse response){
		try{
			String treeNode=request.getParameter("treeNode");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String,Object> paramMap=new HashMap<String,Object>();
			List<String> parentNumList=new ArrayList<String>();
			if (treeNode != null && !(treeNode.equals(""))) {//查询所有
				Map<String,Object> map=(Map<String, Object>) JSON.parse(treeNode);
				this.getParentNum(map,parentNumList);//获取parentNum
				paramMap.put("parentNumList", parentNumList);
			}
		List<Map<String,Object>> list=iPartClassService.getPnCode(paramMap);
		//转成数据list
		List<String> li=new ArrayList<String>();
		int len=list.size();
		for(int i=0;i<len;i++){
			Map<String,Object> m=list.get(i);
			li.add((String) m.get("pnCode"));
		}
		
		resultMap.put("message", "成功！");
		resultMap.put("pnCodeList", li);
		outputJson(response, JSON.toJSONString(resultMap));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取分类信息，在主数据页面生成分类树
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getPartTypeTree.do")
	public void getPartTypeTree(HttpServletRequest request, HttpServletResponse response){
		try{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String jsonData=request.getParameter("jsonData");
			Map<String,Object> dataMap=(Map<String, Object>) JSON.parse(jsonData);
			List<Map<String, Object>> list=iPartClassService.getNodeByPnCode(dataMap);
			resultMap.put("message", "成功！");
			resultMap.put("typeTreeList", list);
			outputJson(response, JSON.toJSONString(resultMap));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//递归删除节点
	public void deleteNodes(Map<String,Object> jsonMap){
		Integer id=(Integer) jsonMap.get("oid");
		iPartClassService.deletePartClass(Integer.valueOf(id));
		iPartClassService.deletePartDefineAttributes(Integer.valueOf(id));
		List<Map<String,Object>> listJson=(List<Map<String, Object>>) jsonMap.get("children");
		if(listJson==null||listJson.size()==0){
			return ;
		}
		for(int i=0;i<listJson.size();i++){
			Map<String,Object> node=listJson.get(i);
			deleteNodes(node);
		}
	}
	//递归更改节点结构
	public void changeNodeStructure(List<Map<String,Object>> nodeListMap,String parentNum){
		for(int i=0;i<nodeListMap.size();i++){
			Map<String,Object> map=nodeListMap.get(i);
			PartClass pc=new PartClass();
			pc.setId((Integer)map.get("oid"));
			pc.setParentNum(parentNum);
			pc.setChildNum((String)map.get("childNum"));
			pc.setPartType((String)map.get("name"));
			pc.setPnCode(parentNum+pc.getChildNum());
			
			iPartClassService.updatePartClass(pc);
			List<Map<String,Object>> list=(List<Map<String,Object>>)map.get("children");
			if(list==null||list.size()==0){
				if(i==nodeListMap.size()-1){
					return;
				}else{
					continue;
				}
			}
			changeNodeStructure(list,pc.getPnCode());
			
		}
	}
	//递归获取parentNum
	public void getParentNum(Map<String,Object> map,List<String> list){
		String parentNum=(String) map.get("pId");
		if(parentNum==null){
			list.add("");
		}else{
			list.add(parentNum);
		}
		List<Map<String,Object>> listJson=(List<Map<String, Object>>) map.get("children");
		if(listJson==null||listJson.size()==0){
			return ;
		}
		for(int i=0;i<listJson.size();i++){
			Map<String,Object> node=listJson.get(i);
			getParentNum(node,list);
		}
	}
	
	

}
