package cn.com.smart.web.controller.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.bean.entity.TNOrg;
import cn.com.smart.web.bean.entity.TNPosition;
import cn.com.smart.web.bean.entity.TNRole;
import cn.com.smart.web.bean.entity.TNUser;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.OPAuthService;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.tag.bean.BaseBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 权限控制
 * @author lmq
 *
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {

	private static final String VIEW_DIR = WEB_BASE_VIEW_DIR+"/auth";
	
	@Autowired
	private OPAuthService opAuthServ;
	@Autowired
	private OPService opServ;

	@RequestMapping("/index")
	public ModelAndView index(ModelAndView modelView) throws Exception {
		modelView.setViewName(VIEW_DIR+"/index");
		return modelView;
	}
	
	/**
	 * 检测权限
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkAuth")
	public @ResponseBody SmartResponse<String> checkAuth(HttpSession session,String uri,String authId) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(uri) && StringUtils.isNotEmpty(authId)) {
			if(opAuthServ.isAuth(uri, new BaseBtn(authId), getUserInfoFromSession(session).getRoleIds())) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
			}
		}
		return smartResp;
	}
	
	@RequestMapping("/roleHas")
	public ModelAndView roleHas(ModelAndView modelView,String id) throws Exception {
		if(StringUtils.isNotEmpty(id)) {
			SmartResponse<Object> smartResp = opServ.find(TNRole.class, id);
			ModelMap modelMap = modelView.getModelMap();
			modelMap.put("id", id);
			if(smartResp.getResult().equals(OP_SUCCESS)) {
				TNRole role = (TNRole) smartResp.getData();
				String name = (null != role)?role.getName():null;
				modelMap.put("name", name);
				role = null;
			}
			smartResp = null;
		}
		modelView.setViewName(VIEW_DIR+"/roleHas");
		return modelView;
	}
	
	@RequestMapping("/userHas")
	public ModelAndView userHas(ModelAndView modelView,String id) throws Exception {
		if(StringUtils.isNotEmpty(id)) {
			SmartResponse<Object> smartResp = opServ.find(TNUser.class, id);
			ModelMap modelMap = modelView.getModelMap();
			modelMap.put("id", id);
			if(smartResp.getResult().equals(OP_SUCCESS)) {
				TNUser user = (TNUser) smartResp.getData();
				String name = (null != user)?user.getFullName():null;
				modelMap.put("name", name);
				user = null;
			}
			smartResp = null;
		}
		modelView.setViewName(VIEW_DIR+"/userHas");
		return modelView;
	}
	
	@RequestMapping("/orgHas")
	public ModelAndView orgHas(ModelAndView modelView,String id) throws Exception {
		if(StringUtils.isNotEmpty(id)) {
			SmartResponse<Object> smartResp = opServ.find(TNOrg.class, id);
			ModelMap modelMap = modelView.getModelMap();
			modelMap.put("id", id);
			if(smartResp.getResult().equals(OP_SUCCESS)) {
				TNOrg org = (TNOrg) smartResp.getData();
				String name = (null != org)?org.getName():null;
				modelMap.put("name", name);
				org = null;
			}
			smartResp = null;
		}
		modelView.setViewName(VIEW_DIR+"/orgHas");
		return modelView;
	}
	
	@RequestMapping("/positionHas")
	public ModelAndView positionHas(ModelAndView modelView,String id) throws Exception {
		if(StringUtils.isNotEmpty(id)) {
			SmartResponse<Object> smartResp = opServ.find(TNPosition.class, id);
			ModelMap modelMap = modelView.getModelMap();
			modelMap.put("id", id);
			if(smartResp.getResult().equals(OP_SUCCESS)) {
				TNPosition position = (TNPosition) smartResp.getData();
				String name = (null != position)?position.getName():null;
				modelMap.put("name", name);
				position = null;
			}
			smartResp = null;
		}
		modelView.setViewName(VIEW_DIR+"/positionHas");
		return modelView;
	}
	
}
