package cn.com.smart.web.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.entity.TNUserSetMenu;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.UserSettingService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 用户设置
 * @author lmq
 *
 */
@Controller
@RequestMapping("/user/setting")
public class UserSettingController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/user/setting";
	
	@Autowired
	private UserSettingService userSettingServ;
	
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView modelView) throws Exception {
		SmartResponse<TNUserSetMenu> smartResp = userSettingServ.findValidSetting();
		modelView.getModelMap().put("smartResp", smartResp);
		modelView.setViewName(VIEW_DIR+"/index");
		return modelView;
	}
	
	
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView modelView,RequestPage page) throws Exception {
		 SmartResponse<Object> smartResp = userSettingServ.findSettingMenus();
		
		String uri = "user/setting/list"; 
		addBtn = new EditBtn("add","showPage/base_user_setting_add", "userSetMenu", "添加个人设置菜单", "600");
		editBtn = new EditBtn("edit","showPage/base_user_setting_edit", "userSetMenu", "修改个人设置菜单", "600");
		delBtn = new DelBtn("user/setting/delete", "确定要删除选中的用户设置吗？",uri,null, null);
		refreshBtn = new RefreshBtn(uri, null,null);
		pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		
		addBtn = null;editBtn = null;delBtn = null;
		refreshBtn = null;pageParam = null;
		
		modelView.setViewName(VIEW_DIR+"/list");
		return modelView;
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> add(TNUserSetMenu userSetMenu) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != userSetMenu) {
			smartResp = userSettingServ.save(userSetMenu);
		}
		return smartResp;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TNUserSetMenu userSetMenu) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != userSetMenu) {
			smartResp = userSettingServ.update(userSetMenu);
		}
		return smartResp;
	}
	
	@RequestMapping(value="/delete", produces="application/json;charset=UTF-8")
	@ResponseBody
    public SmartResponse<String> delete(String id) {
        return userSettingServ.delete(id);
    }
	
}
