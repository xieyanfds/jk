package com.xy.action;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xy.domain.*;
import com.xy.interceptor.bean.ActionBean;
import com.xy.jedis.RedisService;
import com.xy.service.*;
import com.xy.utils.redis.RedisCacheKey;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.xy.utils.SysConstant;
import com.xy.utils.UtilFuns;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
	@Autowired
	private RedisService redisService;

	//SSH传统登录方式
	public String login() throws Exception {
		
		if(UtilFuns.isNotEmpty(getCurrUser())){
			return SUCCESS;
		}
		//此时可能是session过期
//		ew
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

			// 往redis中记录用户拥有的菜单权限
			initPermission(user);

		} catch (Exception e) {
			e.printStackTrace();
			request.put("errorInfo", "对不起，用户名或密码错误！");
			return "login";
		}
		return SUCCESS;
	}
	
	
	//退出
	public String logout(){
		//删除redis中数据
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		redisService.delete(String.format(RedisCacheKey.USER_PERMISSION_ID,user.getId()));
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
		//记录登录日志
		log(user);

		// 将自定义快捷方式菜单放入session
		initShortCut(user);


		// 取出用户使用快捷使用方式
		initAccessLog(user);

	}
	private void log(User user){
		// 登录日志功能
		LoginLog log = new LoginLog();

		log.setLoginName(user.getUserName());
		log.setIpAddress(ServletActionContext.getRequest().getRemoteAddr());
		log.setLoginTime(new Date());
		// 时间自动生成

		loginLogService.saveOrUpdate(log);
	}
	private void initShortCut(User user){
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
	}
	private void initAccessLog(User user){
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
	private void initPermission(User user){
		Set<Role> roles = user.getRoles();
		HashSet<String> mSet = Sets.newHashSet();
		ArrayList<Module> allModule = Lists.newArrayList();
		for (Role role : roles) {
			//获取每个角色的权限
			Set<Module> modules = role.getModules();
			for (Module module : modules) {
				//将用户的所有权限添加到集合中
				allModule.add(module);
				if(module.getCtype()==0) {
					mSet.add(module.getName());
				}
			}
		}
		session.put(SysConstant.ALL_PERMISSION,allModule);
		//设置和session一样的半小时的有效期
		String key = String.format(RedisCacheKey.USER_PERMISSION_ID, user.getId());
		redisService.sadd(key,mSet);
		redisService.expire(key,30,TimeUnit.MINUTES);
//		redisService.setex(String.format(RedisCacheKey.USER_PERMISSION_ID,user.getId()),mSet,30, TimeUnit.MINUTES);
//		session.put(SysConstant.ALL_PERMISSION,mSet);
	}
}

