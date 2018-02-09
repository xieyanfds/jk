package com.xy.action.sysadmin;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Module;
import com.xy.domain.Role;
import com.xy.domain.User;
import com.xy.service.ModuleService;
import com.xy.service.RoleService;
import com.xy.service.UserService;
import com.xy.utils.Page;
import com.xy.utils.SysConstant;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xieyan
 * @description 角色管理
 * @date 2017/12/26.
 */
public class RoleAction extends BaseAction implements ModelDriven<Role>{

	private static final long serialVersionUID = 1L;
	
	private Role model = new Role();
	@Override
	public Role getModel() {
		return model;
	}

	/**
	 * 分页查询
	 */
	private Page<Role> page = new Page<Role>();;
	public Page<Role> getPage() {
		return page;
	}
	public void setPage(Page<Role> page) {
		this.page = page;
	}

	@Autowired
	private RoleService roleService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private UserService userService;
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
		if(!StringUtils.isEmpty(parameter)){
			page.setPageNo(Integer.parseInt(parameter));
		}
		page = roleService.findPage("from Role", page, Role.class, null);
		page.setUrl("roleAction_list");
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
		Role Role = roleService.get(Role.class, model.getId());
		pushVS(Role);
		return "toview";
	}
	/**
	 * 新增，跳转页面
	 * @return
	 * @throws Exception
	 */
	public String tocreate()throws Exception{
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

		roleService.saveOrUpdate(model);
		return "rlist";
	}
	/**
	 * 修改,跳转页面
	 * @return
	 * @throws Exception
	 */
	public String toupdate()throws Exception{
		model = roleService.get(Role.class, model.getId());
		//pushVS(role);
		return "toUpdate";
	}
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		// 先查询
		Role role = roleService.get(Role.class, model.getId());
		//获取当前用户
		User user = super.getCurrUser();

		//设置修改的属性
		role.setName(model.getName());
		role.setRemark(model.getRemark());
		role.setUpdateBy(user.getId());
		role.setUpdateTime(new Date());

		roleService.saveOrUpdate(role);
		return "rlist";
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
	 *    id:Integer,Float,Double.DaOte类型                  id=100               id=200        id=300  
	 *    id=300
	 *    Integer []id;  {100,200,300}
	 *        
	 *                       
	 */
	public String delete()throws Exception{
		// 先获取需要删除的id
		String[] ids = model.getId().split(", ");
		roleService.delete(Role.class, ids);
		
		return "rlist";
	}

	public String tomodule() throws Exception {
		System.out.println(this);
		//先根据id查询角色
		Role role = roleService.get(Role.class, model.getId());
		//放入值栈
		pushVS(role);
		
		return "tomodule";
	}
	/**
	 * 为了使用 zTree树，就要组织好zTree树所使用的json数据
	 * json数据结构如下：
	 * [{"id":"模块的id","pId":"父模块id","name":"模块名","checked":"true|false"},{"id":"模块的id","pId":"父模块id","name":"模块名","checked":"true|false"}]
	 * 
	 * 常用的json插件有哪些？
	 * json-lib    fastjson     struts-json-plugin-xxx.jar,手动拼接
	 * 
	 * 如何输出?
	 * 借助于response对象输出数据
	 */
	public String roleModuleJsonStr() throws Exception {
		//得到角色的id
		Role role = roleService.get(Role.class, model.getId());
		//2.通过对象导航方式，加载出当前角色的模块列表
		Set<Module> modules = role.getModules();
		//查询出所有的模块列表
		List<Module> mList = moduleService.find("from Module where state = 1", Module.class, null);
		int size=mList.size();
		//组织json串
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Module module : mList) {
			size--;
			sb.append("{\"id\":\"").append(module.getId());
			sb.append("\",\"pId\":\"").append(module.getParentId());
			sb.append("\",\"name\":\"").append(module.getName());
			sb.append("\",\"checked\":\"");
			if(modules.contains(module)){
				sb.append("true");
			}else{
				sb.append("false");
			}
			sb.append("\"}");
			
			if(size>0){
				sb.append(",");
			}
		}
		sb.append("]");
		//得到response对象
		HttpServletResponse response = ServletActionContext.getResponse();
		//设置发送的格式
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		//6.使用 response对象输出json串
		response.getWriter().write(sb.toString());
		//7.返回NONE
		return NONE;
	}
	private String moduleIds;
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	/**
	 * 保存当前角色的模块列表
	 * 	<input type="hidden" name="id" value="${id}"/>
	    <input type="hidden" id="moduleIds" name="moduleIds" value="" />
	 * 
	 */
	public String module() throws Exception {
		//1.获取修改的角色
		Role role = roleService.get(Role.class, model.getId());
		
		//2.选中的模块有哪些？
		String ids [] = moduleIds.split(",");
		
		Set<Module> moduleSet = new HashSet<Module>();
		//2.设置角色的权限集合
		for (String id : ids) {
			moduleSet.add(moduleService.get(Module.class, id));
		}
		
		//3.设置角色的权限
		role.setModules(moduleSet);
		
		//4.保存
		roleService.saveOrUpdate(role);
		
		//5.跳转
		return "rlist";
	}

	/**
	 * 自定义快捷菜单，跳转
	 * @return
	 * @throws Exception
	 */
	public String toUser() throws Exception {
		// 获取当前用户
		User currUser = super.getCurrUser();
		// 将查询到的数据压入值栈
		super.pushVS(currUser);
		// 跳转页面
		return "touser";
	}

	/**
	 * 快捷方式
	 * @return
	 * @throws Exception
	 */
	public String userModuleJsonStr() throws Exception {
		// 获取用户
		User user = (User)session.get(SysConstant.CURRENT_USER_INFO);

		// 获取用户角色
		Set<Role> roles = user.getRoles();

		// 遍历角色
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Role role : roles) {
			// 获取角色下的模块
			Set<Module> modules = role.getModules();
			// 查询所有的模块
			List<Module> moduleList = moduleService.find("from Module where state = 1 and parentId in(1,2,3,4,5,6) order by orderNo", Module.class,null);
			// 拼接json字符串
			// [{"id":"模块id","pId":"父模块id","name":"模块名称","checked":"true|false"},{"id":"模块id","pId":"父模块id","checked":"true|false"}]
			for (Module module : modules) {
				if(moduleList.contains(module)){
					sb.append("{\"id\":\"").append(module.getId());
					sb.append("\",\"pId\":\"").append(module.getParentId());
					sb.append("\",\"name\":\"").append(module.getName());
					sb.append("\",\"checked\":\"false\"");
					sb.append("},");
				}
			}
		}
		// 去除末尾的逗号，加上"]";
		String roleModuleJsonStr = sb.toString();
		roleModuleJsonStr = roleModuleJsonStr.substring(0, roleModuleJsonStr.length() - 1) + "]";
		// 获取response对象
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置格式
		response.setContentType("application/json;charset=utf-8");
		// 写回数据
		response.getWriter().write(roleModuleJsonStr);

		return null;
	}
}
