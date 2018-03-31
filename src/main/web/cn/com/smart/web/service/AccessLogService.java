package cn.com.smart.web.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNAccessLog;

import com.mixsmart.utils.StringUtils;

/**
 * 访问日志服务类
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Service
public class AccessLogService extends MgrServiceImpl<TNAccessLog> {
	
	/**
	 * 更新响应时间等信息
	 * @param id
	 * @param responseTime
	 * @param useTime
	 */
	public void update(String id, Date responseTime, Long useTime) {
		if(StringUtils.isEmpty(id)) {
			return;
		}
		String hql = "update "+TNAccessLog.class.getName()+" set responseTime=:responseTime, useTime=:useTime where id=:id";
		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("id", id);
		params.put("responseTime", responseTime);
		params.put("useTime", useTime);
		getDao().executeHql(hql, params);
	}
	
}
