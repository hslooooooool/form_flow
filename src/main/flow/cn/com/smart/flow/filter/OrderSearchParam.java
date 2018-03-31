package cn.com.smart.flow.filter;

import cn.com.smart.filter.bean.FilterParam;

import com.mixsmart.utils.StringUtils;

/**
 * 流程实例搜索bean
 * @author lmq
 * @create 2015年7月15日
 * @version 1.0 
 * @since 
 *
 */
public class OrderSearchParam extends FilterParam {

	private String startDate;
	
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String getParamToString() {
		StringBuilder paramBuff = new StringBuilder();
		String param = super.getParamToString();
		if(StringUtils.isNotEmpty(startDate)) {
			paramBuff.append("startDate="+startDate+"&");
		}
		if(StringUtils.isNotEmpty(endDate)) {
			paramBuff.append("endDate="+endDate+"&");
		}
		if(StringUtils.isNotEmpty(paramBuff.toString())) {
			param = StringUtils.isEmpty(param)?paramBuff.toString():"&"+paramBuff.toString();
			param = param.substring(0, param.length()-1);
		}
		return param;
	}
	
}
