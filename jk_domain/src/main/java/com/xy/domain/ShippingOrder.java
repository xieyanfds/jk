package com.xy.domain;

import java.util.Date;

/**
 * @author xieyan
 * @description 委托单
 * @date 2017/12/27.
 */
public class ShippingOrder extends BaseEntity{

    private String id;

    private PackingList packingList;//一对一的装箱单

    private String orderType;//运输方式，海运/空运
    private String shipper;//货主
    private String consignee;//提单抬头
    private String notifyParty;//正本通知人
    private String lcNo;//信用证
    private String portOfLoading;//装运港
    private String portOfTrans;//转船港
    private String portOfDischarge;//卸货港

    private Date loadingDate;//装期
    private Date limitDate;//效期
    private int isBatch;//是否分批 '1是0否'
    private int isTrans;//是否转船 '1是0否'

    private String copyNum;//份数
    private String remark;//扼要说明
    private String specialCondition;//运输要求
    private String freight;//运费说明
    private String checkBy;//复核人
    private int state;//'0草稿 1已上报',


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PackingList getPackingList() {
        return packingList;
    }

    public void setPackingList(PackingList packingList) {
        this.packingList = packingList;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getNotifyParty() {
        return notifyParty;
    }

    public void setNotifyParty(String notifyParty) {
        this.notifyParty = notifyParty;
    }

    public String getLcNo() {
        return lcNo;
    }

    public void setLcNo(String lcNo) {
        this.lcNo = lcNo;
    }

    public String getPortOfLoading() {
        return portOfLoading;
    }

    public void setPortOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    public String getPortOfTrans() {
        return portOfTrans;
    }

    public void setPortOfTrans(String portOfTrans) {
        this.portOfTrans = portOfTrans;
    }

    public String getPortOfDischarge() {
        return portOfDischarge;
    }

    public void setPortOfDischarge(String portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }

    public Date getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(Date loadingDate) {
        this.loadingDate = loadingDate;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public int getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(int isBatch) {
        this.isBatch = isBatch;
    }

    public int getIsTrans() {
        return isTrans;
    }

    public void setIsTrans(int isTrans) {
        this.isTrans = isTrans;
    }

    public String getCopyNum() {
        return copyNum;
    }

    public void setCopyNum(String copyNum) {
        this.copyNum = copyNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpecialCondition() {
        return specialCondition;
    }

    public void setSpecialCondition(String specialCondition) {
        this.specialCondition = specialCondition;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
