package cn.com.smart.web.controller.impl;

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
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNSubSystem;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.service.SubSystemService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 子系统信息管理控制类
 * @author lmq <br />
 * 2016年12月23日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/subSystem")
public class SubSystemController extends BaseController {

	private String VIEW_DIR = baseDir+"/subSystem/";
	
	@Autowired
	private SubSystemService subSysServ;
	@Autowired
	private OPService opServ;
	
	/**
	 * 子系统列表
	 * @param session 
	 * @param page 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpSession session, RequestPage page) {
		ModelAndView modelView = new ModelAndView();
		Map<String, Object> params = null;
		SmartResponse<Object> smartResp = opServ.getDatas("sub_system_list", params, page.getStartNum(), page.getPageSize());
		String uri = "subSystem/list";
		ModelMap modelMap = modelView.getModelMap();
		
		addBtn = new EditBtn("add", "subSystem/add", null, "添加子系统信息", "600");
		editBtn = new EditBtn("edit", "subSystem/edit", null, "修改子系统信息", "600");
		delBtn = new DelBtn("subSystem/delete", "您确定要删除选择的数据吗？", uri, null, null);
		refreshBtn = new RefreshBtn(uri, null, null);
		pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
		
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelMap.put("smartResp", smartResp);
		modelMap.put("delBtn", delBtn);
		
		modelView.setViewName(VIEW_DIR+"list");
		return modelView;  
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView add() {
		ModelAndView modelView = new ModelAndView();
		int sortOrder = subSysServ.getDao().getSortOrder(null);
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("sortOrder", sortOrder);
		modelView.setViewName(VIEW_DIR+"add");
		return modelView;
	}
	
	/**
	 * 保存
	 * @param session
	 * @param subSystem
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> save(HttpSession session, TNSubSystem subSystem) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("添加失败");
		if(null == subSystem || 
				StringUtils.isEmpty(subSystem.getName()) 
				|| StringUtils.isEmpty(subSystem.getUrl())) {
			return smartResp;
		}
		UserInfo userInfo = super.getUserInfoFromSession(session);
		subSystem.setUserId(userInfo.getId());
		smartResp = subSysServ.save(subSystem);
		return smartResp;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(String id) {
		ModelAndView modelView = new ModelAndView();
		SmartResponse<TNSubSystem> smartResp = subSysServ.find(id);
		if(OP_SUCCESS.equals(smartResp.getResult())) {
			ModelMap modelMap = modelView.getModelMap();
			modelMap.put("objBean", smartResp.getData());
			modelMap.put("sortOrder", smartResp.getData().getSortOrder());
		}
		modelView.setViewName(VIEW_DIR+"edit");
		return modelView;
	}
	
	/**
	 * 修改
	 * @param subSystem
	 * @return
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> update(TNSubSystem subSystem) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("修改失败");
		if(null == subSystem || 
				StringUtils.isEmpty(subSystem.getName()) 
				|| StringUtils.isEmpty(subSystem.getUrl())) {
			return smartResp;
		}
		smartResp = subSysServ.update(subSystem);
		return smartResp;
	}

	/**
	 * 删除信息
	 * @param id 多个id用英文逗号分隔
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> delete(String id) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("删除失败");
		if(StringUtils.isNotEmpty(id)) {
			smartResp = subSysServ.delete(id);
		}
		return smartResp;
	}
}
