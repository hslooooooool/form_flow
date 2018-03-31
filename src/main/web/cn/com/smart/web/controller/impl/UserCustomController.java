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
import cn.com.smart.web.bean.entity.TNCustomIndex;
import cn.com.smart.web.bean.entity.TNDict;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.DictService;
import cn.com.smart.web.service.UserCustomService;

/**
 * 用户自定义
 * @author lmq
 *
 */
@Controller
@RequestMapping("/user/custom")
public class UserCustomController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/user/custom";
	
	@Autowired
	private UserCustomService userCusServ;
	@Autowired
	private DictService dictServ;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session,ModelAndView modelView) throws Exception {
		SmartResponse<TNCustomIndex> smartResp = userCusServ.getIndexLayout(getUserInfoFromSession(session).getId(), true);
		SmartResponse<TNDict> dictRes = dictServ.getItems("INDEX_LAYOUT_TEMP");
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("smartResp", smartResp);
		modelMap.put("dictRes", dictRes);
		modelView.setViewName(VIEW_DIR+"/index");
		return modelView;
	}
	
	@RequestMapping(value="/saveLayout",method=RequestMethod.POST)
	public @ResponseBody SmartResponse<String> saveLayout(HttpSession session,TNCustomIndex customIndex) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != customIndex) {
			String layout = customIndex.getLayout();
			if(StringUtils.isNotEmpty(layout)) {
				String[] rowsCols = layout.split("-");
				try {
					customIndex.setRows(Integer.parseInt(rowsCols[1]));
					customIndex.setCols(Integer.parseInt(rowsCols[2]));
					customIndex.setUserId(getUserInfoFromSession(session).getId());
					//customIndex.setCusIndexMinWins(cusIndexMinWins);
					smartResp = userCusServ.saveLayout(customIndex);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return smartResp;
	}
	
	@RequestMapping("addLayoutProp")
	public ModelAndView addLayoutProp(TNCustomIndex customIndex,ModelAndView modelView) throws Exception {
		modelView.getModelMap().put("customIndex", customIndex);
		modelView.setViewName(VIEW_DIR+"/addLayoutProp");
		return modelView;
	}
	
}
