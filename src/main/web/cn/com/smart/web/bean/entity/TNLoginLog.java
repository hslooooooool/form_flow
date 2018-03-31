package cn.com.smart.web.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;
import cn.com.smart.web.constant.enums.LoginType;

import com.mixsmart.enums.YesNoType;

/**
 * 用户登录日志（实体Bean）
 * @author lmq
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@Table(name = "t_n_login_log")
public class TNLoginLog extends BaseBeanImpl implements DateBean {

	private static final long serialVersionUID = -8368756499304249621L;
	
	private String id;
	
	private String userId;
	
	private String username;
	
	private Boolean state = YesNoType.NO.getValue();
	
	private String msg;
	
	private Date createTime;
	
	private String ip;
	
	private String deviceType;
	
	private String userAgent;
	
	private String browser;
	
	private String browserVersion;
	
	private String os;
	
	private String loginType = LoginType.NORMAL.getName();
	
	/**
	 * 分辨率
	 */
	private String resolution;
	
	private Float clientScreenWidth;
	
	private Float clientScreenHeight;

	@Id
	@Column(name = "id", length=50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "user_id", length = 50)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "msg",length=255)
	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Column(name = "ip",length=50)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name="username", length=127)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name="state")
	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
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

	@Column(name="os", length=127)
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
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
	
	@Column(name="login_type", length=100, nullable=false)
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	@Column(name="resolution", length=127)
	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	@Column(name="client_screen_width")
	public Float getClientScreenWidth() {
		return clientScreenWidth;
	}

	public void setClientScreenWidth(Float clientScreenWidth) {
		this.clientScreenWidth = clientScreenWidth;
	}

	@Column(name="client_screen_height")
	public Float getClientScreenHeight() {
		return clientScreenHeight;
	}

	public void setClientScreenHeight(Float clientScreenHeight) {
		this.clientScreenHeight = clientScreenHeight;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time",updatable=false)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Transient
	@Override
	public String getPrefix() {
		return "L";
	}

}