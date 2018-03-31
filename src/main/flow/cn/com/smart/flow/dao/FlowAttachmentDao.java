package cn.com.smart.flow.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.flow.bean.entity.TFlowAttachment;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.dao.impl.AttachmentDao;

import com.mixsmart.utils.StringUtils;

/**
 * @author lmq
 * @create 2015年6月22日
 * @version 1.0 
 * @since 
 *
 */
@Repository("flowAttDao")
public class FlowAttachmentDao extends BaseDaoImpl<TFlowAttachment>{

	@Autowired
	private AttachmentDao attDao;

	@Override
	public boolean delete(Serializable id) throws DaoException {
		boolean is = false;
		if(null != id && StringUtils.isNotEmpty(id.toString())) {
			String[] ids = id.toString().split(",");
			List<TFlowAttachment> lists = find(ids);
			if(null != lists && lists.size()>0) {
				if(delete(lists)) {
					log.info("流程附件信息删除[成功]");
					String idStr = "";
					for (TFlowAttachment flowAtt : lists) {
						idStr += flowAtt.getAttachmentId()+",";
					}
					idStr = idStr.substring(0,idStr.length()-1);
					//删除附件
					if(attDao.delete(idStr)) {
						is = true;
						log.info("流程附件删除[成功]");
					} else {
						log.error("流程附件删除[失败]");
					}
				} else {
					log.error("流程附件信息删除[失败]");
				}
			}
		}
		return is;
	}

	/**
	 * 查询附件信息通过流程实例ID
	 * @param orderId 流程实例ID
	 * @return 返回附件实体集合
	 */
	@SuppressWarnings("unchecked")
	public List<TNAttachment> queryAttachmentByOrderId(String orderId) {
		List<TNAttachment> atts = null;
		if(StringUtils.isEmpty(orderId)) {
			return atts;
		}
		String sql = SQLResUtil.getOpSqlMap().getSQL("get_attachment_by_orderid");
		if(StringUtils.isNotEmpty(sql)) {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("orderId", orderId);
			try {
				SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, param, true);
				sqlQuery.addEntity(TNAttachment.class);
				atts = sqlQuery.list();
			} catch (Exception e) {
				throw new DaoException(e);
			}
		}
		return atts;
	}
	
	
	/**
	 * 查询附件信息通过流程实例ID和任务KEY（任务名称）
	 * @param orderId 流程实例ID
	 * @param taskKey 任务KEY
	 * @return 返回附件实体集合
	 */
	@SuppressWarnings("unchecked")
	public List<TNAttachment> queryAttachment(String orderId, String taskKey) {
		List<TNAttachment> atts = null;
		if(StringUtils.isEmpty(orderId)) {
			return atts;
		}
		String sql = SQLResUtil.getOpSqlMap().getSQL("get_attachment_by_taskkey");
		if(StringUtils.isNotEmpty(sql)) {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("orderId", orderId);
			param.put("taskKey", taskKey);
			try {
				SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, param, true);
				sqlQuery.addEntity(TNAttachment.class);
				atts = sqlQuery.list();
			} catch (Exception e) {
				throw new DaoException(e);
			}
		}
		return atts;
	}
	
}
