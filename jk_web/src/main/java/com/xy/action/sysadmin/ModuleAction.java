package com.xy.action.sysadmin;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Module;
import com.xy.domain.User;
import com.xy.service.ModuleService;
import com.xy.utils.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author xieyan
 * @description 模块管理
 * @date 2017/12/26.
 */
public class ModuleAction extends BaseAction implements ModelDriven<Module>{

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(ModuleAction.class);
	
	private Module model = new Module();
	@Override
	public Module getModel() {
		return model;
	}
	
	//分页查询
	private Page<Module> page = new Page<Module>();;
	public Page<Module> getPage() {
		return page;
	}
	public void setPage(Page<Module> page) {
		this.page = page;
	}

	@Autowired
	private ModuleService moduleService;
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		//设置分页的url地址
		/*HttpServletRequest request = ServletActionContext.getRequest();
		//查询所有内容
		String parameter = request.getParameter("page.pageNo");
		if(!StringUtils.isEmpty(parameter)){
			page.setPageNo(Integer.parseInt(parameter));
		}*/
		try {
			page = moduleService.findPage("from Module", page, Module.class, null);
			page.setUrl("moduleAction_list");
			//将page对象压入栈顶
			pushVS(page);
		} catch (Exception e) {
			logger.error("list exception:{}",e);
		}
		return "list";
	}
	/**
	 * 查看
	 * @return
	 * @throws Exception
	 */
	public String toview()throws Exception{
		try {
			List<Module> mlist = moduleService.find("from Module where state = 1 and (ctype = 0 or ctype = 1)", Module.class, null);
			putContext("moduleList", mlist);
			Module Module = moduleService.get(Module.class, model.getId());
			pushVS(Module);
		} catch (Exception e) {
			logger.error("toview exception:{}",e);
		}
		return "toview";
	}
	/**
	 * 新增，跳转页面
	 * @return
	 * @throws Exception
	 */
	public String tocreate()throws Exception{
		try {
			List<Module> mlist = moduleService.find("from Module where state = 1 and (ctype = 0 or ctype = 1)", Module.class, null);
			putContext("moduleList", mlist);
		} catch (Exception e) {
			logger.error("tocreate exception:{}",e);
		}
		return "tocreate";
	}
	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String insert()throws Exception{
		try {
			//添加细粒度权限控制
			//获取当前用户
			User user = super.getCurrUser();
			model.setCreateBy(user.getId());
			model.setCreateDept(user.getDept().getId());
			model.setCreateTime(new Date());
			Module module = moduleService.get(Module.class, model.getParentId());
			model.setParentName(module.getName());

			moduleService.saveOrUpdate(model);
		} catch (Exception e) {
			logger.error("insert exception:{}",e);
		}
		return "mlist";
	}
	/**
	 * 修改,跳转页面
	 * @return
	 * @throws Exception
	 */
	public String toupdate()throws Exception{
		try {
			List<Module> mlist = moduleService.find("from Module where state = 1 and (ctype = 0 or ctype = 1)", Module.class, null);
			putContext("moduleList", mlist);
			Module Module = moduleService.get(Module.class, model.getId());
			pushVS(Module);
		} catch (Exception e) {
			logger.error("toupdate exception:{}",e);
		}
		return "toUpdate";
	}
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		try {
			// 先查询
			Module module = moduleService.get(Module.class, model.getId());
			//获取当前用户
			User user = super.getCurrUser();

			//设置修改的属性
			module.setName(model.getName());
			module.setLayerNum(model.getLayerNum());
			module.setCpermission(model.getCpermission());
			module.setCurl(model.getCurl());
			module.setCtype(model.getCtype());
			module.setState(model.getState());
			module.setBelong(model.getBelong());
			module.setCwhich(model.getCwhich());
			module.setRemark(model.getRemark());
			module.setOrderNo(model.getOrderNo());
			module.setUpdateBy(user.getId());
			module.setUpdateTime(new Date());

			moduleService.saveOrUpdate(module);
		} catch (Exception e) {
			logger.error("update exception:{}",e);
		}
		return "mlist";
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
		try {
			// 先获取需要删除的id
			String[] ids = model.getId().split(", ");
			moduleService.delete(Module.class, ids);
		} catch (Exception e) {
			logger.error("delete exception:{}",e);
		}
		return "mlist";
	}
}
