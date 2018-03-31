package cn.com.smart.web.spring;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.context.ISmartContext;

/**
 * 获取Spring上下文
 * @author lmq
 * @version 2015年8月13日
 * @since 1.0
 */
public class SpringSmartContext implements ISmartContext {

	private static final Logger log = Logger.getLogger(SpringSmartContext.class);
	
	private ApplicationContext applicationContext;
	private DefaultListableBeanFactory beanFactory;
	
	public SpringSmartContext(ApplicationContext applicaionContext) {
		this.applicationContext = applicaionContext;
		beanFactory = (DefaultListableBeanFactory)applicaionContext.getAutowireCapableBeanFactory();
	}

	@Override
	public void put(String name, Object obj) {
		this.errorPrompt();
	}

	@Override
	public void put(String name, Class<?> clazz) {
		if(StringUtils.isNotEmpty(name) && null != clazz) {
			BeanDefinition definition = new RootBeanDefinition(clazz);
			beanFactory.registerBeanDefinition(name, definition);
		}
	}

	@Override
	public boolean isExist(String name) {
		return applicationContext.containsBean(name);
	}

	@Override
	public <T> T find(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	@Override
	public <T> List<T> finds(Class<T> clazz) {
		String[] beanNames = applicationContext.getBeanNamesForType(clazz);
		List<T> beans = null;
		if(null != beanNames && beanNames.length>0) {
			beans = new ArrayList<T>();
			for (String beanName : beanNames) {
				beans.add(applicationContext.getBean(beanName, clazz));
			}
		}
		return beans;
	}

	@Override
	public <T> T findByName(String name, Class<T> clazz) {
		
		return applicationContext.getBean(name, clazz);
	}

	@Override
	public Object findByName(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 错误提示
	 */
	private void errorPrompt() {
		log.error("不支持添加实体类，请通过spring的注解方式添加");
	}
}
