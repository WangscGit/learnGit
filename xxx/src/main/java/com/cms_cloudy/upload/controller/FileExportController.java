package com.cms_cloudy.upload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.cms_cloudy.component.service.IPartPrimaryAttributesService;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.controller.ExportExcel;
import com.cms_cloudy.controller.FileController;
import com.cms_cloudy.database.service.IPartClassService;
import com.cms_cloudy.upload.service.IExportExcelService;
import com.cms_cloudy.user.controller.UserController;
import com.cms_cloudy.user.pojo.HrUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/fileExportController")
public class FileExportController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private IExportExcelService exportExcelService;
	@Autowired
	private IPartPrimaryAttributesService partPrimaryAttributesService;
	@Autowired
	private IPartClassService partClassService;

	/**
	 * 将导入文件存到临时文件夹
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportFile.do")
	public void exportFile(HttpServletRequest request, HttpServletResponse response) {
		FileController files = new FileController();
		String fileType = "";
		List<Map<String, Object>> listObj = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		MultipartHttpServletRequest mr;
		try {
			String dir = getAbsoluteBasePath(request) + "partExport" + File.separator + System.currentTimeMillis();
			if (request instanceof MultipartHttpServletRequest) {
				mr = (MultipartHttpServletRequest) request;
				Map<String, Object> map = files.getPartFileElement(mr, dir);// 将文件存到临时目录
				String path = dir + File.separator + map.get("fileName").toString();
				Workbook workbook = null;
				if (map.get("fileName").toString().endsWith("xls")) {
					workbook = new HSSFWorkbook(new FileInputStream(new File(path)));
					fileType = "xls";
				} else if (map.get("fileName").toString().endsWith("xlsx")) {
					workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
					fileType = "xlsx";
				} else {
					String msg = "请选择Excel文件！";
					mapJson.put("result", msg);
					String jsonMsg = JSON.toJSONString(mapJson);
					outputMsg(response, jsonMsg);
					return;
				}
				Sheet sheet = null;
				Set<String> sett = new HashSet<String>();
				for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
					if (i == 1) {
						break;
					}
					// ExportExcel ex = new ExportExcel();
					Map<String, Object> mapData = new HashMap<String, Object>();
					String fileName = "";
					sheet = workbook.getSheetAt(i);
					Row row = sheet.getRow(0);
					if (null == row) {
						String msg = "数据错误，第" + (i + 1) + "个工作表内第一行为字段展示名，不能为空！";
						mapJson.put("rowNoEmpty", msg);
						String jsonMsg = JSON.toJSONString(mapJson);
						outputMsg(response, jsonMsg);
						return;
					}
					int j = 0;
					NumberFormat nf = NumberFormat.getInstance();
					for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {// 获取每个单元格
						if (row.getCell(k).getCellType() == row.getCell(k).CELL_TYPE_NUMERIC) {
							fileName += nf.format(row.getCell(k).getNumericCellValue()) + ",";
						} else {
							fileName += row.getCell(k).toString() + ",";
						}
						String partNumber = row.getCell(k).toString();
						if (!"".equals(partNumber) && partNumber.equals("元器件编码")) {
							j++;
							String[] result = checkPartNumber(sheet, k, sett);
							if (null != result && result.length > 0) {
								String msg = "数据错误，第" + (i + 1) + "个工作表第" + result[1] + "行的元器件编码【" + result[0]
										+ "】在EXCEL表格中有重复!";
								// mapJson.put("result", msg);
								// String jsonMsg = JSON.toJSONString(mapJson);
								// outputMsg(response, jsonMsg);
								// return;
							}
						}
					}
					if (j == 0) {
						String msg = "数据错误，第" + (i + 1) + "个工作表内没有元器件编码字段!";
						// mapJson.put("noPart", msg);
						// String jsonMsg = JSON.toJSONString(mapJson);
						// outputMsg(response, jsonMsg);
						// return;
					}
					fileName = fileName.substring(0, fileName.length() - 1);// 去掉最后一个
					////////////////////////////////////
					Map<String, Object> mapSql = new HashMap<String, Object>();
					mapSql.put("isUsed", true);
					mapSql.put("partExport", "export");
					String showName = "";
					String queryField = "";
					List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFieldAndName(mapSql);
					if (list.size() > 0) {
						for (PartPrimaryAttributes attr : list) {
							if (StringUtils.isNoneEmpty(attr.getShowName())) {
								showName += attr.getShowName() + ",";
							}
							if (StringUtils.isNoneEmpty(attr.getFieldName())) {
								queryField += attr.getFieldName() + ",";
							}
						}
					}
					queryField = queryField.substring(0, queryField.length() - 1);
					showName = showName.substring(0, showName.length() - 1);
					////////////////////////////////////
					mapData.put("sheetName", sheet.getSheetName());
					mapData.put("fieldName", fileName);
					mapData.put("selectName", showName);
					mapData.put("selecrValue", queryField);
					mapData.put("path", path);
					mapData.put("fileType", fileType);
					listObj.add(mapData);
				}
				String msg = JSONArray.fromObject(listObj).toString();
				outputMsg(response, msg);
			} else {
				String msg = JSONArray.fromObject(null).toString();
				outputMsg(response, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得项目的根路径
	 * 
	 * @param request
	 * @return
	 */
	public String getAbsoluteBasePath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		return uuid.replaceAll("-", "");
	}

	/**
	 * 下载用户手册和exe
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/download.do")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		String type = request.getParameter("type");
		String lang = "zh";
		if (null != request.getSession().getAttribute("lang")) {
			lang = request.getSession().getAttribute("lang").toString();
		}
		String fname = data;
		String msg = "";
		if ("1".equals(type)) {
			if ("zh".equals(lang)) {
				msg = "用户手册不存在,请联系管理员！";
			} else {
				msg = "Manual not found !";
			}
		} else {
			if ("zh".equals(lang)) {
				msg = "客户端不存在,请联系管理员！";
			} else {
				msg = "Client not found !";
			}
		}
		try {
			// fname = new String(fname.getBytes("iso8859-1"), "utf-8");
			String path = getAbsoluteBasePath(request) + "WEB-INF" + File.separator + "download" + File.separator
					+ fname;
			File file = new File(path);
			if (!file.exists()) {
				outputMsg(response, "<script>alert('" + msg + "');window.history.go(-1);</script>");
				logger.error(msg);
				return;
			}
			response.setHeader("Location", fname);
			response.setHeader("Cache-Control", "max-age=");
			response.setHeader("Content-disposition",
					"attachment;filename=\"" + new String(fname.getBytes("gb2312"), "ISO8859-1") + "\"");
			response.setContentLength((int) file.length());
			OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			outputStream = null;
		} catch (Exception e) {
			logger.error("下载参数文件失败！", e);
			try {
				outputMsg(response, "<script>alert('下载文件失败，请联系管理员！');window.history.go(-1);</script>");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 保存元器件导入数据
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/saveExportForPartdata.do")
	public void saveExportForPartdata(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String exportList = request.getParameter("exportList");
		String exportPath = request.getParameter("exportPath");
		String exportFileType = request.getParameter("exportFileType");
		String type = request.getParameter("type");// 1:保存导入 2:更新导入
		List<Map> obj = JSON.parseArray(exportList, Map.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// excel数据存储
		List<String> pcList = new ArrayList<String>();// partCode数据存储
		String showName = "";// 用于生成错误报告(excel列名)
		String Fields = "";// 用于生成错误报告(excel字段名)
		List<String> EXFieldName = new ArrayList<String>();// 用于生成错误报告(excel字段名)
		List<Map<String, Object>> EXValue = new ArrayList<Map<String, Object>>();// 用于生成错误报告(excel内容)
		int partNumber = -1;
		int state = -1;
		int partType = -1;
		// int TempPartMark = -1;
		// int DirInOROut = -1;
		int Creator = -1;
		int CreateDate = -1;
		int Modifier = -1;
		int ModifyDate = -1;
		int PartCode = -1;
		File f = new File(exportPath);
		InputStream inputStream;
		Workbook xssfWorkbook = null;
		try {
			inputStream = new FileInputStream(f);
			if (exportFileType.equals("xls")) {
				xssfWorkbook = new HSSFWorkbook(inputStream);
			} else {
				xssfWorkbook = new XSSFWorkbook(inputStream);
			}
			int xx = 0;
			for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
				if (i == 1) {
					break;
				}
				Sheet sheet1 = xssfWorkbook.getSheetAt(i);
				Row rows = sheet1.getRow(0);
				for (int k = 0; k < rows.getPhysicalNumberOfCells(); k++) {// 获取每个单元格
					if (null != obj.get(xx).get("value") && !obj.get(xx).get("value").toString().equals("")) {
						showName+=rows.getCell(k).getStringCellValue()+",";
						Fields += obj.get(xx).get("value").toString()+",";
						EXFieldName.add(obj.get(xx).get("value").toString());
						rows.getCell(k).setCellValue(obj.get(xx).get("value").toString());
						sheet1.setColumnHidden((short) k, false);
					} else {
						sheet1.setColumnHidden((short) k, true);// 隐藏没有匹配字段的列
					}
					xx++;
				}
			}
			//////////////////////////////// 字段名拼接////////////////////////////////////
			for (int sxx = 0; sxx < xssfWorkbook.getNumberOfSheets(); sxx++) {// 获取每个Sheet表
				if (sxx == 1) {
					break;
				}
				List<Object> nums = new ArrayList<Object>();// 存放没有被隐藏的下标
				Sheet sheet = xssfWorkbook.getSheetAt(sxx);
				Row rows = sheet.getRow(0);
				List<Object> fieldName = new ArrayList<Object>();
				List<String> filterField = new ArrayList<String>();
				for (int cc = 0; cc < rows.getPhysicalNumberOfCells(); cc++) {// 获取每个单元格
					if (!sheet.isColumnHidden(cc) && null != obj.get(cc).get("value") && !obj.get(cc).get("value").toString().equals("")) {
						fieldName.add(rows.getCell(cc));// 将选择的列放到最前面
						filterField.add(rows.getCell(cc).toString());// 将选择的列放到最前面
						nums.add(cc);// 存放下标
					}
				}
				Map<String, Object> map = getAllField(filterField);// 获得所有字段值
				String fieldAll = map.get("queryField").toString();
				String[] fieldList = fieldAll.split(",");
				// 填充隐藏列的数据库字段
				for (int a = 0; a < fieldList.length; a++) {
					fieldName.add(fieldList[a]);
				}
				// 去除以选择的列
				for (int j = 0; j < fieldName.size(); j++) {
					// 获得partnumber的下标
					if ("PartNumber".equals(fieldName.get(j).toString())) {
						partNumber = j;
					}
					if ("PartCode".equals(fieldName.get(j).toString())) {
						PartCode = j;
					}
					if ("Modifier".equals(fieldName.get(j).toString())) {
						Modifier = j;
					}
					if ("ModifyDate".equals(fieldName.get(j).toString())) {
						ModifyDate = j;
					}
					if ("state".equals(fieldName.get(j).toString())) {
						state = j;
					}
					if ("Part_Type".equals(fieldName.get(j).toString())) {
						partType = j;
					}
					// if("TempPartMark".equals(fieldName.get(j).toString())){
					// TempPartMark = j;
					// }
					// if("DirInOROut".equals(fieldName.get(j).toString())){
					// DirInOROut = j;
					// }
					if ("Creator".equals(fieldName.get(j).toString())) {
						Creator = j;
					}
					if ("CreateDate".equals(fieldName.get(j).toString())) {
						CreateDate = j;
					}
				}
				//////////////////////////////// 字段名拼接////////////////////////////////////
				//////////////////////////////// value拼接//////////////////////////////////////
				int line = 0;
				line = getValueOfFormulaCell(sheet, line);
				for (int xs = 1; xs < line; xs++) {
					Row row = sheet.getRow(xs);
					List<Object> value = new ArrayList<Object>();// 存放value值
					Map<String, Object> EXMap = new HashMap<String, Object>();
					for (int number = 0; number < nums.size(); number++) {
						String values = null == row.getCell(Integer.valueOf(nums.get(number).toString()))?"":row.getCell(Integer.valueOf(nums.get(number).toString())).toString();
						EXMap.put(EXFieldName.get(number), values);
						EXValue.add(EXMap);// EXCEL部分值存储
						value.add(values);
					}
					for (int a = 0; a < fieldList.length; a++) {
						if (!fieldList[a].equals("")) {
							value.add("");
						}
					}
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("fieldName", fieldName);
					maps.put("fieldValue", value);
					if (partType == -1) {//
						value.set(partType, "noPart");
					} else {
						if (null == value.get(partType) || "".equals(value.get(partType))) {
							value.set(partType, "noPart");
						}
					}
					if (partNumber == -1) {
						value.set(partNumber, this.getUUID());
					} else {
						if (null == value.get(partNumber) || "".equals(value.get(partNumber))) {
							value.set(partNumber, this.getUUID());
						}
					}
					// 创建人/状态编辑
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dateString = formatter.format(new Date());
					HrUser user = getUserInfo(request);
					String loginName = "admin";
					if (null != user) {
						loginName = user.getLoginName();
					}
					value.set(Creator, loginName);
					value.set(CreateDate, dateString);
					value.set(Modifier, loginName);
					value.set(ModifyDate, dateString);
					value.set(state, "1");
					pcList.add(value.get(PartCode).toString());// partcode存储
					list.add(maps);
				}
			}
			List<String> repeatCode = new ArrayList<String>();
			List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
			for (int save = 0; save < list.size(); save++) {
				Map<String, Object> pcMap = list.get(save);
				pcMap.put("PartCode", pcList.get(save));
				List<String> thisRep = new ArrayList<String>();
				if ("1".equals(type)) {
					thisRep = exportExcelService.insertPartData(pcMap, repeatCode);// 保存导入
				} else {
					thisRep = exportExcelService.updatePartData(pcMap, repeatCode);// 更新导入
				}
				if (thisRep.size() > 0) {
					errorList.add(EXValue.get(save));
				}
			}
			inputStream.close();
			// 删除临时目录
			String delP = getAbsoluteBasePath(request) + "partExport";
			deletefileMulu(delP);
			if (errorList.size() > 0) {
				Map<String,Object> sessionMap = new HashMap<String,Object>();
				String sessionKey = this.getUUID();
				showName = showName.substring(0, showName.length()-1);
				Fields = Fields.substring(0, Fields.length()-1);
				jsonMap.put("result", "error");// 部分数据导入失败!
				jsonMap.put("sessionKey", sessionKey);
				sessionMap.put("showName", showName);
				sessionMap.put("list", errorList);
				sessionMap.put("fieldName", Fields);
				request.getSession().setAttribute(sessionKey, sessionMap);//数据量大不能导出
			} else {
				jsonMap.put("result", "success");// 导入成功!
			}
			String msg = JSONObject.fromObject(jsonMap).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存元器件导入数据
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/updateExportForPartdata.do")
	public void updateExportForPartdata(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String exportList = request.getParameter("exportList");
		String exportPath = request.getParameter("exportPath");
		String exportFileType = request.getParameter("exportFileType");
		String type = request.getParameter("type");// 1:保存导入 2:更新导入
		List<Map> obj = JSON.parseArray(exportList, Map.class);
		List<String> pcList = new ArrayList<String>();// partCode数据存储
		String showName = "";// 用于生成错误报告(excel列名)
		String Fields = "";// 用于生成错误报告(excel字段名)
		List<String> EXFieldName = new ArrayList<String>();// 用于生成错误报告(excel字段名)
		List<Map<String, Object>> EXValue = new ArrayList<Map<String, Object>>();// 用于生成错误报告(excel内容)
		int partType = -1;
		int PartCode = -1;
		File f = new File(exportPath);
		InputStream inputStream;
		Workbook xssfWorkbook = null;
		try {
			inputStream = new FileInputStream(f);
			if (exportFileType.equals("xls")) {
				xssfWorkbook = new HSSFWorkbook(inputStream);
			} else {
				xssfWorkbook = new XSSFWorkbook(inputStream);
			}
			int xx = 0;
			for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
				if (i == 1) {
					break;
				}
				Sheet sheet1 = xssfWorkbook.getSheetAt(i);
				Row rows = sheet1.getRow(0);
				for (int k = 0; k < rows.getPhysicalNumberOfCells(); k++) {// 获取每个单元格
					if (null != obj.get(xx).get("value") && !obj.get(xx).get("value").toString().equals("")) {
						showName+=rows.getCell(k).getStringCellValue()+",";
						Fields += obj.get(xx).get("value").toString()+",";
						EXFieldName.add(obj.get(xx).get("value").toString());
						rows.getCell(k).setCellValue(obj.get(xx).get("value").toString());
						sheet1.setColumnHidden((short) k, false);
					} else {
						sheet1.setColumnHidden((short) k, true);// 隐藏没有匹配字段的列
					}
					xx++;
				}
			}
			//////////////////////////////// 字段名拼接////////////////////////////////////
			for (int sxx = 0; sxx < xssfWorkbook.getNumberOfSheets(); sxx++) {// 获取每个Sheet表
				if (sxx == 1) {
					break;
				}
				List<Object> nums = new ArrayList<Object>();// 存放没有被隐藏的下标
				Sheet sheet = xssfWorkbook.getSheetAt(sxx);
				Row rows = sheet.getRow(0);
				List<Object> fieldName = new ArrayList<Object>();
				List<String> filterField = new ArrayList<String>();
				for (int cc = 0; cc < rows.getPhysicalNumberOfCells(); cc++) {// 获取每个单元格
					if (!sheet.isColumnHidden(cc) && null != obj.get(cc).get("value") && !obj.get(cc).get("value").toString().equals("")) {
						fieldName.add(rows.getCell(cc));// 将选择的列放到最前面
						filterField.add(rows.getCell(cc).toString());// 将选择的列放到最前面
						nums.add(cc);// 存放下标
					}
				}
				// 去除以选择的列
				for (int j = 0; j < fieldName.size(); j++) {
					if ("PartCode".equals(fieldName.get(j).toString())) {
						PartCode = j;
					}
					if ("Part_Type".equals(fieldName.get(j).toString())) {
						partType = j;
					}
				}
				//////////////////////////////// 字段名拼接////////////////////////////////////
				//////////////////////////////// value拼接//////////////////////////////////////
				int line = 0;
				line = getValueOfFormulaCell(sheet, line);
				for (int xs = 1; xs < line; xs++) {
					Row row = sheet.getRow(xs);
					Map<String, Object> EXMap = new HashMap<String, Object>();
					for (int number = 0; number < nums.size(); number++) {
						String values = null == row.getCell(Integer.valueOf(nums.get(number).toString()))?"":row.getCell(Integer.valueOf(nums.get(number).toString())).toString();
						EXMap.put(EXFieldName.get(number), values);
						if(partType == Integer.valueOf(nums.get(number).toString()) && "".equals(values)){
							EXMap.put(EXFieldName.get(number), "noPart");
						}
					}
					EXValue.add(EXMap);// EXCEL部分值存储
					pcList.add(EXMap.get("PartCode").toString());// partcode存储
				}
			}
			List<String> repeatCode = new ArrayList<String>();
			List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
			for (int save = 0; save < EXValue.size(); save++) {
				Map<String, Object> pcMap = EXValue.get(save);
				pcMap.put("PartCode", pcList.get(save));
				List<String> thisRep = new ArrayList<String>();
				thisRep = exportExcelService.updatePartData(pcMap, repeatCode);// 更新导入
				if (thisRep.size() > 0) {
					errorList.add(EXValue.get(save));
				}
			}
			inputStream.close();
			// 删除临时目录
			String delP = getAbsoluteBasePath(request) + "partExport";
			deletefileMulu(delP);
			if (errorList.size() > 0) {
				Map<String,Object> sessionMap = new HashMap<String,Object>();
				String sessionKey = this.getUUID();
				showName = showName.substring(0, showName.length()-1);
				Fields = Fields.substring(0, Fields.length()-1);
				jsonMap.put("result", "error");// 部分数据导入失败!
				jsonMap.put("sessionKey", sessionKey);
				sessionMap.put("showName", showName);
				sessionMap.put("list", errorList);
				sessionMap.put("fieldName", Fields);
				request.getSession().setAttribute(sessionKey, sessionMap);//数据量大不能导出
			} else {
				jsonMap.put("result", "updateSuccess");// 导入成功!
			}
			String msg = JSONObject.fromObject(jsonMap).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Map<String, Object> getAllField(List<String> filterField) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		map.put("isUsed", true);
		map.put("filterField", filterField);
		String queryField = "";
		List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFieldAndName(map);
		if (list.size() > 0) {
			for (PartPrimaryAttributes attr : list) {
				if (StringUtils.isNoneEmpty(attr.getFieldName())) {
					queryField += attr.getFieldName() + ",";
				}
			}
		}
		mapResult.put("queryField", queryField);
		return mapResult;
	}

	// 删除目录
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
	 * excel表实际行数
	 * 
	 * @param sheet
	 * @param flag
	 * @return
	 * @throws IOException
	 */
	public static int getValueOfFormulaCell(Sheet sheet, int... flag) throws IOException {
		int row_real = 0;
		int rows = sheet.getPhysicalNumberOfRows();// 此处物理行数统计有错误,
		int size = flag.length;
		try {

			for (int i = 0; i < rows; i++) {
				Row row = sheet.getRow(i);
				int total = 0;
				ArrayList<Integer> blank = new ArrayList<Integer>();
				int type = -1;
				String s = null;
				for (int j : flag) {
					if (!(row.getCell(j) == null) && row.getCell(j).getCellType() < 2) {
						type = row.getCell(j).getCellType();
						row.getCell(j).setCellType(1);
					}

					if (row.getCell(j) == null || row.getCell(j).getStringCellValue().matches("^\\s+$")
							|| row.getCell(j).getCellType() > 2) {
						total++;

						if (!(row.getCell(j) == null) && row.getCell(j).getCellType() < 2) {
							row.getCell(j).setCellType(type);
						}
						blank.add(j);

					}
				}
				System.out.println(s + "我");
				// 如果4列都是空说明就该返回
				if (total == flag.length) {

					return row_real;
				} else if (total == 0) {
					row_real++;

				} else {
					String h = "";
					for (Integer b : blank) {

						h = h + "第" + (b + 1) + "列" + " ";
					}
					throw new Exception("第" + (i + 1) + "行" + h + "不能为空");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row_real;
	}

	/**
	 * 检查Excel内的物料编码是否重复
	 * 
	 * @param sheet
	 * @param
	 */
	public String[] checkPartNumber(Sheet sheet, int index, Set<String> sett) {
		int rows = 0;
		String[] str = new String[2];
		try {
			rows = getValueOfFormulaCell(sheet, rows);
			for (int i = 0; i < rows; i++) {
				if (i == 0) {
					continue;
				}
				if (!sett.add(sheet.getRow(i).getCell(index).toString())) {
					str[0] = sheet.getRow(i).getCell(index).toString();
					str[1] = (i + 1) + "";
					return str;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用户导入模板下载
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/downloadUserTemplation.do")
	public void downloadUserTemplation(HttpServletRequest request, HttpServletResponse response) {
		String lang = "zh";
		if (null != request.getSession().getAttribute("lang")) {
			lang = request.getSession().getAttribute("lang").toString();
		}
		String msg = "";
		if ("zh".equals(lang)) {
			msg = "模板不存在,请联系管理员！";
		} else {
			msg = "Template not found !";
		}
		try {
			String path = getAbsoluteBasePath(request) + "WEB-INF" + File.separator + "download" + File.separator
					+ "user" + File.separator + "用户导入模板.xls";
			File file = new File(path);
			if (!file.exists()) {
				outputMsg(response, "<script>alert('" + msg + "');window.history.go(-1);</script>");
				logger.error(msg);
				return;
			}
			response.setHeader("Location", "用户导入模板.xls");
			response.setHeader("Cache-Control", "max-age=");
			response.setHeader("Content-disposition",
					"attachment;filename=\"" + new String("用户导入模板.xls".getBytes("gb2312"), "ISO8859-1") + "\"");
			response.setContentLength((int) file.length());
			OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			outputStream = null;
		} catch (Exception e) {
			logger.error("下载模板失败！", e);
			try {
				outputMsg(response, "<script>alert('下载模板失败，请联系管理员！');window.history.go(-1);</script>");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 保存元器件导入数据
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	@RequestMapping(value = "/saveExportForPartdataBaker.do")
	public void saveExportForPartdataBaker(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String exportList = request.getParameter("exportList");
		String exportPath = request.getParameter("exportPath");
		String exportFileType = request.getParameter("exportFileType");
		List<Map> obj = JSON.parseArray(exportList, Map.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> pcList = new ArrayList<Map<String, Object>>();// 分类信息存储
		int partNumber = -1;
		int state = -1;
		int partType = -1;
		int TempPartMark = -1;
		int DirInOROut = -1;
		int Creator = -1;
		int CreateDate = -1;
		int firstCategory = -1;
		int secondCategory = -1;
		int thirdCategory = -1;
		int fourthCategory = -1;
		int fifthCategory = -1;
		int Modifier = -1;
		int ModifyDate = -1;
		File f = new File(exportPath);
		InputStream inputStream;
		Workbook xssfWorkbook = null;
		try {
			inputStream = new FileInputStream(f);
			if (exportFileType.equals("xls")) {
				xssfWorkbook = new HSSFWorkbook(inputStream);
			} else {
				xssfWorkbook = new XSSFWorkbook(inputStream);
			}
			int xx = 0;
			for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
				Sheet sheet1 = xssfWorkbook.getSheetAt(i);
				Row rows = sheet1.getRow(0);
				for (int k = 0; k < rows.getPhysicalNumberOfCells(); k++) {// 获取每个单元格
					if (null != obj.get(xx).get("value") && !obj.get(xx).get("value").toString().equals("")) {
						rows.getCell(k).setCellValue(obj.get(xx).get("value").toString());
						sheet1.setColumnHidden((short) k, false);
					} else {
						sheet1.setColumnHidden((short) k, true);// 隐藏没有匹配字段的列
					}
					xx++;
				}
			}
			//////////////////////////////// 字段名拼接////////////////////////////////////
			for (int sxx = 0; sxx < xssfWorkbook.getNumberOfSheets(); sxx++) {// 获取每个Sheet表
				List<Object> nums = new ArrayList<Object>();// 存放没有被隐藏的下标
				Sheet sheet = xssfWorkbook.getSheetAt(sxx);
				Row rows = sheet.getRow(0);
				List<Object> fieldName = new ArrayList<Object>();
				List<String> filterField = new ArrayList<String>();
				for (int cc = 0; cc < rows.getPhysicalNumberOfCells(); cc++) {// 获取每个单元格
					if (!sheet.isColumnHidden(cc) && null != obj.get(cc).get("value") && !obj.get(cc).get("value").toString().equals("")) {
						fieldName.add(rows.getCell(cc));// 将选择的列放到最前面
						filterField.add(rows.getCell(cc).toString());// 将选择的列放到最前面
						nums.add(cc);// 存放下标
					}
				}
				Map<String, Object> map = getAllField(filterField);// 获得所有字段值
				String fieldAll = map.get("queryField").toString();
				String[] fieldList = fieldAll.split(",");
				// 填充隐藏列的数据库字段
				for (int a = 0; a < fieldList.length; a++) {
					fieldName.add(fieldList[a]);
				}
				// 去除以选择的列
				for (int j = 0; j < fieldName.size(); j++) {
					// 获得partnumber的下标
					if ("PartNumber".equals(fieldName.get(j).toString())) {
						partNumber = j;
					}
					if ("state".equals(fieldName.get(j).toString())) {
						state = j;
					}
					if ("Part_Type".equals(fieldName.get(j).toString())) {
						partType = j;
					}
					if ("TempPartMark".equals(fieldName.get(j).toString())) {
						TempPartMark = j;
					}
					if ("DirInOROut".equals(fieldName.get(j).toString())) {
						DirInOROut = j;
					}
					if ("Creator".equals(fieldName.get(j).toString())) {
						Creator = j;
					}
					if ("CreateDate".equals(fieldName.get(j).toString())) {
						CreateDate = j;
					}
					if ("firstCategory".equals(fieldName.get(j).toString())) {
						firstCategory = j;
					}
					if ("secondCategory".equals(fieldName.get(j).toString())) {
						secondCategory = j;
					}
					if ("thirdCategory".equals(fieldName.get(j).toString())) {
						thirdCategory = j;
					}
					if ("fourthCategory".equals(fieldName.get(j).toString())) {
						fourthCategory = j;
					}
					if ("fifthCategory".equals(fieldName.get(j).toString())) {
						fifthCategory = j;
					}
					if ("Modifier".equals(fieldName.get(j).toString())) {
						Modifier = j;
					}
					if ("ModifyDate".equals(fieldName.get(j).toString())) {
						ModifyDate = j;
					}
				}
				//////////////////////////////// 字段名拼接////////////////////////////////////
				//////////////////////////////// value拼接//////////////////////////////////////
				int line = 0;
				line = getValueOfFormulaCell(sheet, line);
				for (int xs = 1; xs < line; xs++) {

					Map<String, Object> pcMap = new HashMap<String, Object>();
					pcMap.put("pcType1", sheet.getSheetName());// partClass
																// 第一节点信息存储(sheet名称)

					Row row = sheet.getRow(xs);
					List<Object> value = new ArrayList<Object>();// 存放value值
					for (int number = 0; number < nums.size(); number++) {
						String values = row.getCell(Integer.valueOf(nums.get(number).toString())).toString();
						value.add(values.trim());// 去除首尾空格
					}
					for (int a = 0; a < fieldList.length; a++) {
						if (!fieldList[a].equals("")) {
							value.add("");
						}
					}
					// 元器件名称-赋值 || 分类信息存储 开始
					boolean pcCheck = false;
					if (firstCategory != -1) {
						value.set(partType, "第一级分类" + "," + value.get(fifthCategory));
						pcMap.put("pcType2", value.get(fifthCategory));
						pcCheck = true;
					}
					if (secondCategory != -1) {
						value.set(partType, value.get(secondCategory));
						pcMap.put("pcType3", "第二级分类" + "," + value.get(secondCategory));
						pcCheck = true;
					}
					if (thirdCategory != -1) {
						value.set(partType, value.get(thirdCategory));
						pcMap.put("pcType4", "第三级分类" + "," + value.get(thirdCategory));
						pcCheck = true;
					}
					if (fourthCategory != -1) {
						value.set(partType, value.get(fourthCategory));
						pcMap.put("pcType5", "第四级分类" + "," + value.get(fourthCategory));
						pcCheck = true;
					}
					// 元器件名称-赋值 || 分类信息存储 结束

					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("fieldName", fieldName);
					maps.put("fieldValue", value);

					if (pcCheck == false) {
						jsonMap.put("result", "noPartClass");// 没有对应元器件分类信息
						jsonMap.put("msg", "工作表：" + sheet.getSheetName() + "，没有映射匹配分类信息，缺少分类信息字段以及对应的值！");
						String msg = JSONObject.fromObject(jsonMap).toString();
						outputJson(response, msg);
						return;
					} else {
						if ("".equals(value.get(partType))) {
							jsonMap.put("result", "noValuePartType");// 没有对应物料编码
							jsonMap.put("msg", "工作表：" + sheet.getSheetName() + "第" + xs + "行" + "，分类字段的值不能为空！");
							String msg = JSONObject.fromObject(jsonMap).toString();
							outputJson(response, msg);
							return;
						}
						if (-1 == TempPartMark) {
							// jsonMap.put("result", "noTempPartMark");//临时物料标识
							// jsonMap.put("msg",
							// "工作表："+sheet.getSheetName()+"，缺少临时物料标识字段以及对应的值！");
							// String msg =
							// JSONObject.fromObject(jsonMap).toString();
							// outputJson(response,msg);
							// return;
						}
						if (-1 == DirInOROut) {
							// jsonMap.put("result", "noDirInOROut");//目录内外字段匹配
							// jsonMap.put("msg",
							// "工作表："+sheet.getSheetName()+"，缺少目录内外字段以及对应的值！");
							// String msg =
							// JSONObject.fromObject(jsonMap).toString();
							// outputJson(response,msg);
							// return;
						}
						// if("".equals(value.get(TempPartMark))){
						// jsonMap.put("result",
						// "noValueTempPartMark");//没有临时物料标识value
						// jsonMap.put("msg",
						// "工作表："+sheet.getSheetName()+"，临时物料标识的值不能为空！");
						// String msg =
						// JSONObject.fromObject(jsonMap).toString();
						// outputJson(response,msg);
						// return;
						// }else{
						// if(value.get(TempPartMark).equals("false") ||
						// value.get(TempPartMark).equals("FALSE") ||
						// value.get(TempPartMark).equals("TRUE") ||
						// value.get(TempPartMark).equals("true")){
						// value.set(DirInOROut,
						// value.get(TempPartMark).equals("false")||
						// value.get(TempPartMark).equals("FALSE")?"目录内":"目录外");
						// }else{
						// jsonMap.put("result",
						// "valueTypeTempPartMark");//临时物料标识value类型错误
						// jsonMap.put("msg",
						// "工作表："+sheet.getSheetName()+"，临时物料标识字段对应的的值只能为false或者true！");
						// String msg =
						// JSONObject.fromObject(jsonMap).toString();
						// outputJson(response,msg);
						// return;
						// }
						// Map<String, Object> mapClass = new HashMap<String,
						// Object>();
						// mapClass.put("tempPartMark",
						// value.get(TempPartMark));
						// mapClass.put("partType", value.get(partType));
						// List<Map<String, Object>> pClass =
						// partClassService.selectPartClass(mapClass);
						// if(null == pClass || pClass.size()<=0){
						// jsonMap.put("result", "noFoundPartTree");//元器件分类树
						// jsonMap.put("msg",
						// "工作表："+sheet.getSheetName()+"，"+"第"+line+"行，"+value.get(DirInOROut)+"元器件名称为"+value.get(partType)+"的找不到相对应的元器件分类！");
						// String msg =
						// JSONObject.fromObject(jsonMap).toString();
						// outputJson(response,msg);
						// return;
						// }else{
						// mapClass = new HashMap<String, Object>();
						// mapClass.put("pnCode", pClass.get(0).get("id"));
						// pClass = partClassService.selectPartClass(mapClass);
						// if(pClass.size()>0){
						// jsonMap.put("result", "noLastPartTree");//元器件分类树
						// jsonMap.put("msg",
						// "工作表："+sheet.getSheetName()+"，"+"第"+line+"行，"+value.get(DirInOROut)+"元器件名称为"+value.get(partType)+"，在元器件分类表内不是末级节点，请修改！");
						// String msg =
						// JSONObject.fromObject(jsonMap).toString();
						// outputJson(response,msg);
						// return;
						// }
						// }
						// }
					}
					if (partNumber != -1 && !"".equals(value.get(partNumber).toString())) {
						// jsonMap.put("result", "noPart");//没有对应物料编码
						// String msg =
						// JSONObject.fromObject(jsonMap).toString();
						// outputJson(response,msg);
						// return;
						// }else{
						// if("".equals(value.get(partNumber))){
						// jsonMap.put("result", "noPartData");//物料编码为空！
						// String msg =
						// JSONObject.fromObject(jsonMap).toString();
						// outputJson(response,msg);
						// return;
						// }
						Map<String, Object> mapSql = new HashMap<String, Object>();
						mapSql.put("PartNumber", value.get(partNumber));
						List<Map<String, Object>> partList = exportExcelService.selectPartDateByPartNumber(mapSql);
						if (null != partList) {
							jsonMap.put("result", "rePartData");// 物料编码重复！
							jsonMap.put("value", value.get(partNumber));
							String msg = JSONObject.fromObject(jsonMap).toString();
							outputJson(response, msg);
							return;
						}
					}
					// 创建人/状态编辑
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dateString = formatter.format(new Date());
					HrUser user = getUserInfo(request);
					String loginName = "admin";
					if (null != user) {
						loginName = user.getLoginName();
					}
					value.set(Creator, loginName);
					value.set(CreateDate, dateString);
					value.set(state, "1");
					value.set(Modifier, loginName);
					value.set(ModifyDate, dateString);
					list.add(maps);// 元器件信息集合
					pcList.add(pcMap);// 分类信息集合
				}
			}
			// 导入
			if (null != list && list.size() > 0) {
				Map<String, Object> resultmap = new HashMap<String, Object>();
				resultmap.put("pdList", list);
				resultmap.put("pcList", pcList);
				// exportExcelService.insertPartData(resultmap);
			}
			inputStream.close();
			// 删除临时目录
			String delP = getAbsoluteBasePath(request) + "partExport";
			deletefileMulu(delP);
			jsonMap.put("result", "success");// 导入成功!
			String msg = JSONObject.fromObject(jsonMap).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			// 删除临时目录
			String delP = getAbsoluteBasePath(request) + "partExport";
			try {
				deletefileMulu(delP);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 生成导入错误报告
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportMistakeResult.do")
	public void exportMistakeResult(HttpServletRequest request, HttpServletResponse response) {
		String sessionKey = request.getParameter("sessionKey");
		Map<String,Object> sessionMap = (Map<String, Object>) request.getSession().getAttribute(sessionKey);
		String result = request.getParameter("list");
		String showName = request.getParameter("name");
		List<Map> resultList = (List<Map>) sessionMap.get("list");
		String showNames = (String) sessionMap.get("showName");
		String fieldNames = (String) sessionMap.get("fieldName");
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("dataList", resultList);
			resultMap.put("fieldName", fieldNames);
			resultMap.put("showName", showNames);
			HSSFWorkbook wb = (new ExportExcel()).exportExcel(resultMap);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=\"" + new String("未导入数据集".getBytes("gb2312"), "ISO8859-1") + ".xls" + "\"");
			OutputStream ouputStream;
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
