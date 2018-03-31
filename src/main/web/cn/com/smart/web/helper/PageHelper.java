package cn.com.smart.web.helper;

import cn.com.smart.constant.IConstant;
import cn.com.smart.init.config.InitSysConfig;
import cn.com.smart.web.constant.IActionConstant;

import com.mixsmart.utils.StringUtils;

/**
 * 分页助手类
 * @author lmq
 *
 */
public class PageHelper {

	/**
	 * 
	 * @param total 总数据数
	 * @param pageSize 每页显示数
	 * @return
	 */
	public static int getTotalPage(long total, int pageSize) {
		return (int) Math.ceil((double) total / pageSize);
	}

	/**
	 * 获取默认页面显示数量
	 * @return
	 */
	public static int defaultPageSize() {
		int pageSize = 0;
		try {
			pageSize = Integer.parseInt(InitSysConfig.getInstance().getValue("page.per.size"));
		} catch (Exception e) {
			pageSize = IActionConstant.PRE_PAGE_SIZE;
		}
		return pageSize;
	}

	/**
	 * 获取页面显示数量
	 * @return
	 */
	public static Integer[] getShowPageSize() {
		Integer[] showPageSizes = null;
		String pageSizeStr = InitSysConfig.getInstance().getValue("page.show.size");
		boolean is = false;
		if(StringUtils.isNotEmpty(pageSizeStr)) {
			try {
				String[] pageSizeArray = pageSizeStr.split(IConstant.MULTI_VALUE_SPLIT);
				showPageSizes = new Integer[pageSizeArray.length];
				for (int i = 0; i < pageSizeArray.length; i++) {
					showPageSizes[i] = Integer.parseInt(pageSizeArray[i].trim());
				}
				is = true;
			} catch (Exception ex) {
				is = false;
			}
		}
		if(!is) {
			showPageSizes = new Integer[] { 15, 25, 35, 50 };
		}
		return showPageSizes;
	}

	/**
	 * 获取当前页---分页
	 * @return
	 */
	public static int getPage(int page) {
		if (page < 1) {
			page = 1;
		}
		return page;
	}

	/**
	 * 获取开始数据---分页
	 * @return
	 */
	public static int getStartNum(int page, int pageSize) {
		int startNum = (getPage(page) - 1) * getPerPageSize(pageSize);
		return startNum;
	}

	/**
	 * 获取开始数据---分页 
	 * @return
	 */
	@Deprecated
	public static int getStartNum(int page) {
		int startNum = (getPage(page) - 1) * getPerPageSize();
		return startNum;
	}

	/**
	 * 获取每页显示数量 
	 * @param perPageSize
	 * @return
	 */
	public static int getPerPageSize(int perPageSize) {
		if (perPageSize < 1) {
			perPageSize = defaultPageSize();
		}
		return perPageSize;
	}

	@Deprecated
	public static int getPerPageSize() {
		int perPageSize = 0;
		try {
			perPageSize = Integer.parseInt(InitSysConfig.getInstance().getValue("page.per.size"));
		} catch (Exception e) {
			perPageSize = IActionConstant.PRE_PAGE_SIZE;
		}
		return perPageSize;
	}
	
}
