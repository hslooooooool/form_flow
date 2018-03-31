package cn.com.smart.web.plugins;

/**
 * JqGrid请求参数
 * @author lmq <br />
 * 2017年2月24日
 * @version 1.0
 * @since 1.0
 */
public class JqGridRequest {

	private Integer page;
	
	private Integer rows;
	
	private String sidx;
	
	private String sort;

	public Integer getPage() {
		page = (null == page || page==0)?1:page;
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	/**
	 * 获取行数
	 * @param defaultRows 默认行数
	 * @return
	 */
	public Integer getRows(Integer defaultRows) {
		return (null == this.rows || this.rows == 0)?defaultRows : this.rows;
	}
}
