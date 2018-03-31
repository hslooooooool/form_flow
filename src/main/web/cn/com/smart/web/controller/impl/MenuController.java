package cn.com.smart.web.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.bean.entity.TNMenu;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.plugins.ZTreeData;
import cn.com.smart.web.service.MenuService;
import cn.com.smart.web.service.RoleMenuService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.RefreshBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 菜单
 * @author lmq
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/menu";
	
	@Autowired
	private MenuService menuServ;
	@Autowired
	private RoleMenuService roleMenuServ;
	
	@RequestMapping("list")
	public ModelAndView list(ModelAndView modelView) throws Exception {
		SmartResponse<Object> smartResp = menuServ.findObjAll();
		
		String uri = "menu/list"; 
		addBtn = new EditBtn("add","showPage/base_menu_add", "menu", "添加菜单", "600");
		editBtn = new EditBtn("edit","showPage/base_menu_edit", "menu", "修改菜单", "600");
		delBtn = new DelBtn("menu/delete.json", "确定要删除选中的菜单吗？（注：该菜单下面的子菜单也会一起删除的哦~）",uri,null, null);
		refreshBtn = new RefreshBtn(uri, "menu",null);
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		addBtn = null;editBtn = null;delBtn = null;
		refreshBtn = null;
		modelView.setViewName(VIEW_DIR+"/list");
		return modelView;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> add(TNMenu menu) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != menu) {
			smartResp = menuServ.save(menu);
		}
		return smartResp;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TNMenu menu) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != menu) {
			smartResp = menuServ.update(menu);
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
		return menuServ.delete(id);
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/treeSelect")
	public @ResponseBody SmartResponse<ZTreeData> treeSelect(String id) throws Exception {
		SmartResponse<ZTreeData> smartResp = null;
		if(StringUtils.isNotEmpty(id)) {
			smartResp = roleMenuServ.menuTree(id);
		} else {
			smartResp = roleMenuServ.menuTree();
		}
		return smartResp;
	}
}
