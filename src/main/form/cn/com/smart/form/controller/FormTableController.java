package cn.com.smart.form.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.form.bean.CreateTableData;
import cn.com.smart.form.bean.entity.TCreateTable;
import cn.com.smart.form.service.FormTableService;
import cn.com.smart.form.util.FormTableUtil;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.tag.bean.ALink;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 表单--数据库表管理 <br />
 * 根据表单信息，创建表单对应的数据库表
 * 
 * @author lmq
 * @create 2015年6月25日
 * @version 1.0 
 * @since 
 *
 */
@Controller
@RequestMapping("/form/table")
public class FormTableController extends BaseController {

	private static final String VIEW_DIR = "form/table";
	
	@Autowired
	private OPService opServ;
	@Autowired
	private FormTableService tableServ;
	
	/**
	 * 表单对应的数据库表---管理
	 * @param request
	 * @param modelView
	 * @param searchParam
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request,ModelAndView modelView,FilterParam searchParam,RequestPage page) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		smartResp = opServ.getDatas("form_table_mgr_list",searchParam, page.getStartNum(), page.getPageSize());
		String uri = "form/table/list";
		addBtn = new EditBtn("add","showPage/form_table_add", null, "创建表", "800");
		editBtn = new EditBtn("edit","showPage/form_table_edit", "formTable", "修改表", "800");
		delBtn = new DelBtn("form/table/delete", "确定要删除选中的表吗，删除后将无法恢复？",uri,null, null);
		refreshBtn = new RefreshBtn(uri, null,null);
		pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
		alinks = new ArrayList<ALink>();
		ALink link = new ALink();
		link.setUri("form/table/show");
		link.setDialogTitle("查看表信息");
		link.setDialogWidth("800");
		alinks.add(link);
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("customBtns", customBtns);
		modelMap.put("searchParam", searchParam);
		modelMap.put("delBtn", delBtn);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("refreshBtn", refreshBtn);
		modelMap.put("pageParam", pageParam);
		modelMap.put("alinks", alinks);
		delBtn = null;link = null;alinks = null;
		refreshBtn = null;pageParam = null;
		modelView.setViewName(VIEW_DIR+"/list");
		
		return modelView;
	}
	
	
	/**
	 * 创建表
	 * @param request
	 * @param table
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> create(HttpServletRequest request,CreateTableData tableData) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != tableData && null != tableData.getTable() 
				&& null != tableData.getFields() && tableData.getFields().size()>0) {
			//验证表名格式
			if(FormTableUtil.isCheckTableName(tableData.getTable().getTableName())) {
				UserInfo userInfo = getUserInfoFromSession(request);
				smartResp = tableServ.createTable(tableData, userInfo.getId());
			} else {
				smartResp.setMsg("表名格式错误");
			}
		} else {
			smartResp.setMsg("请添加字段");
		}
		return smartResp;
	}
	
	
	/**
	 * 更新表结构
	 * @param request
	 * @param table
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public SmartResponse<String> update(HttpServletRequest request,CreateTableData tableData) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != tableData && null != tableData.getTable() 
				&& null != tableData.getFields() && tableData.getFields().size()>0) {
			//验证表名格式
			if(FormTableUtil.isCheckTableName(tableData.getTable().getTableName())) {
				UserInfo userInfo = getUserInfoFromSession(request);
				try {
				    smartResp = tableServ.updateTable(tableData, userInfo.getId());
				} catch (Exception ex) {
					ex.printStackTrace();
					smartResp.setResult(OP_FAIL);
				}
			} else {
				smartResp.setMsg("表名格式错误");
			}
		} else {
			smartResp.setMsg("请添加字段");
		}
		return smartResp;
	}
	
	
	/**
	 * 查看表相关信息
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/show")
	public ModelAndView show(ModelAndView modelView,String id) {
		SmartResponse<TCreateTable> smartResp = new SmartResponse<TCreateTable>();
		if(StringUtils.isNotEmpty(id)) {
			smartResp = tableServ.findTableInfo(id);
		}
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("table", smartResp.getData());
		modelView.setViewName(VIEW_DIR+"/show");
		return modelView;
	}
	
	
	/**
	 * 获取表名及名称
	 * @return
	 */
	@RequestMapping("/item")
	@ResponseBody
	public SmartResponse<Object> item() {
		SmartResponse<Object> smartResp = opServ.getDatas("table_item");
		if(OP_SUCCESS.equals(smartResp.getResult())) {
			List<Object> objs = smartResp.getDatas();
			//表名与表注释组合在一起
			for (Object obj : objs) {
				Object[] objArray = (Object[])obj;
				//objArray[0] = objArray[1];
				if(StringUtils.isNotEmpty(StringUtils.handleNull(objArray[2]))) {
					objArray[1] = objArray[2];
				}
			}
		}
		return smartResp;
	}
	
	/**
	 * 获取字段
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	@RequestMapping("/fields")
	@ResponseBody
	public SmartResponse<Object> fields(HttpServletRequest request, String paramName,String paramValue) {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(paramName) && StringUtils.isNotEmpty(paramValue)) {
			param.put(paramName, paramValue);
		} else {
			param = super.getRequestParamMap(request, false);
		}
		smartResp = opServ.getDatas("table_fields",param);
		if(OP_SUCCESS.equals(smartResp.getResult())) {
			List<Object> objs = smartResp.getDatas();
			//字段名与字段注释组合在一起
			for (Object obj : objs) {
				Object[] objArray = (Object[])obj;
				if(StringUtils.isNotEmpty(StringUtils.handleNull(objArray[2]))) {
					objArray[1] = objArray[2];
				}
			}//for
		}//if
		return smartResp;
	}
	
	@RequestMapping(value="/delete", produces="application/json;charset=UTF-8")
    @ResponseBody
    public SmartResponse<String> delete(String id) {
        return tableServ.delete(id);
    }
}
