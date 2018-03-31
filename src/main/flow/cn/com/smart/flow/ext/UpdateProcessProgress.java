package cn.com.smart.flow.ext;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.flow.IProcessExecuteAware;
import cn.com.smart.flow.bean.SubmitFormData;
import cn.com.smart.flow.bean.entity.TFlowProcess;
import cn.com.smart.flow.dao.FlowProcessDao;
import cn.com.smart.flow.helper.ProcessHelper;
import cn.com.smart.flow.service.FlowFormService;

/**
 * 更新流程进度
 * @author lmq
 * @version 1.0
 * @since 1.0
 * 2015年11月16日
 */
@Component
public class UpdateProcessProgress implements IProcessExecuteAware {

	private static final Logger logger = LoggerFactory.getLogger(UpdateProcessProgress.class);

	@Autowired
	private FlowFormService flowFormServ;
	@Autowired
	private FlowProcessDao flowProcessDao;
	
	@Override
	public void taskExeAfter(SubmitFormData formData, String userId,String orgId) {
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("processId", formData.getProcessId());
		List<TFlowProcess> processList = flowProcessDao.queryByField(param);
		if(CollectionUtils.isNotEmpty(processList)) {
			TFlowProcess process = processList.get(0);
			param.clear();
			if(StringUtils.isNotEmpty(formData.getOrderId())) {
				param.put("orderId", formData.getOrderId());
				param.put("totalNodeNum", process.getTotalNodeNum());
				int position = countCurrentPosition(process.getNodeNameCollection(), formData.getTaskName());
				param.put("executeNodeNum", position);
				float rate = 0f;
				if(position == 0) {
					rate = 0;
				} else if(process.getTotalNodeNum() == position) {
					rate = 100;
				} else {
					DecimalFormat decimalFormater = new DecimalFormat("#.00");
					double tmp = (position/(double)process.getTotalNodeNum())*100;
					rate = Float.parseFloat(decimalFormater.format(tmp));
				}
				param.put("progress", rate);
				LoggerUtils.info(logger,"正在更新流程表单信息...");
				flowFormServ.execute("update_flow_form_info", param);
				LoggerUtils.info(logger,"更新流程表单信息[成功].");
			}
		}
	}
	
	/**
	 * 计算当前节点位置
	 * @param nodeCollection 节点字符串集合
	 * @param currentNodeName 当前节点名称
	 * @return 返回节点所在位置；如果计算失败返回：0
	 */
	private int countCurrentPosition(String nodeCollection,String currentNodeName) {
		int count = 0;
		if(StringUtils.isNotEmpty(nodeCollection) && StringUtils.isNotEmpty(currentNodeName)) {
			String[] nodes = ProcessHelper.nodeStrToArray(nodeCollection);
			if(null != nodes && nodes.length>0) {
				for (int i = 0; i < nodes.length; i++) {
					if(nodes[i].equals(currentNodeName)) {
						count = i+1;
						break;
					}
				}
			}
		}
		return count;
	}

}
