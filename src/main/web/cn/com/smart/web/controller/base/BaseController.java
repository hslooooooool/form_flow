package cn.com.smart.web.controller.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.Smart;
import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.init.config.InitSysConfig;
import cn.com.smart.service.SmartContextService;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.constant.IActionConstant;
import cn.com.smart.web.helper.HttpRequestHelper;
import cn.com.smart.web.service.RoleService;
import cn.com.smart.web.tag.bean.ALink;
import cn.com.smart.web.tag.bean.CustomBtn;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;
import cn.com.smart.web.tag.bean.SelectedEventProp;

/**
 * 控制器基类
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
public abstract class BaseController extends Smart implements IBaseController {
	
	protected Logger log;
	
	protected static String baseDir = WEB_BASE_VIEW_DIR;
	
    protected EditBtn addBtn;
    
    protected EditBtn editBtn;
    
    protected DelBtn delBtn;
    
    protected RefreshBtn refreshBtn;
    
    protected PageParam pageParam;
    
    protected SelectedEventProp selectedEventProp;
    
    protected int isId = 1;
    
    protected int isShowId = 1;
    
    //是否有复选框
    protected int isCheckbox = 0;
    
    protected List<ALink> alinks;
    
    protected List<CustomBtn> customBtns;
    
    @Autowired
	protected RoleService roleServ;
    
    public BaseController() {
    	log = LoggerFactory.getLogger(getClass());
    }
    
    /**
	 * 获取配置文件里面的根目录 <br />
	 * 根目录从配置文件中获取，属性为：root.dir
	 * @return 返回配置文件里面的根目录
	 */
	protected String getRootDir() {
		return InitSysConfig.getInstance().getValue("root.dir");
	}
    
	/**
	 * 通过用户信息，判断该用户是否拥有超级管理员角色
	 * @param userInfo 用户信息
	 * @return 如果该用户拥有超级管理员角色，则返回：true，否则为：false
	 * @throws ServiceException
	 */
    protected boolean isSuperAdmin(UserInfo userInfo) throws ServiceException {
    	return roleServ.isSuperAdmin(userInfo.getRoleIds());
    }
    
    /**
     * 获取基础DAO
     * @param busiName
     * @return 获取Dao名称
     */
    protected BaseDaoImpl<?> getBaseDao(String busiName) {
    	BaseDaoImpl<?> dao = null;
    	if(StringUtils.isNotEmpty(busiName)) {
	    	String daoName = busiName+"Dao";
			try {
				dao = (BaseDaoImpl<?>)SmartContextService.findByName(daoName);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
		return dao;
    }
	
	/**
	 * 添加用户信息到session
	 * @param session
	 * @param userInfo
	 */
	protected void setUserInfo2Session(HttpSession session,UserInfo userInfo) {
		if(null != session) {
			HttpRequestHelper.setUserInfo2Session(session, userInfo);
		}
	}
	
	
	/**
	 * 从session中获取用户信息
	 * @param session
	 * @return 返回session中的用户信息
	 */
    protected UserInfo getUserInfoFromSession(HttpSession session) {
    	UserInfo userInfo = null;
    	if(null != session) {
    		userInfo = HttpRequestHelper.getUserInfoFromSession(session);
    	}
    	return userInfo;
	}
    
    /**
	 * 从session中获取用户信息
	 * @param request
	 * @return 返回session中的用户信息
	 */
    protected UserInfo getUserInfoFromSession(HttpServletRequest request) {
    	UserInfo userInfo = null;
    	if(null != request) {
    		userInfo = HttpRequestHelper.getUserInfoFromSession(request);
    	}
    	return userInfo;
	}
    
    /**
     * 获取基础目录名称
     * @return 返回基础目录名
     */
    protected String getBaseDir() {
    	return WEB_BASE_VIEW_DIR;
    }
	
    /**
     * 获取每页显示条数（分页）
     * @param perPageSize
     * @return 每页显示条数
     */
    protected int getPerPageSize(int perPageSize) {
		if(perPageSize<1) {
			perPageSize = getPerPageSize();
		}
		return perPageSize;
	}
    
    /**
     * 获取每页显示条数（分页）
     * @return 每页显示条数
     */
    protected int getPerPageSize() {
    	int perPageSize = 0;
    	try {
			perPageSize = Integer.parseInt(InitSysConfig.getInstance().getValue("page.per.size"));
		} catch (Exception e) {
			perPageSize = IActionConstant.PRE_PAGE_SIZE;
		}
		return perPageSize;
	}
    
    /**
     * 获取当前页（分页）
     * @param page
     * @return 获取当前页面数
     */
    protected int getPage(int page) {
		if(page<1) {
			page = 1;
		}
		return page;
	}
    
    /**
     * 获取总页数（分页）
     * @param total 总数据数
     * @param pageSize 每页显示数
     * @return 总页数
     */
    protected int getTotalPage(long total,int pageSize) {
		return (int) Math.ceil((double)total/pageSize);
	}
	
	/**
	 * 获取从哪条数据开始显示（分页）
	 * @param page 当前页
	 * @param pageSize 每页显示的数量
	 * @return 开始位置
	 */
    protected int getStartNum(int page,int pageSize) {
		int startNum = (getPage(page)-1)*getPerPageSize(pageSize);
		return startNum;
	}
	
	/**
	 * 获取开始位置（分页）
	 * @param page 当前页
	 * @return 开始位置
	 */
    protected int getStartNum(int page) {
		int startNum = (getPage(page)-1)*getPerPageSize();
		return startNum;
	}
    
    
    /**
     * 从当前的request请求中获取参数 <br />
     * 返回的map对象中包含了，组织机构ID集合（key为：orgIds）
     * @param request
     * @param isOrgFilter 是否按部门过滤
     * @return 返回Map
     */
	public Map<String,Object> getRequestParamMap(HttpServletRequest request, boolean isOrgFilter) {
		Map<String,Object> paramMaps = new HashMap<String, Object>();
		Map<String, String[]> curParamMaps = request.getParameterMap();
		if(null != curParamMaps && curParamMaps.size()>0) {
			for (String key : curParamMaps.keySet()) {
				String[] value = curParamMaps.get(key);
				if(null != value && value.length > 1) {
					paramMaps.put(key, value);
				} else if(null != value)
					paramMaps.put(key, value[0]);
			}
		}
		UserInfo userInfo = getUserInfoFromSession(request);
		if(isOrgFilter) {
			paramMaps.put("orgIds", StringUtils.list2Array(userInfo.getOrgIds()));
			paramMaps.put("orgId", userInfo.getOrgId());
		}
		curParamMaps = null;
		return paramMaps;
	}
	
	
	/**
	 * 根据paramName和paramValue两个参数中提取参数 <br />
	 * 如：paramName=“id,name”   paramValue="1,张三";则提取后等到的参数为：
	 * id=1;name="张三"；变量名称作为MAP的key，变量值作为MAP的value，放到到Map对象中 <br />
	 * <b>注：</b>paramName和paramValue要一对一；如上面的例子:id=1;name=张三
	 * 返回的map对象中包含了，组织机构ID集合（key为：orgIds）
	 * @param paramName 名称集合（多个名称之间用逗号","分割）
	 * @param paramValue 值集合（多个名称之间用逗号","分割）
	 * @param isOrgFilter 是否按部门过滤
	 * @return 返回处理后的Map
	 */
	protected Map<String,Object> getParamMaps(HttpSession session,String paramName,String paramValue, boolean isOrgFilter) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(paramName) && StringUtils.isNotEmpty(paramValue)) {
			String[] paramNames = paramName.split(",");
			String[] paramValues = paramValue.split(",");
			if(paramNames.length == paramValues.length) {
				for (int i = 0; i < paramNames.length; i++) {
					params.put(paramNames[i], paramValues[i]);
				}
			}
			paramNames = null;
			paramValues = null;
		}
		UserInfo userInfo = getUserInfoFromSession(session);
		if(isOrgFilter) {
			params.put("orgIds", StringUtils.list2Array(userInfo.getOrgIds()));
			params.put("orgId", userInfo.getOrgId());
		}
		return params;
	}
}
