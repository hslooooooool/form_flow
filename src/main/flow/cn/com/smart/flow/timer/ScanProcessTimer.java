package cn.com.smart.flow.timer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.smart.flow.scanner.AbstractFlowScanner;
import cn.com.smart.service.SmartContextService;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;

/**
 * 扫描流程定时器
 * @author lmq  2017年3月23日
 * @version 1.0
 * @since 1.0
 */
public class ScanProcessTimer {

	private static final Logger logger = LoggerFactory.getLogger(ScanProcessTimer.class);
	private List<AbstractFlowScanner> scanners = null;
	
	
	/**
	 * 自动注册扫描器
	 */
	private void autoRegisterScanner() {
		scanners = SmartContextService.finds(AbstractFlowScanner.class);
	}
	
	public void run() {
		LoggerUtils.info(logger, "正在启动流程扫描定时器...");
		if(CollectionUtils.isEmpty(scanners)) {
			autoRegisterScanner();
		}
		for (AbstractFlowScanner scanner : scanners) {
			scanner.run();
		}
	}
}
