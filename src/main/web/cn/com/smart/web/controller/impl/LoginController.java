package cn.com.smart.web.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNLoginLog;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.helper.HttpRequestHelper;
import cn.com.smart.web.service.LoginLogService;
import cn.com.smart.web.service.UserService;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 登录
 * @author lmq
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Autowired
	private UserService userServ;
	@Autowired
	private LoginLogService loginLogServ;
	
	@RequestMapping(method=RequestMethod.GET)
	public String index() throws Exception {
		return LOGIN;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView checkLogin(HttpServletRequest request,ModelAndView model, 
			String userName,String password,String code) throws Exception {
		boolean is = false;
		String msg = null;
		HttpSession session = request.getSession();
		String screenWidth = request.getParameter("screenWidth");
		String screenHeight = request.getParameter("screenHeight");
		String resolution = request.getParameter("resolution");
		if(StringUtils.isNotEmpty(userName) 
				&& StringUtils.isNotEmpty(password) 
				&& StringUtils.isNotEmpty(code)) {
			//记录登录日志
			TNLoginLog loginLog = new TNLoginLog();
			String userAgentStr = request.getHeader("User-Agent");
			loginLog.setUserAgent(userAgentStr);
			UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
			loginLog.setBrowser(userAgent.getBrowser().getName());
			loginLog.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
			loginLog.setOs(userAgent.getOperatingSystem().getName());
			loginLog.setDeviceType(userAgent.getOperatingSystem().getDeviceType().getName());
			loginLog.setIp(HttpRequestHelper.getIP(request));
			loginLog.setState(YesNoType.NO.getValue());
			loginLog.setResolution(resolution);
			if(StringUtils.isNotEmpty(screenWidth) && StringUtils.isNum(screenWidth)) {
				loginLog.setClientScreenWidth(Float.parseFloat(screenWidth));
			}
			if(StringUtils.isNotEmpty(screenHeight) && StringUtils.isNum(screenHeight)) {
				loginLog.setClientScreenHeight(Float.parseFloat(screenHeight));
			}
			Object codeStr = session.getAttribute(SESSION_CAPTCHA_LOGIN);
			UserInfo userInfo = null;
			if(null != codeStr && StringUtils.isNotEmpty(codeStr.toString()) 
					&& codeStr.toString().equalsIgnoreCase(code)) {
				SmartResponse<UserInfo> smartResp = userServ.login(userName, password);
				if(OP_SUCCESS.equals(smartResp.getResult())) {
					userInfo = smartResp.getData();
					setUserInfo2Session(session, userInfo);
					is = true;
					loginLog.setState(YesNoType.YES.getValue());
					loginLog.setUsername(userInfo.getUsername());
					loginLog.setUserId(userInfo.getId());
					msg = "登录成功 ";
				} else {
					msg = "用户名或密码输入错误";
				}
				smartResp = null;
			} else {
				msg = "验证码输入错误";
			}
			loginLog.setMsg(msg);
			loginLogServ.save(loginLog);
			LoggerUtils.debug(log, "保存登录日志成功");
			LoggerUtils.debug(log, "登录结果："+msg);
			if(null != userInfo) {
				userInfo.setLoginId(loginLog.getId());
			}
		}
		if(is) {
			RedirectView view =  new RedirectView("/index", true, true, false);
			model.setView(view);
		} else {
			ModelMap modelMap = model.getModelMap();
			modelMap.put("userName", userName);
			modelMap.put("password", password);
			modelMap.put("code", code);
			modelMap.put("msg", msg);
		}
		return model;
	}
	
}
