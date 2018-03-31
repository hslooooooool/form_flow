package cn.com.smart.web.tag;

import com.mixsmart.utils.StringUtils;

/**
 * 表格异步树
 * @author lmq
 * @version 1.0
 */
public class TableAsyncTreeTag extends AbstractTableTreeTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5412228218160950424L;
	
	/**
	 * 异步加载请求地址
	 */
	private String asyncUrl;

	@Override
	protected String getHtml(Boolean isParent,Object[] objArray, int row, int layer, 
	        String defaultValue, int startIndex, int cols, int headerCount) {
	    StringBuilder strBuff = new StringBuilder();
		String classOpTree = "tr-shrink-tree";
		int colNum = objArray.length - startIndex; 
		if(StringUtils.isNotEmpty(this.asyncUrl) && this.asyncUrl.indexOf("?") == -1) {
			this.asyncUrl += "?1=1";
		}
		strBuff.append("<tr data-col-num='"+colNum+"' data-layer='"+layer+"'  data-async-url='"+this.asyncUrl+"&parentId="+StringUtils.handleNull(objArray[0])+"' id='t-"+StringUtils.handleNull(objArray[0])+"' class='tr-tree tr-async-tree "+classOpTree+" t-tree-layer"+layer+" t-"+StringUtils.handleNull(objArray[1])+"' parentid='t-"+StringUtils.handleNull(objArray[1])+"'>");
		int count = 0;
		String tdOpData =  "";
        String uiIconOpData = "";
        if(isParent) {
            tdOpData = "shrink-data";
             uiIconOpData = "ui-icon-triangle-1-e";
            tdOpData = "op-tree "+tdOpData;
        } else {
            uiIconOpData = "ui-icon-radio-on";
        }
        
		String a = getTdContent(objArray, row, defaultValue, count, startIndex);
		strBuff.append("<td class='"+tdOpData+" td-tree "+getTdClass(count)+"' "+super.getTdWidthStyle(thWidth,count)+"><span class='ui-icon "+uiIconOpData+" left'></span> &nbsp;"+a+"</td>");
		for (int i = startIndex; i < objArray.length; i++) {
			count++;
			if(count > cols) {
				break;
			}
			a = getTdContent(objArray, row,StringUtils.handleNull(objArray[i]), count, i);
			strBuff.append("<td "+(StringUtils.isEmpty(getTdClass(count))?"":"class='"+getTdClass(count)+"'")+" "+super.getTdWidthStyle(thWidth,count)+">"+a+"</td>");
			
		}
		strBuff.append(super.handleLastCustomCell(objArray, row, count, headerCount, tdStyles, thWidth));
		strBuff.append("</tr>");
		return strBuff.toString();
	}
	

	@Override
	protected String getTableDivTag() {
		return "cnoj-async-tree-table";
	}
	
	public String getAsyncUrl() {
		return asyncUrl;
	}

	public void setAsyncUrl(String asyncUrl) {
		this.asyncUrl = asyncUrl;
	}

	
	
}
