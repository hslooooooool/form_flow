package cn.com.smart.web.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.mixsmart.utils.StringUtils;

/**
 * 表格树
 * @author lmq
 *
 */
public abstract class AbstractTableTreeTag extends TableTag {

	private static final long serialVersionUID = -8947261853934909356L;
	
	/**
	 * 是否展开
	 * 1---展开
	 * 0---不展开
	 */
	protected int isExpand = 0;
	
	protected String[] thWidth;
	protected String[] tdStyles;
	

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			this.page = null;
			thWidth = printTableHeader(out, getTableDivTag(), null, true, null);
			if(null != thWidth) {
				this.printTableBody(out);
				printFooter(out);
			}
			int cols = headers.split(",").length;
			tdStyles = super.handleStylesClass(cols);
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
		try {
			out.println("<tbody>");
			int cols = headers.split(",").length;
			int headerCount = cols;
			tdStyles = handleStylesClass(cols);
			if(isId == 0) {
                headerCount --;
            }
    		if(!OP_SUCCESS.equals(smartResp.getResult())) {
    			out.println("<tr><td colspan='"+cols+"' class='text-center'>"+smartResp.getMsg()+"</td></tr>");
    		} else {
    			List<Object> objs = smartResp.getDatas();
    			int row = 0;
    			for (Object obj : objs) {
					Object[] objArray = (Object[]) obj;
					
					String cols1Value = null;
					int startIndex = 0;
					if(isId==1 && isIdShow==1) {
						cols1Value = StringUtils.handleNull(objArray[3]);
						startIndex = 4;
					} else if (isId==1 && isIdShow==0) {
						cols1Value = StringUtils.handleNull(objArray[4]);
						startIndex = 5;
					} else {
						cols1Value = StringUtils.handleNull(objArray[3]);
						startIndex = 4;
					}
					out.println(getHtml(isParent(StringUtils.handleNull(objArray[3]), objs), objArray, row,
							countLayer(StringUtils.handleNull(objArray[1]), objs), cols1Value, startIndex, cols, headerCount));
					row++;
				}//for
    		}//else
    		out.println("</tbody></table></div>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 判断是否为父层
	 * @param id　ＩＤ值
	 * @param objs　数组列表
	 * @return　如果为父层，返回：true；否则返回：false
	 */
	protected boolean isParent(String id,List<Object> objs) {
		boolean is = false;
		for (Object obj : objs) {
			Object[] objArray = (Object[]) obj;
			if(id.equals(StringUtils.handleNull(objArray[1]))){
				is = true;
				break;
			}
		}
		return is;
	}
	
	
	/**
	 * 统计层级
	 * @param parentId  父ＩＤ
	 * @param objs 　数组列表
	 * @return 返回层级
	 */
	protected int countLayer(String parentId,List<Object> objs) {
		int layer = 0;
		for (int i = 0; i < objs.size();) {
			Object[] objArray = (Object[]) objs.get(i);
			if(parentId.equals(StringUtils.handleNull(objArray[0]))) {
				layer++;
				parentId = StringUtils.handleNull(objArray[1]);
				i = 0;
			} else {
				i++;
			}
		}
		return layer;
	}

	/**
	 * 获取td样式
	 * @param count
	 * @return
	 */
	protected String getTdClass(int count) {
		String tdStyle = "";
		if(null == tdStyles || tdStyles.length == 0) {
			return tdStyle;
		}
		if(count>=tdStyles.length) {
			tdStyle = tdStyles[tdStyles.length-1];
		} else {
			tdStyle = tdStyles[count];
		}
		return tdStyle;
	}
	
	
	/**
	 * 生成HTML代码 
	 * @param isParent
	 * @param objArray 数组
	 * @param row
	 * @param layer 层级
	 * @param defaultValue
	 * @param startIndex
	 * @param cols
	 * @param headerCount
	 * @return 返回生成的HTML代码
	 */
	protected abstract String getHtml(Boolean isParent,Object[] objArray,int row,int layer, String defaultValue, int startIndex, int cols, int headerCount);
	
	/**
	 * 获取表格DIV标记
	 * @return
	 */
	protected abstract String getTableDivTag();
	
	
	public int getIsExpand() {
		return isExpand;
	}

	public void setIsExpand(int isExpand) {
		this.isExpand = isExpand;
	}

	public String[] getThWidth() {
		return thWidth;
	}

	public void setThWidth(String[] thWidth) {
		this.thWidth = thWidth;
	}

	public String[] getTdStyles() {
		return tdStyles;
	}

	public void setTdStyles(String[] tdStyles) {
		this.tdStyles = tdStyles;
	}
}
