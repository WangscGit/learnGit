package com.cms_cloudy.product.pojo;

import java.util.Date;

/**产品bom主表**/
public class ProductBomEntity {

	private long id;
	/**
	 * 父节点ID
	 */
	private long parentId;
	/**
	 * 节点阶段
	 */
	private int nodeStage;
	/**
	 * 物料编码
	 */
	private String partNumber;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品型号
	 */
	private String productModel;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 产品代号
	 */
	private String productNo;
	/**
	 * 产品图号
	 */
	private String productCode;
	/**
	 * 产品阶段
	 */
	private String productStage;
	/**
	 * 生产数量
	 */
	private int number;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 接收人
	 */
	private String receiver;
	/**
	 * 接收日期
	 */
	private Date receiveTime;
	/**
	 * 创建人
	 */
	private String createuser;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 修改人
	 */
	private String modifyuser;
	/**
	 * 修改日期
	 */
	private Date modifyDate;
	/**
	 * EDA工具
	 */
	private String pTool;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public int getNodeStage() {
		return nodeStage;
	}
	public void setNodeStage(int nodeStage) {
		this.nodeStage = nodeStage;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductModel() {
		return productModel;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductStage() {
		return productStage;
	}
	public void setProductStage(String productStage) {
		this.productStage = productStage;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getModifyuser() {
		return modifyuser;
	}
	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getpTool() {
		return pTool;
	}
	public void setpTool(String pTool) {
		this.pTool = pTool;
	}
}
