package cn.com.smart.web.controller.impl;

import java.util.ArrayList;
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
import cn.com.smart.web.bean.SubmitDataBean;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNRole;
import cn.com.smart.web.constant.enums.SelectedEventType;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.plugins.ZTreeData;
import cn.com.smart.web.service.MenuService;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.service.ResourceService;
import cn.com.smart.web.tag.bean.ALink;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;
import cn.com.smart.web.tag.bean.SelectedEventProp;

/**
 * 角色
 * @author lmq
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/role";
	
	@Autowired
	private OPService opServ;
	@Autowired
	private MenuService menuServ;
	@Autowired
	private ResourceService resServ;
	
	@RequestMapping("/list")
	public ModelAndView list(HttpSession session,ModelAndView modelView,RequestPage page) {
		Map<String,Object> params = null;
		UserInfo userInfo = getUserInfoFromSession(session);
		if(!isSuperAdmin(userInfo)) {
			params = new HashMap<String, Object>(1);
			params.put("roleIds", userInfo.getRoleIds().toArray());
		}
		SmartResponse<Object> smartResp = opServ.getDatas("role_mgr_list", params, page.getStartNum(), page.getPageSize());
		
		String uri = "role/list"; 
		addBtn = new EditBtn("add","showPage/base_role_add", "添加角色", "600");
		editBtn = new EditBtn("edit","showPage/base_role_edit", "role", "修改角色", "600");
		delBtn = new DelBtn("role/delete.json", "确定要删除选中的角色吗？",uri,null, null);
		refreshBtn = new RefreshBtn(uri, null,null);
		pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
		
		alinks = new ArrayList<ALink>();
		ALink link = new ALink();
		link.setUri("role/show");
		link.setDialogTitle("已配置的权限");
		link.setDialogWidth("600");
		alinks.add(link);
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelMap.put("alinks", alinks);

		modelView.setViewName(VIEW_DIR+"/list");
		return modelView;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> add(HttpSession session,TNRole role,
			String[] menuId,SubmitDataBean submitBean) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != role) {
			role.setUserId(getUserInfoFromSession(session).getId());
			smartResp = roleServ.save(role,menuId,(null != submitBean ? submitBean.getTreeProps():null));
		}
		return smartResp;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TNRole role,String[] menuId,SubmitDataBean submitBean) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != role) {
			smartResp = roleServ.update(role,menuId,(null != submitBean ? submitBean.getTreeProps():null));
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
		return roleServ.delete(id);
	}
	
	/**
	 * 查看角色信息
	 * @param modelView
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/show")
	public ModelAndView show(ModelAndView modelView,String id) throws Exception {
		modelView.getModelMap().put("id", id);
		modelView.setViewName(VIEW_DIR+"/show");
		return modelView;
	}
	
	
	/**
	 * 简单角色列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/simplist")
	public ModelAndView simplist(HttpSession session,FilterParam searchParam,
			ModelAndView modelView,RequestPage page) throws Exception {
		String uri = "role/simplist";
		UserInfo userInfo = getUserInfoFromSession(session);
		if(!isSuperAdmin(userInfo)) {
			if(null == searchParam) {
				searchParam = new FilterParam();
			}
			searchParam.setRoleIds(StringUtils.list2Array(userInfo.getRoleIds()));
		}
		SmartResponse<Object> smartResp = opServ.getDatas("role_simp_list",searchParam, page.getStartNum(), page.getPageSize());
		pageParam = new PageParam(uri, "#role-tab", page.getPage(), page.getPageSize());
		selectedEventProp = new SelectedEventProp(SelectedEventType.OPEN_TO_TARGET.getValue(),"auth/roleHas","#has-auth-list","id");	
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("selectedEventProp", selectedEventProp);
		modelMap.put("searchParam", searchParam);
		
		modelView.setViewName(VIEW_DIR+"/simplist");
		return modelView;
	}
	
	
	/**
	 * 拥有该角色的用户列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userlist")
	public ModelAndView userlist(FilterParam searchParam,ModelAndView modelView,RequestPage page) throws Exception {
		String uri = "role/userlist";
		SmartResponse<Object> smartResp = opServ.getDatas("role_user_list",searchParam, page.getStartNum(), page.getPageSize());
		pageParam = new PageParam(uri, "#role-user-tab", page.getPage(), page.getPageSize());
		uri = uri+"?id="+searchParam.getId();
		addBtn = new EditBtn("add","role/addUser?id="+searchParam.getId(), null, "该角色中添加用户", "600");
		delBtn = new DelBtn("role/deleteUser?roleId="+searchParam.getId(), "确定要从该角色中删除选中的用户吗？",uri,"#role-user-tab", null);
		refreshBtn = new RefreshBtn(uri, "roleUser","#role-user-tab");
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("addBtn", addBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("searchParam", searchParam);
		
		modelView.setViewName(VIEW_DIR+"/userlist");
		return modelView;
	}
	
	/**
	 * 从角色中删除用户
	 * @param roleId 角色ID
	 * @param id 用户ID
	 * @return
	 */
	@RequestMapping(value="/deleteUser", produces="application/json;charset=UTF-8")
	@ResponseBody
	public SmartResponse<String> deleteUser(String roleId, String id) {
	    return roleServ.deleteUser(roleId, id);
	}
	
	/**
	 * 拥有该角色的组织机构列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orglist")
	public ModelAndView orglist(FilterParam searchParam,ModelAndView modelView,RequestPage page) throws Exception {
		String uri = "role/orglist";
		SmartResponse<Object> smartResp = opServ.getDatas("role_org_list",searchParam, page.getStartNum(), page.getPageSize());
		pageParam = new PageParam(uri, "#role-org-tab", page.getPage(), page.getPageSize());
		uri = uri+"?id="+searchParam.getId();
		addBtn = new EditBtn("add","role/addOrg?id="+searchParam.getId(), null, "该角色中添加组织机构", "600");
		delBtn = new DelBtn("role/deleteOrg?roleId="+searchParam.getId(), "确定要从该角色中删除选中的组织机构吗？",uri,"#role-org-tab", null);
		refreshBtn = new RefreshBtn(uri, "roleOrg","#role-org-tab");
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("addBtn", addBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("searchParam", searchParam);
		
		modelView.setViewName(VIEW_DIR+"/orglist");
		
		return modelView;
	}
	
	/**
     * 从角色中删除组织架构
     * @param roleId 角色ID
     * @param id 组织架构ID
     * @return 返回操作结果
     */
    @RequestMapping(value="/deleteOrg", produces="application/json;charset=UTF-8")
    @ResponseBody
    public SmartResponse<String> deleteOrg(String roleId, String id) {
        return roleServ.deleteOrg(roleId, id);
    }
	
	/**
	 * 拥有该角色的岗位列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/positionlist")
	public ModelAndView positionlist(FilterParam searchParam,ModelAndView modelView,RequestPage page) throws Exception {
		String uri = "role/positionlist";
		SmartResponse<Object> smartResp = opServ.getDatas("role_position_list",searchParam, page.getStartNum(), page.getPageSize());
		pageParam = new PageParam(uri, "#role-position-tab", page.getPage(), page.getPageSize());
		uri = uri+"?id="+searchParam.getId();
		addBtn = new EditBtn("add","role/addPosition?id="+searchParam.getId(), "该角色中添加职位", "600");
		delBtn = new DelBtn("role/deletePosition?roleId="+searchParam.getId(), "确定要从该角色中删除选中的职位吗？",uri,"#role-position-tab", null);
		refreshBtn = new RefreshBtn(uri, "rolePosition","#role-position-tab");
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("addBtn", addBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("searchParam", searchParam);
		
		modelView.setViewName(VIEW_DIR+"/positionlist");
		return modelView;
	}
	
	/**
     * 从角色中删除职位
     * @param roleId 角色ID
     * @param id 职位ID
     * @return 返回操作结果
     */
    @RequestMapping(value="/deletePosition", produces="application/json;charset=UTF-8")
    @ResponseBody
    public SmartResponse<String> deletePosition(String roleId, String id) {
        return roleServ.deletePosition(roleId, id);
    }
	
	
	/**
	 * 查看该角色拥有的菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewMenus")
	public @ResponseBody SmartResponse<ZTreeData> viewMenus(String id) throws Exception {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		if(StringUtils.isNotEmpty(id)) {
			smartResp = menuServ.roleMenuTree(id);
		}
		return smartResp;
	}
	
	
	/**
	 * 查看该角色拥有的资源
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewRes")
	public @ResponseBody SmartResponse<ZTreeData> viewRes(String id) throws Exception {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		if(StringUtils.isNotEmpty(id)) {
			smartResp = resServ.selectedResAuthTree(id);
		}
		return smartResp;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addUser")
	public ModelAndView addUser(FilterParam searchParam,ModelAndView modelView,RequestPage page) throws Exception {
		String uri = "role/addUser";
		SmartResponse<Object> smartResp = opServ.getDatas("role_adduser_list",searchParam, page.getStartNum(), page.getPageSize());
		pageParam = new PageParam(uri, ".bootstrap-dialog-message", page.getPage(), page.getPageSize());
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("searchParam", searchParam);
		
		modelView.setViewName(VIEW_DIR+"/addUser");
		return modelView;
	}
	
	@RequestMapping(value="/saveUser",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> saveUser(String submitDatas,String id) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(submitDatas) && StringUtils.isNotEmpty(id)) {
			String[] values = submitDatas.split(",");
			smartResp = roleServ.addUser2Role(id, values);
		}
		return smartResp;
	}
	
	

	/**
	 * 
	 * @param searchParam
	 * @param modelView
	 * @param id
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrg")
	public ModelAndView addOrg(FilterParam searchParam,ModelAndView modelView,RequestPage page) throws Exception {
		String uri = "role/addOrg";
		SmartResponse<Object> smartResp = opServ.getDatas("role_addorg_list",searchParam, page.getStartNum(), page.getPageSize());
		pageParam = new PageParam(uri, ".bootstrap-dialog-message", page.getPage(), page.getPageSize());
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("searchParam", searchParam);
		modelView.setViewName(VIEW_DIR+"/addOrg");
		return modelView;
	}
	
	/**
	 * 
	 * @param submitDatas
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveOrg",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> saveOrg(String submitDatas,String id) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(submitDatas) && StringUtils.isNotEmpty(id)) {
			String[] values = submitDatas.split(",");
			smartResp = roleServ.addOrg2Role(id, values);
		}
		return smartResp;
	}
	

	/**
	 * 角色中添加岗位
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPosition")
	public ModelAndView addPosition(FilterParam searchParam,ModelAndView modelView,RequestPage page) throws Exception {
			String uri = "role/addPosition";
			SmartResponse<Object> smartResp = opServ.getDatas("role_addposition_list",searchParam, page.getStartNum(), page.getPageSize());
			pageParam = new PageParam(uri, ".bootstrap-dialog-message", page.getPage(), page.getPageSize());
			ModelMap modelMap = modelView.getModelMap();
			modelMap.put("smartResp", smartResp);
			modelMap.put("pageParam", pageParam);
			modelMap.put("searchParam", searchParam);
			modelView.setViewName(VIEW_DIR+"/addPosition");
			return modelView;
	}
	
	/**
	 * 角色中添加岗位
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/savePosition",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> savePosition(String submitDatas,String id) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(submitDatas) && StringUtils.isNotEmpty(id)) {
			String[] values = submitDatas.split(",");
			smartResp = roleServ.addPosition2Role(id, values);
		}
		return smartResp;
	}
	
	
}
