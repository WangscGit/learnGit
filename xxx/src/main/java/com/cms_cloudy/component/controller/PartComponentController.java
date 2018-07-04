package com.cms_cloudy.component.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.cms_cloudy.component.pojo.FieldSelect;
import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.cms_cloudy.component.service.IPartDataService;
import com.cms_cloudy.component.service.IPartPrimaryAttributesService;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.database.service.IPartClassService;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.util.PropertiesUtils;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/partComponent")
public class PartComponentController extends BaseController {
	@Autowired
	private IPartPrimaryAttributesService partPrimaryAttributesService;
	@Autowired
	private IPartDataService partDataService;
	@Autowired
	private IPartClassService iPartClassService;
	public static String GROUP_NAME = "";
	static {
		try {
			GROUP_NAME = PropertiesUtils.getProperties().getProperty("GROUP_NAME");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 字段定义表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "selectPartPrimaryAttributeByPage")
	public void selectPartPrimaryAttributeByPage(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 分页初始化
		Integer pageNo = 1;
		Integer pageSize = 10;
		if (StringUtils.isNotEmpty(request.getParameter("pageNo"))) {
			pageNo = Integer.valueOf(request.getParameter("pageNo"));
		}
		if (StringUtils.isNotEmpty(request.getParameter("pageSize"))) {
			pageSize = Integer.valueOf(request.getParameter("pageSize"));
		}
		map.put("fieldName", "");
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		try {
			PageInfo<PartPrimaryAttributes> list = partPrimaryAttributesService.selectPartPrimaryAttributesByPage(map);
			String msg = JSONArray.fromObject(list).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/selectPartPrimaryAttribute.do")
	public void selectPartPrimaryAttribute(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		String lang = "zh";
		if (null != request.getSession().getAttribute("lang")) {
			lang = request.getSession().getAttribute("lang").toString();
		}
		String type = request.getParameter("type");
		String[] types = GROUP_NAME.split(",");
		if (StringUtils.isNotEmpty(type) && "daiban".equals(type)) {
			map.put("isUsed", false);
		} else {
			map.put("isUsed", true);
		}
		// map.put("fieldName", "PartNumber");
		try {
			List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectPartPrimaryAttributesList(map);
			mapJson.put("partList", list);
			mapJson.put("types", types);
			mapJson.put("lang", lang);
			String msg = JSONObject.fromObject(mapJson).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 字段定义表数据添加
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertPartPrimaryAttribute.do")
	public void insertPartPrimaryAttribute(HttpServletRequest request, HttpServletResponse response) {
		String fieldName = request.getParameter("fieldName");
		String showName = request.getParameter("showName");
		String englishName = request.getParameter("englishName");
		String dataType = request.getParameter("dataType");
		// String IsNull = request.getParameter("IsNull");
		String description = request.getParameter("description");
//		String isUsed = request.getParameter("isUesd");
		String isDisplay = request.getParameter("isDisplay");
		String isUpdate = request.getParameter("isUpdate");
		String isAudit = request.getParameter("isAudit");
		String isSearch = request.getParameter("isSearch");
		String isMatch = request.getParameter("isMatch");
		String isNull = request.getParameter("isNull");
		String isApply = request.getParameter("isApply");
		String isInsert = request.getParameter("isInsert");
		String type = request.getParameter("type");
		String typeValue = request.getParameter("typeValue");
		String fieldArray = request.getParameter("fieldArray");// 下拉列表值
		PartPrimaryAttributes priAttr = new PartPrimaryAttributes();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		map.put("fieldName", fieldName);
		try {
			showName = java.net.URLDecoder.decode(showName, "UTF-8");
			description = java.net.URLDecoder.decode(description, "UTF-8");
			fieldArray = java.net.URLDecoder.decode(fieldArray, "UTF-8");
			List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectPartPrimaryAttributesByList(map);
			if (list.size() > 0 && "add".equals(type)) {
				mapJson.put("result", "addRepeat");
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
				return;
			}
			List<FieldSelect> fieldList = new ArrayList<FieldSelect>();
			if (StringUtils.isNotEmpty(fieldArray)) {
				fieldList = JSON.parseArray(fieldArray, FieldSelect.class);
			}
			if (list.size() <= 0) {
				priAttr.setFieldName(fieldName);
				priAttr.setShowName(showName);
				priAttr.setEnglishName(englishName);
				priAttr.setDataType(dataType);
				priAttr.setIsNull(isNull);
				priAttr.setDescription(description);
				priAttr.setType(Integer.valueOf(typeValue));
				// Boolean.valueOf(isUsed)
				priAttr.setUsed(false);// 是否已经将字段存到主数据表内
				if ("true".equals(isDisplay)) {
					priAttr.setDisplay(true);
				} else {
					priAttr.setDisplay(false);
				}
				if ("true".equals(isUpdate)) {
					priAttr.setUpdate(true);
				} else {
					priAttr.setUpdate(false);
				}
				if ("true".equals(isAudit)) {
					priAttr.setAudit(true);
				} else {
					priAttr.setAudit(false);
				}
				if ("true".equals(isSearch)) {
					priAttr.setSearch(true);
				} else {
					priAttr.setSearch(false);
				}
				if ("true".equals(isMatch)) {
					priAttr.setMatch(true);
				} else {
					priAttr.setMatch(false);
				}
				if ("true".equals(isInsert)) {
					priAttr.setInsert(true);
				} else {
					priAttr.setInsert(false);
				}
				if ("true".equals(isApply)) {
					priAttr.setApply(true);
				} else {
					priAttr.setApply(false);
				}
				partPrimaryAttributesService.insertPartPrimaryAttributes(priAttr);
				for (int i = 0; i < fieldList.size(); i++) {// 添加下拉列表值
					partPrimaryAttributesService.insertFieldSelect(fieldList.get(i));
				}
				mapJson.put("result", "add");
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			} else {
				priAttr = list.get(0);
				priAttr.setFieldName(fieldName);
				priAttr.setShowName(showName);
				priAttr.setEnglishName(englishName);
				priAttr.setDataType(dataType);
				priAttr.setIsNull(isNull);
				priAttr.setDescription(description);
				// Boolean.valueOf(isUsed)
				priAttr.setUsed(true);
				if ("true".equals(isDisplay)) {
					priAttr.setDisplay(true);
				} else {
					priAttr.setDisplay(false);
				}
				if ("true".equals(isUpdate)) {
					priAttr.setUpdate(true);
				} else {
					priAttr.setUpdate(false);
				}
				if ("true".equals(isAudit)) {
					priAttr.setAudit(true);
				} else {
					priAttr.setAudit(false);
				}
				if ("true".equals(isSearch)) {
					priAttr.setSearch(true);
				} else {
					priAttr.setSearch(false);
				}
				if ("true".equals(isMatch)) {
					priAttr.setMatch(true);
				} else {
					priAttr.setMatch(false);
				}
				if ("true".equals(isInsert)) {
					priAttr.setInsert(true);
				} else {
					priAttr.setInsert(false);
				}
				if ("true".equals(isApply)) {
					priAttr.setApply(true);
				} else {
					priAttr.setApply(false);
				}
				partPrimaryAttributesService.updatePartPrimaryAttributes(priAttr);
				if (fieldList.size() > 0) {
					partPrimaryAttributesService.deleteFieldSelect(fieldName);// 先删除原有的下拉列表值
					for (int i = 0; i < fieldList.size(); i++) {// 添加下拉列表值
						partPrimaryAttributesService.insertFieldSelect(fieldList.get(i));
					}
				}
				mapJson.put("result", "update");
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deletePartPrimaryAttributes.do")
	public void deletePartPrimaryAttributes(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String fieldName = request.getParameter("fieldName");// 根据fieldName删除fieldSelect
		try {
			partPrimaryAttributesService.deletePartPrimaryAttributes(Integer.valueOf(id));
			partPrimaryAttributesService.deleteFieldSelect(fieldName);
			String msg = JSONObject.fromObject(null).toString();

			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询字段定义表所有字段名以及展示名
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "selectFieldAndName")
	public void selectFieldAndName(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapSql = new HashMap<String, Object>();
		map.put("isUsed", true);
		List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFieldAndName(map);
		String showName = "";
		String queryField = "";
		if (list.size() > 0) {
			for (PartPrimaryAttributes attr : list) {
				if (StringUtils.isNoneEmpty(attr.getShowName())) {
					showName += attr.getShowName();
				}
				if (StringUtils.isNoneEmpty(attr.getFieldName())) {
					queryField += attr.getFieldName() + ",";
				}
			}
		}
		queryField = queryField.substring(0, queryField.length() - 1);
		mapSql.put("sql", queryField);
		partPrimaryAttributesService.selectDataByFieldName(mapSql);
	}

	/**
	 * 判断是否存在Part_Data元器件表
	 * 
	 * @author WAGN_SC
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/createPartData.do")
	public void createPartData(HttpServletRequest request, HttpServletResponse response) {
		try {
			partPrimaryAttributesService.insertOrUpdatePartData();
			String msg = JSONObject.fromObject(null).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将用户修改的字段展示顺序保存到数据库
	 * 
	 * @author WAGN_SC
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateForm.do")
	public void updateForm(HttpServletRequest request, HttpServletResponse response) {
		String jsonArr = request.getParameter("fieldList");
		try {
			List<PartPrimaryAttributes> jsonList = (List<PartPrimaryAttributes>) JSON.parseArray(jsonArr,
					PartPrimaryAttributes.class);
			if (jsonList.size() > 0) {
				for (int i = 0; i < jsonList.size(); i++) {
					PartPrimaryAttributes partAttr = jsonList.get(i);
					partAttr.setUsed(true);
					partAttr.setSeqNo(i + 1);
					partPrimaryAttributesService.updatePartAttrSeqNo(partAttr);
				}
			}
			String msg = JSONObject.fromObject(null).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/commitData.do")
	public void commitData(HttpServletRequest request, HttpServletResponse response) {
		try {
			partPrimaryAttributesService.updateOrUpdatePartData();
			String msg = JSONObject.fromObject(null).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 编辑字段信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateField.do")
	public void updateField(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String jsonArr = request.getParameter("data");
		try {
			PartPrimaryAttributes jsonObject = (PartPrimaryAttributes) JSON.parseObject(jsonArr,
					PartPrimaryAttributes.class);
			map.put("fieldName", jsonObject.getFieldName());
			PartPrimaryAttributes partAttr = partPrimaryAttributesService.selectPartPrimaryAttributesList(map).get(0);
			partAttr.setShowName(jsonObject.getShowName());
			partAttr.setDescription(jsonObject.getDescription());
			partAttr.setAudit(jsonObject.isAudit());
			partAttr.setUpdate(jsonObject.isUpdate());
			partAttr.setSearch(jsonObject.isSearch());
			partAttr.setMatch(jsonObject.isMatch());
			partAttr.setDisplay(jsonObject.isDisplay());
			partAttr.setType(jsonObject.getType());
			partPrimaryAttributesService.updatePartPrimaryAttributes(partAttr);
			String msg = JSONObject.fromObject(null).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询添加、修改字段
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectAddOrUpdateField.do")
	public void selectAddOrUpdateField(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String partDataId = request.getParameter("id");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", partDataId);
			Map<String, Object> dataMap = partPrimaryAttributesService.selectAddOrUpdateField(paramMap);
			List<PartPrimaryAttributes> list = (List<PartPrimaryAttributes>) dataMap.get("filedList");
			// 生成物料编码
			String partType = request.getParameter("partType");
			String tempPartMark = request.getParameter("tempPartMark");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partType", partType);
			map.put("tempPartMark", tempPartMark);
			List<Map<String, Object>> l = iPartClassService.selectPartClass(map);
			List<Long> oids = new ArrayList<Long>();
			oids.add((Long) l.get(0).get("oid"));
			String partNumber = iPartClassService.getPartNumber(oids);

			resultMap.put("partNumber", partNumber);
			resultMap.put("message", "成功！");
			resultMap.put("dataList", list);
			resultMap.put("partMap", dataMap.get("partMap"));
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getSelectListByFieldName.do")
	public void getSelectListByFieldName(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String fieldName = request.getParameter("fieldName");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("fieldName", fieldName);
			List<FieldSelect> fsList = partPrimaryAttributesService.getFieldSelectByFieldName(paramMap);
			resultMap.put("fsList", fsList);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/saveSelectList.do")
	public void saveSelectList(HttpServletRequest request, HttpServletResponse response) {
		String fieldArray = request.getParameter("fieldArray");// 下拉列表值
		String fieldName = request.getParameter("fieldName");
		List<FieldSelect> fieldList = new ArrayList<FieldSelect>();
		if (StringUtils.isNotEmpty(fieldArray)) {
			fieldList = JSON.parseArray(fieldArray, FieldSelect.class);
		}
		partPrimaryAttributesService.deleteFieldSelect(fieldName);// 先删除原有的下拉列表值
		for (int i = 0; i < fieldList.size(); i++) {// 添加下拉列表值
			partPrimaryAttributesService.insertFieldSelect(fieldList.get(i));
		}
	}

	/**
	 * 字段定义修改Before
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/primaryAttributeUpdataBefore.do")
	public void primaryAttributeUpdataBefore(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		map.put("id", id);
		try {
			List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectPartPrimaryAttributesList(map);
			String msg = JSON.toJSONString(list.get(0));
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存字段定义修改信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateFieldAttr.do")
	public void updateFieldAttr(HttpServletRequest request, HttpServletResponse response) {
		String jsonArr = request.getParameter("fieldList");
		try {
			List<PartPrimaryAttributes> jsonList = (List<PartPrimaryAttributes>) JSON.parseArray(jsonArr,
					PartPrimaryAttributes.class);
			if (jsonList.size() > 0) {
				for (int i = 0; i < jsonList.size(); i++) {
					PartPrimaryAttributes partAttr = jsonList.get(i);
					partPrimaryAttributesService.updateFieldAttr(partAttr);
				}
			}
			String msg = JSONObject.fromObject(null).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 首页点击图片跳转至详情页
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/selectPartGopage.do")
	public void selectPartGopage(HttpServletResponse response) {
		try {
			List<Map<String, Object>> list = partPrimaryAttributesService.selectPartGopage();
			if (null == list || list.size() <= 0) {
				String msg = JSON.toJSONString(null);
				outputJson(response, msg);
			} else {
				String msg = JSON.toJSONString(list);
				outputJson(response, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加元器件页面的partdata字段
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectInsertPageField.do")
	public void selectInsertPageField(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<PartPrimaryAttributes> inList = partPrimaryAttributesService.selectInsertField(paramMap);
			// 判断中英文
			String langType = (String) request.getSession().getAttribute("lang");
			if (StringUtils.isEmpty(langType)) {
				langType = "zh";
			}
			langType = langType.trim();
			 for (PartPrimaryAttributes ppa : inList) {
			 if (langType.equals("en")) {
				 ppa.setShowName(ppa.getEnglishName());
			 }
			 }
			resultMap.put("message", "成功！");
			resultMap.put("insertList", inList);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据元器件类型更新特殊属性的展示名和物料编码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectProPertiesByPT.do")
	public void selectProPertiesByPT(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String partType = request.getParameter("partType");
			String tempPartMark = request.getParameter("tempPartMark");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partType", partType);
			map.put("tempPartMark", tempPartMark);
			// 生成物料编码
			List<Map<String, Object>> l = iPartClassService.selectPartClass(map);
			List<Long> oids = new ArrayList<Long>();
			oids.add((Long) l.get(0).get("oid"));
			String partNumber = iPartClassService.getPartNumber(oids);
			Map<String, Object> m = partPrimaryAttributesService.selectProperiesByName(map);
			resultMap.put("message", "成功！");
			resultMap.put("property", m);
			resultMap.put("partNumber", partNumber);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改元器件页面的partdata字段
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectEditPageField.do")
	public void selectEditPageField(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String partDataId = request.getParameter("id");// 元器件id
			if (StringUtils.isEmpty(partDataId)) {
				resultMap.put("message", "id为空");
				String msg = JSON.toJSONString(resultMap);
				outputJson(response, msg);
				return;
			}
			HrUser user = this.getUserInfo(request);
			String sql = "select * from part_data where id=" + partDataId;
			Map<String, Object> partMap = partDataService.selectPartDataById(sql.toString(),user);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFixedInsertField(paramMap);
			List<PartPrimaryAttributes> inList = partPrimaryAttributesService.selectInsertField(paramMap);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partType", partMap.get("Part_Type"));
			map.put("tempPartMark", partMap.get("TempPartMark"));
			Map<String, Object> m = partPrimaryAttributesService.selectProperiesByName(map);
			// 判断中英文
			String langType = (String) request.getSession().getAttribute("lang");
			if (StringUtils.isEmpty(langType)) {
				langType = "zh";
			}
			langType = langType.trim();
			for (PartPrimaryAttributes ppa : inList) {
				if (null != m && null != m.get(ppa.getFieldName()) && !"".equals(m.get(ppa.getFieldName()))) {
					ppa.setShowName(m.get(ppa.getFieldName()).toString());
				}
				if (langType.equals("en")) {
					ppa.setShowName(ppa.getEnglishName());
				}
			}
			if (langType.equals("en")) {
				for (PartPrimaryAttributes ppa : list) {
					ppa.setShowName(ppa.getEnglishName());
				}
			}
			resultMap.put("message", "成功！");
			resultMap.put("fixedInsertList", list);
			resultMap.put("insertList", inList);
			resultMap.put("partData", partMap);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 元器件申请页面的partdata字段
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/workfolwPartaddPageField.do")
	public void workfolwPartaddPageField(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFixedInsertField(paramMap);
			List<PartPrimaryAttributes> inList = partPrimaryAttributesService.selectApplyField(paramMap);
			String partType = request.getParameter("partType");
//			String tempPartMark = request.getParameter("tempPartMark");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partType", partType);
//			map.put("tempPartMark", tempPartMark);
			Map<String, Object> m = partPrimaryAttributesService.selectProperiesByName(map);
			// 判断中英文
			String langType = (String) request.getSession().getAttribute("lang");
			if (StringUtils.isEmpty(langType)) {
				langType = "zh";
			}
			langType = langType.trim();
			for (PartPrimaryAttributes ppa : inList) {
				if (null != m && null != m.get(ppa.getFieldName()) && !"".equals(m.get(ppa.getFieldName()))) {
					ppa.setShowName(m.get(ppa.getFieldName()).toString());
				}
				if (langType.equals("en")) {
					ppa.setShowName(ppa.getEnglishName());
				}
			}
			if (langType.equals("en")) {
				for (PartPrimaryAttributes ppa : list) {
					ppa.setShowName(ppa.getEnglishName());
				}
			}
			// 生成物料编码
			List<Map<String, Object>> l = iPartClassService.selectPartClass(map);
			List<Long> oids = new ArrayList<Long>();
			oids.add((Long) l.get(0).get("oid"));
			String partNumber = iPartClassService.getPartNumber(oids);

			resultMap.put("message", "成功！");
			resultMap.put("fixedInsertList", list);
			resultMap.put("applyList", inList);
			resultMap.put("partNumber", partNumber);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 元器件申请修改页面的partdata字段
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectApplyEditPageField.do")
	public void selectApplyEditPageField(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String partDataId = request.getParameter("id");// 元器件id
			if (StringUtils.isEmpty(partDataId)) {
				resultMap.put("message", "id为空");
				String msg = JSON.toJSONString(resultMap);
				outputJson(response, msg);
				return;
			}
			String sql = "select * from part_data where id=" + partDataId;
			Map<String, Object> partMap = partDataService.selectPartDataById(sql.toString(),null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<PartPrimaryAttributes> list = partPrimaryAttributesService.selectFixedInsertField(paramMap);
			List<PartPrimaryAttributes> inList = partPrimaryAttributesService.selectApplyField(paramMap);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partType", partMap.get("Part_Type"));
			map.put("tempPartMark", partMap.get("TempPartMark"));
			Map<String, Object> m = partPrimaryAttributesService.selectProperiesByName(map);
			// 判断中英文
			String langType = (String) request.getSession().getAttribute("lang");
			if (StringUtils.isEmpty(langType)) {
				langType = "zh";
			}
			langType = langType.trim();
			for (PartPrimaryAttributes ppa : inList) {
				if (null != m && null != m.get(ppa.getFieldName()) && !"".equals(m.get(ppa.getFieldName()))) {
					ppa.setShowName(m.get(ppa.getFieldName()).toString());
				}
				if (langType.equals("en")) {
					ppa.setShowName(ppa.getEnglishName());
				}
			}
			if (langType.equals("en")) {
				for (PartPrimaryAttributes ppa : list) {
					ppa.setShowName(ppa.getEnglishName());
				}
			}
			resultMap.put("message", "成功！");
			resultMap.put("fixedInsertList", list);
			resultMap.put("insertList", inList);
			resultMap.put("partData", partMap);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
