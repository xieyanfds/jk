package com.xy.domain;

import java.util.Date;

/**
 * @author xieyan
 * @description 产品
 * @date 2017/12/26.
 */

public class Product extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private String productNo;			//编号
	private String productImage;		//照片	
	private String description;			//描述
				
	private String factoryName;			//厂家简称
	private Double price;				//市场价
	private Double sizeLenght;			//长
	private Double sizeWidth;			//宽
	private Double sizeHeight;			//高
	private String color;				//颜色
	private String packing;				//包装
	private String packingUnit;			//包装单位
	private String type;				//集装箱类别
	private Double qty;					//数量
	private Double cbm;					//体积
	private Double mpsizeLenght;		//大箱尺寸长
	private Double mpsizeWidth;			//大箱尺寸宽
	private Double mpsizeHeight;		//大箱尺寸高
	private String remark;				//备注
	private Date inputTime;				//录入时间
	
	private Factory factory;			//厂家(外键),多对一
	
	
	public Factory getFactory() {
		return factory;
	}
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProductNo() {
		return this.productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}	
	
	public String getProductImage() {
		return this.productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}	
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	
	public String getFactoryName() {
		return this.factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}	
	
	public Double getPrice() {
		return this.price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}	
	
	public Double getSizeLenght() {
		return this.sizeLenght;
	}
	public void setSizeLenght(Double sizeLenght) {
		this.sizeLenght = sizeLenght;
	}	
	
	public Double getSizeWidth() {
		return this.sizeWidth;
	}
	public void setSizeWidth(Double sizeWidth) {
		this.sizeWidth = sizeWidth;
	}	
	
	public Double getSizeHeight() {
		return this.sizeHeight;
	}
	public void setSizeHeight(Double sizeHeight) {
		this.sizeHeight = sizeHeight;
	}	
	
	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}	
	
	public String getPacking() {
		return this.packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}	
	
	public String getPackingUnit() {
		return this.packingUnit;
	}
	public void setPackingUnit(String packingUnit) {
		this.packingUnit = packingUnit;
	}	
	
	public Double getQty() {
		return this.qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}	
	
	public Double getCbm() {
		return this.cbm;
	}
	public void setCbm(Double cbm) {
		this.cbm = cbm;
	}	
	
	public Double getMpsizeLenght() {
		return this.mpsizeLenght;
	}
	public void setMpsizeLenght(Double mpsizeLenght) {
		this.mpsizeLenght = mpsizeLenght;
	}	
	
	public Double getMpsizeWidth() {
		return this.mpsizeWidth;
	}
	public void setMpsizeWidth(Double mpsizeWidth) {
		this.mpsizeWidth = mpsizeWidth;
	}	
	
	public Double getMpsizeHeight() {
		return this.mpsizeHeight;
	}
	public void setMpsizeHeight(Double mpsizeHeight) {
		this.mpsizeHeight = mpsizeHeight;
	}	
	
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
	public Date getInputTime() {
		return this.inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}	
	
	
	
}
