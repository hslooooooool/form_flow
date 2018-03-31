package cn.com.smart.web.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.constant.enums.BtnPropType;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.filter.bean.UserSearchParam;
import cn.com.smart.web.helper.PageHelper;
import cn.com.smart.web.push.MessageType;
import cn.com.smart.web.push.impl.PushMessageContext;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.service.UserService;
import cn.com.smart.web.tag.bean.CustomBtn;
import cn.com.smart.web.tag.bean.CustomTableCell;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 测试
 * @author lmq
 *
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/test";
	
	@Autowired
	private OPService opServ;
	@Autowired
	private UserService userServ;
	@Autowired
	private PushMessageContext pushMsgContext;
	
	@RequestMapping("/upload")
	public ModelAndView upload(ModelAndView modelView) throws Exception {
		modelView.setViewName(VIEW_DIR+"/upload");
		return modelView;
	}
	
	@RequestMapping("/test")
	public ModelAndView test(ModelAndView modelView) throws Exception {
		modelView.setViewName(VIEW_DIR+"/test");
		return modelView;
	}
	
	@RequestMapping("/modal")
	public ModelAndView modal(ModelAndView modelView) throws Exception {
		modelView.setViewName(VIEW_DIR+"/modal");
		return modelView;
	}
	
	@RequestMapping("/date")
	public ModelAndView date(ModelAndView modelView) throws Exception {
		modelView.setViewName(VIEW_DIR+"/date");
		return modelView;
	}
	
	@RequestMapping("/jqGrid")
	public ModelAndView jqGrid(ModelAndView modelView) throws Exception {
		modelView.setViewName(VIEW_DIR+"/jqGrid");
		return modelView;
	}
	
	@RequestMapping("/auth")
	public ModelAndView auth(ModelAndView modelView) throws Exception {
		modelView.setViewName(VIEW_DIR+"/auth");
		return modelView;
	}
	
	@RequestMapping("/tabs")
	public ModelAndView tabs(ModelAndView modelView) throws Exception {
		modelView.setViewName(VIEW_DIR+"/tabs");
		return modelView;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView modelView) throws Exception {
		modelView.setViewName("login_test");
		return modelView;
	}
	
	@RequestMapping("/inputSelect")
	public ModelAndView inputSelect() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName(VIEW_DIR+"/inputSelect");
		return modelView;
	}
	
	@RequestMapping("/tableAsyncTreeJson")
	@ResponseBody
	public SmartResponse<Object> tableAsyncTreeJson(String parentId) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(parentId)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parentId", parentId);
			smartResp = opServ.getTreeDatas("test_org_async_tree", params);
		}
		return smartResp;
	}
	
	@RequestMapping("/tableAsyncTree")
	public ModelAndView tableAsyncTree() {
		ModelAndView modelView = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", "0");
		SmartResponse<Object> smartResp = opServ.getTreeDatas("test_org_async_tree", params);
		
		String uri = "org/list"; 
		addBtn = new EditBtn("add","showPage/base_org_add", "org", "添加菜单", "600");
		editBtn = new EditBtn("edit","showPage/base_org_edit", "org", "修改菜单", "600");
		delBtn = new DelBtn("org/delete",  "确定要删除选中的组织机构吗？（注：如果该机构下面有子机构的话，会一起删除的哦~）",uri,null, null);
		refreshBtn = new RefreshBtn(uri, "org",null);
		ModelMap modelMap = modelView.getModelMap();
		
		addBtn.setBeforeCheck("beforeCheck()");
		editBtn.setBeforeCheck("beforeCheck()");
		
		CustomBtn cusBtn = new CustomBtn("test_btn", "自定义按钮", "自定义按钮", "");
		customBtns = new ArrayList<CustomBtn>();
		customBtns.add(cusBtn);
		modelMap.put("customBtns", customBtns);
		
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("asyncUrl", "test/tableAsyncTreeJson.json");
		addBtn = null;editBtn = null;delBtn = null;
		refreshBtn = null;
		modelView.setViewName(VIEW_DIR+"/tableAsyncTree");
		return modelView;
	}
	
	
	@RequestMapping("/table")
	public ModelAndView tableDatas(HttpSession session,UserSearchParam searchParam,ModelAndView modelView,Integer page) throws Exception {
		page = null == page?1:page;
		page = PageHelper.getPage(page);
		String uri = "user/list"; 
		addBtn = new EditBtn("add","showPage/base_user_add", null, "添加用户", "600");
		editBtn = new EditBtn("edit","showPage/base_user_edit", "user", "修改用户信息", "600");
		addBtn.setBeforeCheck("beforeCheck2");
		editBtn.setBeforeCheck("beforeCheck()");
		String paramStr = null;
		paramStr = (null != searchParam)?searchParam.getParamToString():null;
		uri += StringUtils.isNotEmpty(paramStr)?("?"+paramStr):"";
		uri = StringUtils.isContains(uri, "?")?uri+"&page=":uri+"?page=";
		uri += page;
		refreshBtn = new RefreshBtn(uri, "resource",null);
		
		CustomBtn customBtn = new CustomBtn("xxx", null, "自定义按钮", "","glyphicon-pencil");
		List<CustomBtn> customBtns = new ArrayList<CustomBtn>(1);
		customBtns.add(customBtn);
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("customBtns", customBtns);
		
		if("0".equals(searchParam.getOrgId())) {
			searchParam.setOrgId(null);
		}
		searchParam.setOrgIds(StringUtils.list2Array(getUserInfoFromSession(session).getOrgIds()));
		pageParam = new PageParam(uri, null, page);
		
		SmartResponse<Object> smartResp = userServ.findAllObj(searchParam, getStartNum(page), getPerPageSize());
		
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelMap.put("searchParam", searchParam);
		
		
		addBtn = null;delBtn = null;
		refreshBtn = null;pageParam = null;
		
		modelView.setViewName(VIEW_DIR+"/table");
		return modelView;
	}
	
	@RequestMapping("/jqueryMethod")
	public ModelAndView jqueryMethod(ModelAndView modelView) throws Exception {
		modelView.setViewName(VIEW_DIR+"/jqueryMethod");
		return modelView;
	}
	
	@RequestMapping("/cellList")
	public ModelAndView cellList(HttpSession session,UserSearchParam searchParam,Integer page) {
		ModelAndView modelView = new ModelAndView();
		page = null == page?1:page;
		page = PageHelper.getPage(page);
		String uri = "test/cellList"; 
		addBtn = new EditBtn("add","showPage/base_user_add", null, "添加用户", "600");
		editBtn = new EditBtn("edit","showPage/base_user_edit", "user", "修改用户信息", "600");
		String paramStr = null;
		paramStr = (null != searchParam)?searchParam.getParamToString():null;
		uri += StringUtils.isNotEmpty(paramStr)?("?"+paramStr):"";
		uri = StringUtils.isContains(uri, "?")?uri+"&page=":uri+"?page=";
		uri += page;
		
		refreshBtn = new RefreshBtn(uri, "resource",null);
		CustomBtn customBtn = new CustomBtn("changepwd", "修改密码", "修改密码", "showPage/base_user_batchChangePwd","glyphicon-pencil",BtnPropType.SelectType.MULTI.getValue());
		customBtn.setWidth("500");
		customBtns = new ArrayList<CustomBtn>(1);
		customBtns.add(customBtn);
		Map<String, Object> cellParam = new HashMap<String, Object>();
		cellParam.put("id", 0);
		cellParam.put("name", 1);
		
		List<CustomTableCell> customCells = new ArrayList<CustomTableCell>();
		CustomTableCell cell = new CustomTableCell("<a href='#${id}'>${name}</a>",7,cellParam);
		CustomTableCell cell2 = new CustomTableCell("<button data-uri='test?id=${id}&name=${name}' type=\"button\" class=\"btn btn-success btn-sm\">（成功）Success</button>",8,cellParam);
		customCells.add(cell);
		customCells.add(cell2);
		
		SmartResponse<Object> smartResp = opServ.getDatas("user_mgr_list_test", searchParam, getStartNum(page), getPerPageSize());
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelMap.put("searchParam", searchParam);
		modelMap.put("customBtns", customBtns);
		modelMap.put("customCells", customCells);
		
		modelView.setViewName(VIEW_DIR+"/cellList");
		return modelView;
	}
	
	@RequestMapping("/sendMsgTest")
	@ResponseBody
	public SmartResponse<String> sendMsgTest(String index) {
		pushMsgContext.sendMsg(MessageType.NOTICE, null, null, "测试消息推送"+index, null);
		return new SmartResponse<String>();
	}
}
