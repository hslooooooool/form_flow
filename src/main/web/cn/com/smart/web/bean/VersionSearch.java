package cn.com.smart.web.bean;

import org.apache.commons.lang3.StringUtils;

import cn.com.smart.filter.bean.FilterParam;

/**
 * 版本搜索对象
 * @author lmq <br />
 * 2016年9月15日
 * @version 1.0
 * @since 1.0
 */
public class VersionSearch extends FilterParam {

	private String version;
	
	private String versionType;
	
	@Override
	public String getParamToString() {
		StringBuilder strBuilder = new StringBuilder();
		String param = super.getParamToString();
		if(StringUtils.isNotEmpty(param)) {
			strBuilder.append(param);
		}
		if(null != version) {
			strBuilder.append("&version="+version.toString());
		}
		if(StringUtils.isNotEmpty(versionType)) {
			strBuilder.append("&versionType="+versionType);
		}
		param = strBuilder.toString();
		if(StringUtils.isNotEmpty(param) && param.startsWith("&")) {
			param = param.substring(1);
		}
		return param;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	
}
