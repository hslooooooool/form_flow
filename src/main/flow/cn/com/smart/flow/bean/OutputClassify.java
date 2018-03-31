package cn.com.smart.flow.bean;

import java.util.List;

import cn.com.smart.form.bean.NameValueMap;

/**
 * 流程出口线分类，分为两种:驳回线和正常直走线
 * @author lmq
 * @create 2015年7月13日
 * @version 1.0 
 * @since 
 *
 */
public class OutputClassify {

	/**
	 * 驳回线
	 */
	private List<NameValueMap> backLines;
	
	/**
	 * 正常直走线
	 */
	private List<NameValueMap> normalLines;

	public List<NameValueMap> getBackLines() {
		return backLines;
	}

	public void setBackLines(List<NameValueMap> backLines) {
		this.backLines = backLines;
	}

	public List<NameValueMap> getNormalLines() {
		return normalLines;
	}

	public void setNormalLines(List<NameValueMap> normalLines) {
		this.normalLines = normalLines;
	}
	
}
