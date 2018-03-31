package cn.com.smart.web.spring.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *该类已过时，请使用 {@link cn.com.smart.service.SmartContextService}类代替; <br />
 * {@link #getBean(String)}方法请使用{@link cn.com.smart.service.SmartContextService#findByName(String)}方法代替
 * @author lmq
 *
 */
@Deprecated
public class SpringBeanFactoryUtil extends SpringBeanAutowiringSupport {

	@Autowired
	private BeanFactory beanFactory;
	
	private static SpringBeanFactoryUtil instance; 
	
	static {
		instance = new SpringBeanFactoryUtil();
	}
	
	private SpringBeanFactoryUtil() {
		
	}

	public static SpringBeanFactoryUtil getInstance() {
		return instance;
	}
	
	public Object getBean(String name) {
		return beanFactory.getBean(name);
	}
}
