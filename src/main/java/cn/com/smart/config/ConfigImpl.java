package cn.com.smart.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.com.smart.Smart;

import com.mixsmart.utils.StringUtils;

/**
 * 配置实现类
 * @author lmq
 *
 */
public abstract class ConfigImpl extends Smart implements IConfig {
	
	protected static final Logger log = Logger.getLogger(ConfigImpl.class);

	@Override
	public String handleVar(String value) {
		if(StringUtils.isNotEmpty(value)) {
			String regex = "(?<=\\$\\{)[^\\{\\}]+(?=\\})";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(value);
			while(matcher.find()) {
				String varName = matcher.group();
				if(StringUtils.isNotEmpty(varName)) {
					String val = null;
					try {
						val = getValue(varName);
					} catch (Exception e) {
						log.info("变量【"+val+"】没定义");
						e.printStackTrace();
						val = null;
					}
					if(StringUtils.isEmpty(val)) {
						log.info("变量【"+val+"】没定义");
					}
					value = value.replace("${"+varName+"}", StringUtils.handleNull(val));
				}
			}
			matcher = pattern.matcher(value);
			if(matcher.find()) {
				handleVar(value);
			}
			pattern = null;
			matcher = null;
			return value;
		}
		return value;
	}

	@Override
	public String handleSysVar(String value) {
		String regex = "(?<=#\\{)[^\\{\\}]+(?=\\})";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		while(matcher.find()) {
			String varName = matcher.group();
			if(StringUtils.isNotEmpty(varName)) {
				String val = null;
				try {
				   val = System.getProperty(varName);
				} catch (Exception e) {
					log.info("系统属性【"+val+"】没有找到");
					e.printStackTrace();
					val = null;
				}
				value = value.replace("#{"+varName+"}", StringUtils.handleNull(val));
			}
		}
		matcher = pattern.matcher(value);
		if(matcher.find()) {
			handleSysVar(value);
		}
		pattern = null;
		matcher = null;
		return value;
	}

}
