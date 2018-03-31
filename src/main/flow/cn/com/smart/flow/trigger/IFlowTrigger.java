package cn.com.smart.flow.trigger;

/**
 * 流程触发器接口
 * @author lmq <br />
 * 2016年10月4日
 * @version 1.0
 * @since 1.0
 * @param <T>
 */
public interface IFlowTrigger<T> {

	/**
	 * 触发器名称
	 * @return
	 */
	public String getName();
	
	/**
	 * 具体触发器执行的方法
	 * @param t
	 */
	public void execute(T t);
	
}
