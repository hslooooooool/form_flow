package cn.com.smart.form.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.form.bean.entity.TFormHelper;
import cn.com.smart.form.service.FormHelperService;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.tag.bean.ALink;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 表单帮助---控制器类
 * @author lmq <br />
 * 2016年10月19日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/form/helper")
public class FormHelperController extends BaseFormController {

	private final String VIEW_DIR = BASE_FORM_VIEW_DIR+"helper/";
	
	@Autowired
	private OPService opServ;
	@Autowired
	private FormHelperService formHelperServ;
	
	/**
	 * 表单帮助列表
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, FilterParam searchParam, RequestPage page) {
		ModelAndView modelView = new ModelAndView();
		SmartResponse<Object> smartResp = opServ.getDatas("form_helper_mgr_list", searchParam, page.getStartNum(), page.getPageSize());
		String uri = "form/helper/list";
		addBtn = new EditBtn("add", "showPage/form_helper_add", null, "添加表单帮助信息", "800");
		editBtn = new EditBtn("edit", "form/helper/edit", null, "修改表单帮助信息", "800");
		delBtn = new DelBtn("form/helper/delete", "您确定要删除选中的数据吗？", uri, null, null);
		refreshBtn = new RefreshBtn(uri, null, null);
		pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
		ALink alink = new ALink("form/helper/view", "800", "查看表单帮助信息");
		alinks = new ArrayList<ALink>();
		alinks.add(alink);
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelMap.put("alinks", alinks);
		
		modelView.setViewName(VIEW_DIR+"list");
		return modelView;
	}
	
	/**
	 * 编辑表单帮助信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(String id) {
		ModelAndView modelView = new ModelAndView();
		if(StringUtils.isNotEmpty(id)) {
			TFormHelper objBean = formHelperServ.find(id).getData();
			modelView.getModelMap().put("objBean", objBean);
		}
		modelView.setViewName(VIEW_DIR+"edit");
		return modelView;
	}
	
	/**
	 * 保存表单帮助信息
	 * @param objBean
	 * @return
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> save(HttpSession session, TFormHelper objBean) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("表单帮助信息保存失败");
		if(null != objBean 
				&& StringUtils.isNotEmpty(objBean.getTitle()) 
				&& StringUtils.isNotEmpty(objBean.getContent())) {
			objBean.setContent(objBean.getContent().replaceAll("<table([^>]*?)>", "<table class=\"table table-bordered table-condensed\" $1>"));
			UserInfo userInfo = super.getUserInfoFromSession(session);
			objBean.setUserId(userInfo.getId());
			smartResp = formHelperServ.save(objBean);
			if(smartResp.getResult().equals(OP_SUCCESS)) {
				smartResp.setMsg("表单帮助信息保存成功");
			} 
		}
		return smartResp;
	}
	
	/**
	 * 更新表单帮助信息
	 * @param objBean
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> update(TFormHelper objBean) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("表单帮助信息修改失败");
		if(StringUtils.isNotEmpty(objBean.getId()) 
				&& StringUtils.isNotEmpty(objBean.getTitle()) 
				&& StringUtils.isNotEmpty(objBean.getContent())) {
			objBean.setContent(objBean.getContent().replaceAll("<(?i)table(.*?)>", "<table class=\"table table-bordered table-condensed\" $1>"));
			smartResp = formHelperServ.update(objBean);
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				smartResp.setMsg("表单帮助信息修改成功");
			}
		}
		return smartResp;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public SmartResponse<String> delete(String id) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("删除失败");
		if(StringUtils.isNotEmpty(id)) {
			smartResp = formHelperServ.delete(id);
		}
		return smartResp;
	}
	
	/**
	 * 查看表单帮助信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view(String id) {
		ModelAndView modelView = new ModelAndView();
		if(StringUtils.isNotEmpty(id)) {
			modelView.getModelMap().put("smartResp", formHelperServ.find(id));
		}
		modelView.setViewName(VIEW_DIR+"view");
		return modelView;
	}
	
	@RequestMapping("/blank")
	public ModelAndView blank(String id) {
		ModelAndView modelView = new ModelAndView();
		if(StringUtils.isNotEmpty(id)) {
			modelView.getModelMap().put("smartResp", formHelperServ.find(id));
		}
		modelView.setViewName(VIEW_DIR+"blank");
		return modelView;
	}
}
