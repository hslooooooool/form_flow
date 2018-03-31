package cn.com.smart.web.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.entity.TNResource;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.plugins.ZTreeData;
import cn.com.smart.web.service.ResourceService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 资源
 * @author lmq
 *
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/resource"; 
	
	@Autowired
	private ResourceService resServ;
	
	/**
	 * 资源列表 
	 * @param searchParam
	 * @param modelView
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView list(FilterParam searchParam,ModelAndView modelView,RequestPage page) throws Exception {
		SmartResponse<Object> smartResp = resServ.findAll(searchParam, page.getStartNum(), page.getPageSize());
		String uri = "resource/list"; 
		addBtn = new EditBtn("add","showPage/base_resource_add", null, "添加资源", "600");
		editBtn = new EditBtn("edit","showPage/base_resource_edit", "resource", "修改资源", "600");
		delBtn = new DelBtn("resource/delete.json", "确定要删除选中的资源吗？",uri,null, null);
		refreshBtn = new RefreshBtn(uri, "resource",null);
		pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelMap.put("searchParam", searchParam);
		
		addBtn = null;editBtn = null;delBtn = null;
		refreshBtn = null;pageParam = null;
		
		modelView.setViewName(VIEW_DIR+"/list");
		return modelView;
	}
	
	/**
	 * 添加
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> add(TNResource resource) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != resource) {
			smartResp = resServ.save(resource);
		}
		return smartResp;
	}
	
	/**
	 * 修改
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TNResource resource) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != resource) {
			smartResp = resServ.update(resource);
		}
		return smartResp;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> delete(String id) {
		return resServ.delete(id);
	}
	
	@RequestMapping(value="/selectResAuth")
	public @ResponseBody SmartResponse<ZTreeData>  selectResAuth(String id) throws Exception {
		return resServ.selectResAuthTree(id);
	}
	
	
}
