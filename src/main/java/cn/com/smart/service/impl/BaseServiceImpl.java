package cn.com.smart.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.smart.ISmart;
import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.SmartResponse;
import cn.com.smart.dao.impl.OPDao;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.service.IBaseService;
import cn.com.smart.web.helper.PageHelper;

/**
 * 服务层基础实现类
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 */
public abstract class BaseServiceImpl implements IBaseService, ISmart {

	protected Logger logger;
	
	public BaseServiceImpl() {
		logger = LoggerFactory.getLogger(getClass());
	}
	
	@Autowired
	private OPDao opDao;
	
	@Override
	public int getTotalPage(long total,int pageSize) {
		return PageHelper.getTotalPage(total, pageSize);
	}

	@Override
	public SmartResponse<Object> findAll(Class<?> clazz) throws ServiceException {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
			List<Object> lists = opDao.findObjAll(clazz);
			if(null != lists && lists.size()>0) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
			} else {
				smartResp.setResult(OP_NOT_DATA_SUCCESS);
				smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
			}
			lists = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<Object> find(Class<?> clasz,String id) throws ServiceException {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
			Object bean = opDao.find(clasz,id);
			if(null != bean) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setData(bean);
			}
			bean = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}

	@Override
	public OPDao getOPDao() {
		return opDao;
	}

	@Override
	public SmartResponse<String> save(BaseBeanImpl bean) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != bean) {
			Serializable id = opDao.saveObj(bean);
			if(null != id) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setData(id.toString());
			}
		}
		return smartResp;
	}

	@Override
	public SmartResponse<String> update(BaseBeanImpl bean) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null != bean) {
			if(opDao.updateObj(bean)) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
			}
		}
		return smartResp;
	}
	
	
}
