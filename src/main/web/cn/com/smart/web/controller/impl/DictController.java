package cn.com.smart.web.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.bean.entity.TNDict;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.DictService;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.RefreshBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 数据字典
 * @author lmq
 *
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/dict";
	
	@Autowired
	private DictService dictServ;
	
	@RequestMapping("list")
	public ModelAndView list(ModelAndView modelView) throws Exception {
		SmartResponse<Object> smartResp = dictServ.findObjAll();
		
		String uri = "dict/list"; 
		addBtn = new EditBtn("add","showPage/base_dict_add", "dict", "添加数据字典", "600");
		editBtn = new EditBtn("edit","showPage/base_dict_edit", "dict", "修改数据字典", "600");
		delBtn = new DelBtn("dict/delete.json", "确定要删除选中的数据字典吗？",uri,null, null);
		refreshBtn = new RefreshBtn(uri, "dict",null);
		
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("addBtn", addBtn);
		modelMap.put("editBtn", editBtn);
		modelMap.put("delBtn", delBtn);
		modelMap.put("refreshBtn", refreshBtn);
		addBtn = null;editBtn = null;delBtn = null;
		refreshBtn = null;
		modelView.setViewName(VIEW_DIR+"/list");
		return modelView;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> add(TNDict dict) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != dict) {
			smartResp = dictServ.save(dict);
		}
		return smartResp;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> edit(TNDict dict) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != dict) {
			smartResp = dictServ.update(dict);
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
		return dictServ.delete(id);
	}
	
	/**
	 * 获取数据字典内容
	 * @param busiValue
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/item/{busiValue}")
	public @ResponseBody SmartResponse<Object> item(@PathVariable String busiValue,String name) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(busiValue)) {
			smartResp = dictServ.getItem(busiValue, name);
		}
		return smartResp;
	}
	
	
	/**
	 * 获取数据字典内容
	 * @param busiValue
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/itemById/{id}")
	public @ResponseBody SmartResponse<Object> itemById(@PathVariable String id,String name) throws Exception {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		if(StringUtils.isNotEmpty(id)) {
			smartResp = dictServ.getItemById(id, name);
		}
		return smartResp;
	}
	
}
