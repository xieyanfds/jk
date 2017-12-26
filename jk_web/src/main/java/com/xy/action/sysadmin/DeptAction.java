package com.xy.action.sysadmin;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Dept;
import com.xy.service.DeptService;
import com.xy.utils.Page;

public class DeptAction extends BaseAction implements ModelDriven<Dept>{

	private static final long serialVersionUID = 1L;
	
	private Dept model = new Dept();
	
	public Dept getModel() {
		return model;
	}
	
	//分页查询
	private Page<Dept> page = new Page<Dept>();;
	public Page<Dept> getPage() {
		return page;
	}
	public void setPage(Page<Dept> page) {
		this.page = page;
	}
	
	private DeptService deptService;
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		//设置分页的url地址
		page = deptService.findPage("from Dept", page, Dept.class, null);
		page.setUrl("deptAction_list");
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
		Dept dept = deptService.get(Dept.class, model.getId());
		pushVS(dept);
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
		return "tocreate";
	}
	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String insert()throws Exception{
		deptService.saveOrUpdate(model);
		return "dlist";
	}
	/**
	 * 修改,跳转页面
	 * @return
	 * @throws Exception
	 */
	public String toupdate()throws Exception{
		Dept dept = deptService.get(Dept.class, model.getId());
		pushVS(dept);
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
		putContext("deptList", deptList);
		return "toUpdate";
	}
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		// 先查询
		Dept dept = deptService.get(Dept.class, model.getId());
		
		//设置修改的属性
		dept.setDeptName(model.getDeptName());
		dept.setParent(model.getParent());
		
		deptService.saveOrUpdate(dept);
		return "dlist";
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
		deptService.delete(Dept.class, ids);
		
		return "dlist";
	}
}
