package com.cms_cloudy.product.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cms_cloudy.component.dao.IPartDataDao;
import com.cms_cloudy.component.pojo.PartPrimaryAttributes;
import com.cms_cloudy.component.service.IPartDataService;
import com.cms_cloudy.component.service.IPartPrimaryAttributesService;
import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.controller.ExportExcel;
import com.cms_cloudy.product.pojo.ProductBomEntity;
import com.cms_cloudy.product.pojo.ProductBomPn;
import com.cms_cloudy.product.pojo.ProductPn;
import com.cms_cloudy.product.service.IProductBomService;
import com.cms_cloudy.user.pojo.HrRights;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.util.FormatString;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/productBomController")
public class ProductBomController extends BaseController {
	@Autowired
	private IProductBomService iProductBomService;
	@Autowired
	private IPartDataService partDataService;
	@Autowired
	private IPartDataDao iPartDataDao;
	@Autowired
	private IPartPrimaryAttributesService partPrimaryAttributesService;

	/**
	 * 产品树展示
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "selectSelectProductBomList")
	public void selectSelectProductBomList(HttpServletRequest request, HttpServletResponse response) {
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapParent = new HashMap<String, Object>();
		try {
			HrUser user = this.getUserInfo(request);// 当前登录人
			if (null != user) {
				map.put("receiverAndLoginName", user.getLoginName());
			} else {
				String jsonString = JSON.toJSONString(null);
				outputJson(response, jsonString);
				return;
			}
			List<ProductBomEntity> bomList = iProductBomService.selectProductBomList(map);
			for (int i = 0; i < bomList.size(); i++) {
				if (0 != bomList.get(i).getParentId() && !"".equals(bomList.get(i).getParentId())) {
					long id = bomList.get(i).getId();
					mapParent.put("id", id);
					List<ProductBomEntity> parentList = iProductBomService.selectAllParentData(mapParent);
					parentList.remove(0);
					for (ProductBomEntity bom1 : bomList) {
						for (int x = 0; x < parentList.size(); x++) {
							if (bom1.getId() == parentList.get(x).getId()) {
								parentList.remove(parentList.get(x));
							}
						}
					}
					bomList.addAll(parentList);
				}
			}
			Collections.<ProductBomEntity> sort(bomList, new BeanComparator("id"));// 进行排序
			// Set<ProductBomEntity> ts = new
			// HashSet<ProductBomEntity>(bomList);
			if (null != bomList && bomList.size() > 0) {
				for (int i = 0; i < bomList.size(); i++) {
					ProductBomEntity bom = bomList.get(i);
					Map<Object, Object> mapObj = new HashMap<Object, Object>();
					mapObj.put("id", bom.getId());
					map.put("parentId", bom.getId());
					if (0 == bom.getParentId() || "".equals(bom.getParentId())) {
						mapObj.put("pId", "0");
					} else {
						mapObj.put("pId", bom.getParentId());
					}
					// if(0 == bom.getParentId() || ""
					// .equals(bom.getParentId())){
					// mapObj.put("name",
					// bom.getProductType()+"-"+bom.getNumber()+bom.getProductName());
					// }else{
					mapObj.put("name", bom.getProductName());
					// }
					mapObj.put("nodeStage", bom.getNodeStage());
					mapObj.put("productName", bom.getProductName());
					mapObj.put("productType", bom.getProductType());
					mapObj.put("createuser", bom.getCreateuser());
					mapObj.put("productNo", bom.getProductNo());
					mapObj.put("productCode", bom.getProductCode());
					mapObj.put("productStage", bom.getProductStage());
					mapObj.put("pTool", bom.getpTool());
					mapObj.put("receiver", bom.getReceiver());
					mapObj.put("version", bom.getVersion());
					list.add(mapObj);
				}
				String jsonString = JSON.toJSONString(list);
				outputJson(response, jsonString);
			} else {
				String jsonString = JSON.toJSONString(null);
				outputJson(response, jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProductBomEntity selectAllNodes(long parentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", parentId);
		List<ProductBomEntity> list = iProductBomService.selectProductBomList(map);
		return list.get(0);
	}

	/**
	 * 通过产品ID分页查询元器件数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectPartDataByBom.do")
	public void selectPartDataByBom(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> mapSql = new HashMap<String, Object>();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		String lang = "zh";
		if (null != request.getSession().getAttribute("lang")) {
			lang = request.getSession().getAttribute("lang").toString();
		}
		PageInfo<Map<String, Object>> page = null;
		String pageNo = request.getParameter("pageNo");
		// 分页初始化
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = "1";
		}
		String pageSize = "5";
		String productId = request.getParameter("productId");
		mapSql.put("productID", productId);
		try {
			List<Map<String, Object>> showNameList = partPrimaryAttributesService.selectTableField();
			PageHelper.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
			List<Map<String, Object>> dataList = partDataService.selectProductBomByPn(mapSql);
			if (dataList.size() > 0) {
				page = new PageInfo<Map<String, Object>>(dataList);
			}
			if (dataList.size() > 0) {
				mapJson.put("title", showNameList);
				mapJson.put("lang", lang);
				mapJson.put("list", page.getList());
				mapJson.put("count", page.getTotal());
				mapJson.put("pageNo", pageNo);
				mapJson.put("pageSize", pageSize);
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			} else {
				mapJson.put("title", showNameList);
				mapJson.put("lang", lang);
				mapJson.put("list", null);
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加元器件到产品
	 */
	@RequestMapping(value = "/insertProductPn.do")
	public void insertProductPn(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String partNumbersStr = request.getParameter("partNumbers");
			String productId = request.getParameter("productId");
			if (partNumbersStr == null) {
				resultMap.put("message", "数据为空");
				outputJson(response, JSON.toJSONString(resultMap));
				return;
			}
			List<Object> partNumbers = JSON.parseArray(partNumbersStr);
			for (int i = 0; i < partNumbers.size(); i++) {
				String pNum = (String) partNumbers.get(i);
				String[] pm = pNum.split(",");
				ProductPn pp = new ProductPn();
				pp.setProductId(Integer.valueOf(productId));
				pp.setPartNumber(pm[0]);
				pp.setSelectedTime(new Date());
				if (pm.length < 2) {
					pp.setCoun(1);
				} else {
					pp.setCoun(Integer.valueOf(pm[1]));
				}
				iProductBomService.insertProductPn(pp);
			}
			outputJson(response, JSON.toJSONString(resultMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 产品树购建
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertProductBom.do")
	public void insertProductBom(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> mapSql = new HashMap<String, Object>();
		Map<String, Object> mapSql2 = new HashMap<String, Object>();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		ProductBomEntity bom = new ProductBomEntity();
		ProductBomEntity bomSystem = new ProductBomEntity();
		HrUser user = this.getUserInfo(request);// 当前登录人
		Date date = parseDateToSqlServer(formatDateToSqlServer(new Date()));// 解析日期
		String createuser = "";
		if (null != user) {
			createuser = user.getLoginName();
		} else {
			createuser = "admin";
		}
		String nodeStage = request.getParameter("nodeStage");
		String productName = request.getParameter("productName");
		String systemProductName = request.getParameter("systemProductName");
		String engineProductName = request.getParameter("engineProductName");
		String boardProductName = request.getParameter("boardProductName");
		String productNo = request.getParameter("productNo");
		String productCode = request.getParameter("productCode");
		String version = request.getParameter("version");
		String productStage = request.getParameter("productStage");
		String pTool = request.getParameter("pTool");
		String productType = request.getParameter("productType");
		String receiver = request.getParameter("receiver");
		String parentId = request.getParameter("parentId");
		int nodeLevel = Integer.valueOf(nodeStage);
		try {
			if ("1".equals(nodeStage)) {
				// 一级节点添加开始
				bom.setParentId(0);
				mapSql.put("productName", productName);
				mapSql.put("parentId", 0);
				List<ProductBomEntity> list = iProductBomService.selectProductBomList(mapSql);
				if (list.size() > 0) {
					mapJson.put("result", "first");
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
					return;
				} else {
					bom.setProductName(productName);
					bom.setReceiver(receiver);
					bom.setNodeStage(nodeLevel);
					bom.setCreateuser(createuser);
					bom.setCreateDate(date);
					iProductBomService.insertProductBom(bom);
					// 一级节点添加结束
				}
			} else if ("2".equals(nodeStage)) {
				mapSql.put("productName", systemProductName);
				mapSql.put("parentId", parentId);
				mapSql.put("nodeStage", nodeLevel);
				List<ProductBomEntity> listSystem = iProductBomService.selectProductBomList(mapSql);
				if (listSystem.size() > 0) {
					mapJson.put("result", "system");
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
					return;
				} else {
					bomSystem.setParentId(Long.valueOf(parentId));
					bomSystem.setProductName(systemProductName);
					bomSystem.setReceiver(receiver);
					bomSystem.setReceiveTime(date);
					bomSystem.setNodeStage(nodeLevel);
					bomSystem.setCreateuser(createuser);
					bomSystem.setCreateDate(date);
					iProductBomService.insertProductBom(bomSystem);
				}
			} else if ("3".equals(nodeStage)) {
				mapSql.put("productName", engineProductName);
				mapSql.put("parentId", parentId);
				mapSql.put("nodeStage", nodeLevel);
				List<ProductBomEntity> listSystem = iProductBomService.selectProductBomList(mapSql);
				if (listSystem.size() > 0) {
					mapJson.put("result", "engine");
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
					return;
				} else {
					bomSystem.setParentId(Long.valueOf(parentId));
					bomSystem.setProductName(engineProductName);
					bomSystem.setReceiver(receiver);
					bomSystem.setReceiveTime(date);
					bomSystem.setNodeStage(nodeLevel);
					bomSystem.setCreateuser(createuser);
					bomSystem.setCreateDate(date);
					iProductBomService.insertProductBom(bomSystem);
				}
			} else if ("4".equals(nodeStage)) {
				mapSql.put("productName", boardProductName);
				mapSql.put("parentId", parentId);
				mapSql.put("nodeStage", nodeLevel);
				mapSql2.put("productNo", productNo);
				mapSql2.put("parentId", parentId);
				mapSql2.put("nodeStage", nodeLevel);
				List<ProductBomEntity> listBoard = iProductBomService.selectProductBomList(mapSql);
				List<ProductBomEntity> listBoard2 = iProductBomService.selectProductBomList(mapSql2);
				if (listBoard.size() > 0) {
					mapJson.put("result", "nameRep");
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
					return;
				}
				if (listBoard2.size() > 0) {
					mapJson.put("result", "noRep");
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
					return;
				}
				// if(listBoard.size()>0){
				// ProductBomEntity bomEnd = listBoard.get(listBoard.size()-1);
				// if(!bomEnd.getVersion().equals("Z")){
				// int versionCut = bomEnd.getVersion().charAt(0);
				// versionCut++;
				// char charV = (char)versionCut;
				// version = String.valueOf(charV);
				// }else{
				// mapJson.put("result", "board");
				// String msg = JSONObject.fromObject(mapJson).toString();
				// outputJson(response,msg);
				// return;
				// }
				// }else{
				// version = "A";
				// }
				bom.setProductName(boardProductName);
				bom.setProductNo(productNo);
				bom.setProductCode(productCode);
				// bom.setVersion(version);
				// bom.setReceiver(receiver);
				bom.setProductStage(productStage);
				bom.setpTool(pTool);
				bom.setParentId(Long.valueOf(parentId));
				bom.setNodeStage(nodeLevel);
				bom.setCreateuser(createuser);
				bom.setCreateDate(date);
				iProductBomService.insertProductBom(bom);
			}
			mapJson.put("result", "success");
			String msg = JSONObject.fromObject(mapJson).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 产品树节点修改
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateProductBom.do")
	public void updateProductBom(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> mapSql = new HashMap<String, Object>();
		Map<String, Object> mapSql2 = new HashMap<String, Object>();
		Map<String, Object> mapSql3 = new HashMap<String, Object>();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		ProductBomEntity bom = new ProductBomEntity();
		HrUser user = this.getUserInfo(request);// 当前登录人
		Date date = parseDateToSqlServer(formatDateToSqlServer(new Date()));// 解析日期
		String updateeuser = "";
		if (null != user) {
			updateeuser = user.getLoginName();
		} else {
			updateeuser = "admin";
		}
		String id = request.getParameter("id");
		mapSql.put("id", id);
		bom = iProductBomService.selectProductBomList(mapSql).get(0);// 修改前
		String nodeStage = request.getParameter("nodeStage");
		String productName = request.getParameter("productName");
		String systemProductName = request.getParameter("systemProductName");
		String engineProductName = request.getParameter("engineProductName");
		String boardProductName = request.getParameter("boardProductName");
		String productNo = request.getParameter("productNo");
		String productCode = request.getParameter("productCode");
		String version = request.getParameter("version");
		String productStage = request.getParameter("productStage");
		String pTool = request.getParameter("pTool");
		String productType = request.getParameter("productType");
		String receiver = request.getParameter("receiver");
		int nodeLevel = Integer.valueOf(nodeStage);
		try {
			if (1 == nodeLevel) {
				if (!bom.getProductName().equals(productName)) {
					mapSql2.put("productName", productName);
					mapSql2.put("parentId", 0);
					List<ProductBomEntity> list = iProductBomService.selectProductBomList(mapSql2);
					if (list.size() == 0) {
						// bom.setNumber(list.size()+1);
						bom.setProductName(productName);
						bom.setReceiver(receiver);
						bom.setModifyuser(updateeuser);
						bom.setModifyDate(date);
						iProductBomService.updateProductBom(bom);
					} else {
						mapJson.put("result", "first");
						String msg = JSONObject.fromObject(mapJson).toString();
						outputJson(response, msg);
						return;
					}
				} else {
					bom.setReceiver(receiver);
					bom.setModifyuser(updateeuser);
					bom.setModifyDate(date);
					iProductBomService.updateProductBom(bom);
				}
			} else if (2 == nodeLevel) {
				if (bom.getProductName().equals(systemProductName)) {
					bom.setReceiver(receiver);
					bom.setModifyuser(updateeuser);
					bom.setModifyDate(date);
					iProductBomService.updateProductBom(bom);
				} else {
					mapSql.put("productName", systemProductName);
					mapSql.put("parentId", bom.getParentId());
					List<ProductBomEntity> listlist = iProductBomService.selectProductBomList(mapSql);
					if (listlist.size() > 0) {
						mapJson.put("result", "system");
						String msg = JSONObject.fromObject(mapJson).toString();
						outputJson(response, msg);
						return;
					} else {
						bom.setProductName(systemProductName);
						bom.setReceiver(receiver);
						bom.setReceiveTime(date);
						bom.setModifyuser(updateeuser);
						bom.setModifyDate(date);
						iProductBomService.updateProductBom(bom);
					}
				}
			} else if (3 == nodeLevel) {
				if (bom.getProductName().equals(engineProductName)) {
					bom.setReceiver(receiver);
					bom.setModifyuser(updateeuser);
					bom.setModifyDate(date);
					iProductBomService.updateProductBom(bom);
				} else {
					mapSql.put("productName", engineProductName);
					mapSql.put("parentId", bom.getParentId());
					List<ProductBomEntity> listlist = iProductBomService.selectProductBomList(mapSql);
					if (listlist.size() > 0) {
						mapJson.put("result", "engine");
						String msg = JSONObject.fromObject(mapJson).toString();
						outputJson(response, msg);
						return;
					} else {
						bom.setProductName(engineProductName);
						bom.setReceiver(receiver);
						bom.setReceiveTime(date);
						bom.setModifyuser(updateeuser);
						bom.setModifyDate(date);
						iProductBomService.updateProductBom(bom);
					}
				}
			} else if (4 == nodeLevel) {
				if (boardProductName.equals(bom.getProductName())) {
					if (productNo.equals(bom.getProductNo())) {
						bom.setProductCode(productCode);
						bom.setProductNo(productNo);
						// bom.setVersion(version);
						bom.setProductStage(productStage);
						bom.setpTool(pTool);
						iProductBomService.updateProductBom(bom);
					} else {
						mapSql3.put("productNo", productNo);
						mapSql3.put("parentId", bom.getParentId());
						mapSql3.put("nodeStage", nodeLevel);
						List<ProductBomEntity> listBoard2 = iProductBomService.selectProductBomList(mapSql3);
						if (listBoard2.size() > 0) {
							mapJson.put("result", "noRep");
							String msg = JSONObject.fromObject(mapJson).toString();
							outputJson(response, msg);
							return;
						}
						bom.setProductCode(productCode);
						bom.setProductNo(productNo);
						// bom.setVersion(version);
						bom.setProductStage(productStage);
						bom.setpTool(pTool);
						iProductBomService.updateProductBom(bom);
					}
					// 同步版本
					// mapSql3.put("productName", name);
					// mapSql3.put("parentId", bom.getParentId());
					// mapSql3.put("nodeStage", "4");
					// List<ProductBomEntity> listOther =
					// iProductBomService.selectProductBomList(mapSql3);
					// if(listOther.size()>0){
					// for(int xx=0;xx<listOther.size();xx++){
					// char str = (char)('A' + xx);
					// ProductBomEntity bomEnd = listOther.get(xx);
					// bomEnd.setVersion(String.valueOf(str));
					// iProductBomService.updateProductBom(bomEnd);
					// }
					// }
				} else {
					if (productNo.equals(bom.getProductNo())) {
						// String name = bom.getProductName();
						mapSql3.put("productName", boardProductName);
						mapSql3.put("parentId", bom.getParentId());
						mapSql3.put("nodeStage", "4");
						List<ProductBomEntity> list = iProductBomService.selectProductBomList(mapSql3);
						if (list.size() > 0) {
							mapJson.put("result", "nameRep");
							String msg = JSONObject.fromObject(mapJson).toString();
							outputJson(response, msg);
							return;
						}
						bom.setProductName(boardProductName);
						bom.setProductCode(productCode);
						bom.setProductNo(productNo);
						// bom.setVersion(version);
						bom.setProductStage(productStage);
						bom.setpTool(pTool);
						iProductBomService.updateProductBom(bom);
					} else {
						// String name = bom.getProductName();
						mapSql2.put("productName", boardProductName);
						mapSql2.put("parentId", bom.getParentId());
						mapSql2.put("nodeStage", "4");
						mapSql3.put("productNo", productNo);
						mapSql3.put("parentId", bom.getParentId());
						mapSql3.put("nodeStage", nodeLevel);
						List<ProductBomEntity> listBoard2 = iProductBomService.selectProductBomList(mapSql3);
						List<ProductBomEntity> list = iProductBomService.selectProductBomList(mapSql2);
						if (listBoard2.size() > 0) {
							mapJson.put("result", "noRep");
							String msg = JSONObject.fromObject(mapJson).toString();
							outputJson(response, msg);
							return;
						}
						if (list.size() > 0) {
							mapJson.put("result", "nameRep");
							String msg = JSONObject.fromObject(mapJson).toString();
							outputJson(response, msg);
							return;
							// ProductBomEntity bomEnd =
							// list.get(list.size()-1);
							// if(!bomEnd.getVersion().equals("Z")){
							// int versionCut = bomEnd.getVersion().charAt(0);
							// versionCut++;
							// char charV = (char)versionCut;
							// version = String.valueOf(charV);
							// }else{
							// mapJson.put("result", "board");
							// String msg =
							// JSONObject.fromObject(mapJson).toString();
							// outputJson(response,msg);
							// return;
							// }
						}
						// else{
						// version = "A";
						// }
						bom.setProductName(boardProductName);
						bom.setProductCode(productCode);
						bom.setProductNo(productNo);
						// bom.setVersion(version);
						bom.setProductStage(productStage);
						bom.setpTool(pTool);
						iProductBomService.updateProductBom(bom);
						// 同步版本
						// mapSql3.put("productName", name);
						// mapSql3.put("parentId", bom.getParentId());
						// mapSql3.put("nodeStage", "4");
						// List<ProductBomEntity> listOther =
						// iProductBomService.selectProductBomList(mapSql3);
						// if(listOther.size()>0){
						// for(int xx=0;xx<listOther.size();xx++){
						// char str = (char)('A' + xx);
						// ProductBomEntity bomEnd = listOther.get(xx);
						// bomEnd.setVersion(String.valueOf(str));
						// iProductBomService.updateProductBom(bomEnd);
						// }
						// }
					}
				}
			}
			mapJson.put("result", "success");
			String msg = JSONObject.fromObject(mapJson).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 产品树节点删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteProductBom.do")
	public void deleteProductBom(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> mapSql = new HashMap<String, Object>();
		Map<String, Object> mapVersion = new HashMap<String, Object>();
		String id = request.getParameter("id");
		try {
			Map<String, Object> mapObject = new HashMap<String, Object>();
			mapObject.put("parentId", id);
			List<ProductBomEntity> childTempList = iProductBomService.selectAllChildData(mapObject);
			if (childTempList.size() > 0) {
				for (ProductBomEntity bomId : childTempList) {
					// 先删除关系表productPn
					mapSql.put("productId", bomId.getId());
					List<ProductPn> pnList = iProductBomService.selectProductPnList(mapSql);
					if (pnList.size() > 0) {
						for (int i = 0; i < pnList.size(); i++) {
							iProductBomService.deleteProductPn(pnList.get(i).getId());
						}
					}
					mapVersion.put("id", bomId.getId());
					List<ProductBomEntity> listCheck = iProductBomService.selectProductBomList(mapVersion);
					// 删除bom表
					iProductBomService.deleteProductBom(Integer.valueOf(bomId.getId() + ""));
					if (4 == listCheck.get(0).getNodeStage()) {
						// 同步版本
						// mapResetV.put("productName",
						// listCheck.get(0).getProductName());
						// mapResetV.put("parentId",
						// listCheck.get(0).getParentId());
						// mapResetV.put("nodeStage", "4");
						// List<ProductBomEntity> listOther =
						// iProductBomService.selectProductBomList(mapResetV);
						// if(listOther.size()>0){
						// for(int xx=0;xx<listOther.size();xx++){
						// char str = (char)('A' + xx);
						// ProductBomEntity bomEnd = listOther.get(xx);
						// bomEnd.setVersion(String.valueOf(str));
						// iProductBomService.updateProductBom(bomEnd);
						// }
						// }
					}
				}
			}
			mapSql.put("productId", id);
			List<ProductPn> pnList = iProductBomService.selectProductPnList(mapSql);
			if (pnList.size() > 0) {
				for (int i = 0; i < pnList.size(); i++) {
					iProductBomService.deleteProductPn(pnList.get(i).getId());
				}
			}
			iProductBomService.deleteProductBom(Integer.valueOf(id));
			String msg = JSONObject.fromObject(null).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 产品复制
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "copyProductBom")
	public void copyProductBom(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> mapSql = new HashMap<String, Object>();
		Map<String, Object> mapPn = new HashMap<String, Object>();
		Map<String, Object> mapCheck = new HashMap<String, Object>();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		Map<String, Object> mapId = new HashMap<String, Object>();
		try {
			HrUser user = this.getUserInfo(request);// 当前登录人
			Date date = parseDateToSqlServer(formatDateToSqlServer(new Date()));// 解析日期
			String createuser = "";
			if (null != user) {
				createuser = user.getLoginName();
			} else {
				createuser = "admin";
			}
			String id = request.getParameter("fromId");
			String parentId = request.getParameter("toId");
			String version = "";
			mapSql.put("id", id);
			ProductBomEntity bomNew = new ProductBomEntity();
			List<ProductBomEntity> list = iProductBomService.selectProductBomList(mapSql);
			BeanUtils.copyProperties(list.get(0), bomNew);// JavaBean复制
			bomNew.setParentId(Long.valueOf(parentId));
			bomNew.setId(0);
			mapCheck.put("productName", bomNew.getProductName());
			mapCheck.put("parentId", parentId);
			mapCheck.put("nodeStage", "4");
			List<ProductBomEntity> listCheck = iProductBomService.selectProductBomList(mapCheck);
			if (listCheck.size() > 0) {
				ProductBomEntity bomEnd = listCheck.get(listCheck.size() - 1);
				if (!bomEnd.getVersion().equals("Z")) {
					int versionCut = bomEnd.getVersion().charAt(0);
					versionCut++;
					char charV = (char) versionCut;
					version = String.valueOf(charV);
				} else {
					mapJson.put("result", "fail");
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
					return;
				}
			} else {
				version = "A";
			}
			bomNew.setVersion(version);
			bomNew.setCreateuser(createuser);
			bomNew.setCreateDate(date);
			iProductBomService.insertProductBom(bomNew);
			/**
			 * 获得复制结果的ID
			 */
			mapId.put("productName", bomNew.getProductName());
			mapId.put("parentId", parentId);
			mapId.put("nodeStage", "4");
			mapId.put("version", version);
			List<ProductBomEntity> listId = iProductBomService.selectProductBomList(mapId);
			/**
			 * 版本用Unicode叠加
			 */
			mapPn.put("productId", id);
			// 产品关系表复制
			List<ProductPn> pnList = iProductBomService.selectProductPnList(mapPn);
			if (pnList.size() > 0) {
				for (int i = 0; i < pnList.size(); i++) {
					ProductPn pn = pnList.get(i);
					ProductPn newPn = new ProductPn();
					newPn.setNumbers(pn.getNumbers());
					newPn.setPartNumber(pn.getPartNumber());
					newPn.setProductId(Integer.valueOf(listId.get(0).getId() + ""));
					newPn.setSelectedTime(date);
					iProductBomService.insertProductPn(newPn);
				}
			}
			mapJson.put("result", "success");
			String msg = JSONObject.fromObject(mapJson).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportBom.do")
	public void exportBom(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			// ExcelServlet excel =new ExcelServlet();
			(new ExportExcel()).exportProductBom(id, request, response);
			// excel.download("C://器件信息.xls", response);
			// String msg = JSONObject.fromObject(null).toString();
			// outputJson(response,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 产品追溯分析
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/proAscend.do")
	public void proAscend(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> mapSql = new HashMap<String, Object>();
		Map<String, Object> mapChild = new HashMap<String, Object>();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		List<Object> listObj = new ArrayList<Object>();
		Object partNumber = request.getParameter("partNumber");
		try {
			// 查询该器件在产品BOM下应用集合
			mapSql.put("PartNumber", partNumber);
			partNumber = partPrimaryAttributesService.selectPartDateByPartNumber(mapSql).get(0).get("PartCode");
			mapSql.put("partNumber", partNumber);
			List<Map<String, Object>> list = partDataService.selectDataByPartNumber(mapSql);
			if (list.size() > 0) {
				int count = 0;
				Set<String> ids = new HashSet<String>();
				int i = 0;
				for (Map<String, Object> id : list) {
					ids.add(id.get("productID").toString());
					count += Integer.valueOf(id.get("Numbers").toString());
				}
				i = ids.size();
				// 根据产品ID 查询根节点
				List<Map<String, Object>> bomList = iProductBomService
						.selectAllParent((String[]) ids.toArray(new String[0]));
				Set<Map<String, Object>> lPersonSet = new HashSet<Map<String, Object>>();
				lPersonSet.addAll(bomList);
				bomList.clear();
				bomList.addAll(lPersonSet);
				List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
				if (bomList.size() > 0) {
					for (Map<String, Object> bom : bomList) {
						if ("1".equals(bom.get("node_stage").toString())) {
							parentList.add(bom);
						}
					}
				}
				int x = 0;
				for (Map<String, Object> parents : parentList) {
					Map<String, Object> mapObject = new HashMap<String, Object>();
					mapChild.put("parentId", parents.get("id"));
					List<ProductBomEntity> childTempList = iProductBomService.selectAllChildData(mapChild);
					List<Object> listChild = new ArrayList<Object>();
					int qty = 0;
					for (ProductBomEntity childs : childTempList) {
						if (2 == childs.getNodeStage()) {
							Iterator<String> it = ids.iterator();
							while (it.hasNext()) {
								String productID = it.next();
								// "name": "vis","children": [{"name":
								// "axis","children": [{"name": "Axes","value":
								// 1302}]}, {"name": "controls","children":
								// [{"name": "AnchorControl","value": 2138}]}]
								if (childs.getId() == Long.valueOf(productID)) {
									Map<String, Object> mapChilds = new HashMap<String, Object>();// 第一层子节点
									Map<String, Object> bomQueryMap = new HashMap<String, Object>();
									mapChilds.put("name",
											childs.getProductName() + "-" + childs.getVersion() + "[qty:" + 0 + "]");
									List<Map<String, Object>> bomDetailList = new ArrayList<Map<String, Object>>();
									// BOM文件查询
									bomQueryMap.put("productID", productID);
									bomQueryMap.put("partNumber", partNumber);
									List<Map<String, Object>> dataList = partDataService
											.selectProductBomByBomPn(bomQueryMap);
									int xx = 0;
									for (int b = 0; b < dataList.size(); b++) {// 挂接第二层子节点(excel名称)
										Map<String, Object> bomResult = dataList.get(b);
										Map<String, Object> map = new HashMap<String, Object>();// 第二层子节点
										map.put("name", bomResult.get("excel_name") + "-"
												+ "[qty:" + bomResult.get("Numbers") + "]");
										bomDetailList.add(map);
										mapChilds.put("children", bomDetailList);
										xx += Integer.valueOf(bomResult.get("Numbers").toString());
									}
									mapChilds.put("name",
											childs.getProductName() + "-" + childs.getVersion() + "[qty:" + xx + "]");
									listChild.add(mapChilds);
									qty += xx;
								}
							}
						}
					}
					mapObject.put("name", parents.get("product_name") + "[qty:" + qty + "]");
					mapObject.put("pId", parents.get("id"));
					mapObject.put("children", listChild);
					listObj.add(x, mapObject);
					x++;
				}
				mapJson.put("list", list);
				mapJson.put("partNumber", partNumber + "[qty:" + count + "]");
				mapJson.put("listObj", listObj);
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			} else {
				mapJson.put("list", list);
				mapJson.put("partNumber", partNumber);
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteDataFrombom.do")
	public void deleteDataFrombom(HttpServletRequest request, HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String productId = request.getParameter("productId");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Object> idList = JSON.parseArray(ids);
			if (idList.size() > 0 && !"".equals(productId)) {
				for (int x = 0; x < idList.size(); x++) {
					map.put("partNumber", idList.get(x));
					map.put("id", productId);
					iProductBomService.deleteProductPnByProductId(map);
				}
			}
			String msg = JSONObject.fromObject(null).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户信息以及权限信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRightAndUser.do")
	public void getRightAndUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<HrRights> menuRightList = (List<HrRights>) request.getSession().getAttribute("dataRightList");
			HrUser user = this.getUserInfo(request);
			resultMap.put("dataRightList", menuRightList);
			resultMap.put("user", user);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析excel并返回数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getExcelContent.do")
	public void getExcelContent(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Workbook workbook = null;
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
			MultipartFile mf = mr.getFile(mr.getFileNames().next());
			if (mf.getOriginalFilename().endsWith(".xls")) {
				workbook = new HSSFWorkbook(mf.getInputStream());
			} else if (mf.getOriginalFilename().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(mf.getInputStream());
			} else {
				String msg = "请选择Excel文件！";
				resultMap.put("msg", msg);
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
			}
			Map<String, List<List<String>>> excelmap = new HashMap<String, List<List<String>>>();
			List<String> sheetNameList = new ArrayList<String>();
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取所有Sheet表，生成list集合传到页面
				List<List<String>> sheetList = new ArrayList<List<String>>();
				Sheet sheet = workbook.getSheetAt(i);
				if (sheet.getPhysicalNumberOfRows() != 0) {
					sheetNameList.add(sheet.getSheetName());
				}
				int rowStart = sheet.getFirstRowNum();
				for (int j = rowStart; j < sheet.getPhysicalNumberOfRows(); j++) {// 获取每一行数据
					int lastColumn = sheet.getRow(rowStart).getLastCellNum();// 最大列数按表头列数算
					Row row = sheet.getRow(j);
					List<String> rowList = new ArrayList<String>();
					for (int k = 0; k < lastColumn; k++) {
						Cell c = row.getCell(k, Row.RETURN_BLANK_AS_NULL);
						if (c != null) {
							if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								rowList.add(String.valueOf((int) c.getNumericCellValue()));
							} else {
								c.setCellType(Cell.CELL_TYPE_STRING);
								String value = c.getStringCellValue();
								rowList.add(value);
							}

						} else {
							rowList.add("");
						}
					}
					sheetList.add(rowList);
				}

				excelmap.put(sheet.getSheetName(), sheetList);
			}
			resultMap.put("msg", "1");
			resultMap.put("excelmap", excelmap);
			resultMap.put("sheetNameList", sheetNameList);
			String jsonString = JSON.toJSONString(resultMap);
			outputMsg(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查excel数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkExcelData.do")
	public void checkExcelData(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String jsonData = request.getParameter("jsonData");
			List<Map<String, String>> dataList = (List<Map<String, String>>) JSON.parse(jsonData);
			if (dataList == null || dataList.size() == 0) {
				resultMap.put("msg", "无数据！");
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
			}
			String indexStr = ",";
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, String> map = dataList.get(i);
				Iterator<String> ite = map.keySet().iterator();
				StringBuffer sql = new StringBuffer("select count(1) from part_data where 1=1 ");
				while (ite.hasNext()) {
					String filedName = ite.next();
					String value = map.get(filedName);
					if(filedName.equals("PartNumber")){
						sql.append("and PartCode='" + value + "' ");
					}else{
						sql.append("and " + filedName + "='" + value + "' ");
					}
				}
				long l = iPartDataDao.countPartData(sql.toString());
				if (l == 0) {// 记录查不到的数据
					indexStr += i;
					indexStr += ",";
				}
			}
			resultMap.put("msg", "1");
			resultMap.put("indexStr", indexStr);
			String jsonString = JSON.toJSONString(resultMap);
			outputMsg(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存检查excel数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/saveProductBomPn.do")
	public void saveProductBomPn(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String jsonData = request.getParameter("dataMap");
			String bomName = request.getParameter("bomName");
			String productId = request.getParameter("productId");
			Map<String, Object> dataMap = (Map<String, Object>) JSON.parse(jsonData);
			if (dataMap == null || dataMap.size() == 0) {
				resultMap.put("msg", "无数据！");
				String jsonString = JSON.toJSONString(resultMap);
				outputMsg(response, jsonString);
			}

			// 保存数据
			Iterator<String> ite = dataMap.keySet().iterator();
			// String[] ss=excelName.split("\\\\");
			// String ns[]=ss[ss.length-1].split(".xl");
			// String name=ns[0];
			while (ite.hasNext()) {
				// 一个产品下验证文件名，如果存在就加上版本号
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("excelName", "'"+bomName + "\\_%"+"'");
				paramMap.put("productId", productId);
				List<ProductBomPn> l = partDataService.checkProductBomByExcelName(paramMap);
				String version = "01";
				Map<Integer, String> map = getVersion();
				if (l.size() > 0) {
					String s = l.get(0).getVersion();
					int a = Integer.valueOf(s) + 1;
					if (a < 10) {
						version = "0" + String.valueOf(a);
					} else {
						version = String.valueOf(a);
					}

				}
				// 保存数据
				String key = ite.next();
				JSONArray ja = (JSONArray) dataMap.get(key);
				String js = JSON.toJSONString(ja);
				List<ProductBomPn> pl = JSON.parseArray(js, ProductBomPn.class);
				for (int i = 0; i < pl.size(); i++) {
					ProductBomPn productBomPn = pl.get(i);
					productBomPn.setVersion(version);
					productBomPn.setExcelName(bomName + "_" + map.get(Integer.valueOf(version)) + ".xls");
					productBomPn.setProductId(Integer.valueOf(productId));
					partDataService.insertproductBomPn(productBomPn);
				}
			}
			resultMap.put("msg", "1");
			String jsonString = JSON.toJSONString(resultMap);
			outputMsg(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * version-Map(确定后版本数据可以存到数据库)
	 * 
	 * @return
	 */
	public HashMap<Integer, String> getVersion() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		for (int k = 0; k <= 26; k++) {
			int j = k >= 1 ? k : 0;
			for (int i = 1; i <= 26; i++) {
				if (j >= 1)
					hm.put(k * 26 + i, ((char) (j + 64)) + "" + ((char) (i + 64)));
				else
					hm.put(k * 26 + i, ((char) (i + 64)) + "");
			}
		}
		return hm;
	}

	/**
	 * 通过产品ID分页查询元器件数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectProductBomByBomPn.do")
	public void selectProductBomByBomPn(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> mapSql = new HashMap<String, Object>();
			Map<String, Object> mapJson = new HashMap<String, Object>();
			String lang = "zh";
			if (null != request.getSession().getAttribute("lang")) {
				lang = request.getSession().getAttribute("lang").toString();
			}
			PageInfo<Map<String, Object>> page = null;
			String pageNo = request.getParameter("pageNo");

			// 分页初始化
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = "1";
			}
			String pageSize = "5";
			String productId = request.getParameter("productId");
			String excelName = request.getParameter("excelName");
			mapSql.put("productID", productId);
			List<Map<Object, Object>> bomList = new ArrayList<Map<Object, Object>>();
			List<Map<String, Object>> showNameList = partPrimaryAttributesService.selectTableField();
			if (StringUtils.isNotEmpty(excelName)) {
				mapSql.put("excelName", excelName);
			} else {
				bomList = bomInformationAdd(bomList, Long.valueOf(productId));
				if (null == bomList || bomList.size() <= 0) {
					mapJson.put("title", showNameList);
					mapJson.put("lang", lang);
					mapJson.put("list", null);
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
					return;
				}
				mapSql.put("excelName", bomList.get(0).get("name"));
			}

			PageHelper.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
			List<Map<String, Object>> dataList = partDataService.selectProductBomByBomPn(mapSql);
			if (dataList.size() > 0) {
				page = new PageInfo<Map<String, Object>>(dataList);
			}
			if (dataList.size() > 0) {
				mapJson.put("title", showNameList);
				mapJson.put("lang", lang);
				mapJson.put("list", page.getList());
				mapJson.put("count", page.getTotal());
				mapJson.put("bomList", bomList);
				mapJson.put("pageNo", pageNo);
				mapJson.put("pageSize", pageSize);
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			} else {
				mapJson.put("title", showNameList);
				mapJson.put("lang", lang);
				mapJson.put("list", null);
				mapJson.put("pageNo", pageNo);
				mapJson.put("pageSize", pageSize);
				mapJson.put("count", 0);
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单板下bom数据数据挂接
	 */
	public List<Map<Object, Object>> bomInformationAdd(List<Map<Object, Object>> list, long id) {
		Map<String, Object> paramMapj = new HashMap<String, Object>();
		List<Map> tempList = new ArrayList<Map>();
		paramMapj.put("id", id);
		List<Map<String, Object>> bomPnList = iProductBomService.bomPnList(paramMapj);
		for (int x = 0; x < bomPnList.size(); x++) {
			Map<Object, Object> mapObj = new HashMap<Object, Object>();
			mapObj.put("id", id + Long.valueOf(bomPnList.get(x).get("id").toString()));
			mapObj.put("pId", id);
			mapObj.put("name", bomPnList.get(x).get("excel_name").toString());
			mapObj.put("realId", bomPnList.get(x).get("id").toString());
			mapObj.put("nodeStage", "3");
			tempList.add(mapObj);
		}
		Collections.<Map> sort(tempList, new BeanComparator("realId"));// 进行排序
		list.addAll((Collection<? extends Map<Object, Object>>) tempList);
		return list;
	}

	@RequestMapping(value = "/deleteProductBomPn.do")
	public void deleteProductBomPn(HttpServletRequest request, HttpServletResponse response) {
		String ids = request.getParameter("ids");
		try {
			List<Object> idList = JSON.parseArray(ids);
			if (idList.size() > 0) {
				for (int x = 0; x < idList.size(); x++) {
					ProductBomPn productBomPn = new ProductBomPn();
					productBomPn.setId(Integer.valueOf((String) idList.get(x)));
					partDataService.deleteProductBomPn(productBomPn);
				}
			}
			String msg = JSONObject.fromObject(null).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/saveProBomRemark.do")
	public void saveProBomRemark(HttpServletRequest request, HttpServletResponse response) {
		String jsonData = request.getParameter("jsonData");
		try {
			List<ProductBomPn> list = JSON.parseArray(jsonData, ProductBomPn.class);
			if (list.size() > 0) {
				for (int x = 0; x < list.size(); x++) {
					ProductBomPn productBomPn = list.get(x);
					partDataService.updateProductBomPn(productBomPn);
				}
			}
			String msg = JSONObject.fromObject(null).toString();
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/initOutBomWindow.do")
	public void initOutBomWindow(HttpServletRequest request, HttpServletResponse response) {
		String productId = request.getParameter("productId");
		String excelName = request.getParameter("excelName");
		String outType = request.getParameter("outType");// 导出类型
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isEmpty(outType)) {
			return;
		}
		try {
			// 查询导出数据
			ProductBomPn productBomPn = new ProductBomPn();
			productBomPn.setProductId(Integer.valueOf(productId));
			productBomPn.setExcelName(excelName);
			if (outType.equals("Single")) {// 普通导出
				List<Map<String, Object>> pList = partDataService.selectAllfieldFromProductBomPn(productBomPn);
				resultMap.put("pList", pList);
				
			} else {// 相同物料编码位号合并导出
				List<Map<String, Object>> partCodeList=partDataService.selectPartCodeByBomPn(productBomPn);
				List<Map<String, Object>> rList=new ArrayList<Map<String, Object>>();
				for(int i=0;i<partCodeList.size();i++){
					if(partCodeList.get(i)==null){
						continue;
					}
					productBomPn.setPartCode((String) partCodeList.get(i).get("PartCode"));
					List<Map<String, Object>> cList=partDataService.selectfieldByCode(productBomPn);
					if(cList.size()>1){
						List<String> reList=new ArrayList<String>();
						for(int j=0;j<cList.size();j++){
							String s=(String)cList.get(j).get("reference");
							if(StringUtils.isNotEmpty(s)){
								reList.add(s);
							}
						}
						String reference="";
						if(reList.size()!=0){
							reference=FormatString.numberToString(reList);
						}
						
						Map<String, Object> data=(Map<String, Object>) cList.get(0);
						data.put("reference", reference);
						rList.add(data);
					}else{
						Map<String, Object> data=(Map<String, Object>) cList.get(0);
						rList.add(data);
					}
				}
				resultMap.put("pList", rList);
			}
			// 查询表头字段
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<PartPrimaryAttributes> fieldList = partPrimaryAttributesService.selectUsedField(paramMap);
			PartPrimaryAttributes p = new PartPrimaryAttributes();
			p.setShowName("备注");
			p.setFieldName("remark");
			fieldList.add(p);
			resultMap.put("fieldList", fieldList);
			String msg = JSON.toJSONString(resultMap);
			outputJson(response, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * partData导出
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelData.do")
	public void exportExcelData(HttpServletRequest request, HttpServletResponse response) {
		try {
			String excelName = request.getParameter("excelName");
			String showNameStr = request.getParameter("showName");
			String fieldNameStr = request.getParameter("fieldName");
			String exportData = request.getParameter("exportData");
			List<String> showName = JSON.parseArray(showNameStr, String.class);
			List<String> fieldName = JSON.parseArray(fieldNameStr, String.class);
			String[] a = new String[showName.size()];
			String[] b = new String[fieldName.size()];
			List<Object> list = JSON.parseArray(exportData);
			HSSFWorkbook wb = (new ExportExcel()).exportBomExcel(list, showName.toArray(a), fieldName.toArray(b));
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=\"" + new String(excelName.getBytes("gb2312"), "ISO8859-1") + "\"");
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