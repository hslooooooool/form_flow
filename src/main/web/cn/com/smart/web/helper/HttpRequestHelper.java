package cn.com.smart.web.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.constant.IConstant;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.constant.IActionConstant;

/**
 * HttpRequest助手
 * @author lmq
 *
 */
public class HttpRequestHelper {
	
	/**
	 * 获取当前URI(不含参数)
	 * @param request
	 * @return
	 */
	public static String getCurrentUri(HttpServletRequest request) {
		String currentUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		if(StringUtils.isNotEmpty(contextPath)) {
			currentUri = currentUri.replaceFirst(contextPath+"/", "");
		} else {
			currentUri = currentUri.substring(1, currentUri.length());
		}
		return currentUri;
	}
	
	
	/**
	 * 获取当前URI
	 * @param request
	 * @return
	 */
	public static String getCurrentUriParam(HttpServletRequest request) {
		String currentUri = getCurrentUri(request);
		if(StringUtils.isNotEmpty(request.getQueryString())) {
			currentUri +="?"+request.getQueryString();
		}
		try {
			currentUri = URLDecoder.decode(currentUri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return currentUri;
	}
	
	/**
	 * 设置session值
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void setSession(HttpServletRequest request,String key,Object value) {
		HttpSession session = request.getSession();
		if(null != session) {
			session.setAttribute(key, value);
		}
		session = null;
	}
	
	/**
	 * 获取session值
	 * @param request
	 * @param key
	 * @return
	 */
    public static Object getSession(HttpServletRequest request,String key) {
    	HttpSession session = request.getSession();
    	Object obj = null;
		if(null != session) {
			obj = session.getAttribute(key);
		}
		session = null;
		return obj;
	}
    
    
    /**
	 * 添加用户信息到session
	 * @param session
	 * @param userInfo
	 */
	public static void setUserInfo2Session(HttpSession session,UserInfo userInfo) {
		if(null != session) {
			session.setAttribute(IActionConstant.SESSION_USER_KEY, userInfo);
		}
	}
	
	
	/**
	 * 从session中获取用户信息
	 * @param session
	 * @return
	 */
    public static UserInfo getUserInfoFromSession(HttpSession session) {
    	UserInfo userInfo = null;
    	if(null != session) {
    		userInfo = (UserInfo)session.getAttribute(IActionConstant.SESSION_USER_KEY);
    	}
    	return userInfo;
	}
    
    
    /**
	 * 添加用户信息到session
	 * @param session
	 * @param userInfo
	 */
	public static void setUserInfo2Session(HttpServletRequest request,UserInfo userInfo) {
		if(null != request) {
			getUserInfoFromSession(request.getSession());
		}
	}
	
	
	/**
	 * 从session中获取用户信息
	 * @param session
	 * @return
	 */
    public static UserInfo getUserInfoFromSession(HttpServletRequest request) {
    	UserInfo userInfo = null;
    	if(null != request) {
    		userInfo = (UserInfo)request.getSession().getAttribute(IActionConstant.SESSION_USER_KEY);
    	}
    	return userInfo;
	}
    
    /**
     * 获取客户端IP地址
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
    	String ip = request.getHeader("x-forwarded-for");
    	if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("Proxy-Client-IP");
    	} 
    	if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("WL-Proxy-Client-IP");
    	} 
    	if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getRemoteAddr();
    	}
    	ip = "unknown".equalsIgnoreCase(ip) ? "" : ip;
    	if(StringUtils.isNotEmpty(ip) && ip.indexOf(IConstant.MULTI_VALUE_SPLIT)>-1) {
    		String[] ipArray = ip.split(IConstant.MULTI_VALUE_SPLIT);
    		for (String ipTmp : ipArray) {
				if(!"unknown".equals(ipTmp)) {
					ip = ipTmp;
					break;
				}
			}
    	}
    	return ip;
    } 
    
    /**
     * 获取域名（含端口）
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
    	String domain = null;
    	String path = request.getContextPath();
    	domain = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    	return domain;
    }
	
}
