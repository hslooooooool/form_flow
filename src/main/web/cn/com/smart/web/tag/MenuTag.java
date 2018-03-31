package cn.com.smart.web.tag;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNMenu;
import cn.com.smart.web.service.MenuService;

/**
 * 自定义菜单标签
 * @author lmq
 * @version 1.0 2015年8月23日
 * @since 1.0
 * 
 */
public class MenuTag extends BaseTag {

	private static final long serialVersionUID = -1833264518436649486L;	
	private int isCollapsed = 0;
	private String open = "shrink";
	private List<TNMenu> menus;
	
	private MenuService menuServ;
	private SmartResponse<TNMenu> smartResp;
	
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			log.info("正在创建菜单");
			if(null == smartResp) {
		        menuServ = (MenuService) getService("menuServ");
		        UserInfo userInfo = getUserInfo();
				smartResp = menuServ.getMenuTree(userInfo.getMenuRoleIds());
			}
			if(!OP_SUCCESS.equals(smartResp.getResult())) {
				out.println(smartResp.getMsg());
				return SKIP_BODY;
			}
			menus = smartResp.getDatas();
			Stack<String> idStack = new Stack<String>();
			open = (isCollapsed==1)?"expand":"shrink";
			if(null != menus && menus.size()>0) {
				out.println("<ul class='menu-ul' id='parent_0'>");
				int index = 1;
				for (TNMenu menu : menus) {
					if(isFirstLevelMenu(menu,menus)) {
						if(isParent(menu,menus)) {
							printEndTag(idStack, menu.getParentId(), out);
							out.println(getParentFirstLevelContentHtml(menu,index));
							idStack.push(menu.getId());
						} else {
							printEndTag(idStack, menu.getParentId(), out);
							out.println(getFirstLevelContentHtml(menu,index));
						}
					} else {
						if(isParent(menu,menus)) {
							printEndTag(idStack, menu.getParentId(), out);
							out.println(getParentContentHtml(menu,index));
							idStack.push(menu.getId());
						} else {
							printEndTag(idStack, menu.getParentId(), out);
							out.println(getContentHtml(menu,index));
						}
					}
					index++;
				}
				while(!idStack.isEmpty()) {
					idStack.pop();
					out.println("</ul></li>");
				}
				out.println("</ul>");
			}
			idStack = null;
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
	 * 判断是否为父菜单
	 * @param menu 菜单对象
	 * @param menus 菜单对象集合
	 * @return 是返回：true；否则返回：false
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
	 * 判断是否为一级菜单
	 * @param menu 菜单对象
	 * @param menus 菜单对象集合
	 * @return 是返回：true；否则返回：false
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
	 * 生成父菜单而且是一级菜单的内容
	 * @param menu 菜单对象
	 * @param index 序号
	 * @return 返回处理后的信息
	 */
	private String getParentFirstLevelContentHtml(TNMenu menu,int index) {
		String contents = "<li class='menu-li li-"+open+" layer-"+countLayer(menu.getParentId())+" "+(StringUtils.isEmpty(menu.getIcon())?"no-icon":"")+"' id='"+menu.getId()+"'>"+
	            "<a href='#' data-index='"+index+"' class='"+open+"' data-title='"+menu.getName()+"' data-menu-type='"+(menu.getResource() != null ? StringUtils.handleNull(menu.getResource().getType()):"")+"'"+
				" data-uri='"+(menu.getResource() != null ? StringUtils.handleNull(menu.getResource().getUri()):"")+"'>"+
	            "<div class='ui-icon ui-icon-triangle-1-e left'></div>"+
	            "<i class='glyphicon "+StringUtils.handleNull(menu.getIcon())+"'></i> "+menu.getName()+"</a><ul id='parent_"+menu.getId()+"'>";
		return contents;
	}
	
	/**
	 * 生成一级菜单内容
	 * @param menu 菜单对象
	 * @param index 序号
	 * @return 返回处理后的信息
	 */
	private String getFirstLevelContentHtml(TNMenu menu,int index) {
		String contents = "<li class='menu-li no-parent layer-"+countLayer(menu.getParentId())+" "+(StringUtils.isEmpty(menu.getIcon())?"no-icon":"")+"' id='"+menu.getId()+"'>"+
	           "<a href='#' data-index='"+index+"' data-title='"+menu.getName()+"' data-menu-type='"+(menu.getResource() != null ? StringUtils.handleNull(menu.getResource().getType()):"")+"'"+
				" data-uri='"+(menu.getResource() != null ? StringUtils.handleNull(menu.getResource().getUri()):"")+"'><i class='glyphicon "+StringUtils.handleNull(menu.getIcon())+"'></i>"+menu.getName()+"</a></li>";
		return contents;
	}
	
	/**
	 * 生成父菜单内容
	 * @param menu 菜单对象
	 * @param index 序号
	 * @return 返回处理后的信息
	 */
	private String getParentContentHtml(TNMenu menu,int index) {
		String contents = "<li class='li-"+open+" layer-"+countLayer(menu.getParentId())+" "+(StringUtils.isEmpty(menu.getIcon())?"no-icon":"")+"' id='"+menu.getId()+"'>"+
	            "<a href='#' data-index='"+index+"' class='"+open+"' data-title='"+menu.getName()+"' data-menu-type='"+(menu.getResource() != null ? StringUtils.handleNull(menu.getResource().getType()):"")+"'"+
				" data-uri='"+(menu.getResource() != null ? StringUtils.handleNull(menu.getResource().getUri()):"")+"'>"+
	            "<div class='ui-icon ui-icon-triangle-1-e left'></div>"+
	            "<i class='glyphicon "+StringUtils.handleNull(menu.getIcon())+"'></i> "+menu.getName()+"</a><ul id='parent_"+menu.getId()+"'>";
		return contents;
	}
	
	/**
	 * 生成菜单内容
	 * @param menu 菜单对象
	 * @param index 序号
	 * @return 返回处理后的信息
	 */
	private String getContentHtml(TNMenu menu,int index) {
		String contents = "<li class='no-parent layer-"+countLayer(menu.getParentId())+" "+(StringUtils.isEmpty(menu.getIcon())?"no-icon":"")+"' id='"+menu.getId()+"'>"+
		           "<a href='#' data-index='"+index+"' data-title='"+menu.getName()+"' data-menu-type='"+(menu.getResource() != null ? StringUtils.handleNull(menu.getResource().getType()):"")+"' data-uri='"+(menu.getResource() != null ? StringUtils.handleNull(menu.getResource().getUri()):"")+"'><i class='glyphicon "+StringUtils.handleNull(menu.getIcon())+"'></i> "+menu.getName()+"</a></li>";
			return contents;
	}
	
	/**
	 * 打印html的结束标签
	 * @param stack 菜单ID组成的堆栈
	 * @param menuId 菜单id
	 * @param out 输出对象
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
	
	
	/**
	 * 计算菜单层级
	 * @param parentId 父菜单ID
	 * @return 返回层级
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
	 * 获取是否展开
	 * @return 1--是；0--否
	 */
	public int getIsCollapsed() {
		return isCollapsed;
	}

	public void setIsCollapsed(int isCollapsed) {
		this.isCollapsed = isCollapsed;
	}

	public SmartResponse<TNMenu> getsmartResp() {
		return smartResp;
	}

	public void setsmartResp(SmartResponse<TNMenu> smartResp) {
		this.smartResp = smartResp;
	}

}
