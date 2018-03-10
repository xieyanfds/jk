package com.xy.interceptor.bean;

import java.io.Serializable;

/**
 * @author xieyan
 * @description 使用快捷方式中间记录对象
 * @date 2018/1/23.
 */
public class ActionBean implements Serializable{
    private String accessId;
    private String moduleName;
    private String curl;
    private Integer number;
    private String ico;

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCurl() {
        return curl;
    }

    public void setCurl(String curl) {
        this.curl = curl;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }
}
