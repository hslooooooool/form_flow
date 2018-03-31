package cn.com.smart.web.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.com.smart.context.ISmartContext;
import cn.com.smart.service.SmartContextService;
import cn.com.smart.web.ISmartWeb;
import cn.com.smart.web.SmartWeb;

/**
 * spring初始化结束后执行该类
 * @author lmq
 * @version 2015年8月8日
 * @since 1.0
 *
 */
@Component
public class InitSpringComplete implements ApplicationListener<ContextRefreshedEvent>{
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext context = event.getApplicationContext();
		if(null != context && null == context.getParent()) {
			ISmartContext smartContext = new SpringSmartContext(context);
			SmartContextService.setContext(smartContext);
			ISmartWeb smartWeb = new SmartWeb();
			smartWeb.initSystem();
		}
	}

}
