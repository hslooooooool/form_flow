package cn.com.smart.form.parser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.smart.form.enums.HelperPlugin.DisplayModel;
import cn.com.smart.form.enums.HelperPlugin.SourceType;

import com.mixsmart.utils.StringUtils;

/**
 * 帮助插件 -- 解析器
 * @author lmq <br />
 * 2016年10月21日
 * @version 1.0
 * @since 1.0
 */
@Component
public class HelpersParser implements IFormParser {

	@Override
	public String getPlugin() {
		return "helpers";
	}

	@Override
	public String parse(Map<String, Object> dataMap) {
		if(null == dataMap || dataMap.isEmpty()) {
			return null;
		}
		String name = StringUtils.handleNull(dataMap.get("orgname"));
		String source = StringUtils.handleNull(dataMap.get("orgsource"));
		String url = StringUtils.handleNull(dataMap.get("orgurl"));
		String disModelStr = StringUtils.handleNull(dataMap.get("display_model"));
		String helperId = StringUtils.handleNull(dataMap.get("helper_id"));
		String orgContent = StringUtils.handleNull(dataMap.get("orgcontent"));
		StringBuilder strBuild = new StringBuilder();
		strBuild.append(" <a title=\"帮助\"");
		SourceType sourceType = SourceType.getObj(source);
		if(null != sourceType) {
			String helperUrl = this.getHelperUrl(sourceType, url, helperId);
			if(StringUtils.isNotEmpty(helperUrl)) 
				handleDisplayModel(sourceType,strBuild, disModelStr, helperUrl, helperId);
			if(StringUtils.isEmpty(helperUrl) && StringUtils.isNotEmpty(orgContent)) {
				orgContent = orgContent.replace(" ", "&nbsp;");
				orgContent = orgContent.replaceAll("\\r\\n|\\n", "<br/>");
				strBuild.append(" href=\"javascript:void(0)\" id=\"P"+StringUtils.uuid()+"\" class=\"mix-popover\" data-content=\""+orgContent+"\"");
			}
		}
		strBuild.append("><i class=\"fa fa-question-circle-o\" aria-hidden=\"true\"></i> "+name+"</a>");
		
		return strBuild.toString();
	}

	/**
	 * 获取帮助URL
	 * @param sourceType
	 * @param url
	 * @param helperId
	 * @return
	 */
	private String getHelperUrl(SourceType sourceType, String url, String helperId) {
		String helperUrl = null;
		switch (sourceType) { 
		case LIBRARY:
			helperUrl = "form/helper/${method}/?id="+helperId;
			break;
		case CUSTOM_ADDR:
			helperUrl = url;
			break;
		case CUSTOM_CONTENT:
			helperUrl = null;
			break;
		default:
			break;
		}
		return helperUrl;
	}
	
	/**
	 * 处理显示方式
	 * @param sourceType
	 * @param strBuild
	 * @param disModelStr
	 * @param helperUrl
	 */
	private void handleDisplayModel(SourceType sourceType, StringBuilder strBuild, String disModelStr, String helperUrl, String helperId) {
		DisplayModel disModel = DisplayModel.getObj(disModelStr);
		switch (disModel) {
		case DISPLAY_DIALOG:
			if(sourceType == SourceType.LIBRARY ) {
				helperUrl = "showPage/form_helper_iframeView?id="+helperId;
			} else if(sourceType == SourceType.CUSTOM_ADDR){
				try {
					helperUrl = "showPage/form_helper_iframeView?url="+URLEncoder.encode(helperUrl, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				helperUrl = helperUrl.replace("${method}", "view");
			}
			strBuild.append(" href=\"javascript:void(0)\" class=\"cnoj-open-blank\" data-width=\"800\" data-title=\"帮助信息\" data-uri=\""+helperUrl+"\"");
			break;
		case DISPLAY_POPOVER:
			helperUrl = helperUrl.replace("${method}", "view");
			strBuild.append(" href=\"javascript:void(0)\" id=\"P"+StringUtils.uuid()+"\" class=\"mix-popover\" data-uri=\""+helperUrl+"\"");
			break;
		case DISPLAY_BLANK:
			helperUrl = helperUrl.replace("${method}", "blank");
			strBuild.append(" href=\""+helperUrl+"\" target=\"__blank\" ");
			break;
		default:
			break;
		}
	}
}
