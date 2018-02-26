package com.xy.interceptor;

import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.xy.domain.AccessLog;
import com.xy.domain.User;
import com.xy.interceptor.bean.ActionBean;
import com.xy.interceptor.bean.ActionEnum;
import com.xy.interceptor.comparator.MapValueComparator;
import com.xy.queue.LogActionEngine;
import com.xy.service.AccessLogService;
import com.xy.utils.SysConstant;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.ActionNameBuilder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author xieyan
 * @description
 * @date 2018/1/23.
 */
public class MyInterceptor implements Interceptor{
    @Override
    public void destroy() {

    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        String actionName = actionInvocation.getProxy().getActionName();
        statisticAction(actionName);
        String invoke = actionInvocation.invoke();
        System.out.println("invoke:"+invoke);
        return invoke;
    }
    private void statisticAction(String actionName){
        String[] split = actionName.split("_");
        if(split[0] != ""){
            String actionPath = split[0];
            HttpSession session = ServletActionContext.getRequest().getSession();
            Map<String,ActionBean> actionMap = (Map<String,ActionBean>)session.getAttribute(SysConstant.ALL_ACTIONNAME);
            if(actionMap==null){
                System.out.println("==============================================================");
                System.out.println("session 得到的actionmap为空");
                System.out.println("==============================================================");
                actionMap = Maps.newLinkedHashMap();
                session.setAttribute(SysConstant.ALL_ACTIONNAME, actionMap);
            }
            ActionBean actionBean = actionMap.get(actionPath);
            if(actionBean==null){
                ActionBean result = new ActionBean();
                switch (ActionEnum.getByString(actionPath)){
                    case leaveMessageAction:
                        result.setModuleName(ActionEnum.leaveMessageAction.getModuleName());
                        result.setCurl(ActionEnum.leaveMessageAction.getCurl());
                        result.setIco(ActionEnum.leaveMessageAction.getIco());
                        result.setNumber(1);
                        break;
                    case taskListAction:
                        result.setModuleName(ActionEnum.taskListAction.getModuleName());
                        result.setCurl(ActionEnum.taskListAction.getCurl());
                        result.setIco(ActionEnum.taskListAction.getIco());
                        result.setNumber(1);
                        break;
                    case feedbackAction:
                        result.setModuleName(ActionEnum.feedbackAction.getModuleName());
                        result.setCurl(ActionEnum.feedbackAction.getCurl());
                        result.setIco(ActionEnum.feedbackAction.getIco());
                        result.setNumber(1);
                        break;
                    case contractAction:
                        result.setModuleName(ActionEnum.contractAction.getModuleName());
                        result.setCurl(ActionEnum.contractAction.getCurl());
                        result.setIco(ActionEnum.contractAction.getIco());
                        result.setNumber(1);
                        break;
                    case outProductAction:
                        result.setModuleName(ActionEnum.outProductAction.getModuleName());
                        result.setCurl(ActionEnum.outProductAction.getCurl());
                        result.setIco(ActionEnum.outProductAction.getIco());
                        result.setNumber(1);
                        break;
                    case exportAction:
                        result.setModuleName(ActionEnum.exportAction.getModuleName());
                        result.setCurl(ActionEnum.exportAction.getCurl());
                        result.setIco(ActionEnum.exportAction.getIco());
                        result.setNumber(1);
                        break;
                    case packingListAction:
                        result.setModuleName(ActionEnum.packingListAction.getModuleName());
                        result.setCurl(ActionEnum.packingListAction.getCurl());
                        result.setIco(ActionEnum.packingListAction.getIco());
                        result.setNumber(1);
                        break;
                    case shippingOrderAction:
                        result.setModuleName(ActionEnum.shippingOrderAction.getModuleName());
                        result.setCurl(ActionEnum.shippingOrderAction.getCurl());
                        result.setIco(ActionEnum.shippingOrderAction.getIco());
                        result.setNumber(1);
                        break;
                    case invoiceAction:
                        result.setModuleName(ActionEnum.invoiceAction.getModuleName());
                        result.setCurl(ActionEnum.invoiceAction.getCurl());
                        result.setIco(ActionEnum.invoiceAction.getIco());
                        result.setNumber(1);
                        break;
                    case financeAction:
                        result.setModuleName(ActionEnum.financeAction.getModuleName());
                        result.setCurl(ActionEnum.financeAction.getCurl());
                        result.setIco(ActionEnum.financeAction.getIco());
                        result.setNumber(1);
                        break;
                    case statChartAction:
                        result.setModuleName(ActionEnum.statChartAction.getModuleName());
                        result.setCurl(ActionEnum.statChartAction.getCurl());
                        result.setIco(ActionEnum.statChartAction.getIco());
                        result.setNumber(1);
                        break;
                    case productAction:
                        result.setModuleName(ActionEnum.productAction.getModuleName());
                        result.setCurl(ActionEnum.productAction.getCurl());
                        result.setIco(ActionEnum.productAction.getIco());
                        result.setNumber(1);
                        break;
                    case factoryAction:
                        result.setModuleName(ActionEnum.factoryAction.getModuleName());
                        result.setCurl(ActionEnum.factoryAction.getCurl());
                        result.setIco(ActionEnum.factoryAction.getIco());
                        result.setNumber(1);
                        break;
                    case deptAction:
                        result.setModuleName(ActionEnum.deptAction.getModuleName());
                        result.setCurl(ActionEnum.deptAction.getCurl());
                        result.setIco(ActionEnum.deptAction.getIco());
                        result.setNumber(1);
                        break;
                    case userAction:
                        result.setModuleName(ActionEnum.userAction.getModuleName());
                        result.setCurl(ActionEnum.userAction.getCurl());
                        result.setIco(ActionEnum.userAction.getIco());
                        result.setNumber(1);
                        break;
                    case roleAction:
                        result.setModuleName(ActionEnum.roleAction.getModuleName());
                        result.setCurl(ActionEnum.roleAction.getCurl());
                        result.setIco(ActionEnum.roleAction.getIco());
                        result.setNumber(1);
                        break;
                    case moduleAction:
                        result.setModuleName(ActionEnum.moduleAction.getModuleName());
                        result.setCurl(ActionEnum.moduleAction.getCurl());
                        result.setIco(ActionEnum.moduleAction.getIco());
                        result.setNumber(1);
                        break;
                    case otherAction:
                        return;
                    default :
                        break;
                }
                actionBean = result;
                if(result.getCurl()!=null) {
                    actionMap.put(actionPath,actionBean);
                }

            }else{
                actionBean.setNumber(actionBean.getNumber()+1);
                //引用，不需要再添加
            }
            if(actionBean.getCurl() != null){
                //记录action，持久化到数据库中
                AccessLog accessLog = new AccessLog();
                User currUser = (User) session.getAttribute(SysConstant.CURRENT_USER_INFO);
                accessLog.setId(actionBean.getAccessId());
                accessLog.setUserId(currUser.getId());
                accessLog.setModuleKey(split[0]);
                accessLog.setModuleName(actionBean.getModuleName());
                accessLog.setModuleCurl(actionBean.getCurl());
                accessLog.setNumber(actionBean.getNumber());
                accessLog.setIco(actionBean.getIco());

                if(actionBean.getAccessId() == null){
                    // 此时rds中没有此条记录，插入记录
                    WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
                    AccessLogService accessLogService = applicationContext.getBean(AccessLogService.class);
                    accessLogService.saveOrUpdate(accessLog);
                    List<AccessLog> accessLogs = accessLogService.find("from AccessLog where userId = ? and moduleKey = ?", AccessLog.class, new String[]{currUser.getId(), split[0]});
                    if(accessLogs.size()==1){
                        accessLog = accessLogs.get(0);
                        actionBean.setAccessId(accessLogs.get(0).getId());
                    }else{
                        System.out.println("此时已有多个记录。。。。。。。。，userId="+currUser.getId()+",moduleKey ="+split[0]);
                    }
                }
                //放入队列
                LogActionEngine.pushActionEvent(accessLog);
            }
            //将map里面排序
            actionMap = sortActionMap(actionMap);
            session.setAttribute(SysConstant.ALL_ACTIONNAME,actionMap);
        }
    }

    private Map<String, ActionBean> sortActionMap(Map<String, ActionBean> actionMap) {
        Map<String, ActionBean> sortedMap = Maps.newLinkedHashMap();
        List<Map.Entry<String, ActionBean>> entryList = new ArrayList<Map.Entry<String, ActionBean>>(actionMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, ActionBean>> iterator = entryList.iterator();
        Map.Entry<String, ActionBean> tmpEntry = null;
        while (iterator.hasNext()) {
            tmpEntry = iterator.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
}
