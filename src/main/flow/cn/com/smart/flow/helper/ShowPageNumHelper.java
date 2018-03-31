package cn.com.smart.flow.helper;

import java.util.ArrayList;
import java.util.List;

import cn.com.smart.bean.SmartResponse;

/**
 * 该类已过期，不提倡使用；
 * 如果考虑分页请使用{@link cn.com.smart.web.tag.PanelFooterTag} 类对应的标签（&lt;cnoj:panelFooter />）
 * @author lmq
 * @create 2015年7月17日
 * @version 1.0 
 * @since 
 *
 */
public class ShowPageNumHelper {

	/**
	 * 处理显示页面数字
	 * @param smartResp
	 * @param page
	 * @return
	 */
	public static List<String> showNumHandle(SmartResponse<?> smartResp,int page) {
		List<String> pageNums = new ArrayList<String>();
		if(smartResp.getTotalPage()>1) {
			int showPageNum = 5;
			int first = 1;
			if(page>=showPageNum) {
				first = page-2;
			}
			int last = 1;
			if(smartResp.getTotalPage()>0) {
				if(smartResp.getTotalPage()>showPageNum) {
					if(page>=showPageNum) {
						last = page+2;
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
				pageNums.add(String.valueOf(i));
			}
		}
		return pageNums;
	}
	
}
