package cn.com.smart.web.controller.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mixsmart.security.SecurityUtils;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNSubSystem;
import cn.com.smart.web.bean.entity.TNVersion;
import cn.com.smart.web.constant.enums.VersionType;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.SubSystemService;
import cn.com.smart.web.service.VersionService;
import cn.com.smart.web.sso.SSOUtils;
import cn.com.smart.web.sso.bean.SSOUserInfo;

/**
 * 首页
 * @author lmq
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
	
	@Autowired
	private VersionService versionServ;
	@Autowired
	private SubSystemService subSysServ;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, String forward) throws Exception {
		ModelAndView modelView = new ModelAndView();
		TNVersion version = versionServ.getNewVersion(VersionType.PC);
		if(null == version) {
			version = new TNVersion();
			version.initVersion();
		}
		ModelMap modelMap = modelView.getModelMap();
		modelMap.put("version", version);
		modelMap.put("forward", forward);
		//获取子系统信息
		List<TNSubSystem> subSysList = subSysServ.getSubSystems();
		if(CollectionUtils.isNotEmpty(subSysList)) {
			for (TNSubSystem subSystem : subSysList) {
				if(null == subSystem || StringUtils.isEmpty(subSystem.getUrl()) 
						|| TNSubSystem.SYS_TYPE_EXTERNAL.equals(subSystem.getSysType())) {
					continue;
				}
				String result = "";
				UserInfo userInfo = super.getUserInfoFromSession(request.getSession());
				try {
					SSOUserInfo ssoUserInfo = new SSOUserInfo();
					ssoUserInfo.convertSSOUserInfo(userInfo);
					ssoUserInfo.setSessionId(request.getSession().getId());
					//用户信息转换为JSON格式的数据，并加密
					result = new ObjectMapper().writeValueAsString(ssoUserInfo);
					result = SecurityUtils.desEncode(result, SSOUtils.getSecretKey());
					result = URLEncoder.encode(result, "UTF-8");
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String subSysUrl = subSystem.getUrl();
				if(!subSysUrl.endsWith("/")) {
					subSysUrl +="/";
				}
				subSysUrl += "sso/subLogin?ssoResp="+result;
				subSystem.setUrl(subSysUrl);
			}//for
			modelMap.put("subSysList", subSysList);
		}//if
		modelView.setViewName("index");
		return modelView;
	}
	
	/**
	 * 首页
	 * @param request
	 * @param modelView
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/welcome")
	public ModelAndView welcome(HttpServletRequest request,ModelAndView modelView) throws Exception {
		modelView.setViewName("welcome");
		return modelView;
	}
	
}
