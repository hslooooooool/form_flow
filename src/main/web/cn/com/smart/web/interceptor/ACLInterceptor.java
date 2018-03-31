package cn.com.smart.web.interceptor;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.init.config.InitSysConfig;
import cn.com.smart.service.SmartContextService;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNAccessLog;
import cn.com.smart.web.constant.IActionConstant;
import cn.com.smart.web.constant.IWebConstant;
import cn.com.smart.web.helper.HttpRequestHelper;
import cn.com.smart.web.helper.WebSecurityHelper;
import cn.com.smart.web.service.AccessLogService;
import cn.com.smart.web.service.OPService;
import cn.com.smart.web.sso.SSOUtils;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 访问控制拦截
 * @author lmq
 *
 */
public class ACLInterceptor implements HandlerInterceptor {
  
	private List<String> excludeMaps;
	private List<String> authUriList;
	private String resSuffix;
	private List<String> excludeAccessLogUrls;
	
	private final static Logger log = LoggerFactory.getLogger(ACLInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception arg3)
			throws Exception {
		String currentUri = HttpRequestHelper.getCurrentUri(request);
		long startTime = (Long)request.getAttribute("startTime");
		Date responseTime = new Date();
		long endTime = responseTime.getTime();
		long useTime = endTime - startTime;
		LoggerUtils.debug(log, "请求["+currentUri+"]用时："+useTime+"毫秒");
		if(!isRes(currentUri)) {
			Object al = request.getAttribute("accessLog");
			if(null != al) {
				//保存访问日志
				TNAccessLog accessLog = (TNAccessLog)al;
				accessLog.setResponseTime(responseTime);
				accessLog.setUseTime(useTime);
				AccessLogService accessLogServ = SmartContextService.find(AccessLogService.class);
				LoggerUtils.debug(log, "正在保存访问日志...");
				accessLogServ.save(accessLog);
			}
			response.setHeader("Cache-Control","no-cache");
			response.setHeader("Pragrma","no-cache");
			response.setDateHeader("Expires",-1);
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj, ModelAndView modelAndView) throws Exception {
		String currentUri = HttpRequestHelper.getCurrentUri(request);
		if(isRes(currentUri) || null == modelAndView) {
			return;
		}
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.put("project", InitSysConfig.getInstance().getProjectInfo());
		if(!modelMap.containsKey("currentUri")) {
		    modelMap.put("currentUri", HttpRequestHelper.getCurrentUri(request));
		} 
		if(!modelMap.containsKey("currentUriParam")) {
		    modelMap.put("currentUriParam", HttpRequestHelper.getCurrentUriParam(request));
		}
		//请求参数添加到map里面
		Map<String,String[]> curParamMaps = request.getParameterMap();
		if(null != curParamMaps && curParamMaps.size()>0) {
			Set<Map.Entry<String, String[]>> items = curParamMaps.entrySet();
			for (Map.Entry<String, String[]> item : items) {
				if(item.getValue().length<2) {
					String value = item.getValue()[0];
					if(StringUtils.isNotEmpty(value) && value.startsWith("%")) {
						value = URLDecoder.decode(value, "UTF-8");
					}
					if(value.length()<100 && !modelMap.containsKey(item.getKey())) {
						modelMap.put(item.getKey(), value);
					}
				}
			}
		} //if;
		RedirectView redirectView = ((RedirectView)modelAndView.getView());
		if(null == redirectView || !redirectView.isRedirectView()) {
			if(isLogin(request)) {
				modelMap.put("userInfo", HttpRequestHelper.getUserInfoFromSession(request));
			}
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		String currentUriParam = HttpRequestHelper.getCurrentUriParam(request);
		//获取域名或IP地址
		String serverName = request.getServerName();
		String domainForward = InitSysConfig.getInstance().getValue("domain.forward");
		//如果是通过IP或域名访问的地址，和配置文件中配置的IP或域名不一致；
		//则采用配置文件中的IP或域名访问（即：根据配置的域名或IP重新跳转）.
		if(YesNoType.YES.getStrValue().equals(domainForward)) {
			String domain = InitSysConfig.getInstance().getValue("domain.name");
			if(StringUtils.isNotEmpty(domain) && !serverName.equals(domain)) {
				String path = request.getContextPath();
				String basePath = request.getScheme()+"://"+domain+":"+request.getServerPort()+path+"/";
				response.sendRedirect(basePath+currentUriParam);
				return false;
			}
		}
		String currentUri = HttpRequestHelper.getCurrentUri(request);
		boolean isRes = isRes(currentUri);
		boolean is = false;
		TNAccessLog accessLog = null;
		Date currentTime = new Date();
		long startTime = currentTime.getTime();
		request.setAttribute("startTime", startTime);
		
		if(isLogin(request)) {
			is = true;
			//判断URL权限
	        if(!isRes) {
	            boolean isAuth = handleAuthUri(currentUri, request);
	            //如果URL没有访问（操作）权限
	            if(!isAuth) {
	                String noAccessUri = "/exception/noaccess";
	                noAccessUri = handleRedirectUri(request, noAccessUri);
	                response.sendRedirect(noAccessUri);
	                is = false;
	            }
	        }
		} else {
			if(!isRes(currentUri)) {
				if(!isExclude(currentUri)) {
					HttpSession session = request.getSession();
					//获取SSO(单点登录)服务器地址
					String ssoServerUrl = SSOUtils.getSSOServerURL();
					String domainUrl = HttpRequestHelper.getDomain(request);
					String forward = request.getParameter("forward");
					if(StringUtils.isNotEmpty(forward)) {
						forward = URLEncoder.encode(forward,"UTF-8");
						session.setAttribute("forward", forward);
					}
					LoggerUtils.info(log, "当前服务器地址：["+ domainUrl +"] --- 单点登录服务器地址：["+ ssoServerUrl +"]");
					if(StringUtils.isNotEmpty(ssoServerUrl) && !ssoServerUrl.startsWith(domainUrl)) {
						String ssoRequestUri = "/sso/requestView";
						ssoRequestUri = handleRedirectUri(request, ssoRequestUri);
						response.sendRedirect(ssoRequestUri);
					} else {
						String loginUri = "/login";
						loginUri = handleRedirectUri(request, loginUri);
						response.sendRedirect(loginUri);
					}
					is = false;
				} else {
					is = true;
				}
			} else {
				is = true;
			}
		}
		//从配置文件中获取是否记录日志的标识；如果未配置；默认记录访问日志
		YesNoType yesNo = YesNoType.getObjByStrValue(InitSysConfig.getInstance().getValue("is.access.log"));
		if(null == yesNo) {
			yesNo = YesNoType.YES;
		}
		//判断当前地址是否为不记录访问日志的URL
        if(yesNo.getValue() && isUriContains(excludeAccessLogUrls, currentUri)) {
            yesNo = YesNoType.NO;
        }
		//如果不是资源URL，并且记录访问日志
		if(!isRes && yesNo.getValue()) {
			String userAgentStr = request.getHeader("User-Agent");
			//记录访问日志
			accessLog = new TNAccessLog();
			accessLog.setUserAgent(userAgentStr);
			UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
			accessLog.setBrowser(userAgent.getBrowser().getName());
			if(null != userAgent.getBrowserVersion())
				accessLog.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
			accessLog.setOs(userAgent.getOperatingSystem().getName());
			accessLog.setDeviceType(userAgent.getOperatingSystem().getDeviceType().getName());
			accessLog.setIp(HttpRequestHelper.getIP(request));
			accessLog.setRequestMethod(request.getMethod());
			accessLog.setUri(currentUri);
			String url = HttpRequestHelper.getCurrentUriParam(request);
			if(url.length() > 490) {
				url = url.substring(0, 490);
			}
			accessLog.setUrl(url);
			String param = request.getQueryString();
			if(StringUtils.isNotEmpty(param)) {
				param = URLDecoder.decode(param, "UTF-8");
				if(param.length() > 490) {
					param = param.substring(0, 490);
				}
			}
			accessLog.setParam(param);
			UserInfo userInfo = HttpRequestHelper.getUserInfoFromSession(request);
			if(null != userInfo) {
				accessLog.setUserId(userInfo.getId());
				accessLog.setUsername(userInfo.getUsername());
				accessLog.setLoginId(userInfo.getLoginId());
			}
			accessLog.setCreateTime(currentTime);
			request.setAttribute("accessLog", accessLog);
		}
		return is;
	}
	
	/**
	 *  处理调整URI
	 * @param request
	 * @param uri
	 * @return
	 */
	private String handleRedirectUri(HttpServletRequest request, String uri) {
	    String contextPath = request.getContextPath();
	    if(StringUtils.isNotEmpty(contextPath) && !"/".equals(contextPath)) {
	        uri = contextPath+uri;
        }
	    return uri;
	}
	
	/**
	 * 检测请求的资源样式,js,图片等文件(css,js,img)
	 * @param currentUri
	 * @return
	 */
	private boolean isRes(String currentUri) {
		boolean isRes = false;
		if(StringUtils.isNotEmpty(resSuffix)) {
			String suffix = StringUtils.getFileSuffix(currentUri);
			if(StringUtils.isNotEmpty(suffix)) {
				if(ArrayUtils.isArrayContainsIgnoreCase(resSuffix, suffix, ",")) {
					isRes = true;
				}
			}
		}
		return isRes;
	}
	
	/**
	 * 是否排除
	 * @param currentUri
	 * @return
	 */
	private boolean isExclude(String currentUri) {
		boolean is = false;
		is = currentUri.startsWith("#");
		is = is ? is : isUriContains(excludeMaps, currentUri);
		return is;
	}
	
	/**
	 * 判断URI列表中是否包含URI；
	 * 注：当列表为空或 <code>uri</code> 参数值为空时，返回：true
	 * @param uriList
	 * @param uri
	 * @return 如果包含返回：true；否则返回false
	 */
	private boolean isUriContains(List<String> uriList, String uri) {
	    boolean is = false;
	    if(CollectionUtils.isEmpty(uriList) || StringUtils.isEmpty(uri)) {
	        return true;
	    }
	    for (String uriTmp : uriList) {
	        if(StringUtils.isNotEmpty(uriTmp) && uriTmp.indexOf("*")>-1) {
	            uriTmp = uriTmp.replace("*", "");
                if(uri.startsWith(uriTmp) || uri.endsWith(uriTmp) || uri.contains(uriTmp)) {
                    is = true;
                    break;
                }
            } else {
                if(uri.equals(uriTmp)) {
                    is = true;
                    break;
                }
            }
        }
	    return is;
	}
	
	/**
	 * 判断用户是否登录
	 * @param request
	 * @return
	 */
	private boolean isLogin(HttpServletRequest request) {
		boolean is = false;
		HttpSession session = request.getSession();
		if(null != session) {
			is = (null != session.getAttribute(IActionConstant.SESSION_USER_KEY))?true:false;
		}
		return is;
	}
	
	/**
	 * 处理判断授权URI是否有访问权限
	 * @param uri 没有参数的URI
	 * @param request
	 * @return 有访问权限；则返回：true；否则返回：false
	 */
	private boolean handleAuthUri(String uri, HttpServletRequest request) {
	    boolean is = true;
	    if(StringUtils.isEmpty(uri) || CollectionUtils.isEmpty(authUriList)) {
	        return is;
	    }
	    is = isUriContains(authUriList, uri);
	    if(is) {
	        String authToken = request.getParameter("authToken");
	        if(StringUtils.isEmpty(authToken)) {
	            return false;
	        }
	        String[] array = WebSecurityHelper.parseAuthToken(authToken);
	        if(null == array || array.length < 3) {
	            return false;
	        }
	        if(!(uri.contains(array[2]) || array[2].contains(uri))) {
	            return false;
	        }
	        UserInfo userInfo = HttpRequestHelper.getUserInfoFromSession(request);
	        OPService opServ = SmartContextService.find(OPService.class);
	        Map<String, Object> params = new HashMap<String, Object>(2);
	        if(CollectionUtils.isEmpty(userInfo.getRoleIds())) {
	            params.put("roleIds", new String[]{"-1"});
	        } else {
	            params.put("roleIds", userInfo.getRoleIds().toArray());
	        }
	        params.put("uri", array[0]);
	        SmartResponse<Object> smartResp = opServ.getDatas("get_op_auths_byuri", params);
	        List<String> opAuths = null;
	        if(IWebConstant.OP_SUCCESS.endsWith(smartResp.getResult())) {
	            opAuths = StringUtils.string2List(StringUtils.handleNull(smartResp.getDatas().get(0)), IWebConstant.MULTI_VALUE_SPLIT);
	        }
	        if(CollectionUtils.isNotEmpty(opAuths) && opAuths.contains(array[1])) {
	            is = true;
	        } else {
	            is = false;
	        }
	    } else {
	        is = true;
	    }
	    return is;
	}

	/********getter and setter*******/
	public List<String> getExcludeMaps() {
		return excludeMaps;
	}

	public void setExcludeMaps(List<String> excludeMaps) {
		this.excludeMaps = excludeMaps;
	}

	public String getResSuffix() {
		return resSuffix;
	}

	public void setResSuffix(String resSuffix) {
		this.resSuffix = resSuffix;
	}

    public List<String> getAuthUriList() {
        return authUriList;
    }

    public void setAuthUriList(List<String> authUriList) {
        this.authUriList = authUriList;
    }

    public List<String> getExcludeAccessLogUrls() {
        return excludeAccessLogUrls;
    }

    public void setExcludeAccessLogUrls(List<String> excludeAccessLogUrls) {
        this.excludeAccessLogUrls = excludeAccessLogUrls;
    }
}
