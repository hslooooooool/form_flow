package cn.com.smart.flow.scanner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.smart.flow.SnakerEngineFacets;
import cn.com.smart.flow.scanner.listener.IScanProcessListener;
import cn.com.smart.flow.service.FlowFormService;

import com.mixsmart.utils.CollectionUtils;

/**
 * 流程扫描器；
 * 该类为抽象类，具体扫描方法由子类实现；
 * 子类需要自行添加监听者；
 * 注：该类不提供删除监听者的方法；而且添加监听者时，未采用线程安全
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractFlowScanner<T extends IScanProcessListener> {

	private Set<T> sets = new HashSet<T>();
	
	@Autowired
	protected SnakerEngineFacets facets;
	@Autowired
	protected FlowFormService flowFormServ;
	
	protected Logger logger;
	
	public AbstractFlowScanner() {
		logger = LoggerFactory.getLogger(getClass());
	}
	
	/**
	 * 扫描方法；由子类实现该方法
	 */
	public abstract void run();
	
	/**
	 * 添加监听者
	 * @param listener
	 */
	protected void addListener(T listener) {
		if(null != listener) {
			sets.add(listener);
		}
	}
	
	/**
	 * 添加监听者
	 * @param listeners
	 */
	protected void addListeners(List<T> listeners) {
		if(CollectionUtils.isNotEmpty(listeners)) {
			sets.addAll(listeners);
		}
	}
	
	/**
	 * 获取监听者集合
	 * @return
	 */
	protected Set<T> getListners() {
		return sets;
	}
	
	/**
	 * 通知监听者
	 * @param objs
	 */
	protected void notifyListener(Object... objs) {
		for (T listener : getListners()) {
			listener.execute(this, objs);
		}
	}
}
