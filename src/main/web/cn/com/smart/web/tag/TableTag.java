package cn.com.smart.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import cn.com.smart.web.tag.bean.ALink;
import cn.com.smart.web.tag.bean.CustomTableCell;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 自定义表格标签
 * TODO 2017-02-21 新增自定义列的功能
 * @author lmq
 *
 */
public class TableTag extends AbstractPanelFooterTag {

	private static final long serialVersionUID = 1938463689212817558L;
	
	/**
	 * td Class默认样式
	 */
	protected static final String TD_CLASS = "text-left";

	//表格头数据,多个之间用英文逗号隔开
	protected String headers;
	//表格单元格宽度,多个之间用英文逗号隔开
	protected String headerWidths;
	
	//表格单元格样式,多个之间用英文逗号隔开
	protected String stylesClass;
	
	/**
	 * 是否为原始表格；如果是原始表格，则JS不会对该表格进行额外的处理；
	 * 否则把表格头和表格内容拆分后重新计算表格单元格的宽度; 
	 * 默认为非原始表格，允许JS对表格进行处理
	 */
	private Integer isOriginalTable = YesNoType.NO.getIndex();
	
	//数据中是否包含ID
    protected int isId = 1;
	
	//id是否显示
    protected int isIdShow = 0;
    
    /**
     * 表格ID 
     */
    protected String tableId;
	
    /**
     * 计算高度时需要减去的高度
     */
	protected Integer subtractHeight = 0;
    
    /**
     * 如果为null时，不限制高度，即auto
     * 如果为小于1时，则自动限制高度
     * 如果大于1时，则使用自定义的高度
     */
    protected String limitHeight = "0";
	
	//是否有复选框
    protected int isCheckbox = 0;
    
    /**
     * 是否支持行选中
     * 1--支持
     * 0--不支持
     */
    protected int isRowSelected = 0;
    
    protected List<ALink> alinks;
    
    private List<CustomTableCell> customCells;
    
    @Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			printTableBody(out);
			super.printFooter(out);
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public void release() {
		super.release();
		refreshBtn = null;
		delBtn = null;
		editBtn = null;
		addBtn = null;
		page = null;
		smartResp = null;
	}
    
	/**
	 * 生成表格
	 * @param out JSP输出对象
	 * @param divTag 页面标识符(div名称)
	 * @param tableStyles 表格样式
	 * @param isTree 是否为树，可选值为：true或false
	 */
	protected void printTable(JspWriter out,String divTag,String tableStyles,boolean isTree) {
		try {
			if(StringUtils.isEmpty(limitHeight)) {
	    		out.println("<div>");
	    	} else {
	    		try {
	    			if(YesNoType.YES.getIndex() == getIsOriginalTable()) {
	    				out.println("<div class='cnoj-table-wrap table-wrap-limit' data-subtract-height='"+this.subtractHeight+"'>");
	    			} else {
	    				int height = Integer.parseInt(limitHeight);
		    			if(height<1) {
		    				out.println("<div class='cnoj-table-wrap table-body-scroll table-wrap-limit' data-subtract-height='"+this.subtractHeight+"'>");
		    			} else {
		    				out.println("<div class='table-wrap-limit' data-subtract-height='"+this.subtractHeight+"' style='height:"+limitHeight+"px'>");
		    			}
	    			}
	    		} catch (Exception e) {
	    			out.println("<div>");
				}
	    	}
	    	String tableClassStyle = "";
	    	if(!isTree)
	    		tableClassStyle = "table-striped";
	    	else 
	    		tableClassStyle = "table-hover";
	    	if(null == tableStyles) {
	    		tableStyles = "table-bordered table-condensed";
	    	} else {
	    		tableClassStyle = "";
	    	}
	    	out.println("<table "+(StringUtils.isNotEmpty(this.tableId)?"id='"+this.tableId+"'":"")+" class='table "+tableClassStyle+" "+tableStyles+" "+StringUtils.handleNull(divTag)+" "+(isCheckbox==1?"cnoj-checkbox-wrap":"")+" '>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
   /**
    * 生成表格头
    * @param out JSP输出对象
    * @param divTag 页面标识符(div名称)
    * @param tableStyles　表格样式
    * @param isTree 是否为树，可选值为：true或false
    * @param headerTrStyle 表格标题行样式
    * @return 返回表格标题行中各列的宽
    */
    protected String[] printTableHeader(JspWriter out,String divTag,String tableStyles,boolean isTree,String headerTrStyle) {
    	String[] tWidth = null;
    	try {
    		printTable(out, divTag, tableStyles, isTree);
	    	if(StringUtils.isEmpty(headers)) {
	    		log.info("没有表格标头！");
	    	}
	    	String[] tdStyles = null;
	    	String[] headerArray = null;
	    	int cols = 0;
	    	if(StringUtils.isEmpty(headerTrStyle)) {
	    		headerTrStyle = "ui-state-default";
	    	}
	    	if(StringUtils.isNotEmpty(headers)) {
	    		String dataHeight = "";
	    		if(isAutoHeight == 0) {
	    			dataHeight = "data-height='36'";
	    		}
	    		out.println("<thead><tr class='"+headerTrStyle+"' "+dataHeight+">");
	    		if(isCheckbox==1) {
		    		out.println("<th class='th-checkbox' style=\"width:30px;\"><div class='checkbox'><label><input class='cnoj-checkbox-all cnoj-op-checkbox' data-target='.one-checkbox' value='' type='checkbox'></label></div></th>");
		    	}
	    		headerArray = headers.split(",");
		    	tWidth = new String[headerArray.length];
		    	cols = headerArray.length;
		    	tdStyles = handleStylesClass(cols);
		    	if(StringUtils.isNotEmpty(headerWidths)) {
		    		String[] headerWidthArray = headerWidths.split(",");
		    		if(headerWidthArray.length==headerArray.length) {
		    			for (int i = 0; i < headerArray.length; i++) {
							out.println("<th class='"+tdStyles[i]+"' style='width:"+headerWidthArray[i]+"'>"+headerArray[i]+"</th>");
							tWidth[i] = headerWidthArray[i];
						}
		    		}
		    	} else {
	    			int w = (int)Math.round(100/headerArray.length);
	    			int surplus = 100 - w*headerArray.length;
	    			for (int i = 0; i < headerArray.length; i++) {
	    				int tdW = w;
	    				if(surplus>0 && i == 0) {
	    					tdW += surplus;
	    				}
	    				out.println("<th class='"+tdStyles[i]+"' style='width:"+tdW+"%'>"+headerArray[i]+"</th>");
	    				tWidth[i] = tdW+"%";
	    			}
		    	}
		    	out.println("</tr></thead>");
	    	} 
	    	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	return tWidth;
    }
    
    /**
     * 生成表格内容
     * @param out JSP输出对象
     */
	protected void printTableBody(JspWriter out) {
    	String[] thWidth = printTableHeader(out,"cnoj-table",null,false,null);
    	String[] tdStyles = null;
    	int cols = headers.split(",").length;
    	int colCount = cols;
    	tdStyles = handleStylesClass(cols);
    	if(isId == 0) {
			colCount--;
		}
    	if(null != thWidth) {
    		try {
    			out.println("<tbody>");
	    		if(!OP_SUCCESS.equals(smartResp.getResult())) {
	    			if(isCheckbox==1) {
	    				cols++;
	    			}
	    			out.println("<tr><td colspan='"+cols+"' class='text-center'>"+smartResp.getMsg()+"</td></tr>");
	    		} else {
	    			List<Object> objs = smartResp.getDatas();
	    			int row = 0;
	    			for (Object obj : objs) {
						Object[] objArray = (Object[])obj;
						if(isRowSelected==1 && isCheckbox==1) {
							out.println("<tr id='t-"+StringUtils.handleNull(objArray[0])+"' class='tr-selected tr-mutil-selected'>");
						} else if(isRowSelected == 1 && isCheckbox==0){
							out.println("<tr id='t-"+StringUtils.handleNull(objArray[0])+"' class='tr-selected tr-one-selected'>");
						} else {
							out.println("<tr id='t-"+StringUtils.handleNull(objArray[0])+"'>");
						}
						if(isCheckbox==1) {
							out.println("<td class='td-checkbox' style=\"width:30px;\"><div class='checkbox'><label><input type='checkbox' class='one-checkbox cnoj-op-checkbox' value='"+StringUtils.handleNull(objArray[0])+"'></label></div></td>");
						}
						int count = 0;
						for (int i = 0; i < objArray.length;i++) {
							if(i > colCount) {
								break;
							}
							String tdStyle = getTdStyle(tdStyles, count);
							/**
							 * TODO 修复当表格标签属性配置为显示id时，会显示两次ID列的问题
							 */
							if(isId == 1 && isIdShow != 1 && i==0) {
								continue;
							}
							String tdContent = StringUtils.handleNull(objArray[i]);
							String a = getTdContent(objArray, row, tdContent, count, i);
							out.println("<td "+(StringUtils.isEmpty(tdStyle)?"":"class='"+tdStyle+"'")+" "+getTdWidthStyle(thWidth, count)+">"+a+"</td>");
							count++;
						} 
						out.println(handleLastCustomCell(objArray, row, count, colCount, tdStyles, thWidth));
						out.println("</tr>");
						row++;
					}
	    		}
	    		out.println("</tbody></table></div>");
    		} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
	/**
	 * 处理最后面自定义的单元格
	 * @param objArray
	 * @param count
	 * @param headerCount
	 * @param tdStyles
	 * @param tdWidths
	 * @return
	 */
	protected String handleLastCustomCell(Object[] objArray, int row, int count, int headerCount, String[] tdStyles, String[] tdWidths) {
		StringBuilder cellBuilder = null;
		//如果有自定义单元格，则进行处理 
		if(CollectionUtils.isNotEmpty(customCells)) {
			cellBuilder = new StringBuilder();
			for (CustomTableCell customCell : customCells) {
			    if(customCell.getPosition() <= headerCount && customCell.getPosition() >= count) {
					String tdContent = null;
					if(null != customCell.getCellCallback()) {
						tdContent = customCell.getCellCallback().callback(objArray, row, count, null);
					} else {
						tdContent = customCell.replaceContent(objArray);
					}
					cellBuilder.append("<td "+(StringUtils.isEmpty(getTdStyle(tdStyles, count))?"":"class='"+getTdStyle(tdStyles, count)+"'")+" "+getTdWidthStyle(tdWidths, count)+">"+tdContent+"</td>");
					count++;
				}
			}
		}
		return (null == cellBuilder)?"":cellBuilder.toString();
	}
	
	/**
	 * 处理样式CLASS
	 * @param cols
	 * @return
	 */
	protected String[] handleStylesClass(int cols) {
		if(StringUtils.isEmpty(stylesClass)) {
    		stylesClass = TD_CLASS;
    	}
		String[] tdStyles = null;
    	if(StringUtils.isNotEmpty(stylesClass)) {
    		tdStyles = stylesClass.split(",");
    		if(tdStyles.length==1) {
    			tdStyles = new String[cols];
    			for (int i = 0; i < tdStyles.length; i++) {
    				tdStyles[i] = stylesClass;
				}
    		}
    	}
    	return tdStyles;
	}
	
	/**
	 * 获取td内容
	 * @param objArray
	 * @param defaultValue
	 * @param count 
	 * @param i
	 * @return
	 */
	protected String getTdContent(Object[] objArray, int row, String defaultValue, int count, int i) {
		String a = null;
		//如果有自定义单元格，则替换对应单元格中的内容
		CustomTableCell cell = getCell(i);
		if(null != cell) {
			defaultValue = (null == cell.getCellCallback())?cell.replaceContent(objArray):cell.getCellCallback().callback(objArray, row, count, objArray[i]);
		}
		if(null != alinks && alinks.size()>0) {
			for (ALink alink : alinks) {
				int postion = Integer.parseInt(alink.getLinkPostion());
				if(i == postion) {
					if(null != alink.getCellCallback()) {
						a = alink.getCellCallback().callback(objArray, row, count, defaultValue);
					} 
					if(null == a) {
						a = getALinkContent(alink, objArray);
						a = "<a "+a+">"+defaultValue+"</a>";
					}
					break;
				}
			}
		}
		if(StringUtils.isEmpty(a)) {
			a = defaultValue;
		}
		return a;
	}
	
	/**
	 * 获取TD样式
	 * @param tdStyles
	 * @param index
	 * @return
	 */
    protected String getTdStyle(String[] tdStyles, int index) {
    	String tdStyle = null;
    	if(index > tdStyles.length-1) {
    		index = tdStyles.length-1;
    	}
    	tdStyle = tdStyles[index];
    	if(StringUtils.isEmpty(tdStyle)) {
    		tdStyle = TD_CLASS;
    	}
    	return tdStyle;
    }
    
    /**
	 * 获取TD宽度
	 * @param tdStyles
	 * @param index
	 * @return
	 */
    protected String getTdWidth(String[] tdWidths, int index) {
    	if(index > tdWidths.length-1) {
    		index = tdWidths.length-1;
    	}
    	return tdWidths[index];
    }
	
    /**
	 * 获取TD宽度样式
	 * @param tdWidths
	 * @param count
	 * @return
	 */
    protected String getTdWidthStyle(String[] tdWidths, int index) {
		String thWidthStyle = "";
		String thWidth = getTdWidth(tdWidths, index);
		if(StringUtils.isNotEmpty(thWidth)) {
			thWidthStyle = "style='width:"+thWidth+"'";
		}
		return thWidthStyle;
	}
    
    /**
     * 获取超链接中的uri（组合参数）
     * @param alink ａ链接对象
     * @param objArray 参数数组
     * @return 返回链接组合结果
     */
    protected String getALinkUriParam(ALink alink,Object[] objArray) {
    	StringBuilder paramBuilder = new StringBuilder();
    	String[] paramNameArray = alink.getParamName().split(",");
		String[] paramIndexArray = alink.getParamIndex().split(",");
		for (int j = 0; j < paramNameArray.length; j++) {
			paramBuilder.append(StringUtils.handleNull(paramNameArray[j])+"=");
			if(paramIndexArray[j].startsWith("'") && paramIndexArray[j].endsWith("'")) {
				paramBuilder.append(StringUtils.handleNull(paramIndexArray[j].substring(1,paramIndexArray[j].length()-1)));
			} else {
				paramBuilder.append(objArray[Integer.parseInt(paramIndexArray[j].trim())]);
			}
			paramBuilder.append("&");
		}
		if(paramBuilder.length()>0) {
			paramBuilder = paramBuilder.delete(paramBuilder.length()-1, paramBuilder.length());
		}
    	return paramBuilder.toString();
    }
    
    /**
     * 获取超链接(组合链接)
     * @param alink　ａ链接对象
     * @param objArray　参数数组
     * @return　返回链接组合结果
     */
    protected String getALinkContent(ALink alink,Object[] objArray) {
    	String a = "";
    	String uri = alink.getUri();
		if(uri.indexOf("?")>0) {
			uri = uri+"&"+getALinkUriParam(alink, objArray);
		} else {
			uri = uri+"?"+getALinkUriParam(alink, objArray);
		}
    	if(StringUtils.isEmpty(alink.getaTarget())) {
			a = " href='#' class='"+alink.getClassTarget()+"' title='"+alink.getDialogTitle()+"' data-uri='"+uri+"' data-width='"+alink.getDialogWidth()+"' data-title='"+alink.getDialogTitle()+"'";
		} else {
			a = " href='"+uri+"' title='"+alink.getDialogTitle()+"' target='"+alink.getaTarget()+"'";
		}
    	return a;
    }
    
    /**
     * 获取自定义单元格；如果没有，则返回null
     * @param index
     * @return
     */
    protected CustomTableCell getCell(int index) {
    	CustomTableCell cell = null;
    	if(CollectionUtils.isEmpty(getCustomCells())) {
    		return cell;
    	}
    	for (CustomTableCell cellTmp : getCustomCells()) {
			if(cellTmp.getPosition().intValue() == index) {
				cell = cellTmp;
				break;
			}
		}
    	return cell;
    }
    
    
    ////////setter and getter//////////
	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public int getIsId() {
		return isId;
	}

	public void setIsId(int isId) {
		this.isId = isId;
	}

	public int getIsIdShow() {
		return isIdShow;
	}

	public void setIsIdShow(int isIdShow) {
		this.isIdShow = isIdShow;
	}

	public int getIsCheckbox() {
		return isCheckbox;
	}

	public void setIsCheckbox(int isCheckbox) {
		this.isCheckbox = isCheckbox;
	}

	public String getHeaderWidths() {
		return headerWidths;
	}

	public void setHeaderWidths(String headerWidths) {
		this.headerWidths = headerWidths;
	}

	public String getLimitHeight() {
		return limitHeight;
	}

	public void setLimitHeight(String limitHeight) {
		this.limitHeight = limitHeight;
	}

	public int getIsRowSelected() {
		return isRowSelected;
	}

	public void setIsRowSelected(int isRowSelected) {
		this.isRowSelected = isRowSelected;
	}

	public String getStylesClass() {
		return stylesClass;
	}

	public void setStylesClass(String stylesClass) {
		this.stylesClass = stylesClass;
	}

	public List<ALink> getAlinks() {
		return alinks;
	}

	public void setAlinks(List<ALink> alinks) {
		this.alinks = alinks;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public Integer getSubtractHeight() {
		return subtractHeight;
	}

	public void setSubtractHeight(Integer subtractHeight) {
		this.subtractHeight = subtractHeight;
	}

	public List<CustomTableCell> getCustomCells() {
		return customCells;
	}

	public void setCustomCells(List<CustomTableCell> customCells) {
		this.customCells = customCells;
	}

	public Integer getIsOriginalTable() {
		return isOriginalTable;
	}

	public void setIsOriginalTable(Integer isOriginalTable) {
		this.isOriginalTable = isOriginalTable;
	}

}
