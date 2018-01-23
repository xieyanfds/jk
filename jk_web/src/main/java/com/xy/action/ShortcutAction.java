package com.xy.action;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.domain.Module;
import com.xy.domain.Shortcut;
import com.xy.domain.User;
import com.xy.service.ModuleService;
import com.xy.service.ShortcutService;
import com.xy.utils.UtilFuns;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xieyan
 * @description 快捷菜单
 * @date 2017/12/26.
 */

public class ShortcutAction extends BaseAction implements ModelDriven<Shortcut> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ShortcutService shortcutService;

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
		return "update";

	}

}
