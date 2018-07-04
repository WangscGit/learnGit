package com.cms_cloudy.product.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/productManageController")
public class ProductManageController extends BaseController {

	@Autowired
	private IProductBomService iProductBomService;
	@Autowired
	private IPartDataService partDataService;
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
				HashMap<String, String> varsionMap = getVersion();
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
					if (2 == bom.getNodeStage()) {
						bomInformationAdd(list, bom.getId());
						mapObj.put("name", bom.getProductName() + "【"+varsionMap.get(bom.getVersion())+"】");
					}
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
		String excelName = request.getParameter("excelName");
		mapSql.put("productID", productId);
		try {
			List<Map<String, Object>> showNameList = partPrimaryAttributesService.selectTableField();
			List<Map<Object, Object>> bomList = new ArrayList<Map<Object, Object>>();
			if (StringUtils.isEmpty(excelName)) {
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
			} else {
				mapSql.put("excelName", excelName);
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
				mapJson.put("bomList", bomList);
				mapJson.put("count", page.getTotal());
				mapJson.put("pageNo", pageNo);
				mapJson.put("pageSize", pageSize);
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			} else {
				mapJson.put("title", showNameList);
				mapJson.put("lang", lang);
				mapJson.put("bomList", null);
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
				mapSql.put("productName", boardProductName);
				mapSql.put("parentId", parentId);
				mapSql.put("nodeStage", nodeLevel);
				mapSql2.put("productNo", productNo);
				mapSql2.put("parentId", parentId);
				mapSql2.put("nodeStage", nodeLevel);
				List<ProductBomEntity> listBoard = iProductBomService.selectProductBomList(mapSql);
				List<ProductBomEntity> listBoard2 = iProductBomService.selectProductBomList(mapSql2);
				// if (listBoard.size() > 0) {
				// mapJson.put("result", "nameRep");
				// String msg = JSONObject.fromObject(mapJson).toString();
				// outputJson(response, msg);
				// return;
				// }
				if (listBoard2.size() > 0) {
					mapJson.put("result", "noRep");
					String msg = JSONObject.fromObject(mapJson).toString();
					outputJson(response, msg);
					return;
				}
				if (listBoard.size() > 0) {
					ProductBomEntity bomEnd = listBoard.get(listBoard.size() - 1);
					int versionCut = Integer.valueOf(bomEnd.getVersion());
					versionCut++;
					version = String.valueOf(versionCut);
				} else {
					version = "1";
				}
				bom.setProductName(boardProductName);
				bom.setProductNo(productNo);
				bom.setProductCode(productCode);
				bom.setVersion(version);
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
				if (!bom.getProductName().equals(productName)) {// 修改的产品名称是否有变化
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
				if (boardProductName.equals(bom.getProductName())) {// 修改的单板名称是否有变化
					if (productNo.equals(bom.getProductNo())) {// 修改的产品代号是否有变化
						bom.setProductCode(productCode);
						bom.setProductNo(productNo);
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
					Map<String, Object> mapVersion = new HashMap<String, Object>();
					mapVersion.put("productName", boardProductName);
					mapVersion.put("parentId", bom.getParentId());
					mapVersion.put("nodeStage", "2");
					versionSync(mapVersion);
				} else {
					String name = bom.getProductName();
					if (productNo.equals(bom.getProductNo())) {
						mapSql3.put("productName", boardProductName);
						mapSql3.put("parentId", bom.getParentId());
						mapSql3.put("nodeStage", "2");
						bom.setProductName(boardProductName);
						bom.setProductCode(productCode);
						bom.setProductNo(productNo);
						bom.setProductStage(productStage);
						bom.setpTool(pTool);
						iProductBomService.updateProductBom(bom);
					} else {
						mapSql2.put("productName", boardProductName);
						mapSql2.put("parentId", bom.getParentId());
						mapSql2.put("nodeStage", "2");
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
							ProductBomEntity bomEnd = list.get(list.size() - 1);
							int versionCut = Integer.valueOf(bomEnd.getVersion());
							versionCut++;
							version = String.valueOf(versionCut);
						} else {
							version = "1";
						}
						bom.setProductName(boardProductName);
						bom.setProductCode(productCode);
						bom.setProductNo(productNo);
						bom.setVersion(version);
						bom.setProductStage(productStage);
						bom.setpTool(pTool);
						iProductBomService.updateProductBom(bom);
					}
					// 同步版本结束
					Map<String, Object> mapVersion = new HashMap<String, Object>();
					mapVersion.put("productName", name);
					mapVersion.put("parentId", bom.getParentId());
					mapVersion.put("nodeStage", "2");
					versionSync(mapVersion);
					mapVersion.put("productName", boardProductName);
					versionSync(mapVersion);
					// 同步版本结束
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
		Map<String, Object> mapVersion = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String nodeStage = request.getParameter("nodeStage");
		String parentNodeId = request.getParameter("parentNodeId");
		String productName = "";
		long parentId = 0;
		try {
			if (nodeStage.equals("3")) {// 点击末级节点(挂接在产品结构末级节点下的BOM)进行BOM删除
				Map<String, Object> delMap = new HashMap<String, Object>();
				delMap.put("excelName", id);
				delMap.put("productID", parentNodeId);
				bomPnVersionSync(delMap);
				
				
			} else {
				// 查询该节点下是否有子节点
				Map<String, Object> mapObject = new HashMap<String, Object>();
				mapObject.put("parentId", id);
				List<ProductBomEntity> childTempList = iProductBomService.selectAllChildData(mapObject);
				if (childTempList.size() > 0) {// 有子节点
					for (ProductBomEntity bomId : childTempList) {
						// 先删除关系表productBomPn
						Map<String, Object> delMap = new HashMap<String, Object>();
						delMap.put("excelName", "");
						delMap.put("productID", bomId.getId());
						iProductBomService.deleteProductBomPnByCondition(delMap);// 产品节点下BOM删除
						// 删除bom表
						iProductBomService.deleteProductBom(Integer.valueOf(bomId.getId() + ""));
					}
					// 删除父节点bom表
					iProductBomService.deleteProductBom(Integer.valueOf(id));
				} else {// 产品结构末级节点(不包括挂接的BOM节点)
					// 查询选择节点BOM信息
					mapVersion.put("id", id);
					List<ProductBomEntity> listCheck = iProductBomService.selectProductBomList(mapVersion);
					if (listCheck.get(0).getNodeStage() != 1) {// 判断是否是根节点
						productName = listCheck.get(0).getProductName();
						parentId = listCheck.get(0).getParentId();
						Map<String, Object> delMap = new HashMap<String, Object>();
						delMap.put("excelName", "");
						delMap.put("productID", id);
						iProductBomService.deleteProductBomPnByCondition(delMap);// 产品节点下BOM删除
					}
					iProductBomService.deleteProductBom(Integer.valueOf(id));// 产品节点删除
				}
			}
			if (nodeStage.equals("2")) {// 删除的是单板节点才需要版本同步
				Map<String, Object> mapResetV = new HashMap<String, Object>();
				// 同步版本
				mapResetV.put("productName", productName);
				mapResetV.put("parentId", parentId);
				mapResetV.put("nodeStage", "2");
				versionSync(mapResetV);
			}
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
		String partNumber = request.getParameter("partNumber");
		try {
			mapSql.put("partNumber", partNumber);
			List<Map<String, Object>> list = partDataService.selectDataByPartNumber(mapSql);
			if (list.size() > 0) {
				int count = 0;
				String[] ids = new String[list.size()];
				int i = 0;
				for (Map<String, Object> id : list) {
					ids[i] = id.get("productID").toString();
					count += Integer.valueOf(id.get("Numbers").toString());
					i++;
				}
				List<Map<String, Object>> bomList = iProductBomService.selectAllParent(ids);
				Set<Map<String, Object>> lPersonSet = new HashSet<Map<String, Object>>();
				lPersonSet.addAll(bomList);
				bomList.clear();
				bomList.addAll(lPersonSet);
				List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
				// List<ProductBomEntity> childList = new
				// ArrayList<ProductBomEntity>();
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
						if (4 == childs.getNodeStage()) {
							for (Map<String, Object> lists : list) {
								// [{"name":"刘作虎","children":[{"name":"周鸿祎"},{"name":"周鸿祎"},{"name":"周鸿祎"}]}]
								if (childs.getId() == Long.valueOf(lists.get("productID").toString())) {
									Map<String, Object> mapChilds = new HashMap<String, Object>();
									mapChilds.put("name", childs.getProductName() + "-" + childs.getVersion() + "[qty:"
											+ lists.get("Numbers") + "]");
									listChild.add(mapChilds);
									qty += Integer.valueOf(lists.get("Numbers").toString());
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
				String msg = JSONObject.fromObject(mapJson).toString();
				outputJson(response, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 刪除BOM信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteDataFrombom.do")
	public void deleteDataFrombom(HttpServletRequest request, HttpServletResponse response) {
		String ids = request.getParameter("ids");
		ProductBomPn pojo = new ProductBomPn();
		try {
			List<Object> idList = JSON.parseArray(ids);
			for (int x = 0; x < idList.size(); x++) {
				pojo.setId(Integer.valueOf(idList.get(x).toString()));
				partDataService.deleteProductBomPn(pojo);
			}
			String msg = JSONObject.fromObject(null).toString();
			outputJson(response, msg);
		} catch (Exception e) {
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

	/**
	 * bom对比页面初始化
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/initCompareBomList.do")
	public void initCompareBomList(HttpServletRequest request, HttpServletResponse response) {
		String bomList = request.getParameter("bomList");
		String lang = "zh";
		if (null != request.getSession().getAttribute("lang")) {
			lang = request.getSession().getAttribute("lang").toString();
		}
		List<String> partNumberList1 = new ArrayList<String>();// 物料编码存储---用于BOM对比
		List<String> partReferenceList1 = new ArrayList<String>();// 物料编码存储---用于BOM对比
		List<String> partNumberList2 = new ArrayList<String>();// 物料编码存储---用于BOM对比
		List<String> partReferenceList2 = new ArrayList<String>();// 物料编码存储---用于BOM对比
		List<List<Map<String, Object>>> list = new ArrayList<List<Map<String, Object>>>();// 存储BOM集合
		try {
			List<Object> jsonData = JSON.parseArray(bomList);
			for (int s = 0; s < jsonData.size(); s++) {
				Map<String, String> map = (Map<String, String>) jsonData.get(s);
				List<Map<String, Object>> paramList = iProductBomService.selectCompareBomList(map);
				if (null != paramList) {
					list.add(paramList);
					if (s == 0) {
						for (int x = 0; x < paramList.size(); x++) {
							String pn = paramList.get(x).get("ptNumber").toString();
							String pc  = null==paramList.get(x).get("PartCode")||"".equals(paramList.get(x).get("PartCode"))?"isNull":paramList.get(x).get("PartCode").toString();
							String pf  = null==paramList.get(x).get("Part_Reference")||"".equals(paramList.get(x).get("Part_Reference"))?"isNull":paramList.get(x).get("Part_Reference").toString();
							partNumberList1.add(pn + "*" + pc);
							partReferenceList1.add(pn + "*" + pf);
						}
					} else {
						for (int x = 0; x < paramList.size(); x++) {
							String pn = paramList.get(x).get("ptNumber").toString();
							String pc  = null==paramList.get(x).get("PartCode")||"".equals(paramList.get(x).get("PartCode"))?"isNull":paramList.get(x).get("PartCode").toString();
							String pf  = null==paramList.get(x).get("Part_Reference")||"".equals(paramList.get(x).get("Part_Reference"))?"isNull":paramList.get(x).get("Part_Reference").toString();
							partNumberList2.add(pn + "*" + pc);
							partReferenceList2.add(pn + "*" + pf);
						}
					}
				}
			}
//			Collection partNumberCollection = getDiffent(partNumberList1, partNumberList2);
			List<String> partReferenceCollection = compareRReference(
					new ArrayList<String>(new HashSet(partReferenceList1)),
					new ArrayList<String>(new HashSet(partReferenceList2)));
			List<String> partNumberCollection = compareRReference(
					new ArrayList<String>(new HashSet(partNumberList1)),
					new ArrayList<String>(new HashSet(partNumberList2)));
			Set partNumberList = new HashSet(partNumberCollection);
			Set partReferenceList = new HashSet(partReferenceCollection);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("list", list);
			resultMap.put("partNumberList", partNumberList);
			resultMap.put("partReferenceList", partReferenceList);
			resultMap.put("size", list.size());
			resultMap.put("lang", lang);
			String jsonString = JSON.toJSONString(resultMap);
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * bom对比页面初始化
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/compareBomByCondition.do")
	public void compareBomByCondition(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		String bomCollection = request.getParameter("bomCollection");
		List<String> result = new ArrayList<String>();
		try {
			List<String> jsonList = JSON.parseArray(bomCollection, String.class);
			for (int x = 0; x < jsonList.size(); x++) {
				result.add(jsonList.get(x).split("\\*")[0]);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("filterField", result);
			String jsonString;
			if (result == null || result.size() == 0) {
				jsonString = JSON.toJSONString(null);
			} else {
				List<Map<String, Object>> resultList = partPrimaryAttributesService.selectCompareBom(map);
				jsonString = JSON.toJSONString(resultList);
			}
			outputJson(response, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Collection getDiffent(Collection collmax, Collection collmin) {
		// 使用LinkeList防止差异过大时,元素拷贝
		Collection csReturn = new LinkedList();
		Collection max = collmax;
		Collection min = collmin;
		// 先比较大小,这样会减少后续map的if判断次数
		if (collmax.size() < collmin.size()) {
			max = collmin;
			min = collmax;
		}
		// 直接指定大小,防止再散列
		Map<Object, Integer> map = new HashMap<Object, Integer>(max.size());
		for (Object object : max) {
			map.put(object, 1);
		}
		for (Object object : min) {
			if (map.get(object) == null) {
				csReturn.add(object);
			} else {
				map.put(object, 2);
			}
		}
		for (Map.Entry<Object, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				csReturn.add(entry.getKey());
			}
		}
		return csReturn;
	}

	public List<String> compareRReference(List<String> list1, List<String> list2) {
		List<String> resultList = new ArrayList<String>();
		if (list1.size() == 0 || list2.size() == 0) {
			resultList.addAll(list1);
			resultList.addAll(list2);
		} else {
			for (int s = 0; s < list1.size(); s++) {
				String str1 = list1.get(s).split("\\*")[1];
				boolean flag = false;
				for (int x = 0; x < list2.size(); x++) {
					String str2 = list2.get(x).split("\\*")[1];
					if (str1.equals(str2)) {
						flag = true;
					}
				}
				if (flag == false) {
					resultList.add(list1.get(s));
				}
			}
			for (int s = 0; s < list2.size(); s++) {
				String str1 = list2.get(s).split("\\*")[1];
				boolean flag = false;
				for (int x = 0; x < list1.size(); x++) {
					String str2 = list1.get(x).split("\\*")[1];
					if (str1.equals(str2)) {
						flag = true;
					}
				}
				if (flag == false) {
					resultList.add(list2.get(s));
				}
			}
		}
		return resultList;
	}

	/**
	 * 导出对比结果
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportCompareResult.do")
	public void exportCompareResult(HttpServletRequest request, HttpServletResponse response) {
		String result = request.getParameter("resultList");
		List<Map> resultList = JSON.parseArray(result, Map.class);
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("dataList", resultList);
			resultMap.put("fieldName", "PartCode,Item,Part_Reference,Manufacturer,Value");
			resultMap.put("showName", "物料编码,规格型号,元器件符号,制造商,Value");
			HSSFWorkbook wb = (new ExportExcel()).exportExcel(resultMap);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=\"" + new String("BOM对比信息".getBytes("gb2312"), "ISO8859-1") + ".xls" + "\"");
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
	 * version-Map(确定后版本数据可以存到数据库)
	 * 
	 * @return
	 */
	public HashMap<String, String> getVersion() {
		HashMap<String, String> hm = new HashMap<String, String>();
		for (int k = 0; k <= 26; k++) {
			int j = k >= 1 ? k : 0;
			for (int i = 1; i <= 26; i++) {
				if (j >= 1)
					hm.put(k * 26 + i + "", ((char) (j + 64)) + "" + ((char) (i + 64)));
				else
					hm.put(k * 26 + i + "", ((char) (i + 64)) + "");
			}
		}
		return hm;
	}

	/**
	 * 产品单板执行修改或者删除操作后进行版本同步
	 * 
	 * @param map
	 */
	public void versionSync(Map<String, Object> map) {
		List<ProductBomEntity> listOther = iProductBomService.selectProductBomList(map);
		if (listOther.size() > 0) {
			for (int xx = 0; xx < listOther.size(); xx++) {
				ProductBomEntity bomEnd = listOther.get(xx);
				bomEnd.setVersion(String.valueOf(xx + 1));
				iProductBomService.updateProductBom(bomEnd);
			}
		}
	}

/**
 * bom文件执行修改或者删除操作后进行版本同步
 * 
 * @param map
 */
public void bomPnVersionSync(Map<String, Object> map) {
	Map<String,String> paramMap=new HashMap<String,String>();
	paramMap.put("excelName","'"+(String) map.get("excelName")+"'" );
	paramMap.put("productId", (String) map.get("productID"));
	List<ProductBomPn> listOther = partDataService.selectVersion(paramMap);//要删除的文件及内容
	
	if(listOther==null||listOther.size()==0){
		return;
	}
	String name=((String) map.get("excelName")).split(".xls")[0];
	String []ns=name.split("_");
	String newName=name.substring(0, name.length()-ns[ns.length-1].length()-1);
	paramMap.put("excelName","'"+newName+"\\_%'");
	paramMap.put("version", listOther.get(0).getVersion());
	paramMap.put("escape", "escape");
	List<ProductBomPn> list=partDataService.selectVersion(paramMap);
	iProductBomService.deleteProductBomPnByCondition(map);//删除
	for(int i=0;i<list.size();i++){
		ProductBomPn pbp=list.get(i);
		int ver=Integer.valueOf(pbp.getVersion())-1;
		Map<String,String> vmap=getVersion();
		pbp.setExcelName(newName+"_"+vmap.get(ver+"")+".xls");
		if(ver<10){
			pbp.setVersion("0"+ver);
		}else{
			pbp.setVersion(ver+"");
		}
		partDataService.updatepbpVersion(pbp);
	}
//	if (listOther.size() > 0) {
//		for (int xx = 0; xx < listOther.size(); xx++) {
//			ProductBomEntity bomEnd = listOther.get(xx);
//			bomEnd.setVersion(String.valueOf(xx + 1));
//			iProductBomService.updateProductBom(bomEnd);
//		}
//	}
}
}
// Set<String> set = new HashSet<String>();// 物料编码去重
// set.addAll(jsonList);
// HashMap<String, Integer> hs = new HashMap<String, Integer>();
// for (int x = 0; x < jsonList.size(); x++) {
// String string = jsonList.get(x);
// Integer count = 1;
// if (hs.get(string) != null) {
// count = hs.get(string) + 1;
// }
// if (count == Integer.valueOf(bomSize)) {// BOM相同项
// set.remove(string);
// }
// hs.put(string, count);
// }