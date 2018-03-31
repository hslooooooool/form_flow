package cn.com.smart.web.push;

import java.util.Set;

/**
 * 消息推送接口
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public interface IPushMessage {

	/**
	 * 获取推送插件的名称
	 * @return
	 */
	public String getPluginName();
	
	/**
	 * 全发
	 * @param messageType 消息类型
	 * @param sendData 发送数据对象
	 */
	void sendAll(MessageType messageType, SendData sendData);
	
	/**
	 * 发送给指定用户
	 * @param messageType 消息类型
	 * @param userId 用户ID
	 * @param sendData 发送数据对象
	 */
	void sendUser(MessageType messageType, String userId, SendData sendData);
	
	/**
	 * 发送给多个用户
	 * @param messageType 消息类型
	 * @param userIds 多个用户ID
	 * @param sendData 发送数据对象
	 */
	void sendUser(MessageType messageType, Set<String> userIds, SendData sendData);

	/**
	 * 发送给某个组（如：部门等）
	 * @param messageType 消息类型
	 * @param groupId 组ID（如：部门ID）
	 * @param sendData 发送数据对象
	 */
	void sendGroup(MessageType messageType, String groupId,  SendData sendData);
	
	/**
	 * 发送给多个组
	 * @param messageType 消息类型
	 * @param groupIds 多个组的ID
	 * @param sendData 发送数据对象
	 */
	void sendGroup(MessageType messageType, Set<String> groupIds, SendData sendData);
	
}
