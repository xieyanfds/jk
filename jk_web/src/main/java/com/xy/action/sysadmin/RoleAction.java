package com.xy.action.sysadmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Module;
import com.xy.domain.Role;
import com.xy.service.ModuleService;
import com.xy.service.RoleService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;

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
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		//设置分页的url地址
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
		//设置修改的属性
		role.setName(model.getName());
		role.setRemark(model.getRemark());
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
		List<Module> mList = moduleService.find("from Module", Module.class, null);
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
}
