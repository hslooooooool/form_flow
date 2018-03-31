package cn.com.smart.web.push.impl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.comet4j.core.CometContext;

import cn.com.smart.web.push.MessageType;

/**
 * 注册comet4j通道
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public class RegistComet4jChannel implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		CometContext cometContext = CometContext.getInstance();
		//注册公告通道和消息通道及待办通道
		cometContext.registChannel(MessageType.NOTICE.getValue());
		cometContext.registChannel(MessageType.MESSAGE.getValue());
		cometContext.registChannel(MessageType.TODO.getValue());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		CometContext cometContext = CometContext.getInstance();
		cometContext.removeAllListener();
	}

}
