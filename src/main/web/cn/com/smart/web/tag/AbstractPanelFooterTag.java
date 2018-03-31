package cn.com.smart.web.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.constant.enums.BtnPropType;
import cn.com.smart.web.helper.PageHelper;
import cn.com.smart.web.helper.WebSecurityHelper;
import cn.com.smart.web.service.OPAuthService;
import cn.com.smart.web.tag.bean.BaseBtn;
import cn.com.smart.web.tag.bean.CustomBtn;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * 面板底部分页或按钮栏
 * @author lmq <br />
 * 2016年9月13日
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractPanelFooterTag extends BaseTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3901385946830480568L;

	protected int isAutoHeight = 0;
	
	//是否分页
    protected PageParam page;
	
	//是否有添加按钮
    protected EditBtn addBtn;
	
	//编辑按钮
    protected EditBtn editBtn;
	
	//删除按钮
    protected DelBtn delBtn;
	
	//刷新按钮
    protected RefreshBtn refreshBtn;
    
    protected List<CustomBtn> customBtns;
    
    protected SmartResponse<Object> smartResp;
    
    protected String searchPanelTag;

	/**
     * 生成表尾（如：按钮，分页等信息）
     * @param out JSP输出对象
     */
    protected void printFooter(JspWriter out) {
    	try {
    		UserInfo userInfo = getUserInfo();
    		OPAuthService authServ = (OPAuthService)getService("opAuthServ");
    		StringBuilder htmlContent = new StringBuilder();
    		String dataHeight = "";
    		if(isAutoHeight == 0) {
    			dataHeight = "data-height='34'";
    		}
    		htmlContent.append("<div class='panel-footer panel-footer-page' "+dataHeight+">");
    		int count = 0;
    		String btnStyleFlag = null, pageStyleFlag = null, infoStyleFlag = null;
    		if(null != addBtn || null != editBtn || null != delBtn || null != refreshBtn 
    				|| (null != customBtns && customBtns.size()>0)) {
    			count++;
    			List<String> btnList = new ArrayList<String>();
    			btnStyleFlag = "${btnListStyle}";
    			htmlContent.append("<div class='btn-list' "+StringUtils.handleNull(btnStyleFlag)+"><div class='btn-group cnoj-op-btn-list'>");
    			String btnHtml = null;
    			String authUrl = null;
    			if(null != addBtn && (!addBtn.getIsAuth() || authServ.isAuth(currentUri, addBtn, userInfo.getRoleIds()))) {
    			    authUrl = WebSecurityHelper.addUriAuth(currentUri, addBtn.getId(), addBtn.getUri());
    				if(StringUtils.isEmpty(addBtn.getSelectedType()))
    					addBtn.setSelectedType(BtnPropType.SelectType.NONE.getValue());
    				btnHtml = "<button type='button' id='"+addBtn.getId()+"' class='btn "+(StringUtils.isEmpty(addBtn.getBtnStyle())?BTN_DEFAULT_THEME:addBtn.getBtnStyle())+" add param' data-selected-type='"+
    		    			StringUtils.handleNull(addBtn.getSelectedType())+"' data-uri='"+authUrl+"' data-title='"+StringUtils.handleNull(addBtn.getTitle())+"' data-busi='"+
    		    			StringUtils.handleNull(addBtn.getBusi())+"' data-value='' data-width='"+StringUtils.handleNull(addBtn.getWidth())+"' data-before-check='"+StringUtils.handleNull(addBtn.getBeforeCheck())+"'><i class='glyphicon glyphicon-plus'></i> "
    		    			+(StringUtils.isEmpty(addBtn.getName())?"添加":addBtn.getName())+"</button>";
    				addBtnHtmlToList(btnList, addBtn, btnHtml);
    			}
    			if(null != editBtn && (!editBtn.getIsAuth() || authServ.isAuth(currentUri, editBtn, userInfo.getRoleIds()))) {
    			    authUrl = WebSecurityHelper.addUriAuth(currentUri, editBtn.getId(), editBtn.getUri());
    			    if(StringUtils.isEmpty(editBtn.getSelectedType()))
    					editBtn.setSelectedType(BtnPropType.SelectType.MULTI.getValue());
    				btnHtml = "<button type='button' id='"+editBtn.getId()+"' class='btn "+(StringUtils.isEmpty(editBtn.getBtnStyle())?BTN_DEFAULT_THEME:editBtn.getBtnStyle())+" edit param' data-selected-type='"+StringUtils.handleNull(editBtn.getSelectedType())+"' data-uri='"+
    						authUrl+"' data-title='"+StringUtils.handleNull(editBtn.getTitle())+"' data-busi='"+StringUtils.handleNull(editBtn.getBusi())+"' data-value='' data-width='"+StringUtils.handleNull(editBtn.getWidth())+"' data-before-check='"+
    						StringUtils.handleNull(editBtn.getBeforeCheck())+"'><i class='glyphicon glyphicon-pencil'></i> "+(StringUtils.isEmpty(editBtn.getName())?"编辑":editBtn.getName())+"</button>";
    				addBtnHtmlToList(btnList, editBtn, btnHtml);
    			}
    			if(null != delBtn && (!delBtn.getIsAuth() || authServ.isAuth(currentUri, delBtn, userInfo.getRoleIds()))) {
    			    authUrl = WebSecurityHelper.addUriAuth(currentUri, delBtn.getId(), delBtn.getUri());
    			    if(StringUtils.isEmpty(delBtn.getSelectedType()))
    					delBtn.setSelectedType(BtnPropType.SelectType.MULTI.getValue());
    				btnHtml = "<button type='button' id='"+delBtn.getId()+"' class='btn "+(StringUtils.isEmpty(delBtn.getBtnStyle())?BTN_DEFAULT_THEME:delBtn.getBtnStyle())+" del param' data-selected-type='"+StringUtils.handleNull(delBtn.getSelectedType())+"' data-uri='"+
    				        authUrl+"' data-busi='"+StringUtils.handleNull(delBtn.getBusi())+"' data-msg='"+StringUtils.handleNull(delBtn.getMsg())+"' data-value='' data-refresh-uri='"+StringUtils.handleNull(delBtn.getRefreshUri())+"' data-target='"+
    						StringUtils.handleNull(delBtn.getTarget())+"' data-delAfter='"+StringUtils.handleNull(delBtn.getCallback())+"' ><i class='glyphicon glyphicon-trash'></i> "+(StringUtils.isEmpty(delBtn.getName())?"删除":delBtn.getName())+"</button>";
    				addBtnHtmlToList(btnList, delBtn, btnHtml);
    			}
    			if(null != refreshBtn && (!refreshBtn.getIsAuth() || authServ.isAuth(currentUri, refreshBtn, userInfo.getRoleIds()))) {
    				btnHtml = "<button type='button' id='"+refreshBtn.getId()+"' class='btn "+(StringUtils.isEmpty(refreshBtn.getBtnStyle())?BTN_DEFAULT_THEME:refreshBtn.getBtnStyle())+" refresh' data-uri='"+StringUtils.handleNull(refreshBtn.getUri())+"' data-busi='"+
    			              StringUtils.handleNull(refreshBtn.getBusi())+"' data-target='"+StringUtils.handleNull(refreshBtn.getTarget())+"' ><i class='glyphicon glyphicon-refresh'></i> "+(StringUtils.isEmpty(refreshBtn.getName())?"刷新":refreshBtn.getName())+"</button>";
    				addBtnHtmlToList(btnList, refreshBtn, btnHtml);
    			}
    			if(null != customBtns && customBtns.size()>0) {
    				for (CustomBtn customBtn : customBtns) {
    					btnHtml = "";
    					if(!customBtn.getIsAuth() || authServ.isAuth(currentUri, customBtn, userInfo.getRoleIds())) {
    						String icon = "";
    						authUrl = WebSecurityHelper.addUriAuth(currentUri, customBtn.getId(), customBtn.getUri());
	    					if(StringUtils.isNotEmpty(customBtn.getBtnIcon())) {
	    						if(customBtn.getBtnIcon().split(" ").length > 1) {
	    							icon = "<i class='"+StringUtils.handleNull(customBtn.getBtnIcon())+"'></i>";
	    						} else {
	    							icon = "<i class='glyphicon "+StringUtils.handleNull(customBtn.getBtnIcon())+"'></i>";
	    						}
	    					}
	    					btnHtml = "<button type='button' id='"+customBtn.getId()+"' class='btn "+StringUtils.handleNull(customBtn.getBtnStyle())+" "+customBtn.getOpenStyle().getValue()+" param' data-selected-type='"+StringUtils.handleNull(customBtn.getSelectedType())+"' "
	    					        + "data-uri='"+authUrl+"' data-title='"+StringUtils.handleNull(customBtn.getTitle())+"' data-value='' data-param-name='"+StringUtils.handleNull(customBtn.getParamName())+"' data-width='"+StringUtils.handleNull(customBtn.getWidth())+
	    					           "' data-before-check='"+StringUtils.handleNull(customBtn.getBeforeCheck())+"'>"+icon+" "+StringUtils.handleNull(customBtn.getName())+"</button>";
	    					addBtnHtmlToList(btnList, customBtn, btnHtml);
    					}
    				}// for
    			} //if
    			customBtns = null;
    			if(CollectionUtils.isNotEmpty(btnList)) {
    				for (String html : btnList) {
						htmlContent.append(html);
					}
    				btnList.clear();
    				btnList = null;
    			}
    			htmlContent.append("</div></div>");
    		}
    		userInfo = null;
   			authServ = null;
   			String searchPanel = "";
    		if(null != page && smartResp.getTotalPage()>1) {
    			String pageUri = page.getUri();
        		if(StringUtils.isNotEmpty(pageUri)) {
        			pageUri = pageUri.replaceAll("page=\\d+&|page=\\d+|\\?page=\\d+$", "");
            		pageUri += (StringUtils.isContains(pageUri, "?")?"&":"?")+"page=";
        		} else {
        			pageUri = "";
        		}
        		count++;
        		if(StringUtils.isNotEmpty(searchPanelTag)) {
        			searchPanel = "data-search-panel-tag='"+searchPanelTag+"'";
        		}
    			pageStyleFlag = "${btnPageStyle}";
    			htmlContent.append("<div class='btn-page' "+StringUtils.handleNull(pageStyleFlag)+">");
    			htmlContent.append("<div class='page'><ul class='pagination'><li class='"+(page.getPage()==1?"disabled":"")+"'>");
    			if(page.getPage() == 1) {
    				htmlContent.append("<a href='javascript:void(0)' "+searchPanel+" class='pre-page'>&laquo;</a>");
    			} else {
    				htmlContent.append("<a data-uri='"+pageUri+(page.getPage()-1)+"' "+searchPanel+" href='#' class='cnoj-change-page pre-page' data-target='"+StringUtils.handleNull(page.getTarget())+"'>&laquo;</a>");
    			}
    			htmlContent.append("</li>");
    			if(smartResp.getTotalPage()>1) {
    				int showPageNum = 3;
    				int first = 1;
    				if(page.getPage()>=showPageNum) {
    					first = page.getPage()-1;
    				}
    				int last = 1;
    				if(smartResp.getTotalPage()>0) {
    					if(smartResp.getTotalPage()>showPageNum) {
    						if(page.getPage()>=showPageNum) {
    							last = page.getPage()+1;
    							last = last>smartResp.getTotalPage()?smartResp.getTotalPage():last;
    						} else {
    						   last = showPageNum;	
    						}
    					} else {
    						last = smartResp.getTotalPage();
    					}
    				}
    				if(smartResp.getTotalPage()>showPageNum)
    					first = (last-first)<(showPageNum-1)?(last-showPageNum+1):first;
    				
    				for (int i = first; i <= last; i++) {
						htmlContent.append("<li class='"+(page.getPage()==i?"active":"")+"'><a class='cnoj-change-page' "+searchPanel+" data-uri='"+pageUri+i+"' href='#' data-target='"+StringUtils.handleNull(page.getTarget())+"'>"+i+"</a></li>");
					}
    			}
    			htmlContent.append("<li class='"+(page.getPage()>=smartResp.getTotalPage()?"disabled":"")+"'>");
    			if(page.getPage()>=smartResp.getTotalPage()) {
    				htmlContent.append("<a href='javascript:void(0)' "+searchPanel+" class='next-page'>&raquo;</a>");
    			} else {
    				htmlContent.append("<a href='#' data-uri='"+pageUri+(page.getPage()+1)+"' "+searchPanel+" class='cnoj-change-page next-page' data-target='"+StringUtils.handleNull(page.getTarget())+"'>&raquo;</a>");
    			}
    			htmlContent.append("</li>");
    			htmlContent.append("<li>&nbsp;到<input class='form-control btn-sm goto-page-input' name='page' value='' />页<button data-uri='"+pageUri+"' class='btn btn-default btn-sm cnoj-goto-page' "+searchPanel+" data-target='"+StringUtils.handleNull(page.getTarget())+"'>确定</button></li>");
    			htmlContent.append("</ul>");
    			htmlContent.append("</div>");
    			htmlContent.append("</div>");
    		}
    		count++;
			infoStyleFlag = "${infoStyle}";
    		htmlContent.append("<div class='page-info' "+ StringUtils.handleNull(infoStyleFlag) +">");
    		if(null != page) {
    			htmlContent.append("<span>"+(smartResp.getTotalPage()>0?page.getPage():"0")+" - "+smartResp.getTotalPage()+"</span>");
    		}
    		StringBuilder showPageBuilder = null;
    		int defaultPageSize = 0;
    		//每页显示数量选择
    		if(null != page && page.getIsSelectSize()) {
				defaultPageSize = page.getPageSize();
				if(defaultPageSize == 0) {
					defaultPageSize = PageHelper.defaultPageSize();
				}
				Integer[] showPageSizeArray = PageHelper.getShowPageSize();
				showPageBuilder = new StringBuilder();
				if(null != showPageSizeArray && showPageSizeArray.length>0) {
					showPageBuilder.append("<select class='form-control input-sm cnoj-change-pagesize' "+searchPanel+" data-uri='"+page.getUri()+"' data-target='"+StringUtils.handleNull(page.getTarget())+"'>");
					for (int i = 0; i < showPageSizeArray.length; i++) {
						if(defaultPageSize == showPageSizeArray[i]) {
							showPageBuilder.append("<option selected='selected' value='"+showPageSizeArray[i]+"'>"+showPageSizeArray[i]+"</option>");
						} else {
							showPageBuilder.append("<option value='"+showPageSizeArray[i]+"'>"+showPageSizeArray[i]+"</option>");
						}
					}
					showPageBuilder.append("</select>");
				}
    		}
    		htmlContent.append("<span>&nbsp;&nbsp; 共"+smartResp.getTotalNum()+"条(每页显示"+(smartResp.getTotalPage()==0?(smartResp.getTotalNum()==0?defaultPageSize:smartResp.getTotalNum()):""+(null == showPageBuilder ? smartResp.getPerPageSize():showPageBuilder.toString()))+"</span>条)</span></div>");
    		htmlContent.append("</div>");
    		String resultContent = htmlContent.toString();
    		String styleStart = "style=\"";
    		String styleEnd = "\"";
    		if(count == 3) {
    			resultContent = resultContent.replaceAll("(\\$\\{.*?\\})", "");
    		} else if(count == 2){
    			if(StringUtils.isNotEmpty(btnStyleFlag)) {
    				resultContent = resultContent.replace(btnStyleFlag, "");
    			} else if(StringUtils.isNotEmpty(pageStyleFlag)) {
    				resultContent = resultContent.replace(pageStyleFlag, styleStart+"width: 45%;"+styleEnd);
    			}
    			resultContent = resultContent.replace(infoStyleFlag, styleStart+"margin-left:50%;"+styleEnd);
    		} else {
    			resultContent = resultContent.replace(infoStyleFlag, styleStart+"margin-left:5%;"+styleEnd);
    		}
    		out.println(resultContent);
    		htmlContent = null;
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * 按钮对应的HTML代码添加到LIST中
     * @param list
     * @param btn
     * @param btnHtml
     */
    private void addBtnHtmlToList(List<String> list, BaseBtn btn, String btnHtml) {
    	if(null != btn.getSort()) {
    		list.add(btn.getSort(), btnHtml);
		} else 
			list.add(btnHtml);
    }
    
    /***************getter and setter*****************/
    
	public PageParam getPage() {
		return page;
	}

	public void setPage(PageParam page) {
		this.page = page;
	}

	public EditBtn getAddBtn() {
		return addBtn;
	}

	public void setAddBtn(EditBtn addBtn) {
		this.addBtn = addBtn;
	}

	public EditBtn getEditBtn() {
		return editBtn;
	}

	public void setEditBtn(EditBtn editBtn) {
		this.editBtn = editBtn;
	}

	public DelBtn getDelBtn() {
		return delBtn;
	}

	public void setDelBtn(DelBtn delBtn) {
		this.delBtn = delBtn;
	}

	public RefreshBtn getRefreshBtn() {
		return refreshBtn;
	}

	public void setRefreshBtn(RefreshBtn refreshBtn) {
		this.refreshBtn = refreshBtn;
	}

	public List<CustomBtn> getCustomBtns() {
		return customBtns;
	}

	public void setCustomBtns(List<CustomBtn> customBtns) {
		this.customBtns = customBtns;
	}

	public SmartResponse<Object> getSmartResp() {
		return smartResp;
	}

	public void setSmartResp(SmartResponse<Object> smartResp) {
		this.smartResp = smartResp;
	}

	public String getSearchPanelTag() {
		return searchPanelTag;
	}

	public void setSearchPanelTag(String searchPanelTag) {
		this.searchPanelTag = searchPanelTag;
	}

	public int getIsAutoHeight() {
		return isAutoHeight;
	}

	public void setIsAutoHeight(int isAutoHeight) {
		this.isAutoHeight = isAutoHeight;
	}
}
