package com.xy.shiro;

import com.google.common.collect.Sets;
import com.xy.domain.Module;
import com.xy.domain.Role;
import com.xy.domain.User;
import com.xy.jedis.RedisService;
import com.xy.service.UserService;
import com.xy.utils.SysConstant;
import com.xy.utils.redis.RedisCacheKey;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm{

	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;

	//授权   当jsp页面出现Shiro标签时，就会执行授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		User user = (User) arg0.fromRealm(this.getName()).iterator().next();
		HashSet<String> mSet = Sets.newHashSet();
		if(user!=null) {
			/*Set<Role> roles = user.getRoles();//对象导航
			for (Role role : roles) {
				//获取每个角色的权限
				Set<Module> modules = role.getModules();
				for (Module module : modules) {
					mSet.add(module.getName());
				}
			}*/
//			HttpSession session = ServletActionContext.getRequest().getSession();
//			Set allPermission = (Set)session.getAttribute(SysConstant.ALL_PERMISSION);
			Set<String> allPermission = redisService.smembers(String.format(RedisCacheKey.USER_PERMISSION_ID, user.getId()));
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			authorizationInfo.addStringPermissions(allPermission);//添加用户的权限
			return authorizationInfo;
		}
		return null;
	}

	//认证   token 代表用户在界面输入的用户名和密码
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		//向下转型
		UsernamePasswordToken up = (UsernamePasswordToken) arg0;
		//获取用户
		String sql = "from User where userName = ?";
		List<User> uList = userService.find(sql, User.class, new String[]{up.getUsername()});
		if(uList != null && uList.size()>0){
			User user = uList.get(0);
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
			return authenticationInfo;//此处如果返回，就会立刻进入比较器
			
		}
		return null;//抛出异常
	}

}
