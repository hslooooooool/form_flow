package cn.com.smart.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNCusIndexMinWin;
import cn.com.smart.web.bean.entity.TNCustomIndex;
import cn.com.smart.web.bean.entity.TNMinWindow;
import cn.com.smart.web.dao.impl.CusIndexMinWinDao;
import cn.com.smart.web.dao.impl.CustomIndexDao;
import cn.com.smart.web.dao.impl.MinWindowDao;

/**
 * 
 * @author lmq
 *
 */
@Service
public class UserCustomService extends MgrServiceImpl<TNCustomIndex> {

	@Autowired
	private CustomIndexDao cusIndexDao;
	@Autowired
	private CusIndexMinWinDao cusIndexMinWinDao;
	@Autowired
	private MinWindowDao minWinDao;
	
	/**
	 * 获取用户的首页布局
	 * @param userId
	 * @param isLoadMinWin
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<TNCustomIndex> getIndexLayout(String userId,boolean isLoadMinWin) throws ServiceException {
		SmartResponse<TNCustomIndex> smartResp = new SmartResponse<TNCustomIndex>();
		try {
			TNCustomIndex customIndex = cusIndexDao.queryIndexLayout(userId);
			if(null != customIndex) {
				List<TNCusIndexMinWin> lists = cusIndexMinWinDao.queryByCusIndex(customIndex.getId());
				if(null != lists && lists.size()>0) {
					if(isLoadMinWin) {
						TNMinWindow minWin = null;
						for (TNCusIndexMinWin cusIndexMinWin : lists) {
							minWin = minWinDao.find(cusIndexMinWin.getMinWinId());
							if(null != minWin) {
								cusIndexMinWin.setMinWin(minWin);
							}
						}
						minWin = null;
					}
					customIndex.setCusIndexMinWins(lists);
				}
				lists = null;
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setData(customIndex);
				smartResp.setTotalNum(1);
			}
			customIndex = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 保存布局信息 
	 * @param cusIndex
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> saveLayout(TNCustomIndex cusIndex) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != cusIndex) {
				if(StringUtils.isNotEmpty(cusIndex.getId())) {
					cusIndexDao.delete(cusIndex.getId());
				}
				smartResp = super.save(cusIndex);
				if(OP_SUCCESS.equals(smartResp.getResult())) {
					List<TNCusIndexMinWin> cusIndexMinWins = cusIndex.getCusIndexMinWins();
					if(null != cusIndexMinWins && cusIndexMinWins.size()>0) {
						for (TNCusIndexMinWin cusIndexMinWin : cusIndexMinWins) {
							cusIndexMinWin.setCusIndexId(smartResp.getData());
						}
						cusIndexMinWinDao.save(cusIndexMinWins);
						cusIndexMinWins = null;
					}
					smartResp.setMsg("首页布局设置成功");
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
}
