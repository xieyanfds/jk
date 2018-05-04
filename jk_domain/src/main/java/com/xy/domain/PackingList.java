package com.xy.domain;

import java.util.Date;

/**
 * @author xieyan
 * @description 装箱单
 * @date 2017/12/27.
 */
public class PackingList extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;

	private ShippingOrder shippingOrder;

	private String seller;//卖方
	private String buyer;//买方
	private String invoiceNo;//发票号，在新增发票时设置
	private Date invoiceDate;//发票日期
	private String marks;//唛头
	private String descriptions;//描述
	private String exportIds;//报运ID集合
	private String exportNos;//报运NO集合x,y|z,h
	private Integer state;//0草稿 1已上报

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public ShippingOrder getShippingOrder() {
		return shippingOrder;
	}

	public void setShippingOrder(ShippingOrder shippingOrder) {
		this.shippingOrder = shippingOrder;
	}

	public String getSeller() {
		return this.seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}	
	
	public String getBuyer() {
		return this.buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}	
	
	public String getInvoiceNo() {
		return this.invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}	
	
	public Date getInvoiceDate() {
		return this.invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}	
	
	public String getMarks() {
		return this.marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}	
	
	public String getDescriptions() {
		return this.descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}	
	
	public String getExportIds() {
		return this.exportIds;
	}
	public void setExportIds(String exportIds) {
		this.exportIds = exportIds;
	}	
	
	public String getExportNos() {
		return this.exportNos;
	}
	public void setExportNos(String exportNos) {
		this.exportNos = exportNos;
	}	
	
	public Integer getState() {
		return this.state;
	}
	public void setState(Integer state) {
		this.state = state;
	}	
	
	
	
}
