package cn.com.smart.web.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.tag.bean.ALink;
import cn.com.smart.web.tag.bean.SelectedEventProp;

import com.mixsmart.utils.StringUtils;


/**
 * 表格树
 * @author lmq
 *
 */
public class TableItemTag extends TableTag {

	private static final long serialVersionUID = -8947261853934909356L;
	
	private String[] thWidth;
	private String[] tdStyles;
	
	private SelectedEventProp selectedEventProp;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			thWidth = printTableHeader(out,"cnoj-table cnoj-ui-tr-selected"," table-hover",false,"cnoj-ui-thead");
			isRowSelected = 1;
			if(null == thWidth) {
				if(!OP_SUCCESS.equals(smartResp.getResult())) {
	    			out.println("<tr><td class='text-center'>"+smartResp.getMsg()+"</td></tr>");
	    		} else {
	    			List<Object> objs = smartResp.getDatas();
	    			if(null != objs && objs.size()>0) {
	    				Object[] objArray = (Object[])objs.get(0);
	    				int w = (int)Math.round(100/objArray.length);
		    			for (int i = 0; i < objArray.length; i++) {
		    					objArray[i] = w+"%";
		    			}
	    			}
	    		}
			}
			if(StringUtils.isNotEmpty(stylesClass)) {
				tdStyles = stylesClass.split(",");
			}
			this.printTableBody(out);
			printFooter(out);
			
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	@Override
	public void release() {
		super.release();
	}


	@Override
	protected void printTableBody(JspWriter out) {
    	int cols = thWidth.length;
    	if(StringUtils.isNotEmpty(stylesClass)) {
    		tdStyles = stylesClass.split(",");
    		if(tdStyles.length==1) {
    			tdStyles = new String[cols];
    			for (int i = 0; i < tdStyles.length; i++) {
    				tdStyles[i] = stylesClass;
				}
    		}
    	}
    	if(null != thWidth) {
    		try {
    			out.println("<tbody>");
	    		if(!OP_SUCCESS.equals(smartResp.getResult())) {
	    			out.println("<tr><td colspan='"+cols+"' class='text-center'>"+smartResp.getMsg()+"</td></tr>");
	    		} else {
	    			List<Object> objs = smartResp.getDatas();
	    			String selectedEventPropTag = null;
	    			if(null != selectedEventProp) {
	    				selectedEventPropTag = "data-selected-type='"+StringUtils.handleNull(selectedEventProp.getEventType())+"' "+
	    			                       "data-selected-uri='"+StringUtils.handleNull(selectedEventProp.getUri())+"' "+
	    						           "data-selected-target='"+StringUtils.handleNull(selectedEventProp.getTarget())+"' "+
	    			                       "data-selected-varname='"+StringUtils.handleNull(selectedEventProp.getVarParamName())+"'";
	    			}
	    			int row = 0;
	    			for (Object obj : objs) {
						Object[] objArray = (Object[])obj;
						if(isRowSelected == 1){
							out.println("<tr id='t-"+StringUtils.handleNull(objArray[0])+"' class='tr-selected tr-one-selected' "+StringUtils.handleNull(selectedEventPropTag)+" >");
						} else {
							out.println("<tr id='t-"+StringUtils.handleNull(objArray[0])+"'>");
						}
						int count = 0;
						for (int i = 0; i < objArray.length;i++) {
							String tdStyle = "";
							if(null != tdStyles) {
								tdStyle = tdStyles[count];
							}
							if(i==0)
							   continue;
							String widthStyle = "";
							if(StringUtils.isNotEmpty(thWidth[count])) {
								widthStyle = "style='width:"+thWidth[count]+"'";
							}
							String a = null;
							if(null != alinks && alinks.size()>0) {
								for (ALink alink : alinks) {
									int postion = Integer.parseInt(alink.getLinkPostion());
									if(count == postion) {
										if(null != alink.getCellCallback()) {
											a = alink.getCellCallback().callback(objArray, row, count, objArray[i]);
										} 
										if(null == a) {
											a = getALinkContent(alink, objArray);
											a = "<a "+a+">"+StringUtils.handleNull(objArray[i])+"</a>";
										}
										break;
									}
								}
							}
							if(StringUtils.isEmpty(a)) {
								a = StringUtils.handleNull(objArray[i]);
							}
							out.println("<td "+(StringUtils.isEmpty(tdStyle)?"":"class='"+tdStyle+"'")+" "+widthStyle+" title='"+StringUtils.handleNull(objArray[i])+"'>"+a+"</td>");
							count++;
						}
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

	public SelectedEventProp getSelectedEventProp() {
		return selectedEventProp;
	}

	public void setSelectedEventProp(SelectedEventProp selectedEventProp) {
		this.selectedEventProp = selectedEventProp;
	}

	public SmartResponse<Object> getsmartResp() {
		return smartResp;
	}

	public void setsmartResp(SmartResponse<Object> smartResp) {
		this.smartResp = smartResp;
	}
}
