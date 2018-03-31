package cn.com.smart.init;

import cn.com.smart.Smart;

/**
 * 初始化抽象类
 * @author lmq
 * @version 1.0
 * @since JDK1.6
 * 2015年8月22日
 */
public abstract class InitImpl extends Smart implements Init {

	@Override
	public abstract void reInit();

	protected abstract void init();
}
