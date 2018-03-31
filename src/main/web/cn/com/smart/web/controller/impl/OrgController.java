package cn.com.smart.web.controller.impl;

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
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNOrg;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.filter.bean.UserSearchParam;
import cn.com.smart.web.plugins.OrgZTreeData;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.service.OrgService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 组织机构
 * @author lmq
 *
 */
@Controller
@RequestMapping("org")
public class OrgController extends BaseController {

private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/org";
	
	@Autowired
	private OrgService orgServ;
	@Autowired
	private OPService opServ;
	
	@RequestMapping("list")
	public ModelAndView list(ModelAndView modelView) throws Exception {
		SmartResponse<Object> smartResp = opServ.getTreeDatas("org_mgr_tree_list");
		
		String uri = "org/list"; 
		addBtn = new EditBtn("add","showPage/base_org_add", "org", "添加组织机构", "600");
		editBtn = new EditBtn("edit","showPage/base_org_edit", "org", "修改组织机构", "600");
		delBtn = new DelBtn("org/delete.json", "确定要删除选中的组织机构吗？（注：如果该机构下面有子机构的话，会一起删除的哦~）",uri,null, null);
		refreshBtn = new RefreshBtn(uri, "org",null);
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelView.setViewName(VIEW_DIR+"/list");
		return modelView;
	}
	
	/**
	 * 添加组织架构
	 * @param session
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> add(HttpSession session, TNOrg org) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != org) {
			smartResp = orgServ.save(org);
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				UserInfo userInfo = super.getUserInfoFromSession(session);
				userInfo.getOrgIds().add(smartResp.getData());
			}
		}
		return smartResp;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TNOrg org) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != org) {
			smartResp = orgServ.update(org);
		}
		return smartResp;
	}
	
	/**
	 * 删除
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> delete(HttpSession session, String id) {
		SmartResponse<String> smartResp = orgServ.delete(id);
		if(OP_SUCCESS.equals(smartResp.getResult())) {
			UserInfo userInfo = super.getUserInfoFromSession(session);
			userInfo.getOrgIds().remove(id);
		}
		return smartResp;
	}
	
	@RequestMapping("/tree")
	public @ResponseBody SmartResponse<OrgZTreeData> tree(HttpSession session,String treeType) throws Exception {
		return orgServ.getZTree(getUserInfoFromSession(session).getOrgIds(),treeType);
	}
	
	
	
	/**
	 * 该用户拥有的角色列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rolelist")
	public ModelAndView rolelist(UserSearchParam searchParam,ModelAndView modelView,RequestPage page) throws Exception {
		String uri = "org/rolelist";
		SmartResponse<Object> smartResp = opServ.getDatas("org_role_list",searchParam, page.getStartNum(), page.getPageSize());
		pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
		uri = uri+"?id="+searchParam.getId();
		addBtn = new EditBtn("add","org/addRole?id="+searchParam.getId(), "该组织机构中添加角色", "600");
		delBtn = new DelBtn("org/deleteRole?orgId="+searchParam.getId(), "确定要从该组织机构中删除选中的角色吗？",uri,"#org-role-tab", null);
		refreshBtn = new RefreshBtn(uri, null,"#org-role-tab");
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelMap.put("searchParam", searchParam);
		modelView.setViewName(VIEW_DIR+"/rolelist");
		return modelView;
	}
	
	/**
	 * 从组织架构中删除角色
	 * @param orgId 组织架构ID
 	 * @param id 角色ID
	 * @return 返回操作结果
	 */
	@RequestMapping(value="/deleteRole", produces="application/json;charset=UTF-8")
	@ResponseBody
	public SmartResponse<String> deleteRole(String orgId, String id) {
	    return orgServ.deleteRole(orgId, id);
	}
	
	/**
	 * 组织架构中添加角色（试图）
	 * @param searchParam
	 * @param modelView
	 * @param page
	 * @return
	 */
	@RequestMapping("/addRole")
	public ModelAndView addRole(UserSearchParam searchParam,ModelAndView modelView,RequestPage page) {
		String uri = "org/addRole";
		SmartResponse<Object> smartResp = opServ.getDatas("org_addrole_list",searchParam, page.getStartNum(), page.getPageSize());
		String paramUri = uri += (null != searchParam)?("?"+searchParam.getParamToString()):"";
		pageParam = new PageParam(paramUri, ".bootstrap-dialog-message", page.getPage(), page.getPageSize());
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("pageParam", pageParam);
		modelMap.put("searchParam", searchParam);
		modelView.setViewName(VIEW_DIR+"/addRole");
		return modelView;
		
	}
	
	/**
	 * 组织架构中添加角色
	 * @param id
	 * @param submitDatas
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveRole",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> saveRole(String id,String submitDatas) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(submitDatas) && StringUtils.isNotEmpty(id)) {
			String[] values = submitDatas.split(",");
			smartResp = orgServ.addRole2Org(id, values);
		}
		return smartResp;
	}
	
}
