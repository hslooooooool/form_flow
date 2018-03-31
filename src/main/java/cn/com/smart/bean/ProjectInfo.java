package cn.com.smart.bean;

import cn.com.smart.init.config.InitSysConfig;

import com.mixsmart.utils.StringUtils;

/**
 * 项目属性
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public class ProjectInfo {

	/**
	 * 项目名称
	 */
	private String name;
	
	/**
	 * 开发模式
	 */
	private String devModel;
	
	/**
	 * 版权
	 */
	private String copyright;
	
	/**
	 * 联系方式
	 */
	private String contactInfo;
	
	/**
	 * 版本
	 */
	private String version;

	/**
	 * 获取项目名称
	 * @return 项目名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置项目名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 开发模式
	 * @return 返回值为1或0 <br />
	 * 1--表示开发模式；0--表示产品模式
	 */
	public String getDevModel() {
		return devModel;
	}

	/**
	 * 设置开发模式
	 * @param devModel
	 */
	public void setDevModel(String devModel) {
		this.devModel = devModel;
	}

	/**
	 * 版权
	 * @return 返回版权信息
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * 设置版权
	 * @param copyright
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * 获取联系信息
	 * @return 返回系统的联系信息
	 */
	public String getContactInfo() {
		return contactInfo;
	}

	/**
	 * 设置联系信息
	 * @param contactInfo
	 */
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * 版本信息
	 * @return 系统版本号
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 设置版本信息
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * 初始化参数值
	 * @param prefix
	 * @param config
	 */
	public void initParam(String prefix,InitSysConfig config) {
		if(StringUtils.isNotEmpty(prefix)) {
			prefix += ".";
		}
		this.name = StringUtils.handleNull(config.getValue(prefix+"name"));
		
		this.devModel = StringUtils.handleNull(config.getValue(prefix+"devModel"));
		this.copyright = StringUtils.handleNull(config.getValue(prefix+"copyright"));
			
		this.contactInfo = StringUtils.handleNull(config.getValue(prefix+"contactInfo"));
		this.version = StringUtils.handleNull(config.getValue(prefix+"version"));
	}
}
