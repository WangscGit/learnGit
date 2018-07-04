package com.cms_cloudy.product.pojo;

import java.util.Date;
//产品元器件关系表
public class ProductPn {
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
	//添加次数--同一个产品同一个元器件添加一次+1
	private int coun;
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
	public int getCoun() {
		return coun;
	}
	public void setCoun(int coun) {
		this.coun = coun;
	}
	
}
