package cn.com.smart.web.tag;

import javax.servlet.ServletContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.constant.IActionConstant;
import cn.com.smart.web.constant.IWebConstant;

/**
 * 自定义标签基类
 * @author lmq
 *
 */
public class BaseTag extends TagSupport implements IWebConstant {

	private static final long serialVersionUID = 5895739595457323921L;

	protected static final Logger log = Logger.getLogger(BaseTag.class);
	
	protected String currentUri;
	
	protected static final String BTN_DEFAULT_THEME = "btn-default";
	
	/**
	 * 从session获取用户信息
	 * @return 获取用户信息对象
	 */
	protected UserInfo getUserInfo() {
		return (UserInfo)pageContext.getSession().getAttribute(IActionConstant.SESSION_USER_KEY);
	}
	
	/**
	 * 根据名称获取服务对象
	 * @param name 服务名称
	 * @return 返回服务对象
	 */
	protected Object getService(String name) {
		  ServletContext servletContext = pageContext.getServletContext();
	      WebApplicationContext webAppContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	     return webAppContext.getBean(name);
	}

	/**
	 * 获取当前uri
	 * @return uri
	 */
	public String getCurrentUri() {
		return currentUri;
	}

	/**
	 * 设置当前uri
	 * @param currentUri
	 */
	public void setCurrentUri(String currentUri) {
		this.currentUri = currentUri;
	}
	
}
