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
import cn.com.smart.web.bean.entity.TNMinWindow;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.MinWinService;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 小窗口
 * @author lmq
 *
 */
@Controller
@RequestMapping("/minwin")
public class MinWindowController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/minwin";
	
	@Autowired
	private MinWinService minWinServ;
	@Autowired
	private OPService opServ;
	
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView modelView,RequestPage page) throws Exception {
		SmartResponse<Object> smartResp = opServ.getDatas("minwin_mgr_list", page.getStartNum(), page.getPageSize());
		
		String uri = "minwin/list"; 
		addBtn = new EditBtn("add","showPage/base_minwin_add", "minWin", "添加小窗口", "600");
		editBtn = new EditBtn("edit","showPage/base_minwin_edit", "minWin", "修改小窗口", "600");
		delBtn = new DelBtn("minwin/delete", "确定要删除选中的小窗口吗？",uri,null, null);
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
	public @ResponseBody SmartResponse<String> add(TNMinWindow minWin) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != minWin) {
			smartResp = minWinServ.save(minWin);
		}
		return smartResp;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TNMinWindow minWin) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != minWin) {
			smartResp = minWinServ.update(minWin);
		}
		return smartResp;
	}
	
	@RequestMapping("/items")
	public @ResponseBody SmartResponse<Object> items(String name) throws Exception {
		SmartResponse<Object> smartResp = minWinServ.getItem(name);
		return smartResp;
	}
	
	@RequestMapping("/view")
	public @ResponseBody SmartResponse<Object> view(String id) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(id)) {
			smartResp = minWinServ.find(TNMinWindow.class, id);
		}
		return smartResp;
	}
	
	@RequestMapping(value="/delete", produces="application/json;charset=UTF-8")
    @ResponseBody
    public SmartResponse<String> delete(String id) {
        return minWinServ.delete(id);
    }
}
