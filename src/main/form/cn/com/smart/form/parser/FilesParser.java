package cn.com.smart.form.parser;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.smart.form.enums.FormType;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.StringUtils;

/**
 * 上传文件插件解析器
 * @author lmq  2017年4月25日
 * @version 1.0
 * @since 1.0
 */
@Component
public class FilesParser implements IFormParser {

	@Override
	public String getPlugin() {
		return "files";
	}

	@Override
	public String parse(Map<String, Object> dataMap) {
		if(null == dataMap || dataMap.size()<1) {
			return null;
		}
		boolean isHide = false;
		if(YesNoType.YES.getStrValue().equals(StringUtils.handleNull(dataMap.get("orghide")))) {
			isHide = true;
		}
		boolean isRequire = false;
		if(YesNoType.YES.getStrValue().equals(StringUtils.handleNull(dataMap.get("fieldrequire")))) {
			isRequire = true;
		}
		String url = "";
		String fileType = StringUtils.handleNull(dataMap.get("filetype"));
		String btnStyle = StringUtils.handleNull(dataMap.get("org_btn_style"));
		String formType = StringUtils.handleNull(dataMap.get("formtype"));
		String fieldId = StringUtils.handleNull(dataMap.get("bind_table_field"));
		String maxFileNum = StringUtils.handleNull(dataMap.get("maxfiles"));
		String maxFileSize = StringUtils.handleNull(dataMap.get("filesize"));
		String remarks = StringUtils.handleNull(dataMap.get("remarks"));
		String jsCallback = StringUtils.handleNull(dataMap.get("jscallback"));
		String orgUri = StringUtils.handleNull(dataMap.get("orguri"));
		String formId = StringUtils.handleNull(dataMap.get("formid"));
		
		if(StringUtils.isEmpty(btnStyle)) {
			btnStyle = "btn-success";
		}
		if(StringUtils.isEmpty(fileType)) {
			fileType = "jpg,png,gif,doc,docx,xls,xlsx,ppt,pptx,pdf,zip,rar,txt";
		}
		if(StringUtils.isEmpty(maxFileNum) || !StringUtils.isInteger(maxFileNum)) {
			maxFileNum = "5";
		}
		if(StringUtils.isEmpty(jsCallback)) {
			jsCallback = "showFormAttList";
		}
		if(StringUtils.isEmpty(orgUri)) {
			FormType formTypeObj = FormType.getObj(formType);
			if(null == formTypeObj) {
				throw new NullArgumentException("表单类型值为空");
			}
			url = formTypeObj.getUri();
		} else {
			url = orgUri;
		}
		
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("<div class=\"file-upload-wrap\"><div id=\""+fieldId+"\" data-label-name=\""+StringUtils.handleNull(dataMap.get("title"))+"\"");
		String relateField = StringUtils.handleNull(dataMap.get("relate_field"));
		if(StringUtils.isNotEmpty(relateField)) {
			strBuild.append(" relate-field=\""+relateField+"\"");
		}
		String relateFieldValue = StringUtils.handleNull(dataMap.get("relate_field_value"));
		if(StringUtils.isNotEmpty(relateFieldValue)) {
			strBuild.append(" relate-field-value=\""+relateFieldValue+"\"");
		}
		//String className = StringUtils.handleNull(dataMap.get("class"));
		strBuild.append(" class=\"file-upload "+(isHide?" hide":"")+" "+(isRequire?" require":"")+" hidden-print\"");
		strBuild.append(">");
		strBuild.append("<span class=\"btn "+btnStyle+" btn-sm fileinput-button upload-add\">");
		strBuild.append("<i class=\"glyphicon glyphicon-paperclip\" aria-hidden=\"true\"></i> <span>添加附件</span>");
		strBuild.append("<input class=\"cnoj-upload\" data-limit-upload-num=\""+maxFileNum+"\" data-uri=\""+url+"\" data-close-after=\""+jsCallback+"\" data-accept-file-types=\""+fileType+"\" id=\""+fieldId+"-mfile\" type=\"file\" name=\"atts\" multiple ");
		if(StringUtils.isNotEmpty(maxFileSize) && StringUtils.isNum(maxFileSize)) {
			try {
				float fileSize = Float.parseFloat(maxFileSize) * 1024 * 1024;
				strBuild.append(" data-max-file-size='"+((long)fileSize)+"'");
			} catch(Exception e) {}
		}
		if(StringUtils.isNotEmpty(formId)) {
			strBuild.append(" data-form-data=\""+formId+"\"");
		}
		strBuild.append("/></span>");
		if(StringUtils.isNotEmpty(remarks)) {
			strBuild.append("<div class=\"clear\"></div><div class=\"help-block\"></div>");
		}
		strBuild.append("</div></div>");
		return strBuild.toString();
	}

	
	
}
