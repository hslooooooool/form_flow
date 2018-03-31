package cn.com.smart.bean;

import javax.persistence.Transient;

/**
 * 
 * @author lmq
 *
 */
public abstract class BaseBeanImpl implements BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7805110867344388840L;

	@Override
	@Transient
	public String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

}
