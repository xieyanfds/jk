package com.xy.action;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.domain.User;
import com.xy.service.UserService;
import com.xy.utils.Encrypt;
import com.xy.utils.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author xieyan
 * @description 个人信息修改
 * @date 2017/12/26.
 */
public class OwnUserAction extends BaseAction implements ModelDriven<User> {

	@Autowired
	private UserService userService;

	private User model = new User();

	@Override
	public User getModel() {
		return model;
	}

	/**
	 * 引入jsp中输入的旧密码
	 */
	private String oldPassword;

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * 引入jsp中输入的新密码
	 */
	private String newPassword;

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * 引入jsp中输入的验证新密码
	 */
	private String checkPassword;

	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}

	/**
	 * 进入个人信息修改界面
	 *
	 * @return
	 * @throws Exception
	 */
	public String toupdate() throws Exception {
		User user = super.getCurrUser();

		// 将查询结果放入值栈中
		super.pushVS(user);

		// 跳转页面
		return "toupdate";
	}

	/**
	 * 修改保存
	 *
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		User obj = userService.get(User.class, model.getId());
		obj.setPassword(Encrypt.md5(newPassword, model.getUserName()));
		obj.setUpdateBy(obj.getId());
		obj.setUpdateTime(new Date());
		userService.saveOrUpdate(obj);
		//清除session，避免login跳过
		session.remove(SysConstant.CURRENT_USER_INFO);
		return "update";
	}
}
