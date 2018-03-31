package cn.com.smart.web.bean;

import cn.com.smart.init.config.InitSysConfig;
import cn.com.smart.web.constant.IActionConstant;

/**
 * 请求页面对象
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public class RequestPage {

	/**
	 * 页面
	 */
	private Integer page;
	
	/**
	 * 每页显示数
	 */
	private Integer pageSize;

	
	public Integer getPage() {
		if(null == page || page <= 0 || page > Integer.MAX_VALUE) {
			page = 1;
		}
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * 获取每页显示数量，每页显示数量最多不超过100条
	 * @return
	 */
	public Integer getPageSize() {
		if(null == pageSize || pageSize <= 0 || pageSize > 100) {
			try {
				pageSize = Integer.parseInt(InitSysConfig.getInstance().getValue("page.per.size"));
			} catch (Exception e) {
				pageSize = IActionConstant.PRE_PAGE_SIZE;
			}
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 计算开始显示位置
	 * @return
	 */
	public int getStartNum() {
		return (this.getPage()-1) * this.getPageSize();
	}
	
	/**
     * 获取总的页数 
     * @param total 总数据数
     * @return
     */
	public int getTotalPage(long total) {
		return (int) Math.ceil((double)total/this.getPageSize());
	}
}
