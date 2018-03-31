package cn.com.smart.filter.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import cn.com.smart.filter.HandleFilterParam;

import com.mixsmart.utils.StringUtils;

/**
 * 过滤参数
 * @author lmq
 *
 */
@Component
public class FilterParam implements IFilterParam {

	protected String id;
	
	protected String name;
	
	protected String state;
	
	protected String type;
	
	//过滤角色用
	protected String[] roleIds;
	
	//过滤数据权限用
	protected String[] orgIds;
	
	protected String orgId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		try {
			this.name = URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * 参数转化为字符串
	 * 注：页面分页时，不用该方法也支持根据条件分页
	 * @return 返回转化结果
	 */
	public String getParamToString() {
		Map<String, Object> map = toMap();
		StringBuilder paramBuilder = null;
		if(null != map) {
			paramBuilder = new StringBuilder();
			Set<Map.Entry<String, Object>> sets = map.entrySet();
			for (Map.Entry<String, Object> entry : sets) {
				paramBuilder.append(entry.getKey()+"="+StringUtils.handleNull(entry.getValue())+"&");
			}
			paramBuilder.delete(paramBuilder.length()-1, paramBuilder.length());
		}
		return (null == paramBuilder)?null:paramBuilder.toString();
	}
	
	@Override
	public Map<String, Object> toMap() {
		return new HandleFilterParam(this).getParams();
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String[] getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String[] orgIds) {
		this.orgIds = orgIds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}

