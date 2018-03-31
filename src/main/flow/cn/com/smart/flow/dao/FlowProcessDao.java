package cn.com.smart.flow.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.flow.bean.entity.TFlowForm;
import cn.com.smart.flow.bean.entity.TFlowProcess;
import cn.com.smart.form.bean.entity.TForm;

/**
 * 流程处理DAO
 * @author lmq
 *
 */
@Repository
public class FlowProcessDao extends BaseDaoImpl<TFlowProcess> {

	
	@Autowired
	private FlowFormDao flowFormDao;

	/**
	 * 更新
	 * @param orgProcess
	 * @return
	 * @throws DaoException
	 */
	public boolean updateByProcessId(TFlowProcess orgProcess) throws DaoException {
		boolean is= false;
		if(null != orgProcess) {
			String hql = "update "+TFlowProcess.class.getName()+" set orgId=:orgId where processId=:processId";
			Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("orgId", orgProcess.getOrgId());
			params.put("processId", orgProcess.getProcessId());
			int result = executeHql(hql, params);
			params = null;
			orgProcess = null;
			is = result>0?true:false;
		}
		return is;
	}
	
	
	/**
	 * 通过流程ID获取流程相关数据
	 * @param processId
	 * @return
	 * @throws DaoException
	 */
	public TFlowProcess findByProcessId(String processId) throws DaoException {
		TFlowProcess flowProcess = null;
		if(StringUtils.isNotEmpty(processId)) {
			Map<String,Object> param = new HashMap<String, Object>(1);
			param.put("processId", processId);
			List<TFlowProcess> lists = queryByField(param);
			if(null != lists && lists.size()>0) {
				flowProcess = lists.get(0);
			}
			lists = null;
		}
		return flowProcess;
	}
	
	
	/**
	 * 获取表单信息
	 * @param processId 流程ID
	 * @return
	 * @throws Exception
	 */
	public TForm getFormByProcessId(String processId) throws Exception {
		TForm form = null;
		if(StringUtils.isNotEmpty(processId)) {
			String hql = "from "+TForm.class.getName()+" t where t.id=(select tl.formId from "+
		    TFlowProcess.class.getName()+" tl where tl.processId=:processId)";
			Map<String,Object> param = new HashMap<String, Object>(1);
			param.put("processId", processId);
			List<Object> objs = queryObjHql(hql, param);
			if(null != objs && objs.size()>0) {
				form = (TForm)objs.get(0);
			}
			objs = null;
			param = null;
		}
		return form;
	}
	
	
	/**
	 * 级联删除数据
	 * @param processIds
	 * @return
	 */
	public boolean deleteAssocData(String[] processIds) {
		boolean is = false;
		if(null != processIds && processIds.length>0) {
			Map<String,Object> param = new HashMap<String, Object>(1);
			param.put("processId", processIds);
			try {
				List<TFlowForm> lists = flowFormDao.queryByField(param);
				flowFormDao.deleteAssocByObj(lists);
				deleteByField(param);
				is = true;
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return is;
	}
	
}
