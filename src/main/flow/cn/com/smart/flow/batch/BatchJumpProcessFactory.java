package cn.com.smart.flow.batch;

import java.util.ArrayList;
import java.util.List;

import cn.com.smart.service.SmartContextService;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 批量跳转处理流程工厂
 * @author lmq <br />
 * 2016年11月8日
 * @version 1.0
 * @since 1.0
 */
public class BatchJumpProcessFactory {
	
	public static final String DEFAULT_NAME = "default";
	
	private static List<IBatchJumpProcess> batchs = new ArrayList<IBatchJumpProcess>();
	
	/**
	 * 获取批量跳转处理类
	 * @param batchName
	 * @return
	 */
	public static IBatchJumpProcess getBacthJumpProcess(String batchName) {
		IBatchJumpProcess batchJumpProcess = null;
		if(CollectionUtils.isEmpty(batchs)) {
			batchs = SmartContextService.finds(IBatchJumpProcess.class);
		}
		if(StringUtils.isEmpty(batchName)) {
			batchName = DEFAULT_NAME;
		}//if
		for (IBatchJumpProcess bjp : batchs) {
			if(batchName.equals(bjp.getName())) {
				batchJumpProcess = bjp;
				break;
			}
		}//for
		return batchJumpProcess;
	}
	
}
