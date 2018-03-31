package cn.com.smart.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.service.OPAuthService;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 刷新按钮标签
 * @author lmq
 *
 */
public class RefreshBtnTag extends BtnTag {
	
	private static final long serialVersionUID = 3300996504651197696L;
	
	private RefreshBtn refreshBtn;
	private String target;

	@Override
   	public int doEndTag() throws JspException {
   		return EVAL_PAGE;
   	}

   	@Override
   	public int doStartTag() throws JspException {
   		try {
   			id="refresh";
   			name="刷新";
   			JspWriter out = this.pageContext.getOut();
   			if(null == refreshBtn)
   				refreshBtn = new RefreshBtn(uri, busi, target, btnStyle, name);
   			else {
   				if(StringUtils.isEmpty(refreshBtn.getBtnStyle()))
   					refreshBtn.setBtnStyle(btnStyle);
   				if(StringUtils.isEmpty(refreshBtn.getName()))
   					refreshBtn.setName(name);
   			}
   			UserInfo userInfo = getUserInfo();
   			OPAuthService authServ = (OPAuthService)getService("opAuthServ");
   			if(authServ.isAuth(currentUri, refreshBtn, userInfo.getRoleIds())) {
   				out.println("<button type='button'  class='btn "+refreshBtn.getBtnStyle()+" refresh' data-uri='"+StringUtils.handleNull(refreshBtn.getUri())+"' data-busi='"+StringUtils.handleNull(refreshBtn.getBusi())+"' data-target='"+StringUtils.handleNull(refreshBtn.getTarget())+"' ><i class='glyphicon glyphicon-refresh'></i> "+refreshBtn.getName()+"</button>");
   			}
   			userInfo = null;
   			authServ = null;
   		} catch (Exception e) {
   			throw new JspException(e.getMessage());
   		}
   		return SKIP_BODY;
   	}

   	@Override
   	public void release() {
   		super.release();
   		refreshBtn = null;
   	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getBusi() {
		return busi;
	}

	public void setBusi(String busi) {
		this.busi = busi;
	}
	 
    public String getBtnStyle() {
		return btnStyle;
	}

	public void setBtnStyle(String btnStyle) {
		this.btnStyle = btnStyle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RefreshBtn getRefreshBtn() {
		return refreshBtn;
	}

	public void setRefreshBtn(RefreshBtn refreshBtn) {
		this.refreshBtn = refreshBtn;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
}
