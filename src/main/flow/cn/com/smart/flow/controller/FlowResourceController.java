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
import cn.com.smart.constant.ResourceConstant;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.entity.TNResource;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.service.ResourceService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 流程资源
 * @author lmq
 *
 */
@Controller
@RequestMapping("flow/resource")
public class FlowResourceController extends BaseFlowControler {

	private static final String VIEW_DIR = "flow/resource";
	
	@Autowired
	private OPService opServ;
	@Autowired
	private ResourceService resServ;
	
	@RequestMapping("/list")
	public ModelAndView list(HttpSession session,ModelAndView modelView,String orgId,RequestPage page) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orgIds", StringUtils.list2Array(getUserInfoFromSession(session).getOrgIds()));
		SmartResponse<Object> smartResp = opServ.getDatas("flow_resource_mgr_list",params, page.getStartNum(), page.getPageSize());
		params = null;
		String uri = "flow/resource/list";
		addBtn = new EditBtn("add","showPage/flow_resource_add", "添加流程菜单资源", "600");
		editBtn = new EditBtn("edit","showPage/flow_resource_edit", "resource", "修改流程菜单资源", "600");
		delBtn = new DelBtn("resource/delete.json", "确定要删除选中的流程菜单资源吗？",uri,null, null);
		refreshBtn = new RefreshBtn(uri, "resource",null);
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
	public @ResponseBody SmartResponse<String> add(TNResource resource) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != resource) {
			resource.setType(ResourceConstant.FLOW_RESOURCE);
			//String uri = "process/form?processId="+resource.getUri();
			String uri = "process/form?processName="+resource.getUri();
			resource.setUri(uri);
			smartResp = resServ.save(resource);
		}
		return smartResp;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TNResource resource) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != resource) {
			resource.setType(ResourceConstant.FLOW_RESOURCE);
			String uri = "process/form?processName="+resource.getUri();
			resource.setUri(uri);
			smartResp = resServ.update(resource);
		}
		return smartResp;
	}
	
}
