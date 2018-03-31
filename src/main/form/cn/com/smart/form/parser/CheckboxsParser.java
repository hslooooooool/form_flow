package cn.com.smart.form.parser;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.smart.form.enums.AlignmentMode;
import cn.com.smart.form.enums.FormDataSourceType;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

/**
 * 解析复选
 * @author lmq
 * @version 1.0 
 * @since 1.0
 * 2015年7月4日
 */
@Component
public class CheckboxsParser implements IFormParser {
	
	@Override
	public String getPlugin() {
		return "checkboxs";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String parse(Map<String, Object> dataMap) {
		if(null == dataMap || dataMap.size()<1) {
			return null;
		}
		List<Map<String,Object>> options = (List<Map<String, Object>>) dataMap.get("options");
		if(null == options || options.size()<1) {
			return null;
		}
		StringBuilder strBuild = new StringBuilder();
		String fromData = StringUtils.handleNull(dataMap.get("from_data"));
		String alignmentMode = StringUtils.handleNull(dataMap.get("orgchecked"));
		String dynamicLoad = StringUtils.handleNull(dataMap.get("dynamicload"));
		FormDataSourceType sourceType = FormDataSourceType.getObj(fromData);
		String fieldRequire = StringUtils.handleNull(dataMap.get("fieldrequire"));
		String require = "";
		if(YesNoType.YES.getStrValue().equals(fieldRequire)) {
			require = "require";
		}
		switch (sourceType) {
		case DICT:
		case CUSTOM_URI:
			if(YesNoType.YES.getStrValue().equals(dynamicLoad)) {
				String isHorizontal = "yes";
				if(alignmentMode.equals(AlignmentMode.Vertical.getValue())) {
					isHorizontal = "no";
				}
				strBuild.append("<span class=\"cnoj-checkbox checkbox-parent\" data-label-name=\""+StringUtils.handleNull(dataMap.get("title"))+"\" data-is-horizontal=\""+isHorizontal+"\" data-require=\""+require+"\" data-name=\""+dataMap.get("bind_table_field")+"\" data-uri=\""+StringUtils.handleNull(dataMap.get("data_uri"))+"\"");
				strBuild.append("></span>");
				break;
			}
		default:
			String checked = "";
			strBuild.append("<span class=\"checkbox-parent\" data-label-name=\""+StringUtils.handleNull(dataMap.get("title"))+"\">");
			for (Map<String,Object> option : options) {
				if("checked".equals(option.get("checked"))) {
					checked = "checked=\"checked\"";
				}
				if(dataMap.get("orgchecked").equals(AlignmentMode.Vertical.getValue())) {
					strBuild.append("<div>");
				}
				strBuild.append("<input type=\"checkbox\" name=\""+StringUtils.handleNull(dataMap.get("bind_table_field"))+"\" id=\""+dataMap.get("bind_table_field")+"_"+StringUtils.handleNull(option.get("value"))+"\" value=\""+StringUtils.handleNull(option.get("value"))+"\" ");
				strBuild.append(" "+checked+" /> <label class=\"text-normal\" for=\""+dataMap.get("bind_table_field")+"_"+StringUtils.handleNull(option.get("value"))+"\">"+StringUtils.handleNull(option.get("text"))+"</label>&nbsp;");
				if(dataMap.get("orgchecked").equals(AlignmentMode.Vertical.getValue())) {
					strBuild.append("</div>");
				}
			}//for
			strBuild.append("</span>");
		}
		options = null;
		dataMap.put("name", dataMap.get("parse_name"));
		return strBuild.toString();
	}
	
}
