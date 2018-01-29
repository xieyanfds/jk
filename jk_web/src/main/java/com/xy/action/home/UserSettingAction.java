package com.xy.action.home;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.User;
import com.xy.service.DeptService;
import com.xy.service.UserService;
import com.xy.utils.Encrypt;
import com.xy.utils.SysConstant;
import com.xy.utils.UtilFuns;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSettingAction extends BaseAction implements ModelDriven<User> {

	private static final long serialVersionUID = -4434166390823568036L;
	private User model = new User();
	@Override
	public User getModel() {
		return model;
	}

	@Autowired
	private UserService userService;
	@Autowired
	private DeptService deptService;

	//准备数据跳转到修改页面
	public String toupdate() throws Exception {
		//获取当前session中的用户
		User currUser = (User) this.getSession().get(SysConstant.CURRENT_USER_INFO);
		//获取当前完整用户信息
		User user = userService.get(User.class, currUser.getId());
		//存入值栈
		super.pushVS(user);
		//跳页面
		return "toupdate";
	}
	
	
	//接收页面数据进行用户属性修改
	public String update() throws Exception {
		//查询数据库中用户
		User user = userService.get(User.class, model.getId());
		
		//复制前台传来的属性
		user.setUserName(model.getUserName());                      //用户名
		user.getUserInfo().setName(model.getUserInfo().getName());  //姓名
		if(model.getUserInfo().getBirthday()!=null){
			user.getUserInfo().setBirthday(model.getUserInfo().getBirthday());//生日
		}
		user.getUserInfo().setGender(model.getUserInfo().getGender());//性别
		user.getUserInfo().setStation(model.getUserInfo().getStation());//岗位
		user.getUserInfo().setTelephone(model.getUserInfo().getTelephone());//电话
		user.getUserInfo().setEmail(model.getUserInfo().getEmail());      //邮箱
		user.getUserInfo().setRemark(model.getUserInfo().getRemark());	//备注
		
		//保存
		userService.saveOrUpdate(user);
		//跳页面
		return "update";
		//return toupdate();
	}
	
	public String toupdatePassword() throws Exception {
		//跳页面
		return "toupdatePassword";
	}
	
	public String updatePassword() throws Exception {
		//获取当前session中的用户
		User currUser = (User) this.getSession().get(SysConstant.CURRENT_USER_INFO);
		//获取当前完整用户信息
		User user = userService.get(User.class, currUser.getId());
		String passwordNow = user.getPassword();
		//获取输入的旧密码
		String passwordOld = (String) ServletActionContext.getRequest().getParameter("passwordOld");
		System.out.println(passwordOld);
		//获取新密码
		String password = (String) model.getPassword();
		String passwordNew = (String) ServletActionContext.getRequest().getParameter("passwordNew");
		
		try {
			if(UtilFuns.isEmpty(passwordOld.trim())){
				request.put("errorInfo1", "对不起，不能为空！");
				throw new Exception("输入不能为空!!");
				
			}else if(UtilFuns.isEmpty(password.trim())){
				request.put("errorInfo2", "对不起，不能为空！");
				throw new Exception("输入不能为空!!");
				
			}else if(UtilFuns.isEmpty(passwordNew.trim())){
				request.put("errorInfo3", "对不起，不能为空！");
				throw new Exception("输入不能为空!!");
				
			}else if (!passwordNow.trim().equals(Encrypt.md5(passwordOld.trim(), user.getUserName()))) {
				request.put("errorInfo4", "对不起，原始密码有误！");
				throw new Exception("原始密码有误!!");
			}else if (!password.trim().equals(passwordNew.trim())) {
				request.put("errorInfo5", "对不起，新密码不一致！");
				throw new Exception("新密码不一致!!");
			}else {
				
				user.setPassword(Encrypt.md5(password.trim(), user.getUserName()));
				userService.saveOrUpdate(user);
			}
			
		} catch (Exception e) {
			return "toupdatePassword";
		}
		
		//跳页面
		return toupdate();
	}
	
	
}
