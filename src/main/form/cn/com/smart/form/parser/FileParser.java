package cn.com.smart.form.parser;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.smart.init.config.MimeTypeConfig;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

/**
 * 解析上传文件类型
 * @author lmq
 * @version 1.0 
 * @since 1.0
 * 2015年7月4日
 */
@Component
public class FileParser implements IFormParser {
	
	@Override
	public String getPlugin() {
		return "file";
	}
	
	@Override
	public String parse(Map<String,Object> dataMap) {
		if(null == dataMap || dataMap.size()<1) {
			return null;
		}
		boolean isHide = false;
		if(YesNoType.YES.getStrValue().equals(StringUtils.handleNull(dataMap.get("orghide")))) {
			isHide = true;
		}
		
		String value = StringUtils.handleNull(dataMap.get("value"));
		String fileType = StringUtils.handleNull(dataMap.get("filetype"));
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("<input type=\"file\" name=\""+StringUtils.handleNull(dataMap.get("bind_table_field"))+"\" id=\""+dataMap.get("bind_table_field")+"\" data-label-name=\""+dataMap.get("title")+"\" value=\""+value+"\"  style=\""+dataMap.get("style")+"\"");
		if(StringUtils.isNotEmpty(fileType)) {
			strBuild.append(" accept=\""+MimeTypeConfig.getInstance().getValues(fileType)+"\"");
		}
		String relateField = StringUtils.handleNull(dataMap.get("relate_field"));
		if(StringUtils.isNotEmpty(relateField)) {
			strBuild.append(" relate-field=\""+relateField+"\"");
		}
		String relateFieldValue = StringUtils.handleNull(dataMap.get("relate_field_value"));
		if(StringUtils.isNotEmpty(relateFieldValue)) {
			strBuild.append(" relate-field-value=\""+relateFieldValue+"\"");
		}
		String className = StringUtils.handleNull(dataMap.get("class"));
		strBuild.append(" class=\""+className+(isHide?" hide":"")+"\"");
		strBuild.append(" />");
		return strBuild.toString();
	}
	
}
