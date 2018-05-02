package com.xy.interceptor.bean;

/**
 * @author xieyan
 * @description
 * @date 2018/1/23.
 */
public enum ActionEnum {
    messageAction("我的留言板","messageAction","messageAction_list","home_message"),
    taskListAction("我的代办任务","taskListAction","taskListAction_list","home_task"),
    feedbackAction("意见反馈","feedbackAction","feedbackAction_list","home_feedback"),
    contractAction("购销合同","contractAction","cargo/contractAction_list","cargo_contract"),
    outProductAction("出货表","outProductAction","cargo/outProductAction_toedit","cargo_outProduct"),
    exportAction("出口报运","exportAction","cargo/exportAction_contractList","cargo_export"),
    packingListAction("装箱管理","packingListAction","cargo/packingListAction_list","cargo_packingList"),
    shippingOrderAction("委托管理","shippingOrderAction","cargo/shippingOrderAction_list","cargo_shippingOrderList"),
    invoiceAction("发票管理","invoiceAction","cargo/invoiceAction_list","cargo_invoiceList"),
    financeAction("财务管理","financeAction","cargo/financeAction_list","cargo_finance"),
    statChartAction("统计分析","statChartAction","stat/statChartAction_factorysale",""),
    productAction("产品信息","productAction","baseinfo/productAction_list","baseinfo_product"),
    factoryAction("厂家信息","factoryAction","baseinfo/factoryAction_list","base_factory"),
    deptAction("部门管理","deptAction","sysadmin/deptAction_list","sysadmin_dept"),
    userAction("用户管理","userAction","sysadmin/userAction_list","sysadmin_user"),
    roleAction("角色管理","roleAction","sysadmin/roleAction_list","sysadmin_role"),
    moduleAction("模块管理","moduleAction","sysadmin/moduleAction_list","sysadmin_module"),
    otherAction("","","","");

    private String path;
    private String curl;
    private String moduleName;
    private String ico;
    ActionEnum(String moduleName,String path,String curl,String ico){
        this.moduleName=moduleName;
        this.path=path;
        this.curl=curl;
        this.ico=ico;
    }
    ActionEnum(){

    }
    public String getCurl() {
        return curl;
    }

    public void setCurl(String curl) {
        this.curl = curl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public static ActionEnum getByString(String actionPath){
        for(ActionEnum actionEnum : values()){
            if(actionEnum.getPath().equals(actionPath)){
                return actionEnum;
            }
        }
        return otherAction;
    }
}
