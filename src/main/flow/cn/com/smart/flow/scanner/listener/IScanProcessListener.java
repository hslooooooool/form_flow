package cn.com.smart.flow.scanner.listener;

import cn.com.smart.flow.scanner.AbstractFlowScanner;


/**
 * 流程扫描监听者
 * @author lmq  2017年3月22日
 * @version 1.0
 * @since 1.0
 */
public interface IScanProcessListener {

	/**
	 * 执行方法
	 * @param scanner 扫描器
	 * @param objs
	 */
	public void execute(AbstractFlowScanner scanner, Object... objs);
	
}
