package com.xy.domain;

import java.util.Date;

/**
 * @Description:	PackingList
 * @Author:			
 * @Company:		
 * @CreateDate:		2016-8-15 16:07:10
 */

public class PackingList extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;

	private ShippingOrder shippingOrder;

	private String seller;			
	private String buyer;			
	private String invoiceNo;			//选择
	private Date invoiceDate;			
	private String marks;			
	private String descriptions;			
	private String exportIds;			//报运ID集合
	private String exportNos;			//报运NO集合x,y|z,h
	private Integer state;			//0草稿 1已上报

	
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
