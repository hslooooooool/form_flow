package cn.com.smart.init.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.smart.constant.IConstant;
import cn.com.smart.init.Init;

import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 初始化系统配置文件
 * @author lmq
 *
 */
public class MimeTypeConfig implements Init {
	
	private static MimeTypeConfig instance;
	
	private static final String MIME_CONFIG_FILE = "/mime-type.properties";
	
	private static final Logger logger = LoggerFactory.getLogger(MimeTypeConfig.class);
	
	private long lastModifyTime = 0;
	
	private Properties prop = null;
	
	private MimeTypeConfig() {
		init();
	}
	
	/**
	 * 重新加载配置文件
	 */
	private void reloadConfig() {
		String devModel = InitSysConfig.getInstance().getValue("project.devModel");
		if(StringUtils.isNotEmpty(devModel) && IConstant.PROJECT_DEV_MODEL.equals(devModel)) {
			URL path = this.getClass().getResource(MIME_CONFIG_FILE);
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
	public synchronized static MimeTypeConfig getInstance() {
		if(null == instance) {
			instance = new MimeTypeConfig();
		}
		instance.reloadConfig();
		return instance;
	}
	
	
	protected void init() {
		LoggerUtils.info(logger,"初始化mime配置文件-------");
		InputStream in = null;
		try {
		  in = getClass().getResourceAsStream(MIME_CONFIG_FILE);
		  if(null != in) {
			  prop = new Properties();
			  prop.load(in);
		  }
		  URL path = this.getClass().getResource(MIME_CONFIG_FILE);
		  File file = new File(path.getFile());
		  lastModifyTime  = file.lastModified();
		  path = null;
		  file = null;
		  LoggerUtils.info(logger,"初始化mime配置文件[结束]-------");
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * 获取多个值
	 * @param keys 多个关键字直接用 {@link IConstant#MULTI_VALUE_SPLIT} 分隔
	 * @return 返回多个值，多个值直接用  {@link IConstant#MULTI_VALUE_SPLIT} 分隔
	 */
	public String getValues(String keys) {
		StringBuilder valueBuild = null;
		if(StringUtils.isEmpty(keys)) {
			return null;
		}
		valueBuild = new StringBuilder();
		String[] keyArray = keys.split(IConstant.MULTI_VALUE_SPLIT);
		for (int i = 0; i < keyArray.length; i++) {
			valueBuild.append(getValue(keyArray[i])+IConstant.MULTI_VALUE_SPLIT);
		}
		valueBuild.delete(valueBuild.length()-1, valueBuild.length());
		return valueBuild.toString();
	}
	
	@Override
	public void reInit() {
		init();
	}
	
}
