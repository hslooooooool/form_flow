package cn.com.smart.web.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.helper.TreeHelper;
import cn.com.smart.web.bean.entity.TNMenu;

/**
 * 自定义菜单标签
 * @author lmq
 *
 */
public class HorizontalMenuTag extends BaseTag {

	private static final long serialVersionUID = -1833264518436649486L;
	
	private List<TNMenu> menus;
	private SmartResponse<TNMenu> smartResp;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			log.info("正在创建菜单");
			if(!OP_SUCCESS.equals(smartResp.getResult())) {
				out.println(smartResp.getMsg());
				return SKIP_BODY;
			}
			out.println("<div class='menu-horizontal-nav'><ul>");
			menus = smartResp.getDatas();
			List<TNMenu> rootMenus = getRootMenus();
			int count = 0;
			String activeClass = "";
			for (TNMenu menu : rootMenus) {
				if(count == 0) {
					activeClass = "class='active'";
				} else {
					activeClass = "";
				}
				out.println("<li "+activeClass+" id='p-"+menu.getId()+"'><a data-uri='"+StringUtils.handleNull(menu.getResource().getUri())+"' href=\"#\"><i class='glyphicon "+StringUtils.handleNull(menu.getIcon())+"'></i> "+StringUtils.handleNull(menu.getName())+"</a></li>");
				count++;
			}
			out.println("</ul></div><div class=\"clear\"></div>");
			if(null != menus && menus.size()>0) {
				out.println(" <div class='sub-menu'><div class='sub-menu-border'>");
				for (TNMenu menu : rootMenus) {
					List<TNMenu> subMenus = getSubMenus(menu);
					if(null != subMenus && subMenus.size()>0) {
						out.println("<ul class='ul-sub-menu p-"+menu.getId()+"'>");
						Stack<String> idStack = new Stack<String>();
						for (TNMenu menuTmp : subMenus) {
							if(isParent(menuTmp, subMenus)) {
								//isp = true;
								printEndTag(idStack, menuTmp.getParentId(), out);
								out.println("<li class='li-sub-menu "+(countLayer(menuTmp.getParentId())>1?"li-sub-menu-tree":"")+" layer-"+countLayer(menuTmp.getParentId())+" p-"+menuTmp.getParentId()+"' id='p-"+menuTmp.getId()+"'><a class='op-sub sub-open' data-uri='"+StringUtils.handleNull(menuTmp.getResource().getUri())+"' href='#'><div class='ui-icon ui-icon-triangle-1-e left'></div><i class='glyphicon "+StringUtils.handleNull(menuTmp.getIcon())+"'></i> "+StringUtils.handleNull(menuTmp.getName())+"</a>");
								if(countLayer(menuTmp.getParentId())==1) {
									out.println("<ul class='sub-menu-pop-down p-"+menuTmp.getId()+"'>");
								} else if(countLayer(menuTmp.getParentId())>1) {
									out.println("<ul class='sub-menu-pop-tree p-"+menuTmp.getId()+"''>");
								} else {
									out.println("<ul>");
								}
								idStack.push(menuTmp.getId());
							} else {
								printEndTag(idStack, menuTmp.getParentId(), out);
								out.println("<li class='li-sub-menu op-no-sub layer-"+countLayer(menuTmp.getParentId())+" p-"+menuTmp.getParentId()+"' id='p-"+menuTmp.getId()+"'><a data-uri='"+StringUtils.handleNull(menuTmp.getResource().getUri())+"' href='#'><i class='glyphicon "+StringUtils.handleNull(menuTmp.getIcon())+"'></i> "+StringUtils.handleNull(menuTmp.getName())+"</a></li>");
							}
						}
						while(!idStack.isEmpty()) {
							idStack.pop();
							out.println("</ul></li>");
						}
						out.println("</ul>");
					}
				}
				out.println("</div></div>");
				out.println(" <div class='sub-menu-bottom'><div class='sub-menu-bottom-2'></div></div>");
			}
			menus = null;
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
		smartResp = null;
	}
	
	/**
	 * 获取root菜单
	 * @return
	 */
	private List<TNMenu> getRootMenus() {
		List<TNMenu> roots = new ArrayList<TNMenu>();
		for(TNMenu menu : menus) {
			if(isFirstLevelMenu(menu, menus)) {
				roots.add(menu);
			}
		}
		return roots.size()>0?roots:null;
	}
	
	/**
	 * 获取root菜单
	 * @return
	 */
	private List<TNMenu> getSubMenus(TNMenu menu) {
		List<TNMenu> subMenus = new ArrayList<TNMenu>();
		TreeHelper<TNMenu> menuHelper = new TreeHelper<TNMenu>();
		try {
			subMenus = menuHelper.outPutTree(menus, menu, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subMenus.size()>0?subMenus:null;
	}
	
	
	/**
	 * 判断是否为父菜单
	 * @param menu
	 * @return
	 */
	private boolean isParent(TNMenu menu,List<TNMenu> menus) {
		boolean is = false;
		for (TNMenu menuTmp : menus) {
			if(menu.getId().equals(menuTmp.getParentId())) {
				is = true;
				break;
			}
		}
		return is;
	}
	
	/**
	 * 判断是否为ROOT菜单
	 * @param menu
	 * @return
	 */
	private boolean isFirstLevelMenu(TNMenu menu,List<TNMenu> menus) {
		boolean is = true;
		for (TNMenu menuTmp : menus) {
			if(menu.getParentId().equals(menuTmp.getId())) {
				is = false;
				break;
			}
		}
		return is;
	}
	
	
	/**
	 * 计算层次
	 * @param parentId
	 * @return
	 */
	private int countLayer(String parentId) {
		int count = 0;
		for (int i=0;i<menus.size();) {
			if (menus.get(i).getId().equals(parentId)) {
				count++;
				parentId = menus.get(i).getParentId();
				i=0;
			} else {
				i++;
			}
		}
		return count;
	}

	
	/**
	 * 
	 * @param stack
	 * @param menuId
	 * @param out
	 */
	private void printEndTag(Stack<String> stack,String menuId,JspWriter out) {
		if(null != stack && stack.size()>0) {
			boolean is = true;
			while(!stack.empty() && is) {
				 String parentId = stack.peek();
				if(!parentId.equals(menuId)) {
					stack.pop();
					try {
						out.println("</ul></li>");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					is = false;
				}
		   }
		}
	}

	public SmartResponse<TNMenu> getsmartResp() {
		return smartResp;
	}

	public void setsmartResp(SmartResponse<TNMenu> smartResp) {
		this.smartResp = smartResp;
	}
}
