package cn.com.smart.form.parser;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.mixsmart.utils.StringUtils;

/**
 * 按钮 -- 解析器
 * @author lmq <br />
 * 2016年10月21日
 * @version 1.0
 * @since 1.0
 */
@Component
public class ButtonParser implements IFormParser {
	
	@Override
	public String getPlugin() {
		return "button";
	}

	@Override
	public String parse(Map<String, Object> dataMap) {
		if(null == dataMap || dataMap.isEmpty()) {
			return null;
		}
		StringBuilder strBuild = new StringBuilder();
		String name = StringUtils.handleNull(dataMap.get("orgname"));
		String btnStyle =  StringUtils.handleNull(dataMap.get("org_btn_style"));
		String btnIdClass = StringUtils.handleNull(dataMap.get("btn_id_class"));
		String btnIcon = StringUtils.handleNull(dataMap.get("btn_icon"));
		strBuild.append("<button type=\"button\" ");
		handleClass(strBuild, btnStyle, btnIdClass);
		strBuild.append(">");
		if(StringUtils.isNotEmpty(btnIcon)) {
			strBuild.append("<i class=\""+btnIcon+"\" aria-hidden=\"true\"></i> ");
		}
		strBuild.append(name+"</button>");
		return strBuild.toString();
	}
	
	/**
	 * 处理CLASS或ID
	 * @param strBuild
	 * @param btnStyle
	 * @param btnIdClass
	 */
	private void handleClass(StringBuilder strBuild, String btnStyle, String btnIdClass) {
		String id = null;
		strBuild.append(" class=\"btn "+btnStyle+" ");
		if(StringUtils.isNotEmpty(btnIdClass)) {
			String[] btnIdClassArray = btnIdClass.split("\\s+");
			for (int i = 0; i < btnIdClassArray.length; i++) {
				if(btnIdClassArray[i].startsWith(".")) {
					strBuild.append(btnIdClassArray[i].substring(1)+" ");
				} else {
					id = btnIdClassArray[i].substring(1);
				}
			}
		}//if
		strBuild.append("\"");
		strBuild.append(" id=\""+id+"\"");
	}
	
}
