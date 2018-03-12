package com.xy.action.home;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.AccessLog;
import com.xy.domain.Module;
import com.xy.domain.Shortcut;
import com.xy.domain.User;
import com.xy.interceptor.bean.ActionBean;
import com.xy.service.AccessLogService;
import com.xy.service.ModuleService;
import com.xy.service.ShortcutService;
import com.xy.utils.SysConstant;
import com.xy.utils.UtilFuns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xieyan
 * @description 快捷菜单
 * @date 2017/12/26.
 */

public class ShortcutAction extends BaseAction implements ModelDriven<Shortcut> {
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(ShortcutAction.class);

	@Autowired
	private ShortcutService shortcutService;
	@Autowired
	private AccessLogService accessLogService;
	@Autowired
	private ModuleService moduleService;
	private Shortcut model = new Shortcut();

	@Override
	public Shortcut getModel() {
		return this.model;
	}

	/**
	 * 更新保存
	 * @return
	 */
	public String update() {
		try {
			//修改当前session中快捷栏数据
			User currUser = super.getCurrUser();
			if(currUser.getId().equals(model.getUid())){
                // 快捷方式
                if (UtilFuns.isNotEmpty(model.getModuleIds())) {
                    String[] ids = model.getModuleIds().split(",");
                    if (ids.length > 0) {
                        List<Module> list = new ArrayList<>();
                        for (String id : ids) {
                            if(!id.trim().isEmpty()) {
                                Module module = moduleService.get(Module.class, id);
                                list.add(module);
                            }
                        }
                        session.put("shortList", list);
                    }
                }
            }
			shortcutService.saveOrUpdate(model);
		} catch (Exception e) {
			logger.error("update exception:{}",e);
		}
		return "update";

	}

	/**
	 * 清除常用功能列表
	 * @return
	 */
	public String clear() {
		try {
			// 先清除session中存储的值
			Map<String, ActionBean> actionBeanMap = (Map<String, ActionBean>) session.get(SysConstant.ALL_ACTIONNAME);
			actionBeanMap.clear();
			// 然后删除rds中数据
			User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
			List<AccessLog> accessLogs = accessLogService.find("from AccessLog where userId = ?", AccessLog.class, new String[]{user.getId()});
			for(AccessLog accessLog : accessLogs){
                accessLogService.deleteById(AccessLog.class,accessLog.getId());
            }
		} catch (Exception e) {
			logger.error("clear exception:{}",e);
		}
		return "update";
	}
}
