package cn.com.smart.web.tag.bean;

import com.mixsmart.enums.YesNoType;

/**
 * 页面参数
 * @author lmq
 *
 */
public class PageParam extends BaseBtn {
	
	private int page;
	
	private int pageSize;
	
	private String target;
	
	private Boolean isSelectSize = YesNoType.YES.getValue();
	
	public PageParam(String uri,String target,int page) {
		this.uri = uri;
		this.target = target;
		this.page = page;
	}
	
	public PageParam(String uri, String target, int page, int pageSize) {
		this(uri, target, page);
		this.pageSize = pageSize;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getIsSelectSize() {
		return isSelectSize;
	}

	public void setIsSelectSize(Boolean isSelectSize) {
		this.isSelectSize = isSelectSize;
	}
	
}
