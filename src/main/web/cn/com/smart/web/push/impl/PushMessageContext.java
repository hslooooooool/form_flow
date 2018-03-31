package cn.com.smart.web.push.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.init.config.InitSysConfig;
import cn.com.smart.service.SmartContextService;
import cn.com.smart.web.bean.entity.TNPushMsgLog;
import cn.com.smart.web.bean.entity.TNPushMsgReceiver;
import cn.com.smart.web.constant.IWebConstant;
import cn.com.smart.web.push.IPushMessage;
import cn.com.smart.web.push.MessageReceiverType;
import cn.com.smart.web.push.MessageType;
import cn.com.smart.web.push.SendData;
import cn.com.smart.web.service.PushMsgLogServ;
import cn.com.smart.web.service.PushMsgReceiverServ;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

@Component
public class PushMessageContext {

	private static final Logger logger = LoggerFactory.getLogger(PushMessageContext.class);
	/**
	 * 发送者为系统
	 */
	private static final String SYS_SENDER = "sys";
	
	private List<IPushMessage> pushImplList = null;
	@Autowired
	private PushMsgLogServ msgLogServ;
	@Autowired
	private PushMsgReceiverServ msgReceiverServ;
	
	public PushMessageContext() {
		initList();
	}
	
	/**
	 * 初始化推送信息实现列表
	 */
	private void initList() {
		if(null == pushImplList) {
			pushImplList = new ArrayList<IPushMessage>();
		}
		pushImplList = SmartContextService.finds(IPushMessage.class);
	}
	
	/**
	 * 发送消息
	 * @param msgType 消息类型；具体消息类型请查看 {@link MessageType} 枚举中的定义
	 * @param userIds 发送给用户的ID
	 * @param groupIds 发送给组的ID（部门ID）
	 * @param content 发送内容
	 * @param sender 发送者
	 */
	public void sendMsg(MessageType msgType, Set<String> userIds, Set<String> groupIds, String content, String sender) {
		LoggerUtils.debug(logger, "正在推送消息...");
		if(null == msgType || StringUtils.isEmpty(content)) {
			LoggerUtils.error(logger, "发送的消息类型或消息内容为空");
			throw new NullArgumentException();
		}
		sender = StringUtils.isEmpty(sender) ? SYS_SENDER : sender;
		SendData sendData = null;
		//待办提醒和公告提醒不存库
		switch (msgType) {
		case TODO:
		case NOTICE:
			sendData = new SendData(null, null, content);
			break;
		case MESSAGE:
			sendData = saveLog(msgType, userIds, groupIds, content, sender);
			break;
		default:
			break;
		}
		
		Set<String> pluginNames = getPluginNames(msgType);
		List<IPushMessage> pushAbleList = null;
		if(CollectionUtils.isNotEmpty(pluginNames)) {
			pushAbleList = new ArrayList<IPushMessage>(pluginNames.size());
			for (IPushMessage pushMsg : pushImplList) {
				for (String pluginName : pluginNames) {
					if(pluginName.equals(pushMsg.getPluginName())) {
						pushAbleList.add(pushMsg);
						break;
					}
				}
			}
		}
		/*if(CollectionUtils.isEmpty(pluginNames) && CollectionUtils.isEmpty(pushAbleList)) {
			throw new RuntimeException("没有找到推送插件名称对应的推送实现类");
		}*/
		if(CollectionUtils.isNotEmpty(pluginNames) && CollectionUtils.isNotEmpty(pushAbleList)) {
		    sendMsg(CollectionUtils.isEmpty(pushAbleList)?pushImplList:pushAbleList, msgType, userIds, groupIds, sendData);
        }
	}
	
	/**
	 * 发送消息
	 * @param pushMessage
	 * @param msgType
	 * @param userIds
	 * @param groupIds
	 * @param sendData
	 */
	private void sendMsg(List<IPushMessage> pushMessages, MessageType msgType ,Set<String> userIds, Set<String> groupIds, SendData sendData) {
		for (IPushMessage pushMsg : pushMessages) {
			this.sendMsg(pushMsg, msgType, userIds, groupIds, sendData);
		}
	}
	
	/**
	 * 发送消息
	 * @param pushMessage
	 * @param msgType
	 * @param userIds
	 * @param groupIds
	 * @param sendData
	 */
	private void sendMsg(IPushMessage pushMessage, MessageType msgType ,Set<String> userIds, Set<String> groupIds, SendData sendData) {
		if(CollectionUtils.isEmpty(userIds) && CollectionUtils.isEmpty(groupIds)) {
			pushMessage.sendAll(msgType, sendData);
		} else {
			if(CollectionUtils.isNotEmpty(userIds)) {
				pushMessage.sendUser(msgType, userIds, sendData);
			}
			if(CollectionUtils.isNotEmpty(groupIds)) {
				pushMessage.sendGroup(msgType, groupIds, sendData);
			}
		}
	}
	
	/**
	 * 保存日志
	 * @param msgType 消息类型
	 * @param userIds 用户ID集合
	 * @param groupIds 组ID集合（部门ID）
	 * @param content 发送内容
	 * @param sender 发送者
	 * @return 
	 */
	private SendData saveLog(MessageType msgType, Set<String> userIds, Set<String> groupIds, String content, String sender) {
		TNPushMsgLog pushMsgLog = new TNPushMsgLog();
		pushMsgLog.setContent(content);
		pushMsgLog.setMsgType(msgType.getValue());
		pushMsgLog.setSender(sender);
		LoggerUtils.debug(logger, "正在保存推送消息.");
		SmartResponse<String> smartResp = msgLogServ.save(pushMsgLog);
		if(!IWebConstant.OP_SUCCESS.equals(smartResp.getResult())) {
			LoggerUtils.error(logger, "保存推送消息[失败].");
			return null;
		}
		String msgId = smartResp.getData();
		TNPushMsgReceiver msgReceiver = new TNPushMsgReceiver();
		msgReceiver.setMsgId(msgId);
		msgReceiver.setIsAll(YesNoType.NO.getValue());
		SendData sendData = new SendData(msgId, null, content);
		List<TNPushMsgReceiver> receiverList = new ArrayList<TNPushMsgReceiver>();
		if(CollectionUtils.isEmpty(userIds) && CollectionUtils.isEmpty(groupIds)) {
			msgReceiver.setReceiverType(MessageReceiverType.ALL.getValue());
			msgReceiver.setIsAll(YesNoType.YES.getValue());
			receiverList.add(msgReceiver);
		} else {
			int count = 0;
			if(CollectionUtils.isNotEmpty(userIds)) {
				count = createMsgReceiverBean(receiverList, msgReceiver, userIds, count, MessageReceiverType.USER);
			}
			if(CollectionUtils.isNotEmpty(groupIds)) {
				count = createMsgReceiverBean(receiverList, msgReceiver, groupIds, count, MessageReceiverType.ORG);
			}
		}
		if(CollectionUtils.isNotEmpty(receiverList)) {
			LoggerUtils.debug(logger, "正在保存接收者消息.");
			msgReceiverServ.save(receiverList);
			LoggerUtils.debug(logger, "接收者消息[完成].");
		}
		return sendData;
	}
	
	/**
	 * 创建消息接收者对象
	 * @param receiverList
	 * @param msgReceiver
	 * @param receivers 
	 * @param count
	 * @param receiverType
	 * @return
	 */
	private int createMsgReceiverBean(List<TNPushMsgReceiver> receiverList, TNPushMsgReceiver msgReceiver, Set<String> receivers,
			int count, MessageReceiverType receiverType) {
		TNPushMsgReceiver msgReceiverTmp = null;
		for (String receiver : receivers) {
			if(count == 0) {
				msgReceiverTmp = msgReceiver;
			} else {
				msgReceiverTmp = msgReceiver.clone();
			}
			msgReceiverTmp.setReceiverType(receiverType.getValue());
			msgReceiverTmp.setReceiver(receiver);
			receiverList.add(msgReceiverTmp);
			count++;
		}
		return count;
	}
	
	/**
	 * 获取插件名称
	 * @param msgType 消息类型
	 * @return 返回插件集合；如果没有配置，则返回：null 
	 */
	private Set<String> getPluginNames(MessageType msgType) {
		String configKey = "push.msg."+msgType.getValue();
		String pluginName = InitSysConfig.getInstance().getValue(configKey);
		//采用Set的原因是：去重
		Set<String> pluginNames = null;
		if(StringUtils.isNotEmpty(pluginName)) {
			String[] array = pluginName.split(IWebConstant.MULTI_VALUE_SPLIT);
			pluginNames = new HashSet<String>(array.length);
			for (int i = 0; i < array.length; i++) {
				pluginNames.add(array[i]);
			}
		}
		return pluginNames;
	}
}
