package com.cms_cloudy.pojo;


public class PartDataExcel {
	/**
	 * 物料编码
	 */
	private String PartNumber;
	/**
	 * 细目
	 */
	private  String Part_Type;
	/**
	 * 型号规格
	 */
	private String ITEM;
	/**
	 * 编号前缀
	 */
	private String Part_Reference;
	/**
	 * value
	 */
	private String Value;
	/**
	 * 国产/进口
	 */
	private String Country;
	/**
	 * 数量
	 */
	private String Numbers;
//	pd.ID,pd.,pd.,pd.,pd.,pd.,pd.,pp.
	public PartDataExcel(String string, String string2, String string3,
			String string4, String string5, String string6,String string7) {
		this.PartNumber=string;
		 this.Part_Type=string2;
		this.ITEM=string3;
		this.Part_Reference=string4;
		this.Value=string5;
		this.Country=string6;
		this.Numbers=string7;
	}
	public String getPartNumber() {
		return PartNumber;
	}
	public void setPartNumber(String partNumber) {
		PartNumber = partNumber;
	}
	public String getPart_Type() {
		return Part_Type;
	}
	public void setPart_Type(String part_Type) {
		Part_Type = part_Type;
	}
	public String getITEM() {
		return ITEM;
	}
	public void setITEM(String iTEM) {
		ITEM = iTEM;
	}
	public String getPart_Reference() {
		return Part_Reference;
	}
	public void setPart_Reference(String part_Reference) {
		Part_Reference = part_Reference;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getNumbers() {
		return Numbers;
	}
	public void setNumbers(String numbers) {
		Numbers = numbers;
	}
}
