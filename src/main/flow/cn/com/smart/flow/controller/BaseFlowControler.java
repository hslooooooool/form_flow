package cn.com.smart.flow.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.helper.HttpRequestHelper;

/**
 * 流程控制器基类
 * @author lmq
 *
 */
public class BaseFlowControler extends BaseController {
	
	public String[] getGroups(HttpServletRequest request) {
		if(null != request) 
			return getGroups(request.getSession());
		else 
			return null;
	}
	
	public String[] getGroups(HttpSession session) {
		List<String> groups = null;
		String[] userGroups = null;
		UserInfo userInfo = HttpRequestHelper.getUserInfoFromSession(session);
		if(null != userInfo) {
			groups = new ArrayList<String>();
			if(StringUtils.isNotEmpty(userInfo.getDepartmentId())) {
				groups.add(userInfo.getDepartmentId());
			} else if(StringUtils.isNotEmpty(userInfo.getOrgId())) {
				groups.add(userInfo.getOrgId());
			}
			if(StringUtils.isNotEmpty(userInfo.getPositionId())) {
				groups.add(userInfo.getPositionId());
			}
			groups.add(userInfo.getId());
			userGroups = new String[groups.size()];
			groups.toArray(userGroups);
		}
		return userGroups;
	}
	
}
