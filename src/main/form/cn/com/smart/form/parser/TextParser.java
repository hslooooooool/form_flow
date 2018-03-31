package cn.com.smart.form.parser;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

/**
 * 解析文本框
 * @author lmq
 * @version 1.0 
 * @since 1.0
 * 2015年7月4日
 */
@Component
public class TextParser implements IFormParser {

	protected String plugin = "text";
	
	/**
	 * 树形结构
	 */
	protected static final String INPUT_PLUGIN_TREE = "cnoj-input-tree";
	
	/**
	 * 自动完成
	 */
	protected static final String INPUT_PLUGIN_AUTO_COMPLETE = "cnoj-auto-complete";
	
	/**
	 * 自动完成(关联)
	 */
	protected static final String INPUT_PLUGIN_AUTO_COMPLETE_RELATE = "cnoj-auto-complete-relate";
	
	/**
	 * 输入框选择
	 */
	protected static final String INPUT_PLUGIN_SELECT = "cnoj-input-select";
	
	@Override
	public String getPlugin() {
		return this.plugin;
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
		String classDefaultTag = "";
		if(StringUtils.isNotEmpty(value) && value.startsWith("${")) {
			if("${today}".equals(value)) {
				classDefaultTag = "cnoj-date-defvalue";
			} else if("${username}".equals(value)) {
				classDefaultTag = "cnoj-sysuser-defvalue";
			} else if("${deptname}".equals(value)) {
				classDefaultTag = "cnoj-sysdeptname-defvalue";
			}
			value = "";
		}
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("<input type=\"text\" name=\""+StringUtils.handleNull(dataMap.get("bind_table_field"))+"\" id=\""+dataMap.get("bind_table_field")+"\" data-label-name=\""+dataMap.get("title")+"\" value=\""+value+"\"  style=\""+dataMap.get("style")+"\"");
		String dataFormat = StringUtils.handleNull(dataMap.get("data_format"));
		if(!StringUtils.isEmpty(dataFormat)) {
			strBuild.append(" data-format=\""+dataFormat+"\"");
		}
		String dateFormat = StringUtils.handleNull(dataMap.get("date_format"));
		if(StringUtils.isNotEmpty(dateFormat)) {
			strBuild.append(" data-date-format=\""+dateFormat+"\"");
		}
		String relateField = StringUtils.handleNull(dataMap.get("relate_field"));
		if(StringUtils.isNotEmpty(relateField)) {
			strBuild.append(" relate-field=\""+relateField+"\"");
		}
		String relateFieldValue = StringUtils.handleNull(dataMap.get("relate_field_value"));
		if(StringUtils.isNotEmpty(relateFieldValue)) {
			strBuild.append(" relate-field-value=\""+relateFieldValue+"\"");
		}
		String className = StringUtils.handleNull(dataMap.get("class"))+" "+classDefaultTag;
		String orgType = StringUtils.handleNull(dataMap.get("orgtype"));
		String inputPlugin = StringUtils.handleNull(dataMap.get("input_plugin"));
		if(!StringUtils.isEmpty(inputPlugin)) {
			strBuild.append(" class=\""+className);
			if(isHide) strBuild.append(" hide ");
			strBuild.append(" "+inputPlugin+"\"");
			strBuild.append(" data-uri=\""+StringUtils.handleNull(dataMap.get("input_plugin_uri"))+"\"");
			if(inputPlugin.equals(INPUT_PLUGIN_SELECT) || inputPlugin.equals(INPUT_PLUGIN_TREE)) {
				if(YesNoType.YES.getStrValue().equals(StringUtils.handleNull(dataMap.get("fieldrequire"))))
					strBuild.append(" data-is-show-none=\"no\"");
			}
			if(inputPlugin.equals(INPUT_PLUGIN_SELECT)) {
				if(YesNoType.YES.getStrValue().equals(StringUtils.handleNull(dataMap.get("fieldrequire"))))
					strBuild.append(" data-is-show-all=\"no\"");
			}
		} else if(!("date").equals(orgType) && !"datetime".equals(orgType) && !"time".equals(orgType) && !"text".equals(orgType)){
			strBuild.append(" class=\""+className);
			if(isHide) strBuild.append(" hide ");
			strBuild.append(" \"");
			strBuild.append(" data-format=\""+orgType+"\" ");
		} else {
			strBuild.append(" class=\""+className+" "+(isHide?"hide":"")+" \"");
		}
		strBuild.append(" />");
		return strBuild.toString();
	}
	
}
