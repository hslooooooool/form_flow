package cn.com.smart.flow.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.flow.bean.entity.TFlowPageModel;
import cn.com.smart.flow.service.FlowPageModelService;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 流程页面模版
 * @author lmq
 *
 */
@Controller
@RequestMapping("/flow/page/model")
public class FlowPageModelController extends BaseController {
	private static final String VIEW_DIR = "flow/page/model";
	@Autowired
	private OPService opServ;
	@Autowired
	private FlowPageModelService pageModelServ;
	
	@RequestMapping("/list")
	public ModelAndView list(HttpSession session,ModelAndView modelView,String orgId,RequestPage page) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orgIds", StringUtils.list2Array(getUserInfoFromSession(session).getOrgIds()));
		SmartResponse<Object> smartResp = opServ.getDatas("page_model_mgr_list",params, page.getStartNum(), page.getPageSize());
		params = null;
		String uri = "flow/page/model/list";
		addBtn = new EditBtn("add","showPage/flow_page_model_add", "添加页面模版", "600");
		editBtn = new EditBtn("edit","showPage/flow_page_model_edit", "flowPageModel", "修改页面模版", "600");
		delBtn = new DelBtn("flow/page/model/delete", "确定要删除选中的页面模版吗？",uri,null, null);
		refreshBtn = new RefreshBtn(uri, null,null);
		pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelView.setViewName(VIEW_DIR+"/list");
		return modelView;
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> add(HttpSession session,TFlowPageModel pageModel) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != pageModel) {
			pageModel.setUserId(getUserInfoFromSession(session).getId());
			smartResp = pageModelServ.save(pageModel);
		}
		return smartResp;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TFlowPageModel pageModel) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != pageModel) {
			smartResp = pageModelServ.update(pageModel);
		}
		return smartResp;
	}
	
	@RequestMapping(value="/delete", produces="application/json;charset=UTF-8")
    @ResponseBody
    public SmartResponse<String> delete(String id) {
        return pageModelServ.delete(id);
    }
	
	@RequestMapping("/item")
	public @ResponseBody SmartResponse<Object> item() throws Exception {
		SmartResponse<Object> smartResp = opServ.getDatas("select_page_model_item");
		return smartResp;
	}
	
}
