package com.xy.domain;

import java.util.Date;

/**
 * @author xieyan
 * @description 委托单
 * @date 2017/12/27.
 */
public class ShippingOrder extends BaseEntity{

    private String id;
    private String order_type;//运输方式，海运/空运
    private String shipper;//货主
    private String consignee;//提单抬头
    private String notify_party;//正本通知人
    private String lc_no;//信用证
    private String port_of_loading;//装运港
    private String port_of_trans;//转船港
    private String port_of_discharge;//卸货港

    private Date loading_date;//装期
    private Date limit_date;//效期
    private int is_batch;//是否分批 '1是0否'
    private int is_trans;//是否转船 '1是0否'

    private String copy_num;//份数
    private String remark;//扼要说明
    private String special_condition;//运输要求
    private String freight;//运费说明
    private String check_by;//复核人
    private int state;//'0草稿 1已上报',

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
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

    public String getNotify_party() {
        return notify_party;
    }

    public void setNotify_party(String notify_party) {
        this.notify_party = notify_party;
    }

    public String getLc_no() {
        return lc_no;
    }

    public void setLc_no(String lc_no) {
        this.lc_no = lc_no;
    }

    public String getPort_of_loading() {
        return port_of_loading;
    }

    public void setPort_of_loading(String port_of_loading) {
        this.port_of_loading = port_of_loading;
    }

    public String getPort_of_trans() {
        return port_of_trans;
    }

    public void setPort_of_trans(String port_of_trans) {
        this.port_of_trans = port_of_trans;
    }

    public String getPort_of_discharge() {
        return port_of_discharge;
    }

    public void setPort_of_discharge(String port_of_discharge) {
        this.port_of_discharge = port_of_discharge;
    }

    public Date getLoading_date() {
        return loading_date;
    }

    public void setLoading_date(Date loading_date) {
        this.loading_date = loading_date;
    }

    public Date getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(Date limit_date) {
        this.limit_date = limit_date;
    }

    public int getIs_batch() {
        return is_batch;
    }

    public void setIs_batch(int is_batch) {
        this.is_batch = is_batch;
    }

    public int getIs_trans() {
        return is_trans;
    }

    public void setIs_trans(int is_trans) {
        this.is_trans = is_trans;
    }

    public String getCopy_num() {
        return copy_num;
    }

    public void setCopy_num(String copy_num) {
        this.copy_num = copy_num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpecial_condition() {
        return special_condition;
    }

    public void setSpecial_condition(String special_condition) {
        this.special_condition = special_condition;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getCheck_by() {
        return check_by;
    }

    public void setCheck_by(String check_by) {
        this.check_by = check_by;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
