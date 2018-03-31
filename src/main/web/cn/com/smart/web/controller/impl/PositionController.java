package cn.com.smart.web.controller.impl;

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

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.entity.TNPosition;
import cn.com.smart.web.constant.enums.SelectedEventType;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.filter.bean.UserSearchParam;
import cn.com.smart.web.plugins.OrgPositionZTreeData;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.service.PositionService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;
import cn.com.smart.web.tag.bean.SelectedEventProp;

/**
 * 岗位
 * @author lmq
 *
 */
@Controller
@RequestMapping("/position")
public class PositionController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/position";
	
	@Autowired
	private PositionService posServ;
	@Autowired
	private OPService opServ;
	
	/**
	 * 职位列表
	 * @param session
	 * @param modelView
	 * @param orgId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpSession session,ModelAndView modelView,String orgId,RequestPage page) throws Exception {
		Map<String,Object> params = null;
		orgId = "0".equals(orgId)?null:orgId;
		params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(orgId)) {
			params.put("orgId", orgId);
		}
		params.put("orgIds", StringUtils.list2Array(getUserInfoFromSession(session).getOrgIds()));
		SmartResponse<Object> smartResp = opServ.getDatas("position_mgr_list",params, page.getStartNum(), page.getPageSize());
		params = null;
		String uri = "position/list?orgId="+StringUtils.handleNull(orgId);
		addBtn = new EditBtn("add","showPage/base_position_add?id="+StringUtils.handleNull(orgId),null, "添加职位", "600");
		editBtn = new EditBtn("edit","showPage/base_position_edit", "position", "修改职位", "600");
		delBtn = new DelBtn("position/delete", "确定要删除选中的职位吗？",uri,"#position-list", null);
		refreshBtn = new RefreshBtn(uri, "position","#position-list");
		pageParam = new PageParam(uri, "#position-list", page.getPage(), page.getPageSize());
		
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
	
	/**
	 * 添加职位
	 * @param position
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> add(TNPosition position) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != position) {
			smartResp = posServ.save(position);
		}
		return smartResp;
	}
	
	/**
	 * 修改职位信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TNPosition position) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != position) {
			smartResp = posServ.update(position);
		}
		return smartResp;
	}
	
	/**
	 * 删除职位
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", produces="application/json;charset=UTF-8")
	@ResponseBody
	public SmartResponse<String> delete(String id) {
	    return posServ.delete(id);
	}
	
	/**
	 * 职位简单列表
	 * @param session
	 * @param searchParam
	 * @param modelView
	 * @param page
	 * @return
	 */
	@RequestMapping("/simplist")
	public ModelAndView simplist(HttpSession session,UserSearchParam searchParam,
    		ModelAndView modelView,RequestPage page) {
		String uri = "position/simplist";
		searchParam.setOrgIds(StringUtils.list2Array(getUserInfoFromSession(session).getOrgIds()));
		SmartResponse<Object> smartResp = opServ.getDatas("position_simp_list",searchParam, page.getStartNum(), page.getPageSize());
		pageParam = new PageParam(uri, "#position-tab", page.getPage(), page.getPageSize());
		selectedEventProp = new SelectedEventProp(SelectedEventType.OPEN_TO_TARGET.getValue(),"auth/positionHas","#has-auth-list","id");	

		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("searchParam", searchParam);
		modelMap.put("selectedEventProp", selectedEventProp);
		
		modelView.setViewName(VIEW_DIR+"/simplist");
		return modelView;
	}
	
	/**
	 * 该用户拥有的角色列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rolelist")
	public ModelAndView rolelist(HttpSession session,UserSearchParam searchParam,
    		ModelAndView modelView,RequestPage page) throws Exception {
		String uri = "position/rolelist";
		searchParam.setOrgIds(StringUtils.list2Array(getUserInfoFromSession(session).getOrgIds()));
		SmartResponse<Object> smartResp = opServ.getDatas("position_role_list",searchParam, page.getStartNum(), page.getPageSize());
		pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
		uri = uri+"?id="+searchParam.getId();	
		addBtn = new EditBtn("add","position/addRole?id="+searchParam.getId(), "该职位中添加角色", "600");
		delBtn = new DelBtn("position/deleteRole?positionId="+searchParam.getId(), "确定要从该职位中删除选中的角色吗？",uri,"#position-role-tab", null);
		refreshBtn = new RefreshBtn(uri, null,"#position-role-tab");
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("searchParam", searchParam);
		modelMap.put("addBtn", addBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		
		modelView.setViewName(VIEW_DIR+"/rolelist");
		return modelView;
	}
	
	
	/**
	 * 职位中添加角色（视图）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addRole")
	public ModelAndView addRole(FilterParam searchParam,ModelAndView modelView,RequestPage page) throws Exception {
		String uri = "position/addRole";
		SmartResponse<Object> smartResp = opServ.getDatas("position_addrole_list",searchParam, page.getStartNum(), page.getPageSize());
		pageParam = new PageParam(uri, ".bootstrap-dialog-message", page.getPage(), page.getPageSize());
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("searchParam", searchParam);
		
		modelView.setViewName(VIEW_DIR+"/addRole");
		return modelView;
	}
	
	/**
	 * 职位中添加角色
	 * @param submitDatas
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveRole",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> saveUser(String submitDatas,String id) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(submitDatas) && StringUtils.isNotEmpty(id)) {
			String[] values = submitDatas.split(",");
			smartResp = posServ.addRole2Position(id, values);
		}
		return smartResp;
	}
	
	/**
	 * 菜单树
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orgTree")
	public @ResponseBody SmartResponse<OrgPositionZTreeData> orgTree(HttpSession session) throws Exception {
		return posServ.getOrgPositionZTree(getUserInfoFromSession(session).getOrgIds());
	}
	
	/**
     * 从职位中删除角色
     * @param positionId 职位ID
     * @param id 角色ID
     * @return 返回操作结果
     */
    @RequestMapping(value="/deleteRole", produces="application/json;charset=UTF-8")
    @ResponseBody
    public SmartResponse<String> deleteRole(String positionId, String id) {
        return posServ.deleteRole(positionId, id);
    }
}
