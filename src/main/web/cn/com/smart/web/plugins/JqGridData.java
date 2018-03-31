package cn.com.smart.web.plugins;

import java.util.List;

/**
 * JqGrid插件数据类
 * @author lmq
 *
 */
public class JqGridData {

	private Integer page = 0;
	
	private Integer total = 0;
	
	private Long records = 0l;
	
	private List<JqGridRows> rows;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public List<JqGridRows> getRows() {
		return rows;
	}

	public void setRows(List<JqGridRows> rows) {
		this.rows = rows;
	}
}
