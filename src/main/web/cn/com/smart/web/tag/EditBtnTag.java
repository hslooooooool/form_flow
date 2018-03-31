package cn.com.smart.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.constant.enums.BtnPropType;
import cn.com.smart.web.service.OPAuthService;
import cn.com.smart.web.tag.bean.EditBtn;

import com.mixsmart.utils.StringUtils;

/**
 * 编辑按钮标签
 * @author lmq
 *
 */
public class EditBtnTag extends BtnTag {
	
	private static final long serialVersionUID = 3300996504651197696L;
	
	private EditBtn editBtn;

	protected String selectedType = BtnPropType.SelectType.ONE.getValue();
	
	private String title;
    private String width="600";
    
    private String beforeCheck;

	@Override
   	public int doEndTag() throws JspException {
   		return EVAL_PAGE;
   	}

   	@Override
   	public int doStartTag() throws JspException {
   		try {
   			id = "edit";
   			name = StringUtils.isEmpty(name)?"编辑":name;
   			if(StringUtils.isNotEmpty(selectedType) && 
   					BtnPropType.SelectType.NONE.getValue().equals(selectedType))
   				selectedType = BtnPropType.SelectType.MULTI.getValue();
   			JspWriter out = this.pageContext.getOut();
   			if(null == editBtn) {
   				editBtn = new EditBtn(id, uri, busi, title, width,btnStyle,name);
   				editBtn.setBeforeCheck(beforeCheck);
   			} else {
   				if(StringUtils.isEmpty(editBtn.getBtnStyle()))
   					editBtn.setBtnStyle(btnStyle);
   				if(StringUtils.isEmpty(editBtn.getName()))
   					editBtn.setName(name);
   				if(StringUtils.isEmpty(editBtn.getWidth()))
   					editBtn.setWidth("600");
   				if(StringUtils.isEmpty(editBtn.getSelectedType())) {
   					editBtn.setSelectedType(selectedType);
   				}
   			}
   			UserInfo userInfo = getUserInfo();
   			OPAuthService authServ = (OPAuthService)getService("opAuthServ");
   			if(!editBtn.getIsAuth() || authServ.isAuth(currentUri, editBtn, userInfo.getRoleIds())) {
   				out.println("<button type='button' class='btn "+editBtn.getBtnStyle()+" add param' "+
   						"data-selected-type='"+StringUtils.handleNull(editBtn.getSelectedType())+"' data-uri='"+StringUtils.handleNull(editBtn.getUri())+"' "+
   						"data-title='"+StringUtils.handleNull(editBtn.getTitle())+"' data-busi='"+StringUtils.handleNull(editBtn.getBusi())+"' "+
   						"data-value='' dialog-width='"+editBtn.getWidth()+"' ><i class='glyphicon glyphicon-pencil'></i> "+editBtn.getName()+"</button>");
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
   		editBtn = null;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public EditBtn getEditBtn() {
		return editBtn;
	}

	public void setEditBtn(EditBtn editBtn) {
		this.editBtn = editBtn;
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

	public String getBeforeCheck() {
		return beforeCheck;
	}

	public void setBeforeCheck(String beforeCheck) {
		this.beforeCheck = beforeCheck;
	}
}
