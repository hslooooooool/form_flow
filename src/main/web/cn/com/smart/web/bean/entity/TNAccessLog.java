package cn.com.smart.web.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;

/**
 * 访问日志
 * @author lmq  2017年4月11日
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_n_access_log")
@DynamicUpdate(true)
public class TNAccessLog extends BaseBeanImpl implements DateBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4182197599199117796L;

	private String id;
	
	private String userId;
	
	private String username;
	
	private String loginId;
	
	private String uri;
	
	private String param;
	
	private String url;
	
	private String ip;
	
	private String deviceType;
	
	private String userAgent;
	
	private String browser;
	
	private String browserVersion;
	
	private String os;
	
	private Date createTime;
	
	private Date responseTime;
	
	/**
	 * 单位：毫秒
	 */
	private Long useTime;
	
	/**
	 * 请求方法：如：post；get等
	 */
	private String requestMethod;

	@Id
	@Column(name="id", length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="user_id", length=50)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="username", length=127)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name="login_id", length=50)
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	@Column(name="uri", length=127, nullable=false)
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Column(name="param", length=500)
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Column(name="url", length=500, nullable=false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="ip", length=127)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name="device_type", length=127)
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name="user_agent", length=255)
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Column(name="browser", length=127)
	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	@Column(name="browser_version", length=127)
	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	@Column(name="os", length=127)
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="response_time")
	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	@Column(name="use_time")
	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}
	
	@Column(name="request_method", length=100)
	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	@Transient
	@Override
	public String getPrefix() {
		return "A";
	}

}
