package cn.com.smart.flow.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.StreamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.flow.SnakerEngineFacets;
import cn.com.smart.flow.SnakerHelper;
import cn.com.smart.flow.batch.BatchJumpProcessFactory;
import cn.com.smart.flow.batch.IBatchJumpProcess;
import cn.com.smart.flow.bean.TaskInfo;
import cn.com.smart.flow.bean.entity.TFlowAttachment;
import cn.com.smart.flow.bean.entity.TFlowProcess;
import cn.com.smart.flow.dao.FlowAttachmentDao;
import cn.com.smart.flow.dao.FlowProcessDao;
import cn.com.smart.flow.enums.FlowDeployType;
import cn.com.smart.flow.ext.ExtProcess;
import cn.com.smart.flow.ext.ExtProcessModel;
import cn.com.smart.flow.helper.ProcessHelper;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.utils.StringUtil;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.dao.impl.AttachmentDao;
import cn.com.smart.web.service.OPService;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 流程服务类
 * @author lmq <br />
 * 修改：2016年10月15日
 * @version 1.1
 * @since 1.1
 */
@Service
public class FlowService extends MgrServiceImpl<TFlowProcess> {

	private static Logger logger = LoggerFactory.getLogger(FlowService.class);
	
	@Autowired
	private FlowProcessDao flowProcessDao;	
	@Autowired
	private SnakerEngineFacets facets;
	@Autowired
	private AttachmentDao attDao;
	@Autowired
	private OPService opServ;
	

	/**
	 * 获取流程数据
	 * @param processId 
	 * @return 返回内容为JSON格式的字符串
	 */
	public String getProcessJson(String processId) {
		String json = null;
		if(StringUtils.isNotEmpty(processId)) {
			ExtProcess process = (ExtProcess) facets.getEngine().process().getProcessById(processId);
			AssertHelper.notNull(process);
			ExtProcessModel processModel = (ExtProcessModel)process.getModel();
			if(processModel != null) {
				json = SnakerHelper.getModelJson(processModel);
			}
		}
		return json;
	}
	
	/**
	 * 部署流程
	 * @param processContent 流程内容
	 * @param processId 流程ID
	 * @param userId 用户ID
	 * @param deployType 流程部署类型
	 * @return 返回部署结果
	 */
	public SmartResponse<String> deployProcess(String processContents,String processId,
			String userId,FlowDeployType deployType) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(logger.isInfoEnabled()) {
			logger.info("正在部署流程...");
		}
		if(StringUtils.isNotEmpty(processContents) && null != deployType) {
			InputStream input = null;
			try {
				String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + SnakerHelper.convertXml(processContents);
				input = StreamHelper.getStreamFromString(xml);
				if(StringUtils.isNotEmpty(processId)) {
					if(deployType == FlowDeployType.SAVE) {
						processId = facets.getEngine().process().deploy(input, userId);
					} else {
						facets.getEngine().process().redeploy(processId, input);
					}
			    } else {
			    	processId = facets.deploy(input, userId);
			    }
				if(StringUtils.isNotEmpty(processId)) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("流程部署成功");
					smartResp.setData(processId);
					if(logger.isInfoEnabled()) {
						logger.info("部署流程[成功]---流程ID为["+processId+"]---");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			smartResp.setMsg("流程内容为空或流程部署类型不存在");
		}
		return smartResp;
	}
	
	
	/**
	 * 删除流程
	 * @param processIds
	 * @return
	 */
	public SmartResponse<String> deleteProcess(String[] processIds) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != processIds && processIds.length>0) {
			if(facets.delelteProcess(processIds)) {
				if(flowProcessDao.deleteAssocData(processIds)) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("流程删除成功");
				}
			}
		}
		return smartResp;
	}
	
	
	/**
	 * 保存流程附件
	 * @param att 附件信息
	 * @param taskInfo 流程任务相关数据信息
	 * @return
	 */
	public SmartResponse<String> saveAttachment(TNAttachment att,TaskInfo taskInfo) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != att && null != taskInfo) {
			TFlowAttachment processAtt = new TFlowAttachment();
			processAtt.setAttachmentId(att.getId());
			processAtt.setProcessId(taskInfo.getProcessId());
			processAtt.setOrderId(taskInfo.getOrderId());
			processAtt.setTaskId(taskInfo.getTaskId());
			processAtt.setTaskKey(taskInfo.getTaskKey());
			if(StringUtils.isEmpty(taskInfo.getOrderId())) {
				processAtt.setFormId(taskInfo.getFormId());
				processAtt.setUserId(att.getUserId());
			}
			try {
				smartResp = super.save(processAtt);
			} catch (ServiceException e) {
				e.printStackTrace();
			} finally {
				processAtt = null;
				att = null;
			}
		}
		return smartResp;
	}
	
	
	/**
	 * 删除附件
	 * @param attId
	 */
	public void deleteAttachment(String attId) {
		if(StringUtils.isNotEmpty(attId)) {
			try {
				attDao.delete(attId);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取未能正常流转的流程实例ID数组 <br/>
	 * 根据 <code>processId</code>获取对应流程的流程任务节点名称；
	 * 如果<code>processId</code>则获取所有流程的流程任务节点名称；
	 * 查询出流程任务表中不含有流程任务节点名称的流程实例
	 * @param processId
	 * @return
	 */
	public String[] getAbnormalOrderIds(String processId) {
		String[] orderIdArray = null;
		List<ExtProcess> processList = new ArrayList<ExtProcess>();
		if(StringUtils.isEmpty(processId)) {
			SmartResponse<TFlowProcess> smartResp = super.findAll();
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				for (TFlowProcess fp : smartResp.getDatas()) {
					ExtProcess process = (ExtProcess) facets.getEngine().process().getProcessById(fp.getProcessId());
					if(null != process) {
						processList.add(process);
					}
				}
			}
		} else {
			ExtProcess process = (ExtProcess) facets.getEngine().process().getProcessById(processId);
			if(null != process) {
				processList.add(process);
			}
		}
		Map<String,String[]> nodeMaps = ProcessHelper.getProcessNodes(processList);
		if(null != nodeMaps && nodeMaps.size()>0) {
			//获取流程实例ID列表
			List<Object> orderIds = new ArrayList<Object>();
			Set<String> processIds = nodeMaps.keySet();
			Map<String,Object> param = new HashMap<String, Object>();
			for (String processIdTmp : processIds) {
				param.put("processId", processIdTmp);
				param.put("taskNames", nodeMaps.get(processIdTmp));
				//获取流程实例ID列表
				SmartResponse<Object> orderResp = opServ.getDatas("get_abnormal_order_ids", param);
				if(OP_SUCCESS.equals(orderResp.getResult())) {
					orderIds.addAll(orderResp.getDatas());
				}
			}
			//根据获取的流程实例ID查询流程实例列表
			orderIdArray = new String[orderIds.size()];
			orderIds.toArray(orderIdArray);
		}
		return orderIdArray;
	}
	
	/**
	 * 批量跳转
	 * @param pf
	 * @param userId
	 * @param processId
	 * @param jumpNodeName
	 * @param batchContent
	 * @param nextAssigner
	 * @param handleSuggest
	 * @param isBack
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public SmartResponse<String> batchJump(ProcessFacade pf,String userId, String processId, 
			String jumpNodeName, String batchContent, String nextAssigner, String handleSuggest, String isBack) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isEmpty(processId) 
				|| StringUtils.isEmpty(batchContent) 
				|| StringUtils.isEmpty(jumpNodeName)) {
			throw new NullArgumentException("请求参数为空");
		}
		IBatchJumpProcess bjp = BatchJumpProcessFactory.getBacthJumpProcess(BatchJumpProcessFactory.DEFAULT_NAME);
		if(null == bjp) {
			throw new NullPointerException("未找到IBatchJumpProcess接口的实现类");
		}
		List<String> orderIds = bjp.build(batchContent);
		if(CollectionUtils.isNotEmpty(orderIds)) {
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("handleSuggest", StringUtil.escapeHtml(handleSuggest));
			params.put("operator", userId);
			params.put("isBack", isBack);
			params.put("nextLineName", "jump_"+jumpNodeName);
			Map<String, Object> nextAssigners = null;
			if(StringUtils.isNotEmpty(nextAssigner)) {
				String[] nextAssignerArray = nextAssigner.split(MULTI_VALUE_SPLIT);
				List<String> nextAssignerList = Arrays.asList(nextAssignerArray);
				nextAssigners = new HashMap<String, Object>();
				nextAssigners.put(jumpNodeName, nextAssignerList);
			}
			for (String orderId : orderIds) {
				smartResp = pf.executeAndJump(processId,orderId, SnakerEngine.ADMIN, nextAssigners, params, jumpNodeName);
			}
		}
		return smartResp;
	}
}
