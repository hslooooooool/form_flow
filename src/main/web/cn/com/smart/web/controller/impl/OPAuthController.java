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
import cn.com.smart.web.bean.entity.TNOPAuth;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.OPAuthService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 操作权限
 * @author lmq
 *
 */
@Controller
@RequestMapping("/opauth")
public class OPAuthController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/opauth";
	
	@Autowired
	private OPAuthService authServ;

	@RequestMapping("/list")
	public ModelAndView list(ModelAndView modelView,RequestPage page) throws Exception {
		SmartResponse<Object> smartResp = authServ.findAll(page.getStartNum(), page.getPageSize());
		
		String uri = "opauth/list"; 
		addBtn = new EditBtn("add","showPage/base_opauth_add", "opAuth", "添加操作权限", "600");
		editBtn = new EditBtn("edit","showPage/base_opauth_edit", "opAuth", "修改操作权限", "600");
		delBtn = new DelBtn("opauth/delete.json", "确定要删除选中的操作权限吗？",uri,null, null);
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
	public @ResponseBody SmartResponse<String> add(TNOPAuth opAuth) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != opAuth) {
			smartResp = authServ.save(opAuth);
		}
		return smartResp;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TNOPAuth opAuth) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != opAuth) {
			smartResp = authServ.update(opAuth);
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
		return authServ.delete(id);
	}
	
	
	
	@RequestMapping("/selectAuth")
	public ModelAndView selectAuth(ModelAndView modelView,String ids) throws Exception {
		SmartResponse<TNOPAuth> smartResp = null;
		if(StringUtils.isNotEmpty(ids)) {
			smartResp = authServ.findAuth(ids.split(","));
		} else {
			smartResp = authServ.findAll();
		}
		modelView.getModelMap().put("smartResp", smartResp);
		modelView.setViewName(VIEW_DIR+"/selectAuth");
		return modelView;
	}
	
}
