package cn.com.smart.form.parser;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.smart.form.enums.FormControlFieldType;
import cn.com.smart.form.enums.FormDataSourceType;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

/**
 * 解析下拉框
 * @author lmq
 * @version 1.0 
 * @since 1.0
 * 2015年7月4日
 */
@Component
public class SelectParser implements IFormParser {

	@Override
	public String getPlugin() {
		return "select";
	}

	@Override
	public String parse(Map<String, Object> dataMap) {
		if(null == dataMap || dataMap.size()<1) {
			return null;
		}
		String content = StringUtils.handleNull(dataMap.get("content"));
		String fromData = StringUtils.handleNull(dataMap.get("from_data"));
		String dynamicLoad = StringUtils.handleNull(dataMap.get("dynamicload"));
		String formControlField = StringUtils.handleNull(dataMap.get("form_control_field"));
		FormControlFieldType fcfType = FormControlFieldType.getObjByValue(formControlField);
		String fcfClassName = null;
		if(null != fcfType) {
			fcfClassName = fcfType.getPluginValue();
		}
		FormDataSourceType sourceType = FormDataSourceType.getObj(fromData);
		switch (sourceType) {
		case DICT:
		case CUSTOM_URI:
			if(YesNoType.YES.getStrValue().equals(dynamicLoad)) {
				StringBuilder strBuild = new StringBuilder();
				strBuild.append("<select name=\""+StringUtils.handleNull(dataMap.get("bind_table_field"))+"\" id=\""+dataMap.get("bind_table_field")+"\" data-label-name=\""+dataMap.get("title")+"\" ");
				strBuild.append(" class=\""+dataMap.get("class")+" cnoj-select "+StringUtils.handleNull(fcfClassName)+" \"");
				strBuild.append(" style=\""+dataMap.get("style")+"\" data-uri=\""+dataMap.get("data_uri")+"\"></select>");
				content = strBuild.toString();
				break;
			}
		default:
			if(!StringUtils.isEmpty(content)) {
				content = content.replaceAll("<select([^>].*?)>", "<select class=\""+dataMap.get("class")+" "+StringUtils.handleNull(fcfClassName)+"\" name=\"111\" >");
				content = content.replaceAll("(leipiplugins=\".*?\")|(field.*?=\".*?\")|(org.*?=\".*?\")|(from.*?=\".*?\")|(bind_.*?=\".*?\")", "");
				content = content.replaceAll("name=\".*?\"", "name=\""+dataMap.get("bind_table_field")+"\" id=\""+dataMap.get("bind_table_field")+"\" data-label-name=\"" + dataMap.get("title") + "\" style=\""+dataMap.get("style")+"\"");
			}
			break;
		}
		return content;
	}
	
	
}
