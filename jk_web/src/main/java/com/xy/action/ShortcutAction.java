package com.xy.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;
import com.xy.domain.Module;
import com.xy.domain.Shortcut;
import com.xy.domain.User;
import com.xy.service.ModuleService;
import com.xy.service.ShortcutService;
import com.xy.service.UserService;
import com.xy.utils.SysConstant;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xieyan
 * @description
 * @date 2017/12/26.
 */

public class ShortcutAction extends BaseAction implements ModelDriven<Shortcut> {
	private static final long serialVersionUID = 1L;
	// 注入service
	private ShortcutService shortcutService;

	public void setShortcutService(ShortcutService shortcutService) {
		this.shortcutService = shortcutService;
	}

	private ModuleService moduleService;

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	// 注入service
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// model驱动
	private Shortcut model = new Shortcut();

	@Override
	public Shortcut getModel() {
		return this.model;
	}

	// 转向新增页面
	public String tocreate() {
		// 准备数据
		// List<Shortcut> shortcutList = shortcutService.shortcutList();
		// super.put("shortcutList", shortcutList); //页面就可以访问shortcutList

		return "pcreate";
	}

	// 新增保存
	public String insert() {
		shortcutService.saveOrUpdate(model);

		return "alist"; // 返回列表，重定向action_list
	}

	// 转向修改页面
	public String toupdate() {
		// 准备数据
		// List<Shortcut> shortcutList = shortcutService.shortcutList();
		// super.put("shortcutList", shortcutList); //页面就可以访问shortcutList

		// 准备修改的数据
		Shortcut obj = shortcutService.get(Shortcut.class, model.getUid());
		// super.getValueStack().push(obj);

		return "pupdate";
	}

	// 修改保存
	public String update() {

		// 获取当前用户
		User user = userService.get(User.class, ((User) session.get(SysConstant.CURRENT_USER_INFO)).getId());

		model.setUid(user.getId());
		model.setModleids(model.getModleids());

		shortcutService.saveOrUpdate(model);
		return null;

	}

	// 删除一条
	public String deleteById() {
		shortcutService.deleteById(Shortcut.class, model.getUid());

		return "alist";
	}

	// 删除多条
	public String delete() {
		shortcutService.delete(Shortcut.class, model.getUid().split(", "));

		return "alist";
	}

	// 查看
	public String toview() {

		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(SysConstant.CURRENT_USER_INFO);

		Shortcut shortcut = shortcutService.get(Shortcut.class, user.getId());

		String[] ids = shortcut.getModleids().split(",");

		List<Module> list = new ArrayList<>();

		for (String id : ids) {

			Module module = moduleService.get(Module.class, id);

			list.add(module);
		}

		// {"id":"模块id","name":"模块名称"}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Module module : list) {

			sb.append("{\"name\":\"");
			sb.append(module.getName());
			sb.append("\",\"curl\":\"");
			sb.append(module.getCurl());
			sb.append("\"},");
		}

		// 去除末尾的逗号，加上"]";
		String roleModuleJsonStr = sb.toString();
		roleModuleJsonStr = roleModuleJsonStr.substring(0, roleModuleJsonStr.length() - 1) + "]";
		HttpServletResponse response = ServletActionContext.getResponse();

		System.out.println(roleModuleJsonStr);
		// 写回数据
		try {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(JSON.toJSON(roleModuleJsonStr));
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;

	}

	

}
