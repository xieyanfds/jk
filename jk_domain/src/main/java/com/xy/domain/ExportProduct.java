package com.xy.domain;

import java.io.Serializable;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Description:	报运货物
 */

public class ExportProduct extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@JSONField(serialize=false)
	private Export export;			//报运货物和报运的关系，多对一
	@JSONField(serialize=false)
	private Factory factory;		//报运货物和厂家的关系，多对一
	@JSONField(serialize=false)
	private Set<ExtEproduct> extEproducts;		//报运货物和报运附件的关系，一对多

	@JSONField(name="exportProductId")
	private String id;	  	
	private String productNo;	//货号
	private String packingUnit;			// 包装单位 PCS/SETS
	private Integer cnumber;			//数量
	private Integer boxNum;			//件数
	private Double grossWeight;			//毛重
	private Double netWeight;			//净重
	private Double sizeLength;			//尺寸长
	private Double sizeWidth;			//尺寸宽
	private Double sizeHeight;			//尺寸高
	private Double exPrice;			//出口单价 sales confirmation 中的价格（手填）
	private Double price;			//单价
	private Double tax;			//含税
	@JSONField(serialize=false)
	private Integer orderNo;			

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProductNo() {
		return this.productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}	
	
	public String getPackingUnit() {
		return this.packingUnit;
	}
	public void setPackingUnit(String packingUnit) {
		this.packingUnit = packingUnit;
	}	
	
	public Integer getCnumber() {
		return this.cnumber;
	}
	public void setCnumber(Integer cnumber) {
		this.cnumber = cnumber;
	}	
	
	public Integer getBoxNum() {
		return this.boxNum;
	}
	public void setBoxNum(Integer boxNum) {
		this.boxNum = boxNum;
	}	
	
	public Double getGrossWeight() {
		return this.grossWeight;
	}
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}	
	
	public Double getNetWeight() {
		return this.netWeight;
	}
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}	
	
	public Double getSizeLength() {
		return this.sizeLength;
	}
	public void setSizeLength(Double sizeLength) {
		this.sizeLength = sizeLength;
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
	
	public Double getExPrice() {
		return this.exPrice;
	}
	public void setExPrice(Double exPrice) {
		this.exPrice = exPrice;
	}	
	
	public Double getPrice() {
		return this.price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}	
	
	public Double getTax() {
		return this.tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}	
	
	public Integer getOrderNo() {
		return this.orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Export getExport() {
		return export;
	}
	public void setExport(Export export) {
		this.export = export;
	}
	public Factory getFactory() {
		return factory;
	}
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	public Set<ExtEproduct> getExtEproducts() {
		return extEproducts;
	}
	public void setExtEproducts(Set<ExtEproduct> extEproducts) {
		this.extEproducts = extEproducts;
	}	
	
	
}
