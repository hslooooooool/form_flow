package cn.com.smart.web.push.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.comet4j.core.CometConnection;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.smart.web.bean.entity.TNUser;
import cn.com.smart.web.push.IPushMessage;
import cn.com.smart.web.push.MessageType;
import cn.com.smart.web.push.SendData;
import cn.com.smart.web.service.UserService;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 通过Comet4j插件推送消息
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Component
public class Comet4jPushMessage implements IPushMessage {
	
	private static final Logger logger = LoggerFactory.getLogger(Comet4jPushMessage.class);
	
	private CometEngine cometEngine;
	@Autowired
	private UserService userServ;
	
	/**
	 * 通过Comet4j插件发消息(构造函数)
	 */
	public Comet4jPushMessage() {
		cometEngine = CometContext.getInstance().getEngine();
	}

	@Override
	public String getPluginName() {
		return "comet4j";
	}
	
	@Override
	public void sendAll(MessageType messageType, SendData sendData) {
		LoggerUtils.debug(logger, "正在全发消息...");
		if(null != sendData) {
			cometEngine.sendToAll(MessageType.NOTICE.getValue(), sendData);
			LoggerUtils.debug(logger, "全发消息---[成功]---");
		} else {
			LoggerUtils.error(logger, "发送内容为空...");
		}
	}

	@Override
	public void sendUser(MessageType messageType, String userId, SendData sendData) {
		LoggerUtils.debug(logger, "正在给["+userId+"]发送消息...");
		if(null != messageType && StringUtils.isNotEmpty(userId) && null != sendData) {
			CometConnection cc = cometEngine.getConnection(userId);
			cometEngine.sendTo(messageType.getValue(), cc, sendData);
			LoggerUtils.debug(logger, "发送消息---[成功]---");
		} else {
			LoggerUtils.error(logger, "发送目标或发送内容为空...");
		}
	}

	@Override
	public void sendUser(MessageType messageType, Set<String> userIds, SendData sendData) {
		LoggerUtils.debug(logger, "正在给多人发送消息...");
		if(null != messageType && CollectionUtils.isNotEmpty(userIds) && null != sendData) {
			List<CometConnection> ccs = new ArrayList<CometConnection>();
			for (String userId : userIds) {
				CometConnection cc = cometEngine.getConnection(userId);
				ccs.add(cc);
			}
			cometEngine.sendTo(messageType.getValue(), ccs, sendData);
			LoggerUtils.debug(logger, "多人发送消息---[成功]---");
		} else {
			LoggerUtils.error(logger, "发送目标或发送内容为空...");
		}
	}

	@Override
	public void sendGroup(MessageType messageType, String groupId, SendData sendData) {
		Set<String> groupIds = new HashSet<String>(1);
		groupIds.add(groupId);
		sendGroup(messageType, groupIds, sendData);
	}

	@Override
	public void sendGroup(MessageType messageType, Set<String> groupIds, SendData sendData) {
		LoggerUtils.debug(logger, "正在给组发送消息...");
		if(null != messageType && CollectionUtils.isNotEmpty(groupIds) && null != sendData) {
			String[] orgIds = new String[groupIds.size()];
			groupIds.toArray(orgIds);
			Set<String> userIds = getUserIdByOrgId(orgIds);
			if(null == userIds) {
				LoggerUtils.debug(logger, "发送的组中没有用户...");
				return;
			}
			List<CometConnection> ccs = new ArrayList<CometConnection>();
			for (String userId : userIds) {
				CometConnection cc = cometEngine.getConnection(userId);
				ccs.add(cc);
			}
			cometEngine.sendTo(messageType.getValue(), ccs, sendData);
			LoggerUtils.debug(logger, "组信息发送---[成功]---");
		} else {
			LoggerUtils.error(logger, "发送目标或发送内容为空...");
		}
	}

	/**
	 * 获取组织架构下的用户
	 * @param orgIds
	 * @return
	 */
	private Set<String> getUserIdByOrgId(String[] orgIds) {
		Set<String> userIds = null;
		List<TNUser> users = userServ.getDao().queryByOrgIds(orgIds);
		if(CollectionUtils.isNotEmpty(users)) {
			userIds = new HashSet<String>(users.size());
			for(TNUser user : users) {
				userIds.add(user.getId());
			}
		}
		users = null;
		return userIds;
	}

}
