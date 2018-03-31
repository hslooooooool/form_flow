package cn.com.smart.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * 面板底部标签
 * @author lmq <br />
 * 2016年9月13日
 * @version 1.0
 * @since 1.0
 */
public class PanelFooterTag extends AbstractPanelFooterTag {

	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029533759912999916L;

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			super.printFooter(out);
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public void release() {
		super.release();
	}
	
}
