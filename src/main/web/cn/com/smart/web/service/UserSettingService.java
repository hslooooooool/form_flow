package cn.com.smart.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNUserSetMenu;
import cn.com.smart.web.dao.impl.UserSetMenuDao;

/**
 * 
 * @author lmq
 *
 */
@Service("userSetServ")
public class UserSettingService extends MgrServiceImpl<TNUserSetMenu> {

	@Autowired
	private UserSetMenuDao userSetMenuDao;
	
	/**
	 * 获取个人设置菜单列表
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<Object> findSettingMenus() throws ServiceException {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		try {
			List<Object> lists = userSetMenuDao.queryRelated();
			if(null != lists && lists.size()>0) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
				smartResp.setTotalNum(lists.size());
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
	
	/**
	 * 获取个人设置菜单列表
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<TNUserSetMenu> findValidSetting() throws ServiceException {
		SmartResponse<TNUserSetMenu> smartResp = new SmartResponse<TNUserSetMenu>();
		try {
			List<TNUserSetMenu> lists = userSetMenuDao.queryVaidMenu();
			if(null != lists && lists.size()>0) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
				smartResp.setTotalNum(lists.size());
			}
			lists = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
}
