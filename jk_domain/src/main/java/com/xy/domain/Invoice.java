package com.xy.domain;

/**
 * @author xieyan
 * @description
 * @date 2018/1/6.
 */
public class Invoice extends BaseEntity {

    private String id;

    private PackingList packingList;//一对一的装箱单

    private String scNo;//报运合同号
    private String blNo;//提单号
    private String tradeTerms;//交易条款
    private int state;

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

    public String getScNo() {
        return scNo;
    }

    public void setScNo(String scNo) {
        this.scNo = scNo;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
