package com.xy.action.sysadmin;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Dept;
import com.xy.domain.Role;
import com.xy.domain.User;
import com.xy.service.DeptService;
import com.xy.service.RoleService;
import com.xy.service.UserService;
import com.xy.utils.Page;
import com.xy.utils.SysConstant;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xieyan
 * @description 用户管理
 * @date 2017/12/26.
 */
public class UserAction extends BaseAction implements ModelDriven<User>{

	private static final long serialVersionUID = 1L;

	@Autowired
	private SimpleMailMessage simpleMailMessage;
	@Autowired
	private JavaMailSender javaMailSender;

	private User model = new User();
	@Override
	public User getModel() {
		return model;
	}

	/**
	 * 分页查询
	 */
	private Page<User> page = new Page<User>();;
	public Page<User> getPage() {
		return page;
	}
	public void setPage(Page<User> page) {
		this.page = page;
	}

	@Autowired
	private UserService userService;

	@Autowired
	private DeptService deptService;

	@Autowired
	private RoleService roleService;
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		//设置分页的url地址
		HttpServletRequest request = ServletActionContext.getRequest();
		//查询所有内容
		String parameter = request.getParameter("page.pageNo");
		if(parameter!=null){
			page.setPageNo(Integer.parseInt(parameter));
		}
		page = userService.findPage("from User", page, User.class, null);
		page.setUrl("userAction_list");
		//将page对象压入栈顶
		pushVS(page);
		return "list";
	}
	/**
	 * 查看
	 * @return
	 * @throws Exception
	 */
	public String toview()throws Exception{
		User User = userService.get(User.class, model.getId());
		pushVS(User);
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
		putContext("deptList", deptList);
		List<User> ulist = userService.find("from User where state = 1", User.class, null);
		putContext("userList", ulist);
		return "toview";
	}
	/**
	 * 新增，跳转页面
	 * @return
	 * @throws Exception
	 */
	public String tocreate()throws Exception{
		List<Dept> list = deptService.find("from Dept where state = 1", Dept.class, null);
		putContext("deptList", list);
		List<User> ulist = userService.find("from User where state = 1", User.class, null);
		putContext("userList", ulist);
		return "tocreate";
	}
	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String insert()throws Exception{
		//添加细粒度权限控制
		//获取当前用户
		User user = super.getCurrUser();
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		model.setCreateTime(new Date());
		model.getUserInfo().setCreateBy(user.getId());
		model.getUserInfo().setCreateDept(user.getDept().getId());
		model.getUserInfo().setCreateTime(new Date());

		userService.saveOrUpdate(model);

		//再开启一个线程完成邮件发送功能
		//spring集成javaMail
		Thread th = new Thread(new Runnable() {
			public void run() {
				try {
					simpleMailMessage.setTo(model.getUserInfo().getEmail());
					simpleMailMessage.setSubject("新员工入职的系统账户通知");
					simpleMailMessage.setText("欢迎您加入本集团，您的用户名:"+model.getUserName()+",初始密码："+ SysConstant.DEFAULT_PASS);

					javaMailSender.send(simpleMailMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		th.start();

		return "ulist";
	}
	/**
	 * 修改,跳转页面
	 * @return
	 * @throws Exception
	 */
	public String toupdate()throws Exception{
		User User = userService.get(User.class, model.getId());
		pushVS(User);
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
		putContext("deptList", deptList);
		List<User> ulist = userService.find("from User where state = 1", User.class, null);
		putContext("userList", ulist);
		return "toUpdate";
	}
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		// 先查询
		User user = userService.get(User.class, model.getId());
		User currUser = super.getCurrUser();

		//设置修改的属性
		user.setDept(model.getDept());
		user.setUserName(model.getUserName());
		user.setState(model.getState());
		user.setUpdateBy(currUser.getId());
		user.setUpdateTime(new Date());

		userService.saveOrUpdate(user);
		return "ulist";
	}
	/**
	 * 跳转到修改角色页面
	 * @return
	 * @throws Exception
	 */
	public String torole()throws Exception{
		//查询所有角色
		List<Role> rList = roleService.find("from Role", Role.class, null);
		putContext("roleList", rList);
		User userInfo = userService.get(User.class, model.getId());
		pushVS(userInfo);
		//设置用户的 角色字符串
		StringBuffer buffer = new StringBuffer();
		for (Role role : userInfo.getRoles()) {
			buffer.append(role.getName()).append(",");
		}
		putContext("userRoleStr", buffer.toString());
		return "torole";
	}
	/**
	 * 保存用户角色
	 * @return
	 * @throws Exception
	 */
	private String []roleIds;//保存角色的列表
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	public String role()throws Exception{
		//先获取修改用户
		User user = userService.get(User.class, model.getId());
		//设置用户角色
		Set<Role> roles = new HashSet<Role>();
		for (String string : roleIds) {
			Role role = roleService.get(Role.class, string);
			roles.add(role);
		}
		//3.设置用户与角色列表之间的关系
		user.setRoles(roles);
		
		
		//4.保存到数据库表中
		userService.saveOrUpdate(user);//影响的是用户角色的中间表
		
		//5.跳页面
		return "ulist";
	}
	/**
	 * 删除
	 * <input type="checkbox" name="id" value="100"/>
	 * <input type="checkbox" name="id" value="3d00290a-1af0-4c28-853e-29fbf96a2722"/>
	 * .....
	 * model
	 *    id:String类型
	 *       具有同名框的一组值如何封装数据？
	 *       如何服务端是String类型：
	 *                       100, 3d00290a-1af0-4c28-853e-29fbf96a2722, 3d00290a-1af0-4c28-853e-29fbf96a2722
	 *                       
	 *    id:Integer,Float,Double.Date类型                  id=100               id=200        id=300  
	 *    id=300
	 *    Integer []id;  {100,200,300}
	 *        
	 *                       
	 */
	public String delete()throws Exception{
		// 先获取需要删除的id
		String[] ids = model.getId().split(", ");
		userService.delete(User.class, ids);
		
		return "ulist";
	}
	/**
	 * ajax查询用户详情
	 * @throws Exception
	 */
	public void findById4ajax() throws Exception {
		//根据主键查出用户详情
		User user = userService.get(User.class, model.getId());
		//将用户转为json写回
		String jsonString = JSON.toJSONString(user);
		HttpServletResponse response = ServletActionContext.getResponse();
		//设置中文
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonString);

	}
	/**
	 * ajax查询用户的下属
	 * @throws Exception
	 */
	public void hasManager() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		//根据主键查出用户详情
		List<User> userList = userService.find("from User where userInfo.manager.id=? ", User.class,new String[]{ model.getId()});
		if(userList!=null&&userList.size()>0){
			//存在 将用户转为json写回
			String jsonString = JSON.toJSONString(userList);
			//设置中文
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(jsonString);
		}else{
			//不存在
			response.getWriter().write("0");
		}


	}
}
