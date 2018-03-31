package cn.com.smart.flow.scanner.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.entity.Task;

import com.mixsmart.utils.LoggerUtils;

import cn.com.smart.flow.bean.entity.TFlowForm;
import cn.com.smart.flow.scanner.AbstractFlowScanner;
import cn.com.smart.flow.scanner.TaskScanner;

/**
 * 扫描任务的监听者；
 * 该接口为抽象类；具体实现方式由子类实现
 * @author lmq  2017年3月22日
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractScanTaskListener implements IScanProcessListener {

	protected Logger logger = null;
	
	public AbstractScanTaskListener() {
		logger = LoggerFactory.getLogger(getClass());
	}
	
	@Override
	public void execute(AbstractFlowScanner scanner, Object... objs) {
		if(scanner instanceof TaskScanner) {
			if(null != objs && objs.length > 0) {
				Task task = (Task) objs[0];
				TFlowForm flowForm = (TFlowForm) objs[1];
				execute(task, flowForm);
			} else {
				LoggerUtils.error(logger, "参数错误");
				throw new IllegalArgumentException();
			} 
		} else {
			throw new ClassCastException();
		}
	}

	/**
	 * 处理扫描到的任务；
	 * 具体处理方法由子类实现
	 * @param task 任务实体
	 * @param flowForm 流程表单实体
	 */
	protected abstract void execute(Task task, TFlowForm flowForm);
	
}
