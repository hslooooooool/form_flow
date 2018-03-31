package cn.com.smart.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNMinWindow;
import cn.com.smart.web.dao.impl.MinWindowDao;

/**
 * 
 * @author lmq
 *
 */
@Service
public class MinWinService extends MgrServiceImpl<TNMinWindow> {

	@Autowired
	private MinWindowDao minWinDao;
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<Object> getItem(String name) throws ServiceException {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
		    List<Object> lists = minWinDao.queryItems(name);
			if(null != lists && lists.size()>0) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
				smartResp.setTotalNum(lists.size());
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
}
