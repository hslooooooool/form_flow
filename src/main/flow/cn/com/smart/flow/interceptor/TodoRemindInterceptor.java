package cn.com.smart.flow.interceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.smart.flow.bean.entity.TFlowForm;
import cn.com.smart.flow.service.FlowFormService;
import cn.com.smart.web.bean.entity.TNOrg;
import cn.com.smart.web.bean.entity.TNPosition;
import cn.com.smart.web.bean.entity.TNUser;
import cn.com.smart.web.push.MessageType;
import cn.com.smart.web.push.impl.PushMessageContext;
import cn.com.smart.web.service.UserService;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 待办提醒
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Component
public class TodoRemindInterceptor implements SnakerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(TodoRemindInterceptor.class);
	
	private static final String MSG_TMPL = "您有一个新的待办，需要处理“${title}”中的“${displayName}”环节";
	
	private static final String START_NODE = "start";
	
	@Autowired
	private PushMessageContext pushMsgContext;
	@Autowired
	private UserService userServ;
	@Autowired
	private FlowFormService flowFormServ;

	@Override
	public void intercept(Execution execution) {
		LoggerUtils.debug(logger, "处理待办提醒");
		if(null == execution || CollectionUtils.isEmpty(execution.getTasks())) {
			LoggerUtils.error(logger, "流程任务为空");
			return;
		}
		List<Task> tasks = execution.getTasks();
		//如果上一节点是开始节点；则不提醒
		if(START_NODE.equals(tasks.get(0).getParentTaskId())) {
			return;
		}
		Set<String> userIds = null;
		Set<String> groupIds = null;
		Set<String> positionIds = null;
		
		String title = getTitle(execution.getOrder().getId());
		if(StringUtils.isEmpty(title)) {
			title = StringUtils.handleNull(execution.getOrder().getVariableMap().get("title"));
		}
		String sendContent = MSG_TMPL.replace("${title}", title);
		for (Task task : tasks) {
			userIds = new HashSet<String>();
			groupIds = new HashSet<String>();
			positionIds = new HashSet<String>();
			String[] actorIds = task.getActorIds();
			for (int i = 0; i < actorIds.length; i++) {
				if(actorIds[i].startsWith(TNOrg.PREFIX+"_")) {
					groupIds.add(actorIds[i]);
				} else if(actorIds[i].startsWith(TNPosition.PREFIX+"_")) {
					positionIds.add(actorIds[i]);
				} else {
					userIds.add(actorIds[i]);
				}
			}//for
			if(CollectionUtils.isNotEmpty(positionIds)) {
				Set<String> userIdTmps = getUserIdByPosition(positionIds);
				if(CollectionUtils.isNotEmpty(userIdTmps)) {
					userIds.addAll(userIdTmps);
				}
			}
			//task.getModel()
			String content = sendContent.replace("${displayName}", task.getDisplayName());
			pushMsgContext.sendMsg(MessageType.TODO, userIds, groupIds, content, null);
		}//for
		
	}
	
	/**
	 * 获取用户ID通过职位ID
	 * @param positionIds
	 * @return
	 */
	private Set<String> getUserIdByPosition(Set<String> positionIds) {
		Set<String> userIds = null;
		String[] array = new String[positionIds.size()];
		positionIds.toArray(array);
		List<TNUser> users = userServ.getDao().findByPositionId(array);
		if(CollectionUtils.isNotEmpty(users)) {
			userIds = new HashSet<String>(users.size());
			for (TNUser user : users) {
				userIds.add(user.getId());
			}
		}
		return userIds;
	} 
	
	/**
	 * 获取流程实例标题
	 * @param orderId
	 * @return
	 */
	private String getTitle(String orderId) {
		TFlowForm flowForm = flowFormServ.getFlowFormByOrderId(orderId);
		if(null != flowForm) {
			return flowForm.getTitle();
		}
		return null;
	}
}
