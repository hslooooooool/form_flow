package cn.com.smart.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.smart.ISmart;
import cn.com.smart.bean.BaseBean;
import cn.com.smart.bean.SmartResponse;
import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.filter.HandleFilterParam;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.service.IBaseEntityService;
import cn.com.smart.web.helper.PageHelper;

import com.mixsmart.utils.StringUtils;

/**
 * @author lmq
 * @version 1.0
 * @since 1.0
 *
 */
public class BaseEntityServiceImpl<T extends BaseBean> extends BaseServiceImpl implements IBaseEntityService<T>, ISmart {

	@Autowired
	private BaseDaoImpl<T> baseDao;
	
	@Override
	public SmartResponse<T> findAll() {
		SmartResponse<T> fsResp = new SmartResponse<T>();
		fsResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			List<T> list = baseDao.findAll();
			if(null != list && !list.isEmpty()) {
				fsResp.setResult(OP_SUCCESS);
				fsResp.setMsg(OP_SUCCESS_MSG);
				fsResp.setDatas(list);
				fsResp.setTotalNum(list.size());
			}
			list = null;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return fsResp;
	}

	@Override
	public SmartResponse<T> findByParam(Map<String, Object> param) {
		return findByParam(param,null);
	}
	
	@Override
	public SmartResponse<T> findByParam(Map<String, Object> param,String orderBy) {
		SmartResponse<T> fsResp = new SmartResponse<T>();
		fsResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		List<T> list = baseDao.queryByField(param,orderBy);
		if(null != list && !list.isEmpty()) {
			fsResp.setResult(OP_SUCCESS);
			fsResp.setMsg(OP_SUCCESS_MSG);
			fsResp.setDatas(list);
			fsResp.setTotalNum(list.size());
		}
		list = null;
		return fsResp;
	}

	@Override
	public SmartResponse<T> findByParam(Map<String, Object> param, int page, int pageSize) {
		return findByParam(param, page, pageSize,null);
	}
	
	@Override
	public SmartResponse<T> findByParam(Map<String, Object> param, int page, int pageSize,String orderBy) {
		SmartResponse<T> fsResp = new SmartResponse<T>();
		fsResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		long total = baseDao.count(param);
		if(total>0) {
			int start = PageHelper.getStartNum(page, pageSize);
			List<T> list = baseDao.queryByField(param,start,pageSize,orderBy);
			if(null != list && !list.isEmpty()) {
				fsResp.setResult(OP_SUCCESS);
				fsResp.setMsg(OP_SUCCESS_MSG);
				fsResp.setDatas(list);
				fsResp.setTotalNum(total);
				fsResp.setTotalPage(PageHelper.getTotalPage(total, pageSize));
			}
			list = null;
		}
		return fsResp;
	}

	@Override
	public SmartResponse<T> findByParam(FilterParam filterParam) {
		return findByParam(filterParam,null);
	}
	
	@Override
	public SmartResponse<T> findByParam(FilterParam filterParam,String orderBy) {
		SmartResponse<T> fsResp = new SmartResponse<T>();
		fsResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		Map<String,Object> param = null;
		if(null != filterParam)
			param = new HandleFilterParam(filterParam).getParams();
		List<T> list = baseDao.queryByField(param, orderBy);
		if(null != list && !list.isEmpty()) {
			fsResp.setResult(OP_SUCCESS);
			fsResp.setMsg(OP_SUCCESS_MSG);
			fsResp.setDatas(list);
			fsResp.setTotalNum(list.size());
		}
		list = null;
		return fsResp;
	}

	@Override
	public SmartResponse<T> findByParam(FilterParam filterParam, int page, int pageSize) {
		return findByParam(filterParam, page, pageSize,null);
	}
	
	@Override
	public SmartResponse<T> findByParam(FilterParam filterParam, int page, int pageSize,String orderBy) {
		SmartResponse<T> fsResp = new SmartResponse<T>();
		fsResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		Map<String,Object> param = null;
		if(null != filterParam)
			param = new HandleFilterParam(filterParam).getParams();
		long total = baseDao.count(param);
		if(total>0) {
			int start = PageHelper.getStartNum(page, pageSize);
			List<T> list = baseDao.queryByField(param,start,pageSize,orderBy);
			if(null != list && !list.isEmpty()) {
				fsResp.setResult(OP_SUCCESS);
				fsResp.setMsg(OP_SUCCESS_MSG);
				fsResp.setDatas(list);
				fsResp.setTotalNum(total);
				fsResp.setTotalPage(PageHelper.getTotalPage(total, pageSize));
			}
			list = null;
		}
		return fsResp;
	}
	
	@Override
	public SmartResponse<T> finds(String[] id) throws ServiceException {
		SmartResponse<T> smartResp = new SmartResponse<T>();
		try {
			List<T> lists = baseDao.find(id);
			if(null != lists && lists.size()>0) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
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
	public SmartResponse<T> find(String id) {
		SmartResponse<T> smartResp = new SmartResponse<T>();
		if(StringUtils.isNotEmpty(id)) {
			T t = baseDao.find(id);
			if(null != t) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setData(t);
			}
		}
		return smartResp;
	}

	@Override
	public BaseDaoImpl<T> getDao() {
		return baseDao;
	}
	
}
