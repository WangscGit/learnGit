package com.cms_cloudy.component.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.cms_cloudy.component.service.IPartDataService;
import com.cms_cloudy.component.service.IPartPrimaryAttributesService;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.controller.ExportExcel;
import com.cms_cloudy.database.pojo.FileImg;
import com.cms_cloudy.database.pojo.PartTypeTree;
import com.cms_cloudy.database.service.IPartClassService;
import com.cms_cloudy.upload.controller.FileExportController;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.websocket.pojo.PushPartData;
import com.cms_cloudy.websocket.pojo.PushpartDetail;
import com.cms_cloudy.websocket.service.IPushPartDataService;

@Controller
@RequestMapping(value = "/partDataController")
public class PartDataController extends BaseController {
	@Autowired
	private IPartDataService iPartDataService;
	@Autowired
	private IPartPrimaryAttributesService partPrimaryAttributesService;
	@Autowired
	private IPartClassService iPartClassService;
	@Autowired
	private IPushPartDataService pushPartDataService;

	/**
	 * 根据查询条件获取partData
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectPartData.do")
	public void selectPartData(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jsonData = request.getParameter("jsonData");
			String collection = request.getParameter("collection");
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			Map<String, Object> dataMap = (Map<String, Object>) JSON.parse(jsonData);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(StringUtils.isEmpty(pageSize)){
				pageSize="20";
			}
			// 从session中获取当前用户userid 判断是否收藏
			HrUser user = getUserInfo(request);
			Long userId = 0l;
			if (user != null) {
				userId = user.getUserId();
			}
			if (dataMap == null) {
				dataMap = new HashMap<String, Object>();
			}
			if (StringUtils.isNotEmpty(collection)) {
				dataMap.put("collection", collection);
			}
			dataMap.put("userId", userId);
			dataMap.put("pageNo", pageNo);
			dataMap.put("pageSize", pageSize);
			Map<String, Object> resMap = iPartDataService.selectPartData(dataMap);
			resultMap.put("message", "成功！");
			resultMap.put("resultList", resMap.get("partDataList"));
			resultMap.put("count", resMap.get("count"));
			resultMap.put("manuList", resMap.get("manuList"));
			resultMap.put("keyComponentList", resMap.get("keyComponentList"));
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getFiledAndDataType.do")
	public void getFiledAndDataType(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<PartPrimaryAttributes> list = partPrimaryAttributesService
					.selectSeachField(new HashMap<String, Object>());
			String jsonString = JSON.toJSONString(list);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 修改partData */
	@RequestMapping(value = "/updatePartData.do")
	public void updatePartData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String jsonData = request.getParameter("jsonData");
			Map<String, Object> dataMap = (Map<String, Object>) JSON.parse(jsonData);
			HrUser user = getUserInfo(request);
			// 修改前，从数据库查出该器件基本信息
			Map<String, Object> updateDataBefore = new HashMap<String, Object>();
			if (null != dataMap && StringUtils.isNoneEmpty(dataMap.get("PartNumber").toString())) {
				updateDataBefore = partPrimaryAttributesService.selectPartDateByPartNumber(dataMap).get(0);
			}
			if ("2".equals(updateDataBefore.get("state").toString())) {
				resultMap.put("message", "fail");
				String msg = JSON.toJSONString(resultMap);
				outputJson(response, msg);
				return;
			}
			dataMap.put("state", "1");// 检出
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(new Date());
			dataMap.put("Modifier", user.getLoginName());
			dataMap.put("ModifyDate", dateString);
			iPartDataService.updatePartData(dataMap);
			// 修改后，向元器件修改历史记录表添加一条记录
			// if(null != updateDataBefore){
			// insertPartHistories(updateDataBefore);
			// }
			resultMap.put("message", "成功！");
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 元器件修改记录添加 Wangsc
	 * 
	 * @param map
	 */
	public void insertPartHistories(Map<String, Object> map) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// 获得最大版本号
		int versionId = partPrimaryAttributesService.getMaxVersionId(map.get("PartNumber").toString());
		map.put("VersionID", versionId);
		if (null != map.get("id").toString() || !"".equals(map.get("id").toString())) {
			map.remove("id");
		}
		map.put("Modifier", "admin");
		map.put("ModifyDate", sdf.format(new Date()));
		iPartDataService.insertPartHistories(map);
	}

	/** 删除partData */
	@RequestMapping(value = "/deletePartData.do")
	public void deletePartData(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jsonData = request.getParameter("ids");
			List<String> ids = (List<String>) JSON.parse(jsonData);
			iPartDataService.deletePartData(ids);
			// 推送表数据删除
			deletePushDetail(ids);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("message", "成功！");
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 添加partData */
	@RequestMapping(value = "/insertPartData.do")
	public void insertPartData(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jsonData = request.getParameter("jsonData");
			Map<String, Object> dataMap = (Map<String, Object>) JSON.parse(jsonData);
			HrUser user = getUserInfo(request);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(new Date());
			dataMap.put("state", "1");// 检出
			dataMap.put("Modifier", user.getLoginName());
			dataMap.put("ModifyDate", dateString);
			dataMap.put("Creator", user.getLoginName());
			dataMap.put("CreateDate", dateString);
			Long id = iPartDataService.insertPartData(dataMap);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("message", "成功！");
			resultMap.put("id", id);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 统计partData的partType数量 */
	@RequestMapping(value = "/countPartData.do")
	public void countAddPartData(HttpServletRequest request, HttpServletResponse response) {
		try {
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			String divId = request.getParameter("divId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startDate", startDateStr);
			paramMap.put("endDate", endDateStr);
			String name = "";
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<String> partTypeList = new ArrayList<String>();
			List<String> typeList=new ArrayList<String>();
			List<Long> numLsit=new ArrayList<Long>();
			//判断中英文
			String langType=(String) request.getSession().getAttribute("lang");
			if(StringUtils.isEmpty(langType)){
				langType="zh";
			}
			langType=langType.trim();
			// 呆滞料统计
			if (divId.equals("div1")) {
				long s = iPartDataService.countAllPartData(new HashMap<String, Object>());
				long n = iPartDataService.countProductPnByTime(paramMap);
				partTypeList.add((langType.equals("zh")?"呆滞料":"slowMoving"));
				partTypeList.add((langType.equals("zh")?"正常料":"normalMaterial"));
				Map<String, Object> zmap = new HashMap<String, Object>();
				zmap.put("name", (langType.equals("zh")?"正常料":"normalMaterial"));
				zmap.put("value", n);
				list.add(zmap);
				Map<String, Object> dmap = new HashMap<String, Object>();
				dmap.put("name",(langType.equals("zh")?"呆滞料":"slowMoving"));
				dmap.put("value", s - n);
				list.add(dmap);
				name = (langType.equals("zh")?"呆滞料":"slowMoving Count");
			}

			// 元器件录入统计
			if (divId.equals("div2")) {
				List<PartTypeTree> partTypeTreeList = iPartClassService.getNodeByCoun(paramMap);
				int len = partTypeTreeList.size();
				for (int i = 0; i < len; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					partTypeList.add(partTypeTreeList.get(i).getPartType());
					if (partTypeTreeList.get(i).getPartList() == null
							|| partTypeTreeList.get(i).getPartList().size() == 0) {
						continue;
					} // 没有子节点的暂时不显示
					typeList.add(partTypeTreeList.get(i).getPartType());
					numLsit.add(partTypeTreeList.get(i).getCoun());
//					map.put("name", partTypeTreeList.get(i).getPartType());
//					map.put("value", partTypeTreeList.get(i).getCoun());
					list.add(map);
				}
				name = (langType.equals("zh")?"元器件录入统计":"ComponentEntry Count");
			}

			// 元器件选用统计
			if (divId.equals("div3")) {
				List<PartTypeTree> treelist = iPartDataService.selectPtByProSelTime(paramMap);
				List<PartTypeTree> pList = iPartClassService.getPartTreeListCoun(treelist);
				int len = pList.size();
				for (int i = 0; i < len; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					partTypeList.add(pList.get(i).getPartType());
					if (pList.get(i).getPartList() == null || pList.get(i).getPartList().size() == 0) {
						continue;
					} // 没有子节点的暂时不显示
					typeList.add(pList.get(i).getPartType());
					numLsit.add(pList.get(i).getCoun());
//					map.put("name", pList.get(i).getPartType());
//					map.put("value", pList.get(i).getCoun());
					list.add(map);
				}
				name = (langType.equals("zh")?"元器件选用统计":"omponentsSelection Count");
			}

			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("message", "成功！");
			resultMap.put("dataList", list);
			resultMap.put("partTypeList", partTypeList);
			resultMap.put("typeList", typeList);
			resultMap.put("numLsit", numLsit);
			resultMap.put("name", name);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 统计productPn数量 */
	@RequestMapping(value = "/countProductPn.do")
	public void countProductPn(HttpServletRequest request, HttpServletResponse response) {
		try {
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			String gm = request.getParameter("gm");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startDate", startDateStr);
			paramMap.put("endDate", endDateStr);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> typeList = new ArrayList<String>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> zmap = new HashMap<String, Object>();
			Map<String, Object> dmap = new HashMap<String, Object>();
			long coun = iPartDataService.countProductPnByTime(paramMap);// 总数
			long gc = 0;
			//判断中英文
			String langType=(String) request.getSession().getAttribute("lang");
			if(StringUtils.isEmpty(langType)){
				langType="zh";
			}
			if (gm.equals("g")) {
				gc = iPartDataService.countPpCountry(paramMap);// 国内数量
				typeList.add(langType.equals("zh")?"国产料":"Hom");
				typeList.add(langType.equals("zh")?"进口料":"Imp");
				zmap.put("name", langType.equals("zh")?"国产料":"Hom");
				zmap.put("value", gc);
				list.add(zmap);
				dmap.put("name",langType.equals("zh")?"进口料":"Imp");
				dmap.put("value", coun - gc);
				list.add(dmap);
				resultMap.put("name", langType.equals("zh")?"国产化统计分析":"StatisticalAnalysis");
			} else {
				gc = iPartDataService.countPptempPartMark(paramMap);// 目录内数量
				typeList.add(langType.equals("zh")?"优选目录":"In");
				typeList.add(langType.equals("zh")?"临时目录":"Out");
				zmap.put("name", langType.equals("zh")?"优选目录":"In");
				zmap.put("value", gc);
				list.add(zmap);
				dmap.put("name",langType.equals("zh")?"临时目录":"Out");
				dmap.put("value", coun - gc);
				list.add(dmap);
				resultMap.put("name", langType.equals("zh")?"超目录统计分析":"StatisticalAnalysis");
			}
			resultMap.put("message", "成功！");
			resultMap.put("dataList", list);
			resultMap.put("typeList", typeList);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * partData导出
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isUsed", true);
			List<PartPrimaryAttributes> lists = partPrimaryAttributesService.selectFieldAndName(map);
			String[] showName = new String[lists.size()];
			String[] fieldName = new String[lists.size()];
			int i = 0;
			if (lists.size() > 0) {
				for (PartPrimaryAttributes attr : lists) {
					if (StringUtils.isNoneEmpty(attr.getShowName())) {
						showName[i] = attr.getShowName();
					}
					if (StringUtils.isNoneEmpty(attr.getFieldName())) {
						fieldName[i] = attr.getFieldName();
					}
					i++;
				}
			}
		    List<String> idList = JSON.parseArray(ids, String.class);
		    Map<String, Object> queryMap = new HashMap<String,Object>();
		    queryMap.put("idList", idList);
			Map<String, Object> list = partPrimaryAttributesService.selectPartDatasAllForRedis(queryMap);
			HSSFWorkbook wb = (new ExportExcel()).exportExcel(list, showName, fieldName);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=\"" + new String("器件信息".getBytes("gb2312"), "ISO8859-1") + ".xls" + "\"");
			OutputStream ouputStream;
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 表头展示字段
	 */
	@RequestMapping(value = "/selectTalbeField.do")
	public void selectTalbeField(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> showNameList = partPrimaryAttributesService.selectTableField();
			resultMap.put("showNameList", showNameList);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 足迹记录
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/insertSearch.do")
	public void insertSearch(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapDetail = new HashMap<String, Object>();
		String searchData = request.getParameter("searchData");
		HrUser user = getUserInfo(request);
		List<Map<String, Object>> list = iPartDataService.selectPartDataBySearchInp(searchData);
		if (null != list && list.size() > 0) {
			map.put("inputContent", searchData);
			if (null != user) {
				map.put("userId", user.getUserId());
			} else {
				map.put("noUser", 0);
			}
			map.put("type", "0");
			List<PushPartData> pustList = pushPartDataService.selectPushPartData(map);
			if (null == pustList || pustList.size() == 0) {
				PushPartData data = new PushPartData();
				data.setInputContent(searchData);
				data.setType('0');
				data.setTimes(1);
				if (null != user) {
					data.setUserId(user.getUserId());
				}
				pushPartDataService.insertPushPartData(data);
				// 推送从表数据维护------用于模糊查询数据的级联删除
				mapDetail.put("input", searchData);
				List<PushpartDetail> listDetail = pushPartDataService.selectPushPartDetail(mapDetail);
				if (null == listDetail || listDetail.size() == 0) {
					for (int x = 0; x < list.size(); x++) {
						PushpartDetail detail = new PushpartDetail();
						detail.setInputContent(searchData);
						detail.setPartId(Long.valueOf(list.get(x).get("id").toString()));
						pushPartDataService.insertPushPartDetail(detail);
					}
				}
			} else {
				PushPartData data = pustList.get(0);
				data.setTimes(data.getTimes() + 1);
				if (null != user) {
					data.setUserId(user.getUserId());
				}
				pushPartDataService.updatePushPartDatar(data);
			}
		}
	}

	public void deletePushDetail(List<String> ids) {
		for (String id : ids) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> pushDetail = new HashMap<String, Object>();
			Map<String, Object> mapMain = new HashMap<String, Object>();
			pushDetail.put("part_id", id);
			// 推送主表删除
			pushPartDataService.deletePushPartDataByPartId(Integer.valueOf(id));
			// 根据元器件ID查询推送从表信息
			List<PushpartDetail> detailList = pushPartDataService.selectPushPartDetail(pushDetail);
			if (null != detailList && detailList.size() > 0) {
				for (PushpartDetail partId : detailList) {
					// 删除从表元器件信息
					pushPartDataService.deletePushPartDetail(Integer.valueOf(String.valueOf(partId.getId())));
					map.put("input", partId.getInputContent());
					// 查询从表内是否还有该类型数据
					List<PushpartDetail> detailList2 = pushPartDataService.selectPushPartDetail(map);
					if (null == detailList2 || detailList2.size() == 0) {// 如果没有删除推送主表信息
						mapMain.put("inputContent", partId.getInputContent());
						mapMain.put("type", "0");
						List<PushPartData> pushMain = pushPartDataService.selectPushPartData(mapMain);
						for (PushPartData main : pushMain) {
							pushPartDataService.deletePushPartData(Integer.valueOf(String.valueOf(main.getId())));
						}
					}
				}
			}
		}
	}

	/**
	 * 通过页面的方式添加partData
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertPartDataFromPage.do")
	public void insertPartDataFromPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			String lock = request.getParameter("lock");
			String jsonData = request.getParameter("jsonData");
			Map<String, Object> dataMap = (Map<String, Object>) JSON.parse(jsonData);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			HrUser user = getUserInfo(request);
			if(user==null){
				resultMap.put("message", "2");
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
				return;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(new Date());
			Map<String,String>imgMap=uploadPCImg(request);//保存图片，获取路径
			dataMap.putAll(imgMap);
			if (lock.equals("1")) {//添加
				dataMap.put("state", "1");// 检出
				dataMap.put("Modifier", user.getLoginName());
				dataMap.put("ModifyDate", dateString);
				dataMap.put("Creator", user.getLoginName());
				dataMap.put("CreateDate", dateString);
				dataMap.put("PartNumber", FileExportController.getUUID());
				Long id = iPartDataService.insertPartData(dataMap);
				if(id==0l){
					resultMap.put("message", "0");
					String jsonString = JSON.toJSONString(resultMap);
					outputMsg(response, jsonString);
					return;
				}
				resultMap.put("message", "1");
				resultMap.put("id", id);
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
			} else {
				// 修改前，从数据库查出该器件基本信息
				Map<String, Object> updateDataBefore = new HashMap<String, Object>();
				if (null != dataMap && StringUtils.isNotEmpty(dataMap.get("id").toString())) {
					updateDataBefore = partPrimaryAttributesService.selectPartDateById(dataMap).get(0);
				}
				if(!updateDataBefore.get("PartCode").equals(dataMap.get("PartCode"))){
					long l=iPartDataService.checkPartCode((String) dataMap.get("PartCode"));
					if(l!=0l){
						resultMap.put("message", "0");
						String jsonString = JSON.toJSONString(resultMap);
						outputMsg(response, jsonString);
						return;
					}
				}
//				if ("2".equals(updateDataBefore.get("state").toString())) {
//					resultMap.put("message", "fail");
//					String msg = JSON.toJSONString(resultMap);
//					outputJson(response, msg);
//					return;
//				}
				dataMap.put("state", "1");// 检出
				dataMap.put("Modifier", user.getLoginName());
				dataMap.put("ModifyDate", dateString);
				iPartDataService.updatePartData(dataMap);
				//修改后，向元器件修改历史记录表添加一条记录
				 if(null != updateDataBefore){
					 insertPartHistories(updateDataBefore);
			     }
				resultMap.put("message", "1");
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 上传图片
	public Map<String,String> uploadPCImg(HttpServletRequest request) throws FileNotFoundException, IOException {
		HrUser hrUser = getUserInfo(request);
		if (hrUser == null) {
			return null;
		}
		// 图片上传
		// 从配置文件读取存放路径
		Properties prop = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(
				Thread.currentThread().getContextClassLoader().getResource("param.properties").getPath()));
		prop.load(in); /// 加载属性列表
		String path = prop.getProperty("upload_path");
		in.close();
		String imgUrl = request.getSession().getServletContext().getRealPath("/") + "uploadImg";
		File file = new File(imgUrl);
		if (!file.exists()) {// 文件夹不存在时创建文件夹,项目路径下
			file.mkdirs();
		}
		File file1 = new File(path);
		if (!file1.exists()) {// 文件夹不存在时创建文件夹，配置文件中路径下
			file1.mkdirs();
		}
		String lock = request.getParameter("lock");
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
		Iterator<String> i = mr.getFileNames();
		Map<String,String> urlMap=new HashMap<String,String>();
		while (i.hasNext()) {
			FileImg fileImg = new FileImg();
			String name = i.next();
			MultipartFile f = mr.getFile(name);
			if (f.isEmpty()) {// 添加时不选图片时默认图片。修改时不选图片不改图片信息
				if (lock.equals("2")) {
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("imgName", "47abbfca91334243a8b73dc5964410af.PNG");
				List<FileImg> list = iPartClassService.selectImgByName(map);
				if (list.size() > 0) {
					urlMap.put(name.substring(0, name.length()-1), list.get(0).getImgUrl());
					continue;
				}
				continue;
			}
			String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "."
					+ (f.getOriginalFilename().split("\\."))[1];
			InputStream is = f.getInputStream();
			FileOutputStream fos = new FileOutputStream(imgUrl + "/" + fileName);// 项目路径下
			FileOutputStream fs = new FileOutputStream(path + "/" + fileName);// 配置文件中路径下
			byte[] bytes = new byte[1024];
			int len;
			while ((len = is.read(bytes)) != -1) {
				fos.write(bytes, 0, len);
				fs.write(bytes, 0, len);
			}
			fos.flush();
			fs.flush();
			fos.close();
			fs.close();
			is.close();
			// 将图片信息保存到数据库
			fileImg.setImgSname(f.getOriginalFilename());
			fileImg.setImgName(fileName);
			fileImg.setCreateTime(new Date());
			fileImg.setCreateUser(hrUser.getUserName());
			fileImg.setImgUrl("uploadImg" + File.separator + fileName);
			iPartClassService.insertFileImg(fileImg);
			urlMap.put(name.substring(0, name.length()-1), fileImg.getImgUrl());

		}
		return urlMap;
	}
}
