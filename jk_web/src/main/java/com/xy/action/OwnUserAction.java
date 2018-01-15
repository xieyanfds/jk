package com.xy.action;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.domain.User;
import com.xy.service.UserService;
import com.xy.utils.Encrypt;
import com.xy.utils.SysConstant;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

/**
 * @author xieyan
 * @description 个人信息修改
 * @date 2017/12/26.
 */
public class OwnUserAction extends BaseAction implements ModelDriven<User> {

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
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user = (User) session.getAttribute(SysConstant.CURRENT_USER_INFO);

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
		userService.saveOrUpdate(obj);
		return "update";
	}

	/**
	 * 跳到登陆界面
	 */
	public String login() throws Exception {
		return "login";
	}

	/**
	 * 邮件
	 */
	public String email() throws Exception {
		/*new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-mail.xml");
					JavaMailSender sender = (JavaMailSender) ac.getBean("mailSender"); // 得到邮件的发送对象，专门用于邮件发送

					// 发送一个允许带图片，同时带附件的邮件
					MimeMessage message = sender.createMimeMessage();// 创建一封允许带图片，同时带附件的邮件对象

					// 为了更好的操作MimeMessage对象，借用一个工具类来操作它
					MimeMessageHelper helper = new MimeMessageHelper(message, true);

					// 通过工具类设置主题，内容，图片，附件
					helper.setFrom("service@store.com");
					helper.setTo("jack@store.com");
					helper.setSubject("新密码提醒");
					helper.setText("<html><head></head><body><h1>" + newPassword + " </h1>"
							+ "<img src=cid:image /></body></html>", true);// 第二个参数说明内容要解析为html代码

					// 添加图片
					FileSystemResource resource = new FileSystemResource(new File("F:/01.jpg"));
					helper.addInline("image", resource);

					sender.send(message);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();*/
		return "update";
	}

	/**
	 * 验证旧密码
	 */
	public String check() throws Exception {
		User obj = userService.get(User.class, model.getId());
		if (!obj.getPassword().equals(Encrypt.md5(oldPassword.trim(), obj.getUserName()))) {
			return "error1";
		}
		if (!newPassword.equals(checkPassword)) {
			return "error2";
		}
		return update();
	}

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
