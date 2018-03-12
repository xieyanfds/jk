package com.xy.action.sysadmin;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Dept;
import com.xy.service.DeptService;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xieyan
 * @description 部门管理
 * @date 2017/12/26.
 */
public class DeptAction extends BaseAction implements ModelDriven<Dept>{

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(DeptAction.class);
	
	private Dept model = new Dept();
	@Override
	public Dept getModel() {
		return model;
	}

	/**
	 * 分页查询
	 */
	private Page<Dept> page = new Page<Dept>();;
	public Page<Dept> getPage() {
		return page;
	}
	public void setPage(Page<Dept> page) {
		this.page = page;
	}

	@Autowired
	private DeptService deptService;
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		try {
			//设置分页的url地址
			HttpServletRequest request = ServletActionContext.getRequest();
			//查询所有内容
			String parameter = request.getParameter("page.pageNo");
			if(!StringUtils.isEmpty(parameter)){
                page.setPageNo(Integer.parseInt(parameter));
            }
			page = deptService.findPage("from Dept", page, Dept.class, null);
			page.setUrl("deptAction_list");
			//将page对象压入栈顶
			pushVS(page);
		} catch (NumberFormatException e) {
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
			Dept dept = deptService.get(Dept.class, model.getId());
			pushVS(dept);
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
			List<Dept> list = deptService.find("from Dept where state = 1", Dept.class, null);
			putContext("deptList", list);
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
			deptService.saveOrUpdate(model);
		} catch (Exception e) {
			logger.error("insert exception:{}",e);
		}
		return "dlist";
	}
	/**
	 * 修改,跳转页面
	 * @return
	 * @throws Exception
	 */
	public String toupdate()throws Exception{
		try {
			Dept dept = deptService.get(Dept.class, model.getId());
			pushVS(dept);
			List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
			putContext("deptList", deptList);
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
			Dept dept = deptService.get(Dept.class, model.getId());

			//设置修改的属性
			dept.setDeptName(model.getDeptName());
			dept.setParent(model.getParent());

			deptService.saveOrUpdate(dept);
		} catch (Exception e) {
			logger.error("update exception:{}",e);
		}
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
		try {
			// 先获取需要删除的id
			String[] ids = model.getId().split(", ");
			deptService.delete(Dept.class, ids);
		} catch (Exception e) {
			logger.error("delete exception:{}",e);
		}
		return "dlist";
	}
}
