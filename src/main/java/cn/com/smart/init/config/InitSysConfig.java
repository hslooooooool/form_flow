package cn.com.smart.init.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import cn.com.smart.bean.ProjectInfo;
import cn.com.smart.config.ConfigImpl;
import cn.com.smart.constant.IConstant;
import cn.com.smart.init.Init;

import com.mixsmart.utils.StringUtils;

/**
 * 初始化系统配置文件
 * @author lmq
 *
 */
public class InitSysConfig extends ConfigImpl implements Init {
	
	private static InitSysConfig instance;
	
	private long lastModifyTime = 0;
	
	private Properties prop = null;
	
	private InitSysConfig() {
		init();
	}
	
	/**
	 * 重新加载配置文件
	 */
	private void reloadConfig() {
		String devModel = instance.getValue("project.devModel");
		if(StringUtils.isNotEmpty(devModel) && IConstant.PROJECT_DEV_MODEL.equals(devModel)) {
			URL path = this.getClass().getResource(IConstant.SYS_CONFIG_FILE);
			File file = new File(path.getFile());
			path = null;
			if(file.exists()) {
				//判断文件是否被修改过，如果文件已修改，则重新初始化文件
				if(file.lastModified()>lastModifyTime) {
					reInit();
				}
			}
			file = null;
		}
	}
	
	/**
	 * 获取实例
	 * @return InitSysConfig
	 */
	public synchronized static InitSysConfig getInstance() {
		if(null == instance) {
			instance = new InitSysConfig();
		}
		instance.reloadConfig();
		return instance;
	}
	
	
	protected void init() {
		log.info("初始化配置文件-------");
		InputStream in = null;
		try {
		  in = getClass().getResourceAsStream(IConstant.SYS_CONFIG_FILE);
		  if(null != in) {
			  prop = new Properties();
			  prop.load(in);
		  }
		  URL path = this.getClass().getResource(IConstant.SYS_CONFIG_FILE);
		  File file = new File(path.getFile());
		  lastModifyTime  = file.lastModified();
		  path = null;
		  file = null;
		  log.info("初始化配置文件[结束]-------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 获取值
	 * @param key
	 * @return String
	 */
	public String getValue(String key) {
		String value = null;
		if(null != prop && StringUtils.isNotEmpty(key)){
			try {
				value = StringUtils.handleNull(prop.get(key));
				value = handleSysVar(value);
				value = handleVar(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * 获取项目信息
	 * @return 获取项目信息对象
	 */
	public ProjectInfo getProjectInfo() {
		ProjectInfo projectInfo = null;
		if(null != prop){
			projectInfo = new ProjectInfo();
			projectInfo.initParam("project", this);
		}
		return projectInfo;
	}
	
	@Override
	public void reInit() {
		init();
	}
	
}
