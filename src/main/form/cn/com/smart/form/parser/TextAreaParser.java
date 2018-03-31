package cn.com.smart.form.parser;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

/**
 * 解析文本域
 * @author lmq
 * @version 1.0 
 * @since 1.0
 * 2015年7月4日
 */
@Component
public class TextAreaParser implements IFormParser {

	@Override
	public String getPlugin() {
		return "textarea";
	}

	@Override
	public String parse(Map<String, Object> dataMap) {
		if(null == dataMap || dataMap.size()<1) {
			return null;
		}
		boolean isHide = false;
		if("1".equals(StringUtils.handleNull(dataMap.get("orghide")))) {
			isHide = true;
		}
		String orgRich = StringUtils.handleNull(dataMap.get("orgrich"));
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("<textarea name=\""+StringUtils.handleNull(dataMap.get("bind_table_field"))+"\" id=\""+dataMap.get("bind_table_field")+"\" ");
		String className = StringUtils.handleNull(dataMap.get("class"));
		if(YesNoType.YES.getStrValue().equals(orgRich)) {
			className = className.replace("form-control", "");
		}
		strBuild.append("class=\""+className+(("1".equals(orgRich))?" cnoj-richtext":""));
		if(isHide) strBuild.append(" hide ");
		strBuild.append("\"");
		strBuild.append("data-label-name=\""+dataMap.get("title")+"\" style=\""+dataMap.get("style")+"\"");
		String relateField = StringUtils.handleNull(dataMap.get("relate_field"));
		if(StringUtils.isNotEmpty(relateField)) {
			strBuild.append(" relate-field=\""+relateField+"\"");
		}
		String relateFieldValue = StringUtils.handleNull(dataMap.get("relate_field_value"));
		if(StringUtils.isNotEmpty(relateFieldValue)) {
			strBuild.append(" relate-field-value=\""+relateFieldValue+"\"");
		}
		strBuild.append(" >");
		String defaultValue = StringUtils.handleNull(dataMap.get("value"));
		if(!StringUtils.isEmpty(defaultValue)) {
			defaultValue = defaultValue.replace("&lt;br/&gt;", "\n");
			strBuild.append(defaultValue);
		}
		strBuild.append("</textarea>");
		
		/*if("1".equals(orgRich)) {
			//strBuild.append("<script> UE.getEditor(\""+dataMap.get("bind_table_field")+"\",{toolbars:[[\"fullscreen\", \"source\", \"|\",\"bold\", \"italic\", \"underline\",\"|\", \"justifyleft\", \"justifycenter\", \"justifyright\", \"justifyjustify\"]],wordCount:false,elementPathEnabled:false");
			//strBuild.append("});</script>");
		}*/
		return strBuild.toString();
	}

}
