package com.xy.action.home;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.TaskList;
import com.xy.domain.User;
import com.xy.service.TaskListService;
import com.xy.service.UserService;
import com.xy.utils.Page;
import com.xy.utils.SysConstant;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author xieyan
 * @description 待办任务
 * @date 2017/12/26.
 */

public class TaskListAction extends BaseAction implements ModelDriven<TaskList> {
	@Autowired
	private TaskListService taskListService;
	@Autowired
	private UserService userService;

	// model驱动
	private TaskList model = new TaskList();
	@Override
	public TaskList getModel() {
		return this.model;
	}

	// 作为属性驱动，接收并封装页面参数
	private Page page = new Page(); // 封装页面的参数，主要当前页参数

	public void setPage(Page page) {
		this.page = page;
	}

	// 列表展示
	public String list() {
		// 获取当前登录用户
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 查询自己发布的任务 或者 执行者是自己的任务
		String hql = "from TaskList where userId='" + user.getId() + "' or pusherId='" + user.getId() + "' order by pushDate  desc"; // 查询所有内容
		// 给页面提供分页数据
		page.setUrl("tasklistAction_list"); // 配置分页按钮的转向链接
		page = taskListService.findPage(hql, page, TaskList.class, null);
		super.putContext("page", page);

		return "plist"; // page list
	}
	
	
	// 列表展示
	public String findAllTask() {
		// 获取当前登录用户
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 查询自己发布的任务 或者 执行者是自己的任务
		String hql = "from TaskList  order by pushDate  desc"; // 查询所有内容
		// 给页面提供分页数据
		page.setUrl("tasklistAction_list"); // 配置分页按钮的转向链接
		page = taskListService.findPage(hql, page, TaskList.class, null);
		super.putContext("page", page);

		return "plist"; // page list
	}
	
	// 只展示我的任务列表
	public String onlyLookMyTask() {
		// 获取当前登录用户
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 查询自己发布的任务 或者 执行者是自己的任务
		String hql = "from TaskList where userId='" + user.getId()+"' order by pushDate  desc"; // 查询所有内容
		// 给页面提供分页数据
		page.setUrl("tasklistAction_list"); // 配置分页按钮的转向链接
		page = taskListService.findPage(hql, page, TaskList.class, null);
		super.putContext("page", page);

		return "plist"; // page list
	}

	// 转向新增页面
	public String tocreate() {
		// 获取当前登录用户
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 查询出当前登录用户下的直接下属
		List<User> userList = userService.find("from User where userInfo.manager.id='" + user.getId() + "'", User.class,null);
		// 放入值栈中
		super.putContext("userList", userList);

		return "pcreate";
	}

	// 新增保存
	public String insert() {

		taskListService.saveOrUpdate(model);
		// 返回列表
		return list();
	}

	// 转向修改页面
	public String toupdate() {
		// 获取当前登录用户
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 查询出当前登录用户下的直接下属
		List<User> userList = userService.find("from User where userInfo.manager.id='" + user.getId() + "'", User.class,
				null);
		// 放入值栈中
		super.putContext("userList", userList);
		// 准备修改的数据
		TaskList obj = taskListService.get(TaskList.class, model.getId());
		super.pushVS(obj);
		return "pupdate";
	}

	// 修改保存
	public String update() {
		TaskList taskList = taskListService.get(TaskList.class, model.getId());

		// 设置修改的属性，根据业务去掉自动生成多余的属性
		taskList.setUserId(model.getUserId());
		taskList.setUserName(model.getUserName());
		taskList.setContent(model.getContent());
		taskList.setPushDate(model.getPushDate());
		taskList.setEndDate(model.getEndDate());
		taskList.setState(model.getState());
		taskList.setMajor(model.getMajor());

		taskListService.saveOrUpdate(taskList);

		return list();
	}

	// 删除一条
	public String deleteById() {
		taskListService.deleteById(TaskList.class, model.getId());

		return list();
	}

	// 删除多条
	public String delete() {
		// 调用service
		taskListService.delete(TaskList.class, model.getId().split(", "));
		return list();
	}

	// 查看
	public String toview() {
		TaskList obj = taskListService.get(TaskList.class, model.getId());
		super.pushVS(obj);

		return "pview"; // 转向查看页面
	}
	/**
	 * 查询用户是否是领导
	 * @throws IOException
	 */
	public void isManager() throws IOException {
		// 获取当前登录用户
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		// 查询出当前登录用户下的直接下属
		List<User> userList = userService.find("from User where userInfo.manager.id='" + user.getId() + "'", User.class,
				null);
		// 判断是否存在下属
		PrintWriter writer = ServletActionContext.getResponse().getWriter();
		if (userList != null && userList.size() > 0) {
			// 存在,写回1
			writer.println("1");
		} else {
			// 不存在,写回0
			writer.println("0");
		}

	}
	/**
	 * 将任务提交给上级领导
	 * @return
	 * @throws Exception
	 */
	public String updateToManager() throws Exception {
		
		//根据主键获取任务对象
		TaskList taskList = taskListService.get(TaskList.class, model.getId());
		System.out.println(model.getUserId());
		//修改任务的执行者id
		taskList.setUserId(model.getUserId());
		//修改任务的执行者名称
		User user = userService.get(User.class, model.getUserId());
		taskList.setUserName(user.getUserName());
		
		return list();
	}
	

}
