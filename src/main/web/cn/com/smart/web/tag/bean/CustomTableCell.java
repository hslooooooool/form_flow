package cn.com.smart.web.tag.bean;

import java.util.Map;
import java.util.Set;

import com.mixsmart.utils.StringUtils;

/**
 * 表格自定义单元格 <br />
 * <code>content</code>内容中可以用变量；
 * 比如：内容是一个超链接，则为：&lt;a href="xxxxx?id=${id}"&gt;名称&lt;/a&gt;；
 * 然后在<code>paramsMap</code>参数（该参数为{@link Map}对象）上加上映射关系；
 * 如:paramsMap.put("id",1)，表示id对应的数据下标为1
 * @author lmq <br />
 * 2017年2月21日
 * @version 1.0
 * @since 1.0
 */
public class CustomTableCell {

	/**
	 * 
	 */
	private String content;
	
	private Integer position;
	
	private Map<String, Object> paramsMap;
	
	private ICustomCellCallback cellCallback;
	
	public CustomTableCell() {

	}

	public CustomTableCell(String content, Integer position,
			Map<String, Object> paramsMap) {
		this.content = content;
		this.position = position;
		this.paramsMap = paramsMap;
	}
	
	public CustomTableCell(String content, Integer position,
			Map<String, Object> paramsMap, ICustomCellCallback cellCallback) {
		this.content = content;
		this.position = position;
		this.paramsMap = paramsMap;
		this.cellCallback = cellCallback;
	}

	/**
	 * 替换内容
	 * @param objArray
	 * @return
	 */
	public String replaceContent(Object[] objArray) {
		if(null == paramsMap 
				|| paramsMap.size() == 0 
				|| null == objArray || objArray.length == 0) {
			return content;
		} else {
			String newContent = content;
			Set<String> params = paramsMap.keySet();
			for (String param : params) {
				Object value = paramsMap.get(param);
				if(null != value && StringUtils.isInteger(value.toString())) {
					int index = Integer.parseInt(value.toString());
					if(index < objArray.length) {
						newContent = newContent.replace("${"+param+"}", StringUtils.handleNull(objArray[index]));
					}
				} //if
			}//for
			return newContent;
		} //else
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public ICustomCellCallback getCellCallback() {
		return cellCallback;
	}

	public void setCellCallback(ICustomCellCallback cellCallback) {
		this.cellCallback = cellCallback;
	}
	
	
}
