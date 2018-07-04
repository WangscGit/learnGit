package com.cms_cloudy.product.pojo;
import java.util.Date;
//产品元器件关系表
public class ProductBomPn {
		//id
		private int id;
		//产品id
		private int productId;
		//物料编码
		private String partNumber;
		//数量
		private int numbers;
		//选择时间
		private Date selectedTime;
		//excel名称
		private String excelName;
		//excel路径
		private String excelUrl;
		//备注
		private String remark;
		//sheet名
		private String sheetName;
		
		private String version;
		//冗余字段
		//位号
		private String reference;
		//物料编码--非partNumber
		private String partCode;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getProductId() {
			return productId;
		}
		public void setProductId(int productId) {
			this.productId = productId;
		}
		public String getPartNumber() {
			return partNumber;
		}
		public void setPartNumber(String partNumber) {
			this.partNumber = partNumber;
		}
		public int getNumbers() {
			return numbers;
		}
		public void setNumbers(int numbers) {
			this.numbers = numbers;
		}
		public Date getSelectedTime() {
			return selectedTime;
		}
		public void setSelectedTime(Date selectedTime) {
			this.selectedTime = selectedTime;
		}
		public String getExcelName() {
			return excelName;
		}
		public void setExcelName(String excelName) {
			this.excelName = excelName;
		}
		public String getExcelUrl() {
			return excelUrl;
		}
		public void setExcelUrl(String excelUrl) {
			this.excelUrl = excelUrl;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getSheetName() {
			return sheetName;
		}
		public void setSheetName(String sheetName) {
			this.sheetName = sheetName;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getReference() {
			return reference;
		}
		public void setReference(String reference) {
			this.reference = reference;
		}
		public String getPartCode() {
			return partCode;
		}
		public void setPartCode(String partCode) {
			this.partCode = partCode;
		}
		
}
