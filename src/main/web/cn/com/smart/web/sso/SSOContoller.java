package cn.com.smart.web.sso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.constant.IConstant;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNLoginLog;
import cn.com.smart.web.constant.IActionConstant;
import cn.com.smart.web.constant.enums.LoginType;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.dao.impl.UserDao;
import cn.com.smart.web.helper.HttpRequestHelper;
import cn.com.smart.web.service.LoginLogService;
import cn.com.smart.web.service.UserService;
import cn.com.smart.web.sso.bean.SSOUserInfo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mixsmart.enums.YesNoType;
import com.mixsmart.security.SecurityUtils;
import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.NetUtils;
import com.mixsmart.utils.StringUtils;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 提供统一的登录验证访问
 * @author lmq <br />
 * 2016年12月19日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/sso")
public class SSOContoller extends BaseController {
	
	private String VIEW_DIR = baseDir+"/sso/";
	
	@Autowired
	private UserService userServ;
	@Autowired
	private LoginLogService loginLogServ;

	/**
	 * 验证单点登录请求
	 * @param request
	 * @return
	 */
	@RequestMapping("/auth")
	public void auth(HttpServletRequest request, HttpServletResponse response) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(log.isInfoEnabled()) {
			log.info("正在验证单点登录...");
		}
		smartResp.setResult("401");
		smartResp.setMsg("未授权");
		String ips = request.getParameter("ip");
		String clientKey = request.getParameter("key");
		String validTimeStr = request.getParameter("validTime");
		String callbackUrl = request.getParameter("callbackUrl");
		if(log.isInfoEnabled()) {
			log.info("Get the remote host request parameters [ips="+ips+",clientKey="+clientKey+
					",validTimeStr="+validTimeStr+",callbackUrl="+callbackUrl+"].");
		}
		if(StringUtils.isEmpty(ips) 
				|| StringUtils.isEmpty(clientKey) 
				|| StringUtils.isEmpty(validTimeStr) 
				|| StringUtils.isEmpty(callbackUrl)) {
			log.error("请求参数为空...");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		/*try {
			ips = URLDecoder.decode(ips, "UTF-8");
			validTimeStr = URLDecoder.decode(validTimeStr, "UTF-8");
			callbackUrl = URLDecoder.decode(callbackUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		
		Long validTime = SSOHelper.getValidTime(validTimeStr);
		//String clientIp = HttpRequestHelper.getIP(request);
		String key = SSOHelper.getKey(ips, validTime);
		if(log.isInfoEnabled()) {
			//log.info("Get client(Browser) IP Address ["+clientIp+"]...");
			log.info("Get the server handle KEY ["+key+"]...");
		}
		if(!key.equals(clientKey)) {
			log.error("客户端请求的key["+clientKey+"]和服务器端处理后获取的key["+key+"]不一致");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		long currentTime = System.currentTimeMillis();
		if(validTime < currentTime) {
			smartResp.setResult("403");
			smartResp.setMsg("禁止访问");
		}
		
		String[] whiteIps = SSOUtils.getClientWhiteIps();
		boolean isWhite = false;
		String[] serverIpArray = null; 
		
		ips = SecurityUtils.desDecode(ips, SSOUtils.getSecretKey());
		serverIpArray = ips.split(IConstant.MULTI_VALUE_SPLIT);
		if(log.isInfoEnabled()) {
			log.info(" White IPs List ["+ArrayUtils.arrayToString(whiteIps, IConstant.MULTI_VALUE_SPLIT)+"]");
		}
		if(null != whiteIps) {
			for (String ip : whiteIps) {
				for(String serverIp : serverIpArray) {
					if(ip.equals(serverIp)) {
						isWhite = true;
						break;
					}
				}
				if(isWhite) break;
			}//for
			if(!isWhite) {
				log.error("客户端请求的IP["+ips+"]未在白名单中["+ArrayUtils.arrayToString(whiteIps, IConstant.MULTI_VALUE_SPLIT)+"]");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		} else {
			isWhite = true;
		}
		HttpSession session = request.getSession();
		boolean is = isWhite && ((null != session.getAttribute(IActionConstant.SESSION_USER_KEY))?true:false);
		String result = "";
		if(is) {
			if(log.isInfoEnabled()) {
				log.info("单点登录验证[成功]...");
			}
			UserInfo userInfo = super.getUserInfoFromSession(session);
			try {
				SSOUserInfo ssoUserInfo = new SSOUserInfo();
				ssoUserInfo.convertSSOUserInfo(userInfo);
				ssoUserInfo.setSessionId(session.getId());
				//用户信息转换为JSON格式的数据，并加密
				result = new ObjectMapper().writeValueAsString(ssoUserInfo);
				result = SecurityUtils.desEncode(result, SSOUtils.getSecretKey());
				result = URLEncoder.encode(result, "UTF-8");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		YesNoType yesNo = YesNoType.getObj(is);
		if(callbackUrl.indexOf("?") > -1) {
			callbackUrl = callbackUrl + "&result="+yesNo.getStrValue();
		} else {
			callbackUrl = callbackUrl + "?result="+yesNo.getStrValue();
		}
		if(yesNo.getValue()) {
			callbackUrl += "&ssoResp="+result;
		}
		try {
			if(log.isInfoEnabled()) {
				log.info("登录结果返回给客户端，客户端接受地址为["+callbackUrl+"]");
			}
			response.sendRedirect(callbackUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 请求视图；即：提供浏览器负责发送信息到SSO服务器
	 * @param request
	 * @return
	 */
	@RequestMapping("/requestView")
	public ModelAndView requestView(HttpServletRequest request) {
		if(log.isInfoEnabled()) {
			log.info("正在请求单点登录...");
		}
		ModelAndView modelView = new ModelAndView();
		//获取获取本地IP地址
		String[] ipArray = NetUtils.getLocalAllIPv4();
		String ips = ArrayUtils.arrayToString(ipArray, IConstant.MULTI_VALUE_SPLIT);
		if(log.isInfoEnabled()) {
			log.info("Get the Local IP Address["+ips+"]...");
		}
		ips = SecurityUtils.desEncode(ips, SSOUtils.getSecretKey());
		//获取客户端IP地址（浏览器端的IP地址）
		/*String clientIp = HttpRequestHelper.getIP(request);
		if(log.isInfoEnabled()) {
			log.info("Get client(Browser) IP Address["+clientIp+"]...");
		}*/
		//有效时间1小时
		long validTime = System.currentTimeMillis() + (1000*60*60);
		String validTimeStr = SSOHelper.getValidTime(validTime);
		String key = SSOHelper.getKey(ips, validTime);
		String ssoUrl = SSOUtils.getSSOServerURL();
		String callbackUrl = HttpRequestHelper.getDomain(request);
	    callbackUrl += "sso/login";
	    
		try {
			String enIps = URLEncoder.encode(ips, "UTF-8");
			String enValidTimeStr = URLEncoder.encode(validTimeStr, "UTF-8");
			callbackUrl = URLEncoder.encode(callbackUrl, "UTF-8");
			if(ssoUrl.indexOf("?")>-1) {
				ssoUrl = ssoUrl+"&ip="+enIps;
			} else {
				ssoUrl = ssoUrl+"?ip="+enIps;
			}
			ssoUrl = ssoUrl+"&validTime="+enValidTimeStr+"&key="+key+"&callbackUrl="+callbackUrl;
			modelView.getModelMap().put("serverUrl", ssoUrl);
			if(log.isInfoEnabled()) {
				log.info("单点登录服务器URL为["+ssoUrl+"]...");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		modelView.setViewName(VIEW_DIR+"requestView");
		return modelView;
	}
	
	/**
	 * 根据SSO服务器返回来的结果，进行本系统的登录；
	 * 如：在本系统的SESSION中记录SSO返回来的用户信息；
	 * 获取返回来的用户在本系统中的角色
	 * @param session
	 * @param result
	 * @param ssoResp
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public SmartResponse<String> login(HttpServletRequest request, String result, String ssoResp) {
		if(log.isInfoEnabled()) {
			log.info("正在处理单点登录服务器返回来的信息；返回结果为[result:"+result+"]");
		}
		HttpSession session = request.getSession();
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("登录失败");
		if(!YesNoType.YES.getStrValue().equals(result) || StringUtils.isEmpty(ssoResp)) {
			return smartResp;
		}
		/*try {
			ssoResp = URLDecoder.decode(ssoResp, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		if(checkLogin(request, ssoResp)) {
			String beforeUri = StringUtils.handleNull(session.getAttribute(IActionConstant.SESSION_LOGIN_BEFORE_URI));
			smartResp.setData(beforeUri);
			smartResp.setResult(OP_SUCCESS);
			smartResp.setMsg("登录成功");
			if(log.isInfoEnabled()) {
				log.info("单点登录[成功]...");
			}
		}
		return smartResp;
	}
	
	/**
	 * 请求登录子系统
	 * @param session
	 * @param ssoResp
	 * @return
	 */
	@RequestMapping("/subLogin")
	public ModelAndView subLogin(HttpServletRequest request, String ssoResp) {
		if(log.isInfoEnabled()) {
			log.info("请求登录子系统");
		}
		ModelAndView modelView = new ModelAndView();
		RedirectView view =  null;
		if(StringUtils.isNotEmpty(ssoResp) && checkLogin(request, ssoResp)) {
			if(log.isInfoEnabled()) {
				log.info("子系统登录[成功]...");
			}
			view = new RedirectView("/index", true, true, false);
		} else  {
			log.error("子系统登录[失败]...");
			view = new RedirectView("/login", true, true, false);
		}
		modelView.setView(view);
		return modelView;
	}
	
	/**
	 * 验证单点登录信息
	 * @param session
	 * @param ssoResp
	 * @return
	 */
	private boolean checkLogin(HttpServletRequest request, String ssoResp) {
		boolean is = false;
		HttpSession session = request.getSession();
		String resolution = request.getParameter("resolution");
		String screenWidth = request.getParameter("screenWidth");
		String screenHeight = request.getParameter("screenHeight");
		String ssoResult = SecurityUtils.desDecode(ssoResp, SSOUtils.getSecretKey());
	
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
		loginLog.setLoginType(LoginType.SINGLE.getName());
		if(StringUtils.isNotEmpty(screenWidth) && StringUtils.isNum(screenWidth)) {
			loginLog.setClientScreenWidth(Float.parseFloat(screenWidth));
		}
		if(StringUtils.isNotEmpty(screenHeight) && StringUtils.isNum(screenHeight)) {
			loginLog.setClientScreenHeight(Float.parseFloat(screenHeight));
		}
		UserInfo userInfo = null;
		String msg = "单点登录失败";
		try {
			SSOUserInfo ssoUserInfo = new ObjectMapper().readValue(ssoResult, SSOUserInfo.class);
			if(null != ssoUserInfo) {
				userInfo = ssoUserInfo.convertUserInfo();
				UserDao userDao = (UserDao)userServ.getDao();
				userInfo.setMenuRoleIds(userDao.queryMenuRoleIds(userInfo.getId()));
				userInfo.setRoleIds(userDao.queryRoleIds(userInfo.getId()));
				userInfo.setOrgIds(userDao.queryOrgIds(userInfo.getId()));
				super.setUserInfo2Session(session, userInfo);
				is = true;
				loginLog.setState(YesNoType.YES.getValue());
				loginLog.setUsername(userInfo.getUsername());
				loginLog.setUserId(userInfo.getId());
				msg = "单点登录成功";
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loginLog.setMsg(msg);
		loginLogServ.save(loginLog);
		LoggerUtils.debug(log, "保存单点登录日志成功");
		if(null != userInfo) {
			userInfo.setLoginId(loginLog.getId());
		}
		return is;
	}
}
