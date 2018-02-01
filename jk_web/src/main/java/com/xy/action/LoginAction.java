package com.xy.action;

import com.google.common.collect.Lists;
import com.xy.domain.*;
import com.xy.interceptor.bean.ActionBean;
import com.xy.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.xy.utils.SysConstant;
import com.xy.utils.UtilFuns;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author xieyan
 * @description 登录
 * @date 2017/12/26.
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	@Autowired
	private ShortcutService shortcutService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private LoginLogService loginLogService;
	@Autowired
	private AccessLogService accessLogService;

	//SSH传统登录方式
	public String login() throws Exception {
		
//		if(true){
//			String msg = "登录错误，请重新填写用户名密码!";
//			this.addActionError(msg);
//			throw new Exception(msg);
//		}
//		User user = new User(username, password);
//		User login = userService.login(user);
//		if (login != null) {
//			ActionContext.getContext().getValueStack().push(user);
//			session.put(SysConstant.CURRENT_USER_INFO, login);	//记录session
//			return SUCCESS;
//		}
//		return "login";
		if(UtilFuns.isNotEmpty(getCurrUser())){
			return SUCCESS;
		}
		if(UtilFuns.isEmpty(username)){
			return "login";
		}
		try {
			//1.得到Subject
			Subject subject = SecurityUtils.getSubject();
			//2.调用登录方法
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);//当这一代码执行时，就会自动跳入到AuthRealm中认证方法
			
			//3.登录成功时，就从Shiro中取出用户的登录信息
			User user = (User) subject.getPrincipal();
			
			//4.将用户放入session域中
			session.put(SysConstant.CURRENT_USER_INFO, user);

			initSessionInfo(user);


		} catch (Exception e) {
			e.printStackTrace();
			request.put("errorInfo", "对不起，用户名或密码错误！");
			return "login";
		}
		return SUCCESS;
	}
	
	
	//退出
	public String logout(){
		//删除session中数据
		ServletActionContext.getRequest().getSession().invalidate();
//		session.remove(SysConstant.CURRENT_USER_INFO);		//删除session
		
		return "logout";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private void initSessionInfo(User user){
		// 登录日志功能
		LoginLog log = new LoginLog();

		log.setLoginName(user.getUserName());
		log.setIpAddress(ServletActionContext.getRequest().getRemoteAddr());
		log.setLoginTime(new Date());
		// 时间自动生成

		loginLogService.saveOrUpdate(log);

		// 将快捷方式菜单放入session
		Shortcut shortcut = shortcutService.get(Shortcut.class, user.getId());
		if (shortcut != null && UtilFuns.isNotEmpty(shortcut.getModuleIds())) {
			String[] ids = shortcut.getModuleIds().split(",");
			if (ids.length > 0) {
				List<Module> list = Lists.newArrayList();
				for (String id : ids) {
					if(!id.trim().isEmpty()) {
						Module module = moduleService.get(Module.class, id);
						list.add(module);
					}
				}
				session.put("shortList", list);
			}
		}

		// 取出对应用户快捷使用方式
		List<AccessLog> accessLogs = accessLogService.find("from AccessLog where userId = ? order by number desc", AccessLog.class, new String[]{user.getId()});
		LinkedHashMap<String, ActionBean> linkedHashMap = new LinkedHashMap<>();
		for(AccessLog accessLog : accessLogs){
			ActionBean actionBean = new ActionBean();
			actionBean.setAccessId(accessLog.getId());
			actionBean.setCurl(accessLog.getModuleCurl());
			actionBean.setNumber(accessLog.getNumber());
			actionBean.setModuleName(accessLog.getModuleName());
			actionBean.setIco(accessLog.getIco());
			linkedHashMap.put(accessLog.getModuleKey(),actionBean);
		}
		session.put(SysConstant.ALL_ACTIONNAME,linkedHashMap);
	}
}

